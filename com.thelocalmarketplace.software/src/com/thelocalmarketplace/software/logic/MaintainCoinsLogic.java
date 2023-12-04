package com.thelocalmarketplace.software.logic;

/**
 * @author Tanmay Mishra (30127407)
 * -------------------------------------------
 * @author Camila Hernandez (30134911)
 * @author Adrian Brisebois
 * @author Alan Yong
 * @author Ananya jain
 * @author Andrew Matti
 * @author Atique Muhammad
 * @author Christopher Lo
 * @author Danny Ly
 * @author Eric George
 * @author Gareth Jenkins
 * @author Ian Beler
 * @author Jahnissi Nwakanma
 * @author Jenny Dang
 * @author Maheen Nizamani
 * @author Michael Svoboda
 * @author Olivia Crosby
 * @author Rico Manalastas
 * @author Ryan Korsrud
 * @author Shanza Raza
 * @author Sukhnaaz Sidhu
 * @author Zhenhui Ren
 */

import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.StateLogic.States;

public class MaintainCoinsLogic extends AbstractLogicDependant {

	
	
	public MaintainCoinsLogic(CentralStationLogic logic) throws NullPointerException {
		super(logic);
		// TODO Auto-generated constructor stub
	}

	public void maintainCoinsAttendant() {
		this.logic.stateLogic.gotoState(States.SUSPENDED);
		//Turn off the machine for Attendant to open the Hardware and adjust the needed requirements
		this.logic.hardware.turnOff();
		
	}
	
	public void maintainCoinsCheck() {
		this.logic.hardware.turnOn();
		if ((this.logic.stateLogic.getState()== States.SUSPENDED) && this.logic.hardware.isSupervised())
			if (this.logic.hardware.getCoinStorage().hasSpace()&& 
				!this.logic.hardware.getCoinDispensers().isEmpty()&& 
				this.logic.hardware.getCoinValidator().hasSpace()) 
			{	
				this.logic.stateLogic.gotoState(States.NORMAL);
				System.out.println("Maintenance of Coin done make sure the hardware is closed");
			}else {
				System.out.println("Error detected going to suspended state");
				this.logic.stateLogic.gotoState(States.SUSPENDED);
			}
	}
	
	
	
	

}
