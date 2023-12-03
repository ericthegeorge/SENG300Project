package com.thelocalmarketplace.software.gui;

import javax.swing.*;

import com.thelocalmarketplace.software.logic.CentralStationLogic;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

// need help to connect the software to this

public class CashScreenGUI {
    private JFrame cashPageFrame;
    private JPanel cashPagePanel;
    private JTextField totalCashField;
    private Map<Float, Integer> currencyCounts;
    private JPanel currencyButtonsPanel;
    private JButton goBackButton;
    private MainGUI mainGUI;
    private CentralStationLogic logic;
    

    public CashScreenGUI(MainGUI m, CentralStationLogic l) {
    	mainGUI = m;
    	logic = l;
    	
        cashPageFrame = new JFrame("The LocalMarketplace Self-Checkout Station");
        cashPagePanel = new JPanel();
        totalCashField = new JTextField(15);
        currencyCounts = new HashMap<>();
        currencyButtonsPanel = new JPanel();
 
        addWidgets();

        cashPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cashPageFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        cashPageFrame.setContentPane(cashPagePanel);
    }

    private void addWidgets() {
        cashPagePanel.setLayout(new BorderLayout());

        // panel for bill and coin buttons on the left
        currencyButtonsPanel.setLayout(new GridLayout(0, 1));

        // buttons for bills and coins
        float[] currencyValues = {100.0f, 50.0f, 20.0f, 10.0f, 5.0f, 2.0f, 1.0f, 0.25f, 0.10f, 0.05f};
        for (float value : currencyValues) {
            addButton(value);
        }

        // panel for total cash information on the right
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // label for total cash inserted
        JLabel totalLabel = new JLabel("Total Cash Inserted:");
        totalPanel.add(totalLabel);
        totalLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        totalCashField.setFont(new Font("Arial", Font.PLAIN, 30));
        totalPanel.add(totalCashField);

        // add the bill and coin buttons and total panels to the main panel
        cashPagePanel.add(currencyButtonsPanel, BorderLayout.WEST);
        cashPagePanel.add(totalPanel, BorderLayout.CENTER);

        goBackButton = new JButton("Go back");
        goBackButton.setPreferredSize(new Dimension(500, 50));

        // notify attendant
        JButton notifyButton = new JButton("Notify Attendant");

        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cashPageFrame.dispose();
                mainGUI.getCardLayout().show(mainGUI.getMainPanel(), "payment");
            }
        });
        
        notifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notifyAttendant();
            }
        });

        JPanel bottomButtonsPanel = new JPanel(new GridLayout(1, 2));
        bottomButtonsPanel.add(goBackButton);
        bottomButtonsPanel.add(notifyButton);

        cashPagePanel.add(bottomButtonsPanel, BorderLayout.SOUTH);
    }

    private void addButton(float value) {
        JButton button = new JButton("$" + value);
        button.setPreferredSize(new Dimension(200, 100));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCurrencyInsertion(value);
            }
        });
        currencyButtonsPanel.add(button);
    }

    // i'm not sure if we should handle the cash inserted differently since this have any of the software in it
    
    private void handleCurrencyInsertion(float value) {
        currencyCounts.put(value, currencyCounts.getOrDefault(value, 0) + 1);
        updateTotalCash();
    }

    private void updateTotalCash() {
        float totalCash = (float) currencyCounts.entrySet().stream()
                .mapToDouble(entry -> entry.getKey() * entry.getValue())
                .sum();

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        totalCashField.setText(currencyFormat.format(totalCash));
    }

    private void notifyAttendant() {
        JOptionPane.showMessageDialog(cashPageFrame, "Attendant notified. Please wait for assistance.");
    }
    
    public JPanel getPanel() {
    	return cashPagePanel;
    }
}
