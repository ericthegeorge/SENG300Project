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
