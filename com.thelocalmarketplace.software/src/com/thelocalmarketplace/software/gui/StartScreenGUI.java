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
		startScreenFrame.setVisible(true);
	}
	
	private void addWidgets() {
		welcomeLabel = new JLabel("Welcome!");
		welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		startLabel = new JLabel("Please touch 'Start Session' to begin");
		startLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		startScreenPanel.setLayout(new BoxLayout(startScreenPanel, BoxLayout.Y_AXIS));
		
		membershipButton = new JButton("Swipe membership card");
		membershipButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		beginCheckoutButton = new JButton("Start Session");
		beginCheckoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		selectCheckoutStationButton = new JButton("Select checkout station type");
		selectCheckoutStationButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		selectLanguageButton = new JButton("Select language");
		selectLanguageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		startScreenPanel.add(welcomeLabel);
		startScreenPanel.add(startLabel);
		startScreenPanel.add(membershipButton);
		startScreenPanel.add(beginCheckoutButton);
		startScreenPanel.add(selectCheckoutStationButton);
		startScreenPanel.add(selectLanguageButton);
		
	}
	
	public static void main(String[] args) {
		StartScreenGUI startScreen = new StartScreenGUI();
	}
}
