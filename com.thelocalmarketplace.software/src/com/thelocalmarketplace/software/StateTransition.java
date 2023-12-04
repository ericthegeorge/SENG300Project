package com.thelocalmarketplace.software;

import com.thelocalmarketplace.software.logic.StateLogic.States;

/**
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
public class StateTransition {

	/**
	 * Initial state
	 */
	private States initial;
	
	/**
	 * Final state
	 */
	private States end;
	
	/**
	 * Constructor for a registerable state transition
	 * @throws NullPointerException If any argument is null
	 */
	public StateTransition(States initial, States end) throws NullPointerException {
		this.setInitialState(initial);
		this.setFinalState(end);
	}

	public States getInitialState() {
		return initial;
	}

	public void setInitialState(States initial) throws NullPointerException {
		if (initial == null) {
			throw new NullPointerException("Initial");
		}
		
		this.initial = initial;
	}

	public States getFinalState() {
		return end;
	}

	public void setFinalState(States end) throws NullPointerException {
		if (end == null) {
			throw new NullPointerException("End");
		}
		
		this.end = end;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof StateTransition)) {
			return false;
		}
		
		StateTransition ob = (StateTransition) o;
		
		return (this.getInitialState().equals(ob.getInitialState()) && this.getFinalState().equals(ob.getFinalState()));
	}
}
