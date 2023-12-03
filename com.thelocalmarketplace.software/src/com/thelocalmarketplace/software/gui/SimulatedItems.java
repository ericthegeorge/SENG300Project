package com.thelocalmarketplace.software.gui;

import java.util.ArrayList;
import java.util.List;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

public class SimulatedItems {
	static ArrayList<Item> simulatedItems = new ArrayList<Item>();
	
	public static void addItemsToProductDatabase() {
		ProductDatabases.BARCODED_PRODUCT_DATABASE.clear();
		
		for (Item i : simulatedItems) {
			//add to product database
		}
	}
	
	public static void instantiateItems() {
		instantiatePLUCodedItems();
		instantiateBarcodedItems();
	}
	
	public static void instantiatePLUCodedItems() {
		
		
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
