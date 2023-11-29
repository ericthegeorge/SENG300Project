package com.thelocalmarketplace.software.controllers.item;

import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import ca.ucalgary.seng300.simulation.SimulationException;

/**
 * Represents the software controller for adding an item via PLU code
 * 
 * @author Ian Beler (30174903)
 * --------------------------------
 * @author 
 * @author 
 * @author 
 * @author 
 * @author 
 * @author Maheen Nizmani (30172615)
 * @author 
 * @author 
 * @author
 * @author 
 * @author 
 * @author 
 * @author 
 * @author 
 * @author 
 * @author
 * @author 
 * @author 
 * @author
 */

public class AddPLUCodedItemController extends AbstractLogicDependant {

	private boolean isAwaitingPLUMeasurement = false;
	
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
		
		//this.logic.cartLogic.addPLUCodedProductToCart(priceLookUpCode);
		//this.logic.weightLogic.addExpectedWeight(priceLookUpCode, );
		
		// blocks the station
		this.logic.stateLogic.gotoState(States.BLOCKED);
		isAwaitingPLUMeasurement = true;
		
		System.out.println("Please place item on scale to determine price");
	}

	/**
	 * Checks whether or not the system is awaiting a scale measurement, so that when a PLU is entered,
	 * it uses the weight on the scale when theMassOnTheScaleHasChanged is called to calculate the 
	 * cost of the PLU coded item.
	 * @return whether or not the system is awaiting a scale measurement
	 */
	public boolean awaitingPLUMeasurement() {
		return isAwaitingPLUMeasurement;
	}
	
}
