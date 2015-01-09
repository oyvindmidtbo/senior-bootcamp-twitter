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
    
    protected static LocalDateTime formatCreatedAt(String createdAt) {
        // TODO: Fikse formatering fra "Thu Jan 08 13:46:39 +0000 2015" til en LocalDateTime
        // TODO: Dette er nemlig KRISEKODE!!!
        
        int year = Integer.parseInt(createdAt.substring(26, 30));
        int month = getMonth(createdAt.substring(4, 7));
        int day = Integer.parseInt(createdAt.substring(8, 10));
        
        int hour = Integer.parseInt(createdAt.substring(11, 13));
        int minute = Integer.parseInt(createdAt.substring(14, 16));
        int second = Integer.parseInt(createdAt.substring(17, 19));
        
        return LocalDateTime.of(year, month, day, hour, minute, second);
    }
    
    private static int getMonth(String month) {
        switch (month) {
            case "Jan":
                return 1;
            case "Feb":
                return 2;
            case "Mar":
                return 3;
            case "Apr":
                return 4;
            case "May":
                return 5;
            case "Jun":
                return 6;
            case "Jul":
                return 7;
            case "Aug":
                return 8;
            case "Sep":
                return 9;
            case "Oct":
                return 10;
            case "Nov":
                return 11;
            case "Des":
                return 12;
            default:
                return 1;
        }
        
    }
}
