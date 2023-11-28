package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import java.awt.*;

public class StartScreenGUI {
	JFrame startScreenFrame;
	JPanel startScreenPanel;
	JPanel verticalPanel;
	JPanel horizontalPanel;
	JLabel welcomeLabel;
	JLabel startLabel;
	JButton membershipButton;
	JButton startSessionButton;
	JButton selectCheckoutStationButton;
	JButton selectLanguageButton;
	
	public StartScreenGUI() {
		startScreenFrame = new JFrame("TheLocalMarketplace Self-Checkout Station");
		startScreenPanel = new JPanel();
		verticalPanel = new JPanel();
		horizontalPanel = new JPanel();
		
		addWidgets();
		
		startScreenFrame.getContentPane().add(startScreenPanel, BorderLayout.CENTER);
		
		startScreenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startScreenFrame.pack();
		startScreenFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		startScreenFrame.setVisible(true);
	}
	
	private void addWidgets() {
		startScreenPanel.setLayout(new BoxLayout(startScreenPanel, BoxLayout.Y_AXIS));
		verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
		horizontalPanel.setLayout(new BoxLayout(horizontalPanel, BoxLayout.X_AXIS));
		
		// Create labels
		welcomeLabel = new JLabel("Welcome!", SwingConstants.CENTER);
		startLabel = new JLabel("Please touch 'Start Session' to begin", SwingConstants.CENTER);
		
		// Set label alignment
		welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		startLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// Set label font size
		Font welcomeLabelFont = new Font("Arial", Font.PLAIN, 70);
		Font startLabelFont = new Font("Arial", Font.PLAIN, 45);
		welcomeLabel.setFont(welcomeLabelFont);
		startLabel.setFont(startLabelFont);
		
		// Create buttons
		membershipButton = new JButton("Swipe membership card");
		startSessionButton = new JButton("Start Session");
		selectCheckoutStationButton = new JButton("Select checkout station type");
		selectLanguageButton = new JButton("Select language");
		
		// Set button alignment
		membershipButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		startSessionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		selectCheckoutStationButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		selectLanguageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// Set button font size
		Font startSessionButtonFont = new Font("Arial", Font.PLAIN, 40);
		startSessionButton.setFont(startSessionButtonFont);
		
		// Set button size
		Dimension buttonSize = new Dimension(350, 1500);
		startSessionButton.setMaximumSize(buttonSize);
		
		// Add glue to push labels to the center vertically
		verticalPanel.add(Box.createVerticalGlue());
		
		// Add labels and buttons to the panel
		verticalPanel.add(welcomeLabel);
		verticalPanel.add(Box.createVerticalStrut(20));
		verticalPanel.add(startLabel);
		verticalPanel.add(Box.createVerticalStrut(20));
		verticalPanel.add(startSessionButton);
		verticalPanel.add(Box.createVerticalStrut(20));
		verticalPanel.add(selectCheckoutStationButton);
		
		// Add glue to push labels to the center
		verticalPanel.add(Box.createVerticalGlue());
		
		// 
		
		horizontalPanel.add(Box.createHorizontalGlue());
		horizontalPanel.add(membershipButton);
		horizontalPanel.add(selectLanguageButton);
		horizontalPanel.add(Box.createHorizontalGlue());
		
		startScreenPanel.add(verticalPanel);
		startScreenPanel.add(horizontalPanel);
	}
	
	public static void main(String[] args) {
		StartScreenGUI startScreen = new StartScreenGUI();
	}
}
