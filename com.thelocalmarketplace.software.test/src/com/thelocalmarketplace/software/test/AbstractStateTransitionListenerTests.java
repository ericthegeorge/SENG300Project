package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.software.AbstractStateTransitionListener;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import powerutility.PowerGrid;

/** Tests AbstractStateTransitionListener
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

public class AbstractStateTransitionListenerTests {


    // Creates listener stub for testing 
    private class StateTransitionListenerStub extends AbstractStateTransitionListener {
        public boolean onTransitionCalled = false;
    	public StateTransitionListenerStub(CentralStationLogic logic) {
            super(logic);
        }

        @Override
        public void onTransition() {
            onTransitionCalled = true;
        }
    }
    
    private StateLogic stateLogic;
    private StateTransitionListenerStub listener;
    
    @Before 
    public void setup() {
    	AbstractSelfCheckoutStation.resetConfigurationToDefaults();
    	SelfCheckoutStationGold station = new SelfCheckoutStationGold();
        CentralStationLogic logic = new CentralStationLogic(station);
        stateLogic = logic.stateLogic;
        listener = new StateTransitionListenerStub(logic);
        PowerGrid.engageUninterruptiblePowerSource();
        
        station.plugIn(PowerGrid.instance());
        station.turnOn();
        
        stateLogic.registerListener(States.BLOCKED, listener);
    }
    
    @After
    public void teardown() {
    	PowerGrid.engageFaultyPowerSource();
    	AbstractSelfCheckoutStation.resetConfigurationToDefaults();
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullLogic() {
        new StateTransitionListenerStub(null);
    }

    @Test
    public void testOnTransitionMethod() throws Exception {
        stateLogic.gotoState(States.BLOCKED);
        assertTrue("onTransition should be called during the state transition", listener.onTransitionCalled);
    }
    
    @Test
    public void testOnTransitionMethodSameState() throws Exception {
    	stateLogic.registerListener(States.NORMAL, listener);
        stateLogic.gotoState(States.NORMAL);
        assertTrue("onTransition should be called during the state transition", listener.onTransitionCalled);
    }
}
