package db;

import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.kernel.impl.util.StringLogger;
import scala.collection.Iterator;
import twitter.Conversation;
import twitter.Tweet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Provider {
    GraphDatabaseService graphDb;
    private static enum RelTypes implements RelationshipType
    {
        REPLY_TO,
        RETWEET
    }

    private GraphDatabaseService createDatabase(){
        return new GraphDatabaseFactory().newEmbeddedDatabase(createTempDatabaseDir().getAbsolutePath());
    }

    private static File createTempDatabaseDir()
    {
        File directory;
        try
        {
            directory = File.createTempFile( "neo4j-bootcamp", "dir" );
            System.out.println( String.format( "Created a new Neo4j database at [%s]", directory.getAbsolutePath() ) );
        }
        catch ( IOException e )
        {
            throw new RuntimeException( e );
        }
        if ( !directory.delete() )
        {
            throw new RuntimeException( "temp config directory pre-delete failed" );
        }
        if ( !directory.mkdirs() )
        {
            throw new RuntimeException( "temp config directory not created" );
        }
        directory.deleteOnExit();
        return directory;
    }

    public GraphDatabaseService getDatabase() {
        if(graphDb == null){
            graphDb = createDatabase();
        }
        return graphDb;
    }

    public Node createTweet(Tweet tweet){
        GraphDatabaseService db = getDatabase();

        Node tweetNode = db.createNode();

        tweetNode.setProperty( "text", tweet.getText() != null ? tweet.getText() :
                tweet.getRetweetedStatus().getText() != null ? tweet.getRetweetedStatus().getText() : "No Text");
        tweetNode.setProperty( "userId", tweet.getUserId());
        tweetNode.setProperty( "tweetId", tweet.getTweetId() != null ? tweet.getTweetId() : -1);
        if(tweet.getInReplyToStatusId() != null && tweet.getInReplyToStatusId().length() > 0){
            Node replyToNode = getTweet(tweet.getInReplyToStatusId());

            if(replyToNode != null) {
                tweetNode.createRelationshipTo(replyToNode, RelTypes.REPLY_TO);
            }
            else{
                replyToNode = createTweet(
                    new Tweet()
                        .setTweetId(tweet.getInReplyToStatusId())
                        .setText("?")
                );
                tweetNode.createRelationshipTo(replyToNode, RelTypes.REPLY_TO);
            }
        }
        if(tweet.getRetweetedStatus() != null){
            Node reTweetNode = getTweet(tweet.getRetweetedStatus().getTweetId());
            if(reTweetNode != null) {
                tweetNode.createRelationshipTo(reTweetNode, RelTypes.RETWEET);
            }
            else{
                reTweetNode = createTweet(
                        new Tweet()
                                .setTweetId(tweet.getRetweetedStatus().getTweetId())
                                .setText(tweet.getRetweetedStatus().getText())
                );
                tweetNode.createRelationshipTo(reTweetNode, RelTypes.RETWEET);
            }
        }
        Label tweetLabel = DynamicLabel.label("Tweet");
        tweetNode.addLabel(tweetLabel);

        return tweetNode;
    }



    public Node getTweet(String tweetId){
        ExecutionEngine engine = new ExecutionEngine( getDatabase(), StringLogger.DEV_NULL );
        GraphDatabaseService db = getDatabase();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tweetId",tweetId);
        String query = "MATCH (node:Tweet {tweetId: {tweetId} }) RETURN node";
        ExecutionResult result = engine.execute(query, params);
        Iterator<Node> nodes = result.columnAs("node");
        if (nodes.hasNext()) {
            Node tweet = nodes.next();
            return tweet;
        } else {
            return null;
        }

    }

    public Set<Node> getReplies(Node node){
        Set<Node> replies = new HashSet<Node>();
        ExecutionEngine engine = new ExecutionEngine( getDatabase(), StringLogger.DEV_NULL );
        GraphDatabaseService db = getDatabase();

        for (Relationship relationship : node.getRelationships(RelTypes.RETWEET, Direction.INCOMING)) {
            replies.add(relationship.getStartNode());
        }

        return replies;
    }

    /* Get all replies and replies to replies (and so on). Recursion ahoy */
    public Set<Node> getAllChildren(Node top, Set<Node> replies){
        if(replies == null) {
            replies = new HashSet<Node>();
        }
        ExecutionEngine engine = new ExecutionEngine( getDatabase(), StringLogger.DEV_NULL );
        GraphDatabaseService db = getDatabase();

        for(Node node : getReplies(top)){
            replies.add(node);
            if(node.hasRelationship(RelTypes.RETWEET, Direction.INCOMING)) {
                replies.addAll(getAllChildren(node, replies));
            }
        }
        return replies;
    }


    public Path getPathToConversationStart(Node end){
        Path fullPath = null;
        GraphDatabaseService db = getDatabase();

            TraversalDescription td = db.traversalDescription()
                    .depthFirst()
                    .relationships(RelTypes.RETWEET, Direction.OUTGOING)
                    .evaluator(Evaluators.all());

            org.neo4j.graphdb.traversal.Traverser traversal = td.traverse(end);
            for(Path path : traversal) {
                fullPath = path;
            }
        return fullPath;
    }

    public String getIdForConversationStart(Tweet end){
        String retVal = end.getUserId();
        Node node = getTweet(end.getUserId());
        Path path = getPathToConversationStart(node);
        retVal = (String)path.endNode().getProperty("tweetId");
        return retVal;
    }

    public String getIdForConversationStart(String endId){
        String retVal = endId;
        Node node = getTweet(endId);
        Path path = getPathToConversationStart(node);
        retVal = (String)path.endNode().getProperty("tweetId");
        return retVal;

    }


    public Node getNodeForConversationStart(String endId){
        Node retVal = getTweet(endId);
        Path path = getPathToConversationStart(retVal);
        retVal = path.endNode();
        return retVal;

    }


    public int getConversationSizeForId(String id){
        return getAllChildren(getTweet(id), null).size()+1;
    }

    public Conversation getConversationForTweet(Tweet tweet){

        String id = tweet.getTweetId() != null ? tweet.getTweetId() : tweet.getRetweetedStatus().getTweetId();
        Node topNode = getNodeForConversationStart(id);
        int size = getConversationSizeForId(id);
        System.out.println(id);
        String conversationId = String.valueOf(topNode.getProperty("tweetId"));
        String userId = (String)topNode.getProperty("userId");
        String text = (String)topNode.getProperty("text");

        Conversation conversation = new Conversation(size, conversationId, userId, text);
        return conversation;
    }


 //eksempel


    public static void main(String[] args){
        Provider provider = new Provider();
        GraphDatabaseService db = provider.getDatabase();
        Tweet test =  new Tweet()
                .setTweetId("3")
                .setText("Tweeet tweeeeet")
                .setInReplyToStatusId("1");
        try ( Transaction tx = db.beginTx(); ) {
            provider.createTweet(
                    new Tweet()
                            .setTweetId("1")
                            .setRetweetedStatus(test)
            );

            provider.createTweet(
                    new Tweet()
                            .setTweetId("2")
                            .setRetweetedStatus(test)
            );

            provider.createTweet(
                    new Tweet()
                            .setTweetId("2")
                            .setRetweetedStatus(test)
            );

            provider.createTweet(
                    test
            );

            tx.success();
        } catch(Exception e) {
            e.printStackTrace();
        }

        try(Transaction tx = db.beginTx()) {
            for (PropertyContainer tweet : provider.getPathToConversationStart(provider.getTweet("3"))) {
                if(tweet instanceof Relationship){
                    System.out.println(((Relationship) tweet).getType().name());
                }
                for (String propertyKey : tweet.getPropertyKeys()) {
                    System.out.println("\t" + propertyKey + " : " + tweet.getProperty(propertyKey));
                }
            }
            System.out.println(provider.getConversationSizeForId("1"));
            System.out.println(provider.getConversationSizeForId(provider.getIdForConversationStart("1")));
            System.out.println(provider.getConversationForTweet(test).getTweetId());
            tx.success();

        }
    }





}
