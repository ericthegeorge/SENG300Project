package com.thelocalmarketplace.software;

import java.util.Map;

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

public class Utilities {

	/**
	 * Used for incrementing/decrementing a counting map
	 * @param <T> The type that is being counted
	 * @param map Is a reference to a map object of type: Map<T, Integer>
	 * @param item Is the item to increment/decrement
	 * @param amount Is the amount to increment/decrement by
	 */
	public static <T> void modifyCountMapping(Map<T, Integer> map, T item, int amount) {
		if (map.containsKey(item)) {
			map.put(item, map.get(item) + amount);
		}
		else {
			map.put(item, amount);
		}
		
		if (map.get(item) <= 0) {
			map.remove(item);
		}
	}
}
