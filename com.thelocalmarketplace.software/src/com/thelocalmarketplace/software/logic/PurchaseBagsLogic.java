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
 * @author Tanmay Mishra (30127407)
 * -------------------------------------------
 * @author Camila Hernandez (30134911)
 * @author Adrian Brisebois
 * @author Alan Yong
 * @author Ananya jain
 * @author Andrew Matti
 * @author Atique Muhammad
 * @author Christopher Lo
 * @author Danny Ly
 * @author Eric George
 * @author Gareth Jenkins
 * @author Ian Beler
 * @author Jahnissi Nwakanma
 * @author Jenny Dang
 * @author Maheen Nizamani
 * @author Michael Svoboda
 * @author Olivia Crosby
 * @author Rico Manalastas
 * @author Ryan Korsrud
 * @author Shanza Raza
 * @author Sukhnaaz Sidhu
 * @author Zhenhui Ren
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
		if (logic.weightLogic.getTotalBagMass().compareTo(totalBagMass) < 0 || this.approvedBagging) {
			// If bag weight is under the allowed weight
			this.logic.weightLogic.overrideDiscrepancy();
			this.approvedBagging = false;
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
