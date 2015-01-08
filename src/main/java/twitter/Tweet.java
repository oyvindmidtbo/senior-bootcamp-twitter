package twitter;

public class Tweet {
    
    private String id;
    private String text;
    private String inReplyToScreenName;
    private String inReplyToStatusId;
    private String inReplyToStatusIdStr;
    private String inReplyToUserId;
    private String inReplyToUserIdStr;


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

    public String getId() {
        return id;
    }

    public Tweet setId(String id) {
        this.id = id;
        return this;
    }
}
