package com.thelocalmarketplace.software.listeners;

import com.thelocalmarketplace.software.AbstractStateTransitionListener;
import com.thelocalmarketplace.software.logic.CentralStationLogic;

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

public class NormalListener extends AbstractStateTransitionListener {

	public NormalListener(CentralStationLogic logic) throws NullPointerException {
		super(logic);
	}

	@Override
	public void onTransition() {

		this.logic.hardware.getBaggingArea().enable();
		this.logic.hardware.getHandheldScanner().enable();
		this.logic.hardware.getMainScanner().enable();
		this.logic.hardware.getCoinSlot().enable();
		this.logic.hardware.getCoinValidator().enable();
		this.logic.hardware.getBanknoteInput().enable();
		this.logic.hardware.getCoinValidator().enable();

		this.logic.hardware.getScanningArea().enable();
		this.logic.hardware.getBanknoteValidator().enable();
		this.logic.hardware.getCardReader().enable();
		this.logic.hardware.getPrinter().enable();
		this.logic.hardware.getReusableBagDispenser().enable();

	/*	this.logic.hardware.baggingArea.enable();
		
		this.logic.hardware.handheldScanner.enable();
		this.logic.hardware.mainScanner.enable();
		
		this.logic.hardware.coinSlot.enable();
		this.logic.hardware.coinValidator.enable();
		
		this.logic.hardware.banknoteInput.enable();
		this.logic.hardware.banknoteValidator.enable();

	 */
	}
}
