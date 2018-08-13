package coffeetalk;

public class ChatMessage implements java.io.Serializable {
    
    private String message;
    private String sender;
    
    public ChatMessage(String s, String m) {
        sender = s;
        message = m;
    }
    
    public String toString() {
        return sender + ": " + message;
    }
    
    public String getMessage() {
        return message;
    }
    
    public String getSender() {
        return sender;
    }
    