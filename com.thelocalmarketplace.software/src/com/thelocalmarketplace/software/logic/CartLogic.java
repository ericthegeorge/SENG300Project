package com.thelocalmarketplace.software.logic;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.bag.ReusableBag;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.Utilities;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import ca.ucalgary.seng300.simulation.SimulationException;

/**
 * Handles all logical operations on the customer's cart
 * 
 * Combined from Tara's and Angelina's seperate projects
 * 
 * @author Connell Reffo (10186960)
 * @author Tara Strickland (10105877)
 * @author Angelina Rochon (30087177)
 * @author Julian Fan (30235289)
 * @author Braden Beler (30084941)
 * @author Samyog Dahal (30194624)
 * @author Maheen Nizmani (30172615)
 * @author Phuong Le (30175125)
 * @author Daniel Yakimenka (10185055)
 * @author Merick Parkinson (30196225)
 * @author Farida Elogueil (30171114)
 * @author Tanmay Mishra (30127407)
 */
public class CartLogic {
	
	/**
	 * Tracks all of the products that are in the customer's cart
	 * Includes products without barcodes
	 * Maps a product to its count
	 */
	private Map<Item, Integer> cart;
	
	/**
	 * Tracks how much money the customer owes
	 */
	private BigDecimal balanceOwed;
	private CentralStationLogic logic;
	
	/**
	 * Constructor for a new CartLogic instance
	 */
	public CartLogic(CentralStationLogic l) {
		logic = l;
		// Initialization
		this.cart = new HashMap<Item, Integer>();
		
		this.balanceOwed = BigDecimal.ZERO;
	}
	
	/**
	 * Adds a Barcoded item to the cart, and updates balance using the price
	 * @param item - barcoded item to add to the cart
	 * @param itemPrice - price of the barcoded item
	 */
	public void addProductToCart(BarcodedItem item, long itemPrice) {
		Utilities.modifyCountMapping(cart, item, 1);
		
		// Update balance owed
		BigDecimal newPrice = this.balanceOwed.add(new BigDecimal(itemPrice));
		this.updateBalance(newPrice);
	}
	/**
	 * Adds a Reusable bag to the cart, and updates the weight of the cart
	 */
	public void addProductToCart(ReusableBag item) {
		Utilities.modifyCountMapping(cart, item, 1);
		
		// Reusable Bags have no price as of now
//		BigDecimal newPrice = this.balanceOwed.add(new BigDecimal(itemPrice));
//		this.updateBalance(newPrice);
	}
	
	/**
	 * Adds a PLU coded item to the cart, and updates balance using the price
	 * @param item - PLU coded item to add the cart
	 * @param itemPrice - price of the PLU coded item
	 */
	public void addProductToCart(PLUCodedItem item, double itemPrice) {
		Utilities.modifyCountMapping(cart, item, 1);
		
		// Update balance owed
		BigDecimal newPrice = this.balanceOwed.add(new BigDecimal(itemPrice));
		this.updateBalance(newPrice);
	}
	
	/**
	 * Removes a barcoded item from customer's cart
	 * @param item The barcoded item to remove
	 * @throws SimulationException If the item is not in the cart
	 */
	public void removeProductFromCart(BarcodedItem item) throws SimulationException {
		if (!this.getCart().containsKey(item)) {
			throw new InvalidStateSimulationException("Product not in cart");
		}
		
		Utilities.modifyCountMapping(cart, item, -1);
		
		// Update balance owed
		BarcodedProduct product = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(item.getBarcode());
		BigDecimal newPrice = this.balanceOwed.subtract(new BigDecimal(product.getPrice()));
		this.updateBalance(newPrice);
	}
	
