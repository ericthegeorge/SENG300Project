package com.thelocalmarketplace.software.gui;

import javax.swing.*;

import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.logic.CentralStationLogic;

import java.awt.*;

/**
 *
 *
 * @author Alan Yong (30105707)
 * @author Andrew Matti (30182547)
 * @author Olivia Crosby (30099224)
 * @author Rico Manalastas (30164386)
 * @author Shanza Raza (30192765)
 * @author Danny Ly (30127144)
 * @author Maheen Nizmani (30172615)
 * @author Christopher Lo (30113400)
 * @author Michael Svoboda (30039040)
 * @author Sukhnaaz Sidhu (30161587)
 * @author Ian Beler (30174903)
 * @author Gareth Jenkins (30102127)
 * @author Jahnissi Nwakanma (30174827)
 * @author Camila Hernandez (30134911)
 * @author Ananya Jain (30196069)
 * @author Zhenhui Ren (30139966)
 * @author Eric George (30173268)
 * @author Jenny Dang (30153821)
 * @author Tanmay Mishra (30127407)
 * @author Adrian Brisebois (30170764)
 * @author Atique Muhammad (30038650)
 * @author Ryan Korsrud (30173204)
 */


public class KeypadScreenGUI {
	CentralStationLogic logic;
	MainGUI mainGUI;
	
