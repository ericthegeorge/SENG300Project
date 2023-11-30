package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import java.awt.*;


public class KeyboardScreenGUI {
	
	// Variables for the screen
	JFrame keyboardScreenFrame;
	JPanel keyboardScreenPanel;
	JPanel keyRowOnePanel;
	JPanel keyRowTwoPanel;
	JPanel keyRowThreePanel;
	JPanel keyRowFourPanel;
	JPanel keyRowFivePanel;
	JPanel textAreaPanel;
	JTextArea textArea;
	
	
	// Constructor for the screen
	public KeyboardScreenGUI() {
		keyboardScreenFrame = new JFrame("The LocalMarketplace Self-Checkout Station");
		keyboardScreenPanel = new JPanel();
		keyRowOnePanel = new JPanel();
		keyRowTwoPanel = new JPanel();
		keyRowThreePanel = new JPanel();
		keyRowFourPanel = new JPanel();
		keyRowFivePanel = new JPanel();
		textAreaPanel = new JPanel();
		textArea = new JTextArea("Enter Item Name or code");
		
		
		
		addWidgets();
		
		// TODO: Build out the rest of the screen. 
		keyboardScreenFrame.getContentPane().add(keyboardScreenPanel, BorderLayout.CENTER);
		
		
		keyboardScreenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		keyboardScreenFrame.pack();
		keyboardScreenFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		keyboardScreenFrame.setVisible(true);
		
	}
	
	// Adds all the buttons
	private void addWidgets() {
		// Create Layouts
		keyboardScreenPanel.setLayout(new BoxLayout(keyboardScreenPanel, BoxLayout.Y_AXIS));
		keyRowOnePanel.setLayout(new BoxLayout(keyRowOnePanel, BoxLayout.X_AXIS));
		keyRowTwoPanel.setLayout(new BoxLayout(keyRowTwoPanel, BoxLayout.X_AXIS));
		keyRowThreePanel.setLayout(new BoxLayout(keyRowThreePanel, BoxLayout.X_AXIS));
		keyRowFourPanel.setLayout(new BoxLayout(keyRowFourPanel, BoxLayout.X_AXIS));
		keyRowFivePanel.setLayout(new BoxLayout(keyRowFivePanel, BoxLayout.X_AXIS));
		textAreaPanel.setLayout(new BoxLayout(textAreaPanel, BoxLayout.X_AXIS));
		
		
		// Add Buttons
		keyRowOnePanel.add(new JButton(" Q "));
		keyRowOnePanel.add(new JButton(" W "));
		keyRowOnePanel.add(new JButton(" E "));
		keyRowOnePanel.add(new JButton(" R "));
		keyRowOnePanel.add(new JButton(" T "));
		keyRowOnePanel.add(new JButton(" Y "));
		keyRowOnePanel.add(new JButton(" U "));
		keyRowOnePanel.add(new JButton(" I "));
		keyRowOnePanel.add(new JButton(" O "));
		keyRowOnePanel.add(new JButton(" P "));
		keyRowOnePanel.add(new JButton(" _ "));
		
		keyRowTwoPanel.add(new JButton(" A "));
		keyRowTwoPanel.add(new JButton(" S "));
		keyRowTwoPanel.add(new JButton(" D "));
		keyRowTwoPanel.add(new JButton(" F "));
		keyRowTwoPanel.add(new JButton(" G "));
		keyRowTwoPanel.add(new JButton(" H "));
		keyRowTwoPanel.add(new JButton(" J "));
		keyRowTwoPanel.add(new JButton(" K "));
		keyRowTwoPanel.add(new JButton(" L "));
		keyRowTwoPanel.add(new JButton(" @ "));
		
		keyRowThreePanel.add(new JButton(" Z "));
		keyRowThreePanel.add(new JButton(" X "));
		keyRowThreePanel.add(new JButton(" C "));
		keyRowThreePanel.add(new JButton(" V "));
		keyRowThreePanel.add(new JButton(" B "));
		keyRowThreePanel.add(new JButton(" N "));
		keyRowThreePanel.add(new JButton(" M "));
		keyRowThreePanel.add(new JButton(" , "));
		keyRowThreePanel.add(new JButton(" . "));
		keyRowThreePanel.add(new JButton(" - "));
		
		keyRowFourPanel.add(new JButton(" SPACE "));
		keyRowFourPanel.add(new JButton(" BACKSPACE "));
		keyRowFourPanel.add(new JButton(" ENTER "));
		
		keyRowFivePanel.add(new JButton(" 1 "));
		keyRowFivePanel.add(new JButton(" 2 "));
		keyRowFivePanel.add(new JButton(" 3 "));
		keyRowFivePanel.add(new JButton(" 4 "));
		keyRowFivePanel.add(new JButton(" 5 "));
		keyRowFivePanel.add(new JButton(" 6 "));
		keyRowFivePanel.add(new JButton(" 7 "));
		keyRowFivePanel.add(new JButton(" 8 "));
		keyRowFivePanel.add(new JButton(" 9 "));
		keyRowFivePanel.add(new JButton(" 0 "));
		
		// Add the smaller containers to the main panel
		textAreaPanel.add(textArea);
		keyboardScreenPanel.add(textAreaPanel);
		keyboardScreenPanel.add(keyRowFivePanel);
		keyboardScreenPanel.add(keyRowOnePanel);
		keyboardScreenPanel.add(keyRowTwoPanel);
		keyboardScreenPanel.add(keyRowThreePanel);
		keyboardScreenPanel.add(keyRowFourPanel);
		
		
	}
	
	// Just for testing screen.
	public static void main(String[] args) {
		KeyboardScreenGUI keyboardGUI = new KeyboardScreenGUI();
	}
}

