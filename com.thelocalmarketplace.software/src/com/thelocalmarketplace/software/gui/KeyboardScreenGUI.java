package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import java.awt.*;


public class KeyboardScreenGUI {
	
	// Variables for the screen
	JFrame keyboardScreenFrame;
	JPanel keyboardScreenPanel;
	
	
	// Constructor for the screen
	public KeyboardScreenGUI() {
		JFrame keyboardScreenFrame = new JFrame("The LocalMarketplace Self-Checkout Station");
		JPanel keyboardScreenPanel = new JPanel();
		
		keyboardScreenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addWidgets();
		
		// TODO: Build out the rest of the screen. 
		
	}
	
	// Adds all the buttons
	private void addWidgets() {
		
		
	}
	
	// Just for testing screen.
	public static void main(String[] args) {
		KeyboardScreenGUI keyboardGUI = new KeyboardScreenGUI();
	}
}

