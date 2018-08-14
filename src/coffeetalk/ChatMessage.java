package coffeetalk;
/**
 * Wrapper class for chat messages
 * @author MajikalExplosions
 */
public class ChatMessage implements java.io.Serializable {
    
    private String message;
    private String sender;
    
    public ChatMessage(String s, String m) {
        sender = s;
        message = ": " + m;
    }
    
    public String toString() {
        return sender + message;
    }
    
	public void encrypt(int key) {
		MathUtilities.cipher(message, key, true);
    }
	
	public void decrypt(int key) {
		MathUtilities.cipher(message, key, false);
	}
    
    public String getMessage() {
        return message.substring(2);
    }
    
    public String getSender() {
        return sender;
    }
}