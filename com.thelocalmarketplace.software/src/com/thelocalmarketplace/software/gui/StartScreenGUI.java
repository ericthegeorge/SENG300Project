package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import java.awt.*;

public class StartScreenGUI {
	JFrame startScreenFrame;
	JPanel startScreenPanel;
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
		startScreenPanel.setLayout(new BoxLayout(startScreenPanel, BoxLayout.Y_AXIS));
		
		membershipButton = new JButton("Swipe membership card");
		membershipButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		beginCheckoutButton = new JButton("Begin checkout");
		beginCheckoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		selectCheckoutStationButton = new JButton("Select checkout station type");
		selectCheckoutStationButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		selectLanguageButton = new JButton("Select language");
		selectLanguageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		startScreenPanel.add(membershipButton);
		startScreenPanel.add(beginCheckoutButton);
		startScreenPanel.add(selectCheckoutStationButton);
		startScreenPanel.add(selectLanguageButton);
		
	}
	
	public static void main(String[] args) {
		StartScreenGUI startScreen = new StartScreenGUI();
	}
}
