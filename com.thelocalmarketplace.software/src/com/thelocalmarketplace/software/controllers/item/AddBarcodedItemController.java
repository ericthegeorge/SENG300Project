package com.thelocalmarketplace.software.controllers.item;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodeScannerListener;
import com.jjjwelectronics.scanner.IBarcodeScanner;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import ca.ucalgary.seng300.simulation.SimulationException;

/**
 * Represents the software controller for adding a barcoded items
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
public class AddBarcodedItemController extends AbstractLogicDependant implements BarcodeScannerListener {    
    
    /**
     * AddBarcodedProductController Constructor
     * @param logic A reference to the logic instance
     * @throws NullPointerException If logic is null
     */
    public AddBarcodedItemController(CentralStationLogic logic) throws NullPointerException {
    	super(logic);
        
        // Register self to main and hand held barcode scanners

		this.logic.hardware.getMainScanner().register(this);
		this.logic.hardware.getHandheldScanner().register(this);
        //this.logic.hardware.mainScanner.register(this);
        //this.logic.hardware.handheldScanner.register(this);
    }
    
    /**
     * Adds a new barcode
     * If a weight discrepancy is detected, then station is blocked
     * @param barcodedItem The item to be scanned and added
     * @throws SimulationException If session not started
     * @throws SimulationException If station is blocked
     * @throws SimulationException If barcode is not registered in database
     * @throws NullPointerException If barcode is null
     */
    public void addBarcode(Barcode barcode) throws SimulationException, NullPointerException {
    	if (barcode == null) {
            throw new NullPointerException("Barcode is null");
        }
    	else if (!this.logic.isSessionStarted()) {
    		throw new InvalidStateSimulationException("The session has not been started");
    	}
    	else if (this.logic.stateLogic.inState(States.BLOCKED)) {
    		throw new InvalidStateSimulationException("Station is blocked");
    	}
    	
    	this.logic.cartLogic.addBarcodedProductToCart(barcode);
    	this.logic.weightLogic.addExpectedWeight(barcode);
		if(logic.getMainGUI() != null) logic.getMainGUI().getAddItemScreen().getWeightTextArea().setText("");
    	logic.weightLogic.delayedDiscrepancyCheck(5000);
    }
    
    @Override
	public void aBarcodeHasBeenScanned(IBarcodeScanner barcodeScanner, Barcode barcode) throws SimulationException, NullPointerException {
		if(logic.getMainGUI() != null) logic.getMainGUI().getAddItemScreen().getErrorTextArea().setText("Item added to cart. Please place scanned item in bagging area");
    	this.addBarcode(barcode);
	}
    
    // ---- Unused ----

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
}
