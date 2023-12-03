package com.thelocalmarketplace.software.controllers.pay;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.card.Card.CardData;
import com.jjjwelectronics.card.CardReaderListener;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.CentralStationLogic.CardMethods;
import com.thelocalmarketplace.software.logic.CentralStationLogic.PaymentMethods;
import com.thelocalmarketplace.software.logic.StateLogic.States;

/**
 * Card Reader Controller
 * @author Christopher Lo (30113400)
 * Updated listeners to taking action regarding on which was called, as well as adding if-statements for each type of card payments
 * --------------------------------
 * @author Maheen Nizmani (30172615)
 * --------------------------------
 * @author Connell Reffo (10186960)
 * @author Tara Strickland (10105877)
 * @author Angelina Rochon (30087177)
 * @author Julian Fan (30235289)
 * @author Braden Beler (30084941)
 * @author Samyog Dahal (30194624)
 * @author Phuong Le (30175125)
 * @author Daniel Yakimenka (10185055)
 * @author Merick Parkinson (30196225)
 * --------------------------------
 */
public class CardReaderController extends AbstractLogicDependant implements CardReaderListener{
    
	String type;
	
	/**
     * Base constructor
     * @param logic Reference to the central station logic
     * @throws NullPointerException If logic is null
     */
    public CardReaderController(CentralStationLogic logic) throws NullPointerException {
        super(logic);

        this.logic.hardware.getCardReader().register(this);
       //this.logic.hardware.cardReader.register(this);
    }


    @Override
    public void aCardHasBeenInserted() {
        System.out.println("A card has been inserted");
        type = "insert";
        this.logic.cardPaymentLogic.isDataRead(false);
    }

    @Override
    public void theCardHasBeenRemoved() {

    }

    @Override
    public void aCardHasBeenTapped() {
        System.out.println("A card has been tapped");
        type = "tap";
        this.logic.cardPaymentLogic.isDataRead(false);
    }


    //Ask for signature when card is swiped
    @Override
    public void aCardHasBeenSwiped() {
        System.out.println("A card has been swiped");
        type = "swipe";
    }
   

    @Override
    public void theDataFromACardHasBeenRead(CardData data) {
      String type = data.getType();
    	PaymentMethods t = this.logic.cardPaymentLogic.getCardType(data.getType());
    	CardMethods c = this.logic.cardPaymentLogic.setCardPaymentType(type);

        this.logic.cardPaymentLogic.isDataRead(true);

        if (!this.logic.isSessionStarted()) {
            throw new InvalidStateSimulationException("Session not started");
        }
        else if (!this.logic.stateLogic.inState(States.CHECKOUT)) {
            throw new InvalidStateSimulationException("Not ready for checkout");
        }
        else if (!this.logic.getSelectedPaymentMethod().equals(t)) {
        	throw new InvalidStateSimulationException("Pay by " + t.toString() + " not selected");
        }
        
        if (CardMethods.TAP.equals(c))
        	System.out.println("Tap payment has been processed");
		//	if (this.logic.cardPaymentLogic.validateSignature()) { // Open a signature input UI here
				//check if transaction successful
				if(this.logic.cardPaymentLogic.approveTransaction(data.getNumber(),this.logic.cartLogic.getBalanceOwed().doubleValue())){
			
				   //if successful reduce amount owed by customer otherwise do nothing
				   this.logic.cartLogic.modifyBalance(logic.cartLogic.getBalanceOwed().negate());
				   }
		//	}
        
        else if (CardMethods.INSERT.equals(c)) {
        //	if (this.logic.cardPaymentLogic.validateSignature()) { // Open a signature input UI here
        	        //check if transaction successful
        	        if(this.logic.cardPaymentLogic.approveTransaction(data.getNumber(),this.logic.cartLogic.getBalanceOwed().doubleValue())){

        	            //if successful reduce amount owed by customer otherwise do nothing
        	            this.logic.cartLogic.modifyBalance(logic.cartLogic.getBalanceOwed().negate());
        	        } 
        //	}
        }
        else if (CardMethods.SWIPE.equals(c)) {
        //	 if (this.logic.cardPaymentLogic.validateSignature()) { // Open a signature input UI here
        	      	//check if transaction successful
        	        if(this.logic.cardPaymentLogic.approveTransaction(data.getNumber(),this.logic.cartLogic.getBalanceOwed().doubleValue())){

        	        	//if successful reduce amount owed by customer otherwise do nothing
        	        	this.logic.cartLogic.modifyBalance(logic.cartLogic.getBalanceOwed().negate());
        	        }
        //	 }       
        }	        			
        else
        	throw new InvalidStateSimulationException("Invalid card payment method");

        System.out.println("Total owed: " + this.logic.cartLogic.getBalanceOwed());
  
        if(type.equals("Membership") && this.logic.stateLogic.inState(States.MEMBER)){
        	logic.membershipLogic.enterMembershipByCard(data);
        }
    }
    
    // ---- Unused ----

    @Override
    public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {

    }

    @Override
    public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {

    }

    @Override
    public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {

    }

    @Override
    public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {

    }
}
