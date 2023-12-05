package com.thelocalmarketplace.software.test.logic;

//Shanza Raza 30192765

import com.thelocalmarketplace.hardware.AttendantStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.software.gui.MainGUI;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.SignalAttendantLogic;

import ca.ucalgary.seng300.simulation.InvalidArgumentSimulationException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;

public class TestSignalAttendant {
	
	@Test
	public void testGetAssistance() {
	SelfCheckoutStationBronze scsb = new SelfCheckoutStationBronze();
	CentralStationLogic csl = new CentralStationLogic(scsb); 
	SignalAttendantLogic sat = new SignalAttendantLogic(csl);  
	sat.getAssistance(scsb);
	assertTrue(sat.isHelpNeeded());
} 
	
	@Test
	public void testClearAssistanceRequest() {
		SelfCheckoutStationBronze scsb = new SelfCheckoutStationBronze();
		CentralStationLogic csl = new CentralStationLogic(scsb); 
		SignalAttendantLogic sat = new SignalAttendantLogic(csl);    
		sat.getAssistance(scsb);
		sat.clearAssistanceRequest(scsb);
		assertFalse(sat.isHelpNeeded());
	}
	
	@Test (expected = InvalidArgumentSimulationException.class)
	public void testSignalHelpNeeded1() {
		SelfCheckoutStationBronze scsb = new SelfCheckoutStationBronze();
		CentralStationLogic csl = new CentralStationLogic(scsb); 
		SignalAttendantLogic sat = new SignalAttendantLogic(csl);
		sat.signalHelpNeeded(scsb);
	}
	
	@Test 
	public void testSignalHelpNeeded2() {
		SelfCheckoutStationBronze scsb = new SelfCheckoutStationBronze();
		CentralStationLogic csl = new CentralStationLogic(scsb); 
		SignalAttendantLogic sat = new SignalAttendantLogic(csl);
		AttendantStation as = sat.getAttendantStation();  
		as.add(scsb);
		sat.signalHelpNeeded(scsb);
		assertTrue(sat.isHelpNeeded());
	}
	
	@Test (expected = IllegalStateException.class)
	public void testSignalHelpNeeded3() {
		SelfCheckoutStationBronze scsb = new SelfCheckoutStationBronze();
		CentralStationLogic csl = new CentralStationLogic(scsb); 
		SignalAttendantLogic sat = new SignalAttendantLogic(csl);
		AttendantStation as = sat.getAttendantStation();  
		as.add(scsb);
		sat.getAssistance(scsb);
		sat.signalHelpNeeded(scsb);
}
	
	@Test (expected = InvalidArgumentSimulationException.class)
	public void testClearCustomerRequest1() {
		SelfCheckoutStationBronze scsb = new SelfCheckoutStationBronze();
		CentralStationLogic csl = new CentralStationLogic(scsb); 
		SignalAttendantLogic sat = new SignalAttendantLogic(csl);
		sat.clearCustomerRequest(scsb);
	}
	
	@Test 
	public void testClearCustomerRequest2() {
		SelfCheckoutStationBronze scsb = new SelfCheckoutStationBronze();
		CentralStationLogic csl = new CentralStationLogic(scsb); 
		SignalAttendantLogic sat = new SignalAttendantLogic(csl);
		AttendantStation as = sat.getAttendantStation();  
		as.add(scsb);
		sat.clearCustomerRequest(scsb);
		assertFalse(sat.isHelpNeeded()); 
}
	@Test 
	public void testClearCustomerRequest3() {
		SelfCheckoutStationBronze scsb = new SelfCheckoutStationBronze();
		CentralStationLogic csl = new CentralStationLogic(scsb); 
		SignalAttendantLogic sat = new SignalAttendantLogic(csl);
		AttendantStation as = sat.getAttendantStation();  
		as.add(scsb);
		sat.signalHelpNeeded(scsb);
		sat.clearCustomerRequest(scsb);
		assertFalse(sat.isHelpNeeded());
}
	@Test 
	public void testClearCustomerRequest4() {
		SelfCheckoutStationBronze scsb = new SelfCheckoutStationBronze();
		CentralStationLogic csl = new CentralStationLogic(scsb); 
		SignalAttendantLogic sat = new SignalAttendantLogic(csl);
		AttendantStation as = sat.getAttendantStation();  
		as.add(scsb);
		sat.signalHelpNeeded(scsb);
		sat.clearCustomerRequest(scsb);
		sat.clearCustomerRequest(scsb);
		assertFalse(sat.isHelpNeeded());
}
	
}
