package com.thelocalmarketplace.software.logic;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Mass.MassDifference;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.gui.MainGUI;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.InvalidArgumentSimulationException;
import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import ca.ucalgary.seng300.simulation.SimulationException;

/**
 * Handles all logic operations related to weight for a self checkout station
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

public class WeightLogic extends AbstractLogicDependant {
	
	/** expected weight change on software side */
	private Mass expectedWeight;
	
	/** actual weight on scale */
	private Mass actualWeight;
	
	/** Tolerance for weight difference before scale blocks due to discrepancy */
	private Mass sensitivity; 
	
	/** True if the bagging area scale is not over weight capacity; false otherwise */
	public boolean scaleOperational; 
	
	/** mass of all bags that have been added */
	private Mass bagMassTotal;

	
	public WeightLogic(CentralStationLogic logic) throws NullPointerException {
		super(logic);
		
		this.logic = logic;
		this.expectedWeight = Mass.ZERO;
		this.actualWeight = Mass.ZERO;
		this.sensitivity=logic.hardware.getBaggingArea().getSensitivityLimit();
		//this.sensitivity = logic.hardware.baggingArea.getSensitivityLimit();
		this.bagMassTotal = Mass.ZERO;
		this.scaleOperational = true;
	}
	
	public Mass getActualWeight() {
		return this.actualWeight;
	}
	
	/** sets total mass of current bags
	 * 
	 * @param m new mass at last event
	 **/
	public void updateTotalBagMass(Mass m) {
		this.bagMassTotal = m;
		
	}
	
	/** gets total mass of current bags
	 * 
	 * @param m new mass at last event
	 **/
	public Mass getTotalBagMass() {
		return this.bagMassTotal;
	}
	
	/** Getter for expected mass
	 * */
	public Mass getExpectedWeight(){
		return this.expectedWeight;
	}
	
	/** Adds the expected weight of the product with given barcode to the expectedWeight
	 * @param barcode barcode of the item for which to add the expected weight */
	public void addExpectedWeight(Barcode barcode) {
		if (!ProductDatabases.BARCODED_PRODUCT_DATABASE.containsKey(barcode)) {
			throw new InvalidStateSimulationException("Barcode not registered to product database");
		}
		BarcodedProduct product = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode);
		Mass mass = new Mass(product.getExpectedWeight());
		this.expectedWeight = this.expectedWeight.sum(mass);
	}
	
	/** Updates the expected weight by adding the weight of a PLU coded item to expected weight
	 * The expected weight will be the same as the actual weight since the actual weight measurement is used to determine cost
	 * the expected weight is merely updated in case someone removes the item or adds more items to the scale
	 * @param massPLUCodedItem the mass of the PLU coded item */
	public void addExpectedWeight(Mass massPLUCodedItem) {
		this.expectedWeight = this.expectedWeight.sum(massPLUCodedItem);
	}
	
	/** Removes the weight of the barcode product given from expectedWeight
	 * @param barcode - barcode of item to remove weight of */
	public void removeExpectedWeight(Barcode barcode) {
		if (!ProductDatabases.BARCODED_PRODUCT_DATABASE.containsKey(barcode)) {
			throw new InvalidStateSimulationException("Barcode not registered to product database");
		}
		BarcodedProduct product = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode);
		Mass mass = new Mass(product.getExpectedWeight());
		MassDifference difference = this.expectedWeight.difference(mass);
		if (difference.compareTo(Mass.ZERO) < 0) throw new InvalidStateSimulationException("Expected weight cannot be negative");
		this.expectedWeight = difference.abs();
	}
	
	/** Removes the weight of the PLU coded item given from expectedWeight
	 * @param item - the PLU coded item to remove the weight of */
	public void removeExpectedWeight(PLUCodedItem item) {
		if (!ProductDatabases.PLU_PRODUCT_DATABASE.containsKey(item.getPLUCode())) {
			throw new InvalidStateSimulationException("price-lookup code not registered to product database");
		}
		PLUCodedProduct product = ProductDatabases.PLU_PRODUCT_DATABASE.get(item.getPLUCode());
		Mass mass = item.getMass();
		MassDifference difference = this.expectedWeight.difference(mass);
		if (difference.compareTo(Mass.ZERO) < 0) throw new InvalidStateSimulationException("Expected weight cannot be negative");
		this.expectedWeight = difference.abs();
	}
	
	/** UPdates actual weight to the mass passed
	 * @param mass - Mass to change the actual weight to */
	public void updateActualWeight(Mass mass) {
		this.actualWeight = mass;
	}
	
	/** Indicates that an item should not be bagged
	 * @param barcode - barcode of item to skip bagging 
	 */
	public void skipBaggingRequest(Barcode barcode) {
		logic.attendantLogic.requestApprovalSkipBagging(barcode);
	}
	
	/** Checks if there is a weight discrepancy 
	 * @return True if there is a discrepancy; False otherwise
	 * @throws SimulationException If session not started
	 * @throws SimulationException If the scale is not operational */
	public boolean checkWeightDiscrepancy() {
		if (logic.getMainGUI() != null) logic.getMainGUI().getAddItemScreen().getErrorTextArea().setText("Add an item or pay for the order.");
		// Handles exceptions 
		if (!this.logic.isSessionStarted()) throw new InvalidStateSimulationException("Session not started");
		 else if (!this.scaleOperational) throw new InvalidStateSimulationException("Scale not operational");
		
		// Checks for discrepancy and calls notifier if needed 
		if (actualWeight.difference(expectedWeight).abs().compareTo(this.sensitivity) <= 0 ) {
			return false;
		}
	
		if (actualWeight.compareTo(expectedWeight) > 0) this.logic.weightDiscrepancyController.notifyOverload();
		else this.logic.weightDiscrepancyController.notifyUnderload();
		return true;
	}
	
	/** If there is a weight discrepancy, enters blocking state; otherwise, goes back to normal */
	public void handleWeightDiscrepancy() {
		if (this.logic.weightLogic.checkWeightDiscrepancy()) {
			if (!this.logic.stateLogic.inState(States.BLOCKED)) {
				this.logic.stateLogic.gotoState(States.BLOCKED);
			}
		} else {
			this.logic.stateLogic.gotoState(States.NORMAL);
		}
	}
	
	/** Sets expected weight to actual weight */
	public void overrideDiscrepancy() {
		//TODO: Require attendant authentication/verification
		
		
		this.expectedWeight = this.actualWeight;
	}
	
	/**Checks if the weight on the scale is different from the expected weight after a delay
	 * @param miliseconds to delay the check
	 */
	public void delayedDiscrepancyCheck(int milliseconds) {
		this.logic.stateLogic.gotoState(States.BLOCKED);
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		                if(logic.weightLogic.checkWeightDiscrepancy()) {
		                	logic.weightLogic.handleWeightDiscrepancy();
		                }
		            }
		        }, 
		        milliseconds 
		);
	}

	public void setExpectedWeight(Mass expectedWeight) {
		this.expectedWeight = expectedWeight;
	}
}
