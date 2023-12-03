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

public class SimulatedItems {
	static ArrayList<Item> simulatedItems = new ArrayList<Item>();
	
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
		
		// Create the PLU coded products
		PLUCodedProduct apple_prod = new PLUCodedProduct(PLUapple, "apple", 6);
		PLUCodedProduct avocado_prod = new PLUCodedProduct(PLUavocado, "avocado", 3);
		PLUCodedProduct asparagus_prod = new PLUCodedProduct(PLUasparagus, "asparagus", 5);
		PLUCodedProduct blueberries_prod = new PLUCodedProduct(PLUblueberries, "blueberries", 8);
		PLUCodedProduct beets_prod = new PLUCodedProduct(PLUbeets, "beets", 2);
		PLUCodedProduct celery_prod = new PLUCodedProduct(PLUcelery, "celery", 1);
		PLUCodedProduct endive_prod = new PLUCodedProduct(PLUendive, "endive", 7);
		PLUCodedProduct grapes_prod = new PLUCodedProduct(PLUgrapes, "grapes", 3);
		PLUCodedProduct jicama_prod = new PLUCodedProduct(PLUjicama, "jicama", 2);
		PLUCodedProduct kale_prod = new PLUCodedProduct(PLUkale, "kale", 6);
		PLUCodedProduct lettuce_prod = new PLUCodedProduct(PLUlettuce, "lettuce", 2);
		PLUCodedProduct mangos_prod = new PLUCodedProduct(PLUmangos, "mangos", 8);
		PLUCodedProduct greenpeppers_prod = new PLUCodedProduct(PLUgreenpeppers, "green peppers", 5);
		PLUCodedProduct onions_prod = new PLUCodedProduct(PLUonions, "onions", 2);
		PLUCodedProduct redpeppers_prod = new PLUCodedProduct(PLUredpeppers, "red peppers", 6);
		PLUCodedProduct radishes_prod = new PLUCodedProduct(PLUradishes, "radishes", 2);
		PLUCodedProduct shallots_prod = new PLUCodedProduct(PLUshallots, "shallots", 3);
		PLUCodedProduct spinach_prod = new PLUCodedProduct(PLUspinach, "spinach", 4);
		PLUCodedProduct tomatoes_prod = new PLUCodedProduct(PLUtomatoes, "tomatoes", 5);
		PLUCodedProduct yams_prod = new PLUCodedProduct(PLUyams, "yams", 2);
		PLUCodedProduct watermelon_prod = new PLUCodedProduct(PLUwatermelon, "watermelon", 7);
		
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
		ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUspinach, tomatoes_prod);
		ProductDatabases.INVENTORY.put(spinach_prod, 50);
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

		BarcodedProduct product_ham = new BarcodedProduct(barcode_ham, "some ham",(long)8.99,(double)500.0);
		BarcodedProduct product_milk = new BarcodedProduct(barcode_milk, "some milk",(long)5.99,(double)400.0);
		BarcodedProduct product_bread = new BarcodedProduct(barcode_bread, "some bread",(long)2.99,(double)300.0);
		
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode_ham, product_ham);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode_milk, product_milk);
        ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode_bread, product_bread);
		
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
