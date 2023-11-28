package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import java.awt.*;

public class StartScreenGUI {
	JFrame startScreenFrame;
	JPanel startScreenPanel;
	JLabel welcomeLabel;
	JLabel startLabel;
	JButton membershipButton;
	JButton beginCheckoutButton;
	JButton selectCheckoutStationButton;
	JButton selectLanguageButton;
	
	public StartScreenGUI() {
		startScreenFrame = new JFrame("TheLocalMarketplace Self-Checkout Station");
		startScreenPanel = new JPanel();
		
		addWidgets();
		
		startScreenFrame.getContentPane().add(startScreenPanel, BorderLayout.CENTER);
		
		startScreenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startScreenFrame.pack();
		startScreenFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		startScreenFrame.setVisible(true);
	}
	
	private void addWidgets() {
		startScreenPanel.setLayout(new BoxLayout(startScreenPanel, BoxLayout.Y_AXIS));
		
		// Create labels
		welcomeLabel = new JLabel("Welcome!", SwingConstants.CENTER);
		startLabel = new JLabel("Please touch 'Start Session' to begin", SwingConstants.CENTER);
		
		// Set label alignment
		welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		startLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// Set label font size
		Font labelFont = new Font("Arial", Font.PLAIN, 38);
		welcomeLabel.setFont(labelFont);
		startLabel.setFont(labelFont);
		
		// Create buttons
		membershipButton = new JButton("Swipe membership card");
		beginCheckoutButton = new JButton("Start Session");
		selectCheckoutStationButton = new JButton("Select checkout station type");
		selectLanguageButton = new JButton("Select language");
		
		// Set button alignment
		membershipButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		beginCheckoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		selectCheckoutStationButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		selectLanguageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// Add glue to push labels to the center vertically
		startScreenPanel.add(Box.createVerticalGlue());
		
		// Add labels and buttons to the panel
		startScreenPanel.add(welcomeLabel);
		startScreenPanel.add(startLabel);
		
		// Add glue to push labels to the center
		startScreenPanel.add(Box.createVerticalGlue());
		//startScreenPanel.add(membershipButton);
	//	startScreenPanel.add(beginCheckoutButton);
	//	startScreenPanel.add(selectCheckoutStationButton);
		//startScreenPanel.add(selectLanguageButton);
		
	}
	
	public static void main(String[] args) {
		StartScreenGUI startScreen = new StartScreenGUI();
	}
}
