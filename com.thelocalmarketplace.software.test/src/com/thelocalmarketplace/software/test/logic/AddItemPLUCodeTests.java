package com.thelocalmarketplace.software.test.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.controllers.item.AddPLUCodedItemController;
import com.thelocalmarketplace.software.logic.AttendantLogic;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import powerutility.PowerGrid;


import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.software.logic.StateLogic.States;

public class AddItemPLUCodeTests {
	SelfCheckoutStationBronze station;
	CentralStationLogic session;
	
	AddPLUCodedItemController addPLUCodedItemController;
	
	//stuff for database
	
	public Barcode barcode;
	public Barcode barcode2;
	public Numeral digits;
	public Barcode nullBarcode;
	
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
	
	public BarcodedItem bitem6;
	public Mass itemMass6;
	
	public Numeral[] barcode_numeral;
	public Numeral[] barcode_numeral2;
	public Numeral[] barcode_numeral3;
	public Barcode barcode3;
	public BarcodedProduct product;
	public BarcodedProduct product2;
	public BarcodedProduct product3;
	public BarcodedProduct nullProduct;

	@Before 
	public void setUp() {
		PowerGrid.engageUninterruptiblePowerSource();
		PowerGrid.instance().forcePowerRestore();
		
		AbstractSelfCheckoutStation.resetConfigurationToDefaults();

		this.station = new SelfCheckoutStationBronze();
		
		
		//initialize database
		barcode_numeral = new Numeral[] {Numeral.one, Numeral.two, Numeral.three};
		barcode_numeral2 = new Numeral[] {Numeral.three, Numeral.two, Numeral.three};
		barcode_numeral3 = new Numeral[] {Numeral.three, Numeral.three, Numeral.three};
		barcode = new Barcode(barcode_numeral);
		barcode2 = new Barcode(barcode_numeral2);
		barcode3 = new Barcode(barcode_numeral3);

		
		product = new BarcodedProduct(barcode, "some item",(long)5.99,(double)400.0);
		product2 = new BarcodedProduct(barcode2, "some item 2",(long)1.00,(double)300.0);

		ProductDatabases.BARCODED_PRODUCT_DATABASE.clear();
		ProductDatabases.INVENTORY.clear();
		
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode, product);
		ProductDatabases.INVENTORY.put(product, 1);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode2, product2);
		ProductDatabases.INVENTORY.put(product2, 1);	


		itemMass = new Mass((double) 400.0);
		bitem = new BarcodedItem(barcode, itemMass);
		itemMass2 = new Mass((double) 300.0);//300.0 grams
		bitem2 = new BarcodedItem(barcode2, itemMass2);
		itemMass3 = new Mass((double) 300.0);//3.0 grams
		bitem3 = new BarcodedItem(barcode3, itemMass3);
		//bitem4 = new BarcodedItem(b_test, itemMass3);
		itemMass5 = new Mass((double) 300.0);
		bitem5 = new BarcodedItem(barcode2,itemMass5);
		
		itemMass6 = new Mass((double) 10.0);
		bitem6 = new BarcodedItem(barcode2,itemMass6);
		
		BarcodedProduct bproduct = new BarcodedProduct(bitem.getBarcode(), "bitem", 1, 400.0);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(bitem.getBarcode(), bproduct);
		ProductDatabases.INVENTORY.put(bproduct, 10);
		
		BarcodedProduct bproduct2 = new BarcodedProduct(bitem2.getBarcode(), "bitem2", 2, 300.0);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(bitem2.getBarcode(), bproduct2);
		ProductDatabases.INVENTORY.put(bproduct2, 10);
		
		// Add the PriceLookUpCode to the product database
        PriceLookUpCode validPLUCode = new PriceLookUpCode("1234");
        PLUCodedProduct pluProduct = new PLUCodedProduct(validPLUCode, "Product Description", 10);
        ProductDatabases.PLU_PRODUCT_DATABASE.put(validPLUCode, pluProduct);
		
		//Initialize station
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		session = new CentralStationLogic(station);
		session.setBypassIssuePrediction(true);
		session.startSession();
		
		addPLUCodedItemController = new AddPLUCodedItemController(session);
	}
	@Rule
	public TestName name = new TestName();
	
	@After
	public void displayTest() {
		System.out.println(name.getMethodName() + " has completed. \n");
	}
	
	//the following function was taken mainly from Angelina's tests for bulkyitems
	/*public void scanUntilAdded(Product p, BarcodedItem b) {
		int cnt = 0;
		
		while(cnt< 10000 && !session.cartLogic.getCart().containsKey(p)) {
			station.getHandheldScanner().scan(b);
			cnt++;
		}
	}*/
	
	public void putTestBarcodedItemInCart(BarcodedItem item) {
		long itemPrice;
		if (item.getBarcode() == product.getBarcode()) {
			itemPrice = product.getPrice();
		}
		else { //if (item.getBarcode() == product2.getBarcode()) {
			itemPrice = product2.getPrice();
		}
		session.addBarcodedProductController.addBarcode(item.getBarcode());
	}
	
	
    @Test
    public void testAddPLUCode_ValidPLUCode() {
        PriceLookUpCode validPLUCode = new PriceLookUpCode("1234");

        addPLUCodedItemController.addPLUCode(validPLUCode);

        assertTrue(addPLUCodedItemController.getAwaitingPLUMeasurement());

    }
        
    @Test(expected = NullPointerException.class)
    public void testAddPLUCode_NullPLUCode() {
        // Adding a null PLU code
    	addPLUCodedItemController.addPLUCode(null);
    }
    
    @Test(expected = InvalidStateSimulationException.class)
    public void testAddPLUCode_SessionNotStarted() {
        // Add PLU code when the session is not started
    	addPLUCodedItemController.addPLUCode(new PriceLookUpCode("5678"));
    }
    
    @Test(expected = InvalidStateSimulationException.class)
    public void testAddPLUCode_StationBlocked() {
        // Assume session has begun but station is blocked
        session.startSession();
        session.stateLogic.gotoState(States.BLOCKED);

        // Add PLU code when the station is blocked
        addPLUCodedItemController.addPLUCode(new PriceLookUpCode("5678"));
    }
    
    @Test(expected = InvalidStateSimulationException.class)
    public void testAddPLUCode_InvalidPLUCode() {
        // Assume session has begun and station is not blocked
        session.startSession();
        session.stateLogic.gotoState(States.NORMAL);

        // Add an invalid PLU code that is not in the product database
        addPLUCodedItemController.addPLUCode(new PriceLookUpCode("9999"));
    }
    
    
    

}
