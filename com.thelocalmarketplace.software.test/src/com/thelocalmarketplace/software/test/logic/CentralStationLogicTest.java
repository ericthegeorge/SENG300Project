package com.thelocalmarketplace.software.test.logic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.thelocalmarketplace.software.logic.StateLogic.States;
import com.thelocalmarketplace.software.test.controllers.IBanknoteDispenserStub;
import com.thelocalmarketplace.software.test.controllers.ICoinDispenserStub;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.banknote.Banknote;
import com.tdc.coin.Coin;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.software.controllers.pay.cash.BanknoteDispenserController;
import com.thelocalmarketplace.software.controllers.pay.cash.CoinDispenserController;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.CentralStationLogic.PaymentMethods;


import ca.ucalgary.seng300.simulation.SimulationException;
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

public class CentralStationLogicTest {
	
	SelfCheckoutStationBronze station;
	CentralStationLogic session;
	Currency currency;
	
	private Coin dollar;
	private Coin fiveCentCoin;
	private Coin twentyFiveCentCoin;
	private ICoinDispenserStub stub;
	private IBanknoteDispenserStub stub1;
	
	
	private Banknote fiveDollarBill;
	private Banknote tenDollarBill;
	private Banknote twentyDollarBill;
	Banknote[] banknoteList;
	
	private BigDecimal[] denominationsCoinCAD = new BigDecimal[] {
			new BigDecimal(0.05),
			new BigDecimal(0.25),
			new BigDecimal(1.00)
	};
	private BigDecimal[] denominationsBanknotesCAD = new BigDecimal[] {
			new BigDecimal(5.00),
			new BigDecimal(10.00),
			new BigDecimal(20.00)
	};
	@Before
	public void init() {
		PowerGrid.engageUninterruptiblePowerSource();
		PowerGrid.instance().forcePowerRestore();
		
		
		this.currency = Currency.getInstance("CAD");
		
		AbstractSelfCheckoutStation.resetConfigurationToDefaults();
		AbstractSelfCheckoutStation.configureCoinDenominations(denominationsCoinCAD);
		AbstractSelfCheckoutStation.configureCoinDispenserCapacity(10);
		AbstractSelfCheckoutStation.configureCoinStorageUnitCapacity(10);
		AbstractSelfCheckoutStation.configureCoinTrayCapacity(10);
		AbstractSelfCheckoutStation.configureCurrency(currency);
		AbstractSelfCheckoutStation.configureBanknoteDenominations(denominationsBanknotesCAD);
		AbstractSelfCheckoutStation.configureBanknoteStorageUnitCapacity(10);
		
		station = new SelfCheckoutStationBronze();
		
		currency = Currency.getInstance("CAD");
		fiveCentCoin = new Coin(currency,new BigDecimal(0.05));
		twentyFiveCentCoin = new Coin(currency,new BigDecimal(0.25));
		Coin dollar_ = new Coin(currency,new BigDecimal(1.00));
		
		fiveDollarBill = new Banknote(currency, new BigDecimal(5.0));
		tenDollarBill = new Banknote(currency, new BigDecimal(10.0));
		twentyDollarBill = new Banknote(currency, new BigDecimal(20.0));
		
		
		station.plugIn(PowerGrid.instance());
		station.turnOn();

		session = new CentralStationLogic(station);
		session.setBypassIssuePrediction(true);
		
		stub = new ICoinDispenserStub();
		stub1 = new IBanknoteDispenserStub() ;
		
		
	}
	
