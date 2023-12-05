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

package com.thelocalmarketplace.software.test.logic;

import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.AttendantStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.software.logic.AttendantLogic;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import powerutility.PowerGrid;

public class EnableStationTests {
    public AttendantLogic logic;
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

        logic = new AttendantLogic(session);
    }

    @Test(expected = InvalidStateSimulationException.class)
    public void testEnableActiveSession() {
        session.startSession();
        logic.enableStation();
    }

    @Test
    public void testEnableStationFromBlockedState() {
        session.stateLogic.gotoState(States.BLOCKED);
        logic.enableStation();
        assertNotEquals("State should now be active", States.BLOCKED, session.stateLogic.getState());
    }
    
    @Test(expected = InvalidStateSimulationException.class)
    public void testEnableStationFromNormalState() {
        session.stateLogic.gotoState(States.NORMAL);
        logic.enableStation();
    }
}
