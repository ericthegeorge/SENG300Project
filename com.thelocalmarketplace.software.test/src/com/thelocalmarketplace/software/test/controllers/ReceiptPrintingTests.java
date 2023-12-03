package com.thelocalmarketplace.software.test.controllers;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.OverloadedDevice;

import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;

import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.controllers.ReceiptPrintingController;
import com.thelocalmarketplace.software.logic.AttendantLogic;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import powerutility.PowerGrid;

/**
 * @author Phuong Le (30175125)
 * ----------------------------
 * @author Angelina Rochon (30087177)
 * @author Connell Reffo (10186960)
 * @author Tara Strickland (10105877)
 * @author Julian Fan (30235289)
 * @author Braden Beler (30084941)
 * @author Samyog Dahal (30194624)
 * @author Maheen Nizmani (30172615)
 * @author Daniel Yakimenka (10185055)
 * @author Merick Parkinson (30196225)
 * @author Farida Elogueil (30171114)
 */
public class ReceiptPrintingTests {

    private CentralStationLogic session;
    private SelfCheckoutStationBronze station;
    private ReceiptPrintingController controller;
    
	public BarcodedItem bitem;
	public Mass itemMass;
	public Numeral[] barcode_numeral;
	public BarcodedProduct product;
	public Barcode barcode;
	public Numeral digits;
    

    @Before
    public void setUp() {
        PowerGrid.engageUninterruptiblePowerSource();
        PowerGrid.instance().forcePowerRestore();
        AbstractSelfCheckoutStation.resetConfigurationToDefaults();
        station = new SelfCheckoutStationBronze();
        station.plugIn(PowerGrid.instance());
        station.turnOn();
        session = new CentralStationLogic(station);
		session.setBypassIssuePrediction(true);
        session.startSession();
        controller = session.receiptPrintingController;
        
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
    }

    @After
    public void tearDown() {
        PowerGrid.engageUninterruptiblePowerSource();
    }
    
    @Test
    public void testHandlePrintReceiptWithoutInk() throws OverloadedDevice {    
        session.cartLogic.addBarcodedProductToCart(bitem.getBarcode());      	
        station.getPrinter().addPaper(1000);
        controller.handlePrintReceipt(new BigDecimal(0));
        assertTrue(this.session.stateLogic.inState(States.SUSPENDED));
    }
    
    @Test
    public void testHandlePrintReceiptWithoutPaper() throws OverloadedDevice {
        session.cartLogic.addBarcodedProductToCart(bitem.getBarcode());  
        station.getPrinter().addInk(1000);
        controller.handlePrintReceipt(new BigDecimal(0));
        
        assertTrue(this.session.stateLogic.inState(States.SUSPENDED));
    }
    
    @Test
    public void testPrintReceiptWithPaperandInk() throws OverloadedDevice {
        session.cartLogic.addBarcodedProductToCart(bitem.getBarcode());  
        station.getPrinter().addInk(1000);
        station.getPrinter().addPaper(1000);
        controller.handlePrintReceipt(new BigDecimal(0));
        
        assertNotEquals(this.session.stateLogic.getState(), States.SUSPENDED);
    }
    
    @Test
    public void testNotifyOutofInk() throws OverloadedDevice {
        session.cartLogic.addBarcodedProductToCart(bitem.getBarcode());  
        station.getPrinter().addInk(5);
        station.getPrinter().addPaper(5);
        controller.handlePrintReceipt(new BigDecimal(0));
        
        assertEquals(this.session.stateLogic.getState(), States.SUSPENDED);
    }
    
    @Test
    public void testNotifyOutofPaper() throws OverloadedDevice {
        session.cartLogic.addBarcodedProductToCart(bitem.getBarcode());  
        station.getPrinter().addInk(1000);
        station.getPrinter().addPaper(1);
        controller.handlePrintReceipt(new BigDecimal(0));
        
        assertEquals(this.session.stateLogic.getState(), States.SUSPENDED);
    }
    
    @Test
    public void testAttendantResolvingError() throws OverloadedDevice {
        session.cartLogic.addBarcodedProductToCart(bitem.getBarcode());  
        station.getPrinter().addInk(1000);
        station.getPrinter().addPaper(1);
        controller.handlePrintReceipt(new BigDecimal(0));
        
        assertEquals(this.session.stateLogic.getState(), States.SUSPENDED);
        
        station.getPrinter().addPaper(100);
        AttendantLogic attendant = new AttendantLogic(session);
        attendant.printDuplicateReceipt();
        assertEquals(this.session.stateLogic.getState(), States.NORMAL);
    }
@Test 
    public void testLowInkforHighLevel() throws OverloadedDevice {
    	session.cartLogic.addBarcodedProductToCart(bitem.getBarcode());  
        station.getPrinter().addInk(1000);
        station.getPrinter().addPaper(100);
        controller.handlePrintReceipt(new BigDecimal(0));
        boolean lowInk = controller.getLowInk();

        assertEquals(this.session.stateLogic.getState(), States.NORMAL);
        
        assertFalse(lowInk);
    }
    @Test 
    public void testLowInkforLowLevel() throws OverloadedDevice {
    	session.cartLogic.addBarcodedProductToCart(bitem.getBarcode());  
        station.getPrinter().addInk(126);
        station.getPrinter().addPaper(100);
        controller.handlePrintReceipt(new BigDecimal(0));

        boolean lowInk = controller.getLowInk();
        System.out.println(lowInk);

        assertEquals(this.session.stateLogic.getState(), States.NORMAL);
        
        assertFalse(lowInk);
    }
    
    @Test
    public void testLowPaperforHighLevel() throws OverloadedDevice {
    	session.cartLogic.addBarcodedProductToCart(bitem.getBarcode());  
        station.getPrinter().addInk(1000);
        station.getPrinter().addPaper(800);
        controller.handlePrintReceipt(new BigDecimal(0));
        
        boolean lowPaper = controller.getLowPaper();
        
        assertEquals(this.session.stateLogic.getState(), States.NORMAL);

        assertFalse(lowPaper);
    	
    }
    
    @Test
    public void testLowPaperforLowLevel() throws OverloadedDevice {
    	session.cartLogic.addBarcodedProductToCart(bitem.getBarcode());  
        station.getPrinter().addInk(1000);
        station.getPrinter().addPaper(2);
        controller.handlePrintReceipt(new BigDecimal(0));
        
        boolean lowPaper = controller.getLowPaper();
        
        assertEquals(this.session.stateLogic.getState(), States.SUSPENDED);

        assertFalse(lowPaper);
    }

}

