package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import java.awt.*;

public class StartScreenGUI {
	JFrame startScreenFrame;
	JPanel startScreenPanel;
	JPanel verticalPanel;
	JPanel languageVerticalPanel;
	JPanel stationVerticalPanel;
	JPanel horizontalPanel;
	JLabel welcomeLabel;
	JLabel startLabel;
	JLabel languageLabel;
	JLabel stationLabel;
	JButton membershipButton;
	JButton startSessionButton;
	JComboBox selectCheckoutStationComboBox;
	JComboBox selectLanguageComboBox;
	
	public StartScreenGUI() {
		startScreenFrame = new JFrame("TheLocalMarketplace Self-Checkout Station");
		startScreenPanel = new JPanel();
		verticalPanel = new JPanel();
		languageVerticalPanel = new JPanel();
		stationVerticalPanel = new JPanel();
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
		languageVerticalPanel.setLayout(new BoxLayout(languageVerticalPanel, BoxLayout.Y_AXIS));
		stationVerticalPanel.setLayout(new BoxLayout(stationVerticalPanel, BoxLayout.Y_AXIS));
		horizontalPanel.setLayout(new BoxLayout(horizontalPanel, BoxLayout.X_AXIS));
		
		// Create labels
		welcomeLabel = new JLabel("Welcome!", SwingConstants.CENTER);
		startLabel = new JLabel("Please touch 'Start Session' to begin", SwingConstants.CENTER);
		languageLabel = new JLabel("Select a language");
		stationLabel = new JLabel("Select a self-checkout station type");
		
		// Set label alignment
		welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		startLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		languageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		stationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// Set label font size
		Font welcomeLabelFont = new Font("Arial", Font.PLAIN, 70);
		Font startLabelFont = new Font("Arial", Font.PLAIN, 45);
		welcomeLabel.setFont(welcomeLabelFont);
		startLabel.setFont(startLabelFont);
		
		// Create buttons
		membershipButton = new JButton("Swipe membership card");
		startSessionButton = new JButton("Start Session");
	
		// Language selection
		String[] languageChoices = { "English", "French" };
		
		// Checkout station selection
		String[] stationChoices = { "Bronze", "Silver", "Gold" };
				
		// Create ComboBox
		selectCheckoutStationComboBox = new JComboBox<String>(stationChoices);
		selectLanguageComboBox = new JComboBox<String>(languageChoices);
		
		// Set preferred size for combo boxes
		Dimension comboBoxSize = new Dimension(0, -100);
		selectCheckoutStationComboBox.setPreferredSize(comboBoxSize);
		selectLanguageComboBox.setPreferredSize(comboBoxSize);

		// Set button alignment
		membershipButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		startSessionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	
		// Set button font size
		Font startSessionButtonFont = new Font("Arial", Font.PLAIN, 40);
		startSessionButton.setFont(startSessionButtonFont);
		Font membershipButtonFont = new Font("Arial", Font.PLAIN, 20);
		membershipButton.setFont(membershipButtonFont);
		
		// Set button size
		Dimension startSessionButtonSize = new Dimension(350, 1500);
		startSessionButton.setMaximumSize(startSessionButtonSize);
		Dimension membershipButtonSize = new Dimension(350, 350);
		membershipButton.setMaximumSize(membershipButtonSize);
		
		// Add glue to push labels to the center vertically
		verticalPanel.add(Box.createVerticalGlue());
		
		// Add labels and buttons to the panel
		verticalPanel.add(Box.createVerticalStrut(150));
		verticalPanel.add(welcomeLabel);
		verticalPanel.add(Box.createVerticalStrut(20));
		verticalPanel.add(startLabel);
		verticalPanel.add(Box.createVerticalStrut(20));
		verticalPanel.add(startSessionButton);
		verticalPanel.add(Box.createVerticalStrut(20));
		verticalPanel.add(membershipButton);
		
		// Add glue to push labels to the center
		verticalPanel.add(Box.createVerticalGlue());
		
		languageVerticalPanel.add(Box.createVerticalGlue());
		languageVerticalPanel.add(languageLabel);
		languageVerticalPanel.add(selectLanguageComboBox);
		languageVerticalPanel.add(Box.createVerticalGlue());
		
		stationVerticalPanel.add(Box.createVerticalGlue());
		stationVerticalPanel.add(stationLabel);
		stationVerticalPanel.add(selectCheckoutStationComboBox);
		stationVerticalPanel.add(Box.createVerticalGlue());
		
		horizontalPanel.add(Box.createHorizontalGlue());
		horizontalPanel.add(languageVerticalPanel);
		horizontalPanel.add(stationVerticalPanel);
		horizontalPanel.add(Box.createHorizontalGlue());
		
		startScreenPanel.add(verticalPanel);
		startScreenPanel.add(Box.createVerticalStrut(-100));
		startScreenPanel.add(horizontalPanel);
	}
	
	public static void main(String[] args) {
		StartScreenGUI startScreen = new StartScreenGUI();
	}
}
