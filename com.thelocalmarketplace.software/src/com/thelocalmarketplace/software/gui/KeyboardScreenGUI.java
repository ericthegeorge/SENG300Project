package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import java.awt.*;

public class KeyboardScreenGUI {
	
	// Variables for the screen
	JFrame keyboardScreenFrame;
	JPanel keyboardScreenPanel;
	JPanel keyRowOnePanel;
	JPanel keyRowTwoPanel;
	JPanel keyRowThreePanel;
	JPanel keyRowFourPanel;
	JPanel keyRowFivePanel;
	JPanel keyRowSixPanel;
	JPanel textAreaPanel;
	JTabbedPane visualAlphaPanel;
	JPanel item_A;
	JPanel item_BtoC;
	JPanel item_DtoJ;
	JPanel item_KtoM;
	JPanel item_NtoP;
	JPanel item_QtoS;
	JPanel item_TtoZ;
	JTextArea textArea;
	JLabel textBar;
	String searchText;
	
	
	// Constructor for the screen
	public KeyboardScreenGUI() {
		keyboardScreenFrame = new JFrame("The LocalMarketplace Self-Checkout Station");
		keyboardScreenPanel = new JPanel();
		keyRowOnePanel = new JPanel();
		keyRowTwoPanel = new JPanel();
		keyRowThreePanel = new JPanel();
		keyRowFourPanel = new JPanel();
		keyRowFivePanel = new JPanel();
		keyRowSixPanel = new JPanel();
		textAreaPanel = new JPanel();
		visualAlphaPanel = new JTabbedPane();
		item_A = new JPanel();
		item_BtoC = new JPanel();
		item_DtoJ = new JPanel();
		item_KtoM = new JPanel();
		item_NtoP = new JPanel();
		item_QtoS = new JPanel();
		item_TtoZ = new JPanel();
		textBar = new JLabel(searchText);
		textArea = new JTextArea("Enter Item Name or code");
		
		searchText = "ENTER ITEM NAME OR CODE";
		
		addWidgets();
		
		// TODO: Build out the rest of the screen. 
		keyboardScreenFrame.getContentPane().add(keyboardScreenPanel, BorderLayout.CENTER);
		
		
		keyboardScreenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		keyboardScreenFrame.pack();
		keyboardScreenFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		keyboardScreenFrame.setVisible(true);
		
	}
	
