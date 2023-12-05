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
 * @author Alan Yong (30105707)
 * @author Andrew Matti (30182547)
 * @author Olivia Crosby (30099224)
 * @author Rico Manalastas (30164386)
 * @author Shanza Raza (30192765)
 * @author Danny Ly (30127144)
 * @author Christopher Lo (30113400)
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
