package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.ArrayList;

/**
 * 
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

public class AddItemGUI extends JFrame {
	private CentralStationLogic logic;
	private MainGUI mainGUI;
	private JPanel mainPanel;
	private JTextArea errorTextArea;
	private JTextArea weightTextArea;
	private JTextArea costTextArea;
	DefaultListModel<String> leftReceiptModel = new DefaultListModel<>();
	DefaultListModel<String> rightReceiptModel = new DefaultListModel<>();
	JList<String> rightReceiptList = new JList<>(rightReceiptModel);
	JList<String> leftReceiptList = new JList<>(leftReceiptModel);
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
	    
	    String baggingLabelString = "            Receipt: ";
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
	   costTextArea = new JTextArea(costTxt1 + String.valueOf(totalCost));
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
	   String weightTxt1 = "The weight of added PLU  Items will appear here.";
	   weightTextArea = new JTextArea(weightTxt1);
	   weightTextArea.setLineWrap(true);
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
		   mainGUI.paymentScreen.updateReceiptListNames(getLeftReceiptModel());
		   mainGUI.paymentScreen.updateReceiptListPrices(getRightReceiptModel());
		   mainGUI.paymentScreen.setCartItemList(mainGUI.paymentScreen.getReceiptListNames(), mainGUI.paymentScreen.getReceiptListPrices());
		   mainGUI.paymentScreen.updateCartItemList(mainGUI.paymentScreen.getCartItemModel());
		   mainGUI.paymentScreen.updateCartScrollPanel();
		   mainGUI.paymentScreen.updateTotalPriceText(""+logic.cartLogic.calculateTotalCost().floatValue());

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
        
        // Add another list element to the right (bottom)
        getLeftReceiptList().setFont(new Font("Arial", Font.PLAIN, 26)); // Set the font size
        leftReceiptList.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(20, 20, 20, -1),
                BorderFactory.createLineBorder(Color.BLACK)
        ));

        JScrollPane leftReceiptScrollPane = new JScrollPane(leftReceiptList);

        // Add another list element to the right (bottom)
        // rightReceiptModel = new DefaultListModel<>();
        // JList<String> rightReceiptList = new JList<>(rightReceiptModel);
        getRightReceiptList().setFont(new Font("Arial", Font.PLAIN, 26)); // Set the font size
        
        rightReceiptList.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(20, -1, 20, 20),
                BorderFactory.createLineBorder(Color.BLACK)
        ));
		
        JScrollPane rightReceiptScrollPane = new JScrollPane(rightReceiptList);

        upperInnerBox.add(upperLeftBox);
        upperLeftBox.add(leftScrollPane);
        upperLeftBox.add(rightScrollPane);
        
        // Create a panel for the buttons in the middle of the third section
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1));
 	   	
        List<String> stringList = new ArrayList<>();
        stringList.add("Scan");
        stringList.add("PLU Code");
        stringList.add("Visual Catalogue");
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
	            	if(bproduct.getDescription().contentEquals(selectedItem)) {
	            		logic.hardware.getMainScanner().scan(bitem);
//		            	leftReceiptModel.addElement(mainGUI.getDescriptionOfItem(bitem));
//		            	rightReceiptModel.addElement("$"+bproduct.getPrice());
	            	}
	        	} else if (i instanceof PLUCodedItem) {
	        		//nothing happens
	        	}
			}
			updateReceipt();
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
	    	baggingAreaList.addElement(selectedItem);
	    	mainGUI.getItemsInCart().remove(mainGUI.getItemFromDescription(selectedItem));
	    	mainGUI.getItemsInBaggingArea().add(mainGUI.getItemFromDescription(selectedItem));
	        logic.hardware.getBaggingArea().addAnItem(mainGUI.getItemFromDescription(selectedItem));
	        ((DefaultListModel) cartListObjt.getModel()).remove(cartListObjt.getSelectedIndex());
	        updateReceipt();
		});

        JButton moveBackToCartButton = (JButton) buttonPanel.getComponent(4);
	        moveBackToCartButton.addActionListener(e -> {
	        String selectedItem = baggingAreaObjt.getSelectedValue();
			logic.hardware.getBaggingArea().removeAnItem(mainGUI.getItemFromDescription(selectedItem));
			mainGUI.getItemsInCart().add(mainGUI.getItemFromDescription(selectedItem));
			cartList.addElement(selectedItem);
			((DefaultListModel) baggingAreaObjt.getModel()).remove(baggingAreaObjt.getSelectedIndex());
            updateReceipt();
		});
        
        // Add panels to the bottomHighBox
        
       JButton removeItemButton = new JButton("Remove Item");
 	   removeItemButton.setFont(new Font("Arial", Font.PLAIN, 30)); // Set a larger font size
 	   bottomHighLeftBox.add(removeItemButton);
 	   removeItemButton.addActionListener(e -> {
            String selectedItem = leftReceiptList.getSelectedValue();
 			for(Item i : logic.cartLogic.getCart().keySet()) {
	            if(mainGUI.getDescriptionOfItem(i).equals(selectedItem)) {
	            	if(i instanceof BarcodedItem) {
	            		BarcodedItem bitem = (BarcodedItem) i;
	            		logic.removeItemLogic.removeBarcodedItem(bitem);
	            	} else if (i instanceof PLUCodedItem ) {
	            		PLUCodedItem pitem = (PLUCodedItem) i;
	            		logic.removeItemLogic.checkCartForPLUCodedItemToRemove(pitem.getPLUCode());
	            		getErrorTextArea().setText("Remove the PLU Item from the bagging area.");
	            	}
	        	}
	            updateReceipt();
 			}
 		});
 	   
       JButton attendantButton = new JButton("Call Attendant");
       attendantButton.setFont(new Font("Arial", Font.PLAIN, 30)); // Set a larger font size
 	   bottomHighLeftBox.add(attendantButton);
 	   attendantButton.addActionListener(e -> {
 		   mainGUI.getAttendantScreen().getStationObjects(0).getSquare().setBackground(Color.red);
 		});
 	   
        
        // Add the button panel to the upper box
        upperInnerBox.add(buttonPanel);

        // Add the upper inner box to the upper box
        upperBox.add(upperInnerBox, BorderLayout.CENTER);

        upperRightBox.add(leftReceiptScrollPane);
        upperRightBox.add(rightReceiptScrollPane);
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

    public void updateReceipt() {
    	rightReceiptModel.clear();
    	leftReceiptModel.clear();
		

    	for(Item i : logic.cartLogic.getCart().keySet()) {
    		leftReceiptModel.addElement(mainGUI.getDescriptionOfItem(i));
    		
    		double price = 0;
    		String formattedPrice ="";
    		if(i instanceof BarcodedItem) {
    			BarcodedItem bitem = (BarcodedItem) i;
    			price = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(bitem.getBarcode()).getPrice();
    		}
    		if(i instanceof PLUCodedItem) {
    			PLUCodedItem pitem = (PLUCodedItem) i;
    			PLUCodedProduct pproduct = ProductDatabases.PLU_PRODUCT_DATABASE.get(pitem.getPLUCode());
    			price = logic.cartLogic.calculatePriceOfPLU(pproduct.getPrice(), pitem.getMass());
    		}
    		formattedPrice = String.format("$%.2f", price);
    		rightReceiptModel.addElement(formattedPrice);
    	}
    }

	// Getter methods to access the JLists
    public JList<String> getRightReceiptList() {
        return rightReceiptList;
    }

    public JList<String> getLeftReceiptList() {
        return leftReceiptList;
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

	// Getter for leftReceiptModel
    public DefaultListModel<String> getLeftReceiptModel() {
        return leftReceiptModel;
    }

    // Setter for leftReceiptModel
    public void setLeftReceiptModel(List<String> items) {
        leftReceiptModel.clear(); // Clear existing items

        for (String item : items) {
            leftReceiptModel.addElement(item);
        }
    }

    // Getter for rightReceiptModel
    public DefaultListModel<String> getRightReceiptModel() {
        return rightReceiptModel;
    }

    // Setter for rightReceiptModel
    public void setRightReceiptModel(List<String> items) {
        rightReceiptModel.clear(); // Clear existing items

        for (String item : items) {
            rightReceiptModel.addElement(item);
        }
    }
}
