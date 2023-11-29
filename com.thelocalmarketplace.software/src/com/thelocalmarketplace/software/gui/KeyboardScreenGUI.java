package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import java.awt.*;


public class KeyboardScreenGUI {
	
	// Variables for the screen
	JFrame keyboardScreenFrame;
	JPanel keyboardScreenPanel;
	JPanel keyboardScreenPane;
	
	
	// Constructor for the screen
	public KeyboardScreenGUI() {
		keyboardScreenFrame = new JFrame("The LocalMarketplace Self-Checkout Station");
		keyboardScreenPanel = new JPanel();
		keyboardScreenPane = new JPanel();
		
		
		addWidgets();
		
		// TODO: Build out the rest of the screen. 
		keyboardScreenFrame.getContentPane().add(keyboardScreenPanel, BorderLayout.CENTER);
		keyboardScreenFrame.getContentPane().add(keyboardScreenPane, BorderLayout.CENTER);
		
		keyboardScreenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		keyboardScreenFrame.pack();
		keyboardScreenFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		keyboardScreenFrame.setVisible(true);
		
	}
	
	// Adds all the buttons
	private void addWidgets() {
		// Create Layouts
		BoxLayout keyboardRow1 = new BoxLayout();
		keyboardScreenPanel.setLayout(keyboardRow1);
		BoxLayout keyboardRow2 = new BoxLayout();
		keyboardScreenPane.setLayout(keyboardRow2);
		
		// Add Buttons
		keyboardScreenPanel.add(new JButton(" Q "));
		keyboardScreenPanel.add(new JButton(" W "));
		keyboardScreenPanel.add(new JButton(" E "));
		keyboardScreenPanel.add(new JButton(" R "));
		keyboardScreenPanel.add(new JButton(" T "));
		keyboardScreenPanel.add(new JButton(" Y "));
		keyboardScreenPanel.add(new JButton(" U "));
		keyboardScreenPanel.add(new JButton(" I "));
		keyboardScreenPanel.add(new JButton(" O "));
		keyboardScreenPanel.add(new JButton(" P "));
		keyboardScreenPanel.add(new JButton(" _ "));
		
		keyboardScreenPane.add(new JButton(" A "));
		keyboardScreenPane.add(new JButton(" S "));
		keyboardScreenPane.add(new JButton(" D "));
		keyboardScreenPane.add(new JButton(" F "));
		keyboardScreenPane.add(new JButton(" G "));
		keyboardScreenPane.add(new JButton(" H "));
		keyboardScreenPane.add(new JButton(" J "));
		keyboardScreenPane.add(new JButton(" K "));
		keyboardScreenPane.add(new JButton(" L "));
		keyboardScreenPane.add(new JButton(" @ "));
		
		
	}
	
	// Just for testing screen.
	public static void main(String[] args) {
		KeyboardScreenGUI keyboardGUI = new KeyboardScreenGUI();
	}
}

