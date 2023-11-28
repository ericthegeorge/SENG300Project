package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import java.awt.*;

public class StartScreenGUI {
	JFrame startScreenFrame;
	JPanel startScreenPanel;
	
	public StartScreenGUI() {
		startScreenFrame = new JFrame("TheLocalMarketplace Self-Checkout Station");
		startScreenPanel = new JPanel();
		
		addwidgets();
		
		startScreenFrame.getContentPane().add(startScreenPanel, BorderLayout.CENTER);
		
		startScreenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startScreenFrame.pack();
		startScreenFrame.setVisible(true);
	}
}