	/**
	 * Removes a PLU coded item from customer's cart
	 * @param item the PLU coded item to remove
	 * @throws SimulationException If the item is not in the cart
	 */
	public void removeProductFromCart(PLUCodedItem item) throws SimulationException {
		if (!this.getCart().containsKey(item)) {
			throw new InvalidStateSimulationException("Product not in cart");
		}
		
		Utilities.modifyCountMapping(cart, item, -1);
		
		// Update balance owed
		PLUCodedProduct product = ProductDatabases.PLU_PRODUCT_DATABASE.get(item.getPLUCode());
		double itemPrice = product.getPrice() * (item.getMass().inGrams().doubleValue() / 1000);
		itemPrice = new BigDecimal(itemPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
		BigDecimal newPrice = this.balanceOwed.subtract(new BigDecimal(itemPrice));
		this.updateBalance(newPrice);
	}
	
	/**
	 * Takes a barcode, looks it up in product database, then and calls addProductToCart() to add it to the cart
	 * @param barcode The barcode to use
	 * @throws SimulationException If barcode is not registered to product database
	 * @throws SimulationException If barcode is not registered in available inventory
	 */
	public void addBarcodedProductToCart(Barcode barcode) throws SimulationException {
		BarcodedProduct product = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode);
		
		if (!ProductDatabases.BARCODED_PRODUCT_DATABASE.containsKey(barcode)) {
			throw new InvalidStateSimulationException("Barcode not registered to product database");
		}
		else if (!ProductDatabases.INVENTORY.containsKey(product) || ProductDatabases.INVENTORY.get(product) < 1) {
			throw new InvalidStateSimulationException("No items of this type are in inventory");
		}
		
		long weight = (long) product.getExpectedWeight();
		Mass itemMass = new Mass(BigInteger.valueOf(weight));
		BarcodedItem item = new BarcodedItem(barcode, itemMass);
		
		this.addProductToCart(item, product.getPrice());
	}
	
	/**
	 * Takes a PLU coded item and its price, and calls addProductToCart() to add it to the cart
	 * @param item The PLU coded item to add
	 * @param itemPrice the price of the item (already calculated based on weight and price per kg)
	 */
	public void addPLUCodedItemToCart(PLUCodedItem item, double itemPrice) throws SimulationException {
		this.addProductToCart(item, itemPrice);
	}
	
	/**
	 * Gets the customer's cart
	 * @return A list of products that represent the cart
	 */
	public Map<Item, Integer> getCart() {
		return this.cart;
	}
	
	/**
	 * Calculates the balance owed based on the products added to customer's cart
	 * @return The balance owed
	 */
	public BigDecimal calculateTotalCost() {
		long balance = 0;
		
		for (Entry<Item, Integer> itemAndCount : this.getCart().entrySet()) {
			Item item = itemAndCount.getKey();
			int count = itemAndCount.getValue();
			
			// update balance when the item is a barcoded item
			if (item instanceof BarcodedItem) {
				BarcodedItem barcodedItem = (BarcodedItem) item;
				BarcodedProduct product = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcodedItem.getBarcode());
				balance += product.getPrice() * count;
			}
			// update balance when the item is a PLU coded item
			else if (item instanceof PLUCodedItem) {
				PLUCodedItem pluCodedItem = (PLUCodedItem) item;
				PLUCodedProduct product = ProductDatabases.PLU_PRODUCT_DATABASE.get(pluCodedItem.getPLUCode());
				double itemPrice = product.getPrice() * (item.getMass().inGrams().doubleValue()/1000);
				itemPrice = new BigDecimal(itemPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
				balance += itemPrice * count;
			}
		}
		return new BigDecimal(balance);
	}
	
	/**
	 * Gets the balance owed by the customer
	 * @return The balance owed
	 */
	public BigDecimal getBalanceOwed() {
		return this.balanceOwed;
	}
	
	public double calculatePriceOfPLU(long price, Mass mass) {
		double itemPrice = price * (mass.inGrams().doubleValue()/1000);
		itemPrice = new BigDecimal(itemPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
		return itemPrice;
	}
	
	/**
   * Increments/Decrements the customer's balance
   * @param amount Is the amount to increment/decrement by
   */
  public void modifyBalance(BigDecimal amount) {
    this.balanceOwed = this.balanceOwed.add(amount);
    if (this.balanceOwed.compareTo(BigDecimal.ZERO) < 0) {
      this.balanceOwed = BigDecimal.ZERO;
    }
  }
	
	/**
	 * Sets the customer's balance
	 * @param balance The new balance value
	 */
	public void updateBalance(BigDecimal balance) {
		this.balanceOwed = balance;
		double balanceRounded = balance.setScale(3, RoundingMode.HALF_UP).doubleValue();
		String balanceToShow = String.format("%.2f", balanceRounded);
		logic.getMainGUI().getAddItemScreen().getCostTextArea().setText("$"+balanceToShow);
	}
}
