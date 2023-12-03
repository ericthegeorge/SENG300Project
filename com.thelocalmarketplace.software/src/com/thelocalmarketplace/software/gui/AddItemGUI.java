package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.thelocalmarketplace.software.logic.CentralStationLogic;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class AddItemGUI extends JFrame {
	private CentralStationLogic logic;
	private MainGUI mainGUI;
	private JPanel mainPanel;

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
        
        String cartLabelString = "Cart: ";
 	    JTextArea cartTextArea = new JTextArea(cartLabelString);
 	    cartTextArea.setEditable(false);
 	    cartTextArea.setFont(new Font("Arial", Font.PLAIN, 55));
 	    cartTextArea.setBackground(Color.WHITE);
 	    cartTextArea.setBorder(new EmptyBorder(30, 30, 50, 30));
 	    topGrid.add(cartTextArea);
        
        String addLabelString = "Add Item By: ";
	    JTextArea addItemTextArea = new JTextArea(addLabelString);
	    addItemTextArea.setEditable(false);
	    addItemTextArea.setFont(new Font("Arial", Font.PLAIN, 55));
	    addItemTextArea.setBackground(Color.WHITE);
	    addItemTextArea.setBorder(new EmptyBorder(30, 30, 50, 30));
	    topGrid.add(addItemTextArea);
	    
	    String baggingLabelString = "Bagging Area: ";
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
       

        
     // Add panels to the bottomHighBox
    
       JButton removeItemButton = new JButton("Remove Item");
	   removeItemButton.setFont(new Font("Arial", Font.PLAIN, 30)); // Set a larger font size
	   bottomHighLeftBox.add(removeItemButton);
        

	   JButton addOwnBagsButton = new JButton("Add own Bags");
	   addOwnBagsButton.setFont(new Font("Arial", Font.PLAIN, 30)); // Set a larger font size
	   bottomHighLeftBox.add(addOwnBagsButton);
        

	   JButton dontBagItemButton = new JButton("Dont bag item");
	   dontBagItemButton.setFont(new Font("Arial", Font.PLAIN, 30)); // Set a larger font size
	   bottomHighLeftBox.add(dontBagItemButton);
			
			
	   float totalCost = 0;
	   String costTxt1 = "TOTAL COST ($): ";
	   JTextArea costTextArea = new JTextArea(costTxt1 + String.valueOf(totalCost));
	   costTextArea.setEditable(false);
	   costTextArea.setFont(new Font("Arial", Font.PLAIN, 60));
	   costTextArea.setBackground(Color.WHITE);
	   costTextArea.setBorder(new EmptyBorder(30, 30, 30, 30));
	   costTextArea.setAlignmentY(Component.CENTER_ALIGNMENT);
	   bottomHighRightBox.add(costTextArea);
	   
	   String errorText = "error / notifications";
	   JTextArea errorTextArea = new JTextArea(errorText);
	   errorTextArea.setEditable(false);
	   errorTextArea.setFont(new Font("Arial", Font.PLAIN, 55));
	   errorTextArea.setBackground(Color.WHITE);
	   errorTextArea.setBorder(new EmptyBorder(30, 30, 30, 30));
	   errorTextArea.setAlignmentY(Component.CENTER_ALIGNMENT);
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

        // Upper box split into 3 equally sized components
        JPanel upperInnerBox = new JPanel(new GridLayout(1, 3));
        JPanel upperRightBox = new JPanel(new GridLayout(1, 2));
        upperInnerBox.setBorder(new EmptyBorder(0, 0, 0, 0)); // 20px padding

        // Add list element to the left
        DefaultListModel<String> cartList = new DefaultListModel<>();
        cartList.addElement("Item 1");
        cartList.addElement("Item 2");
        JList<String> cartListObjt = new JList<>(cartList);
        cartListObjt.setFont(new Font("Arial", Font.PLAIN, 26));
        cartListObjt.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(20, 20, 20, 20),
                BorderFactory.createLineBorder(Color.BLACK)
        ));
        JScrollPane leftScrollPane = new JScrollPane(cartListObjt);
        upperInnerBox.add(leftScrollPane);

        // Create a panel for the buttons in the middle of the third section
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1));
 	   	
       
        List<String> stringList = new ArrayList<>();
        stringList.add("Scan");
        stringList.add("PLU Code");
        stringList.add("Visual Catalouge");
        stringList.add("Add to Bagging Area");

        // Add four buttons to the panel with 30px padding around each
        for (int i = 1; i <= 4; i++) {
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
        
        

        // Add the button panel to the upper box
        upperInnerBox.add(buttonPanel);

        // Add another list element to the right
        DefaultListModel<String> rightTopListModel = new DefaultListModel<>();
        rightTopListModel.addElement("Item 3");
        rightTopListModel.addElement("Item 4");
        JList<String> rightTopList = new JList<>(rightTopListModel);
        rightTopList.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(20, 20, 20, 20),
                BorderFactory.createLineBorder(Color.BLACK)
        ));
        rightTopList.setFont(new Font("Arial", Font.PLAIN, 26));
        
        JScrollPane rightTopScrollPane = new JScrollPane(rightTopList);
        upperInnerBox.add(upperRightBox);
        upperRightBox.add(rightTopScrollPane);

        // Add the upper inner box to the upper box
        upperBox.add(upperInnerBox, BorderLayout.CENTER);

        // Add another list element to the right (bottom)
        DefaultListModel<String> rightRightListModel = new DefaultListModel<>();
        rightRightListModel.addElement("Item 5");
        rightRightListModel.addElement("Item 6");
        JList<String> rightBottomList = new JList<>(rightRightListModel);
        rightBottomList.setFont(new Font("Arial", Font.PLAIN, 26)); // Set the font size
        
        rightBottomList.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(20, 20, 20, 20),
                BorderFactory.createLineBorder(Color.BLACK)
        ));
        JScrollPane rightBottomScrollPane = new JScrollPane(rightBottomList);

        upperRightBox.add(rightBottomScrollPane);
        

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
}
