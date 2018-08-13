package coffeetalk;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 * 
 * @author MajikalExplosions
 */
public class CommPanel extends JPanel {
    
    private Chat chat;
    private JTextArea history;
    private JTextField input;
    private Socket socket;
    /**
     * @param h history panel
     * @param i input panel
     * @param s socket with server connection
     * @param n name of user
     */
    public CommPanel(JTextArea h, JTextField i, String n) {
        //Start by setting text fields to what they need to be
        input.setEditable(true);
        
        input.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chat.addChatMessage(new ChatMessage(n, input.getText()));
                input.setText("");
            }
        });
        
        //Make history like chat history
        history.setEditable(false);
        history.setLineWrap(true);
        history.setWrapStyleWord(true);
        
        //Add scrolling bar at the side
        JScrollPane scroll = new JScrollPane(history);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setAutoscrolls(true);
        
        //Attempt to connect to the server.
        CommInitializer initComm = new CommInitializer();
        initComm.start();
        chat.displayChatMessage(new ChatMessage("[Server]", "Please type in the server's IP Address."));
        
    }
    
    private class Chat {
    	
    	private ChatMessage[] chat;
    	
    	public void displayChatMessage(ChatMessage cm) {
            if(cm.getMessage().equals("")) {
                return;
            }
            ChatMessage[] temp = new ChatMessage[chat.length + 1];
            for (int i = 0; i < chat.length; i++) {
                temp[i] = chat[i];
            }
            temp[temp.length - 1] = cm;
            chat = temp;
            updateChatHistory();
        }
        
        public void addChatMessage(ChatMessage cm) {
            displayChatMessage(cm);
            sendChatMessage(cm);
        }
        
        public void updateChatHistory() {
            input.setText("");
            history.setText("");
            for (ChatMessage cm : chat) {
                history.setText(history.getText() + cm.toString() + "\n");
            }
        }
        
        public boolean sendChatMessage(ChatMessage cm) {
            try {
                ObjectOutputStream output;
                output = new ObjectOutputStream(socket.getOutputStream());
                output.writeObject(cm);
            }
            catch(Exception e) {
                return false;
            }
            return true;
        }
        
        public int getChatLength() {
            if (chat == null) {
                return 0;
            }
            return chat.length;
        }
        
        public ChatMessage[] getChat() {
        	return chat;
        }
    }
    
    private class ChatReceiver extends Thread {
        
        public ChatReceiver() {
            super();
        }
        
        public void run() {
            ObjectInputStream input;
            while(true) {
                try {
                    input = new ObjectInputStream(socket.getInputStream());
                    ChatMessage inc = (ChatMessage) input.readObject();
                    chat.displayChatMessage(inc);
                }
                catch(Exception e) {
                	continue;
                }
            }
            
        }
        
    }
    
    private class CommInitializer extends Thread {
    	
    	public void run() {
            try {
                while(chat.getChatLength() == 1) {
                    System.out.print("");
                }
                String ip = chat.getChat()[1].getMessage();
                ip += ":58541";
                socket = new Socket(ip.substring(0, ip.indexOf(":")), Integer.parseInt(ip.substring(ip.indexOf(":") + 1)));
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
        }
    }
    
    private void startCommReciever() {
        ChatReceiver cr = new ChatReceiver();
        cr.start();
    }
}
