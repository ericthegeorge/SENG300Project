package com.thelocalmarketplace.software.test.controllers;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tdc.banknote.Banknote;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.hardware.SelfCheckoutStationSilver;
import com.thelocalmarketplace.software.controllers.pay.cash.BanknoteDispenserController;
import com.thelocalmarketplace.software.logic.CentralStationLogic;

/**
 * Tests for Bank Note Dispenser
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

public class BanknoteDispenserControllerTest {
	private BanknoteDispenserController inputBronze;
	private BanknoteDispenserController inputSilver;
	private BanknoteDispenserController inputGold;

	private CentralStationLogic logicBronze;
	private CentralStationLogic logicSilver;
	private CentralStationLogic logicGold;
	
	private BigDecimal tenDollar;
	private BigDecimal fiveDollar;
	private BigDecimal twoDollar;
	private BigDecimal dollar;
	private BigDecimal quarter;
	private BigDecimal halfDollar;
	private BigDecimal[] coinList;
	private BigDecimal[] noteList;
	private SelfCheckoutStationBronze stationBronze;
	private SelfCheckoutStationSilver stationSilver;
	private SelfCheckoutStationGold stationGold;
	private Currency cad;

	private Banknote twoNote;
	private Banknote fiveNote;
	private Banknote tenNote;
	private Banknote[] noteArray;
	private IBanknoteDispenserStub stub;
	
	@Before
	public void setup() {
		dollar = new BigDecimal(1);
		halfDollar = new BigDecimal(0.50);
		quarter = new BigDecimal(0.25);
		twoDollar = new BigDecimal(2);
		fiveDollar = new BigDecimal(5);
		tenDollar = new BigDecimal(10);
		coinList = new BigDecimal[] {quarter, halfDollar, dollar};
		noteList = new BigDecimal[] {twoDollar, fiveDollar, tenDollar};
		
		cad = Currency.getInstance("CAD"); 
		
		twoNote = new Banknote(cad, twoDollar);
		fiveNote = new Banknote(cad, fiveDollar);
		tenNote = new Banknote(cad, tenDollar);
		noteArray = new Banknote[] {twoNote, fiveNote, tenNote};
		
		stub = new IBanknoteDispenserStub();
		
		SelfCheckoutStationBronze.configureCurrency(cad);
		SelfCheckoutStationSilver.configureCurrency(cad);
		SelfCheckoutStationGold.configureCurrency(cad);
		
		SelfCheckoutStationBronze.configureCoinDenominations(coinList);
		SelfCheckoutStationSilver.configureCoinDenominations(coinList);
		SelfCheckoutStationGold.configureCoinDenominations(coinList);
		
		SelfCheckoutStationBronze.configureBanknoteDenominations(noteList);
		SelfCheckoutStationSilver.configureBanknoteDenominations(noteList);
		SelfCheckoutStationGold.configureBanknoteDenominations(noteList);
		
		SelfCheckoutStationBronze.configureBanknoteStorageUnitCapacity(10);
		SelfCheckoutStationSilver.configureBanknoteStorageUnitCapacity(10);
		SelfCheckoutStationGold.configureBanknoteStorageUnitCapacity(10);
		
		SelfCheckoutStationBronze.configureCoinStorageUnitCapacity(10);
		SelfCheckoutStationSilver.configureCoinStorageUnitCapacity(10);
		SelfCheckoutStationGold.configureCoinStorageUnitCapacity(10);
		
		SelfCheckoutStationBronze.configureCoinTrayCapacity(5);
		SelfCheckoutStationSilver.configureCoinTrayCapacity(5);
		SelfCheckoutStationGold.configureCoinTrayCapacity(5);
		
		SelfCheckoutStationBronze.configureCoinDispenserCapacity(5);
		SelfCheckoutStationSilver.configureCoinDispenserCapacity(5);
		SelfCheckoutStationGold.configureCoinDispenserCapacity(5);
		
		stationBronze = new SelfCheckoutStationBronze();
		stationSilver = new SelfCheckoutStationSilver();
		stationGold = new SelfCheckoutStationGold();
		logicBronze = new CentralStationLogic(stationBronze);
		logicSilver = new CentralStationLogic(stationSilver);
		logicGold = new CentralStationLogic(stationGold);
		inputBronze = new BanknoteDispenserController(logicBronze, twoDollar);
		inputSilver = new BanknoteDispenserController(logicSilver, twoDollar);
		inputGold = new BanknoteDispenserController(logicGold, twoDollar);
	}
	
	@Test (expected = NullPointerException.class)
	public void failedSetup() {
		new BanknoteDispenserController(logicBronze,null);
	}
	
	@Test
	public void getBanknotesTest() {
		List<Banknote> expected = new ArrayList<Banknote>();
		List<Banknote> actual1 = inputBronze.getAvailableBanknotes();
		List<Banknote> actual2 = inputSilver.getAvailableBanknotes();
		List<Banknote> actual3 = inputGold.getAvailableBanknotes();
		
		assertEquals(expected, actual1);
		assertEquals(expected, actual2);
		assertEquals(expected, actual3);
	}
	
	@Test
	public void banknoteAddedTest() {
		List<Banknote> expected = new ArrayList<Banknote>();
		expected.add(twoNote);
		
		inputBronze.banknoteAdded(stub, twoNote);
		inputSilver.banknoteAdded(stub, twoNote);
		inputGold.banknoteAdded(stub, twoNote);
		
		List<Banknote> actual1 = inputBronze.getAvailableBanknotes();
		List<Banknote> actual2 = inputSilver.getAvailableBanknotes();
		List<Banknote> actual3 = inputGold.getAvailableBanknotes();
		
		assertEquals(expected, actual1);
		assertEquals(expected, actual2);
		assertEquals(expected, actual3);
	}
	
	
	@Test
	public void banknotesEmptyTest() {
		List<Banknote> expected = new ArrayList<Banknote>();
		
		inputBronze.banknoteAdded(stub, twoNote);
		inputSilver.banknoteAdded(stub, twoNote);
		inputGold.banknoteAdded(stub, twoNote);
		
		inputBronze.banknotesEmpty(stub);
		inputSilver.banknotesEmpty(stub);
		inputGold.banknotesEmpty(stub);
		
		List<Banknote> actual1 = inputBronze.getAvailableBanknotes();
		List<Banknote> actual2 = inputSilver.getAvailableBanknotes();
		List<Banknote> actual3 = inputGold.getAvailableBanknotes();
		
		assertEquals(expected, actual1);
		assertEquals(expected, actual2);
		assertEquals(expected, actual3);
	}
	
	@Test
	public void banknoteRemovedTest() {
		List<Banknote> expected = new ArrayList<Banknote>();
		
		inputBronze.banknoteAdded(stub, twoNote);
		inputSilver.banknoteAdded(stub, twoNote);
		inputGold.banknoteAdded(stub, twoNote);
		
		inputBronze.banknoteRemoved(stub, twoNote);
		inputSilver.banknoteRemoved(stub, twoNote);
		inputGold.banknoteRemoved(stub, twoNote);
		
		List<Banknote> actual1 = inputBronze.getAvailableBanknotes();
		List<Banknote> actual2 = inputSilver.getAvailableBanknotes();
		List<Banknote> actual3 = inputGold.getAvailableBanknotes();
		
		assertEquals(expected, actual1);
		assertEquals(expected, actual2);
		assertEquals(expected, actual3);
	}
	
	@Test
	public void banknotesLoadedTest() {
		List<Banknote> expected = new ArrayList<Banknote>();
		expected.add(twoNote);
		expected.add(fiveNote);
		expected.add(tenNote);
		
		inputBronze.banknotesLoaded(stub, noteArray);
		inputSilver.banknotesLoaded(stub, noteArray);
		inputGold.banknotesLoaded(stub, noteArray);
		
		List<Banknote> actual1 = inputBronze.getAvailableBanknotes();
		List<Banknote> actual2 = inputSilver.getAvailableBanknotes();
		List<Banknote> actual3 = inputGold.getAvailableBanknotes();
		
		assertEquals(expected, actual1);
		assertEquals(expected, actual2);
		assertEquals(expected, actual3);
	}
	
	@Test
	public void banknotesUnloadedTest() {
		List<Banknote> expected = new ArrayList<Banknote>();
		expected.add(twoNote);
		
		inputBronze.banknotesLoaded(stub, noteArray);
		inputSilver.banknotesLoaded(stub, noteArray);
		inputGold.banknotesLoaded(stub, noteArray);
		
		inputBronze.banknotesUnloaded(stub, fiveNote, tenNote);
		inputSilver.banknotesUnloaded(stub, fiveNote, tenNote);
		inputGold.banknotesUnloaded(stub, fiveNote, tenNote);
		
		List<Banknote> actual1 = inputBronze.getAvailableBanknotes();
		List<Banknote> actual2 = inputSilver.getAvailableBanknotes();
		List<Banknote> actual3 = inputGold.getAvailableBanknotes();
		
		assertEquals(expected, actual1);
		assertEquals(expected, actual2);
		assertEquals(expected, actual3);
	}
	
	//Note: not sure what exactly to test here. Am I to use the IBanknoteDispenser somehow?
	@Test
	public void moneyFullTest() {
		inputBronze.moneyFull(stub);
		inputSilver.moneyFull(stub);
		inputGold.moneyFull(stub);
	}
	// for the test cases could not change the capacity of banknote dispenser, foreverever set at 1000
		@Test
		public void shouldWarnNotEmptyTest() {
			// should not warn if dispenser banknote > 200 
			
			
			
			for (int i = 0; i < 200; i++) {
				inputBronze.banknoteAdded(stub, twoNote);
			}
			for (int i = 0; i < 200; i++) {
				inputSilver.banknoteAdded(stub, twoNote);
			}
			for (int i = 0; i < 200; i++) {
				inputGold.banknoteAdded(stub, twoNote);
			}
			
			inputBronze.banknoteAdded(stub, twoNote);
			inputSilver.banknoteAdded(stub, twoNote);
			inputGold.banknoteAdded(stub, twoNote);
			
			assertFalse(inputBronze.shouldWarnEmpty());
			assertFalse(inputSilver.shouldWarnEmpty());
			assertFalse(inputGold.shouldWarnEmpty());
			
		}
		
		@Test
		public void shouldWarnEmptyTest() {
			// should not warn if dispenser banknote <= 200 
			
			
			
			inputBronze.banknoteAdded(stub, twoNote);
			inputSilver.banknoteAdded(stub, twoNote);
			inputGold.banknoteAdded(stub, twoNote);
			
			assertTrue(inputBronze.shouldWarnEmpty());
			assertTrue(inputSilver.shouldWarnEmpty());
			assertTrue(inputGold.shouldWarnEmpty());
			
		}
		@Test
		public void shouldNotWarnFullTest() {
			// should  not warn if dispenser banknote < 800 
			
			for (int i = 0; i < 799; i++) {
				inputBronze.banknoteAdded(stub, twoNote);
			}
			for (int i = 0; i < 799; i++) {
				inputSilver.banknoteAdded(stub, twoNote);
			}
			for (int i = 0; i < 799; i++) {
				inputGold.banknoteAdded(stub, twoNote);
			}
			
			assertFalse(inputBronze.shouldWarnFull());
			assertFalse(inputSilver.shouldWarnFull());
			assertFalse(inputGold.shouldWarnFull());
		}
		@Test
		public void shouldWarnFullTest() {
			
			// should  warn if dispenser banknote >= 800 
			
			for (int i = 0; i < 800; i++) {
				inputBronze.banknoteAdded(stub, twoNote);
			}
			for (int i = 0; i < 800; i++) {
				inputSilver.banknoteAdded(stub, twoNote);
			}
			for (int i = 0; i < 800; i++) {
				inputGold.banknoteAdded(stub, twoNote);
			}
			
			assertTrue(inputBronze.shouldWarnFull());
			assertTrue(inputSilver.shouldWarnFull());
			assertTrue(inputGold.shouldWarnFull());
		}
		
}
