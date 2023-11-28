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
	
	private void addWidgets() {
		completionScreenPanel.setLayout(new BoxLayout(completionScreenPanel, BoxLayout.Y_AXIS));
		
		// Create labels
		receiptPrintedLabel = new JLabel("Receipt printed...", SwingConstants.CENTER);
		collectBagsLabel = new JLabel("Please collect your bags from the bagging area", SwingConstants.CENTER);
		thankYouMessageLabel = new JLabel("Thank you for shopping with us!");
		kindMessageLabel = new JLabel("Have a good day!");
		
		// Set label alignment
		receiptPrintedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		collectBagsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		thankYouMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		kindMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// Set label font size
		Font labelFont = new Font("Arial", Font.PLAIN, 45);
		receiptPrintedLabel.setFont(labelFont);
		collectBagsLabel.setFont(labelFont);
		thankYouMessageLabel.setFont(labelFont);
		kindMessageLabel.setFont(labelFont);
		
		// Add glue to push labels to the center vertically
		// Add labels and buttons to the main vertical panel
		completionScreenPanel.add(Box.createVerticalGlue());
		completionScreenPanel.add(receiptPrintedLabel);
		completionScreenPanel.add(Box.createVerticalStrut(20));
		completionScreenPanel.add(collectBagsLabel);
		completionScreenPanel.add(Box.createVerticalStrut(50));
		completionScreenPanel.add(thankYouMessageLabel);
		completionScreenPanel.add(Box.createVerticalStrut(20));
		completionScreenPanel.add(kindMessageLabel);
		completionScreenPanel.add(Box.createVerticalGlue());
	}
	
	public static void main(String[] args) {
		CompletionScreenGUI completionScreen = new CompletionScreenGUI();
	}
}
