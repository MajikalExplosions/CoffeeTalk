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
	
	public void InitialiseWindow() {
		
		//Create JFrame
		window = new JFrame("CoffeeTalk");
		
		//I don't like background programs.
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        startPage(Page.Start);
	}
	
	public void startPage(Page p) {
		
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
		case Start:
			//Start Page
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
	            		startPage(Page.Encoder);
	            	}
	            }
	        });
			
			window.add(input);
			window.add(submit);
			break;
		default:
			//the heck why's it here
			break;
		}
		window.setVisible(true);
	}
	
	public void loadPage() {
		
	}
}
