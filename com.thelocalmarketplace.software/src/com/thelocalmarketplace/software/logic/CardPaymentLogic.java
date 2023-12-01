package com.thelocalmarketplace.software.logic;

import java.util.Scanner;

import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.Card.CardInsertData;
import com.thelocalmarketplace.hardware.external.CardIssuer;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.CentralStationLogic.CardMethods;
import com.thelocalmarketplace.software.logic.CentralStationLogic.PaymentMethods;

/**
 * Card Payment Logic
 * @author Christopher Lo (30113400)
 * 
 * Now handles the logic for: credit/debit tap/insert/swipe
 * Also now can set card payment type with the CardMethod enumeration from the Central Station logic
 * Also added a validateSignature method
 * ---------------------------------
 * @author Maheen Nizmani (30172615)
 * ---------------------------------
 * @author Connell Reffo (10186960)
 * @author Tara Strickland (10105877)
 * @author Angelina Rochon (30087177)
 * @author Julian Fan (30235289)
 * @author Braden Beler (30084941)
 * @author Samyog Dahal (30194624)
 * @author Phuong Le (30175125)
 * @author Daniel Yakimenka (10185055)
 * @author Merick Parkinson (30196225)
 * @author Farida Elogueil (30171114)
 */
public class CardPaymentLogic extends AbstractLogicDependant {

    public String signature;
    public CardIssuer issuer;
    boolean dataRead;

    
    public CardPaymentLogic(CentralStationLogic logic, CardIssuer bank) throws NullPointerException {
    	super(logic);
    	
        this.issuer = bank;
    }

    //approve the transaction
    public boolean approveTransaction(String cardNumber, double chargeAmount) {
    	
        Long holdNumber = this.issuer.authorizeHold(cardNumber, chargeAmount);
        
        if (holdNumber != -1) {
            return this.issuer.postTransaction(cardNumber, holdNumber, chargeAmount);
        }
        
        return false;
    }


    //keeps track of whether data was read or not
    public void isDataRead(boolean read) {
        dataRead = read;
    }

    //returns if data is read or not
    public boolean isDataRead(){
        return dataRead;
    }
    
    public PaymentMethods getCardType(String type) {
    	String t = type.toLowerCase();
    	
    	if (t.contains("debit")) {
    		return PaymentMethods.DEBIT;
    	}
    	else if (t.contains("credit")) {
    		return PaymentMethods.CREDIT;
    	}
    	
    	return PaymentMethods.NONE;
    }
    
    public CardMethods setCardPaymentType(String type) {
    	String t = type.toLowerCase();
    	
    	if (t.contains("tap")) {
    		return CardMethods.TAP;
    	}
    	else if (t.contains("insert")) {
    		return CardMethods.INSERT;
    	}
    	else if (t.contains("swipe")) {
    		return CardMethods.SWIPE;
    	}
    	
    	return CardMethods.NONE;
    }
    
    public boolean validateSignature() {
    	Scanner scanner = new Scanner(System.in); // Temporary Signature Input Method; GUI should be able to replace this
    	System.out.println("Please enter a signature:");
    	
    	signature = scanner.nextLine();
    	scanner.close();
    	return true;
    }
}
