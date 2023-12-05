package com.thelocalmarketplace.software.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.jjjwelectronics.EmptyDevice;
import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.Item;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.printer.ReceiptPrinterListener;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.CentralStationLogic.PaymentMethods;
import com.thelocalmarketplace.software.logic.StateLogic.States;

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
public class ReceiptPrintingController extends AbstractLogicDependant implements ReceiptPrinterListener {

	// A duplicate receipt that can be printed by the attendant.
	String duplicateReceipt;
	private Boolean lowInk = false;
	private Boolean lowPaper = false;
	private HashMap<PaymentMethods, BigDecimal> methods;

	/**
	 * Base constructor
	 */
	public ReceiptPrintingController(CentralStationLogic logic) throws NullPointerException {
		super(logic);

		this.duplicateReceipt = "";
		this.logic.hardware.getPrinter().register(this);
		
		this.methods = new HashMap<>();
		for (PaymentMethods pm : PaymentMethods.values()) {
			methods.put(pm, BigDecimal.ZERO);
		}
		// this.logic.hardware.printer.register(this);
	}

	/**
	 * Generates a string that represents a receipt to be printed
	 * 
	 * @return The receipt as a string.
	 */
	public String createPaymentRecord(BigDecimal change) {
		StringBuilder paymentRecord = new StringBuilder();
		Map<Item, Integer> cartItems = this.logic.cartLogic.getCart();
		// Begin the receipt.
		paymentRecord.append("The Local Marketplace\n");
		paymentRecord.append("(403) - 123 - 456\n");
		paymentRecord.append("Customer Receipt\n");
		if (logic.membershipLogic.getCardHolder() != null) {
			paymentRecord.append("Member Name: " + logic.membershipLogic.getCardHolder() + "\n" + "Member Number: "
					+ logic.membershipLogic.getNumber() + "\n");
		}
		paymentRecord.append("=========================\n");

		
		//only used if no gui, for testing
		int itemNumber = 0;
		
		// Iterate through each item in the cart, adding printing them on the receipt.
		for (Entry<Item, Integer> entry : cartItems.entrySet()) {
			Item item = entry.getKey();
			Integer quantity = entry.getValue();
			BigDecimal price = new BigDecimal(logic.cartLogic.getReusableBagPrice());
			if (item instanceof BarcodedItem) {
				BarcodedItem barcodedItem = (BarcodedItem) item;
				BarcodedProduct product = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcodedItem.getBarcode());
				price = new BigDecimal(product.getPrice());
			} else if (item instanceof PLUCodedItem) {
				PLUCodedItem barcodedItem = (PLUCodedItem) item;
				price = new BigDecimal(this.logic.addPLUCodedProductController.getPLUCodedItemPrice(barcodedItem));
			}

			BigDecimal totalItemCost = price.multiply(new BigDecimal(quantity));
			if (logic.getMainGUI() != null) paymentRecord.append(logic.getMainGUI().getDescriptionOfItem(item).split("\\s+")[0] + "\n");
			else {
				paymentRecord.append("Item "+itemNumber+":");
				itemNumber++;
			}
			paymentRecord.append(" - Qty: ");
			paymentRecord.append(quantity);
			paymentRecord.append(", Unit Price: $");
			paymentRecord.append(String.format("%.2f", price));
			paymentRecord.append(", Total: $");
			paymentRecord.append(String.format("%.2f",totalItemCost));
			paymentRecord.append("\n");
		}

		paymentRecord.append("=========================\n");
		paymentRecord.append("Total Cost: $").append(String.format("%.2f", logic.cartLogic.calculateTotalCost())).append("\n");
		if (!change.equals(BigDecimal.ZERO))
		paymentRecord.append("Change Given: $").append(change.toString()).append("\n");
		
		for (PaymentMethods pm : PaymentMethods.values()) {
			BigDecimal paidAmt = methods.get(pm);
			if(!paidAmt.equals(BigDecimal.ZERO))
			paymentRecord.append(pm.name()+ ": $" + String.format("%.2f", paidAmt) + "\n");
		}
		
		if (!change.equals(BigDecimal.ZERO))
			paymentRecord.append("Change Given: $").append(change.toString()).append("\n");
		
		System.out.print(paymentRecord);

		return paymentRecord.toString();
	}

	/**
	 * Generates receipt and calls receipt printing hardware to print it.
	 * 
	 * @param change
	 */
	public void handlePrintReceipt(BigDecimal change) {
		String receiptText = createPaymentRecord(change);
		try {
			this.printReceipt(receiptText);
			this.finish();
		} catch (Exception e) {
			this.onPrintingFail();
			this.duplicateReceipt = receiptText;

		}
	}

	/**
	 * Helper method for printing receipt
	 * 
	 * @param receiptText Is the string to print
	 * @throws OverloadedDevice
	 * @throws EmptyDevice
	 */
	private void printReceipt(String receiptText) throws EmptyDevice, OverloadedDevice {
		for (char c : receiptText.toCharArray()) {

			this.logic.hardware.getPrinter().print(c);
			// this.logic.hardware.printer.print(c);
		}

		this.logic.hardware.getPrinter().cutPaper();
		// this.logic.hardware.printer.cutPaper();
	}

	/**
	 * Prints out a duplicate receipt. Only meant to be used by the attendant.
	 * Returns the machine to normal as there is no longer a receipt that hasn't
	 * been printed.
	 */
	public void printDuplicateReceipt() {
		try {
			// Try to print out the receipt once more.
			this.printReceipt(duplicateReceipt);
			// Returns the machine to normal as the receipt is printed and the machine can
			// resume.
			this.logic.stateLogic.gotoState(States.NORMAL);
			// Removes the receipts as it is no longer needed.
			this.duplicateReceipt = "";
		} catch (Exception e) {
			// If the receipt fails to print again, this will be called.
			this.onPrintingFail();
		}
	}

	/**
	 * Executes after a receipt is successfully printed
	 */
	private void finish() {

		// Thank customer
		System.out.println("Thank you for your business with The Local Marketplace");

		// End session
		this.logic.stopSession();
	}

	/**
	 * Executes if a receipt fails to print. Prints message to console and sets the
	 * state control software to suspended.
	 */
	private void onPrintingFail() {
		System.out.println("Failed to print receipt");

		// Suspend station
		this.logic.stateLogic.gotoState(States.SUSPENDED);
	}
	
	public void addAmountPaid(PaymentMethods pm, BigDecimal amountPaid) {
		methods.replace(pm, BigDecimal.ZERO, amountPaid);
	}

	@Override
	public void thePrinterIsOutOfPaper() {
		this.onPrintingFail();
	}

	@Override
	public void thePrinterIsOutOfInk() {
		this.onPrintingFail();
	}

	@Override
	public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub

	}

	@Override
	public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub

	}

	@Override
	public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub

	}

	@Override
	public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub

	}

	@Override
	public void thePrinterHasLowInk() {
		lowInk = true;
	}

	@Override
	public void thePrinterHasLowPaper() {
		lowPaper = true;
	}

	@Override
	public void paperHasBeenAddedToThePrinter() {
		lowPaper = false;
	}

	@Override
	public void inkHasBeenAddedToThePrinter() {
		lowInk = false;
	}

	public Boolean getLowInk() {
		return lowInk;
	}

	public Boolean getLowPaper() {
		return lowPaper;
	}
}
