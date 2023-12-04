package com.thelocalmarketplace.software.test.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.SimulationException;
import powerutility.NoPowerException;
import powerutility.PowerGrid;

/**
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

public class WeightDiscrepencyTests {
	
	SelfCheckoutStationBronze station;
	CentralStationLogic session;
	
	//stuff for database
	public ProductDatabases database_one_item;
	public Barcode barcode;
	public Numeral digits;
	
	public BarcodedItem bitem;
	public Mass itemMass;
	
	public BarcodedItem bitem2;
	public Mass itemMass2;
	public BarcodedItem bitem3;
	public Mass itemMass3;
	public Numeral[] barcode_numeral;
	public BarcodedProduct product;
	
	@Before public void setUp() {
		PowerGrid.engageUninterruptiblePowerSource();
		PowerGrid.instance().forcePowerRestore();
		
		AbstractSelfCheckoutStation.resetConfigurationToDefaults();
		
		//d1 = new dummyProductDatabaseWithOneItem();
		//d2 = new dummyProductDatabaseWithNoItemsInInventory();
		station = new SelfCheckoutStationBronze();

		
		//initialize database
		barcode_numeral = new Numeral[]{Numeral.one,Numeral.two, Numeral.three};
		barcode = new Barcode(barcode_numeral);
		product = new BarcodedProduct(barcode, "some item",5,(double)300.0);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.clear();
		ProductDatabases.INVENTORY.clear();
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode, product);
		ProductDatabases.INVENTORY.put(product, 1);


		//initialize barcoded item
		itemMass = new Mass((long) 1000000);
		bitem = new BarcodedItem(barcode, itemMass);
		itemMass2 = new Mass((double) 300.0);//300.0 grams
		bitem2 = new BarcodedItem(barcode, itemMass2);
		itemMass3 = new Mass((double) 300.0);//3.0 grams
		bitem3 = new BarcodedItem(barcode, itemMass3);
	}
	
	/** Ensures failures do not occur from scanner failing to scan item, thus isolating test cases */
	public void scanUntilAdded(BarcodedItem item) {
		Boolean itemAdded = false;
		do {
			station.getHandheldScanner().scan(item);
			//check if item has been added to cart
			for (Item i : session.cartLogic.getCart().keySet()) {
				if (i instanceof BarcodedItem) {
					BarcodedItem barcodedItem = (BarcodedItem) i;
					if(barcodedItem.getBarcode().equals(item.getBarcode())) {
						itemAdded = true;
					}
				}
			}
		} while (!itemAdded);
	}
	
	
	@Test (expected = NoPowerException.class) public void testWeightDiscrepencyWithNoPower() {
		station.plugIn(PowerGrid.instance());
		station.turnOff();
		session = new CentralStationLogic(station);
		session.setBypassIssuePrediction(true);
		session.startSession();
		this.scanUntilAdded(bitem);
		station.getHandheldScanner().scan(bitem);
		station.getBaggingArea().addAnItem(bitem);
		
	}
	@Test (expected = SimulationException.class)public void testWeightDiscrepencyWithoutPowerTurnOn() {
		station.turnOn();
		session = new CentralStationLogic(station);
		session.setBypassIssuePrediction(true);
		this.scanUntilAdded(bitem2);
		session.startSession();
		station.getBaggingArea().addAnItem(bitem2);
	}
	@Test public void testWeightDiscrepencyWithPowerTurnOnNoDiscrepency() throws InterruptedException {
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		session = new CentralStationLogic(station);
		session.setBypassIssuePrediction(true);
		session.startSession();
		this.scanUntilAdded(bitem3);
		station.getBaggingArea().addAnItem(bitem3);
	}

	
	@Test 
	public void testWeightDiscrepencyWithPowerTurnOnHasDiscrepencyDifferentThanItem() {

		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		session = new CentralStationLogic(station);
		session.setBypassIssuePrediction(true);
		session.startSession();
		
		this.scanUntilAdded(bitem2);
		station.getBaggingArea().addAnItem(bitem);

		assertTrue("weight discrepancy not detected", session.weightLogic.checkWeightDiscrepancy());
		assertTrue("station not blocked", this.session.stateLogic.inState(States.BLOCKED));
	}
	
	@Test public void testWeightDiscrepencyWithPowerTurnOnHasDiscrepencyNoItem() {
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		session = new CentralStationLogic(station);
		session.setBypassIssuePrediction(true);
		session.startSession();
		
		//station.scanner.scan(bitem2);
		station.getBaggingArea().addAnItem(bitem2);
		assertTrue("weight discrepency not detected", this.session.stateLogic.inState(States.BLOCKED));
	}@Test public void testWeightDiscrepencyWithPowerTurnOnNoDiscrepencyOnSensativity() {
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		session = new CentralStationLogic(station);
		session.setBypassIssuePrediction(true);
		session.startSession();
		
		
		//sensativity of the scale is 100mg
		Mass  sensativity = station.getBaggingArea().getSensitivityLimit();
		Mass m = sensativity.sum(itemMass2);
		BarcodedItem i= new BarcodedItem(barcode, m);
		this.scanUntilAdded(bitem2);
		station.getBaggingArea().addAnItem(i);
		assertTrue("weight discrepency tedected", !this.session.stateLogic.inState(States.BLOCKED));
	}@Test public void testWeightDiscrepencyWithPowerTurnOnNoDiscrepencyWithinSensativity() {
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		session = new CentralStationLogic(station);
		session.setBypassIssuePrediction(true);
		session.startSession();
		
		//sensativity of the scale is 100mg
		Mass sensativity = station.getBaggingArea().getSensitivityLimit();
		Mass m = sensativity.sum(itemMass2.difference(new Mass(1)).abs());
		BarcodedItem i= new BarcodedItem(barcode, m);
		this.scanUntilAdded(bitem2);
		
		station.getBaggingArea().addAnItem(i);
		assertTrue("weight discrepency tedected", !this.session.stateLogic.inState(States.BLOCKED));
	}
	
	@Test
	public void testWeightDiscrepencyWithPowerTurnOnHasDiscrepency() {
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		session = new CentralStationLogic(station);
		session.setBypassIssuePrediction(true);
		session.startSession();
		
		session.hardware.getHandheldScanner().scan(bitem2);
		this.scanUntilAdded(bitem2);
		station.getBaggingArea().addAnItem(bitem);
		assertTrue("weight discrepancy detected", this.session.stateLogic.inState(States.BLOCKED));
	}
	
	
	@Test public void testWeightDiscrepencyWithPowerTurnOnNoDiscrepencyItemPlacedBack() {
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		session = new CentralStationLogic(station);
		session.setBypassIssuePrediction(true);
		session.startSession();
		
		//station.scanner.scan(bitem2);
		station.getBaggingArea().addAnItem(bitem);
		station.getBaggingArea().removeAnItem(bitem);
		assertTrue("weight discrepency tedected", !this.session.stateLogic.inState(States.BLOCKED));

	}
	
	@Test 
	public void testWeightDiscrepencyWithPowerTurnOnNoDiscrepencyItemPlacedBackAfterOther() {
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		session = new CentralStationLogic(station);
		session.setBypassIssuePrediction(true);
		session.startSession();
		
		this.scanUntilAdded(bitem3);
		station.getBaggingArea().addAnItem(bitem);
		station.getBaggingArea().removeAnItem(bitem);
		station.getBaggingArea().addAnItem(bitem3);
		assertFalse("weight discrepancy detected when shouldn't be", this.session.stateLogic.inState(States.BLOCKED));
	}
	
	@Test public void testWeightDiscrepencyWithPowerTurnOnHasDiscrepencyRescan() {
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		session = new CentralStationLogic(station);
		session.setBypassIssuePrediction(true);
		session.startSession();
		
		this.scanUntilAdded(bitem);
		this.scanUntilAdded(bitem);
		
		assertEquals(1, session.cartLogic.getCart().size());
	}
	
	@Test(expected = SimulationException.class)
	public void testAddExpectedWeightNonExistentBarcode() {
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		session = new CentralStationLogic(station);
		session.setBypassIssuePrediction(true);
		session.startSession();
		
		this.session.weightLogic.addExpectedWeight(new Barcode(new Numeral[] {Numeral.one}));
	}
	
	@Test(expected = SimulationException.class)
	public void testRemoveExpectedWeightNonExistentBarcode() {
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		session = new CentralStationLogic(station);
		session.setBypassIssuePrediction(true);
		session.startSession();
		
		this.session.weightLogic.removeExpectedWeight(new Barcode(new Numeral[] {Numeral.one}));
	}
}
