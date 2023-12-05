package com.thelocalmarketplace.software.controllers.item;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Mass.MassDifference;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import ca.ucalgary.seng300.simulation.SimulationException;

/**
 * Represents the software controller for adding an item via PLU code
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

public class AddPLUCodedItemController extends AbstractLogicDependant {

	private boolean isAwaitingPLUMeasurement = false;
	private PriceLookUpCode priceLookUpCode;
	
	/**
	 * AddItemByPLUController Constructor
	 * @param logic reference to the logic instance
	 * @throws NullPointerException when logic is null
	 */
	public AddPLUCodedItemController(CentralStationLogic logic) throws NullPointerException {
		super(logic);
	}
	
	/**
	 * Adds a new price lookup code
	 * If a weight discrepancy is detected, then station is blocked
	 * @param the price lookup code entered
	 */
	public void addPLUCode(PriceLookUpCode priceLookUpCode) {
		if (priceLookUpCode == null) {
			throw new NullPointerException("Price lookup code is null");
		}
		else if (!this.logic.isSessionStarted()) {
			throw new InvalidStateSimulationException("The session has not been started");
		}
		else if (this.logic.stateLogic.inState(States.BLOCKED)) {
			throw new InvalidStateSimulationException("Station is blocked");
		}
		else if (!ProductDatabases.PLU_PRODUCT_DATABASE.containsKey(priceLookUpCode)) {
			throw new InvalidStateSimulationException("Price-lookup code not registered to product database");
		}
		
		this.priceLookUpCode = priceLookUpCode;
		
		// blocks the station
		this.logic.stateLogic.gotoState(States.BLOCKED);
		isAwaitingPLUMeasurement = true;
		if(logic.getMainGUI() != null) logic.getMainGUI().getAddItemScreen().getErrorTextArea().setText("Please place PLU item ("+priceLookUpCode+") in the bagging area.");
		Mass currentWeight = logic.weightLogic.getActualWeight();
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	if(logic.weightLogic.getActualWeight().equals(currentWeight))
		            	logic.getMainGUI().getAddItemScreen().getErrorTextArea().setText("Weight discrepancy detected. Place PLU ("+priceLookUpCode+") in bagging area.");
		            }
		        }, 
		        5000 
		);
	}

	
	/**
	 * is called when the mass of the scale changes and isAwaitingPLUMeasurement == true
	 * determines the mass and price of the item, calls the method to add it to cart, and updates the expected weight
	 * @param prevMass expected mass before adding the item
	 * @param newMass expected mass after adding the item
	 */
	public void addPLUCodedItem(Mass prevMass, Mass newMass) {
		isAwaitingPLUMeasurement = false;
		
		MassDifference difference = prevMass.difference(newMass);
		Mass itemMass = difference.abs();
		
		PLUCodedProduct product = ProductDatabases.PLU_PRODUCT_DATABASE.get(priceLookUpCode);
		
		if (!ProductDatabases.INVENTORY.containsKey(product) || ProductDatabases.INVENTORY.get(product) < 1) {
			throw new InvalidStateSimulationException("No items of this type are in inventory");
		}
		
		PLUCodedItem item = new PLUCodedItem(priceLookUpCode, itemMass);
		
		double itemPrice = getPLUCodedItemPrice(item);
		this.logic.cartLogic.addPLUCodedItemToCart(item, itemPrice);
		if(logic.getMainGUI() != null) logic.getMainGUI().getAddItemScreen().getWeightTextArea().setText("Weight of added PLU Item ("+priceLookUpCode+"): "+itemMass.inGrams()+"g");
		
		// expected weight is updated so that checkWeightDiscrepancy() in WeightLogic does not incorrectly return true
		this.logic.weightLogic.addExpectedWeight(itemMass);
	}
	
	/**
	 * get item price by multiplying price per kg by the weight in kg
	 * @param item - PLU coded item to get price of
	 * @return - price of the PLU coded item
	 */
	public double getPLUCodedItemPrice(PLUCodedItem item) {
		PLUCodedProduct product = ProductDatabases.PLU_PRODUCT_DATABASE.get(item.getPLUCode());
		double itemPrice = (product.getPrice()*item.getMass().inGrams().doubleValue()) / 1000;
	    BigDecimal bd = BigDecimal.valueOf(itemPrice);
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
	    itemPrice = bd.doubleValue();
	    System.out.println(itemPrice);
		return itemPrice;
	}

	/**
	 * Checks whether or not the system is awaiting a scale measurement, so that when a PLU is entered,
	 * it uses the weight on the scale when theMassOnTheScaleHasChanged is called to calculate the 
	 * cost of the PLU coded item.
	 * @return whether or not the system is awaiting a scale measurement
	 */
	public boolean getAwaitingPLUMeasurement() {
		return isAwaitingPLUMeasurement;
	}
	
}
