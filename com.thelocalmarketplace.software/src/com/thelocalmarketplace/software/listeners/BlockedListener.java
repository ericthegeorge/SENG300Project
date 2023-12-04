package com.thelocalmarketplace.software.listeners;

import com.thelocalmarketplace.software.AbstractStateTransitionListener;
import com.thelocalmarketplace.software.logic.CentralStationLogic;

/**
 * Blocked Session
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

public class BlockedListener extends AbstractStateTransitionListener {

	public BlockedListener(CentralStationLogic logic) throws NullPointerException {
		super(logic);
	}

	@Override
	public void onTransition() {

		this.logic.hardware.getBaggingArea().disable();
		this.logic.hardware.getHandheldScanner().disable();
		this.logic.hardware.getCoinSlot().disable();
		this.logic.hardware.getCoinValidator().disable();
		this.logic.hardware.getBanknoteInput().disable();
		this.logic.hardware.getBanknoteValidator().disable();
		this.logic.hardware.getMainScanner().disable();
		this.logic.hardware.getScanningArea().disable();
		this.logic.hardware.getCardReader().disable();
		this.logic.hardware.getPrinter().disable();
		this.logic.hardware.getReusableBagDispenser().disable();

		/*
		this.logic.hardware.baggingArea.disable();
		
		this.logic.hardware.handheldScanner.disable();
		this.logic.hardware.mainScanner.disable();
		
		this.logic.hardware.coinSlot.disable();
		this.logic.hardware.coinValidator.disable();
		
		this.logic.hardware.banknoteInput.disable();
		this.logic.hardware.banknoteValidator.disable();
		 */
	}
}
