package coffeetalk;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInterface  {
	
	JFrame window;
	DataStore data;
	JPanel content;
	JPanel header;
	JPanel footer;
	
	public void InitialiseWindow() {
		
		data = new DataStore();
		
		//Create JFrame
		window = new JFrame("CoffeeTalk");
		content = new JPanel();
		header = new JPanel();
		footer = new JPanel();
		
		//I don't like background programs.
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        window.setSize(200, 100);
		window.setLayout(new java.awt.GridLayout(2, 1));
		
		JButton submit = new JButton("Start");
		JPanel input = new JPanel();
		input.setLayout(new java.awt.GridLayout(1, 2));
		input.add(new JLabel("Username"));
		JTextField name = new JTextField();
		input.add(name);
		
		submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if (! name.getText().equals("")) {
            		data.username = name.getText();
            		
            		//Add stuff
            		window.getContentPane().removeAll();
            		window.setLayout(new java.awt.BorderLayout());
            		window.add(content, java.awt.BorderLayout.CENTER);
            		window.add(header, java.awt.BorderLayout.NORTH);
            		window.add(footer, java.awt.BorderLayout.SOUTH);
            		
            		startPage(Page.Encoder);
            	}
            }
        });
		
		window.add(input);
		window.add(submit);
		window.setVisible(true);
	}
	
	public void startPage(Page p) {
		content.removeAll();
		
		switch(p) {
		case Encoder:
			//Encoder Page
			break;
		case Messenger:
			//Messenger Page
			break;
		case Settings:
			//Settings Page
			break;
		default:
			//the heck why's it here
			break;
		}
		window.validate();
		window.repaint();
		window.setVisible(true);
	}
	

}
