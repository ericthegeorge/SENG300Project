package com.thelocalmarketplace.software.test.logic;

import com.thelocalmarketplace.software.controllers.ReceiptPrintingController;
import com.thelocalmarketplace.software.logic.CentralStationLogic;

public class ReceiptPrintingControllerStub extends ReceiptPrintingController {
    public ReceiptPrintingControllerStub(CentralStationLogic logic) throws NullPointerException {
		super(logic);
		// TODO Auto-generated constructor stub
	}

	@Override
    public Boolean getLowInk() {
        // Simulate condition low ink
        return true;
    }
	
	@Override
    public Boolean getLowPaper() {
        // Simulate condition low paper
        return true;
    }
}