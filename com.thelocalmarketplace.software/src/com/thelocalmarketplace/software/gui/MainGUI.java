package com.thelocalmarketplace.software.gui;

import javax.swing.*;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.tdc.banknote.Banknote;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.hardware.SelfCheckoutStationSilver;
import com.thelocalmarketplace.hardware.external.CardIssuer;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.gui.CompletionScreenGUI;
import com.thelocalmarketplace.software.gui.StartScreenGUI;
import com.thelocalmarketplace.software.logic.CentralStationLogic;

import powerutility.PowerGrid;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;

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

public class MainGUI {
    private JFrame mainFrame;
    private JFrame attendantFrame;
    private JPanel mainPanel;
    private JPanel attendantPanel;
    private CardLayout mainCardLayout;
    private CardLayout attendantCardLayout;
	public static Card membershipCard;
    
    private ArrayList<Item> itemsInCart = new ArrayList<Item>();
    private ArrayList<Item> itemsInBaggingArea = new ArrayList<Item>();

	private StartScreenGUI startScreen;
    private CompletionScreenGUI completionScreen;
    private AttendantStationGUI attendantScreen;
    private CreditScreenGUI creditScreen;
    private DebitScreenGUI debitScreen;
    private VisualCatalogueScreenGUI keyboardScreen;
    public PaymentScreenGUI paymentScreen;
    private AddItemGUI addItemScreen;
    private CashScreenGUI cashScreen;
    
	private AbstractSelfCheckoutStation station;
    private CentralStationLogic logic;


    
    public static void main(String[] args) {
    	restartGUI();
    }
    
    public static void restartGUI() {
    	AbstractSelfCheckoutStation.resetConfigurationToDefaults();
        configureCurrency();
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
                membershipCard = new Card("Membership", "111222333", "Demo Member", null, null, false, false);
            }
        });
    }

    public MainGUI(AbstractSelfCheckoutStation s) {
        addItemsToCart();
        
    	station = s;
    	logic = new CentralStationLogic(station);
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
        cashScreen = new CashScreenGUI(this, logic);
        mainPanel.add(startScreen.getPanel(), "start");
        mainPanel.add(completionScreen.getPanel(), "completion");
        mainPanel.add(creditScreen.getPanel(), "credit");
        mainPanel.add(debitScreen.getPanel(), "debit");
        mainPanel.add(keyboardScreen.getPanel(), "keyboard");
        mainPanel.add(paymentScreen.getPanel(), "payment");
        mainPanel.add(addItemScreen.getPanel(), "addItem");
        mainPanel.add(cashScreen.getPanel(), "cash");
        
        attendantFrame = new JFrame("Attendant GUI");
        attendantPanel = new JPanel();
        attendantCardLayout = new CardLayout();
        attendantPanel.setLayout(attendantCardLayout);
        
        attendantScreen = new AttendantStationGUI(this, logic);
        attendantPanel.add(attendantScreen.getPanel(), "attendant");
        
    	logic.setGUI(this);
        
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

	static public void configureCurrency() {
		BigDecimal twoDollar = new BigDecimal(2);
		BigDecimal dollar = new BigDecimal(1);
		BigDecimal halfDollar = new BigDecimal(0.50);
		BigDecimal quarter = new BigDecimal(0.25);
		BigDecimal dime = new BigDecimal(0.10);
		BigDecimal nickel = new BigDecimal(0.05);
		BigDecimal fiveDollar = new BigDecimal(5);
		BigDecimal tenDollar = new BigDecimal(10);
		BigDecimal twentyDollar = new BigDecimal(20);
		BigDecimal fiftyDollar = new BigDecimal(50);
		BigDecimal hundredDollar = new BigDecimal(100);
		BigDecimal[] coinList = new BigDecimal[] {nickel, dime, quarter, halfDollar, dollar, twoDollar};
		BigDecimal[] noteList = new BigDecimal[] {fiveDollar, tenDollar, twentyDollar, fiftyDollar, hundredDollar};
		
		Currency cad = Currency.getInstance("CAD"); 
		
		AbstractSelfCheckoutStation.configureCurrency(cad);
		AbstractSelfCheckoutStation.configureCoinDenominations(coinList);
		AbstractSelfCheckoutStation.configureBanknoteDenominations(noteList);
		AbstractSelfCheckoutStation.configureBanknoteStorageUnitCapacity(10);
		AbstractSelfCheckoutStation.configureCoinStorageUnitCapacity(10);
		AbstractSelfCheckoutStation.configureCoinTrayCapacity(5);
		AbstractSelfCheckoutStation.configureCoinDispenserCapacity(5);
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

	public AddItemGUI getAddItemScreen() {
		return addItemScreen;
	}

	public ArrayList<Item> getItemsInBaggingArea() {
		return itemsInBaggingArea;
	}

	// Getter for paymentScreen
    public PaymentScreenGUI getPaymentScreen() {
        return paymentScreen;
    }

    // Setter for paymentScreen
    public void setPaymentScreen(PaymentScreenGUI paymentScreen) {
        this.paymentScreen = paymentScreen;
    }
    
    public JFrame getMainFrame() {
		return mainFrame;
	}

	public JFrame getAttendantFrame() {
		return attendantFrame;
	}

}
