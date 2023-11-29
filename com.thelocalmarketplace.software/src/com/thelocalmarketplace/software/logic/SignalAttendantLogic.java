package com.thelocalmarketplace.software.logic;

import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.hardware.AttendantStation;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import com.thelocalmarketplace.software.logic.AttendantLogic;

import ca.ucalgary.seng300.simulation.InvalidArgumentSimulationException;

public class SignalAttendantLogic {
	private AttendantStation attendantStation;
	private AttendantLogic attendantLogic;
	private boolean helpNeeded;
	
	public void getAssistance(ISelfCheckoutStation station) {
		helpNeeded = true;
	}
	
	public void clearAssistanceRequest(ISelfCheckoutStation station) {
		helpNeeded = false;
	}
	
	/** Tracks AttendantLogic and AttendantStation hardware 
	 * a customerSelfCheckoutStation can be supervised by at most one attendant station
	 * @param attendantStation
	 * @param attendantLogic
	 */ 
	public SignalAttendantLogic(AttendantStation attendantStation, AttendantLogic attendantLogic) {
		this.attendantStation = attendantStation;
	    this.attendantLogic = attendantLogic;
	    this.helpNeeded = false;
	}
	/** Method signals to the system that help is needed at a customer station
	 * @param station customerStation should belong to superviseStations list for a specific instance of attendantStation
	 * @exception InvalidArgumentSimulationException thrown is customer self-checkout is not supervised by attendee station
	 */
	public void signalHelpNeeded(ISelfCheckoutStation station) {
		if (attendantStation.supervisedStations().contains(station)) {
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
	public void clearCustomerRequest(ISelfCheckoutStation station) {
        if (attendantStation.supervisedStations().contains(station)) {
            clearAssistanceRequest(station);
        } else {
            throw new InvalidArgumentSimulationException("The provided customer station is not supervised by this attendant station.");
        }
    }   
}
