package com.thelocalmarketplace.software.gui;

import javax.swing.*;

import com.thelocalmarketplace.software.logic.CentralStationLogic;

import java.awt.*;

/**
 * @author Camila Hernandez (30134911)
  ------------------------------------
 * @author Alan Yong (30105707)
 * @author Andrew Matti (30182547)
 * @author Olivia Crosby (30099224)
 * @author Rico Manalastas (30164386)
 * @author Shanza Raza (30192765)
 * @author Danny Ly (30127144)
 * @author Maheen Nizmani (30172615)
 * @author Christopher Lo (30113400)
 * @author Michael Svoboda (30039040)
 * @author Sukhnaaz Sidhu (30161587)
 * @author Ian Beler (30174903)
 * @author Gareth Jenkins (30102127)
 * @author Jahnissi Nwakanma (30174827)
 * @author Ananya Jain (30196069)
 * @author Zhenhui Ren (30139966)
 * @author Eric George (30173268)
 * @author Jenny Dang (30153821)
 * @author Tanmay Mishra (30127407)
 * @author Adrian Brisebois (30170764)
 * @author Atique Muhammad (30038650)
 * @author Ryan Korsrud (30173204)
 */

public class CompletionScreenGUI {
	private JFrame completionScreenFrame;
	private JPanel completionScreenPanel;
	private JLabel receiptPrintedLabel;
	private JLabel collectBagsLabel;
	private JLabel thankYouMessageLabel;
	private JLabel kindMessageLabel;
	private CentralStationLogic logic;
	private MainGUI mainGUI;
	
	public CompletionScreenGUI(MainGUI m, CentralStationLogic l) {
		mainGUI = m;
		logic = l;
		completionScreenFrame = new JFrame("TheLocalMarketplace Self-Checkout Station");
		completionScreenPanel = new JPanel();

		addWidgets();
		
		completionScreenFrame.getContentPane().add(completionScreenPanel, BorderLayout.CENTER);
		
		completionScreenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		completionScreenFrame.pack();
		completionScreenFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

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
	
	public JPanel getPanel() {
		return this.completionScreenPanel;
	}
	
	
	public JPanel getCompletionScreenPanel() {
		return completionScreenPanel;
	}
}