	@Test public void startSessionStateNormalTest() {
		session.startSession();
		assertTrue("station did not start in noraml state",session.stateLogic.inState(States.NORMAL));
	}
	@Test public void startSessionPaymentMethodTest() {
		session.selectPaymentMethod(PaymentMethods.CASH);
		session.startSession();
		assertTrue("station did not set payment methods correctly", session.getSelectedPaymentMethod()==PaymentMethods.CASH);
	}@Test public void hasStartedTest() {
		session.startSession();
		
		assertTrue("session did not start", session.isSessionStarted());
	}@Test public void hasStoppedTest() {
		session.startSession();
		session.stopSession();
		assertTrue("session did not stop", !session.isSessionStarted());
	}@Test(expected = SimulationException.class) public void canStartWhenStarted() {
		session.startSession();
		session.startSession();
		
	}@Test(expected = NullPointerException.class) public void testNullHardware() {
		session = new CentralStationLogic(null);
	}@Test public void getAllCoinsInDispenserTest() throws CashOverloadException, DisabledException {
		session.startSession();
		session.hardware.getCoinDispensers().get(new BigDecimal(0.05)).receive(fiveCentCoin);
		session.hardware.getCoinDispensers().get(new BigDecimal(0.05)).receive(fiveCentCoin);
		session.hardware.getCoinDispensers().get(new BigDecimal(0.25)).receive(twentyFiveCentCoin);
		Map<BigDecimal, Integer> result = session.getAvailableCoinsInDispensers();
		assertTrue("did not get all coins in dispenser", result.get(new BigDecimal(0.05)) == 2 && result.get(new BigDecimal(0.25))==1);
	}@Test public void  getAllBanknotesInDispenserTest() throws CashOverloadException, DisabledException {
		session.startSession();
		banknoteList = new Banknote[]{tenDollarBill,tenDollarBill};
		
		Banknote[] fiveList = new Banknote[] {fiveDollarBill};
		Banknote[] twentyList = new Banknote[] {twentyDollarBill, twentyDollarBill, twentyDollarBill};
		
		session.hardware.getBanknoteDispensers().get(new BigDecimal(10.00)).load(banknoteList);
		session.hardware.getBanknoteDispensers().get(new BigDecimal(5.00)).load(fiveList);
		session.hardware.getBanknoteDispensers().get(new BigDecimal(20.00)).load(twentyList);
		Map<BigDecimal, Integer> result = session.getAvailableBanknotesInDispensers();
		assertTrue("did not get all coins in dispenser", result.get(new BigDecimal(5.0)) == 1 && result.get(new BigDecimal(10.0))==2 && result.get(new BigDecimal(20.0))==3);
	}
	@Test
	public void issuePredictedBanknoteDispenserEmpty() {
		session.setBypassIssuePrediction(false);
		// should warn if dispenser banknote <= 200  which it is 
		BigDecimal cent5 = new BigDecimal(0.05);
		BigDecimal dollar5 = new BigDecimal(5.00);
		BigDecimal cent25 = new BigDecimal(0.25);
		BigDecimal dollar1 = new BigDecimal(1.00);
		BigDecimal ten = new BigDecimal(10.00);
		BigDecimal twenty = new BigDecimal(20.00);
		
		CoinDispenserController inputBronze25 = session.coinDispenserControllers.get(cent25);
		for (int i = 0; i < 5; i++) {inputBronze25.coinAdded(stub, twentyFiveCentCoin);}
		CoinDispenserController inputBronze1_ = session.coinDispenserControllers.get(dollar1);
		for (int i = 0; i < 5; i++) {inputBronze1_.coinAdded(stub, twentyFiveCentCoin);}
		
		BanknoteDispenserController inputBronze10 = session.banknoteDispenserControllers.get(ten);
		for (int i = 0; i < 500; i++) {inputBronze10.banknoteAdded(stub1, tenDollarBill);}
		
		BanknoteDispenserController inputBronze20 = session.banknoteDispenserControllers.get(twenty);
		for (int i = 0; i < 500; i++) {inputBronze20.banknoteAdded(stub1, twentyDollarBill);}
		
		CoinDispenserController inputBronze = session.coinDispenserControllers.get(cent5);
		for (int i = 0; i < 5; i++) {inputBronze.coinAdded(stub, fiveCentCoin);}
		
		
		BanknoteDispenserController inputBronze1 = session.banknoteDispenserControllers.get(dollar5);
		inputBronze1.banknoteAdded(stub1, fiveDollarBill);
		assertTrue(session.issuePredicted());		
		
	}
	@Test
	public void issuePredictedBanknoteDispenserFull() {
		session.setBypassIssuePrediction(false);
		// should  warn if dispenser banknote >= 800 
		BigDecimal cent5 = new BigDecimal(0.05);
		BigDecimal dollar5 = new BigDecimal(5.00);
		BigDecimal cent25 = new BigDecimal(0.25);
		BigDecimal dollar1 = new BigDecimal(1.00);
		BigDecimal ten = new BigDecimal(10.00);
		BigDecimal twenty = new BigDecimal(20.00);
		
		CoinDispenserController inputBronze25 = session.coinDispenserControllers.get(cent25);
		for (int i = 0; i < 5; i++) {inputBronze25.coinAdded(stub, twentyFiveCentCoin);}
		CoinDispenserController inputBronze1_ = session.coinDispenserControllers.get(dollar1);
		for (int i = 0; i < 5; i++) {inputBronze1_.coinAdded(stub, twentyFiveCentCoin);}
		
		BanknoteDispenserController inputBronze10 = session.banknoteDispenserControllers.get(ten);
		for (int i = 0; i < 500; i++) {inputBronze10.banknoteAdded(stub1, tenDollarBill);}
		
		BanknoteDispenserController inputBronze20 = session.banknoteDispenserControllers.get(twenty);
		for (int i = 0; i < 500; i++) {inputBronze20.banknoteAdded(stub1, twentyDollarBill);}
		
		CoinDispenserController inputBronze = session.coinDispenserControllers.get(cent5);
		BanknoteDispenserController inputBronze1 = session.banknoteDispenserControllers.get(dollar5);
		
		
		for (int i = 0; i < 5; i++) {inputBronze.coinAdded(stub, fiveCentCoin);}
		for (int i = 0; i < 800; i++) {inputBronze1.banknoteAdded(stub1, fiveDollarBill);}		
		assertTrue(session.issuePredicted());
	}
	@Test
	public void issuePredictedCoinDispenserEmpty() {
		session.setBypassIssuePrediction(false);
		// should not warn if dispenser banknote <= 2
		BigDecimal cent5 = new BigDecimal(0.05);
		BigDecimal dollar5 = new BigDecimal(5.00);
		BigDecimal cent25 = new BigDecimal(0.25);
		BigDecimal dollar1 = new BigDecimal(1.00);
		BigDecimal ten = new BigDecimal(10.00);
		BigDecimal twenty = new BigDecimal(20.00);
		
		CoinDispenserController inputBronze25 = session.coinDispenserControllers.get(cent25);
		for (int i = 0; i < 5; i++) {inputBronze25.coinAdded(stub, twentyFiveCentCoin);}
		CoinDispenserController inputBronze1_ = session.coinDispenserControllers.get(dollar1);
		for (int i = 0; i < 5; i++) {inputBronze1_.coinAdded(stub, twentyFiveCentCoin);}
		
		BanknoteDispenserController inputBronze10 = session.banknoteDispenserControllers.get(ten);
		for (int i = 0; i < 500; i++) {inputBronze10.banknoteAdded(stub1, tenDollarBill);}
		
		BanknoteDispenserController inputBronze20 = session.banknoteDispenserControllers.get(twenty);
		for (int i = 0; i < 500; i++) {inputBronze20.banknoteAdded(stub1, twentyDollarBill);}
		
		CoinDispenserController inputBronze = session.coinDispenserControllers.get(cent5);
		BanknoteDispenserController inputBronze1 = session.banknoteDispenserControllers.get(dollar5);
		
		// make sure banknote does not affect test
		
		
		for (int i = 0; i < 500; i++) {inputBronze1.banknoteAdded(stub1, fiveDollarBill);}
		
		inputBronze.coinAdded(stub, fiveCentCoin);
		assertTrue(session.issuePredicted());			
	}
	@Test
	public void issuePredictedCoinDispenserFull() {
		session.setBypassIssuePrediction(false);
		// should  warn if dispenser banknote >= 8
		BigDecimal cent5 = new BigDecimal(0.05);
		BigDecimal dollar5 = new BigDecimal(5.00);
		BigDecimal cent25 = new BigDecimal(0.25);
		BigDecimal dollar1 = new BigDecimal(1.00);
		BigDecimal ten = new BigDecimal(10.00);
		BigDecimal twenty = new BigDecimal(20.00);
		
		CoinDispenserController inputBronze25 = session.coinDispenserControllers.get(cent25);
		for (int i = 0; i < 5; i++) {inputBronze25.coinAdded(stub, twentyFiveCentCoin);}
		CoinDispenserController inputBronze1_ = session.coinDispenserControllers.get(dollar1);
		for (int i = 0; i < 5; i++) {inputBronze1_.coinAdded(stub, twentyFiveCentCoin);}
		
		BanknoteDispenserController inputBronze10 = session.banknoteDispenserControllers.get(ten);
		for (int i = 0; i < 500; i++) {inputBronze10.banknoteAdded(stub1, tenDollarBill);}
		
		BanknoteDispenserController inputBronze20 = session.banknoteDispenserControllers.get(twenty);
		for (int i = 0; i < 500; i++) {inputBronze20.banknoteAdded(stub1, twentyDollarBill);}
		
		CoinDispenserController inputBronze = session.coinDispenserControllers.get(cent5);
		BanknoteDispenserController inputBronze1 = session.banknoteDispenserControllers.get(dollar5);
		// make sure banknote does not affect test
		
		for (int i = 0; i < 500; i++) {inputBronze1.banknoteAdded(stub1, fiveDollarBill);}
		
		for (int i = 0; i < 8; i++) {inputBronze.coinAdded(stub, fiveCentCoin);}
		assertTrue(session.issuePredicted());
	}
	@Test
	public void issueNotPredicted() {
		System.out.println("issuenot");
		session.setBypassIssuePrediction(false);
		
		BigDecimal cent5 = new BigDecimal(0.05);
		BigDecimal dollar5 = new BigDecimal(5.00);
		BigDecimal cent25 = new BigDecimal(0.25);
		BigDecimal dollar1 = new BigDecimal(1.00);
		BigDecimal ten = new BigDecimal(10.00);
		BigDecimal twenty = new BigDecimal(20.00);
		
		CoinDispenserController inputBronze25 = session.coinDispenserControllers.get(cent25);
		for (int i = 0; i < 5; i++) {inputBronze25.coinAdded(stub, twentyFiveCentCoin);}
		CoinDispenserController inputBronze1_ = session.coinDispenserControllers.get(dollar1);
		for (int i = 0; i < 5; i++) {inputBronze1_.coinAdded(stub, twentyFiveCentCoin);}
		
		BanknoteDispenserController inputBronze10 = session.banknoteDispenserControllers.get(ten);
		for (int i = 0; i < 500; i++) {inputBronze10.banknoteAdded(stub1, tenDollarBill);}
		
		BanknoteDispenserController inputBronze20 = session.banknoteDispenserControllers.get(twenty);
		for (int i = 0; i < 500; i++) {inputBronze20.banknoteAdded(stub1, twentyDollarBill);}
		
		CoinDispenserController inputBronze = session.coinDispenserControllers.get(cent5);
		BanknoteDispenserController inputBronze1 = session.banknoteDispenserControllers.get(dollar5);
		
		
		for (int i = 0; i < 500; i++) {inputBronze1.banknoteAdded(stub1, fiveDollarBill);}
		for (int i = 0; i < 5; i++) {inputBronze.coinAdded(stub, fiveCentCoin);}
		assertFalse(session.issuePredicted());
	}

	@Test
    public void issuePredictedLowInk() {
        // Initialize the station and session
        SelfCheckoutStationBronze station = new SelfCheckoutStationBronze();
        CentralStationLogic session = new CentralStationLogic(station);
        session.setBypassIssuePrediction(false);

        // Generate test double for ReceiptPrintingController
        ReceiptPrintingControllerStub receiptPrintingControllerStub = new ReceiptPrintingControllerStub(session);
        session.receiptPrintingController = receiptPrintingControllerStub;

        // predict issue when low ink detected
        assertTrue(session.issuePredicted());
    }
	
	@Test
    public void issuePredictedPaper() {
        // Initialize the station and session
        SelfCheckoutStationBronze station = new SelfCheckoutStationBronze();
        CentralStationLogic session = new CentralStationLogic(station);
        session.setBypassIssuePrediction(false);

        // Generate test double for ReceiptPrintingController
        ReceiptPrintingControllerStub receiptPrintingControllerStub = new ReceiptPrintingControllerStub(session);
        session.receiptPrintingController = receiptPrintingControllerStub;

        // predict issue when low paper detected
        assertTrue(session.issuePredicted());
    }
	
}


