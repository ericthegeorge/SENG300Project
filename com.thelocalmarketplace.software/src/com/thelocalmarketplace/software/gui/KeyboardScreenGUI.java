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

public class KeyboardScreenGUI {
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
	public KeyboardScreenGUI(MainGUI m, CentralStationLogic l) {
		mainGUI = m;
		logic = l;
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
    		// TODO: Implement visual catalog
    		
    	});
		item_A.add(Box.createRigidArea(new Dimension(5,0)));
		JButton avocado_button = new JButton(SimulatedItems.avocado_description);
		item_A.add(avocado_button);
		avocado_button.addActionListener(e -> {
    		// TODO: Implement visual catalog
    		
    	});
		item_A.add(Box.createRigidArea(new Dimension(5,0)));
		JButton asparagus_button = new JButton(SimulatedItems.asparagus_description);
		item_A.add(asparagus_button);
		asparagus_button.addActionListener(e -> {
    		// TODO: Implement visual catalog
    		
    	});
		item_A.add(Box.createRigidArea(new Dimension(5,0)));
		item_BtoC.add(Box.createRigidArea(new Dimension(5,0)));
		JButton blueberries_button = new JButton(SimulatedItems.blueberries_description);
		item_BtoC.add(blueberries_button);
		blueberries_button.addActionListener(e -> {
    		// TODO: Implement visual catalog
    		
    	});
		item_BtoC.add(Box.createRigidArea(new Dimension(5,0)));
		JButton beets_button = new JButton(SimulatedItems.beets_description);
		item_BtoC.add(beets_button);
		beets_button.addActionListener(e -> {
    		// TODO: Implement visual catalog
    		
    	});
		item_BtoC.add(Box.createRigidArea(new Dimension(5,0)));
		JButton celery_button = new JButton(SimulatedItems.celery_description);
		item_BtoC.add(celery_button);
		celery_button.addActionListener(e -> {
    		// TODO: Implement visual catalog
    		
    	});
		item_BtoC.add(Box.createRigidArea(new Dimension(5,0)));
		item_DtoJ.add(Box.createRigidArea(new Dimension(5,0)));
		JButton endive_button = new JButton(SimulatedItems.endive_description);
		item_DtoJ.add(endive_button);
		endive_button.addActionListener(e -> {
    		// TODO: Implement visual catalog
    		
    	});
		item_DtoJ.add(Box.createRigidArea(new Dimension(5,0)));
		JButton grapes_button = new JButton(SimulatedItems.grapes_description);
		item_DtoJ.add(grapes_button);
		grapes_button.addActionListener(e -> {
    		// TODO: Implement visual catalog
    		
    	});
		item_DtoJ.add(Box.createRigidArea(new Dimension(5,0)));
		JButton jicama_button = new JButton(SimulatedItems.jicama_description);
		item_DtoJ.add(jicama_button);
		jicama_button.addActionListener(e -> {
    		// TODO: Implement visual catalog
    		
    	});
		item_DtoJ.add(Box.createRigidArea(new Dimension(5,0)));
		item_KtoM.add(Box.createRigidArea(new Dimension(5,0)));
		JButton kale_button = new JButton(SimulatedItems.kale_description);
		item_KtoM.add(kale_button);
		kale_button.addActionListener(e -> {
    		// TODO: Implement visual catalog
    		
    	});
		item_KtoM.add(Box.createRigidArea(new Dimension(5,0)));
		JButton lettuce_button = new JButton(SimulatedItems.lettuce_description);
		item_KtoM.add(lettuce_button);
		lettuce_button.addActionListener(e -> {
    		// TODO: Implement visual catalog
    		
    	});
		item_KtoM.add(Box.createRigidArea(new Dimension(5,0)));
		JButton mango_button = new JButton(SimulatedItems.mangos_description);
		item_KtoM.add(mango_button);
		mango_button.addActionListener(e -> {
    		// TODO: Implement visual catalog
    		
    	});
		item_KtoM.add(Box.createRigidArea(new Dimension(5,0)));
		item_NtoP.add(Box.createRigidArea(new Dimension(5,0)));
		JButton grnpeppers_button = new JButton(SimulatedItems.greenpeppers_description);
		item_NtoP.add(grnpeppers_button);
		grnpeppers_button.addActionListener(e -> {
    		// TODO: Implement visual catalog
    		
    	});
		item_NtoP.add(Box.createRigidArea(new Dimension(5,0)));
		JButton onions_button = new JButton(SimulatedItems.onions_description);
		item_NtoP.add(onions_button);
		onions_button.addActionListener(e -> {
    		// TODO: Implement visual catalog
    		
    	});
		item_NtoP.add(Box.createRigidArea(new Dimension(5,0)));
		JButton redpeppers_button = new JButton(SimulatedItems.radishes_description);
		item_NtoP.add(redpeppers_button);
		redpeppers_button.addActionListener(e -> {
    		// TODO: Implement visual catalog
    		
    	});
		item_NtoP.add(Box.createRigidArea(new Dimension(5,0)));
		item_QtoS.add(Box.createRigidArea(new Dimension(5,0)));
		JButton radish_button = new JButton(SimulatedItems.radishes_description);
		item_QtoS.add(radish_button);
		radish_button.addActionListener(e -> {
    		// TODO: Implement visual catalog
    		
    	});
		item_QtoS.add(Box.createRigidArea(new Dimension(5,0)));
		JButton shallot_button = new JButton(SimulatedItems.shallots_description);
		item_QtoS.add(shallot_button);
		shallot_button.addActionListener(e -> {
    		// TODO: Implement visual catalog
    		
    	});
		item_QtoS.add(Box.createRigidArea(new Dimension(5,0)));
		JButton spinach_button = new JButton(SimulatedItems.spinach_description);
		item_QtoS.add(spinach_button);
		spinach_button.addActionListener(e -> {
			PLUCodedItem pitem = (PLUCodedItem) mainGUI.getItemFromDescription(spinach_button.getText());
			logic.addPLUCodedProductController.addPLUCode(pitem.getPLUCode());
    		// TODO: Implement visual catalog
    		
    	});
		item_QtoS.add(Box.createRigidArea(new Dimension(5,0)));
		item_TtoZ.add(Box.createRigidArea(new Dimension(5,0)));
		JButton tomato_button = new JButton(SimulatedItems.tomatoes_description);
		item_TtoZ.add(tomato_button);
		tomato_button.addActionListener(e -> {
    		// TODO: Implement visual catalog
    		
    	});
		item_TtoZ.add(Box.createRigidArea(new Dimension(5,0)));
		JButton yam_button = new JButton(SimulatedItems.yams_description);
		item_TtoZ.add(yam_button);
		yam_button.addActionListener(e -> {
    		// TODO: Implement visual catalog
    		
    	});
		item_TtoZ.add(Box.createRigidArea(new Dimension(5,0)));
		JButton watermelon_button = new JButton(SimulatedItems.watermelon_description);
		item_TtoZ.add(watermelon_button);
		watermelon_button.addActionListener(e -> {
    		// TODO: Implement visual catalog
    		
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
		
		// Add Buttons for keyboard
		// Letters for first row
		JButton q_button = new JButton(" Q ");
		keyRowOnePanel.add(q_button);
		q_button.addActionListener(e -> {
    		addText("Q");
    		
    	});
		keyRowOnePanel.add(Box.createRigidArea(new Dimension(5,50)));
		JButton w_button = new JButton(" W ");
		keyRowOnePanel.add(w_button);
		w_button.addActionListener(e -> {
    		addText("W");
    		
    	});
		keyRowOnePanel.add(Box.createRigidArea(new Dimension(5,50)));
		JButton e_button = new JButton(" E ");
		keyRowOnePanel.add(e_button);
		e_button.addActionListener(e -> {
    		addText("E");
    		
    	});
		
		keyRowOnePanel.add(Box.createRigidArea(new Dimension(5,50)));
		JButton r_button = new JButton(" R ");
		keyRowOnePanel.add(r_button);
		r_button.addActionListener(e -> {
    		addText("R");
    		
    	});
		keyRowOnePanel.add(Box.createRigidArea(new Dimension(5,50)));
		JButton t_button = new JButton(" T ");
		keyRowOnePanel.add(t_button);
		t_button.addActionListener(e -> {
    		addText("T");
    		
    	});
		keyRowOnePanel.add(Box.createRigidArea(new Dimension(5,50)));
		JButton y_button = new JButton(" Y ");
		keyRowOnePanel.add(y_button);
		y_button.addActionListener(e -> {
    		addText("Y");
    		
    	});
		keyRowOnePanel.add(Box.createRigidArea(new Dimension(5,50)));
		JButton u_button = new JButton(" U ");
		keyRowOnePanel.add(u_button);
		u_button.addActionListener(e -> {
    		addText("U");
    		
    	});
		keyRowOnePanel.add(Box.createRigidArea(new Dimension(5,50)));
		JButton i_button = new JButton(" I ");
		keyRowOnePanel.add(i_button);
		i_button.addActionListener(e -> {
    		addText("I");
    		
    	});
		keyRowOnePanel.add(Box.createRigidArea(new Dimension(5,50)));
		JButton o_button = new JButton(" O ");
		keyRowOnePanel.add(o_button);
		o_button.addActionListener(e -> {
    		addText("O");
    		
    	});
		keyRowOnePanel.add(Box.createRigidArea(new Dimension(5,50)));
		JButton p_button = new JButton(" P ");
		keyRowOnePanel.add(p_button);
		p_button.addActionListener(e -> {
    		addText("P");
    		
    	});
		keyRowOnePanel.add(Box.createRigidArea(new Dimension(5,50)));
		JButton under_button = new JButton(" _ ");
		keyRowOnePanel.add(under_button);
		under_button.addActionListener(e -> {
    		addText("_");
    		
    	});
		
		// Letters for second row
		JButton a_button = new JButton(" A ");
		keyRowTwoPanel.add(a_button);
		a_button.addActionListener(e -> {
    		addText("A");
    		
    	});
		keyRowTwoPanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton s_button = new JButton(" S ");
		keyRowTwoPanel.add(s_button);
		s_button.addActionListener(e -> {
    		addText("S");
    		
    	});
		keyRowTwoPanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton d_button = new JButton(" D ");
		keyRowTwoPanel.add(d_button);
		d_button.addActionListener(e -> {
    		addText("D");
    		
    	});
		keyRowTwoPanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton f_button = new JButton(" F ");
		keyRowTwoPanel.add(f_button);
		f_button.addActionListener(e -> {
    		addText("F");
    		
    	});
		keyRowTwoPanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton g_button = new JButton(" G ");
		keyRowTwoPanel.add(g_button);
		g_button.addActionListener(e -> {
    		addText("G");
    		
    	});
		keyRowTwoPanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton h_button = new JButton(" H ");
		keyRowTwoPanel.add(h_button);
		h_button.addActionListener(e -> {
    		addText("H");
    		
    	});
		keyRowTwoPanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton j_button = new JButton(" J ");
		keyRowTwoPanel.add(j_button);
		j_button.addActionListener(e -> {
    		addText("J");
    		
    	});
		keyRowTwoPanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton k_button = new JButton(" K ");
		keyRowTwoPanel.add(k_button);
		k_button.addActionListener(e -> {
    		addText("K");
    		
    	});
		keyRowTwoPanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton l_button = new JButton(" L ");
		keyRowTwoPanel.add(l_button);
		l_button.addActionListener(e -> {
    		addText("L");
    		
    	});
		keyRowTwoPanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton at_button = new JButton(" @ ");
		keyRowTwoPanel.add(at_button);
		at_button.addActionListener(e -> {
    		addText("@");
    		
    	});
		
		// Letters for third row
		JButton z_button = new JButton(" Z ");
		keyRowThreePanel.add(z_button);
		z_button.addActionListener(e -> {
    		addText("Z");
    		
    	});
		keyRowThreePanel.add(Box.createRigidArea(new Dimension(5,50)));
		JButton x_button = new JButton(" X ");
		keyRowThreePanel.add(x_button);
		x_button.addActionListener(e -> {
    		addText("X");
    		
    	});
		keyRowThreePanel.add(Box.createRigidArea(new Dimension(5,50)));
		JButton c_button = new JButton(" C ");
		keyRowThreePanel.add(c_button);
		c_button.addActionListener(e -> {
    		addText("C");
    		
    	});
		keyRowThreePanel.add(Box.createRigidArea(new Dimension(5,50)));
		JButton v_button = new JButton(" V ");
		keyRowThreePanel.add(v_button);
		v_button.addActionListener(e -> {
    		addText("V");
    		
    	});
		keyRowThreePanel.add(Box.createRigidArea(new Dimension(5,50)));
		JButton b_button = new JButton(" B ");
		keyRowThreePanel.add(b_button);
		b_button.addActionListener(e -> {
    		addText("B");
    		
    	});
		keyRowThreePanel.add(Box.createRigidArea(new Dimension(5,50)));
		JButton n_button = new JButton(" N ");
		keyRowThreePanel.add(n_button);
		n_button.addActionListener(e -> {
    		addText("N");
    		
    	});
		keyRowThreePanel.add(Box.createRigidArea(new Dimension(5,50)));
		JButton m_button = new JButton(" M ");
		keyRowThreePanel.add(m_button);
		m_button.addActionListener(e -> {
    		addText("M");
    		
    	});
		keyRowThreePanel.add(Box.createRigidArea(new Dimension(5,50)));
		JButton comma_button = new JButton(" , ");
		keyRowThreePanel.add(comma_button);
		comma_button.addActionListener(e -> {
    		addText(",");
    		
    	});
		keyRowThreePanel.add(Box.createRigidArea(new Dimension(5,50)));
		JButton period_button = new JButton(" . ");
		keyRowThreePanel.add(period_button);
		period_button.addActionListener(e -> {
    		addText(".");
    		
    	});
		keyRowThreePanel.add(Box.createRigidArea(new Dimension(5,50)));
		JButton dash_button = new JButton(" - ");
		keyRowThreePanel.add(dash_button);
		dash_button.addActionListener(e -> {
    		addText("-");
    		
    	});
		
		// Enter/space/backspace/go back
		JButton back_button = new JButton(" GO BACK ");
		keyRowFourPanel.add(back_button);
		back_button.addActionListener(e -> {
    		// TODO: Implement Go Back Page action
    		
    	});
		keyRowFourPanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton space_button = new JButton(" SPACE ");
		keyRowFourPanel.add(space_button);
		space_button.addActionListener(e -> {
    		addText(" ");
    		
    	});
		keyRowFourPanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton delete_button = new JButton(" BACKSPACE ");
		keyRowFourPanel.add(delete_button);
		delete_button.addActionListener(e -> {
    		deleteText();
    		
    	});
		keyRowFourPanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton enter_button = new JButton(" ENTER ");
		keyRowFourPanel.add(enter_button);
		enter_button.addActionListener(e -> {
			// TODO: Implement enter action
    		
    	});
		
		// Number row
		JButton one_button = new JButton(" 1 ");
		keyRowFivePanel.add(one_button);
		one_button.addActionListener(e -> {
    		addText("1");
    		
    	});
		keyRowFivePanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton two_button = new JButton(" 2 ");
		keyRowFivePanel.add(two_button);
		two_button.addActionListener(e -> {
    		addText("2");
    		
    	});
		keyRowFivePanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton three_button = new JButton(" 3 ");
		keyRowFivePanel.add(three_button);
		three_button.addActionListener(e -> {
    		addText("3");
    		
    	});
		keyRowFivePanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton four_button = new JButton(" 4 ");
		keyRowFivePanel.add(four_button);
		four_button.addActionListener(e -> {
    		addText("4");
    		
    	});
		keyRowFivePanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton five_button = new JButton(" 5 ");
		keyRowFivePanel.add(five_button);
		five_button.addActionListener(e -> {
    		addText("5");
    		
    	});
		keyRowFivePanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton six_button = new JButton(" 6 ");
		keyRowFivePanel.add(six_button);
		six_button.addActionListener(e -> {
    		addText("6");
    		
    	});
		keyRowFivePanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton seven_button = new JButton(" 7 ");
		keyRowFivePanel.add(seven_button);
		seven_button.addActionListener(e -> {
    		addText("7");
    		
    	});
		keyRowFivePanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton eight_button = new JButton(" 8 ");
		keyRowFivePanel.add(eight_button);
		eight_button.addActionListener(e -> {
    		addText("8");
    		
    	});
		keyRowFivePanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton nine_button = new JButton(" 9 ");
		keyRowFivePanel.add(nine_button);
		nine_button.addActionListener(e -> {
    		addText("9");
    		
    	});
		keyRowFivePanel.add(Box.createRigidArea(new Dimension(5,0)));
		JButton zero_button = new JButton(" 0 ");
		keyRowFivePanel.add(zero_button);
		zero_button.addActionListener(e -> {
    		addText("0");
    		
    	});
		
		// attendant button row
		keyRowSixPanel.add(Box.createRigidArea(new Dimension(0,50)));
		JButton attend_button = new JButton(" ATTENDANT ASSISTANCE ");
		keyRowSixPanel.add(attend_button);
		attend_button.addActionListener(e -> {
    		// TODO: Implement Attendant Assistance
			
    		
    	});
		//keyRowSixPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		keyRowSixPanel.add(Box.createRigidArea(new Dimension(98,50)));
		JButton bagItem_button = new JButton(" BAG ITEM ");
		keyRowSixPanel.add(bagItem_button);
		bagItem_button.addActionListener(e -> {
    		// TODO: Implement Attendant Assistance
    		
    	});
		//keyRowSixPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
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
		if(searchText.equals("ENTER ITEM NAME OR CODE")) {
			searchText = letter;
    		textBar.setText(searchText);
		}
		else {
			searchText += letter;
			textBar.setText(searchText);
		}	
		
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

