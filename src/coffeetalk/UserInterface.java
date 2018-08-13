package coffeetalk;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;

public class UserInterface  {
	
	JFrame window;
	DataStore data;
	JPanel content;
	JPanel header;
	JPanel footer;
	Theme theme;
	
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
		window.setLayout(new GridLayout(2, 1));
		
		JButton submit = new JButton("Start");
		JPanel input = new JPanel();
		input.setLayout(new GridLayout(1, 2));
		input.add(new JLabel("Username"));
		JTextField name = new JTextField();
		input.add(name);
		
		submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if (! name.getText().equals("")) {
            		data.username = name.getText();
            		
            		//Add window stuff
            		window.getContentPane().removeAll();
            		window.setLayout(new BorderLayout());
            		window.add(content, BorderLayout.CENTER);
            		window.add(header, BorderLayout.NORTH);
            		window.add(footer, BorderLayout.SOUTH);
            		
            		//Add header and footer
            		header.setLayout(new GridLayout(1, 2));
            		footer.setLayout(new BorderLayout());
            		
            		//Add header buttons
            		JButton encoder = new JButton("Encoder");
            		JButton messenger = new JButton("Messenger");
            		header.add(encoder);
            		header.add(messenger);
            		
            		//Make buttons actually do stuff
            		encoder.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							startPage(Page.Encoder);
						}
            		});
            		messenger.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							startPage(Page.Messenger);
						}
            		});
            		
            		//Add footer buttons
            		JButton settings = null;
            		try {
						settings = new JButton(new ImageIcon(ImageIO.read(new File("data/settings.png")).getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
					} catch (IOException ex) {
						//Image icon not found?
					}
            		footer.add(settings, BorderLayout.WEST);
            		footer.add(new JLabel(), BorderLayout.CENTER);//Add buffer so button isn't too wide
            		
            		//Make button actually do stuff
            		settings.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							startPage(Page.Settings);
						}
            		});
            		
            		//Read themes and set default
            		ObjectInputStream inputFile;
            		try {
            			inputFile = new ObjectInputStream(new FileInputStream("data/data.bin"));
						data = (DataStore) inputFile.readObject();
					} catch (ClassNotFoundException | IOException e1) {
						//File not found; Just ignore.
					}
            		data.themes = MathUtilities.getThemes();
            		if (data.currentTheme == -1) {
            			theme = new Theme();
            		}
            		else {
            			try {
            				theme = data.themes[data.currentTheme];
            			}
            			catch (ArrayIndexOutOfBoundsException e2) {
            				data.currentTheme = -1;
            				theme = new Theme();
            			}
            		}
            		
            		//Makes sure data is stored on app exit
            		window.addWindowStateListener(new WindowAdapter() {
            	        public void windowClosing(WindowEvent e) {
            	        	ObjectOutputStream outputFile;
							try {
								outputFile = new ObjectOutputStream(new FileOutputStream("data/data.bin"));
								outputFile.writeObject(data);
	            	        	outputFile.close();
							} catch(IOException e1) {
								System.exit(0);
							}
            	        }

            	    });
            		
            		//Start Page
            		window.setSize(450, 650);
            		window.setResizable(false);
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
			
			//Add basic outline thing
			content.setLayout(new GridLayout(3, 1));
			JPanel input = new JPanel();
			JPanel settings = new JPanel();
			JPanel output = new JPanel();
			
			//Add input fields
			input.setLayout(new BorderLayout());
			input.add(new JLabel("Message Input"), BorderLayout.SOUTH);
	        JTextField message = new JTextField();
	        input.add(message, BorderLayout.CENTER);
	        
	        //Add settings
	        
	        //Add output
			
	        content.add(input);
	        content.add(settings);
	        content.add(output);
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
