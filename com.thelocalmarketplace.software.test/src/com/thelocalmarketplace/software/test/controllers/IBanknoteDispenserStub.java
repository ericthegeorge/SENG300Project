package com.thelocalmarketplace.software.test.controllers;

import java.util.List;

import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.NoCashAvailableException;
import com.tdc.banknote.Banknote;
import com.tdc.banknote.BanknoteDispenserObserver;
import com.tdc.banknote.IBanknoteDispenser;

import powerutility.PowerGrid;

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


public class IBanknoteDispenserStub implements IBanknoteDispenser {

	public IBanknoteDispenserStub() {
		
	}
	
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void load(Banknote... banknotes) throws CashOverloadException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Banknote> unload() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCapacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void emit() throws NoCashAvailableException, DisabledException, CashOverloadException {
		// TODO Auto-generated method stub
		
	}

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
	public boolean detach(BanknoteDispenserObserver observer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void detachAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attach(BanknoteDispenserObserver observer) {
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

}
