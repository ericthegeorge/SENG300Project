package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DebitScreenGUI {
    private JFrame debitPageFrame;
    private JPanel debitPagePanel;
    
    public DebitScreenGUI() {
        debitPageFrame = new JFrame("The LocalMarketplace Self-Checkout Station");
        debitPagePanel = new JPanel();

        addWidgets();

        debitPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        debitPageFrame.setSize(1000, 1000);
        debitPageFrame.setContentPane(debitPagePanel);
        debitPageFrame.setVisible(true);
    }

    private void addWidgets() {

        JButton button1 = new JButton("Insert");
        JButton button2 = new JButton("Tap");
        JButton button3 = new JButton("Go back");

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	debitPageFrame.dispose();
            	PaymentScreenGUI paymentScreen = new PaymentScreenGUI();
            }
        });


        debitPagePanel.setLayout(new GridLayout(1, 3)); 
        debitPagePanel.add(button1);
        debitPagePanel.add(button2);
        debitPagePanel.add(button3);
    }
    
    public static void main(String[] args) {
        DebitScreenGUI debitScreen = new DebitScreenGUI();
    }
}
