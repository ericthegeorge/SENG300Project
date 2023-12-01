package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class CheckoutGUI extends JFrame {

    public CheckoutGUI() {
        // Set up the main frame
        setTitle("Checkout Station");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Make it full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        // Get the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Create components
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Define ratios for panel sizes
        double upperBoxRatio = 2.0 / 3.0;
        double bottomBoxRatio = 1.0 / 3.0;

        // Split the page into two boxes horizontally
        JPanel upperBox = new JPanel(new BorderLayout());
        JPanel bottomBox = new JPanel(new GridLayout(2, 1));
        
    
        
        
        
        
        
        JPanel bottomLowBox = new JPanel(new GridLayout(1, 1));
        
        JPanel bottomHighBox = new JPanel(new GridLayout(1, 1));
        JPanel bottomHighLeftBox = new JPanel(new GridLayout(1, 1));
        JPanel bottomHighRightBox = new JPanel(new GridLayout(1, 1));
        
        bottomBox.add(bottomHighBox);
        bottomHighBox.add(bottomHighRightBox);
        bottomHighBox.add(bottomHighLeftBox);
        
        
        bottomBox.add(bottomLowBox);
        
        
     // Add panels to the bottomHighBox
    
        	JButton button2 = new JButton("Remove Item");
	        button2.setFont(new Font("Arial", Font.PLAIN, 20)); // Set a larger font size
	        button2.setBorder(new EmptyBorder(30, 100, 30, 100)); // 30px padding around each button, adjust width as needed
	        Dimension ButtonSize2 = new Dimension(30, 70);
			button2.setMaximumSize(ButtonSize2);
			bottomHighLeftBox.add(button2);
        

			JButton button3 = new JButton("Add own Bags");
			button3.setFont(new Font("Arial", Font.PLAIN, 20)); // Set a larger font size
	        button3.setBorder(new EmptyBorder(30, 100, 30, 100)); // 30px padding around each button, adjust width as needed
	        Dimension ButtonSize3 = new Dimension(30, 70);
			button3.setMaximumSize(ButtonSize3);
			bottomHighLeftBox.add(button3);
        

			JButton button4 = new JButton("Dont bag item");
			button4.setFont(new Font("Arial", Font.PLAIN, 20)); // Set a larger font size
	        button4.setBorder(new EmptyBorder(30, 100, 30, 100)); // 30px padding around each button, adjust width as needed
	        Dimension ButtonSize4 = new Dimension(30, 70);
			button4.setMaximumSize(ButtonSize4);
			bottomHighLeftBox.add(button4);
        
			JButton button5 = new JButton("testing");
			button5.setFont(new Font("Arial", Font.PLAIN, 20)); // Set a larger font size
	        button5.setBorder(new EmptyBorder(30, 100, 30, 100)); // 30px padding around each button, adjust width as needed
	        Dimension ButtonSize5 = new Dimension(30, 300);
			button5.setMaximumSize(ButtonSize5);
			bottomHighRightBox.add(button5);
        


        // Upper box split into 3 equally sized components
        JPanel upperInnerBox = new JPanel(new GridLayout(1, 3));
        JPanel upperRightBox = new JPanel(new GridLayout(1, 2));
        upperInnerBox.setBorder(new EmptyBorder(20, 20, 20, 20)); // 20px padding

        // Add list element to the left
        DefaultListModel<String> leftListModel = new DefaultListModel<>();
        leftListModel.addElement("Item 1");
        leftListModel.addElement("Item 2");
        JList<String> leftList = new JList<>(leftListModel);
        leftList.setFont(new Font("Arial", Font.PLAIN, 26));
        leftList.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(20, 20, 20, 20),
                BorderFactory.createLineBorder(Color.BLACK)
        ));
        JScrollPane leftScrollPane = new JScrollPane(leftList);
        upperInnerBox.add(leftScrollPane);

        // Create a panel for the buttons in the middle of the third section
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                new EmptyBorder(10, 10, 10, 10) // 20px padding on top, 30px on both sides, 20px on bottom
        ));
        
        List<String> stringList = new ArrayList<>();
        stringList.add("Barcode Scan");
        stringList.add("Handheld Scan");
        stringList.add("PLU Code");
        stringList.add("Visual Catalouge");

        // Add four buttons to the panel with 30px padding around each
        for (int i = 1; i <= 4; i++) {
            JButton button = new JButton(stringList.get(i-1));
            button.setFont(new Font("Arial", Font.PLAIN, 56)); // Set a larger font size
            button.setBorder(new EmptyBorder(30, 100, 30, 100)); // 30px padding around each button, adjust width as needed
            Dimension ButtonSize = new Dimension(300, 500);
    		button.setMaximumSize(ButtonSize);
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

        // Add your components to the bottom box
        JPanel bottomLeftPanel = new JPanel();
        // Add components to the bottom left panel as needed
        bottomBox.add(bottomLeftPanel);
        upperRightBox.add(rightBottomScrollPane);
        

        // Set sizes based on ratios
        upperBox.setPreferredSize(new Dimension(screenSize.width, (int) (screenSize.height * upperBoxRatio)));
        bottomBox.setPreferredSize(new Dimension(screenSize.width, (int) (screenSize.height * bottomBoxRatio)));

        // Add the upper and bottom boxes to the main panel
        mainPanel.add(upperBox, BorderLayout.NORTH);
        mainPanel.add(bottomBox, BorderLayout.CENTER);

        // Add the main panel to the frame
        add(mainPanel);

        // Set the frame visibility
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CheckoutGUI());
    }
}

