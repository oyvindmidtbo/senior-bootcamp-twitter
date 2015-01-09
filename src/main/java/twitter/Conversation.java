package twitter;


public class Conversation {

    int conversationSize;
    String tweetId;
    String userId;
    String text;

    public Conversation(int size, String msgId, String user, String msg){
        conversationSize = size;
        tweetId = msgId;
        userId = user;
        String text = msg;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getConversationSize() {
        return conversationSize;
    }

    public void setConversationSize(int conversationSize) {
        conversationSize = conversationSize;
    }

    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
