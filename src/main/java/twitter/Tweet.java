package twitter;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tweet {


    @JsonProperty(value = "id")
    private String tweetId;

    private Date createdAt;
    private String text;

    private String inReplyToScreenName;
    private String inReplyToStatusId;
    private String inReplyToStatusIdStr;
    private String inReplyToUserId;
    private String inReplyToUserIdStr;
    private boolean retweeted;
    private int retweetCount;
    
    private Tweet retweetedStatus;

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
        if (user == null)
            return "-1";

        return user.getId();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Tweet setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public boolean isRetweeted() {
        return retweeted;
    }

    public Tweet setRetweeted(boolean retweeted) {
        this.retweeted = retweeted;
        return this;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public Tweet setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
        return this;
    }

    public Tweet getRetweetedStatus() {
        return retweetedStatus;
    }

    public Tweet setRetweetedStatus(Tweet retweetedStatus) {
        this.retweetedStatus = retweetedStatus;
        return this;
    }
}
