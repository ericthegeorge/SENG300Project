package com.thelocalmarketplace.software.logic;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Map.Entry;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Mass.MassDifference;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;

/**
 * Logic class for the remove item use case functionality. Adapted from AddBarcodedItemTests
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

public class RemoveItemLogic extends AbstractLogicDependant{
	
	private boolean isAwaitingPLURemoval = false;
	private PriceLookUpCode priceLookUpCode;
	private Map<Item, Integer> cartItems = this.logic.cartLogic.getCart();
	private PLUCodedItem pluItemForRemoval;
	
	
	/**
	 * Base constructor
	 * @param logic Reference to central station logic
	 * @throws NullPointerException If logic is null
	 */
	public RemoveItemLogic(CentralStationLogic logic) throws NullPointerException {
		super(logic);
	}
	
	/**
	 * Removes a barcoded item from the cart and updates states and expected weight.
	 * @param item - barcoded item to be removed
	 * @throws NullPointerException
	 */
	public void removeBarcodedItem(BarcodedItem item) throws NullPointerException{
    	if (item == null) {
            throw new NullPointerException("Barcode is null");
        }
    	
    	else if (!this.logic.isSessionStarted()) {
    		throw new InvalidStateSimulationException("The session has not been started");
    	}
    	
    	
    	// When method is used to resolve weight discrepancies
    	else if (this.logic.stateLogic.inState(States.BLOCKED)) {
	    	this.logic.cartLogic.removeProductFromCart(item);
	    	this.logic.weightLogic.removeExpectedWeight(item.getBarcode());
	    	System.out.println("Item removed from cart.");
	    	logic.weightLogic.handleWeightDiscrepancy();
    	}
    	
    	// When method is used to remove unwanted items (without triggering a weight discrepancy
    	else {
    		this.logic.cartLogic.removeProductFromCart(item);
	    	this.logic.weightLogic.removeExpectedWeight(item.getBarcode());
	    	this.logic.stateLogic.gotoState(States.BLOCKED);
			if(logic.getMainGUI() != null) logic.getMainGUI().getAddItemScreen().getErrorTextArea().setText("Please remove the item from the bagging area");
	    	logic.weightLogic.delayedDiscrepancyCheck(5000);
    	}
	}
	
	/**
	 * checks that the cart has an item of a desired PLU code that the customer wants to remove
	 * then sets the boolean isAwaitingPLURemoval to true, meaning removePLUCodedItem will be called
	 * when the mass on the scale changes.
	 * @param code - the PLU code of the item the customer wants to remove from the cart
	 */
	public void checkCartForPLUCodedItemToRemove(PriceLookUpCode code) {
		this.priceLookUpCode = code;
		
		if (code == null) {
			throw new NullPointerException("Price lookup code is null");
		}
		else if (!this.logic.isSessionStarted()) {
			throw new InvalidStateSimulationException("The session has not been started");
		}
		else if (!ProductDatabases.PLU_PRODUCT_DATABASE.containsKey(priceLookUpCode)) {
			throw new InvalidStateSimulationException("Price-lookup code not registered to product database");
		}
		
		for (Entry<Item, Integer> entry : cartItems.entrySet()) {
            Item item = entry.getKey();
            
            if (item instanceof PLUCodedItem) {
            	PLUCodedItem barcodedItem = (PLUCodedItem) item;
            	if (priceLookUpCode == barcodedItem.getPLUCode()) {
            		this.isAwaitingPLURemoval = true;
            		break;
            	}
            }
		}
	}
	
	/**
	 * is called when the mass of the scale changes and isAwaitingPLURemoval == true
	 * determines the mass removed, checks if it matches a specific item of the PLU code in the cart
	 * then, if so, calls the method to remove it from cart, and updates the expected weight
	 * @param prevMass expected mass before removing the item
	 * @param newMass expected mass after removing the item
	 */
	public void removePLUCodedItem(Mass prevMass, Mass newMass) {
		isAwaitingPLURemoval = false;	// makes sure the system is no longer waiting for a PLU measurement on the scale for removal
		pluItemForRemoval = null;	// resets pluItemForRemoval in case the function has already been called and it is still defined
		
		MassDifference difference = prevMass.difference(newMass);
		Mass itemMass = difference.abs();
		
		for (Entry<Item, Integer> entry : cartItems.entrySet()) {
            Item item = entry.getKey();
            
            if (item instanceof PLUCodedItem) {
            	PLUCodedItem PLUCodedItem = (PLUCodedItem) item;
            	if (priceLookUpCode == PLUCodedItem.getPLUCode() && itemMass.equals(PLUCodedItem.getMass())) {
            		pluItemForRemoval = PLUCodedItem;
            		break;
            	}
            }
		}
		
		// if the person removes a weight from the scale, but there is no item in the cart that matches the weight removed
		if (pluItemForRemoval == null) {
			throw new InvalidStateSimulationException("Item of the weight removed does not exist in cart");
		}
		
		// When method is used to resolve weight discrepancies
		else if (this.logic.stateLogic.inState(States.BLOCKED)) {
	    	this.logic.cartLogic.removeProductFromCart(pluItemForRemoval);
	    	this.logic.weightLogic.removeExpectedWeight(pluItemForRemoval);
	    	System.out.println("Item removed from cart.");
	    	logic.weightLogic.handleWeightDiscrepancy();
    	}
    	
    	// When method is used to remove unwanted items (without triggering a weight discrepancy)
    	else {
    		this.logic.cartLogic.removeProductFromCart(pluItemForRemoval);
	    	this.logic.weightLogic.removeExpectedWeight(pluItemForRemoval);
	    	this.logic.stateLogic.gotoState(States.BLOCKED);
	    	System.out.println("Item removed from cart. Please remove the item from the bagging area");
	    	logic.weightLogic.delayedDiscrepancyCheck(5000);
    	}
	}
	
	/**
	 * Checks whether or not the system is awaiting a scale measurement, to determine the PLU coded item to remove from cart
	 * @return whether or not the system is awaiting a scale measurement
	 */
	public boolean getAwaitingPLURemoval() {
		return isAwaitingPLURemoval;
	}
	
	// old method
	/**
	 * Removes a PLU coded item from the cart and updates states and expected weight
	 * @param item - PLU coded item to be removed
	 * @throws NullPointerException
	 */
	
	/*public void removePLUCodedItem(PLUCodedItem item) throws NullPointerException{
    	if (item == null) {
            throw new NullPointerException("PLU code is null");
        }
    	
    	else if (!this.logic.isSessionStarted()) {
    		throw new InvalidStateSimulationException("The session has not been started");
    	}
    	
    	
    	
    	// When method is used to resolve weight discrepancies
    	else if (this.logic.stateLogic.inState(States.BLOCKED)) {
	    	this.logic.cartLogic.removeProductFromCart(item);
	    	this.logic.weightLogic.removeExpectedWeight(item);
	    	System.out.println("Item removed from cart.");
	    	logic.weightLogic.handleWeightDiscrepancy();
    	}
    	
    	// When method is used to remove unwanted items (without triggering a weight discrepancy
    	else {
    		this.logic.cartLogic.removeProductFromCart(item);
	    	this.logic.weightLogic.removeExpectedWeight(item);
	    	this.logic.stateLogic.gotoState(States.BLOCKED);
	    	System.out.println("Item removed from cart. Please remove the item from the bagging area");
    	}
	}*/
}
