package com.thelocalmarketplace.software.test.logic;

//Shanza Raza 30192765

import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.software.logic.CentralStationLogic;

import com.thelocalmarketplace.software.logic.SelectLanguageLogic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.Test.None;

public class testSelectLanguage {
	
	@Test
	public void testSetSupportedLanguages1() {
		SelfCheckoutStationGold scsg = new SelfCheckoutStationGold();
		CentralStationLogic csl = new CentralStationLogic(scsg); 
		SelectLanguageLogic sll = new SelectLanguageLogic(csl, "English");
		List<String> list = Arrays.asList("English", "French", "Spanish", "German", "Portuguese");
		sll.setSupportedLanguages(list);
		List<String> g = sll.getSupportedLanguages();
		assertEquals(list, g); 
		//testing if setSupportedLanguages works properly
	}
	
	@Test
	public void testSetSupportedLanguages2() {
		SelfCheckoutStationGold scsg = new SelfCheckoutStationGold();
		CentralStationLogic csl = new CentralStationLogic(scsg); 
		SelectLanguageLogic sll = new SelectLanguageLogic(csl, "English");
		List<String> g = sll.getSupportedLanguages();
		List<String> list = Arrays.asList("English", "French", "Spanish", "German");
		sll.setSupportedLanguages(list);
		assertNotEquals(list, g);
		//testing if setSupportedLanguages works properly (compare default supported languages with new ones set, should not be equal)
		}
	
	@Test (expected = None.class)
	public void testDisplayLanguages1() {
		SelfCheckoutStationGold scsg = new SelfCheckoutStationGold();
		CentralStationLogic csl = new CentralStationLogic(scsg); 
		SelectLanguageLogic sll = new SelectLanguageLogic(csl, "English");
		sll.displayLanguages();
		//testing to ensure theres no issues
	}
	
	@Test (expected = None.class)
	public void testDisplayLanguages2() {
		SelfCheckoutStationGold scsg = new SelfCheckoutStationGold();
		CentralStationLogic csl = new CentralStationLogic(scsg); 
		SelectLanguageLogic sll = new SelectLanguageLogic(csl, null);
		sll.displayLanguages();
		//testing to ensure there are no issues (even with a "null language")
	}
	
	@Test
	public void testSelectLanguage1() {
		SelfCheckoutStationGold scsg = new SelfCheckoutStationGold();
		CentralStationLogic csl = new CentralStationLogic(scsg); 
		SelectLanguageLogic sll = new SelectLanguageLogic(csl, "English");
		assertTrue(sll.selectLanguage("English"));
		//English, the default language, is part of the supported languages, so selectLanguage should return true
	}

	@Test
	public void testSelectLanguage2() {
		SelfCheckoutStationGold scsg = new SelfCheckoutStationGold();
		CentralStationLogic csl = new CentralStationLogic(scsg); 
		SelectLanguageLogic sll = new SelectLanguageLogic(csl, "English");
		assertTrue(sll.selectLanguage("French"));
		//French, which is not the default language, is part of the supported languages, so selectLanguage should return true
}
	@Test
	public void testSelectLanguage3() {
		SelfCheckoutStationGold scsg = new SelfCheckoutStationGold();
		CentralStationLogic csl = new CentralStationLogic(scsg); 
		SelectLanguageLogic sll = new SelectLanguageLogic(csl, "English");
		assertFalse(sll.selectLanguage("German"));
		//German is not part of supported languages, so selectLanguage should return false
}
	
	@Test
	public void testSelectLanguage4() {
		SelfCheckoutStationGold scsg = new SelfCheckoutStationGold();
		CentralStationLogic csl = new CentralStationLogic(scsg); 
		SelectLanguageLogic sll = new SelectLanguageLogic(csl, "English");
		List<String> list = Arrays.asList("English", "French", "Spanish", "German");
		sll.setSupportedLanguages(list);
		assertTrue(sll.selectLanguage("German")); 
		//German should be part of the supported languages after setting it with setSupportedLanguages, so selectLanguage should return true
}
	@Test
	public void testSelectLanguage5() {
		SelfCheckoutStationGold scsg = new SelfCheckoutStationGold();
		CentralStationLogic csl = new CentralStationLogic(scsg); 
		SelectLanguageLogic sll = new SelectLanguageLogic(csl, "English");
		List<String> list = Arrays.asList("German", "Portuguese", "Punjabi");
		sll.setSupportedLanguages(list);
		assertTrue(sll.selectLanguage("English")); 
		//even though the supported languages have changed, the default language, English, remains the same, so selectLanguage should return true 	
}
	@Test
	public void testCancelLanguageSelection() {
		SelfCheckoutStationGold scsg = new SelfCheckoutStationGold();
		CentralStationLogic csl = new CentralStationLogic(scsg); 
		SelectLanguageLogic sll = new SelectLanguageLogic(csl, "English");
		sll.selectLanguage("French");
		sll.cancelLanguageSelection();
		assertSame(sll.getSelectedLanguage(), "English");	
		//make sure the default language, English, is the same as the selected language (after canceling the one selected before, French)
	}
	
	
	
}
