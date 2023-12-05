/**
 * @author Camila Hernandez (30134911)
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
 * @author Ananya Jain (30196069)
 * @author Zhenhui Ren (30139966)
 * @author Eric George (30173268)
 * @author Jenny Dang (30153821)
 * @author Tanmay Mishra (30127407)
 * @author Adrian Brisebois (30170764)
 * @author Atique Muhammad (30038650)
 * @author Ryan Korsrud (30173204)
 */

package com.thelocalmarketplace.software.test.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import powerutility.NoPowerException;
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
		// Checking weight 
		assertEquals(new Mass(BigInteger.valueOf(5_000_000)), session.weightLogic.getActualWeight());
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

		for (int j = 0; j < bagsLoaded-1; j++) {
			station.getReusableBagDispenser().load(new ReusableBag());
		}
		
		session.purchaseBagsLogic.purchaseBags(4);
		station.getBaggingArea().addAnItem(new ReusableBag());
		station.getBaggingArea().addAnItem(new ReusableBag());
		station.getBaggingArea().addAnItem(new ReusableBag());
		station.getBaggingArea().addAnItem(new ReusableBag());
		
		assertTrue(session.stateLogic.getState().equals(States.NORMAL));
		assertTrue(session.cartLogic.getCart().size() == 4);
		assertEquals(new Mass(BigInteger.valueOf(20_000_000)), session.weightLogic.getActualWeight());

	}
	
	@Test(expected = EmptyDevice.class)
	public void moreBagsRequiredThanHaveTest() throws EmptyDevice, OverloadedDevice {
		int bagsLoaded = 2;

		for (int j = 0; j < bagsLoaded-1; j++) {
			station.getReusableBagDispenser().load(new ReusableBag());
		}
		
		session.stateLogic.gotoState(States.ADDBAGS);
		session.purchaseBagsLogic.purchaseBags(4);	
	}
	
	@Test(expected = InvalidStateSimulationException.class)
	public void sessionNotStarted() throws OverloadedDevice, EmptyDevice {
		session.stopSession();
		station.getReusableBagDispenser().load(new ReusableBag());
		session.stateLogic.gotoState(States.ADDBAGS);
		session.purchaseBagsLogic.purchaseBags(1);
	}
	
	@Test(expected = OverloadedDevice.class)
	public void tooManyBagsInDispenserTest() throws OverloadedDevice{
		int bagsLoaded = 99999;
		for (int j = 0; j < bagsLoaded +1; j++) {
			station.getReusableBagDispenser().load(new ReusableBag());
		}
	}
	
	
	@Test(expected = EmptyDevice.class)
	public void EmptyBagDispenserTest() throws OverloadedDevice, EmptyDevice {
		station.getReusableBagDispenser().load(new ReusableBag());
		station.getReusableBagDispenser().unload();
		session.purchaseBagsLogic.purchaseBags(1);
	}
	
	@Test(expected = NoPowerException.class)
	public void noPowerTest() throws OverloadedDevice, EmptyDevice {
		station.turnOff();
		station.getReusableBagDispenser().load(new ReusableBag());
		session.stateLogic.gotoState(States.ADDBAGS);
		session.purchaseBagsLogic.purchaseBags(1);
	}
	
}
