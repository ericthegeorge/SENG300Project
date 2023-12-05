package com.thelocalmarketplace.software.test.controllers;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.bag.ReusableBag;
import com.jjjwelectronics.scanner.Barcode;
//import com.jjjwelectronics.scanner.BarcodeScanner;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.gui.MainGUI;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import ca.ucalgary.seng300.simulation.SimulationException;
import powerutility.NoPowerException;
import powerutility.PowerGrid;

/**
 * Tests for adding a barcoded item to the cart
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

public class AddItemTests {
	//TODO redo like all of this?
	SelfCheckoutStationBronze station;
	CentralStationLogic session;
	
	//stuff for database
	
	public Barcode barcode;
	public Barcode barcode2;
	public Numeral digits;
	
	public BarcodedItem bitem;
	public Mass itemMass;
	
	public BarcodedItem bitem2;
	public Mass itemMass2;
	public BarcodedItem bitem3;
	public Mass itemMass3;
	
	public BarcodedItem bitem4;
	public Mass itemMass4;
	
	public BarcodedItem bitem5;
	public Mass itemMass5;
	public Numeral[] barcode_numeral;
	public Numeral[] barcode_numeral2;
	public Numeral[] barcode_numeral3;
	public Barcode b_test;
	public BarcodedProduct product;
	public BarcodedProduct product2;
	public BarcodedProduct product3;
	
	@Before public void setUp() {
		PowerGrid.engageUninterruptiblePowerSource();
		PowerGrid.instance().forcePowerRestore();
		
		AbstractSelfCheckoutStation.resetConfigurationToDefaults();
		
		//d1 = new dummyProductDatabaseWithOneItem();
		//d2 = new dummyProductDatabaseWithNoItemsInInventory();
		this.station = new SelfCheckoutStationBronze();
		
		//initialize database
//		session.startSession();
		barcode_numeral = new Numeral[] {Numeral.one, Numeral.two, Numeral.three};
		barcode_numeral2 = new Numeral[] {Numeral.three, Numeral.two, Numeral.three};
		barcode_numeral3 = new Numeral[] {Numeral.three, Numeral.three, Numeral.three};
		barcode = new Barcode(barcode_numeral);
		barcode2 = new Barcode(barcode_numeral2);
		b_test = new Barcode(barcode_numeral3);
		product = new BarcodedProduct(barcode, "some item",(long)5.99,(double)300.0);
		product2 = new BarcodedProduct(barcode2, "some item 2",(long)1.00,(double)300.0);
		product3 = new BarcodedProduct(b_test, "some item 3",(long)1.00,(double)300.0);
		
		ProductDatabases.BARCODED_PRODUCT_DATABASE.clear();
		ProductDatabases.INVENTORY.clear();
		
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode, product);
		ProductDatabases.INVENTORY.put(product, 1);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode2, product2);
		ProductDatabases.INVENTORY.put(product2, 1);	

		//initialize barcoded item
		itemMass = new Mass((long) 1000000);
		bitem = new BarcodedItem(barcode2, itemMass);
		itemMass2 = new Mass((double) 300.0);//300.0 grams
		bitem2 = new BarcodedItem(barcode, itemMass2);
		itemMass3 = new Mass((double) 300.0);//3.0 grams
		bitem3 = new BarcodedItem(barcode, itemMass3);
		bitem4 = new BarcodedItem(b_test, itemMass3);
		itemMass5 = new Mass((double) 300.0);
		bitem5 = new BarcodedItem(barcode2,itemMass5);
		
	    station.plugIn(PowerGrid.instance());
	    station.turnOn();
	    session = new CentralStationLogic(station);
	    session.setBypassIssuePrediction(true);
	    session.startSession();
	}
	
	@After
	public void tearDown() {
		PowerGrid.engageUninterruptiblePowerSource();
	}
	//the following function was taken mainly from Angelina's tests for bulkyitems
	public void scanUntilAdded(Product p, BarcodedItem b) {
		int cnt = 0;
		
		while(cnt< 10000 && !session.cartLogic.getCart().containsKey(p)) {
			station.getHandheldScanner().scan(b);
			cnt++;
		}
	}
	
	//Test for when there is no power for the barcode to be scanned
	@Test (expected = NoPowerException.class) public void testNoPower(){
		station.plugIn(PowerGrid.instance());
		station.turnOff();
		session = new CentralStationLogic(station);
	    session.setBypassIssuePrediction(true);
		session.startSession();
		this.scanUntilAdded(product, bitem2);
	}
	@Test(expected = InvalidStateSimulationException.class)
	public void testAddBarcodeStationBlocked() throws SimulationException {
	    session.stateLogic.gotoState(States.BLOCKED);

//	    Barcode barcode = new Barcode(new Numeral[]{Numeral.one, Numeral.two, Numeral.three});
	    session.addBarcodedProductController.addBarcode(barcode);
	}
	
	@Test
	public void testAddBarcodeStationNormal() throws SimulationException {
	    System.out.println("The session started status is "+session.isSessionStarted());
	    session.stateLogic.gotoState(States.NORMAL);
	    Mass oldmass = session.weightLogic.getExpectedWeight();
	    //System.out.println("Old Mass was"+ oldmass.inGrams());

//	    Barcode barcode = new Barcode(new Numeral[]{Numeral.one, Numeral.two, Numeral.three});
	    session.addBarcodedProductController.addBarcode(barcode);
	    Mass newmass = session.weightLogic.getExpectedWeight();
	    //System.out.println("New Mass is"+ newmass.inGrams());
	    assertTrue(oldmass.compareTo(newmass)!= 0);
	}
	
	@Test(expected = InvalidStateSimulationException.class)
	public void testAddBarcodeSessionNotStarted2() throws SimulationException {
	    station.plugIn(PowerGrid.instance());
	    station.turnOn();
	    session = new CentralStationLogic(station);

	    Barcode barcode = new Barcode(new Numeral[]{Numeral.one, Numeral.two, Numeral.three});
	    session.addBarcodedProductController.addBarcode(barcode);
	}
//
	@Test(expected = InvalidStateSimulationException.class)
	public void testAddBarcodeStationBlocked2() throws SimulationException {
	    session = new CentralStationLogic(station);
	    session.setBypassIssuePrediction(true);
	    session.startSession();
	    session.stateLogic.gotoState(States.NORMAL);
	    session.addBarcodedProductController.addBarcode(b_test);
	}

	@Test(expected = NullPointerException.class)
	public void testAddNullBarcode2() throws SimulationException {
	    session.addBarcodedProductController.addBarcode(null);
		//dummy weight
		station.getBaggingArea().addAnItem(new ReusableBag());
	}
	
	@Test(expected = NullPointerException.class)
	public void addNullPLU() {
		session.addPLUCodedProductController.addPLUCode(null);
		//dummy weight
		station.getBaggingArea().addAnItem(new ReusableBag());
	    
	}
	@Test(expected = InvalidStateSimulationException.class)
	public void addPLUSessionNotStarted() {
	    session = new CentralStationLogic(station);
		session.addPLUCodedProductController.addPLUCode(new PriceLookUpCode("1111"));
		//dummy weight
		station.getBaggingArea().addAnItem(new ReusableBag());
	}
	@Test(expected = InvalidStateSimulationException.class)
	public void addPLUStateBlocked() {
	    session.stateLogic.gotoState(States.BLOCKED);
		session.addPLUCodedProductController.addPLUCode(new PriceLookUpCode("1111"));
		//dummy weight
		station.getBaggingArea().addAnItem(new ReusableBag());
	}
	@Test(expected = InvalidStateSimulationException.class)
	public void addPLUNotInDatabase() {
		//PLU1111 does not have an associated product in the database.
		session.addPLUCodedProductController.addPLUCode(new PriceLookUpCode("1111"));
		//dummy weight
		station.getBaggingArea().addAnItem(new ReusableBag());
	}
	@Test(expected = InvalidStateSimulationException.class)
	public void addPLUNoInventory() {
		//PLU1111 does not have any inventory in the product database.
		ProductDatabases.PLU_PRODUCT_DATABASE.put(new PriceLookUpCode("1111"), new PLUCodedProduct(new PriceLookUpCode("1111"), "desc", 1));
		session.addPLUCodedProductController.addPLUCode(new PriceLookUpCode("1111"));
		//dummy weight
		station.getBaggingArea().addAnItem(new ReusableBag());
	}

}
