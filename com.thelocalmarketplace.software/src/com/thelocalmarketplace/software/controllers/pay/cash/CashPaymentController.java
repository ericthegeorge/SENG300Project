package com.thelocalmarketplace.software.controllers.pay.cash;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;
import java.util.Map.Entry;

import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.banknote.BanknoteValidator;
import com.tdc.banknote.BanknoteValidatorObserver;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.CentralStationLogic.PaymentMethods;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;

/**
 * Pay with Cash
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
public class CashPaymentController extends AbstractLogicDependant implements BanknoteValidatorObserver {
	BigDecimal missed = new BigDecimal(0);

	public CashPaymentController(CentralStationLogic logic) throws NullPointerException {
		super(logic);

		this.logic.hardware.getBanknoteValidator().attach(this);
		//this.logic.hardware.banknoteValidator.attach(this);
		System.out.println("NEW CASH PAYMENT CONTROLLER");
	}
	
	/**
	 * Emits a combination of banknotes and coins to full fill change requirements
	 * @param overpay Is the change to give back
	 * @return The amount that failed to be returned
	 */
	public BigDecimal processCashChange(BigDecimal overpay) {
	    BigDecimal missed = BigDecimal.ZERO;
	    BigDecimal sum = BigDecimal.ZERO;
	    
	    if (overpay.compareTo(BigDecimal.ZERO) == 0) {
	        return missed;
	    }
	    
	    // Sanitize over pay
	    overpay = overpay.abs();
	    
		// Calculate required change in banknotes
	    Map<BigDecimal, Integer> changeInBanknotes = this.logic.banknoteCurrencyLogic.calculateChange(overpay, this.logic.getAvailableBanknotesInDispensers(), false);
		
		// Dispense banknotes
		for (Entry<BigDecimal, Integer> entry : changeInBanknotes.entrySet()) {
			BigDecimal denomination = entry.getKey();
			int count = entry.getValue();

			// Loop for each available banknote
			for (int i = 0; i < count; i++) {
				try {
					this.logic.hardware.getBanknoteDispensers().get(denomination).emit();
					//this.logic.hardware.banknoteDispensers.get(denomination).emit();
					
					sum = sum.add(denomination);
				} catch (Exception e) {
					missed = missed.add(denomination);
					
					System.out.println("Failed to emit banknote");
				}
			}
		}
		
		// Dispense what is left as coins
		missed = missed.add(this.logic.coinPaymentController.processCoinChange(overpay.subtract(sum)));
	
	    return missed;
	}

	
	@Override
	public void goodBanknote(BanknoteValidator validator, Currency currency, BigDecimal denomination) {
		if (!this.logic.isSessionStarted()) {
			throw new InvalidStateSimulationException("Session not started");
		}
		else if (!this.logic.stateLogic.inState(States.CHECKOUT)) {
			throw new InvalidStateSimulationException("Not ready for checkout");
		}
		else if (!this.logic.getSelectedPaymentMethod().equals(PaymentMethods.CASH)) {
			throw new InvalidStateSimulationException("Payment by banknote not selected");
		}
		else if (this.logic.cartLogic.getBalanceOwed().compareTo(BigDecimal.ZERO) == 0) {
			throw new InvalidStateSimulationException("Balance already paid in full");
		}
		
		BigDecimal pay = this.logic.cartLogic.getBalanceOwed().subtract(denomination);
		
        this.logic.cartLogic.modifyBalance(denomination.negate());

        if (pay.compareTo(BigDecimal.ZERO) <= 0) {
        	pay = pay.abs();
        	
            missed = processCashChange(pay);
            
            if (missed.compareTo(BigDecimal.ZERO) > 0) {
            	System.out.println("Not enough change available: " + missed +  " is unavailable");
            }
            else {
                System.out.println("Payment complete. Change dispensed successfully");
            }
        }
        else {
            System.out.println("Payment accepted. Remaining balance: " + pay);
        }
	}

	@Override
	public void badBanknote(BanknoteValidator validator) {
		 System.out.println("Invalid banknote detected. Please try another banknote.");
	}

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

	public BigDecimal getMissed() {
		return missed;
	}


}
