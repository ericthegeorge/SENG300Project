package com.thelocalmarketplace.software.test.logic;

/**
 * @author Danny Ly (30127144)
 * @author Alan Yong (30105707)
 * @author Andrew Matti (30182547)
 * @author Olivia Crosby (30099224)
 * @author Rico Manalastas (30164386)
 * @author Shanza Raza (30192765)
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
	    
	    // Don't use this for membership. 
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
		cardData = new CardDataStub ("Membership", "111222333", "Demo Member", "no cvv");
		falseCardData = new CardDataStub ("Membership", "111242333", "Non-Member", "no cvv");	
	 }
	 
	 @Test
	 public void testIfMemberByNumber() {
		 assertTrue(membershipLogic.enterMembershipByNumber("111222333"));
	 }
	 
	 @Test
	 public void testIfNotMemberByNumber() {
		 assertFalse(membershipLogic.enterMembershipByNumber("392109"));
	 }
	 
	 @Test
	 public void testIfMemberByCard() {
		 assertTrue(membershipLogic.enterMembershipByCard(cardData));
	 }
	 
	 @Test
	 public void testIfNotMemberByCard() {
		 assertFalse(membershipLogic.enterMembershipByCard(falseCardData));
	 }
	 //(expected = NullPointerException.class) 
	 @Test 
	 public void testNullNumber() throws Exception{
		 assertFalse(membershipLogic.enterMembershipByNumber(null));
	 }
	 
	 //(expected = NullPointerException.class)
	 @Test  
	 public void testNullCard() throws Exception{
		 assertFalse(membershipLogic.enterMembershipByCard(null));
	 }

}
