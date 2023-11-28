package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import java.awt.*;

public class CompletionScreenGUI {
	private JFrame completionScreenFrame;
	private JPanel completionScreenPanel;
	private JLabel receiptPrintedLabel;
	private JLabel collectBagsLabel;
	private JLabel thankYouMessageLabel;
	private JLabel kindMessageLabel;
	
	public CompletionScreenGUI() {
		completionScreenFrame = new JFrame("TheLocalMarketplace Self-Checkout Station");
		completionScreenPanel = new JPanel();

		addWidgets();
		
		completionScreenFrame.getContentPane().add(completionScreenPanel, BorderLayout.CENTER);
		
		completionScreenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		completionScreenFrame.pack();
		completionScreenFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		completionScreenFrame.setVisible(true);
	}
}
