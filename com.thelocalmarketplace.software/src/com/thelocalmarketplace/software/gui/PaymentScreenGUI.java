package com.thelocalmarketplace.software.gui;

import javax.swing.*;

import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.CentralStationLogic.PaymentMethods;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

/**
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


public class PaymentScreenGUI {
    private JFrame paymentPageFrame;
    private JPanel paymentPagePanel;
    private JButton cashButton;
    private JButton creditButton;
    private JButton debitButton;
    private JButton notifyAttendantButton;
    private JButton backToCartButton;
    private JButton finishCheckoutButton;
    private JLabel totalPriceLabel;
    private JLabel itemsInCartLabel;
    private JLabel selectPaymentLabel;
    private JList<String> cartItemList;
    private CentralStationLogic logic;
    private MainGUI mainGUI;

    public PaymentScreenGUI(MainGUI m, CentralStationLogic l) {
    	mainGUI = m;
    	logic = l;
        paymentPageFrame = new JFrame("The LocalMarketplace Self-Checkout Station");
        paymentPagePanel = new JPanel();

        addWidgets();

        paymentPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        paymentPageFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        paymentPageFrame.setContentPane(paymentPagePanel);
    }
    private void addWidgets() {
        paymentPagePanel.setLayout(new BorderLayout());

        // top panel
        JPanel topPanel = new JPanel();
        selectPaymentLabel = new JLabel("Select Payment Method"); 
        selectPaymentLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(selectPaymentLabel);
        paymentPagePanel.add(topPanel, BorderLayout.NORTH);


        // center panel (containing left and right panels)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 2));

        // left panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(5, 1, 0, 20));

        // buttons
        cashButton = new JButton("Cash");
        creditButton = new JButton("Credit");
        debitButton = new JButton("Debit");
        notifyAttendantButton = new JButton("Notify Attendant");
        backToCartButton = new JButton("Back to cart");

        buttonsPanel.add(cashButton);
        buttonsPanel.add(creditButton);
        buttonsPanel.add(debitButton);
        buttonsPanel.add(notifyAttendantButton);
        buttonsPanel.add(backToCartButton);

        centerPanel.add(buttonsPanel);
        

        
        // right panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        finishCheckoutButton = new JButton("Finish Checkout");
        finishCheckoutButton.setFont(new Font("Arial", Font.BOLD, 15));
        finishCheckoutButton.setPreferredSize(new Dimension(150, 50));
        itemsInCartLabel = new JLabel("Items in cart:");
        itemsInCartLabel.setFont(new Font("Arial", Font.BOLD, 20));

        DefaultListModel<String> cartItemList = new DefaultListModel<>();
        cartItemList.addElement("Item 1");
        cartItemList.addElement("Item 2");
        cartItemList.addElement("Item 3");
 
        JList<String> cartListObjt = new JList<>(cartItemList);
        cartListObjt.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        cartListObjt.setLayoutOrientation(JList.VERTICAL_WRAP); 
        cartListObjt.setVisibleRowCount(-1);

        cartListObjt.setFont(new Font("Arial", Font.PLAIN, 20));

        JScrollPane cartScrollPane = new JScrollPane(cartListObjt);

        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(itemsInCartLabel, BorderLayout.NORTH);
        listPanel.add(cartScrollPane, BorderLayout.CENTER);
        
        float totalPrice = 0;
        String totalPriceString = "Total Price ($): ";
        JTextArea totalPriceText = new JTextArea(totalPriceString + String.valueOf(totalPrice));
        totalPriceText.setEditable(false);
        totalPriceText.setFont(new Font("Arial", Font.BOLD, 20));

        rightPanel.add(listPanel, BorderLayout.CENTER);
        rightPanel.add(totalPriceText, BorderLayout.NORTH);
        rightPanel.add(finishCheckoutButton, BorderLayout.SOUTH);

        centerPanel.add(rightPanel);

        paymentPagePanel.add(centerPanel, BorderLayout.CENTER);
        
        cashButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	getPaymentPageFrame().dispose();
                logic.selectPaymentMethod(PaymentMethods.CASH);
            	mainGUI.getCardLayout().show(mainGUI.getMainPanel(), "cash");
            }
        });
        
        debitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	getPaymentPageFrame().dispose();
                logic.selectPaymentMethod(PaymentMethods.DEBIT);
            	mainGUI.getCardLayout().show(mainGUI.getMainPanel(), "debit");
            }
        });
        
        creditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	getPaymentPageFrame().dispose();
                logic.selectPaymentMethod(PaymentMethods.CREDIT);
            	mainGUI.getCardLayout().show(mainGUI.getMainPanel(), "credit");
            }
        });
        
        backToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	getPaymentPageFrame().dispose();
            	mainGUI.getCardLayout().show(mainGUI.getMainPanel(), "addItem");
            }
        });
        
        // notify attendant
        notifyAttendantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO attendantScreen.dosomething()
            }
        });
        
        finishCheckoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(logic.cartLogic.getBalanceOwed().intValue() == 0) {
                	BigDecimal missed = logic.coinPaymentController.getMissed().add(logic.cashPaymentController.getMissed());
                	logic.receiptPrintingController.handlePrintReceipt(missed);
                    paymentPageFrame.dispose();
                    mainGUI.getCardLayout().show(mainGUI.getMainPanel(), "completion");
            	}
            }
        });
    }
 
    private void notifyAttendant() {
        JOptionPane.showMessageDialog(paymentPageFrame, "Attendant notified. Please wait for assistance.");
    }

	public JButton getCashButton() {
		return cashButton;
	}
	public JButton getCreditButton() {
		return creditButton;
	}
	public JButton getDebitButton() {
		return debitButton;
	}
	public JButton getNotifyAttendantButton() {
		return notifyAttendantButton;
	}
	public JFrame getPaymentPageFrame() {
		return paymentPageFrame;
	}
	public JPanel getPanel() {
		return paymentPagePanel;
	}
}
