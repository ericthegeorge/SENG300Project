package com.thelocalmarketplace.software.test.controllers;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;

import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.SimulationException;
import powerutility.PowerGrid;

/**
 * Tests to add own bags
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

public class AddOwnBagsTests {
	
	SelfCheckoutStationBronze station;
	CentralStationLogic session;

	Mass bag1mass;
	Mass bag2mass;
	Mass invalidBagMass;
	Mass m_product;
	
	public BarcodedProduct product;
	public Barcode barcode;
	public Numeral digits;
	public Numeral[] barcode_numeral;
	
	//although bags arent barcoded this will allow me to test logic
	Bag bag1;
	Bag bag2;
	Bag bag3;
	BarcodedItem b;
	
	@Before public void setUp() {
		PowerGrid.engageUninterruptiblePowerSource();
		PowerGrid.instance().forcePowerRestore();
		
		AbstractSelfCheckoutStation.resetConfigurationToDefaults();
		
		//d1 = new dummyProductDatabaseWithOneItem();
		//d2 = new dummyProductDatabaseWithNoItemsInInventory();
		
		station = new SelfCheckoutStationBronze();
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		session = new CentralStationLogic(station);
		
		bag1mass = new Mass((double)8);
		bag2mass = new Mass((double)10);
		invalidBagMass = new Mass((double)30);
		
		barcode_numeral = new Numeral[]{Numeral.one,Numeral.two, Numeral.three};
		barcode = new Barcode(barcode_numeral);
		
		product = new BarcodedProduct(barcode, "some item",5,(double)300.0);
		m_product = new Mass((double)300.0);
		b =  new BarcodedItem(barcode, m_product);
		
		ProductDatabases.BARCODED_PRODUCT_DATABASE.clear();
		ProductDatabases.INVENTORY.clear();
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode, product);
		ProductDatabases.INVENTORY.put(product, 1);
		
		
		
		bag1 = new Bag(bag1mass);
		bag2 = new Bag(bag2mass);
		bag3 = new Bag(invalidBagMass);
		
		session.setBypassIssuePrediction(true);
	}
	
	@After 
	public void tearDown() {
		PowerGrid.engageFaultyPowerSource();
	}
	
	
	@Test public void addValidBagsTestBagMass() throws Exception {
		session.startSession();
		session.addBagsLogic.startAddBags();
		station.getBaggingArea().addAnItem(bag1);
		session.addBagsLogic.endAddBags();
		assertTrue("bag mass did not update correctly", session.weightLogic.getExpectedWeight().equals(bag1mass));
		
	}@Test(expected = SimulationException.class) public void addBagsWhenSessionNotStarted() throws Exception {
		session.addBagsLogic.startAddBags();
		
	}@Test(expected = SimulationException.class) public void endBagsWhenSessionNotStarted() throws Exception {
		session.addBagsLogic.endAddBags();
		
	}@Test(expected = SimulationException.class) public void addBagsWhenNotInState() throws Exception {
		session.startSession();
		//session.addBagsLogic.startAddBags();
		session.addBagsLogic.endAddBags();
	}
	
	@Test public void addValidBagsTestNoDiscrepency() throws Exception {
		session.startSession();
		session.addBagsLogic.startAddBags();
		station.getBaggingArea().addAnItem(bag1);
		session.addBagsLogic.endAddBags();
		assertTrue("bag mass did not update correctly", !session.attendantLogic.getBaggingDiscrepency());
		
	}
	
	@Test public void addMultipleValidBagsTestExpectedMass() throws Exception {
		session.startSession();
		session.addBagsLogic.startAddBags();
		station.getBaggingArea().addAnItem(bag1);
		station.getBaggingArea().addAnItem(bag2);
		session.addBagsLogic.endAddBags();
		Mass expected = bag1mass.sum(bag2mass);
		assertTrue("bag mass did not update correctly",session.weightLogic.getExpectedWeight().equals(expected));
		
	}@Test public void addMultipleValidBagsTestNoDiscrepency() throws Exception {
		session.startSession();
		session.addBagsLogic.startAddBags();
		station.getBaggingArea().addAnItem(bag1);
		station.getBaggingArea().addAnItem(bag2);
		session.addBagsLogic.endAddBags();
		assertTrue("bag mass did not update correctly", !session.attendantLogic.getBaggingDiscrepency());
		
	}
	
	@Test public void addInValidBagsTest() throws Exception {
		session.startSession();
		session.addBagsLogic.startAddBags();
		station.getBaggingArea().addAnItem(bag3);
		session.addBagsLogic.endAddBags();
		//station.baggingArea.addAnItem(bag2);
		//Mass expected = bag1mass.sum(bag2mass);
		assertTrue("bag mass did not update correctly", session.attendantLogic.getBaggingDiscrepency());
		
	}@Test public void addValidBagsExitBaggingCheckBlockedTest() throws Exception {
		session.startSession();
		session.addBagsLogic.startAddBags();
		station.getBaggingArea().addAnItem(bag1);
		session.addBagsLogic.endAddBags();
		//station.baggingArea.addAnItem(bag2);
		//Mass expected = bag1mass.sum(bag2mass);
		assertTrue("bag mass did not update correctly", !this.session.stateLogic.inState(States.BLOCKED));
		
	}@Test public void addValidBagsAddItemCheckBlockedTest() throws Exception {
		session.startSession();
		session.addBagsLogic.startAddBags();
		station.getBaggingArea().addAnItem(bag1);
		session.addBagsLogic.endAddBags();
		
		session.addBarcodedProductController.addBarcode(barcode);
		station.getBaggingArea().addAnItem(b);
		//station.handheldScanner.scan();
		//station.baggingArea.addAnItem(bag2);
		//Mass expected = bag1mass.sum(bag2mass);
		assertTrue("bag mass did not update correctly", this.session.stateLogic.inState(States.NORMAL));
		
	}@Test public void addValidBagsAddItemCheckActualMassTest() throws Exception {
		session.startSession();
		session.addBagsLogic.startAddBags();
		station.getBaggingArea().addAnItem(bag1);
		session.addBagsLogic.endAddBags();
		
		session.addBarcodedProductController.addBarcode(barcode);
		station.getBaggingArea().addAnItem(b);
		Mass expected = m_product.sum(bag1mass);
		
		//station.handheldScanner.scan();
		//station.baggingArea.addAnItem(bag2);
		//Mass expected = bag1mass.sum(bag2mass);
		assertTrue("bag mass did not update correctly", this.session.weightLogic.getActualWeight().equals(expected));
		
	}@Test public void addInvalidBagsFixByApproving() throws Exception {
		session.startSession();
		session.addBagsLogic.startAddBags();
		station.getBaggingArea().addAnItem(bag3);
		session.addBagsLogic.endAddBags();
		session.attendantLogic.approveBaggingArea();
		assertTrue("bag mass did not update correctly", this.session.stateLogic.inState(States.NORMAL));
		
	}@Test public void addInvalidBagsFixByApprovingCheckExpectedMass() throws Exception {
		session.startSession();
		session.addBagsLogic.startAddBags();
		station.getBaggingArea().addAnItem(bag3);
		session.addBagsLogic.endAddBags();
		session.attendantLogic.approveBaggingArea();
		assertTrue("bag mass did not update correctly",session.weightLogic.getExpectedWeight().equals(invalidBagMass));
		
	}
	@Test public void addInvalidBagsFixByRemoving() throws Exception {
		session.startSession();
		session.addBagsLogic.startAddBags();
		station.getBaggingArea().addAnItem(bag3);
		session.addBagsLogic.endAddBags();
		station.getBaggingArea().removeAnItem(bag3);
		session.addBagsLogic.endAddBags();
		
		assertTrue("bag mass did not update correctly", !this.session.stateLogic.inState(States.BLOCKED));
		
	}@Test public void addInvalidBagsFixByapprovingAddMoreBags() throws Exception {
		session.startSession();
		session.addBagsLogic.startAddBags();
		station.getBaggingArea().addAnItem(bag3);
		session.addBagsLogic.endAddBags();
		session.attendantLogic.approveBaggingArea();
		session.addBagsLogic.startAddBags();
		station.getBaggingArea().addAnItem(bag2);
		session.addBagsLogic.endAddBags();

		assertTrue("bag mass did not update correctly", this.session.attendantLogic.getBaggingDiscrepency());
		
	}
	
	public class Bag extends Item{
		public Bag(Mass m) {
			super(m);
		}
	}
}
