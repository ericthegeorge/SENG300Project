package com.thelocalmarketplace.software.logic;

import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.hardware.AttendantStation;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.InvalidArgumentSimulationException;
import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;

/**
 * Attendant Logic
 * @author Angelina Rochon (30087177)
 * ----------------------------------
 * @author Connell Reffo (10186960)
 * @author Tara Strickland (10105877)
 * @author Julian Fan (30235289)
 * @author Braden Beler (30084941)
 * @author Samyog Dahal (30194624)
 * @author Maheen Nizmani (30172615)
 * @author Phuong Le (30175125)
 * @author Daniel Yakimenka (10185055)
 * @author Merick Parkinson (30196225)
 * @author Farida Elogueil (30171114)
 */
public class AttendantLogic {
	/** tracks the station logic being monitored */
	private CentralStationLogic logic;
	
	/** tracks weather or not a bagging discrepency has been found */
	private boolean inBaggingDiscrepency;

	//public AttendantStation station=new AttendantStation();

	
	public AttendantLogic(CentralStationLogic l) {
		this.logic = l;
	}
	
	/** simulates attendant signifying they approve the bagging area 
	 * @throws Exception if the add bags state cannot be exited*/
	public void approveBaggingArea() throws Exception {
		this.logic.weightLogic.overrideDiscrepancy();
		this.logic.addBagsLogic.approvedBagging = true;
		this.logic.addBagsLogic.endAddBags();
	}
	
	/** simulates attendant being notified that a bagging discrepancy has occurred 
	 * The only way for customer to transition out of ADDBAGS state is for attendant to call
	 * approveBaggingArea() */
	public void baggingDiscrepencyDetected() {
		//TODO GUI: display that customer is awaiting approval to attendant
		this.inBaggingDiscrepency = true;
	}
	
	/** setter for in baggingDiscrepency */
	
	public void setBaggingDiscrepency(boolean b) {
		this.inBaggingDiscrepency = b;
	}
	
/** getter for in baggingDiscrepency for testing*/
	
	public boolean getBaggingDiscrepency() {
		return this.inBaggingDiscrepency;
	}
	
	/** Notifies attendant of request to skip bagging a particular product with given barcode 
	 * @param barcode - barcode of item the customer is requesting to skip bagging 
	 * @throws InvalidArgumentSimulationException if barcode is null */
	public void requestApprovalSkipBagging(Barcode barcode) {
		if (barcode == null) throw new InvalidArgumentSimulationException("Cannot skip bagging with null barcode");
		if (!this.logic.stateLogic.inState(States.BLOCKED)) throw new InvalidStateSimulationException("Skip bagging request should only occur in a blocked state");
		///TODO GUI: set alert for customer to wait for attendant approval
	}
	
	/** Attendant approval to skip bagging of item with specified barcode
	 * removes the expected weight of the requested barcode
	 * unblocks station only if no weight discrepancy remains after expected weight is removed
	 * @param barcode - barcode of item that the attendant is approving to skip bagging 
	 * @throws InvalidArgumentSimulationException if barcode is null */
	public void grantApprovalSkipBagging(Barcode barcode) {
		if (barcode == null) throw new InvalidArgumentSimulationException("Cannot skip bagging with null barcode");
		logic.weightLogic.removeExpectedWeight(barcode);
		logic.weightLogic.handleWeightDiscrepancy();
	}

	/**
	 * Attendant adds ink to the printer with specified amount
	 * @param amount
	 * @throws OverloadedDevice
	 */
	public void addInk(int amount) throws OverloadedDevice {
		this.logic.hardware.getPrinter().addInk(amount);
    }

	/**
	 * Attendant addds paper to the printer with amount specified
	 * @param amount
	 * @throws OverloadedDevice
	 */

	public void addPaper(int amount) throws OverloadedDevice {
		this.logic.hardware.getPrinter().addPaper(amount);
	}
	
	public void printDuplicateReceipt() {
		this.logic.receiptPrintingController.printDuplicateReceipt();
	}

	/**
	 * disables use of current station
	 */
	public void disableStation(){
		if(this.logic.isSessionStarted()==true){
			this.logic.stateLogic.gotoState(States.SUSPENDED);
			this.logic.hardware.getMainScanner().disable();
			this.logic.hardware.getScanningArea().disable();
			this.logic.hardware.getBanknoteValidator().disable();
			this.logic.hardware.getCoinValidator().disable();
			this.logic.hardware.getCardReader().disable();
			this.logic.hardware.getHandheldScanner().disable();
			this.logic.hardware.getBaggingArea().disable();
			this.logic.hardware.getBanknoteInput().disable();
			this.logic.hardware.getCoinSlot().disable();
			this.logic.hardware.getPrinter().disable();
			this.logic.hardware.getReusableBagDispenser().disable();
		}


	}

	/**
	 * enables use of current station
	 */

	public void enableStation(){
		if(this.logic.isSessionStarted()==false || this.logic.stateLogic.inState(States.SUSPENDED)){
			this.logic.stateLogic.gotoState(States.NORMAL);
			this.logic.hardware.unplug();
			this.logic.hardware.getMainScanner().enable();
			this.logic.hardware.getScanningArea().enable();
			this.logic.hardware.getBanknoteValidator().enable();
			this.logic.hardware.getCoinValidator().enable();
			this.logic.hardware.getCardReader().enable();
			this.logic.hardware.getHandheldScanner().enable();
			this.logic.hardware.getBaggingArea().enable();
			this.logic.hardware.getBanknoteInput().enable();
			this.logic.hardware.getCoinSlot().enable();
			this.logic.hardware.getPrinter().enable();
			this.logic.hardware.getReusableBagDispenser().enable();

		}
	}
}
