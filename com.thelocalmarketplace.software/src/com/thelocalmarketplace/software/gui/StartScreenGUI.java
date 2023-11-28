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
		membershipButton = new JButton("Swipe membership card");
		beginCheckoutButton = new JButton("Begin checkout");
		selectCheckoutStationButton = new JButton("Select checkout station type");
		selectLanguageButton = new JButton("Select language");
		
		
	}
}
