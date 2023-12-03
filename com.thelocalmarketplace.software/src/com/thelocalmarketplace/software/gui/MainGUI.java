package com.thelocalmarketplace.software.gui;

import javax.swing.*;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.hardware.SelfCheckoutStationSilver;
import com.thelocalmarketplace.software.gui.CompletionScreenGUI;
import com.thelocalmarketplace.software.gui.StartScreenGUI;
import com.thelocalmarketplace.software.logic.CentralStationLogic;

import powerutility.PowerGrid;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI {
    private JFrame mainFrame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

	private StartScreenGUI startScreen;
    private CompletionScreenGUI completionScreen;
    private AttendantStationGUI attendantScreen;
    private CreditScreenGUI creditScreen;
    private DebitScreenGUI debitScreen;
    private KeyboardScreenGUI keyboardScreen;
    private PaymentScreenGUI paymentScreen;
    private AddItemGUI addItemScreen;
    
	private AbstractSelfCheckoutStation station;
    private CentralStationLogic logic;
    
    public static void main(String[] args) {
    	AbstractSelfCheckoutStation.resetConfigurationToDefaults();
        AbstractSelfCheckoutStation station = new SelfCheckoutStationGold();
		PowerGrid.engageUninterruptiblePowerSource();
		PowerGrid.instance().forcePowerRestore();
		station.plugIn(PowerGrid.instance());
		station.turnOn();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainGUI gui = new MainGUI(station);
                gui.logic.setBypassIssuePrediction(true);
            }
        });
    }

    public MainGUI(AbstractSelfCheckoutStation s) {
    	station = s;
    	logic = new CentralStationLogic(station);
    	logic.setGUI(this);
        mainFrame = new JFrame("Main GUI");
        mainPanel = new JPanel();
        cardLayout = new CardLayout();

        mainPanel.setLayout(cardLayout);

        //initializing and adding Screens
        startScreen = new StartScreenGUI(this, logic);
        completionScreen = new CompletionScreenGUI(this, logic);
        attendantScreen = new AttendantStationGUI(this, logic);
        creditScreen = new CreditScreenGUI(this, logic);
        debitScreen = new DebitScreenGUI(this, logic);
        keyboardScreen = new KeyboardScreenGUI(this, logic);
        paymentScreen = new PaymentScreenGUI(this, logic);
        addItemScreen = new AddItemGUI(this, logic);
        mainPanel.add(startScreen.getPanel(), "start");
        mainPanel.add(completionScreen.getPanel(), "completion");
        mainPanel.add(attendantScreen.getPanel(), "attendant");
        mainPanel.add(creditScreen.getPanel(), "credit");
        mainPanel.add(debitScreen.getPanel(), "debit");
        mainPanel.add(keyboardScreen.getPanel(), "keyboard");
        mainPanel.add(paymentScreen.getPanel(), "payment");
        mainPanel.add(addItemScreen.getPanel(), "addItem");
        
        JButton switchButton = new JButton("Switch Screens");
        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.next(mainPanel);
            }
        });
        
        mainFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
//      mainFrame.getContentPane().add(switchButton, BorderLayout.SOUTH);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setVisible(true);
    }
    
    public CardLayout getCardLayout() {
		return cardLayout;
	}

	public StartScreenGUI getStartScreen() {
		return startScreen;
	}

	public CompletionScreenGUI getCompletionScreen() {
		return completionScreen;
	}

	public AttendantStationGUI getAttendantScreen() {
		return attendantScreen;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

}
