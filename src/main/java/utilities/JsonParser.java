package utilities;

import org.json.JSONException;
import org.json.JSONObject;
import twitter.Tweet;

public class JsonParser {

    private static final String ID = "id";
    private static final String TEXT = "text";
    private static final String IN_REPLY_TO_SCREEN_NAME = "in_reply_to_screen_name";
    private static final String IN_REPLY_TO_STATUS_ID = "in_reply_to_status_id";
    private static final String IN_REPLY_TO_STATUS_ID_STR = "in_reply_to_status_id_str";
    private static final String IN_REPLY_TO_USER_ID = "in_reply_to_user_id";
    private static final String IN_REPLY_TO_USER_ID_STR = "in_reply_to_user_id_str";

    public static Tweet createTweetObjectFromJson(String json) {
        Tweet tweet;
        
        try {
            JSONObject jsonObject = new JSONObject(json);
            
            tweet = new Tweet()
                    .setTweetId(jsonObject.getString(ID))
                    .setText(jsonObject.getString(TEXT))
                    .setInReplyToScreenName(jsonObject.getString(IN_REPLY_TO_SCREEN_NAME))
                    .setInReplyToStatusId(jsonObject.getString(IN_REPLY_TO_STATUS_ID))
                    .setInReplyToStatusIdStr(jsonObject.getString(IN_REPLY_TO_STATUS_ID_STR))
                    .setInReplyToUserId(jsonObject.getString(IN_REPLY_TO_USER_ID))
                    .setInReplyToUserIdStr(jsonObject.getString(IN_REPLY_TO_USER_ID_STR));
            
        } catch (JSONException e) {
            return null;
        }

        return tweet;
    }
}
