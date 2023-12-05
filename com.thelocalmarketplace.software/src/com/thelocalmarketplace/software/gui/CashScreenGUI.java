package com.thelocalmarketplace.software.gui;

import javax.swing.*;

import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.banknote.Banknote;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.CentralStationLogic.PaymentMethods;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

// need help to connect the software to this

/**
 * GUI for the cash transactions 
 * 
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
 * @author Camila Hernandez (30134911)
 * @author Ananya Jain (30196069)
 * @author Zhenhui Ren (30139966)
 * @author Eric George (30173268)
 * @author Jenny Dang (30153821)
 * @author Tanmay Mishra (30127407)
 * @author Adrian Brisebois (30170764)
 * @author Atique Muhammad (30038650)
 * @author Ryan Korsrud (30173204)
 */

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
        float[] currencyValues = {100f, 50f, 20f, 10f, 5f, 2f, 1f, 0.25f, 0.10f, 0.05f};
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
            	BigDecimal cash = new BigDecimal(Double.parseDouble(totalCashField.getText().substring(1)));
            	logic.cartLogic.modifyBalance(cash.negate());
//            	System.out.println("cash paying with amount " + cash);
            	logic.receiptPrintingController.addAmountPaid(PaymentMethods.CASH, cash);
//            	logic.cartLogic.modifyBalance(cash.negate());
            	
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
            	if(value>=5) {
            		try {
            			System.out.println(value);
						logic.hardware.getBanknoteInput().receive(new Banknote(Currency.getInstance("CAD"), new BigDecimal(value)));
					} catch (DisabledException | CashOverloadException e1) {
						e1.printStackTrace();
					}
            	}
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
