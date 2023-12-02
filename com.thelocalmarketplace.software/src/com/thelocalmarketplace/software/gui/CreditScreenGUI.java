package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.thelocalmarketplace.software.controllers.pay.CardReaderController;
import com.thelocalmarketplace.software.logic.CentralStationLogic;

// need help to connect the software to this

public class CreditScreenGUI {
    private JFrame creditPageFrame;
    private JPanel creditPagePanel;
    private CardReaderController cardReaderController;
    
    public CreditScreenGUI() {
        creditPageFrame = new JFrame("The LocalMarketplace Self-Checkout Station");
        creditPagePanel = new JPanel();

        addWidgets();
 
        creditPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        creditPageFrame.setSize(1000, 1000); 
        creditPageFrame.setContentPane(creditPagePanel);
        creditPageFrame.setVisible(true);
    }
    
    private void addWidgets() {

        JButton insertButton = new JButton("Insert");
        JButton tapButton = new JButton("Tap");
        JButton swipeButton = new JButton("Swipe");
        JButton goBackButton = new JButton("Go back");


        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardReaderController.aCardHasBeenInserted();
            }
        });

        tapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardReaderController.aCardHasBeenTapped();
            }
        });
        
        swipeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardReaderController.aCardHasBeenSwiped();
            }
        });

        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	creditPageFrame.dispose();
            	PaymentScreenGUI paymentScreen = new PaymentScreenGUI();
            }
        });

        creditPagePanel.setLayout(new GridLayout(2, 2)); 
        creditPagePanel.add(insertButton);
        creditPagePanel.add(tapButton);
        creditPagePanel.add(swipeButton);
        creditPagePanel.add(goBackButton);
    }
    
    public static void main(String[] args) {
    	CreditScreenGUI creditScreen = new CreditScreenGUI();
    }
}
