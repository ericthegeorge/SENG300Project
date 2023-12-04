/* Maintain Banknotes Tests
 * @author Ryan Korsrud (30173204)
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
 * @author Maheen Nizamani (30172615)
 * @author Jenny Dang (30153821)
 * @author Tanmay Mishra (30127407)
 * @author Adrian Brisebois (30170764)
 * @author Atique Muhammad (30038650)
 */ 

package com.thelocalmarketplace.software.test.logic;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tdc.CashOverloadException;
import com.tdc.banknote.Banknote;
import com.tdc.coin.Coin;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.AttendantStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.MaintainBanknotesLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.SimulationException;
import powerutility.PowerGrid;

public class MaintainBanknotesTests {
	public MaintainBanknotesLogic logic;
	public CentralStationLogic session;
	public SelfCheckoutStationBronze station;
	
	@Before
    public void setup() {
		PowerGrid.engageUninterruptiblePowerSource();
		PowerGrid.instance().forcePowerRestore();
		AbstractSelfCheckoutStation.resetConfigurationToDefaults();
		
    	SelfCheckoutStationBronze station = new SelfCheckoutStationBronze();
    	
    	session = new CentralStationLogic(station);
        session.setBypassIssuePrediction(true);
        session.hardware.setSupervisor(new AttendantStation());
        session.hardware.plugIn(PowerGrid.instance());
        session.hardware.turnOn();
        session.startSession();

        logic = new MaintainBanknotesLogic(session);
    }
	
	@After
	public void teardown() {
		AbstractSelfCheckoutStation.resetConfigurationToDefaults();
	}
	
	@Test
	public void testMaintainBanknotesAttendant() {
		logic.maintainBanknotesAttendant();
    	assertEquals("State isn't suspended", States.SUSPENDED, session.stateLogic.getState());
	}
	
	@Test
	public void testMaintainBanknotesCheck() {
		logic.maintainBanknotesAttendant();
    	logic.maintainBanknotesCheck();
    	assertEquals("State isn't suspended", States.NORMAL, session.stateLogic.getState());
	}
	
	 @Test
	 public void testMaintainCoinsCheckCoinDispenserEmpty() {
		 session.hardware.getBanknoteDispensers().clear();
		 logic.maintainBanknotesAttendant();
		 logic.maintainBanknotesCheck();
		 assertEquals("State should be suspended", States.SUSPENDED, session.stateLogic.getState());
	 }
	
	@Test
	public void testMaintainBanknotesStorageFull() {
		this.overloadBanknoteStorage();
		logic.maintainBanknotesAttendant();
    	logic.maintainBanknotesCheck();
    	assertEquals("State should be suspended", States.SUSPENDED, session.stateLogic.getState());
	}
	
	
	@Test
    public void testMaintainBanknotesUnsupervised() {
    	session.hardware.setSupervisor(null);
    	logic.maintainBanknotesAttendant();
    	logic.maintainBanknotesCheck();
    	assertEquals("State should be suspended", States.SUSPENDED, session.stateLogic.getState());
    }
	
	// helper function
    public void overloadBanknoteStorage() {
    	Banknote banknote = new Banknote(Currency.getInstance("CAD"), new BigDecimal(2));
    	for(int i=0; i<1000;i++) {
    		try {
				session.hardware.getBanknoteStorage().load(banknote);
			} catch (SimulationException e) {
				e.printStackTrace();
			} catch (CashOverloadException e) {
				e.printStackTrace();
			}
    	}
    }
}