	// Variables for the screen
	JFrame keyboardScreenFrame;
	JPanel keyboardScreenPanel;
	JPanel keyRowOnePanel;
	JPanel keyRowTwoPanel;
	JPanel keyRowThreePanel;
	JPanel keyRowFourPanel;
	JPanel keyRowFivePanel;
	JPanel keyRowSixPanel;
	JPanel textAreaPanel;
	JTabbedPane visualAlphaPanel;
	JTextArea textArea;
	JLabel textBar;
	String searchText;
	
	
	// Constructor for the screen
	public KeypadScreenGUI(MainGUI m, CentralStationLogic l) {
		mainGUI = m;
		logic = l;
		keyboardScreenFrame = new JFrame("Keypad");
		keyboardScreenPanel = new JPanel();
		keyRowOnePanel = new JPanel();
		keyRowTwoPanel = new JPanel();
		keyRowThreePanel = new JPanel();
		keyRowFourPanel = new JPanel();
		keyRowFivePanel = new JPanel();
		keyRowSixPanel = new JPanel();
		textAreaPanel = new JPanel();
		visualAlphaPanel = new JTabbedPane();
		textBar = new JLabel(searchText);
		textArea = new JTextArea("Enter Item Name or code");
		
		searchText = "ENTER PLU CODE";
		
		addWidgets();
		
		// TODO: Build out the rest of the screen. 
		
		keyboardScreenFrame.setSize(new Dimension(500,500));
		keyboardScreenFrame.getContentPane().add(keyboardScreenPanel, BorderLayout.CENTER);
		keyboardScreenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		keyboardScreenFrame.setLocationRelativeTo(null);
		keyboardScreenFrame.setVisible(true);
		keyboardScreenFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	// Adds all the buttons and organizes the layouts
	private void addWidgets() {
		// Create Layouts
		keyboardScreenPanel.setLayout(new BoxLayout(keyboardScreenPanel, BoxLayout.Y_AXIS));
		keyRowOnePanel.setLayout(new BoxLayout(keyRowOnePanel, BoxLayout.X_AXIS));
		keyRowTwoPanel.setLayout(new BoxLayout(keyRowTwoPanel, BoxLayout.X_AXIS));
		keyRowThreePanel.setLayout(new BoxLayout(keyRowThreePanel, BoxLayout.X_AXIS));
		keyRowFourPanel.setLayout(new BoxLayout(keyRowFourPanel, BoxLayout.X_AXIS));
		keyRowFivePanel.setLayout(new BoxLayout(keyRowFivePanel, BoxLayout.X_AXIS));
		keyRowSixPanel.setLayout(new BoxLayout(keyRowSixPanel, BoxLayout.X_AXIS));
		textAreaPanel.setLayout(new BoxLayout(textAreaPanel, BoxLayout.X_AXIS));
		
		// Build Text Box
		textBar.setText(searchText);
		textAreaPanel.add(textBar);
		textAreaPanel.add(Box.createRigidArea(new Dimension(0,50)));
		
		// Enter/space/backspace/go back
		keyRowFivePanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton back_button = new JButton("BACK");
		back_button.setMargin(new Insets(0,5,0,4));
		keyRowFivePanel.add(back_button);
		back_button.addActionListener(e -> {
    		keyboardScreenFrame.dispose();
    	});
		keyRowFivePanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton delete_button = new JButton("DEL");
		delete_button.setMargin(new Insets(0,10,0,9));
		keyRowFivePanel.add(delete_button);
		delete_button.addActionListener(e -> {
    		deleteText();
    		
    	});
		keyRowFivePanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton enter_button = new JButton("ENTER");
		enter_button.setMargin(new Insets(0,2,0,2));
		keyRowFivePanel.add(enter_button);
		enter_button.addActionListener(e -> {
			if(searchText.length()==4) {
				if(ProductDatabases.PLU_PRODUCT_DATABASE.get(new PriceLookUpCode(searchText)) != null) {
					logic.addPLUCodedProductController.addPLUCode(new PriceLookUpCode(searchText)); 
					mainGUI.getAddItemScreen().updateReceipt();
					keyboardScreenFrame.dispose();
				} else {
					searchText = "PLU NOT FOUND";
					textBar.setText("PLU NOT FOUND");
				}
			}
    	});
		
		// Number row
		keyRowOnePanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton one_button = new JButton(" 1 ");
		keyRowOnePanel.add(one_button);
		one_button.addActionListener(e -> {
    		addText("1");
    		
    	});
		keyRowOnePanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton two_button = new JButton(" 2 ");
		keyRowOnePanel.add(two_button);
		two_button.addActionListener(e -> {
    		addText("2");
    		
    	});
		keyRowOnePanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton three_button = new JButton(" 3 ");
		keyRowOnePanel.add(three_button);
		three_button.addActionListener(e -> {
    		addText("3");
    		
    	});
		keyRowTwoPanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton four_button = new JButton(" 4 ");
		keyRowTwoPanel.add(four_button);
		four_button.addActionListener(e -> {
    		addText("4");
    		
    	});
		keyRowTwoPanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton five_button = new JButton(" 5 ");
		keyRowTwoPanel.add(five_button);
		five_button.addActionListener(e -> {
    		addText("5");
    		
    	});
		keyRowTwoPanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton six_button = new JButton(" 6 ");
		keyRowTwoPanel.add(six_button);
		six_button.addActionListener(e -> {
    		addText("6");
    		
    	});
		keyRowThreePanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton seven_button = new JButton(" 7 ");
		keyRowThreePanel.add(seven_button);
		seven_button.addActionListener(e -> {
    		addText("7");
    		
    	});
		keyRowThreePanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton eight_button = new JButton(" 8 ");
		keyRowThreePanel.add(eight_button);
		eight_button.addActionListener(e -> {
    		addText("8");
    		
    	});
		keyRowThreePanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton nine_button = new JButton(" 9 ");
		keyRowThreePanel.add(nine_button);
		nine_button.addActionListener(e -> {
    		addText("9");
    		
    	});
		keyRowFourPanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton zero_button = new JButton(" 0 ");
		keyRowFourPanel.add(zero_button);
		zero_button.addActionListener(e -> {
    		addText("0");
    		
    	});

		
		// Add the smaller containers to the main panel
		keyboardScreenPanel.add(textAreaPanel);
		keyboardScreenPanel.add(keyRowOnePanel);
		keyboardScreenPanel.add(keyRowTwoPanel);
		keyboardScreenPanel.add(keyRowThreePanel);
		keyboardScreenPanel.add(keyRowFourPanel);
		keyboardScreenPanel.add(keyRowFivePanel);
		
		
	}
	
	private void addText(String letter) {
		if(searchText.equals("ENTER PLU CODE") || searchText.equals("PLU NOT FOUND")) {
			searchText = letter;
    		textBar.setText(searchText);
		}
		else if (searchText.length()<4){
			searchText += letter;
			textBar.setText(searchText);
		}	
		
	}
	
	private void deleteText() {
		if(!searchText.equals("ENTER PLU CODE") || !searchText.equals("PLU NOT FOUND")) {
			searchText = searchText.substring(0, searchText.length() - 1);
		}
		
		if(searchText.equals("")) {
			searchText = "ENTER PLU CODE";
			textBar.setText(searchText);
		}
		else {
			textBar.setText(searchText);
		}
		
	}

	public JPanel getPanel() {
		return keyboardScreenPanel;
	}
}

