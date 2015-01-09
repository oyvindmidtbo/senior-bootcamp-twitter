package twitter;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.event.Event;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import db.Provider;
import org.java_websocket.WebSocketImpl;
import org.neo4j.graphdb.Transaction;
import server.TwitterHub;
import utilities.JsonParser;

import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TwitterClient {

    public static void main(String[] args) throws UnknownHostException {
        BlockingQueue<String> msgQueue = new LinkedBlockingQueue<>(100000);
        Provider db = new Provider();
        ClientBuilder builder = createClientBuilder(msgQueue);
        Client hosebirdClient = builder.build();

        hosebirdClient.connect();

        WebSocketImpl.DEBUG = true;
        int port = 8887; // 843 flash policy port

        try {
            port = Integer.parseInt(args[0]);
        } catch (Exception ignored) {
        }

        TwitterHub hub = new TwitterHub(port);
        hub.start();
        System.out.println("TwitterHub started on port: " + hub.getPort());

        while (!hosebirdClient.isDone()) {
            String msg = null;
            try {
                msg = msgQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Tweet tweet = JsonParser.createTweetObjectFromJson(msg);
            try ( Transaction tx = db.getDatabase().beginTx(); ) {
                if (tweet != null) {

                        db.createTweet(tweet);

                    if (tweet.getInReplyToStatusId() != null && !tweet.getInReplyToStatusId().equals("null")) {
                        System.out.println((tweet.getText()));
                        hub.sendToAll(tweet.getText());
                    }
                }
                tx.success();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private static ClientBuilder createClientBuilder(BlockingQueue<String> msgQueue) {
        BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<>(1000);

        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();

        List<Long> followings = Lists.newArrayList(1234L, 566788L);
        List<String> terms = Lists.newArrayList("twitter", "api");
        hosebirdEndpoint.followings(followings);
        hosebirdEndpoint.trackTerms(terms);

        Authentication hosebirdAuth = new OAuth1(TwitterAuthentication.getConsumerKey(), TwitterAuthentication.getConsumerSecret(),
                TwitterAuthentication.getAccessToken(), TwitterAuthentication.getAccessTokenSecret());

        return new ClientBuilder()
                .name("Hosebird-Client-01")                              // optional: mainly for the logs
                .hosts(hosebirdHosts)
                .authentication(hosebirdAuth)
                .endpoint(hosebirdEndpoint)
                .processor(new StringDelimitedProcessor(msgQueue))
                .eventMessageQueue(eventQueue);

    }
}
