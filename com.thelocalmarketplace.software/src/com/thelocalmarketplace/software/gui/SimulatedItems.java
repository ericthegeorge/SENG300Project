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
