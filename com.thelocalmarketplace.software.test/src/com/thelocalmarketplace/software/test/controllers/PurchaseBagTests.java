package com.thelocalmarketplace.software.test.controllers;

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
	public void normalUse() throws EmptyDevice, OverloadedDevice {
		station.getReusableBagDispenser().load(new ReusableBag());
		session.purchaseBagsLogic.purchaseBags(1);
		station.getBaggingArea().addAnItem(new ReusableBag());
		assertTrue(session.stateLogic.getState().equals(States.NORMAL));
		assertTrue(session.cartLogic.getCart().size() == 1);
	}
}
