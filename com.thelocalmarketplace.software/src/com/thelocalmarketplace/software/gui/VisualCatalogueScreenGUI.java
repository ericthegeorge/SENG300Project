package com.thelocalmarketplace.software.gui;

import javax.swing.*;

import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.software.logic.CentralStationLogic;

import java.awt.*;

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


public class VisualCatalogueScreenGUI {
	CentralStationLogic logic;
	MainGUI mainGUI;
	
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
	public VisualCatalogueScreenGUI(MainGUI m, CentralStationLogic l) {
		mainGUI = m;
		logic = l;
		keyboardScreenFrame = new JFrame("Visual Catalogue");
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
		textArea = new JTextArea("SELECT AN ITEM");
		
		searchText = "SELECT AN ITEM";
		
		addWidgets();
		
		// TODO: Build out the rest of the screen. 
		keyboardScreenFrame.getContentPane().add(keyboardScreenPanel, BorderLayout.CENTER);
		
		
		keyboardScreenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		keyboardScreenFrame.pack();
		keyboardScreenFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	
	// Adds all the buttons and organizes the layouts
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
		JButton apple_button = new JButton(SimulatedItems.apple_description);
		item_A.add(apple_button);
		apple_button.addActionListener(e -> {
			addText(SimulatedItems.apple_description);
    	});
		item_A.add(Box.createRigidArea(new Dimension(5,0)));
		JButton avocado_button = new JButton(SimulatedItems.avocado_description);
		item_A.add(avocado_button);
		avocado_button.addActionListener(e -> {
    		addText(SimulatedItems.avocado_description);
    		
    	});
		item_A.add(Box.createRigidArea(new Dimension(5,0)));
		JButton asparagus_button = new JButton(SimulatedItems.asparagus_description);
		item_A.add(asparagus_button);
		asparagus_button.addActionListener(e -> {
    		addText(SimulatedItems.asparagus_description);
    		
    	});
		item_A.add(Box.createRigidArea(new Dimension(5,0)));
		item_BtoC.add(Box.createRigidArea(new Dimension(5,0)));
		JButton blueberries_button = new JButton(SimulatedItems.blueberries_description);
		item_BtoC.add(blueberries_button);
		blueberries_button.addActionListener(e -> {
    		addText(SimulatedItems.blueberries_description);
    		
    	});
		item_BtoC.add(Box.createRigidArea(new Dimension(5,0)));
		JButton beets_button = new JButton(SimulatedItems.beets_description);
		item_BtoC.add(beets_button);
		beets_button.addActionListener(e -> {
    		addText(SimulatedItems.beets_description);
    		
    	});
		item_BtoC.add(Box.createRigidArea(new Dimension(5,0)));
		JButton celery_button = new JButton(SimulatedItems.celery_description);
		item_BtoC.add(celery_button);
		celery_button.addActionListener(e -> {
    		addText(SimulatedItems.celery_description);
    		
    	});
		item_BtoC.add(Box.createRigidArea(new Dimension(5,0)));
		item_DtoJ.add(Box.createRigidArea(new Dimension(5,0)));
		JButton endive_button = new JButton(SimulatedItems.endive_description);
		item_DtoJ.add(endive_button);
		endive_button.addActionListener(e -> {
    		addText(SimulatedItems.endive_description);
    		
    	});
		item_DtoJ.add(Box.createRigidArea(new Dimension(5,0)));
		JButton grapes_button = new JButton(SimulatedItems.grapes_description);
		item_DtoJ.add(grapes_button);
		grapes_button.addActionListener(e -> {
    		addText(SimulatedItems.grapes_description);
    		
    	});
		item_DtoJ.add(Box.createRigidArea(new Dimension(5,0)));
		JButton jicama_button = new JButton(SimulatedItems.jicama_description);
		item_DtoJ.add(jicama_button);
		jicama_button.addActionListener(e -> {
    		addText(SimulatedItems.jicama_description);
    		
    	});
		item_DtoJ.add(Box.createRigidArea(new Dimension(5,0)));
		item_KtoM.add(Box.createRigidArea(new Dimension(5,0)));
		JButton kale_button = new JButton(SimulatedItems.kale_description);
		item_KtoM.add(kale_button);
		kale_button.addActionListener(e -> {
    		addText(SimulatedItems.kale_description);
    		
    	});
		item_KtoM.add(Box.createRigidArea(new Dimension(5,0)));
		JButton lettuce_button = new JButton(SimulatedItems.lettuce_description);
		item_KtoM.add(lettuce_button);
		lettuce_button.addActionListener(e -> {
    		addText(SimulatedItems.lettuce_description);
    		
    	});
		item_KtoM.add(Box.createRigidArea(new Dimension(5,0)));
		JButton mango_button = new JButton(SimulatedItems.mangos_description);
		item_KtoM.add(mango_button);
		mango_button.addActionListener(e -> {
    		addText(SimulatedItems.mangos_description);
    		
    	});
		item_KtoM.add(Box.createRigidArea(new Dimension(5,0)));
		item_NtoP.add(Box.createRigidArea(new Dimension(5,0)));
		JButton grnpeppers_button = new JButton(SimulatedItems.greenpeppers_description);
		item_NtoP.add(grnpeppers_button);
		grnpeppers_button.addActionListener(e -> {
    		addText(SimulatedItems.greenpeppers_description);
    		
    	});
		item_NtoP.add(Box.createRigidArea(new Dimension(5,0)));
		JButton onions_button = new JButton(SimulatedItems.onions_description);
		item_NtoP.add(onions_button);
		onions_button.addActionListener(e -> {
    		addText(SimulatedItems.onions_description);
    		
    	});
		item_NtoP.add(Box.createRigidArea(new Dimension(5,0)));
		JButton redpeppers_button = new JButton(SimulatedItems.radishes_description);
		item_NtoP.add(redpeppers_button);
		redpeppers_button.addActionListener(e -> {
    		addText(SimulatedItems.radishes_description);
    		
    	});
		item_NtoP.add(Box.createRigidArea(new Dimension(5,0)));
		item_QtoS.add(Box.createRigidArea(new Dimension(5,0)));
		JButton radish_button = new JButton(SimulatedItems.radishes_description);
		item_QtoS.add(radish_button);
		radish_button.addActionListener(e -> {
    		addText(SimulatedItems.radishes_description);
    		
    	});
		item_QtoS.add(Box.createRigidArea(new Dimension(5,0)));
		JButton shallot_button = new JButton(SimulatedItems.shallots_description);
		item_QtoS.add(shallot_button);
		shallot_button.addActionListener(e -> {
    		addText(SimulatedItems.shallots_description);
    		
    	});
		item_QtoS.add(Box.createRigidArea(new Dimension(5,0)));
		JButton spinach_button = new JButton(SimulatedItems.spinach_description);
		item_QtoS.add(spinach_button);
		spinach_button.addActionListener(e -> {
    		addText(SimulatedItems.spinach_description);
    		
    	});
		item_QtoS.add(Box.createRigidArea(new Dimension(5,0)));
		item_TtoZ.add(Box.createRigidArea(new Dimension(5,0)));
		JButton tomato_button = new JButton(SimulatedItems.tomatoes_description);
		item_TtoZ.add(tomato_button);
		tomato_button.addActionListener(e -> {
    		addText(SimulatedItems.tomatoes_description);
    		
    	});
		item_TtoZ.add(Box.createRigidArea(new Dimension(5,0)));
		JButton yam_button = new JButton(SimulatedItems.yams_description);
		item_TtoZ.add(yam_button);
		yam_button.addActionListener(e -> {
    		addText(SimulatedItems.yams_description);
    		
    	});
		item_TtoZ.add(Box.createRigidArea(new Dimension(5,0)));
		JButton watermelon_button = new JButton(SimulatedItems.watermelon_description);
		item_TtoZ.add(watermelon_button);
		watermelon_button.addActionListener(e -> {
    		addText(SimulatedItems.watermelon_description);
    		
    	});

		item_TtoZ.add(Box.createRigidArea(new Dimension(5,0)));

		// Add Tabs to layout
		visualAlphaPanel.addTab("        A        ", item_A);
		visualAlphaPanel.addTab("      B - C      ", item_BtoC);
		visualAlphaPanel.addTab("      D - J      ", item_DtoJ);
		visualAlphaPanel.addTab("      K - M      ", item_KtoM);
		visualAlphaPanel.addTab("      N - P      ", item_NtoP);
		visualAlphaPanel.addTab("      Q - S      ", item_QtoS);
		visualAlphaPanel.addTab("      T - Z      ", item_TtoZ);
		
		
		// Enter/space/backspace/go back
		JButton back_button = new JButton(" GO BACK ");
		keyRowFourPanel.add(back_button);
		back_button.addActionListener(e -> {
    		mainGUI.getCardLayout().show(mainGUI.getMainPanel(), "addItem");
    	});

		keyRowFourPanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton enter_button = new JButton(" ENTER ");
		keyRowFourPanel.add(enter_button);
		enter_button.addActionListener(e -> {
			PLUCodedItem pitem = (PLUCodedItem) mainGUI.getItemFromDescription(searchText);
			logic.addPLUCodedProductController.addPLUCode(pitem.getPLUCode());
			mainGUI.getCardLayout().show(mainGUI.getMainPanel(), "addItem");
			mainGUI.getAddItemScreen().updateReceipt();
    	});
		
		// attendant button row
		keyRowSixPanel.add(Box.createRigidArea(new Dimension(0,50)));
		JButton attend_button = new JButton(" ATTENDANT ASSISTANCE ");
		keyRowSixPanel.add(attend_button);
		attend_button.addActionListener(e -> {
    		// TODO: Implement Attendant Assistance
    	});

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
	
	private void addText(String letter) {
		searchText = letter;
		textBar.setText(searchText);
	}
	
	private void deleteText() {
		if(!searchText.equals("ENTER ITEM NAME OR CODE")) {
			searchText = searchText.substring(0, searchText.length() - 1);
		}
		
		if(searchText.equals("")) {
			searchText = "ENTER ITEM NAME OR CODE";
			textBar.setText(searchText);
			
		}
		else {
			textBar.setText(searchText);
			
		}
		
	}

	public JPanel getPanel() {
		return keyboardScreenPanel;
	}
}

