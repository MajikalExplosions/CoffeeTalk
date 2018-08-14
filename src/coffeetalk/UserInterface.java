package coffeetalk;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;

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
			JTextField outputField = new JTextField();
			
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
	        settings.setLayout(new GridLayout(1, 2));
	        JPanel keyInput = new JPanel();
	        JPanel optionsMenu = new JPanel();
	        settings.add(keyInput);
	        keyInput.setLayout(new GridLayout(2, 1));
	        JTextField keyForm = new JTextField();
	        String[] options = {"Custom", "Preset A", "Preset B", "Preset C", "Preset D", "Preset E"};
	        JComboBox dropdown = new JComboBox(options);
	        dropdown.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					@SuppressWarnings("rawtypes")
					JComboBox cb = (JComboBox) e.getSource();
			        if (((String) cb.getSelectedItem()).equals("Custom")) {
			        	keyForm.setEnabled(true);
			        }
			        else {
			        	keyForm.setEnabled(false);
			        }
				}
    		});
	        keyInput.add(dropdown);
	        keyInput.add(keyForm);
	        optionsMenu.setLayout(new BorderLayout());
	        ButtonGroup bg = new ButtonGroup();
	        JRadioButton encrypt = new JRadioButton("Encrypt");
	        JRadioButton decrypt = new JRadioButton("Decrypt");
	        optionsMenu.add(encrypt, BorderLayout.WEST);
	        optionsMenu.add(decrypt, BorderLayout.EAST);
	        bg.add(encrypt);
	        bg.add(decrypt);
	        JButton submitEncryption = new JButton("Submit");	        
	        submitEncryption.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						if (! (message.getText().equals("") || keyForm.getText().equals(""))) {
							outputField.setText(MathUtilities.cipher(message.getText(), Integer.parseInt(keyForm.getText().toString()), encrypt.isSelected()));
						}
					}
					catch(NumberFormatException e2) {
						System.out.println(e2.getMessage());
						//Do nothing
					}
				}
    		});
	        optionsMenu.add(submitEncryption, BorderLayout.SOUTH);
	        settings.add(optionsMenu);
	        
	        //Add output
	        output.setLayout(new BorderLayout());
			output.add(new JLabel("Message Output"), BorderLayout.SOUTH);
	        output.add(outputField, BorderLayout.CENTER);
	        outputField.setEditable(false);
	        
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
