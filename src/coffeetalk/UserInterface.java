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
        
        //Set JFrame Size and Layout(CHANGE THESE, THEY EXAMPLES)
        //window.setLayout(new java.awt.GridLayout(4, 2));  
        
        //Set JFrame Size
        window.setSize(450, 650);
        
        startPage();
        
      //Show Window
        window.setVisible(true);
     
	}
	
	
//Method Divider cuz oRgAnIsAtIoN	
	
	public void startPage() {
		
		//Init stuff
		JButton startButton;
		startButton = new JButton("Start");
		
		//Button trigger
		startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            	
            	//Goes to the encoder/decoder only page (not messenger)
            	offlinePage();
            	
            }
        });		
		
	}
	
//Method Divider cuz oRgAnIsAtIoN	
	
	
	public void offlinePage() {
		
		
	}
	
	
}
