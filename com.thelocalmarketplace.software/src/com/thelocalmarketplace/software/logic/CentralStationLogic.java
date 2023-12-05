package com.thelocalmarketplace.software.logic;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jjjwelectronics.Mass;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.external.CardIssuer;
import com.thelocalmarketplace.software.controllers.*;
import com.thelocalmarketplace.software.controllers.pay.CardReaderController;
import com.thelocalmarketplace.software.controllers.pay.cash.BanknoteDispenserController;
import com.thelocalmarketplace.software.controllers.pay.cash.CashPaymentController;
import com.thelocalmarketplace.software.controllers.pay.cash.CoinDispenserController;
import com.thelocalmarketplace.software.controllers.pay.cash.CoinPaymentController;
import com.thelocalmarketplace.software.gui.AttendantStationGUI;
import com.thelocalmarketplace.software.gui.MainGUI;
//import com.thelocalmarketplace.software.gui.AttendantStationGUI.StationObject;
import com.thelocalmarketplace.software.logic.StateLogic.States;
import com.thelocalmarketplace.software.controllers.item.*;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import ca.ucalgary.seng300.simulation.SimulationException;
import powerutility.PowerGrid;

/**
 * Represents the central session logic for the control software
 * 
 * Codebase originated from Connell's project
 * Combined idea of having session handling part of central logic unit from Braden's project
 * 
 * @author Christopher Lo (30113400)
 * added CardMethods enumeration and changed method names to fit Insert/Tap Use Cases
 * -----------------------------------
 * @author Alan Yong (30105707)
 * @author Andrew Matti (30182547)
 * @author Olivia Crosby (30099224)
 * @author Rico Manalastas (30164386)
 * @author Shanza Raza (30192765)
 * @author Danny Ly (30127144)
 * @author Maheen Nizmani (30172615)
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
public class CentralStationLogic {	
	/*
	 * Enumeration of possible payment methods
	 */
	public enum PaymentMethods {
		NONE, // Default
		CREDIT,
		DEBIT,
		CASH
	}	
	public enum CardMethods {
		NONE, // Default
		TAP,
		INSERT,
		SWIPE
	}

	public JFrame frame = new JFrame();	
	
	public CentralStationLogic logic;
	
	/**
	 * Reference to AttendantStationGUI
	 */
	public AttendantStationGUI attendantStationGUI;
	/**
	 * Reference to physical hardware
	 */
	public AbstractSelfCheckoutStation hardware;
	/**
	 * Instance of the cart logic
	 */
	public CartLogic cartLogic;
	/**
	 * Instance of the currency logic for coin denominations
	 */
	public CurrencyLogic coinCurrencyLogic;
	/**
	 * Instance of the currency logic for banknote denominations
	 */
	public CurrencyLogic banknoteCurrencyLogic;
	/**
	 * Instance of the controller that handles payment with coin
	 */
	public CoinPaymentController coinPaymentController;
	/**
	 * Instance of the controller that handles payment with cash
	 */
	public CashPaymentController cashPaymentController;
	/**
	 * Instances of the controllers that handle coin dispensing indexed by their corresponding coin denomination
	 */
	public Map<BigDecimal, CoinDispenserController> coinDispenserControllers = new HashMap<>();
	/**
	 * Instances of the controllers that handle banknote dispensing indexed by their corresponding banknote denomination
	 */
	public Map<BigDecimal, BanknoteDispenserController> banknoteDispenserControllers = new HashMap<>();
	/**
	 * Instance of the controller that handles adding barcoded product
	 */
	public AddBarcodedItemController addBarcodedProductController;
	/**
	 * Instance of the controller that handles adding PLU coded product
	 */
	public AddPLUCodedItemController addPLUCodedProductController;
	/** 
	 * Instance of weight logic 
	 */
	public WeightLogic weightLogic;
	/**
	 * Instance of add bags logic 
	 */
	public AddBagsLogic addBagsLogic;
	/*
	 * Instance of logic that handles item removal
	 */
	public RemoveItemLogic removeItemLogic;
	/**
	 * Instance of logic that handles purchasing bags
	 */
	public PurchaseBagsLogic purchaseBagsLogic;
	/**
	 * Instance of the controller that handles weight discrepancy detected
	 */
	public WeightDiscrepancyController weightDiscrepancyController;
	/**
	 * Instance of controller that handles swiping a card
	 */
	public CardReaderController cardReaderController;
	/**
	 * Instance of the controller that handles receipt printing
	 */
	public ReceiptPrintingController receiptPrintingController;
	/**
	 * Instance of logic for attendant
	 */
	public AttendantLogic attendantLogic;
	/**
	 * Instance of signaling attendant logic
	 */
	public SignalAttendantLogic signalAttendantLogic;
	/**
	 * Instance of logic for card payment via swipe
	 */
	public CardPaymentLogic cardPaymentLogic;
	/**
	 * Instance of logic for states
	 */
	public StateLogic stateLogic;
	/**
	 * Current selected payment method
	 */
	private PaymentMethods paymentMethod;
	/**
	 * Current selected payment method
	 */
	private CardMethods cardMethod;
	/**
     * Instance of logic for selecting a language
     */
    public SelectLanguageLogic selectLanguageLogic;
    /**
     * Instance of logic for handling memberships
     */
    public MembershipLogic membershipLogic;
	/**
	 * Tracks if the customer session is active
	 */
	private boolean sessionStarted;
	private boolean bypassIssuePrediction;
	private MainGUI mainGUI;
	/**
	 * Maximum allowable bag mass in grams (can be configured)
	 */
	private BigDecimal maximumBagMass = new BigDecimal(1000); //default

	
	//________________________________________________
	
    private int stationNumber;
    private Map<Character, Color> circleColors; // Map to store colors for circles
    private boolean isEnabled;
    private double weight;

    private void initializeCircleColors() {
        // Initialize default colors for circles
        circleColors.put('I', Color.YELLOW);
        circleColors.put('P', Color.YELLOW);
        circleColors.put('C', Color.YELLOW);
        circleColors.put('B', Color.YELLOW);
        circleColors.put('S', Color.YELLOW);
        circleColors.put('H', Color.YELLOW);
    }

    public int getStationNumber() {
        return stationNumber;
    }

    public Color getCircleColor(char label) {
        return circleColors.get(label);
    }

    public void setCircleColor(char label, Color color) {
        circleColors.put(label, color);
    }
    
    public void setCircleColorYellow(char label) {
        setCircleColor(label, Color.YELLOW);
    }

    public void setCircleColorRed(char label) {
        setCircleColor(label, Color.RED);
    }

    public void setCircleColorGreen(char label) {
        setCircleColor(label, Color.GREEN);
    }




    @Override
    public String toString() {
        return "Station #" + stationNumber;
    }

    // Add a getter and setter for isEnabled
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    //______________________________________________
    
	
	/**
	 * Base constructor for a new CentralStationLogic instance
	 * @throws NullPointerException If hardware is null
	 */
	public CentralStationLogic(AbstractSelfCheckoutStation hardware) throws NullPointerException {
		if (hardware == null) {
			throw new NullPointerException("Hardware");
		}
		this.hardware = hardware;

		this.sessionStarted = false;
		this.paymentMethod = PaymentMethods.NONE;
		
		
		// Initialize SelectLanguageLogic
        this.selectLanguageLogic = new SelectLanguageLogic(this, "English");
        
		// Reference to logic objects
		this.cartLogic = new CartLogic(this);
		this.weightLogic = new WeightLogic(this);
		this.stateLogic = new StateLogic(this);
		this.purchaseBagsLogic = new PurchaseBagsLogic(this);

		// Instantiate each controller
		this.coinPaymentController = new CoinPaymentController(this);
		this.cashPaymentController = new CashPaymentController(this);
		this.addBarcodedProductController = new AddBarcodedItemController(this);
		this.addPLUCodedProductController = new AddPLUCodedItemController(this);
		this.weightDiscrepancyController = new WeightDiscrepancyController(this);
		this.cardReaderController = new CardReaderController(this);
		this.receiptPrintingController = new ReceiptPrintingController(this);
		this.attendantLogic = new AttendantLogic(this);
		
		this.addBagsLogic = new AddBagsLogic(this);
		this.removeItemLogic = new RemoveItemLogic(this);
		this.membershipLogic = new MembershipLogic(this);
		
		this.coinCurrencyLogic = new CurrencyLogic(this.hardware.getCoinDenominations());
		this.banknoteCurrencyLogic = new CurrencyLogic(this.hardware.getBanknoteDenominations());
		
		//Initialize signalAttendantLogic
		this.signalAttendantLogic = new SignalAttendantLogic(this);
		
		this.setupCoinDispenserControllers(this.coinCurrencyLogic.getDenominationsAsList());
		this.setupBanknoteDispenserControllers(this.banknoteCurrencyLogic.getDenominationsAsList());
	
		//_________________________________---
		
		//this.stationNumber = stationNumber;
		this.circleColors = new HashMap<>();
        initializeCircleColors();
		
		//______________________________________---
		initializeMembershipDatabase();
	}
	
	/**
	 * Gets the current selected payment method
	 * @return the payment method
	 */
	public PaymentMethods getSelectedPaymentMethod() {
		return this.paymentMethod;
	}
	public CardMethods getSelectedCardPaymentMethod() {
		return this.cardMethod;
	}
	
	/**
	 * Sets the desired payment method for the customer
	 * @param method Is the payment method to use
	 */
	public void selectPaymentMethod(PaymentMethods method) {
		this.paymentMethod = method;
	}	
	public void selectCardMethod(CardMethods method) {
		this.cardMethod = method;
	}

	/**
	 * Helper method to setup coin dispenser controllers
	 * @param denominations Is the list of coin denominations supported by the hardware
	 */
	private void setupCoinDispenserControllers(List<BigDecimal> denominations) {
		for (BigDecimal d : denominations) {
			this.coinDispenserControllers.put(d, new CoinDispenserController(this, d));
		}
	}
	/**
	 * helper method to setup the bank details
	 * @param bank is the details of the customer's bank
	 */
	public void setupBankDetails(CardIssuer bank) {
		this.cardPaymentLogic = new CardPaymentLogic(this, bank);
	}
	/**
	 * Helper method to setup banknote dispenser controllers
	 * @param denominations Is the list of coin denominations supported by the hardware
	 */
	private void setupBanknoteDispenserControllers(List<BigDecimal> denominations) {
		for (BigDecimal d : denominations) {
			this.banknoteDispenserControllers.put(d, new BanknoteDispenserController(this, d));
		}
	}
	
	/**
	 * Gets all of the available coins in each coin dispenser
	 * @return A mapping of coin counts indexed by their denomination
	 */
	public Map<BigDecimal, Integer> getAvailableCoinsInDispensers() {
		Map<BigDecimal, Integer> available = new HashMap<>();
		
		// Assume all coins in each dispenser are of the same denomination (they should be)
		for (Entry<BigDecimal, CoinDispenserController> e : this.coinDispenserControllers.entrySet()) {
			final BigDecimal d = e.getKey();
			final CoinDispenserController c = e.getValue();
			
			available.put(d, c.getAvailableChange().size());
		}
		
		return available;
	}
	
	/**
	 * Gets all of the available banknotes tracked in each banknote dispenser
	 * @return A mapping of banknote counts indexed by their denomination
	 */
	public Map<BigDecimal, Integer> getAvailableBanknotesInDispensers() {
	 	Map<BigDecimal, Integer> available = new HashMap<>();

	    for (Entry<BigDecimal, BanknoteDispenserController> entry : this.banknoteDispenserControllers.entrySet()) {
	        final BigDecimal denomination = entry.getKey();
	        final BanknoteDispenserController controller = entry.getValue();
	        
	        available.put(denomination, controller.getAvailableBanknotes().size());
	    }
	    
	    return available;
	}
	
	private void initializeMembershipDatabase() {
		MembershipDatabase.NUMBER_TO_CARDHOLDER.put("111222333", "Demo Member");
	}

	/**
	 * Checks if the session is started
	 * @return True if the session is active; false otherwise
	 */
	public boolean isSessionStarted() {
		return this.sessionStarted;
	}
	
	/**
	 * Marks the current self checkout session as active
	 * @throws SimulationException If the session is already active
	 */
	public void startSession() throws SimulationException {
		if (this.isSessionStarted()) {
			throw new InvalidStateSimulationException("Session already started");
		}
		if(issuePredicted()) {
			this.stateLogic.gotoState(States.BLOCKED);
		} else {
			System.out.println("Session started");
			this.stateLogic.gotoState(States.NORMAL);
			this.sessionStarted = true;
		}
	}

	/**
	 * Marks the current self checkout session as inactive
	 */
	public void stopSession() {
		System.out.println("Session ended");
		this.sessionStarted = false;
		
		//the session can be ended first as the issue prediction 
		//is not relevant to the current customer
		if(issuePredicted()) {
			this.stateLogic.gotoState(States.BLOCKED);
		} 
	}
	
	public boolean issuePredicted() {
		if (bypassIssuePrediction) return false;
		boolean issueExists = false;

		boolean lowInk = receiptPrintingController.getLowInk();
		if (lowInk) {
			if(attendantStationGUI != null) attendantStationGUI.logic.setCircleColorYellow('I');
			issueExists = true;
		}
		boolean lowPaper = receiptPrintingController.getLowPaper();
		if (lowPaper) {
			if(attendantStationGUI != null) attendantStationGUI.logic.setCircleColorYellow('P');
			issueExists = true;
		}

		//Banknote dispenser checks
		for (Entry<BigDecimal, BanknoteDispenserController> entry : this.banknoteDispenserControllers.entrySet()) {
			final BanknoteDispenserController controller = entry.getValue();
			if(controller.shouldWarnEmpty()) {
				if(attendantStationGUI != null) attendantStationGUI.logic.setCircleColorRed('B');
				issueExists = true;
			}
			if(controller.shouldWarnFull()) {
				if(attendantStationGUI != null) attendantStationGUI.logic.setCircleColorGreen('B');
				issueExists = true;
			}
		}

		//Coin dispenser checks
		for (Entry<BigDecimal, CoinDispenserController> entry : this.coinDispenserControllers.entrySet()) {
			final CoinDispenserController controller = entry.getValue();
			if(controller.shouldWarnEmpty()) {
				if(attendantStationGUI != null) attendantStationGUI.logic.setCircleColorRed('C');
				issueExists = true;
			}
			if(controller.shouldWarnFull()) {
				if(attendantStationGUI != null) attendantStationGUI.logic.setCircleColorGreen('C');
				issueExists = true;
			}
			else {
				if(attendantStationGUI != null) {
					attendantStationGUI.logic.setCircleColorYellow('I');
					attendantStationGUI.logic.setCircleColorYellow('P');
					attendantStationGUI.logic.setCircleColorYellow('S');
					attendantStationGUI.logic.setCircleColorYellow('C');
					attendantStationGUI.logic.setCircleColorYellow('H');
					attendantStationGUI.logic.setCircleColorYellow('B');
				}
			}

		}
		return issueExists;
	}
	
	/**Toggles checking of issuePrediction, allowing legacy test cases to perform
	 * @param bypassIssuePrediction
	 */
	public void setBypassIssuePrediction(boolean bypassIssuePrediction) {
		this.bypassIssuePrediction = bypassIssuePrediction;
	}
	
	public void setGUI(MainGUI g) {
		attendantStationGUI = g.getAttendantScreen();
		mainGUI = g;
	}
	public MainGUI getMainGUI() {
		return mainGUI;
	}
	/**
	 * @return the maximumBagMass
	 */
	public BigDecimal getMaximumBagMass() {
		return maximumBagMass;
	}
	/**
	 * @param maximumBagMass the maximumBagMass to set
	 */
	public void setMaximumBagMass(BigDecimal maximumBagMass) {
		this.maximumBagMass = maximumBagMass;
	}
}
