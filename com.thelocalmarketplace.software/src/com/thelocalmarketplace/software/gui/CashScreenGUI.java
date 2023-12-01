package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jenny Dang (30153821)
 * --------------------------------
 * @author Adrian Brisebois
 * @author Alan Yong
 * @author Ananya jain
 * @author Andrew Matti
 * @author Atique Muhammad
 * @author Christopher Lo
 * @author Danny Ly
 * @author Eric George
 * @author Gareth Jenkins
 * @author Ian Beler
 * @author Jahnissi Nwakanma
 * @author Camila Hernandez 
 * @author Maheen Nizamani
 * @author Michael Svoboda
 * @author Olivia Crosby
 * @author Rico Manalastas
 * @author Ryan Korsrud
 * @author Shanza Raza
 * @author Sukhnaaz Sidhu
 * @author Tanmay Mishra
 * @author Zhenhui Ren
 */

public class CashScreenGUI {
    private JFrame cashPageFrame;
    private JPanel cashPagePanel;
    private JTextField totalCashField;
    private Map<Integer, Integer> billCounts;

    public CashScreenGUI() {
        cashPageFrame = new JFrame("The LocalMarketplace Self-Checkout Station");
        cashPagePanel = new JPanel();
        totalCashField = new JTextField(15);
        billCounts = new HashMap<>();

        addWidgets();

        cashPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cashPageFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        cashPageFrame.setContentPane(cashPagePanel);
        cashPageFrame.setVisible(true);
    }

    private void addWidgets() {
        cashPagePanel.setLayout(new BorderLayout());

        // panel for bill buttons on the left
        JPanel billButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // buttons for bills
        int[] billValues = {100, 50, 20, 10, 5};
        for (int value : billValues) {
            JButton billButton = new JButton("$" + value);
            billButton.setPreferredSize(new Dimension(200, 100));
            billButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleBillInsertion(value);
                }
            });
            billButtonsPanel.add(billButton);
        }

        // panel for total cash information on the right
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // label for total cash inserted
        JLabel totalLabel = new JLabel("Total Cash Inserted:");
        totalPanel.add(totalLabel);
        totalLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        totalCashField.setFont(new Font("Arial", Font.PLAIN, 30));
        totalPanel.add(totalCashField);

        // add the bill buttons and total panels to the main panel
        cashPagePanel.add(billButtonsPanel, BorderLayout.WEST);
        cashPagePanel.add(totalPanel, BorderLayout.CENTER);

        // notify attendant
        JButton notifyButton = new JButton("Notify Attendant");
        notifyButton.setPreferredSize(new Dimension(200, 50));
        notifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notifyAttendant();
            }
        });
        cashPagePanel.add(notifyButton, BorderLayout.SOUTH);
    }

    private void handleBillInsertion(int value) {
        billCounts.put(value, billCounts.getOrDefault(value, 0) + 1);
        updateTotalCashField();
    }

    private void updateTotalCashField() {
        int totalCash = billCounts.entrySet().stream()
                .mapToInt(entry -> entry.getKey() * entry.getValue())
                .sum();

        // format the total cash as currency
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        totalCashField.setText(currencyFormat.format(totalCash));
    }

    private void notifyAttendant() {
        JOptionPane.showMessageDialog(cashPageFrame, "Attendant notified. Please wait for assistance.");
    }

    public static void main(String[] args) {
        CashScreenGUI cashScreen = new CashScreenGUI();
    }
}
