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

public class CommPanel extends JPanel {
    
    private ChatMessage[] chat;
    private JTextArea history;
    private JTextField input;
    private Socket socket;
    /**
     * @param h history panel
     * @param i input panel
     * @param s socket with server connection
     * @param n name of user
     */
    public CommPanel(JTextArea h, JTextField i, Socket s, String n) {
        //Start by setting text fields to what they need to be
        input.setEditable(true);
        
        input.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addChatMessage(new ChatMessage(n, input.getText()));
                input.setText("");
            }
        });
        
        history.setEditable(false);
        history.setLineWrap(true);
        history.setWrapStyleWord(true);
        
        JScrollPane scroll = new JScrollPane(history);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setAutoscrolls(true);
    }
    
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
        updateChatText();
    }
    
    public void addChatMessage(ChatMessage cm) {
        displayChatMessage(cm);
        sendChatMessage(cm);
    }
    
    public void updateChatText() {
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
    /*
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
        updateChatText();
    }
    
    public void addChatMessage(ChatMessage cm) {
        displayChatMessage(cm);
        sendChatMessage(cm);
    }
    
    public void updateChatText() {
        inputBox.setText("");
        chatBox.setText("");
        for (ChatMessage cm : chat) {
            chatBox.setText(chatBox.getText() + cm.toString() + "\n");
        }
    }
    
    public int getChatLength() {
        if (chat == null) {
            return 0;
        }
        return chat.length;
    }
    
    public ChatMessage getChatMessage(int index) {
        return chat[index];
    }
    */
    private class ChatReceiver extends Thread {
        
        public ChatReceiver() {
            super();
        }
        
        public void run() {
            ObjectInputStream input;
            while(true) {
                try {
                    input = new ObjectInputStream(socket.getInputStream());
                    boolean messageReceived = false;
                    
                    System.out.println("Listening for message.");
                    ChatMessage inc = (ChatMessage) input.readObject();
                    System.out.println("Received: " + inc.toString());
                    if (! inc.getSender().equals("[Game]")) {
                        displayChatMessage(inc);
                        continue;
                    }
                }
                catch(IOException e) {
                    System.out.println("Error receiving message:" + e.getMessage());
                }
                catch(ClassNotFoundException e) {
                    System.out.println("Wait what happened?");              
                }
                catch(NullPointerException e) {
                    //This gets run a lot idk
                }
            }
            
        }
        
    }
    
    public void setSocket(Socket s) {
        socket = s;
    }
    
    private void waitForComm() {
        ChatReceiver cr = new ChatReceiver();
        cr.start();
    }
}
