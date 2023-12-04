package com.thelocalmarketplace.software.controllers.pay.cash;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.banknote.Banknote;
import com.tdc.banknote.BanknoteDispenserObserver;
import com.tdc.banknote.IBanknoteDispenser;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.CentralStationLogic;

/**
 * Banknote Dispensing
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

public class BanknoteDispenserController extends AbstractLogicDependant implements BanknoteDispenserObserver {
	/** 
	 * Percentage full/empty to trigger respective warnings.
	 * For example, when set to 20 (%), the full warning or empty warning 
	 * will be triggered when there >= 8 or <= 2 banknotes respectively 
	 * in a dispenser with a maximum capacity of 10. 
	 */
	static double warnAtPercentage = 20 / 100.0; //this can be modified by the customer as needed
	IBanknoteDispenser dispenser;
	private List<Banknote> available;


	public BanknoteDispenserController(CentralStationLogic logic, BigDecimal denomination) throws NullPointerException {
		super(logic);
		
		if (denomination == null) {
			throw new NullPointerException("Denomination");
		}
		
		this.available = new ArrayList<>();
		
		dispenser = this.logic.hardware.getBanknoteDispensers().get(denomination);
		
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
	
	public List<Banknote> getAvailableBanknotes() {
        return this.available;
    }
	
	@Override
	public void banknotesEmpty(IBanknoteDispenser dispenser) {
		this.available.clear();
		
	}
	@Override
	public void banknoteAdded(IBanknoteDispenser dispenser, Banknote banknote) {
		this.available.add(banknote);
		
	}
	
	@Override
	public void banknoteRemoved(IBanknoteDispenser dispenser, Banknote banknote) {
		this.available.remove(banknote);
		
	}
	@Override
	public void banknotesLoaded(IBanknoteDispenser dispenser, Banknote... banknotes) {
		for (Banknote b : banknotes) {
            this.available.add(b);
        }
		
	}
	@Override
	public void banknotesUnloaded(IBanknoteDispenser dispenser, Banknote... banknotes) {
		 for (Banknote b : banknotes) {
	            this.available.remove(b);
	        }
		
	}
	@Override
	public void moneyFull(IBanknoteDispenser dispenser) {
		System.out.println("Banknote Dispenser is full: " + dispenser);
		
	}
	
	// ---- Unused ----
	
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
}
