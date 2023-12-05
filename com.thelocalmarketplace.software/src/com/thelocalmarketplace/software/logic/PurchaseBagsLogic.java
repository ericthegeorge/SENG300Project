package com.thelocalmarketplace.software.logic;
import java.util.Scanner;

import com.jjjwelectronics.EmptyDevice;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.bag.ReusableBag;
import com.jjjwelectronics.bag.ReusableBagDispenserBronze;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;


/**
 * Logic for Purchasing Bags 
 * @author Tanmay Mishra (30127407)
 * -------------------------------------------
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
 * @author Adrian Brisebois (30170764)
 * @author Atique Muhammad (30038650)
 * @author Ryan Korsrud (30173204)
 */

public class PurchaseBagsLogic extends AbstractLogicDependant{
	
//	public double bagExpectedWeight = 20;
//	public String bagDescription = "Reusable bags for purchase";
//	public long price = 1;
	public int numberOfBags;
	public Mass totalBagMass= new Mass(0);
	// sample barcode for bags
//	Numeral[] barcodeDigits = {Numeral.one, Numeral.two, Numeral.three, Numeral.four};
//	Barcode bagBarcode = new Barcode(barcodeDigits);
//	BarcodedProduct bagsforPurchase = new BarcodedProduct(bagBarcode, bagDescription, price, bagExpectedWeight );

	/** tracks weather or not the attendant has approved the current bagging area*/
	public boolean approvedBagging;
	ReusableBag bagInstance;
	
	public PurchaseBagsLogic(CentralStationLogic logic) throws NullPointerException {
		super(logic);
	}
	

	/**allows the customer to purchase a bag
	 * @param bagsToPurchase
	 * @throws EmptyDevice
	 */
	public void purchaseBags(int bagsToPurchase) throws EmptyDevice {
		if (!logic.isSessionStarted()) throw new InvalidStateSimulationException("Session has not started");
		numberOfBags = bagsToPurchase;
        for (int i = 0; i < bagsToPurchase; i++) {
            bagInstance = new ReusableBag();
        	logic.cartLogic.addProductToCart(bagInstance);
        	try {
				logic.hardware.getReusableBagDispenser().dispense();
			} catch (EmptyDevice e) {
				throw new EmptyDevice("Bag dispenser is empty");
			}
        }
	}
}
