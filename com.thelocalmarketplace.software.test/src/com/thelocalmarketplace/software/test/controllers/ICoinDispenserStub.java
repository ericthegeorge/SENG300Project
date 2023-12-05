package com.thelocalmarketplace.software.test.controllers;

import java.util.List;

import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.NoCashAvailableException;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinDispenserObserver;
import com.tdc.coin.ICoinDispenser;

import ca.ucalgary.seng300.simulation.SimulationException;
import powerutility.PowerGrid;

/**
 * Coin Dispenser Stub
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

public class ICoinDispenserStub implements ICoinDispenser {

	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isActivated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasPower() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void connect(PowerGrid grid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disactivate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean detach(CoinDispenserObserver observer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void detachAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attach(CoinDispenserObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void receive(Coin cash) throws CashOverloadException, DisabledException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasSpace() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void load(Coin... coins) throws SimulationException, CashOverloadException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Coin> unload() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCapacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void emit() throws CashOverloadException, NoCashAvailableException, DisabledException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reject(Coin coin) throws DisabledException, CashOverloadException {
		// TODO Auto-generated method stub
		
	}

}
