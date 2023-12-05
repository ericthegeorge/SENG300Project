package com.thelocalmarketplace.software.test.controllers;

import com.jjjwelectronics.card.BlockedCardException;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.Card.CardInsertData;
import com.jjjwelectronics.card.InvalidPINException;
import com.jjjwelectronics.card.MagneticStripeFailureException;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.hardware.external.CardIssuer;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.CentralStationLogic.PaymentMethods;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import powerutility.PowerGrid;

import ca.ucalgary.seng300.simulation.SimulationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/** 
 * Added for personal testing, can be modified or deleted by testing team
 * Removed IssuePredicted() in session.StartSession() method during testing in order to be able to run tests, up to the testers to implement the right preconditions for IssuePredicted() 
 * to hold true in order to be able to start the session
 * @author Christopher Lo (30113400)
 * ----------------------------------
 * @author Alan Yong (30105707)
 * @author Andrew Matti (30182547)
 * @author Olivia Crosby (30099224)
 * @author Rico Manalastas (30164386)
 * @author Shanza Raza (30192765)
 * @author Danny Ly (30127144)
 * @author Maheen Nizmani (30172615)
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

public class PayByCardTest {

    SelfCheckoutStationGold station;
    CentralStationLogic session;

    CardIssuer bank;

    Card debit;
    Card credit;

    @Before
    public void setup() {

        PowerGrid.engageUninterruptiblePowerSource();
        PowerGrid.instance().forcePowerRestore();

        AbstractSelfCheckoutStation.resetConfigurationToDefaults();

        station = new SelfCheckoutStationGold();
        station.plugIn(PowerGrid.instance());
        station.turnOn();

        session = new CentralStationLogic(station);
        session.setBypassIssuePrediction(true);
        session.startSession();

        //set up bank details
        CardIssuer bank= new CardIssuer("Scotia Bank",10);
        session.setupBankDetails(bank);
        this.debit = new Card("DEBIT", "123456789", "John", "329", "1234", true, true);
        Calendar expiry = Calendar.getInstance();
        expiry.set(2025,Calendar.JANUARY,24);
        bank.addCardData("123456789", "John",expiry,"329",32.00);
        
        //set up mastercard details
        CardIssuer master= new CardIssuer("MasterCard",3);
        session.setupBankDetails(master);
        this.credit = new Card("CREDIT", "123456789", "John", "329", "1234", true, true);
        master.addCardData("123456789", "John",expiry,"329",32.00);


        this.session.selectPaymentMethod(PaymentMethods.DEBIT);
    }
  
	@Test
	public void testInsertTransaction() throws IOException {
	    session.cartLogic.updateBalance(BigDecimal.valueOf(10.00));
	    session.hardware.getCardReader().enable();
	    session.stateLogic.gotoState(States.CHECKOUT);
	    session.hardware.getCardReader().insert(this.debit, "1234");

	    
	    assertEquals(BigDecimal.valueOf(0.0),session.cartLogic.getBalanceOwed());
	}
	
	@Test
	public void testSwipeTransaction() throws IOException {
	    session.cartLogic.updateBalance(BigDecimal.valueOf(10.00));
	    session.hardware.getCardReader().enable();
	    session.stateLogic.gotoState(States.CHECKOUT);
	    try {
	    session.hardware.getCardReader().swipe(this.debit);
	    }catch(MagneticStripeFailureException msfe) {
		    session.hardware.getCardReader().swipe(this.debit);
	    }
	    
	    assertEquals(BigDecimal.valueOf(0.0),session.cartLogic.getBalanceOwed());
	}
    
    @Test
    public void testCreditTapTransaction() throws IOException {
    	this.session.selectPaymentMethod(PaymentMethods.CREDIT);
    	
        session.cartLogic.updateBalance(BigDecimal.valueOf(10.00));
        session.hardware.getCardReader().enable();
        session.stateLogic.gotoState(States.CHECKOUT);
        session.hardware.getCardReader().tap(this.credit);
        
        assertEquals(BigDecimal.valueOf(0.0),session.cartLogic.getBalanceOwed());
    }

	@Test
	public void testCreditInsertTransaction() throws IOException {
		this.session.selectPaymentMethod(PaymentMethods.CREDIT);
		
	    session.cartLogic.updateBalance(BigDecimal.valueOf(10.00));
	    session.hardware.getCardReader().enable();
	    session.stateLogic.gotoState(States.CHECKOUT);
	    session.hardware.getCardReader().insert(this.credit, "1234");
	    
	    assertEquals(BigDecimal.valueOf(0.0),session.cartLogic.getBalanceOwed());
	}
	
	@Test
	public void testCreditSwipeTransaction() throws IOException {
		this.session.selectPaymentMethod(PaymentMethods.CREDIT);
		
	    session.cartLogic.updateBalance(BigDecimal.valueOf(10.00));
	    session.hardware.getCardReader().enable();
	    session.stateLogic.gotoState(States.CHECKOUT);
	    session.hardware.getCardReader().swipe(this.credit);
	    
	    assertEquals(BigDecimal.valueOf(0.0),session.cartLogic.getBalanceOwed());
	}
	
	@Test (expected = BlockedCardException.class)
	public void testBlockedAfterThreeAttemptsTransaction() throws InvalidPINException, IOException {
	    session.cartLogic.updateBalance(BigDecimal.valueOf(10.00));
	    session.hardware.getCardReader().enable();
	    session.stateLogic.gotoState(States.CHECKOUT);
	    
	    for (int i = 0; i < 3; i++) {
		    try {
		    	session.hardware.getCardReader().insert(this.debit, "1111");
		    } catch(InvalidPINException e) {
		    	session.hardware.getCardReader().remove();
		    }
	    }
	    session.hardware.getCardReader().insert(this.debit, "1111");
	    assertEquals(BigDecimal.valueOf(0.0),session.cartLogic.getBalanceOwed());
	}
	
    
    @Test
    public void testTapTransaction() throws IOException {
        session.cartLogic.updateBalance(BigDecimal.valueOf(10.00));
        session.hardware.getCardReader().enable();
        session.stateLogic.gotoState(States.CHECKOUT);
        session.hardware.getCardReader().tap(this.debit);
        
        assertEquals(BigDecimal.valueOf(0.0),session.cartLogic.getBalanceOwed());
    }
}
