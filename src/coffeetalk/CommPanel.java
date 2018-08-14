package coffeetalk;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 * 
 * @author MajikalExplosions
 */
public class CommPanel {
    
    private Chat chat;
    private JTextArea history;
    private JTextField input;
    private Socket socket;
    private String name;
    
    public CommPanel(String n) {
        //Add names and things
        name = n;
        
        //Attempt to connect to the server.
        CommInitializer initComm = new CommInitializer();
        initComm.start();
        chat = new Chat();
    }
    
    public JScrollPane setHistoryPanel(JTextArea h) {
    	history = h;
    	history.setEditable(false);
        history.setLineWrap(true);
        history.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(history);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setAutoscrolls(true);
        return scroll;
    }
    
    public void setInputPanel(JTextField i) {
    	input = i;
    	input.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	submitChatMessage(input.getText());
            }
        });
        input.setEditable(true);
    }
    
    public void submitChatMessage(String text) {
    	chat.addChatMessage(new ChatMessage(name, text));
    	input.setText("");
    }
    
    public void manualRefresh() {
    	chat.updateChatHistory();
    }
    
    
    private class Chat {
    	
    	private ChatMessage[] chat2;
    	
    	public Chat() {
    		chat2 = new ChatMessage[0];
    	}
    	
    	public void displayChatMessage(ChatMessage cm) {
            if(cm.getMessage().equals("")) {
                return;
            }
            ChatMessage[] temp = new ChatMessage[chat2.length + 1];
            for (int i = 0; i < chat2.length; i++) {
                temp[i] = chat2[i];
            }
            temp[temp.length - 1] = cm;
            chat2 = temp;
            updateChatHistory();
        }
        
        public void addChatMessage(ChatMessage cm) {
            displayChatMessage(cm);
            sendChatMessage(cm);
        }
        
        public void updateChatHistory() {
        	if (input != null && history != null) {
	            input.setText("");
	            history.setText("");
	            for (ChatMessage cm : chat2) {
	                history.setText(history.getText() + cm.toString() + "\n");
	            }
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
            return chat2.length;
        }
        
        public ChatMessage[] getChat() {
        	return chat2;
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
    		int i2 = 1;
    		while (true) {
	            try {	            	
	            	while(chat == null) {
	                	System.out.println("HI I'm Here");
	                }
	            	chat.displayChatMessage(new ChatMessage("[Server]", "Please type in the server's IP Address."));
	            	while(chat.getChatLength() <= i2) {
	                	System.out.println("Waiting for message");
	                }
	                String ip = chat.getChat()[i2].getMessage();
	                ip += ":58541";
	                chat.displayChatMessage(new ChatMessage("[Server]", "Attempting to connect to the server..."));
	                socket = new Socket(ip.substring(0, ip.indexOf(":")), Integer.parseInt(ip.substring(ip.indexOf(":") + 1)));
	                chat.displayChatMessage(new ChatMessage("[Server]", "Connected!"));
	                break;
	            }
	            catch(NumberFormatException | NoRouteToHostException | UnknownHostException e2) {
	            	chat.displayChatMessage(new ChatMessage("[Server]", "IP Invalid."));
	            	i2 += 4;
	            	
	            }
	            catch (Exception e) {
	            	System.out.println("HI I'm HERE");
	            }
    		}
        }
    }
    
    private void startCommReciever() {
        ChatReceiver cr = new ChatReceiver();
        cr.start();
    }
}
