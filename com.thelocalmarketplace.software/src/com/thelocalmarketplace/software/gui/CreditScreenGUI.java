package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;

import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.Card.CardData;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.external.CardIssuer;
import com.thelocalmarketplace.software.controllers.pay.CardReaderController;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.CentralStationLogic.CardMethods;
import com.thelocalmarketplace.software.logic.CentralStationLogic.PaymentMethods;
import com.thelocalmarketplace.software.logic.StateLogic.States;

// need help to connect the software to this

/**
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

public class CreditScreenGUI {
	private JFrame creditPageFrame;
	private JPanel creditPagePanel;
	private CentralStationLogic logic;
	private MainGUI mainGUI;
	private AbstractSelfCheckoutStation station;
	private Card creditCard;

	public CreditScreenGUI(MainGUI m, CentralStationLogic l) {
		mainGUI = m;
		logic = l;
		creditPageFrame = new JFrame("The LocalMarketplace Self-Checkout Station");
		creditPagePanel = new JPanel();

		CardIssuer bank = new CardIssuer("Scotia Bank", 3);
		logic.setupBankDetails(bank);
		this.creditCard = new Card("CREDIT", "123456789", "John", "329", "1234", true, true);
		Calendar expiry = Calendar.getInstance();
		expiry.set(2025, Calendar.JANUARY, 24);
		bank.addCardData("123456789", "John", expiry, "329", 32.00);

		addWidgets();

		creditPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		creditPageFrame.setSize(1000, 1000);
		creditPageFrame.setContentPane(creditPagePanel);
	}

	private void addWidgets() {

		JButton insertButton = new JButton("Insert");
		JButton tapButton = new JButton("Tap");
		JButton swipeButton = new JButton("Swipe");
		JButton goBackButton = new JButton("Go back");

		insertButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logic.selectCardMethod(CardMethods.INSERT);
				try {
					BigDecimal paid = logic.cartLogic.getBalanceOwed();
					CardData cd = logic.hardware.getCardReader().insert(creditCard, "1234");
					if (cd != null) {
						logic.receiptPrintingController.addAmountPaid(PaymentMethods.CREDIT, paid);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		tapButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logic.selectCardMethod(CardMethods.TAP);
				try {
					BigDecimal paid = logic.cartLogic.getBalanceOwed();
					CardData cd = logic.hardware.getCardReader().tap(creditCard);
					if (cd != null) {
						logic.receiptPrintingController.addAmountPaid(PaymentMethods.CREDIT, paid);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		swipeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logic.selectCardMethod(CardMethods.SWIPE);
				try {
					BigDecimal paid = logic.cartLogic.getBalanceOwed();
					CardData cd = logic.hardware.getCardReader().tap(creditCard);
					if (cd != null) {
						logic.receiptPrintingController.addAmountPaid(PaymentMethods.CREDIT, paid);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		goBackButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				creditPageFrame.dispose();
				mainGUI.getCardLayout().show(mainGUI.getMainPanel(), "payment");
			}
		});

		creditPagePanel.setLayout(new GridLayout(2, 2));
		creditPagePanel.add(insertButton);
		creditPagePanel.add(tapButton);
		creditPagePanel.add(swipeButton);
		creditPagePanel.add(goBackButton);
	}

	public JPanel getPanel() {
		return creditPagePanel;
	}

}
