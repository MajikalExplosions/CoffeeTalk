package coffeetalk;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInterface  {
	
	JFrame window;
	
	public void InitialiseWindow() {
		
		//Create JFrame
		window = new JFrame("CoffeeTalk");
		
		//I don't like background programs.
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Sets Window Size
        window.setSize(450, 650);
        
        startPage();
	}
	
	public void startPage() {
		
		//Init stuff
		JButton startButton;
		startButton = new JButton("Start");
		
		window.add(startButton);
		
		//makes startbutton actually appear. W O A H
		startButton.setVisible(true);
		
		//Button trigger
		startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//Terminates everything on window, and restores it to blank state
            	window.removeAll();
            	window.setVisible(true);
                window.setSize(450, 650);
            	offlinePage();
            	
            }
        });		
		
	}
	
	public void offlinePage() {
		
		
		
	}
	
	
}
