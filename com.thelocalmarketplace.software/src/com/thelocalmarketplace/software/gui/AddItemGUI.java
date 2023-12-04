package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class AddItemGUI extends JFrame {
	private CentralStationLogic logic;
	private MainGUI mainGUI;
	private JPanel mainPanel;
	private JTextArea errorTextArea;
	private JTextArea weightTextArea;
	private JTextArea costTextArea;
	DefaultListModel<String> cartList;
	DefaultListModel<String> baggingAreaList;

    public AddItemGUI(MainGUI m, CentralStationLogic l) {
    	mainGUI = m;
    	logic = l;
        // Set up the main frame
        setTitle("Checkout Station");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Make it full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        // Get the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Create components
        mainPanel = new JPanel(new BorderLayout());

        // Define ratios for panel sizes
        double topBoxRatio = 1.0 / 10.0;
        double upperBoxRatio = 7.0 / 10.0;
        double bottomBoxRatio = 3.0 / 10.0;
        
       
        // Split the page into two boxes horizontally
        JPanel topBox = new JPanel(new GridLayout(1, 1));
        JPanel upperBox = new JPanel(new BorderLayout());
        JPanel bottomBox = new JPanel(new GridLayout(2, 1));
        // BoxLayout
        
        JPanel topGrid = new JPanel(new GridLayout(1, 3));
        topBox.add(topGrid);
        
        String cartLabelString = "Cart:         Bagging Area:";
 	    JTextArea cartTextArea = new JTextArea(cartLabelString);
 	    cartTextArea.setEditable(false);
 	    cartTextArea.setFont(new Font("Arial", Font.PLAIN, 55));
 	    cartTextArea.setBackground(Color.WHITE);
 	    cartTextArea.setBorder(new EmptyBorder(30, 30, 50, 30));
 	    topGrid.add(cartTextArea);
        
        String addLabelString = "          Add Item By: ";
	    JTextArea addItemTextArea = new JTextArea(addLabelString);
	    addItemTextArea.setEditable(false);
	    addItemTextArea.setFont(new Font("Arial", Font.PLAIN, 55));
	    addItemTextArea.setBackground(Color.WHITE);
	    addItemTextArea.setBorder(new EmptyBorder(30, 30, 50, 30));
	    topGrid.add(addItemTextArea);
	    
	    String baggingLabelString = "            Reciept: ";
	    JTextArea baggingTextArea = new JTextArea(baggingLabelString);
	    baggingTextArea.setEditable(false);
	    baggingTextArea.setFont(new Font("Arial", Font.PLAIN, 55));
	    baggingTextArea.setBackground(Color.WHITE);
	    baggingTextArea.setBorder(new EmptyBorder(30, 30, 50, 30));
	    topGrid.add(baggingTextArea);

        
        JPanel bottomHighRightBox = new JPanel(new GridLayout(1, 1));
        JPanel bottomHighLeftBox = new JPanel(new GridLayout(1, 3));
        
        JPanel bottomLowLeftBox = new JPanel(new GridLayout(1, 1));
        JPanel bottomLowRightBox = new JPanel(new GridLayout(1, 1));
        
        bottomBox.add(bottomHighLeftBox);
        bottomBox.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(1, 1, 1, 1),
                BorderFactory.createLineBorder(Color.BLACK)
        ));
        bottomHighRightBox.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(1, 1, 1, 1),
                BorderFactory.createLineBorder(Color.BLACK)
        ));
        //bottomHighRightBox.add(bottomLowRightBox);
        //bottomHighLeftBox.add(bottomLowLeftBox);
        
        
        bottomBox.add(bottomHighRightBox);
        
        
        bottomBox.add(bottomLowLeftBox);
        bottomBox.add(bottomLowRightBox);
        
        bottomLowLeftBox.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(1, 1, 1, 1),
                BorderFactory.createLineBorder(Color.BLACK)
        ));
        bottomLowRightBox.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(1, 1, 1, 1),
                BorderFactory.createLineBorder(Color.BLACK)
        ));
       
	   float totalCost = 0;
	   String costTxt1 = "TOTAL COST ($): ";
	   JTextArea costTextArea = new JTextArea(costTxt1 + String.valueOf(totalCost));
	   costTextArea.setEditable(false);
	   costTextArea.setFont(new Font("Arial", Font.PLAIN, 60));
	   costTextArea.setBackground(Color.WHITE);
	   costTextArea.setBorder(new EmptyBorder(30, 30, 30, 30));
	   costTextArea.setAlignmentY(Component.CENTER_ALIGNMENT);
	   bottomHighRightBox.add(costTextArea);
	   
	   String errorText = "Scan an item, enter a PLU code, or use the visual catalogue.";
	   errorTextArea = new JTextArea(errorText);
	   errorTextArea.setEditable(false);
	   errorTextArea.setFont(new Font("Arial", Font.PLAIN, 55));
	   errorTextArea.setBackground(Color.WHITE);
	   errorTextArea.setBorder(new EmptyBorder(30, 30, 30, 30));
	   errorTextArea.setAlignmentY(Component.CENTER_ALIGNMENT);
	   errorTextArea.setLineWrap(true);
	   bottomLowLeftBox.add(errorTextArea);
	   
	   float totalWeight = 0;
	   String weightTxt1 = "WEIGHT (kg): ";
	   JTextArea weightTextArea = new JTextArea(weightTxt1 + String.valueOf(totalWeight));
	   weightTextArea.setEditable(false);
	   weightTextArea.setFont(new Font("Arial", Font.PLAIN, 35));
	   weightTextArea.setBackground(Color.WHITE);
	   weightTextArea.setBorder(new EmptyBorder(35, 35, 35, 35));
	   bottomLowRightBox.add(weightTextArea);
	   
	   JButton payButton = new JButton("Pay");
	   payButton.setFont(new Font("Arial", Font.PLAIN, 50)); // Set a larger font size
	   bottomLowRightBox.add(payButton);

       payButton.addActionListener(e -> {
    	   if(logic.cartLogic.getCart().size() != 0) {
        	   logic.stateLogic.gotoState(States.CHECKOUT);
        	   mainGUI.getCardLayout().show(mainGUI.getMainPanel(), "payment");
    	   }
		});

        // Upper box split into 3 equally sized components
        JPanel upperInnerBox = new JPanel(new GridLayout(1, 3));
        JPanel upperRightBox = new JPanel(new GridLayout(1, 2));
        JPanel upperLeftBox = new JPanel(new GridLayout(1, 2));
        upperInnerBox.setBorder(new EmptyBorder(0, 0, 0, 0)); // 20px padding

        // Add list element to the left
        cartList = new DefaultListModel<>();
        JList<String> cartListObjt = new JList<>(cartList);
        for(Item i : mainGUI.getItemsInCart()) {
        		cartList.addElement(mainGUI.getDescriptionOfItem(i));
        }
        cartListObjt.setFont(new Font("Arial", Font.PLAIN, 26));
        cartListObjt.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(20, 20, 20, 20),
                BorderFactory.createLineBorder(Color.BLACK)
        ));
        JScrollPane leftScrollPane = new JScrollPane(cartListObjt);

        // Add another list element to the right
        baggingAreaList = new DefaultListModel<>();
        JList<String> baggingAreaObjt = new JList<>(baggingAreaList);
        baggingAreaObjt.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(20, 20, 20, 20),
                BorderFactory.createLineBorder(Color.BLACK)
        ));
        baggingAreaObjt.setFont(new Font("Arial", Font.PLAIN, 26));
        
        JScrollPane rightScrollPane = new JScrollPane(baggingAreaObjt);
        



        upperInnerBox.add(upperLeftBox);
        upperLeftBox.add(leftScrollPane);
        upperLeftBox.add(rightScrollPane);

   
        

        // Create a panel for the buttons in the middle of the third section
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1));
 	   	
        List<String> stringList = new ArrayList<>();
        stringList.add("Scan");
        stringList.add("PLU Code");
        stringList.add("Visual Catalouge");
        stringList.add("Add to Bagging Area");
        stringList.add("Move back to Cart");

        // Add four buttons to the panel with 30px padding around each
        for (int i = 1; i <= 5; i++) {
            JButton button = new JButton(stringList.get(i-1));
            button.setFont(new Font("Arial", Font.PLAIN, 40)); // Set a larger font size
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.BLACK),
                    new EmptyBorder(25, 10, 25, 10) // 20px padding on top, 30px on both sides, 20px on bottom
            ));
            button.setPreferredSize(new Dimension(100, 50));
            //Dimension ButtonSize = new Dimension(100, buttonPanel.getWidth()-2);
    		//button.setPreferredSize(ButtonSize);
            buttonPanel.add(button);
        }
        
        JButton scanItemButton = (JButton) buttonPanel.getComponent(0);

        scanItemButton.addActionListener(e -> {
            String selectedItem = cartListObjt.getSelectedValue();
			for(Item i : mainGUI.getItemsInCart()) {
		       	if (i instanceof BarcodedItem) {
	        		BarcodedItem bitem = (BarcodedItem) i;
	            	BarcodedProduct bproduct = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(bitem.getBarcode());
	            	if(bproduct.getDescription().contentEquals(selectedItem)) logic.hardware.getMainScanner().scan(bitem);
	        	} else if (i instanceof PLUCodedItem) {
	        		//nothing happens
	        	}
			}
		});
        
        JButton PLUCodedButton = (JButton) buttonPanel.getComponent(1);
        PLUCodedButton.addActionListener(e -> {
        	mainGUI.getCardLayout().show(mainGUI.getMainPanel(), "keypad");
        	createTextSearchWindow();
		});
        
        
        JButton visualCatalogueButton = (JButton) buttonPanel.getComponent(2);
        visualCatalogueButton.addActionListener(e -> {
			mainGUI.getCardLayout().show(mainGUI.getMainPanel(), "keyboard");
		});
        
        JButton addToBaggingAreaButton = (JButton) buttonPanel.getComponent(3);
        addToBaggingAreaButton.addActionListener(e -> {
            String selectedItem = cartListObjt.getSelectedValue();
			for(Item i : mainGUI.getItemsInCart()) {
		       	if (i instanceof BarcodedItem) {
	        		BarcodedItem bitem = (BarcodedItem) i;
	            	BarcodedProduct bproduct = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(bitem.getBarcode());
	                if(bproduct.getDescription().equals(selectedItem)) {
	                	((DefaultListModel) cartListObjt.getModel()).remove(cartListObjt.getSelectedIndex());
		                logic.hardware.getBaggingArea().addAnItem(bitem);
	                	baggingAreaList.addElement(bproduct.getDescription());
	                }
	        	} else if (i instanceof PLUCodedItem) {
	        		PLUCodedItem pitem = (PLUCodedItem) i;
	        		PLUCodedProduct pproduct = ProductDatabases.PLU_PRODUCT_DATABASE.get(pitem.getPLUCode());
	                if(pproduct.getDescription().equals(selectedItem)) {
	                	((DefaultListModel) cartListObjt.getModel()).remove(cartListObjt.getSelectedIndex());
		                logic.hardware.getBaggingArea().addAnItem(pitem);
	                	baggingAreaList.addElement(pproduct.getDescription());
	                }
	        	}
			}
		});
        
        // Add panels to the bottomHighBox
        
       JButton removeItemButton = new JButton("Remove Item");
 	   removeItemButton.setFont(new Font("Arial", Font.PLAIN, 30)); // Set a larger font size
 	   bottomHighLeftBox.add(removeItemButton);
 	   
 	   removeItemButton.addActionListener(e -> {
            String selectedItem = cartListObjt.getSelectedValue();
            ArrayList<Item> snapshotOfCart = new ArrayList<Item>(mainGUI.getItemsInCart());
 			for(Item i : snapshotOfCart) {
	            if(mainGUI.getDescriptionOfItem(i).equals(selectedItem)) {
	            	((DefaultListModel) cartListObjt.getModel()).remove(cartListObjt.getSelectedIndex());
	            	mainGUI.getItemsInCart().remove(i);
	        	}
 			}
 		});

 	   JButton addOwnBagsButton = new JButton("Add own Bags");
 	   addOwnBagsButton.setFont(new Font("Arial", Font.PLAIN, 30)); // Set a larger font size
 	   bottomHighLeftBox.add(addOwnBagsButton);
         

 	   JButton dontBagItemButton = new JButton("Dont bag item");
 	   dontBagItemButton.setFont(new Font("Arial", Font.PLAIN, 30)); // Set a larger font size
 	   bottomHighLeftBox.add(dontBagItemButton);
        
        // Add the button panel to the upper box
        upperInnerBox.add(buttonPanel);

        // Add the upper inner box to the upper box
        upperBox.add(upperInnerBox, BorderLayout.CENTER);

        // Add another list element to the right (bottom)
        DefaultListModel<String> leftRecieptModel = new DefaultListModel<>();
        leftRecieptModel.addElement("Item 5");
        leftRecieptModel.addElement("Item 6");
        JList<String> leftRecieptList = new JList<>(leftRecieptModel);
        leftRecieptList.setFont(new Font("Arial", Font.PLAIN, 26)); // Set the font size
        
        leftRecieptList.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(20, 20, 20, 20),
                BorderFactory.createLineBorder(Color.BLACK)
        ));
        JScrollPane leftRecieptScrollPane = new JScrollPane(leftRecieptList);

        // Add another list element to the right (bottom)
        DefaultListModel<String> rightRecieptModel = new DefaultListModel<>();
        rightRecieptModel.addElement("Item 5");
        rightRecieptModel.addElement("Item 6");
        JList<String> rightRecieptList = new JList<>(rightRecieptModel);
        rightRecieptList.setFont(new Font("Arial", Font.PLAIN, 26)); // Set the font size
        
        rightRecieptList.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(20, 20, 20, 20),
                BorderFactory.createLineBorder(Color.BLACK)
        ));
        JScrollPane rightRecieptScrollPane = new JScrollPane(rightRecieptList);

        upperRightBox.add(leftRecieptScrollPane);
        upperRightBox.add(rightRecieptScrollPane);
        upperInnerBox.add(upperRightBox);
        

        // Set sizes based on ratios
        topBox.setPreferredSize(new Dimension(screenSize.width, (int) (screenSize.height * topBoxRatio)));
        upperBox.setPreferredSize(new Dimension(screenSize.width, (int) (screenSize.height * upperBoxRatio)));
        bottomBox.setPreferredSize(new Dimension(screenSize.width, (int) (screenSize.height * bottomBoxRatio)));

        // Add the upper and bottom boxes to the main panel
        mainPanel.add(topBox, BorderLayout.NORTH);
        mainPanel.add(upperBox, BorderLayout.CENTER);
        mainPanel.add(bottomBox, BorderLayout.SOUTH);

        // Add the main panel to the frame
        add(mainPanel);
    }
    
	public JPanel getPanel() {
		return mainPanel;
	}

	public JTextArea getErrorTextArea() {
		return errorTextArea;
	}
	
	private void createTextSearchWindow() {
		KeypadScreenGUI keypad = new KeypadScreenGUI(mainGUI, logic);
    }

	public JTextArea getWeightTextArea() {
		return weightTextArea;
	}

	public JTextArea getCostTextArea() {
		return costTextArea;
	}
}
