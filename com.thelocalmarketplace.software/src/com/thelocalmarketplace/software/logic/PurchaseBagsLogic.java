package com.thelocalmarketplace.software.logic;
import com.jjjwelectronics.Mass;
import com.thelocalmarketplace.software.AbstractLogicDependant;


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


public class PurchaseBagsLogic extends AbstractLogicDependant{
	
	public Mass bagExpectedWeight = new Mass((double)20);

	/** tracks weather or not the attendant has approved the current bagging area*/
	public boolean approvedBagging;
	
	public PurchaseBagsLogic(CentralStationLogic logic) throws NullPointerException {
		super(logic);
	}
	
	public void startPurchaseBags() {
		
	}
	
}
