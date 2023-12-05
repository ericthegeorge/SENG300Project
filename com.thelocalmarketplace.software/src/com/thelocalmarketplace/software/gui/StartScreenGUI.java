package com.thelocalmarketplace.software.gui;

import javax.swing.*;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.hardware.SelfCheckoutStationSilver;
import com.thelocalmarketplace.software.logic.CentralStationLogic;

import powerutility.PowerGrid;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Camila Hernandez (30134911)
 * --------------------------------
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

public class StartScreenGUI {
	private JFrame frame;
	private JPanel panel;
	private JPanel verticalPanel;
	private JPanel languageVerticalPanel;
	private JPanel stationVerticalPanel;
	private JPanel horizontalPanel;
	private JLabel welcomeLabel;
	private JLabel startLabel;
	private JLabel languageLabel;
	private JLabel stationLabel;
	private JButton membershipButton;
	private JButton startSessionButton;
	//	private JComboBox selectCheckoutStationComboBox;
	private JComboBox selectLanguageComboBox;
	private AbstractSelfCheckoutStation station;
	private CentralStationLogic logic;
	private MainGUI mainGUI;
	
	public StartScreenGUI(MainGUI m, CentralStationLogic l) {
		mainGUI = m;
		logic = l;
		frame = new JFrame("TheLocalMarketplace Self-Checkout Station");
		panel = new JPanel();
		verticalPanel = new JPanel();
		languageVerticalPanel = new JPanel();
		stationVerticalPanel = new JPanel();
		horizontalPanel = new JPanel();
		
		addWidgets();
		
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	
	}

	private void addWidgets() {
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
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
		//selectCheckoutStationComboBox = new JComboBox<String>(stationChoices);
		selectLanguageComboBox = new JComboBox<String>(languageChoices);
		
		// Set preferred size for combo boxes
		Dimension comboBoxSize = new Dimension(0, -100);
		//selectCheckoutStationComboBox.setPreferredSize(comboBoxSize);
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
				// Add labels and buttons to the main vertical panel
		verticalPanel.add(Box.createVerticalGlue());
		// Add labels and buttons to the vertical panel
		verticalPanel.add(Box.createVerticalStrut(150));
		verticalPanel.add(welcomeLabel);
		verticalPanel.add(Box.createVerticalStrut(20));
		verticalPanel.add(startLabel);
		verticalPanel.add(Box.createVerticalStrut(20));
		verticalPanel.add(startSessionButton);
		verticalPanel.add(Box.createVerticalStrut(20));
		verticalPanel.add(membershipButton);
		verticalPanel.add(Box.createVerticalGlue());
		
		// Add glue to push labels to the center vertically
		// Add labels and buttons to the language vertical panel
		languageVerticalPanel.add(Box.createVerticalGlue());
		languageVerticalPanel.add(languageLabel);
		languageVerticalPanel.add(selectLanguageComboBox);
		languageVerticalPanel.add(Box.createVerticalGlue());
		
		// Add glue to push labels to the center vertically
		// Add labels and buttons to the station vertical panel
		stationVerticalPanel.add(Box.createVerticalGlue());
		stationVerticalPanel.add(stationLabel);
		//stationVerticalPanel.add(selectCheckoutStationComboBox);
		stationVerticalPanel.add(Box.createVerticalGlue());
		
		// Add glue to push labels to the center horizontally
		// Add labels and buttons to the horizontal panel
		horizontalPanel.add(Box.createHorizontalGlue());
		horizontalPanel.add(languageVerticalPanel);
		horizontalPanel.add(Box.createHorizontalStrut(10));
		horizontalPanel.add(stationVerticalPanel);
		horizontalPanel.add(Box.createHorizontalGlue());
		
		// Add all sub-panels to the main panel
		panel.add(verticalPanel);
		panel.add(Box.createVerticalStrut(-100));
		panel.add(horizontalPanel);
		
        //Initializing action listeners for all buttons
		getStartSessionButton().addActionListener(e -> {
			logic.startSession();
			mainGUI.getCardLayout().show(mainGUI.getMainPanel(), "addItem");
		});
		
       //Initializing action listeners for all buttons
		membershipButton.addActionListener(e -> {
			logic.membershipLogic.enterMembershipByNumber(MainGUI.membershipCard.number);
		});
	}
	
	public JButton getStartSessionButton() {
		return startSessionButton;
	}

	public JPanel getPanel() {
		return panel;
	}

}
