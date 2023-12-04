package com.thelocalmarketplace.software.gui;

import javax.swing.*;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.hardware.SelfCheckoutStationSilver;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.gui.CompletionScreenGUI;
import com.thelocalmarketplace.software.gui.StartScreenGUI;
import com.thelocalmarketplace.software.logic.CentralStationLogic;

import powerutility.PowerGrid;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainGUI {
    private JFrame mainFrame;
    private JFrame attendantFrame;
    private JPanel mainPanel;
    private JPanel attendantPanel;
    private CardLayout mainCardLayout;
    private CardLayout attendantCardLayout;
    
    private ArrayList<Item> itemsInCart = new ArrayList<Item>();

	private StartScreenGUI startScreen;
    private CompletionScreenGUI completionScreen;
    private AttendantStationGUI attendantScreen;
    private CreditScreenGUI creditScreen;
    private DebitScreenGUI debitScreen;
    private VisualCatalogueScreenGUI keyboardScreen;
    private PaymentScreenGUI paymentScreen;
    private AddItemGUI addItemScreen;
    
	private AbstractSelfCheckoutStation station;
    private CentralStationLogic logic;
    
    public static void main(String[] args) {
    	AbstractSelfCheckoutStation.resetConfigurationToDefaults();
        AbstractSelfCheckoutStation station = new SelfCheckoutStationGold();
		PowerGrid.engageUninterruptiblePowerSource();
		PowerGrid.instance().forcePowerRestore();
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		SimulatedItems.instantiateItems();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainGUI gui = new MainGUI(station);
                gui.logic.setBypassIssuePrediction(true);
            }
        });
        
    }

    public MainGUI(AbstractSelfCheckoutStation s) {
        addItemsToCart();
        
    	station = s;
    	logic = new CentralStationLogic(station);
    	logic.setGUI(this);
        mainFrame = new JFrame("Main GUI");
        mainPanel = new JPanel();
        mainCardLayout = new CardLayout();
        mainPanel.setLayout(mainCardLayout);

        //initializing and adding Screens
        startScreen = new StartScreenGUI(this, logic);
        completionScreen = new CompletionScreenGUI(this, logic);
        creditScreen = new CreditScreenGUI(this, logic);
        debitScreen = new DebitScreenGUI(this, logic);
        keyboardScreen = new VisualCatalogueScreenGUI(this, logic);
        paymentScreen = new PaymentScreenGUI(this, logic);
        addItemScreen = new AddItemGUI(this, logic);
        mainPanel.add(startScreen.getPanel(), "start");
        mainPanel.add(completionScreen.getPanel(), "completion");
        mainPanel.add(creditScreen.getPanel(), "credit");
        mainPanel.add(debitScreen.getPanel(), "debit");
        mainPanel.add(keyboardScreen.getPanel(), "keyboard");
        mainPanel.add(paymentScreen.getPanel(), "payment");
        mainPanel.add(addItemScreen.getPanel(), "addItem");
        
        attendantFrame = new JFrame("Attendant GUI");
        attendantPanel = new JPanel();
        attendantCardLayout = new CardLayout();
        attendantPanel.setLayout(attendantCardLayout);
        
        attendantScreen = new AttendantStationGUI(this, logic);
        attendantPanel.add(attendantScreen.getPanel(), "attendant");
        
        JButton switchButton = new JButton("Switch Screens");
        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainCardLayout.next(mainPanel);
            }
        });
        
        attendantFrame.getContentPane().add(attendantPanel, BorderLayout.CENTER);
        attendantFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        attendantFrame.pack();
        attendantFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        attendantFrame.setVisible(true);
        
        mainFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setVisible(true);
    }
    
    public void addItemsToCart() {
    	int PLUCodedItemsToAdd = 3;
    	for(Item i : SimulatedItems.simulatedItems) {
    		//Only add three of the PLUCoded Items
    		if (i instanceof PLUCodedItem) {
    			if(PLUCodedItemsToAdd > 0) {
    				itemsInCart.add(i);
        			PLUCodedItemsToAdd--;
    			}
    		//Add every barcoded item
    		} else {
        		itemsInCart.add(i);
    		}
    		
    	}
    }
    
	public String getDescriptionOfItem(Item item) {
		if(item instanceof BarcodedItem) {
			BarcodedItem bitem = (BarcodedItem) item;
			BarcodedProduct bproduct = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(bitem.getBarcode());
			return bproduct.getDescription();
		} else if (item instanceof PLUCodedItem) {
			PLUCodedItem pitem = (PLUCodedItem) item;
			PLUCodedProduct pproduct = ProductDatabases.PLU_PRODUCT_DATABASE.get(pitem.getPLUCode());
			return pproduct.getDescription();
		}
		return null;
	}
	
	public Item getItemFromDescription(String description) {
		for (Item i : SimulatedItems.simulatedItems) {
			if (getDescriptionOfItem(i).equals(description)) {
				return i;
			}
		}
		return null;	
	}
    
    public CardLayout getCardLayout() {
		return mainCardLayout;
	}

	public StartScreenGUI getStartScreen() {
		return startScreen;
	}

	public CompletionScreenGUI getCompletionScreen() {
		return completionScreen;
	}

	public AttendantStationGUI getAttendantScreen() {
		return attendantScreen;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public ArrayList<Item> getItemsInCart() {
		return itemsInCart;
	}

	public CreditScreenGUI getCreditScreen() {
		return creditScreen;
	}

	public DebitScreenGUI getDebitScreen() {
		return debitScreen;
	}

	public VisualCatalogueScreenGUI getKeyboardScreen() {
		return keyboardScreen;
	}

	public PaymentScreenGUI getPaymentScreen() {
		return paymentScreen;
	}

	public AddItemGUI getAddItemScreen() {
		return addItemScreen;
	}

}
