package com.thelocalmarketplace.software.logic;

import com.jjjwelectronics.Mass;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;

/**
 * Add Own Bags (Logic)
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

public class AddBagsLogic extends AbstractLogicDependant {
	
	/** Mass limit for bags when customer is adding own bags */
	public Mass bagWeightLimit = new Mass((double)20);
	
	/** tracks weather or not the attendant has approved the current bagging area*/
	public boolean approvedBagging;
	
	public AddBagsLogic(CentralStationLogic logic) throws NullPointerException {
		super(logic);
	}
	
	/**Handles start of bagging state
	 * Notifies customer to place bags on scale
	 * Enables bagging area and disables scanners 
	 * @throws InvalidStateSimulationException if called when CentralStationLogic is not in ADDINGBAGS state 
	 * @throws InvalidStateSimulationException if session is not started */
	public void startAddBags() {
		//TODO GUI: please place bags on scale
		if (!logic.isSessionStarted()) throw new InvalidStateSimulationException("Session has not started");
		
		this.logic.stateLogic.gotoState(States.ADDBAGS);
		
		System.out.println("Please place bags on the scale");
		
	}
	
	
	/**When user has finished adding their bags to the scale
	 * Requires attendant verification if bags are too heavy (indicated by this.approvedBagging = true)
	 * Sets expected weight to the current weight of the bags on the scale 
	 * @throws Exception - when bags are too heavy 
	 * @throws InvalidStateSimulationException if called when CentralStationLogic is not in ADDINGBAGS state 
	 * @throws InvalidStateSimulationException if session is not started */
	public void endAddBags() {
		if (!logic.isSessionStarted()) throw new InvalidStateSimulationException("Session has not started");
		if (!logic.stateLogic.inState(States.ADDBAGS)) throw new InvalidStateSimulationException("Cannot end ADDBAGS state when not in ADDBAGS state");
		
		if (logic.weightLogic.getTotalBagMass().compareTo(bagWeightLimit) < 0 || this.approvedBagging) {
			// If bag weight is under the allowed weight
			this.logic.weightLogic.overrideDiscrepancy();
			this.approvedBagging = false;
			this.logic.attendantLogic.setBaggingDiscrepency(false);
			
			this.logic.stateLogic.gotoState(States.NORMAL);
		} else {
			//bags are too heavy 
			//TODO GUI: display waiting for attendant approval
			this.logic.attendantLogic.baggingDiscrepencyDetected();
		}
	}

}
