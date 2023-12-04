package com.thelocalmarketplace.software.controllers.pay.cash;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.banknote.IBanknoteDispenser;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinDispenserObserver;
import com.tdc.coin.ICoinDispenser;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.CentralStationLogic;

/**
 * Represents an object that will control a coin dispenser of a specific coin denomination
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
public class CoinDispenserController extends AbstractLogicDependant implements CoinDispenserObserver {
	/** 
	 * Percentage full/empty to trigger respective warnings.
	 * For example, when set to 20 (%), the full warning or empty warning 
	 * will be triggered when there >= 8 or <= 2 banknotes respectively 
	 * in a dispenser with a maximum capacity of 10. 
	 */
	static double warnAtPercentage = 20 / 100.0; //this can be modified by the customer as needed
	ICoinDispenser dispenser;
	
	/**
	 * List of available coins of this denomination
	 */
	private List<Coin> available;

	/**
	 * Base constructor
	 * @param logic Is the reference to the logic
	 * @param denomination Is the denomination this controller will dispense
	 * @throws NullPointerException If any argument is null
	 */
	public CoinDispenserController(CentralStationLogic logic, BigDecimal denomination) throws NullPointerException {
		super(logic);
		
		if (denomination == null) {
			throw new NullPointerException("Denomination");
		}
		
		this.available = new ArrayList<>();
		
		dispenser = this.logic.hardware.getCoinDispensers().get(denomination);
		
		// Attach self to specific dispenser corresponding to its denomination
		dispenser.attach(this);
	}
	
	public boolean shouldWarnFull() {
		if(dispenser.getCapacity() - available.size() <= dispenser.getCapacity() * warnAtPercentage) return true;
		return false;
	}
	
	public boolean shouldWarnEmpty() {
		if(available.size() <= dispenser.getCapacity() * warnAtPercentage) return true;
		return false;
	}
	
	/**
	 * Gets a list of coins of corresponding denomination that are available as change
	 * @return The list of coins
	 */
	public List<Coin> getAvailableChange() {
		return this.available;
	}
	
	@Override
	public void coinsEmpty(ICoinDispenser dispenser) {
		this.available.clear();
	}

	@Override
	public void coinAdded(ICoinDispenser dispenser, Coin coin) {
		this.available.add(coin);
	}

	@Override
	public void coinRemoved(ICoinDispenser dispenser, Coin coin) {
		this.available.remove(coin);
	}
	
	@Override
	public void coinsLoaded(ICoinDispenser dispenser, Coin... coins) {
		for (Coin c : coins) {
			this.available.add(c);
		}
	}

	@Override
	public void coinsUnloaded(ICoinDispenser dispenser, Coin... coins) {
		for (Coin c : coins) {
			this.available.remove(c);
		}
	}
	
	// ------ Unused -------

	@Override
	public void enabled(IComponent<? extends IComponentObserver> component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabled(IComponent<? extends IComponentObserver> component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turnedOn(IComponent<? extends IComponentObserver> component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turnedOff(IComponent<? extends IComponentObserver> component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void coinsFull(ICoinDispenser dispenser) {
		// TODO Auto-generated method stub
		
	}
}
