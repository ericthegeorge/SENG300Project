package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.software.controllers.pay.CardReaderController;
import com.thelocalmarketplace.software.logic.CentralStationLogic;

// need help to connect the software to this

public class DebitScreenGUI {
    private JFrame debitPageFrame;
    private JPanel debitPagePanel;
    private CardReaderController cardReaderController;
    
    public DebitScreenGUI() {
        debitPageFrame = new JFrame("The LocalMarketplace Self-Checkout Station");
        debitPagePanel = new JPanel();

        addWidgets();

        debitPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        debitPageFrame.setSize(1000, 1000); 
        debitPageFrame.setContentPane(debitPagePanel);
        debitPageFrame.setVisible(true);

        this.cardReaderController = new CardReaderController(logic);
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
            	debitPageFrame.dispose();
            	PaymentScreenGUI paymentScreen = new PaymentScreenGUI();
            }
        });


        debitPagePanel.setLayout(new GridLayout(2, 2)); 
        debitPagePanel.add(insertButton);
        debitPagePanel.add(tapButton);
        debitPagePanel.add(swipeButton);
        debitPagePanel.add(goBackButton);
    }
    
    public static void main(String[] args) throws NullPointerException {
        DebitScreenGUI debitScreen = new DebitScreenGUI();
    }
}
