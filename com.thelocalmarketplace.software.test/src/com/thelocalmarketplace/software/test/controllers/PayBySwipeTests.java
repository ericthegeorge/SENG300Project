package com.thelocalmarketplace.software.test.controllers;

import com.jjjwelectronics.card.Card;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.hardware.external.CardIssuer;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.CentralStationLogic.CardMethods;
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

/**
 * @author Maheen Nizmani (30172615)
 * ---------------------------------
 * @author Alan Yong (30105707)
 * @author Andrew Matti (30182547)
 * @author Olivia Crosby (30099224)
 * @author Rico Manalastas (30164386)
 * @author Shanza Raza (30192765)
 * @author Danny Ly (30127144)
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

public class PayBySwipeTests {

    SelfCheckoutStationGold station;
    CentralStationLogic session;

    CardIssuer bank;

    Card card;


    /*ensures failure is not a result of magnetic strip failure*/
    public void swipeUntilCardAccepted() throws IOException {

        do{
            session.hardware.getCardReader().swipe(this.card);
        } while(!session.cardPaymentLogic.isDataRead());

    }
    @Before
    public void setup() {

        PowerGrid.engageUninterruptiblePowerSource();
        PowerGrid.instance().forcePowerRestore();

        AbstractSelfCheckoutStation.resetConfigurationToDefaults();

        station=new SelfCheckoutStationGold();
        station.plugIn(PowerGrid.instance());
        station.turnOn();


        session = new CentralStationLogic(station);
        session.setBypassIssuePrediction(true);
        session.startSession();


        //set up bank details
        CardIssuer bank= new CardIssuer("Scotia Bank",3);
        session.setupBankDetails(bank);
        this.card = new Card("DEBIT", "123456789", "John", "329", null, false, false);
        Calendar expiry = Calendar.getInstance();
        expiry.set(2025,Calendar.JANUARY,24);
        bank.addCardData("123456789", "John",expiry,"329",32.00);


        this.session.selectPaymentMethod(PaymentMethods.DEBIT);
        this.session.selectCardMethod(CardMethods.SWIPE);
    }

    @After
    public void tearDown() {
        PowerGrid.engageFaultyPowerSource();
    }

    @Test(expected=SimulationException.class)
    public void testInValidState() throws IOException {
        session.cartLogic.updateBalance(BigDecimal.valueOf(10.00));
        session.hardware.getCardReader().enable();
        swipeUntilCardAccepted();

    }

    @Test
    public void testValidTransaction() throws IOException {
        session.cartLogic.updateBalance(BigDecimal.valueOf(10.00));
        session.hardware.getCardReader().enable();
        session.stateLogic.gotoState(States.CHECKOUT);
        swipeUntilCardAccepted();
        assertEquals(BigDecimal.valueOf(0.0),session.cartLogic.getBalanceOwed());

    }

    @Test(expected=SimulationException.class)
    public void testSessionNotStartedSwipe() throws IOException{
        session.stopSession();
        session.hardware.getCardReader().enable();
        session.stateLogic.gotoState(States.CHECKOUT);
        swipeUntilCardAccepted();
    }

    @Test
    public void testDeclinedTransaction() throws IOException {
        session.cartLogic.updateBalance(BigDecimal.valueOf(50.00));
        session.hardware.getCardReader().enable();
        session.stateLogic.gotoState(States.CHECKOUT);
        swipeUntilCardAccepted();
        assertEquals(BigDecimal.valueOf(50.0),session.cartLogic.getBalanceOwed());
    }
    
    @Test(expected=SimulationException.class)
    public void testWrongSwipeMethodSelected() throws IOException {
        this.session.selectPaymentMethod(PaymentMethods.CREDIT);
        session.cartLogic.updateBalance(BigDecimal.valueOf(10.00));
        session.hardware.getCardReader().enable();
        session.stateLogic.gotoState(States.CHECKOUT);
        swipeUntilCardAccepted();
    }

    @Test(expected=SimulationException.class)
    public void testStationBlockedSwipe()throws IOException{
        session.cartLogic.updateBalance(BigDecimal.valueOf(10.00));
        session.stateLogic.gotoState(States.BLOCKED);
        session.hardware.getCardReader().enable();
        swipeUntilCardAccepted();
    }

    @Test(expected=SimulationException.class)
    public void tesSessionNotStartedSwipe() throws IOException {
        session.cartLogic.updateBalance(BigDecimal.valueOf(10.00));
        session.hardware.getCardReader().enable();
        swipeUntilCardAccepted();

    }
    
    @Test
    public void testGetCardPaymentTypeDebit() {
    	Card c = new Card("deBiT","123456789","John","329", null, false, false);
    	
    	assertEquals(PaymentMethods.DEBIT, session.cardPaymentLogic.getCardType(c.kind));
    }
    
    @Test
    public void testGetCardPaymentTypeCredit() {
    	Card c = new Card("CreDIt","123456789","John","329", null, false, false);
    	
    	assertEquals(PaymentMethods.CREDIT, session.cardPaymentLogic.getCardType(c.kind));
    }
    
    @Test
    public void testGetCardPaymentTypeNone() {
    	Card c = new Card("fdsgds","123456789","John","329", null, false, false);
    	
    	assertEquals(PaymentMethods.NONE, session.cardPaymentLogic.getCardType(c.kind));
    }
}
