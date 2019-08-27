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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
	CommPanel comm;
	/**
	 * No docs available. Even I don't know how it works anymore.
	 */
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
            		comm = new CommPanel(data.username);
            		
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
						settings = new JButton(new ImageIcon(ImageIO.read(new File("data/settings2.png")).getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
					} catch (IOException ex) {
						//Image icon not found?
						
						System.out.println("Image not found");
						System.out.println(" ");
						
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
		
		/*
		 * ****************IMPORTANT*****************
		 * 
	      JJJJJJJJJJJ               AAA                       CCCCCCCCCCCCC		KKKKKKKKK    KKKKKKK   !!! 	 !!! 
	      J:::::::::J              A:::A                   CCC::::::::::::C		K:::::::K    K:::::K  !!:!!	!!:!!
	      J:::::::::J             A:::::A                CC:::::::::::::::C		K:::::::K    K:::::K  !:::!	!:::!
	      JJ:::::::JJ            A:::::::A              C:::::CCCCCCCC::::C		K:::::::K   K::::::K  !:::!	!:::!
	        J:::::J             A:::::::::A            C:::::C       CCCCCC		KK::::::K  K:::::KKK  !:::!	!:::!
	        J:::::J            A:::::A:::::A          C:::::C                		K:::::K K:::::K   !:::!	!:::!
	        J:::::J           A:::::A A:::::A         C:::::C                		K::::::K:::::K    !:::!	!:::!
	        J:::::j          A:::::A   A:::::A        C:::::C                		K:::::::::::K     !:::!	!:::!
	        J:::::J         A:::::A     A:::::A       C:::::C                		K:::::::::::K     !:::!	!:::!
JJJJJJJ     J:::::J        A:::::AAAAAAAAA:::::A      C:::::C                		K::::::K:::::K    !:::!	!:::!
J:::::J     J:::::J       A:::::::::::::::::::::A     C:::::C                		K:::::K K:::::K   !!:!!	!!:!!
J::::::J   J::::::J      A:::::AAAAAAAAAAAAA:::::A     C:::::C       CCCCCC		KK::::::K  K:::::KKK   !!! 	 !!! 
J:::::::JJJ:::::::J     A:::::A             A:::::A     C:::::CCCCCCCC::::C		K:::::::K   K::::::K       	   
 JJ:::::::::::::JJ     A:::::A               A:::::A     CC:::::::::::::::C		K:::::::K    K:::::K   !!! 	 !!! 
   JJ:::::::::JJ      A:::::A                 A:::::A      CCC::::::::::::C		K:::::::K    K:::::K  !!:!!	!!:!!
     JJJJJJJJJ       AAAAAAA                   AAAAAAA        CCCCCCCCCCCCC		KKKKKKKKK    KKKKKKK   !!! 	 !!!
		 * 
		 * ******************************************
		 * 
		 * Okay so basically to set the color of the buttons, use the following:
		 * <object>.setBackground(theme.getPrimary());
		 * <object>.setBackground(theme.getSecondary());
		 * <object>.setBackground(theme.getAccent());
		 * 
		 * And another note: If there is another component inside one(EX a JLabel in a JPanel)
		 * and you set the outer one's background(In the example it's the JPanel), it WILL NOT show.
		 * That is because the one on top(EX the JLabel) covers it up.
		 */
		
		content.removeAll();//Removes everything in the center panel
		
		switch(p) {
		case Encoder:
			//Encoder Page
			JTextArea outputField = new JTextArea();

			
			//Add basic outline thing
			content.setLayout(new GridLayout(3, 1));//3 vertical boxes
			JPanel input = new JPanel();//Top panel with input
			
			
			
			
			JPanel settings = new JPanel();//Middle panel with settings and things
			JPanel output = new JPanel();//Bottom panel with output
			
			
			//Add input fields
			input.setLayout(new BorderLayout());//We discussed this right
			
			
			//Message Input Label
			JLabel messageInputLabel = new JLabel("Message Input");
			input.add(messageInputLabel, BorderLayout.SOUTH);
			
			
			
			
			
			
			
			//input.add(new JLabel("Message Input"), BorderLayout.SOUTH);//Label at the bottom that says that this is input
	        JTextArea message = new JTextArea();//Place to type message **IMPORTANT**
	        
	        message.setLineWrap(true);
		    
		    
	        /*
		       JScrollPane messageInputScroll = new JScrollPane (message);
		          messageInputScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		          messageInputScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		          messageInputScroll.setAutoscrolls(true);
		    */
		  
		       
	        input.add(message, BorderLayout.CENTER);

	        
	        
	        //Add settings
	        settings.setLayout(new GridLayout(1, 2));//Two horizontal boxes
	        JPanel keyInput = new JPanel();//This is for the dropdown and text input for the key
	        JPanel optionsMenu = new JPanel();//This is like the "Go" and "Encrypt/Decrypt" buttons
	        settings.add(keyInput);
	        keyInput.setLayout(new GridLayout(2, 1));
	        JTextField keyForm = new JTextField();//Text field for custom keys
	        String[] options = {"Custom", "Preset A", "Preset B", "Preset C", "Preset D", "Preset E"};//I'll fix this later
	        JComboBox dropdown = new JComboBox(options);
	        dropdown.addActionListener(new ActionListener() {//Makes stuff happen when you change the option
				public void actionPerformed(ActionEvent e) {
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
	        ButtonGroup bg = new ButtonGroup();//Otherwise the two radio buttons won't toggle each other on/off
	        JRadioButton encrypt = new JRadioButton("Encrypt");
	        JRadioButton decrypt = new JRadioButton("Decrypt");
	        optionsMenu.add(encrypt, BorderLayout.WEST);
	        optionsMenu.add(decrypt, BorderLayout.EAST);
	        encrypt.setSelected(true);
	        bg.add(encrypt);
	        bg.add(decrypt);
	        JButton submitEncryption = new JButton("Submit");
	        submitEncryption.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						if (! (message.getText().equals(""))) {//If message isn't blank
							if (dropdown.getSelectedIndex() == 0) {//If custom is selected
								if (! keyForm.getText().equals("")) {//If key isn't blank then encrypt
									outputField.setText(MathUtilities.cipher(message.getText(), Integer.parseInt(keyForm.getText().toString()), encrypt.isSelected()));
								}
							}
							else {//If preset is selected use preset
								outputField.setText(MathUtilities.cipher(message.getText(), data.presetKeys[dropdown.getSelectedIndex() - 1], encrypt.isSelected()));
							}
						}
					}
					catch(NumberFormatException e2) {
						System.out.println(e2.getMessage());
						//Do nothing
					}
				}
    		});
	        optionsMenu.add(submitEncryption, BorderLayout.SOUTH);//Add everything
	        settings.add(optionsMenu);
	        
	        //Add output
	        output.setLayout(new BorderLayout());//This is basically a repeat of the top section
			//output.add(new JLabel("Message Output"), BorderLayout.SOUTH);
	        output.add(outputField, BorderLayout.CENTER);
	        outputField.setEditable(false);//No editing output field
	        
	        
	        
	        //Add output label
	        JLabel messageOutputLabel = new JLabel("Message Output"); 
	     
	        output.add(messageOutputLabel, BorderLayout.SOUTH);
	        
	        
	        
	        //Add output scrolling
	        outputField.setLineWrap(true);
    
	        
	        /*
		    	JScrollPane messageOutputScroll = new JScrollPane (outputField);
		       		messageOutputScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		       		messageOutputScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		       		messageOutputScroll.setAutoscrolls(true);
	        
	        */
	        
	        
	        
	        
	        content.add(input);//Add fields to content
	        content.add(settings);
	        content.add(output);
	        
	       
	        
	        
        	if (data.currentTheme != -1) {
	        	//MARKER_1 OfflinePanel
	        	content.setBackground(theme.getPrimary());
	        	content.setForeground(theme.getAccent());
	        	content.setOpaque(true);
	        	
	        	input.setBackground(theme.getPrimary());
	        	input.setForeground(theme.getAccent());
	        	input.setOpaque(true);
	        	
	        	settings.setBackground(theme.getPrimary());
	        	settings.setForeground(theme.getAccent());
	        	settings.setOpaque(true);
	        	
	        	output.setBackground(theme.getPrimary());
	        	output.setForeground(theme.getAccent());
	        	output.setOpaque(true);
	        	
	        	messageInputLabel.setBackground(theme.getSecondary());
	        	messageInputLabel.setForeground(theme.getAccent());
	        	messageInputLabel.setOpaque(true);
	        	
	        	message.setBackground(theme.getPrimary());
	        	message.setForeground(theme.getAccent());
	        	message.setOpaque(true);
	        	
	        	keyInput.setBackground(theme.getSecondary());
	        	keyInput.setForeground(theme.getAccent());
	        	keyInput.setOpaque(true);
	        	
	        	optionsMenu.setBackground(theme.getSecondary());
	        	optionsMenu.setForeground(theme.getAccent());
	        	optionsMenu.setOpaque(true);
	        	
	        	keyForm.setBackground(theme.getPrimary());
	        	keyForm.setForeground(theme.getAccent());
	        	keyForm.setOpaque(true);
	        	
	        	dropdown.setBackground(theme.getSecondary());
	        	dropdown.setForeground(theme.getAccent());
	        	dropdown.setOpaque(true);
	        	
	        	encrypt.setBackground(theme.getSecondary());
	        	encrypt.setForeground(theme.getAccent());
	        	encrypt.setOpaque(true);
	        	
	        	decrypt.setBackground(theme.getSecondary());
	        	decrypt.setForeground(theme.getAccent());
	        	decrypt.setOpaque(true);
	        	
	        	submitEncryption.setBackground(theme.getPrimary());
	        	submitEncryption.setForeground(theme.getAccent());
	        	submitEncryption.setBorderPainted(false);
	        	submitEncryption.setOpaque(true);
	        	
	        	messageOutputLabel.setBackground(theme.getSecondary());
	        	messageOutputLabel.setForeground(theme.getAccent());
	        	messageOutputLabel.setOpaque(true);	        	
	        	
	        	outputField.setBackground(theme.getPrimary());
	        	outputField.setForeground(theme.getAccent());
	        	outputField.setOpaque(true);
	        	
	        	
	        	header.setBackground(theme.getSecondary());
	        	header.setForeground(theme.getAccent());
	        	header.setOpaque(true);
	        	
	        	footer.setBackground(theme.getSecondary());
	        	footer.setForeground(theme.getAccent());
	        	footer.setOpaque(true);
	        }
			break;
		case Messenger:
			//Messenger Page
			content.setLayout(new BorderLayout());
			
			//Define 3 sections
			JPanel optionsBar = new JPanel();
			JTextArea history = new JTextArea();
			JPanel inputBar = new JPanel();
			
			JScrollPane historyContainer;//History needs to be scrollable
			historyContainer = comm.setHistoryPanel(history);
			
			optionsBar.setLayout(new BorderLayout());
			inputBar.setLayout(new BorderLayout());
			
			//Add message button and field
			JTextField messageInput = new JTextField();
			comm.setInputPanel(messageInput);
			JButton textSubmit = new JButton("→");
			inputBar.add(messageInput, BorderLayout.CENTER);
			inputBar.add(textSubmit, BorderLayout.EAST);
			
			//These variables are used below but needs to be defined up here for access
			JTextField m_keyInput = new JTextField();//Text field for custom keys
			String[] m_options = {"Custom", "Preset A", "Preset B", "Preset C", "Preset D", "Preset E"};//I'll fix this later
	        JComboBox m_dropdown = new JComboBox(m_options);
	        comm.setDropdownAndInput(m_dropdown, m_keyInput);
			
			//Add stuff that happens on submit
			textSubmit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					comm.submitChatMessage(messageInput.getText());
				}
			});
			
	        m_dropdown.addActionListener(new ActionListener() {//Makes stuff happen when you change the option
				public void actionPerformed(ActionEvent e) {
			        if (((String) m_dropdown.getSelectedItem()).equals("Custom")) {
			        	m_keyInput.setEnabled(true);
			        }
			        else {
			        	m_keyInput.setEnabled(false);
			        }
				}
    		});
	        optionsBar.add(m_keyInput, BorderLayout.CENTER);
	        optionsBar.add(m_dropdown, BorderLayout.WEST);
	        
	        content.add(optionsBar, BorderLayout.NORTH);
	        content.add(historyContainer, BorderLayout.CENTER);
	        content.add(inputBar, BorderLayout.SOUTH);
	        
	        comm.manualRefresh();
	        if (data.currentTheme != -1) {
	        	//MARKER_2 Messenger Panel
	        	
	        	content.setBackground(theme.getPrimary());
	        	content.setForeground(theme.getAccent());
	        	content.setOpaque(true);
	        	
	        	header.setBackground(theme.getSecondary());
	        	header.setForeground(theme.getAccent());
	        	header.setOpaque(true);
	        	
	        	footer.setBackground(theme.getSecondary());
	        	footer.setForeground(theme.getAccent());
	        	footer.setOpaque(true);
	        }
			break;
		case Settings:

			content.setLayout(new BorderLayout());
			JPanel themeSettingBar = new JPanel();
			themeSettingBar.setLayout(new BorderLayout());
			content.add(themeSettingBar, BorderLayout.NORTH);
			
			JComboBox s_dropdown = new JComboBox();
			
			for (Theme t : data.themes) {
				s_dropdown.addItem(t.getName());
			}
			
			if (data.currentTheme != -1) {
				s_dropdown.setSelectedIndex(data.currentTheme);
			}
			
			s_dropdown.addActionListener(new ActionListener() {//Makes stuff happen when you change the option
				public void actionPerformed(ActionEvent e) {
			        theme = data.themes[s_dropdown.getSelectedIndex()];
			        data.currentTheme = s_dropdown.getSelectedIndex();
			        startPage(Page.Settings);
				}
    		});
			
			JLabel s_themeLabel = new JLabel("Theme");
			themeSettingBar.add(s_themeLabel, BorderLayout.WEST);
			themeSettingBar.add(s_dropdown, BorderLayout.CENTER);
			content.add(themeSettingBar, BorderLayout.NORTH);
			
			//Settings Page
			
			if (data.currentTheme != -1) {
	        	//MARKER_3 Settings Panel
				
				themeSettingBar.setBackground(theme.getPrimary());
				themeSettingBar.setForeground(theme.getAccent());
				themeSettingBar.setOpaque(true);
				
				s_dropdown.setBackground(theme.getPrimary());
				s_dropdown.setForeground(theme.getAccent());
				s_dropdown.setOpaque(true);
				
				s_themeLabel.setBackground(theme.getPrimary());
				s_themeLabel.setForeground(theme.getAccent());
				s_themeLabel.setOpaque(true);
				
				content.setBackground(theme.getPrimary());
	        	content.setForeground(theme.getAccent());
	        	content.setOpaque(true);
				
				header.setBackground(theme.getSecondary());
	        	header.setForeground(theme.getAccent());
	        	header.setOpaque(true);
	        	
	        	footer.setBackground(theme.getSecondary());
	        	footer.setForeground(theme.getAccent());
	        	footer.setOpaque(true);
	        }
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