	// Adds all the buttons
	private void addWidgets() {
		// Create Layouts
		keyboardScreenPanel.setLayout(new BoxLayout(keyboardScreenPanel, BoxLayout.Y_AXIS));
		keyRowOnePanel.setLayout(new BoxLayout(keyRowOnePanel, BoxLayout.X_AXIS));
		keyRowTwoPanel.setLayout(new BoxLayout(keyRowTwoPanel, BoxLayout.X_AXIS));
		keyRowThreePanel.setLayout(new BoxLayout(keyRowThreePanel, BoxLayout.X_AXIS));
		keyRowFourPanel.setLayout(new BoxLayout(keyRowFourPanel, BoxLayout.X_AXIS));
		keyRowFivePanel.setLayout(new BoxLayout(keyRowFivePanel, BoxLayout.X_AXIS));
		keyRowSixPanel.setLayout(new BoxLayout(keyRowSixPanel, BoxLayout.X_AXIS));
		textAreaPanel.setLayout(new BoxLayout(textAreaPanel, BoxLayout.X_AXIS));
		
		item_A.setLayout(new BoxLayout(item_A, BoxLayout.X_AXIS));
		item_BtoC.setLayout(new BoxLayout(item_BtoC, BoxLayout.X_AXIS));
		item_DtoJ.setLayout(new BoxLayout(item_DtoJ, BoxLayout.X_AXIS));
		item_KtoM.setLayout(new BoxLayout(item_KtoM, BoxLayout.X_AXIS));
		item_NtoP.setLayout(new BoxLayout(item_NtoP, BoxLayout.X_AXIS));
		item_QtoS.setLayout(new BoxLayout(item_QtoS, BoxLayout.X_AXIS));
		item_TtoZ.setLayout(new BoxLayout(item_TtoZ, BoxLayout.X_AXIS));
		
		
		// Build Tab Layout
		
		item_A.add(Box.createRigidArea(new Dimension(5,0)));
		item_A.add(new JButton(" Apples "));
		item_A.add(Box.createRigidArea(new Dimension(5,0)));
		item_A.add(new JButton(" Avocado "));
		item_A.add(Box.createRigidArea(new Dimension(5,0)));
		item_A.add(new JButton(" Asparagus "));
		item_A.add(Box.createRigidArea(new Dimension(5,0)));
		item_BtoC.add(Box.createRigidArea(new Dimension(5,0)));
		item_BtoC.add(new JButton(" Blueberries "));
		item_BtoC.add(Box.createRigidArea(new Dimension(5,0)));
		item_BtoC.add(new JButton(" Beets "));
		item_BtoC.add(Box.createRigidArea(new Dimension(5,0)));
		item_BtoC.add(new JButton(" Celery "));
		item_BtoC.add(Box.createRigidArea(new Dimension(5,0)));
		item_DtoJ.add(Box.createRigidArea(new Dimension(5,0)));
		item_DtoJ.add(new JButton(" Endive "));
		item_DtoJ.add(Box.createRigidArea(new Dimension(5,0)));
		item_DtoJ.add(new JButton(" Grapes "));
		item_DtoJ.add(Box.createRigidArea(new Dimension(5,0)));
		item_DtoJ.add(new JButton(" Jicama "));
		item_DtoJ.add(Box.createRigidArea(new Dimension(5,0)));
		item_KtoM.add(Box.createRigidArea(new Dimension(5,0)));
		item_KtoM.add(new JButton(" Kale "));
		item_KtoM.add(Box.createRigidArea(new Dimension(5,0)));
		item_KtoM.add(new JButton(" Lettuce "));
		item_KtoM.add(Box.createRigidArea(new Dimension(5,0)));
		item_KtoM.add(new JButton(" Mangos "));
		item_KtoM.add(Box.createRigidArea(new Dimension(5,0)));
		item_NtoP.add(Box.createRigidArea(new Dimension(5,0)));
		item_NtoP.add(new JButton(" Green Peppers "));
		item_NtoP.add(Box.createRigidArea(new Dimension(5,0)));
		item_NtoP.add(new JButton(" Onions "));
		item_NtoP.add(Box.createRigidArea(new Dimension(5,0)));
		item_NtoP.add(new JButton(" Red Peppers "));
		item_NtoP.add(Box.createRigidArea(new Dimension(5,0)));
		item_QtoS.add(Box.createRigidArea(new Dimension(5,0)));
		item_QtoS.add(new JButton(" Radishes "));
		item_QtoS.add(Box.createRigidArea(new Dimension(5,0)));
		item_QtoS.add(new JButton(" Shallots "));
		item_QtoS.add(Box.createRigidArea(new Dimension(5,0)));
		item_QtoS.add(new JButton(" Spinach "));
		item_QtoS.add(Box.createRigidArea(new Dimension(5,0)));
		item_TtoZ.add(Box.createRigidArea(new Dimension(5,0)));
		item_TtoZ.add(new JButton(" Tomatoes "));
		item_TtoZ.add(Box.createRigidArea(new Dimension(5,0)));
		item_TtoZ.add(new JButton(" Yams "));
		item_TtoZ.add(Box.createRigidArea(new Dimension(5,0)));
		item_TtoZ.add(new JButton(" Watermelon "));
		item_TtoZ.add(Box.createRigidArea(new Dimension(5,0)));

		// Add Tabs to layout
		visualAlphaPanel.addTab("        A        ", item_A);
		visualAlphaPanel.addTab("      B - C      ", item_BtoC);
		visualAlphaPanel.addTab("      D - J      ", item_DtoJ);
		visualAlphaPanel.addTab("      K - M      ", item_KtoM);
		visualAlphaPanel.addTab("      N - P      ", item_NtoP);
		visualAlphaPanel.addTab("      Q - S      ", item_QtoS);
		visualAlphaPanel.addTab("      T - Z      ", item_TtoZ);
		
		// Add Buttons for keyboard
		keyRowOnePanel.add(new JButton(" Q "));
		keyRowOnePanel.add(Box.createRigidArea(new Dimension(5,50)));
		keyRowOnePanel.add(new JButton(" W "));
		keyRowOnePanel.add(Box.createRigidArea(new Dimension(5,50)));
		keyRowOnePanel.add(new JButton(" E "));
		keyRowOnePanel.add(Box.createRigidArea(new Dimension(5,50)));
		keyRowOnePanel.add(new JButton(" R "));
		keyRowOnePanel.add(Box.createRigidArea(new Dimension(5,50)));
		keyRowOnePanel.add(new JButton(" T "));
		keyRowOnePanel.add(Box.createRigidArea(new Dimension(5,50)));
		keyRowOnePanel.add(new JButton(" Y "));
		keyRowOnePanel.add(Box.createRigidArea(new Dimension(5,50)));
		keyRowOnePanel.add(new JButton(" U "));
		keyRowOnePanel.add(Box.createRigidArea(new Dimension(5,50)));
		keyRowOnePanel.add(new JButton(" I "));
		keyRowOnePanel.add(Box.createRigidArea(new Dimension(5,50)));
		keyRowOnePanel.add(new JButton(" O "));
		keyRowOnePanel.add(Box.createRigidArea(new Dimension(5,50)));
		keyRowOnePanel.add(new JButton(" P "));
		keyRowOnePanel.add(Box.createRigidArea(new Dimension(5,50)));
		keyRowOnePanel.add(new JButton(" _ "));
		
		keyRowTwoPanel.add(new JButton(" A "));
		keyRowTwoPanel.add(Box.createRigidArea(new Dimension(5,0)));
		keyRowTwoPanel.add(new JButton(" S "));
		keyRowTwoPanel.add(Box.createRigidArea(new Dimension(5,0)));
		keyRowTwoPanel.add(new JButton(" D "));
		keyRowTwoPanel.add(Box.createRigidArea(new Dimension(5,0)));
		keyRowTwoPanel.add(new JButton(" F "));
		keyRowTwoPanel.add(Box.createRigidArea(new Dimension(5,0)));
		keyRowTwoPanel.add(new JButton(" G "));
		keyRowTwoPanel.add(Box.createRigidArea(new Dimension(5,0)));
		keyRowTwoPanel.add(new JButton(" H "));
		keyRowTwoPanel.add(Box.createRigidArea(new Dimension(5,0)));
		keyRowTwoPanel.add(new JButton(" J "));
		keyRowTwoPanel.add(Box.createRigidArea(new Dimension(5,0)));
		keyRowTwoPanel.add(new JButton(" K "));
		keyRowTwoPanel.add(Box.createRigidArea(new Dimension(5,0)));
		keyRowTwoPanel.add(new JButton(" L "));
		keyRowTwoPanel.add(Box.createRigidArea(new Dimension(5,0)));
		keyRowTwoPanel.add(new JButton(" @ "));
		
		keyRowThreePanel.add(new JButton(" Z "));
		keyRowThreePanel.add(Box.createRigidArea(new Dimension(5,50)));
		keyRowThreePanel.add(new JButton(" X "));
		keyRowThreePanel.add(Box.createRigidArea(new Dimension(5,50)));
		keyRowThreePanel.add(new JButton(" C "));
		keyRowThreePanel.add(Box.createRigidArea(new Dimension(5,50)));
		keyRowThreePanel.add(new JButton(" V "));
		keyRowThreePanel.add(Box.createRigidArea(new Dimension(5,50)));
		keyRowThreePanel.add(new JButton(" B "));
		keyRowThreePanel.add(Box.createRigidArea(new Dimension(5,50)));
		keyRowThreePanel.add(new JButton(" N "));
		keyRowThreePanel.add(Box.createRigidArea(new Dimension(5,50)));
		keyRowThreePanel.add(new JButton(" M "));
		keyRowThreePanel.add(Box.createRigidArea(new Dimension(5,50)));
		keyRowThreePanel.add(new JButton(" , "));
		keyRowThreePanel.add(Box.createRigidArea(new Dimension(5,50)));
		keyRowThreePanel.add(new JButton(" . "));
		keyRowThreePanel.add(Box.createRigidArea(new Dimension(5,50)));
		keyRowThreePanel.add(new JButton(" - "));
		
		keyRowFourPanel.add(new JButton(" GO BACK "));
		keyRowFourPanel.add(Box.createRigidArea(new Dimension(5,0)));
		keyRowFourPanel.add(new JButton(" SPACE "));
		keyRowFourPanel.add(Box.createRigidArea(new Dimension(5,0)));
		keyRowFourPanel.add(new JButton(" BACKSPACE "));
		keyRowFourPanel.add(Box.createRigidArea(new Dimension(5,0)));
		keyRowFourPanel.add(new JButton(" ENTER "));
		
		keyRowFivePanel.add(new JButton(" 1 "));
		keyRowFivePanel.add(Box.createRigidArea(new Dimension(5,0)));
		keyRowFivePanel.add(new JButton(" 2 "));
		keyRowFivePanel.add(Box.createRigidArea(new Dimension(5,0)));
		keyRowFivePanel.add(new JButton(" 3 "));
		keyRowFivePanel.add(Box.createRigidArea(new Dimension(5,0)));
		keyRowFivePanel.add(new JButton(" 4 "));
		keyRowFivePanel.add(Box.createRigidArea(new Dimension(5,0)));
		keyRowFivePanel.add(new JButton(" 5 "));
		keyRowFivePanel.add(Box.createRigidArea(new Dimension(5,0)));
		keyRowFivePanel.add(new JButton(" 6 "));
		keyRowFivePanel.add(Box.createRigidArea(new Dimension(5,0)));
		keyRowFivePanel.add(new JButton(" 7 "));
		keyRowFivePanel.add(Box.createRigidArea(new Dimension(5,0)));
		keyRowFivePanel.add(new JButton(" 8 "));
		keyRowFivePanel.add(Box.createRigidArea(new Dimension(5,0)));
		keyRowFivePanel.add(new JButton(" 9 "));
		keyRowFivePanel.add(Box.createRigidArea(new Dimension(5,0)));
		keyRowFivePanel.add(new JButton(" 0 "));
		
		keyRowSixPanel.add(Box.createRigidArea(new Dimension(0,50)));
		keyRowSixPanel.add(new JButton(" ATTENDANT ASSISTANCE "));
		keyRowSixPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		// Build Text Box
		textBar.setText(searchText);
		textAreaPanel.add(textBar);
		textAreaPanel.add(Box.createRigidArea(new Dimension(0,50)));
		
		// Add the smaller containers to the main panel
		
		keyboardScreenPanel.add(visualAlphaPanel, BorderLayout.CENTER);
		
		keyboardScreenPanel.add(textAreaPanel);
		keyboardScreenPanel.add(keyRowFivePanel);
		keyboardScreenPanel.add(keyRowOnePanel);
		keyboardScreenPanel.add(keyRowTwoPanel);
		keyboardScreenPanel.add(keyRowThreePanel);
		keyboardScreenPanel.add(keyRowFourPanel);
		keyboardScreenPanel.add(keyRowSixPanel);
		
		
	}
	
	// Just for testing screen.
	public static void main(String[] args) {
		KeyboardScreenGUI keyboardGUI = new KeyboardScreenGUI();
	}
}

