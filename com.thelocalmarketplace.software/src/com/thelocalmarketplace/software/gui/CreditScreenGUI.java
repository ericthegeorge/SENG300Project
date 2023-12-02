package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreditScreenGUI {
    private JFrame creditPageFrame;
    private JPanel creditPagePanel;
    
    public CreditScreenGUI() {
        creditPageFrame = new JFrame("The LocalMarketplace Self-Checkout Station");
        creditPagePanel = new JPanel();

        addWidgets();

        creditPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        creditPageFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        creditPageFrame.setContentPane(creditPagePanel);
        creditPageFrame.setVisible(true);
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
            	creditPageFrame.dispose();
            	PaymentScreenGUI paymentScreen = new PaymentScreenGUI();
            }
        });


        creditPagePanel.setLayout(new GridLayout(1, 3)); 
        creditPagePanel.add(button1);
        creditPagePanel.add(button2);
        creditPagePanel.add(button3);
    }
    
    public static void main(String[] args) {
    	CreditScreenGUI creditScreen = new CreditScreenGUI();
    }
}
