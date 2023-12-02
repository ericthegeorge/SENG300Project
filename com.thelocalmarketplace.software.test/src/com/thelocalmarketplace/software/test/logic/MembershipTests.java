package com.thelocalmarketplace.software.test.logic;

/**
 * Author: Danny Ly
 */
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.MembershipDatabase;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.Card.CardData;
import com.jjjwelectronics.card.Card.CardSwipeData;
import com.jjjwelectronics.card.Card.CardTapData;
import com.tdc.CashOverloadException;
import com.thelocalmarketplace.software.logic.CentralStationLogic.PaymentMethods;
import com.thelocalmarketplace.software.logic.MembershipLogic;
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

// Membership is not fully implemented in this iteration, so some tests will have errors due to
// this incompletion.
public class MembershipTests {
	
	private SelfCheckoutStationBronze hardware;
	private CentralStationLogic logic;
	private MembershipLogic membershipLogic;
	
	// stub for Card data allows for making a membership card in this test
	public class CardDataStub implements CardData {
	    private String type;
	    private String number;
	    private String cardholder;
	    private String cvv;

	    public CardDataStub(String type, String number, String cardholder, String cvv) {
	        this.type = type;
	        this.number = number;
	        this.cardholder = cardholder;
	        this.cvv = cvv;
	    }

	    @Override
	    public String getNumber() {
	        return number;
	    }

	    @Override
	    public String getType() {
	        return type;
	    }
	    
	    @Override
	    public String getCardholder() {
	        return cardholder;
	    }

	    @Override
	    public String getCVV() {
	        return cvv;
	    }
	}
	
	private CardDataStub cardData;
	private CardDataStub falseCardData;
	
	@Before
	public void setup() throws SimulationException, CashOverloadException, OverloadedDevice {
		PowerGrid.engageUninterruptiblePowerSource();
		PowerGrid.instance().forcePowerRestore();

	    AbstractSelfCheckoutStation.resetConfigurationToDefaults();

	    this.hardware = new SelfCheckoutStationBronze();
		this.hardware.plugIn(PowerGrid.instance());
		this.hardware.turnOn();
		
		this.logic = new CentralStationLogic(hardware);
		this.membershipLogic = new MembershipLogic(logic);
		
		MembershipDatabase.NUMBER_TO_CARDHOLDER.put("111222333", "Demo Member");
		cardData = new CardDataStub ("Membership", "111222333", "Demo Member", "123");
		falseCardData = new CardDataStub ("Membership", "111242333", "Non-Member", "123");
	
	 }
	 
	 @Test
	 public void testIfMemberByNumber() {
		 assertTrue(membershipLogic.enterMembershipByNumber("111222333"));
	 }
	 
	 @Test
	 public void testIfNotMemberByNumber() {
		 assertFalse(membershipLogic.enterMembershipByNumber("123"));
	 }
	 
	 @Test
	 public void testIfMemberByCard() {
		 assertTrue(membershipLogic.enterMembershipByCard(cardData));
	 }
	 
	 @Test
	 public void testIfNotMemberByCard() {
		 assertFalse(membershipLogic.enterMembershipByCard(falseCardData));
	 }
	 
	 @Test (expected = NullPointerException.class) 
	 public void testNullNumber() throws Exception{
		 assertTrue(membershipLogic.enterMembershipByNumber(null));
	 }
	 
	 @Test (expected = NullPointerException.class) 
	 public void testNullCard() throws Exception{
		 assertTrue(membershipLogic.enterMembershipByCard(null));
	 }

}
