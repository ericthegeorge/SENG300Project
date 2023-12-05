package com.thelocalmarketplace.software.logic;

/**
 * @author Tanmay Mishra (30127407)
 * -------------------------------------------
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
 * @author Adrian Brisebois (30170764)
 * @author Atique Muhammad (30038650)
 * @author Ryan Korsrud (30173204)
 */


import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.StateLogic.States;

public class MaintainBanknotesLogic extends AbstractLogicDependant {

	
	
	public MaintainBanknotesLogic(CentralStationLogic logic) throws NullPointerException {
		super(logic);
		// TODO Auto-generated constructor stub
	}

	public void maintainBanknotesAttendant() {
		this.logic.stateLogic.gotoState(States.SUSPENDED);
		//Turn off the machine for Attendant to open the Hardware and adjust the needed requirements
		//this.logic.hardware.turnOff();
		
	}
	
	public void maintainBanknotesCheck() {
		//TODO implement in GUI
		if ((this.logic.stateLogic.getState()== States.SUSPENDED) && this.logic.hardware.isSupervised())
			if (this.logic.hardware.getBanknoteStorage().hasSpace()&& 
				!this.logic.hardware.getBanknoteDispensers().isEmpty()&& 
				this.logic.hardware.getBanknoteValidator().hasSpace()) 
			{	
				this.logic.stateLogic.gotoState(States.NORMAL);
				System.out.println("Maintenance of Banknotes done make sure the hardware is closed properly");
			}
			else {
			System.out.println("Error detected going to suspended state");
			this.logic.stateLogic.gotoState(States.SUSPENDED);
			}
	}
}
