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
 * @author Camila Hernandez (30134911)
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
 * @author Ananya Jain (30196069)
 * @author Zhenhui Ren (30139966)
 * @author Eric George (30173268)
 * @author Jenny Dang (30153821)
 * @author Tanmay Mishra (30127407)
 * @author Adrian Brisebois (30170764)
 * @author Atique Muhammad (30038650)
 * @author Ryan Korsrud (30173204)
 */

public class CardReaderController extends AbstractLogicDependant implements CardReaderListener {

	String type;

	/**
	 * Base constructor
	 * 
	 * @param logic Reference to the central station logic
	 * @throws NullPointerException If logic is null
	 */
	public CardReaderController(CentralStationLogic logic) throws NullPointerException {
		super(logic);

		this.logic.hardware.getCardReader().register(this);
		// this.logic.hardware.cardReader.register(this);
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

	// Ask for signature when card is swiped
	@Override
	public void aCardHasBeenSwiped() {
		System.out.println("A card has been swiped");
		type = "swipe";
	}

	@Override
	public void theDataFromACardHasBeenRead(CardData data) {
		String type = data.getType();
		PaymentMethods t = this.logic.cardPaymentLogic.getCardType(data.getType());
		CardMethods c = this.logic.getSelectedCardPaymentMethod();
		this.logic.cardPaymentLogic.isDataRead(true);

		if (!this.logic.isSessionStarted()) {
			throw new InvalidStateSimulationException("Session not started");
		} else if (!this.logic.stateLogic.inState(States.CHECKOUT)) {
			throw new InvalidStateSimulationException("Not ready for checkout");
		} else if (!this.logic.getSelectedPaymentMethod().equals(t)) {
			throw new InvalidStateSimulationException("Pay by " + t.toString() + " not selected");
		}
		System.out.println(CardMethods.TAP.equals(c));
		if (CardMethods.TAP.equals(c))
			System.out.println("Tap payment has been processed");
		// if (this.logic.cardPaymentLogic.validateSignature()) { // Open a signature
		// input UI here
		// check if transaction successful
		if (this.logic.cardPaymentLogic.approveTransaction(data.getNumber(),
				this.logic.cartLogic.getBalanceOwed().doubleValue())) {

			// if successful reduce amount owed by customer otherwise do nothing
			this.logic.cartLogic.modifyBalance(logic.cartLogic.getBalanceOwed().negate());
		}
		// }

		else if (CardMethods.INSERT.equals(c)) {
			// if (this.logic.cardPaymentLogic.validateSignature()) { // Open a signature
			// input UI here
			// check if transaction successful
//			if (this.logic.cardPaymentLogic.approveTransaction(data.getNumber(),
//					this.logic.cartLogic.getBalanceOwed().doubleValue())) {
//
//				// if successful reduce amount owed by customer otherwise do nothing
//				this.logic.cartLogic.modifyBalance(logic.cartLogic.getBalanceOwed().negate());
//			}
			// }
		} else if (CardMethods.SWIPE.equals(c)) {
			// if (this.logic.cardPaymentLogic.validateSignature()) { // Open a signature
			// input UI here
			// check if transaction successful
//			if (this.logic.cardPaymentLogic.approveTransaction(data.getNumber(),
//					this.logic.cartLogic.getBalanceOwed().doubleValue())) {
//
//				// if successful reduce amount owed by customer otherwise do nothing
//				this.logic.cartLogic.modifyBalance(logic.cartLogic.getBalanceOwed().negate());
//			}
			// }
		}
		if (this.logic.cardPaymentLogic.approveTransaction(data.getNumber(),
				this.logic.cartLogic.getBalanceOwed().doubleValue())) {

			// if successful reduce amount owed by customer otherwise do nothing
			this.logic.cartLogic.modifyBalance(logic.cartLogic.getBalanceOwed().negate());
		} 
//		else
//			throw new InvalidStateSimulationException("Invalid card payment method");

		System.out.println("Total owed: " + this.logic.cartLogic.getBalanceOwed());

		if (type.equals("Membership") && this.logic.stateLogic.inState(States.MEMBER)) {
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
