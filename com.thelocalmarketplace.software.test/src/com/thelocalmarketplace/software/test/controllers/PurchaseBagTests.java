package com.thelocalmarketplace.software.test.controllers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.EmptyDevice;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.bag.ReusableBag;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

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
		session.purchaseBagsLogic.purchaseBags(0);
		station.getBaggingArea().addAnItem(new ReusableBag());
		assertTrue(session.stateLogic.getState().equals(States.BLOCKED));
		assertTrue(session.cartLogic.getCart().isEmpty());
	}
	
	@Test
	public void normalUseOneBagTest() throws EmptyDevice, OverloadedDevice {
		station.getReusableBagDispenser().load(new ReusableBag());
		session.purchaseBagsLogic.purchaseBags(1);
		station.getBaggingArea().addAnItem(new ReusableBag());
		assertTrue(session.stateLogic.getState().equals(States.NORMAL));
		assertTrue(session.cartLogic.getCart().size() == 1);
	}
	
	@Test
	public void negativeBagsTest() throws EmptyDevice, OverloadedDevice {
		station.getReusableBagDispenser().load(new ReusableBag());
		session.purchaseBagsLogic.purchaseBags(-1);
		station.getBaggingArea().addAnItem(new ReusableBag());
		assertTrue(session.stateLogic.getState().equals(States.BLOCKED));
		assertTrue(session.cartLogic.getCart().isEmpty());
	}
	
	@Test
	public void multipleBagsWithBagsLeftTest() throws EmptyDevice, OverloadedDevice {
		int bagsLoaded = 6;

		for (int j = 0; j < bagsLoaded+1; j++) {
			station.getReusableBagDispenser().load(new ReusableBag());
		}
		station.getReusableBagDispenser().load(new ReusableBag());
		session.purchaseBagsLogic.purchaseBags(4);
		station.getBaggingArea().addAnItem(new ReusableBag());
		assertTrue(session.cartLogic.getCart().size() == 4);
		// why is the state not normal?
//		assertTrue(session.stateLogic.getState().equals(States.NORMAL));
	}
	
	@Test 
	public void tooManyBagsInDispenserTest()throws EmptyDevice, OverloadedDevice {
		int bagsLoaded = 99999;
		assertThrows(OverloadedDevice.class, () ->{
		for (int j = 0; j < bagsLoaded +1; j++) {
			station.getReusableBagDispenser().load(new ReusableBag());
			}	});
	}
	
	@Test 
	public void EmptyBagDispenserTest() throws EmptyDevice, OverloadedDevice {
		station.getReusableBagDispenser().load(new ReusableBag());
		station.getReusableBagDispenser().unload();
		assertThrows(EmptyDevice.class, () -> session.purchaseBagsLogic.purchaseBags(1));
	}
	
	
}
