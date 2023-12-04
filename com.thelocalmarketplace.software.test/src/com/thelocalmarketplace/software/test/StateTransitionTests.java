package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.thelocalmarketplace.software.StateTransition;
import com.thelocalmarketplace.software.logic.StateLogic.States;

/** Tests StateTransition
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

public class StateTransitionTests {

	// - - - - - - - - - - Constructor tests - - - - - - - - - -
	@Test(expected = NullPointerException.class)
	public void testConstructorWithNullInitial() {
		new StateTransition(null, States.NORMAL);
	}

	
	@Test(expected = NullPointerException.class)
	public void testConstructorWithNullFinal() {
		new StateTransition(States.NORMAL, null);
	}

	
	@Test
	public void testValidConstructor() {
		StateTransition transition = new StateTransition(States.NORMAL, States.BLOCKED);
		assertNotNull("The StateTransition object should not be null", transition);
		assertEquals("Initial state should be NORMAL", States.NORMAL, transition.getInitialState());
		assertEquals("Final state should be BLOCKED", States.BLOCKED, transition.getFinalState());
	}

	// - - - - - - - - - - Equals tests - - - - - - - - - -

	@Test
	public void testEqualsWithSameObject() {
		StateTransition transition = new StateTransition(States.NORMAL, States.BLOCKED);
		assertTrue("A StateTransition object should be equal to itself", transition.equals(transition));
	}

	
	@Test
	public void testEqualsWithEqualStates() {
		StateTransition st1 = new StateTransition(States.NORMAL, States.BLOCKED);
		StateTransition st2 = new StateTransition(States.NORMAL, States.BLOCKED);
		assertTrue("Two StateTransition objects with the same states should be equal", st1.equals(st2));
	}

	
	@Test
	public void testEqualsWithDifferentStates() {
		StateTransition st1 = new StateTransition(States.NORMAL, States.BLOCKED);
		StateTransition st3 = new StateTransition(States.BLOCKED, States.NORMAL);
		assertFalse("Two StateTransition objects with different states should not be equal", st1.equals(st3));
	}

	
	@Test
	public void testEqualsWithNonStateTransitionObject() {
		StateTransition st1 = new StateTransition(States.NORMAL, States.BLOCKED);
		Object obj = new Object();
		assertFalse("A StateTransition object should not be equal to a non-StateTransition object", st1.equals(obj));
	}
}
