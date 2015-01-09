package twitter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect
@JsonNaming(value = PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class Tweet {


    @JsonProperty(value = "id")
    private String tweetId;

    private String text;

    @JsonProperty(value = "in_reply_to_screen_name")
    private String inReplyToScreenName;
    @JsonProperty(value = "in_reply_to_status_id")
    private String inReplyToStatusId;
    @JsonProperty(value = "in_reply_to_status_id_str")
    private String inReplyToStatusIdStr;
    @JsonProperty(value = "in_reply_to_user_id")
    private String inReplyToUserId;
    @JsonProperty(value = "in_reply_to_user_id_str")
    private String inReplyToUserIdStr;

    private User user;

    public String getText() {
        return text;
    }

    public Tweet setText(String text) {
        this.text = text;
        return this;
    }

    public String getInReplyToScreenName() {
        return inReplyToScreenName;
    }

    public Tweet setInReplyToScreenName(String inReplyToScreenName) {
        this.inReplyToScreenName = inReplyToScreenName;
        return this;
    }

    public String getInReplyToStatusId() {
        return inReplyToStatusId;
    }

    public Tweet setInReplyToStatusId(String inReplyToStatusId) {
        this.inReplyToStatusId = inReplyToStatusId;
        return this;
    }

    public String getInReplyToStatusIdStr() {
        return inReplyToStatusIdStr;
    }

    public Tweet setInReplyToStatusIdStr(String inReplyToStatusIdStr) {
        this.inReplyToStatusIdStr = inReplyToStatusIdStr;
        return this;
    }

    public String getInReplyToUserId() {
        return inReplyToUserId;
    }

    public Tweet setInReplyToUserId(String inReplyToUserId) {
        this.inReplyToUserId = inReplyToUserId;
        return this;
    }

    public String getInReplyToUserIdStr() {
        return inReplyToUserIdStr;
    }

    public Tweet setInReplyToUserIdStr(String inReplyToUserIdStr) {
        this.inReplyToUserIdStr = inReplyToUserIdStr;
        return this;
    }

    public String getTweetId() {
        return tweetId;
    }

    public Tweet setTweetId(String tweetId) {
        this.tweetId = tweetId;
        return this;
    }

    public String getUserId() {
        return user.getId();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
