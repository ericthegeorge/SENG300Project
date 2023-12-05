package com.thelocalmarketplace.software.test.controllers;

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
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.logic.AttendantLogic;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.InvalidArgumentSimulationException;
import powerutility.PowerGrid;

/**
 * Tests to Handle Bulky Items 
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
public class HandleBulkyItemTests {
	
	private SelfCheckoutStationGold station;
	private CentralStationLogic session;
	private BarcodedItem barcodedItem;
	private BarcodedProduct product;
	
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
	
	@Before
	public void setUp() {
		PowerGrid.engageUninterruptiblePowerSource();
		PowerGrid.instance().forcePowerRestore();
		AbstractSelfCheckoutStation.resetConfigurationToDefaults();
		
		station = new SelfCheckoutStationGold();
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		session = new CentralStationLogic(station);
		session.setBypassIssuePrediction(true);
		session.startSession();
		
		Barcode barcode = new Barcode(new Numeral[] {Numeral.one});
		barcodedItem = new BarcodedItem(barcode, Mass.ONE_GRAM);
		product = new BarcodedProduct(barcode, "item", 5, barcodedItem.getMass().inGrams().doubleValue());
		
		ProductDatabases.BARCODED_PRODUCT_DATABASE.clear();
		ProductDatabases.INVENTORY.clear();
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode, product);
		ProductDatabases.INVENTORY.put(product, 1);
	}
	
	@After 
	public void tearDown() {
		PowerGrid.engageFaultyPowerSource();
	}
	
	@Test 
	public void testSkipBaggingNotifiesAttendant() {
		AttendantLogicStub attendantLogic = new AttendantLogicStub(session);
		session.attendantLogic = attendantLogic;
		scanUntilAdded(barcodedItem);
		session.weightLogic.skipBaggingRequest(barcodedItem.getBarcode());
		assertTrue(attendantLogic.requestApprovalCalled);
	}
	
	@Test
	public void testSkipBaggingBlocksStation() {
		scanUntilAdded(barcodedItem);
		session.weightLogic.skipBaggingRequest(barcodedItem.getBarcode());
		assertTrue(this.session.stateLogic.inState(States.BLOCKED));
	}
	
	@Test (expected = InvalidArgumentSimulationException.class)
	public void testSkipBaggingNullBarcode() {
		session.weightLogic.skipBaggingRequest(null);
	}
	
	// Expected reaction not clear; we have therefore assumed unblocking when weight discrepancy removed is expected
	@Test 
	public void testSkipBaggingAddsAnyways() {
		scanUntilAdded(barcodedItem);
		session.weightLogic.skipBaggingRequest(barcodedItem.getBarcode());
		station.getBaggingArea().addAnItem(barcodedItem);
		assertFalse(this.session.stateLogic.inState(States.BLOCKED));
	}
	
	@Test
	public void testAttendantApprovalReducesExceptedWeight() {
		scanUntilAdded(barcodedItem);
		session.weightLogic.skipBaggingRequest(barcodedItem.getBarcode());
		session.attendantLogic.grantApprovalSkipBagging(barcodedItem.getBarcode());
		assertFalse(session.weightLogic.checkWeightDiscrepancy());
	}
	
	@Test 
	public void testAttendantApprovalUnblocksStation() {
		scanUntilAdded(barcodedItem);
		session.weightLogic.skipBaggingRequest(barcodedItem.getBarcode());
		session.attendantLogic.grantApprovalSkipBagging(barcodedItem.getBarcode());
		assertFalse(this.session.stateLogic.inState(States.BLOCKED)); // Ensures no longer blocked
	}
	
	@Test 
	public void testAttendantApprovalStaysBlockedIfDiscrepancyRemains() {
		scanUntilAdded(barcodedItem);
		session.weightLogic.skipBaggingRequest(barcodedItem.getBarcode());
		session.attendantLogic.grantApprovalSkipBagging(barcodedItem.getBarcode());
	}
	
	@Test (expected = InvalidArgumentSimulationException.class)
	public void testAttendantApprovalNullBarcode() {
		session.attendantLogic.grantApprovalSkipBagging(null);
	}

	
	private class AttendantLogicStub extends AttendantLogic {
		public boolean requestApprovalCalled = false;
		
		public AttendantLogicStub(CentralStationLogic l) {super(l);}
		@Override 
		public void requestApprovalSkipBagging(Barcode barcode) {
			requestApprovalCalled = true;
		}
		
		
	}
	

}
