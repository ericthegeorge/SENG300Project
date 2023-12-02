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
