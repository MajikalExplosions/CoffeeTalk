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
        window.setLayout(new java.awt.GridLayout(4, 2));
        
        //Equivalent of setsize, calculates everything based off of objects in window. Yes, that means that you shouldn't use setSize because it WILL mess things up.
        window.pack();
        
      //Show Window
        window.setVisible(true);
	}
	
	
	
	
	
}
