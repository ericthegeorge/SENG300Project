package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.software.controllers.pay.CardReaderController;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.CentralStationLogic.CardMethods;
import com.thelocalmarketplace.software.logic.CentralStationLogic.PaymentMethods;

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

public class DebitScreenGUI {
    private JFrame debitPageFrame;
    private JPanel debitPagePanel;
    private CardReaderController cardReaderController;
    private CentralStationLogic logic;
    private MainGUI mainGUI;
    
    public DebitScreenGUI(MainGUI m, CentralStationLogic l) {
    	mainGUI = m;
    	logic = l;
        debitPageFrame = new JFrame("The LocalMarketplace Self-Checkout Station");
        debitPagePanel = new JPanel();

        addWidgets();

        debitPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        debitPageFrame.setSize(1000, 1000); 
        debitPageFrame.setContentPane(debitPagePanel);
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
            	mainGUI.getCardLayout().show(mainGUI.getMainPanel(), "payment");
            }
        });


        debitPagePanel.setLayout(new GridLayout(2, 2)); 
        debitPagePanel.add(insertButton);
        debitPagePanel.add(tapButton);
        debitPagePanel.add(swipeButton);
        debitPagePanel.add(goBackButton);
    }

	public JPanel getPanel() {
		return debitPagePanel;
	}
}
