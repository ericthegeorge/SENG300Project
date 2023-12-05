package com.thelocalmarketplace.software.gui;

import java.util.ArrayList;
import java.util.List;

import java.math.BigDecimal;
import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

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


public class SimulatedItems {
	static ArrayList<Item> simulatedItems = new ArrayList<Item>();
	public static String apple_description;
	public static String avocado_description;
	public static String asparagus_description; 
	public static String blueberries_description;
	public static String beets_description;
	public static String celery_description;
	public static String endive_description; 
	public static String grapes_description;
	public static String jicama_description;
	public static String kale_description; 
	public static String lettuce_description;
	public static String mangos_description;
	public static String greenpeppers_description;
	public static String onions_description;
	public static String redpeppers_description;
	public static String radishes_description;
	public static String shallots_description;
	public static String spinach_description;
	public static String tomatoes_description;
	public static String yams_description;
	public static String watermelon_description;

	public static void instantiateItems() {
		instantiatePLUCodedItems();
		instantiateBarcodedItems();
	}
	
	public static void instantiatePLUCodedItems() {
		// initialize database
		// Create PLU codes for items
		PriceLookUpCode PLUapple = new PriceLookUpCode("1000");
		PriceLookUpCode PLUavocado = new PriceLookUpCode("1001");
		PriceLookUpCode PLUasparagus = new PriceLookUpCode("1002");
		PriceLookUpCode PLUblueberries = new PriceLookUpCode("1003");
		PriceLookUpCode PLUbeets = new PriceLookUpCode("1004");
		PriceLookUpCode PLUcelery = new PriceLookUpCode("1005");
		PriceLookUpCode PLUendive = new PriceLookUpCode("1006");
		PriceLookUpCode PLUgrapes = new PriceLookUpCode("1007");
		PriceLookUpCode PLUjicama = new PriceLookUpCode("1008");
		PriceLookUpCode PLUkale = new PriceLookUpCode("1009");
		PriceLookUpCode PLUlettuce = new PriceLookUpCode("1010");
		PriceLookUpCode PLUmangos = new PriceLookUpCode("1011");
		PriceLookUpCode PLUgreenpeppers = new PriceLookUpCode("1012");
		PriceLookUpCode PLUonions = new PriceLookUpCode("1013");
		PriceLookUpCode PLUredpeppers = new PriceLookUpCode("1014");
		PriceLookUpCode PLUradishes = new PriceLookUpCode("1015");
		PriceLookUpCode PLUshallots = new PriceLookUpCode("1016");
		PriceLookUpCode PLUspinach = new PriceLookUpCode("1017");
		PriceLookUpCode PLUtomatoes = new PriceLookUpCode("1018");
		PriceLookUpCode PLUyams = new PriceLookUpCode("1019");
		PriceLookUpCode PLUwatermelon = new PriceLookUpCode("1020");
		
		 apple_description =  "apple";
		 avocado_description =  "avocado (PLU:"+PLUavocado+")";
		 asparagus_description =  "asparagus (PLU:"+PLUasparagus+")";
		 blueberries_description =  "blueberries (PLU:"+PLUblueberries+")";
		 beets_description =  "beets (PLU:"+PLUbeets+")";
		 celery_description =  "celery (PLU:"+PLUcelery+")";
		 endive_description =  "endive (PLU:"+PLUendive+")";
		 grapes_description =  "grapes (PLU:"+PLUgrapes+")";
		 jicama_description =  "jicama (PLU:"+PLUjicama+")";
		 kale_description =  "kale (PLU:"+PLUkale+")";
		 lettuce_description =  "lettuce (PLU:"+PLUlettuce+")";
		 mangos_description =  "mangos (PLU:"+PLUmangos+")";
		 greenpeppers_description =  "green peppers (PLU:"+PLUgreenpeppers+")";
		 onions_description =  "onions (PLU:"+PLUonions+")";
		 redpeppers_description =  "red peppers :"+PLUredpeppers+")";
		 radishes_description =  "radishes (PLU:"+PLUradishes+")";
		 shallots_description =  "shallots (PLU:"+PLUshallots+")";
		 spinach_description =  "spinach (PLU:"+PLUspinach+")";
		 tomatoes_description =  "tomatoes (PLU:"+PLUtomatoes+")";
		 yams_description =  "yams (PLU:"+PLUyams+")";
		 watermelon_description =  "watermelon (PLU:"+PLUwatermelon+")";

		// Create the PLU coded products
		PLUCodedProduct apple_prod = new PLUCodedProduct(PLUapple, apple_description, 6);
		PLUCodedProduct avocado_prod = new PLUCodedProduct(PLUavocado, avocado_description, 3);
		PLUCodedProduct asparagus_prod = new PLUCodedProduct(PLUasparagus, asparagus_description, 5);
		PLUCodedProduct blueberries_prod = new PLUCodedProduct(PLUblueberries, blueberries_description, 8);
		PLUCodedProduct beets_prod = new PLUCodedProduct(PLUbeets, beets_description, 2);
		PLUCodedProduct celery_prod = new PLUCodedProduct(PLUcelery, celery_description, 1);
		PLUCodedProduct endive_prod = new PLUCodedProduct(PLUendive, endive_description, 7);
		PLUCodedProduct grapes_prod = new PLUCodedProduct(PLUgrapes, grapes_description, 3);
		PLUCodedProduct jicama_prod = new PLUCodedProduct(PLUjicama, jicama_description, 2);
		PLUCodedProduct kale_prod = new PLUCodedProduct(PLUkale, kale_description, 6);
		PLUCodedProduct lettuce_prod = new PLUCodedProduct(PLUlettuce, lettuce_description, 2);
		PLUCodedProduct mangos_prod = new PLUCodedProduct(PLUmangos, mangos_description, 8);
		PLUCodedProduct greenpeppers_prod = new PLUCodedProduct(PLUgreenpeppers, greenpeppers_description, 5);
		PLUCodedProduct onions_prod = new PLUCodedProduct(PLUonions, onions_description, 2);
		PLUCodedProduct redpeppers_prod = new PLUCodedProduct(PLUredpeppers, redpeppers_description, 6);
		PLUCodedProduct radishes_prod = new PLUCodedProduct(PLUradishes, radishes_description, 2);
		PLUCodedProduct shallots_prod = new PLUCodedProduct(PLUshallots, shallots_description, 3);
		PLUCodedProduct spinach_prod = new PLUCodedProduct(PLUspinach, spinach_description, 4);
		PLUCodedProduct tomatoes_prod = new PLUCodedProduct(PLUtomatoes, tomatoes_description, 5);
		PLUCodedProduct yams_prod = new PLUCodedProduct(PLUyams, yams_description, 2);
		PLUCodedProduct watermelon_prod = new PLUCodedProduct(PLUwatermelon, watermelon_description, 7);
		
		// add plu ccoded products to database and inventory
		ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUapple, apple_prod);
		ProductDatabases.INVENTORY.put(apple_prod, 50);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUavocado, avocado_prod);
		ProductDatabases.INVENTORY.put(avocado_prod, 50);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUasparagus, asparagus_prod);
		ProductDatabases.INVENTORY.put(asparagus_prod, 50);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUblueberries, blueberries_prod);
		ProductDatabases.INVENTORY.put(blueberries_prod, 50);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUbeets, beets_prod);
		ProductDatabases.INVENTORY.put(beets_prod, 50);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUcelery, celery_prod);
		ProductDatabases.INVENTORY.put(celery_prod, 50);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUendive, endive_prod);
		ProductDatabases.INVENTORY.put(endive_prod, 50);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUgrapes, grapes_prod);
		ProductDatabases.INVENTORY.put(grapes_prod, 50);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUjicama, jicama_prod);
		ProductDatabases.INVENTORY.put(jicama_prod, 50);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUkale, kale_prod);
		ProductDatabases.INVENTORY.put(kale_prod, 50);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUlettuce, lettuce_prod);
		ProductDatabases.INVENTORY.put(lettuce_prod, 50);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUmangos, mangos_prod);
		ProductDatabases.INVENTORY.put(mangos_prod, 50);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUgreenpeppers, greenpeppers_prod);
		ProductDatabases.INVENTORY.put(greenpeppers_prod, 50);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUonions, onions_prod);
		ProductDatabases.INVENTORY.put(onions_prod, 50);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUredpeppers, redpeppers_prod);
		ProductDatabases.INVENTORY.put(redpeppers_prod, 50);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUradishes, radishes_prod);
		ProductDatabases.INVENTORY.put(radishes_prod, 50);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUshallots, shallots_prod);
		ProductDatabases.INVENTORY.put(shallots_prod, 50);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUspinach, spinach_prod);
		ProductDatabases.INVENTORY.put(spinach_prod, 50);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUtomatoes, tomatoes_prod);
		ProductDatabases.INVENTORY.put(tomatoes_prod, 50);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUyams, yams_prod);
		ProductDatabases.INVENTORY.put(yams_prod, 50);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUwatermelon, watermelon_prod);
		ProductDatabases.INVENTORY.put(watermelon_prod, 50);
				
		// Create the PLU coded items
		PLUCodedItem apple = new PLUCodedItem(PLUapple, new Mass(new BigDecimal(50)));
		PLUCodedItem avocado = new PLUCodedItem(PLUavocado, new Mass(new BigDecimal(15)));
		PLUCodedItem asparagus = new PLUCodedItem(PLUasparagus, new Mass(new BigDecimal(150)));
		PLUCodedItem blueberries = new PLUCodedItem(PLUblueberries, new Mass(new BigDecimal(250)));
		PLUCodedItem beets = new PLUCodedItem(PLUbeets, new Mass(new BigDecimal(450)));
		PLUCodedItem celery = new PLUCodedItem(PLUcelery, new Mass(new BigDecimal(750)));
		PLUCodedItem endive = new PLUCodedItem(PLUendive, new Mass(new BigDecimal(300)));
		PLUCodedItem grapes = new PLUCodedItem(PLUgrapes, new Mass(new BigDecimal(425)));
		PLUCodedItem jicama = new PLUCodedItem(PLUjicama, new Mass(new BigDecimal(600)));
		PLUCodedItem kale = new PLUCodedItem(PLUkale, new Mass(new BigDecimal(125)));
		PLUCodedItem lettuce = new PLUCodedItem(PLUlettuce, new Mass(new BigDecimal(325)));
		PLUCodedItem mangos = new PLUCodedItem(PLUmangos, new Mass(new BigDecimal(800)));
		PLUCodedItem greenpeppers = new PLUCodedItem(PLUgreenpeppers, new Mass(new BigDecimal(500)));
		PLUCodedItem onions = new PLUCodedItem(PLUonions, new Mass(new BigDecimal(900)));
		PLUCodedItem redpeppers = new PLUCodedItem(PLUredpeppers, new Mass(new BigDecimal(350)));
		PLUCodedItem radishes = new PLUCodedItem(PLUradishes, new Mass(new BigDecimal(400)));
		PLUCodedItem shallots = new PLUCodedItem(PLUshallots, new Mass(new BigDecimal(200)));
		PLUCodedItem spinach = new PLUCodedItem(PLUspinach, new Mass(new BigDecimal(450)));
		PLUCodedItem tomatoes = new PLUCodedItem(PLUtomatoes, new Mass(new BigDecimal(800)));
		PLUCodedItem yams = new PLUCodedItem(PLUyams, new Mass(new BigDecimal(950)));
		PLUCodedItem watermelon = new PLUCodedItem(PLUwatermelon, new Mass(new BigDecimal(2550)));
		
		simulatedItems.add(apple);
		simulatedItems.add(avocado);
		simulatedItems.add(asparagus);
		simulatedItems.add(blueberries);
		simulatedItems.add(beets);
		simulatedItems.add(celery);
		simulatedItems.add(endive);
		simulatedItems.add(grapes);
		simulatedItems.add(jicama);
		simulatedItems.add(kale);
		simulatedItems.add(lettuce);
		simulatedItems.add(mangos);
		simulatedItems.add(greenpeppers);
		simulatedItems.add(onions);
		simulatedItems.add(redpeppers);
		simulatedItems.add(radishes);
		simulatedItems.add(shallots);
		simulatedItems.add(spinach);
		simulatedItems.add(tomatoes);
		simulatedItems.add(yams);
		simulatedItems.add(watermelon);
		
	}
	
	public static void instantiateBarcodedItems() {
	    //initialize database
		Numeral[] barcode_numeral_ham = new Numeral[] {Numeral.one, Numeral.two, Numeral.three};
		Numeral[] barcode_numeral_milk = new Numeral[] {Numeral.three, Numeral.two, Numeral.three};
		Numeral[] barcode_numeral_bread = new Numeral[] {Numeral.three, Numeral.three, Numeral.three};

		
		Barcode barcode_ham = new Barcode(barcode_numeral_ham);
		Barcode barcode_milk = new Barcode(barcode_numeral_milk);
		Barcode barcode_bread = new Barcode(barcode_numeral_bread);

		BarcodedProduct product_ham = new BarcodedProduct(barcode_ham, "Ham (Barcoded)",(long)8.99,(double)500.0);
		BarcodedProduct product_milk = new BarcodedProduct(barcode_milk, "Milk (Barcoded)",(long)5.99,(double)400.0);
		BarcodedProduct product_bread = new BarcodedProduct(barcode_bread, "Bread (Barcoded)",(long)2.99,(double)300.0);
		
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode_ham, product_ham);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode_milk, product_milk);
        ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode_bread, product_bread);
        
		ProductDatabases.INVENTORY.put(product_ham, 50);
		ProductDatabases.INVENTORY.put(product_milk, 50);
        ProductDatabases.INVENTORY.put(product_bread, 50);
		
		//initialize barcoded item
		Mass itemMass_ham = new Mass((double) 500.0);
		Mass itemMass_milk = new Mass((double) 400.0);
		Mass itemMass_bread = new Mass((double) 300.0);
		
		BarcodedItem bitem_ham = new BarcodedItem(barcode_ham, itemMass_ham);
		BarcodedItem bitem_milk = new BarcodedItem(barcode_milk, itemMass_milk);
		BarcodedItem bitem_bread = new BarcodedItem(barcode_bread, itemMass_bread);

		simulatedItems.add(bitem_ham);
	    simulatedItems.add(bitem_milk);
	    simulatedItems.add(bitem_bread);
	}
}
