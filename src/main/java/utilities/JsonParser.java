package utilities;

import org.json.JSONException;
import org.json.JSONObject;
import twitter.Tweet;

import java.time.LocalDateTime;

public class JsonParser {

    private static final String TWEET_ID = "id";
    private static final String CREATED_AT = "created_at";
    private static final String USER = "user";
    private static final String USER_ID = "id";
    private static final String TEXT = "text";
    private static final String IN_REPLY_TO_SCREEN_NAME = "in_reply_to_screen_name";
    private static final String IN_REPLY_TO_STATUS_ID = "in_reply_to_status_id";
    private static final String IN_REPLY_TO_STATUS_ID_STR = "in_reply_to_status_id_str";
    private static final String IN_REPLY_TO_USER_ID = "in_reply_to_user_id";
    private static final String IN_REPLY_TO_USER_ID_STR = "in_reply_to_user_id_str";
    private static final String RETWEETED = "retweeted";
    private static final String RETWEET_COUNT = "retweet_count";

    public static Tweet createTweetObjectFromJson(String json) {
        Tweet tweet;
        
        try {
            JSONObject jsonObject = new JSONObject(json);
            
            tweet = new Tweet()
                    .setTweetId(jsonObject.getString(TWEET_ID))
                    .setUserId(jsonObject.getJSONObject(USER).getString(USER_ID))
                    .setCreatedAt(formatCreatedAt(jsonObject.getString(CREATED_AT)))
                    .setText(jsonObject.getString(TEXT))
                    .setInReplyToScreenName(jsonObject.getString(IN_REPLY_TO_SCREEN_NAME))
                    .setInReplyToStatusId(jsonObject.getString(IN_REPLY_TO_STATUS_ID))
                    .setInReplyToStatusIdStr(jsonObject.getString(IN_REPLY_TO_STATUS_ID_STR))
                    .setInReplyToUserId(jsonObject.getString(IN_REPLY_TO_USER_ID))
                    .setInReplyToUserIdStr(jsonObject.getString(IN_REPLY_TO_USER_ID_STR))
                    .setRetweeted(Boolean.valueOf(jsonObject.getString(RETWEETED)))
                    .setRetweetCount(Integer.valueOf(jsonObject.getString(RETWEET_COUNT)));
            
        } catch (JSONException e) {
            return null;
        }

        return tweet;
    }
    
    private static LocalDateTime formatCreatedAt(String createdAt) {
        // TODO: Fikse formatering fra "Thu Jan 08 13:46:39 +0000 2015" til en LocalDateTime
        return null;
    }
}
