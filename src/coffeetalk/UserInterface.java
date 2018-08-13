package coffeetalk;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

public class UserInterface {
	
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
	
	
	public void startPage() {
		
		
		JButton startButton;
		startButton = new JButton("Start");
		
		
		
		
	}
	
	
	
	
}
