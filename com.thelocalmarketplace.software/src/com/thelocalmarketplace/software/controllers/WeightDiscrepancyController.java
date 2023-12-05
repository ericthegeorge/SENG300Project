package com.thelocalmarketplace.software.controllers;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scale.IElectronicScale;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

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
public class WeightDiscrepancyController extends AbstractLogicDependant implements ElectronicScaleListener {
	
	/**
	 * WeightDiscrepancyController Constructor
	 * @param logic A reference to the logic instance
	 * @throws NullPointerException If logic is null
	 **/
	public WeightDiscrepancyController(CentralStationLogic logic) throws NullPointerException {
		super(logic);
		
		// Register self to bagging area scale

		this.logic.hardware.getBaggingArea().register(this);
		//this.logic.hardware.baggingArea.register(this);
	}
	
	/** Triggered when mass on bagging area scale is changed
	 * If a weight discrepancy is detected, system is updated accordingly */
	@Override
	public void theMassOnTheScaleHasChanged(IElectronicScale scale, Mass mass) {
		
		// Weight discrepancies are ignored when in ADDBAGS state
		if (!this.logic.stateLogic.inState(States.ADDBAGS)) {
			
			// calls the method to add a PLU coded item if the station is expecting that to be the next thing on the scale
			if (this.logic.addPLUCodedProductController.getAwaitingPLUMeasurement()) {
				// passes in the previous mass and the new mass as parameters to calculate the mass of the item
				this.logic.addPLUCodedProductController.addPLUCodedItem(this.logic.weightLogic.getActualWeight(), mass);
			}
			// calls the method to remove a PLU coded item if the station is waiting for one (of a specific PLU code) to be removed
			else if (this.logic.removeItemLogic.getAwaitingPLURemoval()) {
				this.logic.removeItemLogic.removePLUCodedItem(this.logic.weightLogic.getActualWeight(), mass);
			}
			this.logic.weightLogic.updateActualWeight(mass);
			this.logic.weightLogic.handleWeightDiscrepancy();
		} else {
			
			// The actual mass now is whatever was on the scale before this change
			Mass one_bag = mass.difference(this.logic.weightLogic.getActualWeight()).abs();
			
			if (mass.compareTo(this.logic.weightLogic.getActualWeight()) > 0) {
				
				// Add the bag to the bag mass
				if (one_bag.inGrams().compareTo(logic.getMaximumBagMass()) >= 1) {
					logic.stateLogic.gotoState(States.BLOCKED);
					logic.getMainGUI().getAddItemScreen().getErrorTextArea().setText("Bag is too heavy. Please wait for an attendant.");
				}
				this.logic.weightLogic.updateTotalBagMass(this.logic.weightLogic.getTotalBagMass().sum(one_bag));
			} else {
				
				// Remove the bag from the bag mass
				this.logic.weightLogic.updateTotalBagMass(this.logic.weightLogic.getTotalBagMass().difference(one_bag).abs());
			}
			
			// Update actual weight of the scale
			this.logic.weightLogic.updateActualWeight(mass);
		}
	}
	
	/** Triggered when actual weight is over expected weight */
	public void notifyOverload() {
		if (logic.getMainGUI() != null) logic.getMainGUI().getAddItemScreen().getErrorTextArea().setText("Weight discrepancy detected");
		System.out.println("Weight discrepancy detected. Please remove item(s)");
	}
	
	/** Triggered when actual weight is under expected weight */
	public void notifyUnderload() {
		if (logic.getMainGUI() != null) logic.getMainGUI().getAddItemScreen().getErrorTextArea().setText("Weight discrepancy detected");
		System.out.println("Weight discrepancy detected. Please add item(s)");
	}
	
	@Override
	public void theMassOnTheScaleHasExceededItsLimit(IElectronicScale scale) {
		this.logic.weightLogic.scaleOperational = false;
	}

	@Override
	public void theMassOnTheScaleNoLongerExceedsItsLimit(IElectronicScale scale) {
		this.logic.weightLogic.scaleOperational = true;
	}
	
	// ---- Unused -----

	@Override
	public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}
}	
