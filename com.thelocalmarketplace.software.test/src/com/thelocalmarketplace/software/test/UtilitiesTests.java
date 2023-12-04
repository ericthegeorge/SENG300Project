package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.software.Utilities;

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

public class UtilitiesTests {
	
	/**
	 * Map that will track the count of any object
	 */
	private Map<Object, Integer> testMap;
	
	private Object o1;
	private Object o2;
	
	
	@Before
	public void init() {
		this.testMap = new HashMap<>();
		
		// Initialize some arbitrary objects
		this.o1 = "str1";
		this.o2 = "str2";
		
		// Initialize their counts arbitrarily in the test map
		this.testMap.put(o1, 1);
		this.testMap.put(o2, 3);
	}
	
	@Test
	public void testAddAlreadyExistingInModifyCountMapping() {
		Utilities.modifyCountMapping(this.testMap, o1, 2);
		
		assertEquals(3, testMap.get(o1).intValue());
	}
	
	@Test
	public void testAddNotExistingInModifyCountMapping() {
		Object o = new Object();
		
		Utilities.modifyCountMapping(this.testMap, o, 2);
		
		assertEquals(2, testMap.get(o).intValue());
	}
	
	@Test
	public void testRemoveNotExistingInModifyCountMapping() {
		Object o = new Object();
		
		Utilities.modifyCountMapping(this.testMap, o, -3);
		
		assertFalse(this.testMap.containsKey(o));
	}
	
	@Test
	public void testRemoveAlreadyExistingInModifyCountMapping() {
		Utilities.modifyCountMapping(this.testMap, o2, -1);
		
		assertEquals(2, testMap.get(o2).intValue());
	}
	
	@Test
	public void testRemoveCompletelyAlreadyExistingInModifyCountMapping() {
		Utilities.modifyCountMapping(this.testMap, o1, -1);
		
		assertFalse(this.testMap.containsKey(o1));
	}
}
