package com.thelocalmarketplace.software.test.logic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.OverloadedDevice;
import com.tdc.CashOverloadException;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.MembershipDatabase;
import com.thelocalmarketplace.software.logic.MembershipLogic;
import com.thelocalmarketplace.software.logic.PurchaseBagsLogic;
import com.thelocalmarketplace.software.test.logic.MembershipTests.CardDataStub;

import ca.ucalgary.seng300.simulation.SimulationException;
import powerutility.PowerGrid;

public class PurchaseBagTest {
	
	private SelfCheckoutStationBronze hardware;
	private CentralStationLogic logic;
	private PurchaseBagsLogic purchaseBagLogic;
	
	@Before
	public void setup() throws SimulationException, CashOverloadException, OverloadedDevice {
		PowerGrid.engageUninterruptiblePowerSource();
		PowerGrid.instance().forcePowerRestore();

	    AbstractSelfCheckoutStation.resetConfigurationToDefaults();

	    hardware = new SelfCheckoutStationBronze();
		hardware.plugIn(PowerGrid.instance());
		hardware.turnOn();
		
		logic = new CentralStationLogic(hardware);	
		logic.setBypassIssuePrediction(true);
		logic.startSession();
		
		purchaseBagLogic = new PurchaseBagsLogic(logic);
	 }
	
	@After
	public void tearDown() {
		PowerGrid.engageUninterruptiblePowerSource();
	}
	 
	// WIP
	@Test
	public void purchasingBag() {
		purchaseBagLogic.startPurchaseBags();
		
		purchaseBagLogic.purchaseBags();
		
	}
}
