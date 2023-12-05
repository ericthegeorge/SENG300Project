package com.thelocalmarketplace.software.test.logic;

import static org.junit.Assert.assertEquals;
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
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
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

public class RemoveItemTests {

		SelfCheckoutStationBronze station;
		CentralStationLogic session;
		
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
		public Numeral[] barcode_numeral;
		public Numeral[] barcode_numeral2;
		public Numeral[] barcode_numeral3;
		public Barcode barcode3;
		public BarcodedProduct product;
		public BarcodedProduct product2;
		public BarcodedProduct product3;
		public BarcodedProduct nullProduct;
		
		public PriceLookUpCode plucode1;
		public PriceLookUpCode plucode2;
		public PLUCodedItem pluitem1;
		public PLUCodedItem pluitem2;
		public PLUCodedProduct pluProduct1;
		public PLUCodedProduct pluProduct2;
		
		@Before public void setUp() {
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
			plucode1 = new PriceLookUpCode("1234");
			plucode2 = new PriceLookUpCode("1235");

			
			product = new BarcodedProduct(barcode, "some item",(long)5.99,(double)400.0);
			product2 = new BarcodedProduct(barcode2, "some item 2",(long)1.00,(double)300.0);
			pluProduct1 = new PLUCodedProduct(plucode1, "some plu item 1", (long)3.00);
			pluProduct2 = new PLUCodedProduct(plucode2, "some plu item 2", (long)5.00);

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
			
			BarcodedProduct bproduct = new BarcodedProduct(bitem.getBarcode(), "bitem", 1, 400.0);
			ProductDatabases.BARCODED_PRODUCT_DATABASE.put(bitem.getBarcode(), bproduct);
			ProductDatabases.INVENTORY.put(bproduct, 5);
			
			BarcodedProduct bproduct2 = new BarcodedProduct(bitem2.getBarcode(), "bitem2", 2, 300.0);
			ProductDatabases.BARCODED_PRODUCT_DATABASE.put(bitem2.getBarcode(), bproduct2);
			ProductDatabases.INVENTORY.put(bproduct2, 5);
			
			pluitem1 = new PLUCodedItem(plucode1, itemMass);
			pluitem2 = new PLUCodedItem(plucode2, itemMass2);
			
			ProductDatabases.PLU_PRODUCT_DATABASE.clear();
			
			ProductDatabases.PLU_PRODUCT_DATABASE.put(plucode1, pluProduct1);
			ProductDatabases.INVENTORY.put(pluProduct1, 5);
			
			ProductDatabases.PLU_PRODUCT_DATABASE.put(plucode2, pluProduct2);
			ProductDatabases.INVENTORY.put(pluProduct2, 5);
			
			//Initialize station
			station.plugIn(PowerGrid.instance());
			station.turnOn();
			
			session = new CentralStationLogic(station);
			session.setBypassIssuePrediction(true);
			session.startSession();
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
		public void putTestPLUCodedItemInCart(PLUCodedItem item) {
			session.addPLUCodedProductController.addPLUCode(item.getPLUCode());
			station.getBaggingArea().addAnItem(item);
		}
		public void removeTestPLUCodedItemFromCart(PLUCodedItem item) {
			session.removeItemLogic.checkCartForPLUCodedItemToRemove(item.getPLUCode());
			station.getBaggingArea().removeAnItem(item);
		}
		
		/** Tests if the method actually removes an item from the cart when called
		 * 
		 */
		@Test
		public void testSuccessfulBarcodeItemRemoval() {
			//this.scanUntilAdded(product, bitem);
			this.putTestBarcodedItemInCart(bitem);
			assertTrue(session.cartLogic.getCart().size() == 1);
			
			station.getBaggingArea().addAnItem(bitem);
			session.removeItemLogic.removeBarcodedItem(bitem);
			assertEquals(0, session.cartLogic.getCart().size());
			
			System.out.println("Test 1 end\n");
			
		}
		/** Tests if the method actually removes a plu item from the cart when called
		 * 
		 */
		@Test
		public void testSuccessfulPLUItemRemoval() {
			this.putTestPLUCodedItemInCart(pluitem1);
			assertTrue(session.cartLogic.getCart().size() == 1);
			
			this.removeTestPLUCodedItemFromCart(pluitem1);
			assertEquals(0, session.cartLogic.getCart().size());
			
			System.out.println("Test 1 end\n");
			
		}
		/** Tests if the station is blocked while the correct item isn't removed, and unblocked when it is removed
		 * 
		 */
		@Test
		public void testPostRemovalBlock() {
			//this.scanUntilAdded(product, bitem);
			this.putTestBarcodedItemInCart(bitem);
			station.getBaggingArea().addAnItem(bitem);
			
			session.removeItemLogic.removeBarcodedItem(bitem);
			assertTrue(session.stateLogic.getState() == States.BLOCKED);
			
			station.getBaggingArea().removeAnItem(bitem);
			assertTrue(session.stateLogic.getState() == States.NORMAL);
			System.out.println("Test 2 end\n");
		}

		/**
		 * Tests if removing the wrong item from bagging area causes a weight discrepancy
		 */
		@Test
		public void testIncorrectRemovalBarcode() {
			//this.scanUntilAdded(product, bitem);
			this.putTestBarcodedItemInCart(bitem);
			station.getBaggingArea().addAnItem(bitem);
			
			//this.scanUntilAdded(product2, bitem2);
			this.putTestBarcodedItemInCart(bitem2);
			station.getBaggingArea().addAnItem(bitem2);
			
			session.removeItemLogic.removeBarcodedItem(bitem);
			station.getBaggingArea().removeAnItem(bitem2);
			
			assertTrue(session.stateLogic.getState() == States.BLOCKED);
			
			station.getBaggingArea().addAnItem(bitem2);
			station.getBaggingArea().removeAnItem(bitem);
			assertTrue(session.stateLogic.getState() == States.NORMAL);	
			
			System.out.println("Test 3 end\n");
		}
		/**
		 * Tests that you can't remove the wrong PLU coded item from the bagging area
		 */
		@Test (expected = InvalidStateSimulationException.class)
		public void testIncorrectRemovalPLU() {
			this.putTestPLUCodedItemInCart(pluitem1);
			this.putTestPLUCodedItemInCart(pluitem2);
			
			session.removeItemLogic.checkCartForPLUCodedItemToRemove(pluitem1.getPLUCode());
			station.getBaggingArea().removeAnItem(pluitem2);
		}
		/**
		 * Tests if method fails on a null item.
		 */
		
		
		@Test (expected = NullPointerException.class)
		public void failOnNullItemBarcode() {
			session.removeItemLogic.removeBarcodedItem(null);
			
			System.out.println("Test 4 end\n");
			
		}
		
		@Test (expected = NullPointerException.class)
		public void failOnNullItemPLU() {
			session.removeItemLogic.checkCartForPLUCodedItemToRemove(null);
			
			System.out.println("Test 4 end\n");
			
		}
		
		/**
		 * Tests if method fails on removal request of an item not in cart.
		 */
		@Test (expected = InvalidStateSimulationException.class)
		public void failOnItemNotInCart() {
			session.removeItemLogic.removeBarcodedItem(bitem);
			
			System.out.println("Test 5 end\n");
		}
		
		/**
		 * Tests if method fails on removal request of an item of a product not in database.
		 */
		@Test (expected = InvalidStateSimulationException.class)
		public void failOnItemNotInDatabasePLU() {
			this.putTestPLUCodedItemInCart(pluitem1);
			
			ProductDatabases.PLU_PRODUCT_DATABASE.clear();
			
			this.removeTestPLUCodedItemFromCart(pluitem1);
			
			System.out.println("Test 5 end\n");
		}
		
		/**
		 * Tests if method can resolve a weight discrepancy event caused by not putting an added item into the bagging area
		 */
		@Test
		public void resolveWeightDescrepancyByRemovalBarcode() {
			//this.scanUntilAdded(product, bitem);
			this.putTestBarcodedItemInCart(bitem);
			assertTrue(session.stateLogic.getState() == States.BLOCKED);

			session.removeItemLogic.removeBarcodedItem(bitem);
			assertTrue(session.stateLogic.getState() == States.NORMAL);	
			
			System.out.println("Test 6 end\n");
			
		}
		/**
		 * Tests if method can resolve a weight discrepancy event
		 */
		@Test
		public void resolveWeightDescrepancyByRemovalPLU() {
			this.putTestPLUCodedItemInCart(pluitem1);
			session.stateLogic.gotoState(States.BLOCKED);

			this.removeTestPLUCodedItemFromCart(pluitem1);
			assertTrue(session.stateLogic.getState() == States.NORMAL);	
			
			System.out.println("Test 6 end\n");
			
		}
		
		/**
		 * Tests if the method fails when a session hasn't been started
		 */
		@Test (expected = InvalidStateSimulationException.class)
		public void failOnNullSessionBarcode() {
			session.stopSession();
			session.removeItemLogic.removeBarcodedItem(bitem);
		}
		
		/**
		 * Tests if the method fails when a session hasn't been started
		 */
		@Test (expected = InvalidStateSimulationException.class)
		public void failOnNullSessionPLU() {
			session.stopSession();
			session.removeItemLogic.checkCartForPLUCodedItemToRemove(pluitem1.getPLUCode());
		}
}