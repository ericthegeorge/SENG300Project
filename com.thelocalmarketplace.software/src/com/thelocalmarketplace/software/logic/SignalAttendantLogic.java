package com.thelocalmarketplace.software.logic;

import com.thelocalmarketplace.hardware.AttendantStation;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import ca.ucalgary.seng300.simulation.InvalidArgumentSimulationException;

/**
 * @author Camila Hernandez (30134911)
 * --------------------------------
 * @author Adrian Brisebois
 * @author Alan Yong
 * @author Ananya jain
 * @author Andrew Matti
 * @author Atique Muhammad
 * @author Christopher Lo
 * @author Danny Ly
 * @author Eric George
 * @author Gareth Jenkins
 * @author Ian Beler
 * @author Jahnissi Nwakanma
 * @author Jenny Dang
 * @author Maheen Nizamani
 * @author Michael Svoboda
 * @author Olivia Crosby
 * @author Rico Manalastas
 * @author Ryan Korsrud
 * @author Shanza Raza
 * @author Sukhnaaz Sidhu
 * @author Tanmay Mishra
 * @author Zhenhui Ren
 */

public class SignalAttendantLogic extends AbstractLogicDependant{
	private AttendantStation attendantStation;
	private boolean helpNeeded; //flag for attendant
	
	/** 
	 * Used to indicate to attendant that helpNeeded flag is set to True
	 * @param station
	 */
	public void getAssistance(ISelfCheckoutStation station) {
		helpNeeded = true;
	}
	
	/** 
	 * Used to for attendant to indicate that helpNeeded flag is set to False - i.e. no longer needed
	 * @param station
	 */
	public void clearAssistanceRequest(ISelfCheckoutStation station) {
		helpNeeded = false;
	}
	/**
	 * Just a getter for helpNeeded flag
	 * @return
	 */
	public boolean isHelpNeeded() { 
		return helpNeeded;
	}
	
	/** Tracks AttendantLogic and central logic 
	 * a customerSelfCheckoutStation can be supervised by at most one attendant station
	 * @param logic
	 */ 
	public SignalAttendantLogic(CentralStationLogic logic) {
		super(logic);
		//this.attendantStation = new AttendantStation();
	    this.helpNeeded = false;
	}
	/** Method signals to the system that help is needed at a customer station
	 * @param station customerStation should belong to superviseStations list for a specific instance of attendantStation
	 * @exception InvalidArgumentSimulationException thrown is customer self-checkout is not supervised by attendee station
	 */
	public synchronized void signalHelpNeeded(ISelfCheckoutStation station) {
		if (attendantStation.supervisedStations().contains(station)) {
			if (isHelpNeeded()) { 
	               throw new IllegalStateException("Concurrent request: Help is already called.");
	        }
			getAssistance(station);
		}
		else {
			throw new InvalidArgumentSimulationException("This customer station is not supervised by this attendant station.");
		}
	}
	/** Method for the attendant to clear the customer's request once they have been helped
	 * @param station should belong to superviseStations list
	 * @exception InvalidArggumentSimulationException thrown if station not supervised by an attendee
	 */
	public synchronized void clearCustomerRequest(ISelfCheckoutStation station) {
        if (attendantStation.supervisedStations().contains(station)) {
        	if (isHelpNeeded()) {
        		clearAssistanceRequest(station);
        	}
        } else {
            throw new InvalidArgumentSimulationException("The provided customer station is not supervised by this attendant station.");
        }
    }   
}
