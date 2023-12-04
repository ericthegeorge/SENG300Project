package com.thelocalmarketplace.software.logic;
import java.util.Scanner;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.bag.ReusableBag;
import com.jjjwelectronics.bag.ReusableBagDispenserBronze;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.hardware.BarcodedProduct;
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
	
	public void startPurchaseBags() {
		//TODO GUI: please place bags on scale and asks how many bags does the user need. 
		if (!logic.isSessionStarted()) throw new InvalidStateSimulationException("Session has not started");
		
		this.logic.stateLogic.gotoState(States.ADDBAGS);
		Scanner scanner = new Scanner(System.in);
		
		//TODO in GUI 
	    System.out.println("Enter the number of bags you need: ");
	        
        numberOfBags = scanner.nextInt();
        scanner.close();
        bagInstance = new ReusableBag();
//        ReusableBag bagInstance = new ReusableBagDispenserBronze(numberOfBags);
        //Adding the number of bags to the cart
        for (int i = 0;i<numberOfBags;i++ )
        	logic.cartLogic.addProductToCart(bagInstance);
        //TODO in GUI 
		System.out.println("Bag added to order please place bags on the scale");
	}
	
	/**When user has finished adding the bags to the scale
	 * Requires attendant verification if bags are too heavy (indicated by this.approvedBagging = true)
	 * Sets expected weight to the current weight of the bags on the scale 
	 * @throws Exception - when adding bags causes a weight discrepancy 
	 * @throws InvalidStateSimulationException if called when CentralStationLogic is not in ADDINGBAGS state 
	 * @throws InvalidStateSimulationException if session is not started */
	public void purchaseBags() {
		if (!logic.isSessionStarted()) throw new InvalidStateSimulationException("Session has not started");
		if (!logic.stateLogic.inState(States.ADDBAGS)) throw new InvalidStateSimulationException("Cannot end ADDBAGS state when not in ADDBAGS state");
		
		Mass totalBagMass= new Mass(0);  
		for (int i = 0;i<numberOfBags;i++ )
			totalBagMass.sum(bagInstance.getMass());
		
		logic.weightLogic.updateTotalBagMass(totalBagMass);
		if (logic.weightLogic.getTotalBagMass().compareTo(totalBagMass) <= 0 || this.approvedBagging) {
			// If bag weight is under the allowed weight
			this.logic.weightLogic.overrideDiscrepancy();
			this.approvedBagging = true;
			this.logic.attendantLogic.setBaggingDiscrepency(false);
			
			// TODO GUI: 
			System.out.println("Bags added successfully");
			this.logic.stateLogic.gotoState(States.NORMAL);
		} else {
			//bags are too heavy 
			//TODO GUI: display waiting for attendant approval
			this.logic.attendantLogic.baggingDiscrepencyDetected();
		}
	}
}
