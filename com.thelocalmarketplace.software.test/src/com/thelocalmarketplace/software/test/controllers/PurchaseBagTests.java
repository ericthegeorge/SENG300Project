package com.thelocalmarketplace.software.test.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.EmptyDevice;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.bag.ReusableBag;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.PurchaseBagsLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import powerutility.PowerGrid;

public class PurchaseBagTests {
	AbstractSelfCheckoutStation station;
	CentralStationLogic session;
	
	@Before
	public void setup() {
		station = new SelfCheckoutStationGold();
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		session = new CentralStationLogic(station);
		session.setBypassIssuePrediction(true);
		session.startSession();
	}
	
	@Test
	public void zeroBagTest() throws EmptyDevice, OverloadedDevice {
		station.getReusableBagDispenser().load(new ReusableBag());
		session.stateLogic.gotoState(States.ADDBAGS);
		session.purchaseBagsLogic.purchaseBags(0);
		station.getBaggingArea().addAnItem(new ReusableBag());
		
	    session.purchaseBagsLogic.purchaseBags();
		
		assertTrue(session.stateLogic.getState().equals(States.NORMAL));
		assertTrue(session.cartLogic.getCart().isEmpty());
	}
	
	@Test
	public void normalUseOneBagTest() throws EmptyDevice, OverloadedDevice {
		station.getReusableBagDispenser().load(new ReusableBag());
		session.stateLogic.gotoState(States.ADDBAGS);
		session.purchaseBagsLogic.purchaseBags(1);
		station.getBaggingArea().addAnItem(new ReusableBag());
		
		session.purchaseBagsLogic.purchaseBags();
		 
		assertTrue(session.stateLogic.getState().equals(States.NORMAL));
		assertTrue(session.cartLogic.getCart().size() == 1);
		// Checking weight 
		assertEquals(new Mass(BigInteger.valueOf(5_000_000)), session.weightLogic.getTotalBagMass());
	}
	
	@Test
	public void negativeBagsTest() throws EmptyDevice, OverloadedDevice {
		station.getReusableBagDispenser().load(new ReusableBag());
		session.stateLogic.gotoState(States.ADDBAGS);
		session.purchaseBagsLogic.purchaseBags(-1);
		station.getBaggingArea().addAnItem(new ReusableBag());
		
	    session.purchaseBagsLogic.purchaseBags();
	    
		assertTrue(session.stateLogic.getState().equals(States.NORMAL));
		assertTrue(session.cartLogic.getCart().isEmpty());
	}
	
	@Test
	public void multipleBagsWithBagsLeftTest() throws EmptyDevice, OverloadedDevice {
		int bagsLoaded = 6;

		for (int j = 0; j < bagsLoaded-1; j++) {
			station.getReusableBagDispenser().load(new ReusableBag());
		}
		
		session.stateLogic.gotoState(States.ADDBAGS);
		session.purchaseBagsLogic.purchaseBags(4);
		station.getBaggingArea().addAnItem(new ReusableBag());
		
	    session.purchaseBagsLogic.purchaseBags();
		
		assertTrue(session.stateLogic.getState().equals(States.NORMAL));
		assertTrue(session.cartLogic.getCart().size() == 4);
		assertEquals(new Mass(BigInteger.valueOf(20_000_000)), session.weightLogic.getTotalBagMass());

	}
	
	@Test
	public void moreBagsRequiredThanHaveTest() throws EmptyDevice, OverloadedDevice {
		int bagsLoaded = 2;

		for (int j = 0; j < bagsLoaded-1; j++) {
			station.getReusableBagDispenser().load(new ReusableBag());
		}
		
		session.stateLogic.gotoState(States.ADDBAGS);
		assertThrows(EmptyDevice.class, () -> session.purchaseBagsLogic.purchaseBags(4));	
	}
	
	@Test
    public void bagsTooHeavyTest() throws EmptyDevice, OverloadedDevice {
        station.getReusableBagDispenser().load(new ReusableBag());
		session.stateLogic.gotoState(States.ADDBAGS);
		session.purchaseBagsLogic.purchaseBags(1);
		station.getBaggingArea().addAnItem(new ReusableBag());
       
        session.purchaseBagsLogic.purchaseBags();

    }
	
	@Test
	public void sessionNotStarted() throws OverloadedDevice, EmptyDevice {
		session.stopSession();
		station.getReusableBagDispenser().load(new ReusableBag());
		session.stateLogic.gotoState(States.ADDBAGS);
		
		assertThrows(InvalidStateSimulationException.class, () ->
		session.purchaseBagsLogic.purchaseBags(1));
		
		assertThrows(InvalidStateSimulationException.class, () ->
		session.purchaseBagsLogic.purchaseBags());
		
	}
	
	@Test 
	public void tooManyBagsInDispenserTest(){
		int bagsLoaded = 99999;
		assertThrows(OverloadedDevice.class, () ->{
		for (int j = 0; j < bagsLoaded +1; j++) {
			station.getReusableBagDispenser().load(new ReusableBag());
			}	});
	}
	
	
	@Test 
	public void EmptyBagDispenserTest() throws OverloadedDevice {
		station.getReusableBagDispenser().load(new ReusableBag());
		station.getReusableBagDispenser().unload();
		assertThrows(EmptyDevice.class, () -> session.purchaseBagsLogic.purchaseBags(1));
	}
	
	// do a test for no power
	
}
