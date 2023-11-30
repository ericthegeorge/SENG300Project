package com.thelocalmarketplace.software.logic;

import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.Card.CardData;
import com.jjjwelectronics.card.Card.CardSwipeData;
import com.jjjwelectronics.card.Card.CardTapData;
import com.thelocalmarketplace.software.AbstractLogicDependant;

public class MembershipLogic extends AbstractLogicDependant{
	private String number = "";
	private String cardHolder = "";
	
	public String getNumber() {
		return this.number;
	}
	
	public String getCardHolder() {
		return this.cardHolder;
	}
	
	
	public MembershipLogic(CentralStationLogic logic) throws NullPointerException {
		super(logic);
		// TODO Auto-generated constructor stub
	}

	public boolean enterMembershipByNumber(String number) {
		//If number is not a valid membership{return false;}
		//If number is valid, get cardholder name, and set name and number.
		//What basis is a membership number valid?
		return false;
	}
	
	public boolean enterMembershipBySwipe(CardSwipeData csd) {
		//If number is not a valid membership{return false;}
		//If number is valid, get cardholder name, and set name and number.
		return false;
	}
	
	public boolean enterMembershipByScan(CardTapData ctd) {
		
		//If number is not a valid membership{return false;}
		//If number is valid, get cardholder name, and set name and number.
		return false;
	}
	
	private boolean isValidMembership(String number) {
		
		return false;
	}
	
	//only get data once or multiplying chance of failure
	private String[] getCardData(CardData data) {
		String[] retData = new String[2];
		if (!data.getType().equals("Membership")) {
			return retData;
		}
		retData[0] = data.getNumber();
		retData[1] = data.getCardholder();
		return retData;
	}
	
	
	
}
