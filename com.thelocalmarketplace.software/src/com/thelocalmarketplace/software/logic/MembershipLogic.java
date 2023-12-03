package com.thelocalmarketplace.software.logic;

import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.Card.CardData;
import com.jjjwelectronics.card.Card.CardSwipeData;
import com.jjjwelectronics.card.Card.CardTapData;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.StateLogic.States;

public class MembershipLogic extends AbstractLogicDependant{
	private String number;
	//name of the user 
	private String cardHolder;
	public Error code;
	//types of errors in putting a membership number
	private enum Error{
		WRONG_CARD_TYPE, NO_SUCH_MEMBER_FOUND, NO_ERROR;
	}
	public String getNumber() {
		return this.number;
	}
	
	public String getCardHolder() {
		return this.cardHolder;
	}
	
	
	public MembershipLogic(CentralStationLogic logic) throws NullPointerException {
		super(logic);
		 code = Error.NO_ERROR;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Save the membership for the session by number. 
	 * @param membershipNumber the membership number
	 * @return False if the membership is not in the database
	 */
	public boolean enterMembershipByNumber(String membershipNumber) {
		this.code = Error.NO_ERROR;
		this.setMemberData(membershipNumber);
		//If number is not a valid membership{return false;}
		if (!isValidMembership()) {
			return false;
		}
		finishMembershipAddingProcess();
		return true;
	}
	/**
	 * Save the membership for the session by Card. 
	 * @param cardData the card data of the card
	 * @return False if the membership is not in the database, or if the card used was not a membership card, 
	 * and as a result, the membership was not in the database
	 */
	public boolean enterMembershipByCard(CardData cardData) {
		if (cardData == null) {
			return false;
		}
		this.code = Error.NO_ERROR;
		this.setMemberData(cardData);
		//If number is not a valid membership{return false;}
		if (!isValidMembership()) {	
			return false;
		}
		finishMembershipAddingProcess();
		return true;
	}
	
	/*
	 * Helper to validate membership based on its existence in the database.
	 */
	private boolean isValidMembership() {
		if(MembershipDatabase.NUMBER_TO_CARDHOLDER.get(number) == (null)) {
			this.code = Error.NO_SUCH_MEMBER_FOUND;
			return false;
		}
		return true;
	}
	/**
	 * Sets the members data based on the card data. Used only once to minimize chances of corruption.
	 * @param data the card data used to supply membership identity.
	 */
	//only get data once or multiplying chance of failure
	private void setMemberData(CardData data) {
		if (!data.getType().equals("Membership")) {
			this.code = Error.WRONG_CARD_TYPE;
			return;
		}
		this.number = data.getNumber();
		this.cardHolder = data.getCardholder();
	}
	/**
	 * Sets the members data based on the card data. Used only once to minimize chances of corruption.
	 * @param number the membership number.
	 */
	private void setMemberData(String number) {
		this.number = number;
		this.cardHolder = MembershipDatabase.NUMBER_TO_CARDHOLDER.get(number);
		if (this.cardHolder == null) {
			this.code = Error.NO_SUCH_MEMBER_FOUND;
		}
	}
	/**
	 * Final process to return state back to normal.
	 */
	private void finishMembershipAddingProcess() {
		this.logic.stateLogic.gotoState(States.NORMAL);
	}
	
	
	
}
