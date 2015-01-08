package db;

import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.util.StringLogger;
import scala.collection.Iterator;
import twitter.Tweet;


import java.io.File;
import java.io.IOException;
import java.util.*;

public class Provider {
    GraphDatabaseService graphDb;
    private static enum RelTypes implements RelationshipType
    {
        REPLY_TO
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

    public boolean createTweet(Tweet tweet){
        GraphDatabaseService db = getDatabase();
        try ( Transaction tx = db.beginTx(); )
        {
            Node tweetNode = db.createNode();
            tweetNode.setProperty( "text", tweet.getText() );
            tweetNode.setProperty( "userId", tweet.getUserId());
            tweetNode.setProperty( "tweetId", tweet.getTweetId());
            if(tweet.getInReplyToStatusId() != null && tweet.getInReplyToStatusId().length() > 0){
                Node replyToNode = getTweet(tweet.getInReplyToStatusId());
                if(replyToNode != null) {
                    tweetNode.createRelationshipTo(replyToNode, RelTypes.REPLY_TO);
                }
            }
            Label tweetLabel = DynamicLabel.label("Tweet");
            tweetNode.addLabel(tweetLabel);
            tx.success();
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

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
        try ( Transaction tx = db.beginTx(); ) {
            for (Relationship relationship : node.getRelationships(RelTypes.REPLY_TO, Direction.INCOMING)) {
                replies.add(relationship.getStartNode());
            }
        }
        return replies;
    }


/*
    public static void main(String[] args){
        Provider provider = new Provider();
        GraphDatabaseService db = provider.getDatabase();
        System.out.println(provider.createTweet(
                new Tweet()
                .setTweetId("1")
                .setText("Tweeet tweeeeet")
                .setUserId("1")
        ));

        System.out.println(provider.createTweet(
                new Tweet()
                        .setTweetId("2")
                        .setText("Twooot tweeeeet")
                        .setUserId("1")
        ));

        System.out.println(provider.createTweet(
                new Tweet()
                        .setTweetId("3")
                        .setText("Tweeet tweeeeet")
                        .setUserId("2")
                        .setInReplyToStatusId("1")
        ));

        Node tweet = provider.getTweet("1");
        try(Transaction tx = db.beginTx()){
            for (String propertyKey : tweet.getPropertyKeys()) {
                System.out.println("\t" + propertyKey + " : " + tweet.getProperty(propertyKey));
            }
            tx.success();
        }
        System.out.println(provider.getReplies(provider.getTweet("1")).size());
    }
*/




}
