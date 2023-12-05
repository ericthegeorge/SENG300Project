package com.thelocalmarketplace.software.test.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.gui.MainGUI;
import com.thelocalmarketplace.software.logic.CentralStationLogic;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import ca.ucalgary.seng300.simulation.SimulationException;
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

public class CartLogicTests {
	
	SelfCheckoutStationBronze station;
	CentralStationLogic logic;
	
	public Barcode barcode;
	public Numeral digits;
	
	public BarcodedItem bitem;

	public Numeral[] barcode_numeral;
	public Numeral[] barcode_numeral2;
	public Numeral[] barcode_numeral3;
	public Barcode b_test;
	public Barcode barcode2;
	public BarcodedProduct product;
	public BarcodedProduct product2;
	public BarcodedProduct product3;
	
	@Before public void setUp() {
		PowerGrid.engageUninterruptiblePowerSource();
		PowerGrid.instance().forcePowerRestore();
		
		AbstractSelfCheckoutStation.resetConfigurationToDefaults();
		
		barcode_numeral = new Numeral[]{Numeral.one,Numeral.two, Numeral.three};
		barcode_numeral2 = new Numeral[]{Numeral.three,Numeral.two, Numeral.three};
		barcode_numeral3 = new Numeral[]{Numeral.three,Numeral.three, Numeral.three};
		barcode = new Barcode(barcode_numeral);
		barcode2 = new Barcode(barcode_numeral2);
		b_test = new Barcode(barcode_numeral3);
		product = new BarcodedProduct(barcode, "some item",5,(double)3.0);
		product2 = new BarcodedProduct(barcode2, "some item 2",(long)1.00,(double)300.0);
		product3 = new BarcodedProduct(b_test, "some item 3",(long)1.00,(double)3.0);
		
		ProductDatabases.BARCODED_PRODUCT_DATABASE.clear();
		ProductDatabases.INVENTORY.clear();
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode, product);
		ProductDatabases.INVENTORY.put(product, 1);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode2, product2);
		ProductDatabases.INVENTORY.put(product2, 1);
		
		station = new SelfCheckoutStationBronze();
		station.plugIn(PowerGrid.instance());
		station.turnOn();
		
		logic = new CentralStationLogic(station);
		logic.setBypassIssuePrediction(true);
		logic.startSession();
	}
	
	public BarcodedItem testMethodToFindBarcodedItemInCart(Barcode barcode) {
		Map<Item, Integer> cartItems = this.logic.cartLogic.getCart();
		
		if (barcode == null) {
			throw new NullPointerException("Price lookup code is null");
		}
		else if (!this.logic.isSessionStarted()) {
			throw new InvalidStateSimulationException("The session has not been started");
		}
		
		for (Entry<Item, Integer> entry : cartItems.entrySet()) {
            Item item = entry.getKey();
            
            if (item instanceof BarcodedItem) {
            	BarcodedItem barcodedItem = (BarcodedItem) item;
            	if (barcode == barcodedItem.getBarcode()) {
            		return barcodedItem;
            	}
            }
		}
		
		throw new InvalidStateSimulationException("Product not in cart");
		
	}
	
	@Test public void updatePriceOfCartTest() {
		BigDecimal price1 = new BigDecimal(50.0);
		logic.cartLogic.updateBalance(price1);
		assertTrue("price of cart was not updated correctly when adding to it", logic.cartLogic.getBalanceOwed().equals(price1));
	}@Test public void updateNegativePriceOfCartTest() {
		BigDecimal price1 = new BigDecimal(-50.0);
		logic.cartLogic.updateBalance(price1);
		assertTrue("price of cart was not updated correctly when subtracting from it", logic.cartLogic.getBalanceOwed().equals(price1));
	}@Test public void addProductToCartTestCheckPrice() {
		logic.cartLogic.addBarcodedProductToCart(barcode);
		assertTrue("price of cart was not updated correctly after adding to cart", logic.cartLogic.getBalanceOwed().equals(new BigDecimal(5)));
	}@Test public void addMultipleProductToCartTestCheckPrice() {
		BigDecimal price1 = new BigDecimal(5);
		BigDecimal price2 = new BigDecimal((long)1.00);
		BigDecimal expected = price1.add(price2);
		logic.cartLogic.addBarcodedProductToCart(barcode);
		logic.cartLogic.addBarcodedProductToCart(barcode2);
		assertTrue("price of cart was not updated correctly after adding to cart", logic.cartLogic.getBalanceOwed().equals(expected));
	}@Test public void addMultipleItemsToCartTestGetTotalPrice() {
		BigDecimal price1 = new BigDecimal(5);
		BigDecimal price2 = new BigDecimal((long)1.00);
		BigDecimal expected = price1.add(price2);
		logic.cartLogic.addBarcodedProductToCart(barcode);
		logic.cartLogic.addBarcodedProductToCart(barcode2);
		assertTrue("price of cart was not calculated correctly", logic.cartLogic.calculateTotalCost().equals(expected));
	}
	
	@Test
	public void testRemoveBarcodedItemFromCart() {
		logic.cartLogic.addBarcodedProductToCart(barcode);
		assertEquals(1, logic.cartLogic.getCart().size());
		
		BarcodedItem bitem = testMethodToFindBarcodedItemInCart(barcode);
		
		logic.cartLogic.removeProductFromCart(bitem);
		assertEquals(0, logic.cartLogic.getCart().size());
	}
	
	@Test(expected = SimulationException.class)
	public void testRemoveNonExistentBarcodedItemFromCart() {
		BarcodedItem bitem = testMethodToFindBarcodedItemInCart(barcode);
		logic.cartLogic.removeProductFromCart(bitem);
	}
	
	@Test(expected = SimulationException.class)
	public void testAddBarcodeNotInDatabase() {
		Barcode b = new Barcode(new Numeral[] {Numeral.one});
		
		logic.cartLogic.addBarcodedProductToCart(b);
	}
	
	@Test(expected = SimulationException.class)
	public void testAddBarcodeNotInInventory2() {
		Barcode b = new Barcode(new Numeral[] {Numeral.two});
		BarcodedProduct p = new BarcodedProduct(b, "some item", 5, 4.0);
		
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(b, p);
		
		logic.cartLogic.addBarcodedProductToCart(b);
	}
	
	@Test
	public void testModifyBalanceAdd() {
		logic.cartLogic.modifyBalance(new BigDecimal(5));
		logic.cartLogic.modifyBalance(new BigDecimal(3));
		
		assertEquals(new BigDecimal(8).setScale(5, RoundingMode.HALF_DOWN), logic.cartLogic.getBalanceOwed().setScale(5, RoundingMode.HALF_DOWN));
	}
	
	@Test
	public void testModifyBalanceSubtract() {
		logic.cartLogic.modifyBalance(new BigDecimal(3));
		logic.cartLogic.modifyBalance(new BigDecimal(-5));
		
		assertEquals(new BigDecimal(0).setScale(5, RoundingMode.HALF_DOWN), logic.cartLogic.getBalanceOwed().setScale(5, RoundingMode.HALF_DOWN));
	}
	
	@Test
	public void testCalculatePriceOfPLU() {
		BigInteger micrograms = new BigInteger("5000000000");
		Mass mass = new Mass(micrograms);
		double price = logic.cartLogic.calculatePriceOfPLU(5, mass);
		assertTrue(25 == price);
	}
}
