package com.thelocalmarketplace.software.gui;

import javax.swing.*;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.CentralStationLogic.PaymentMethods;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

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


public class PaymentScreenGUI {
    private JFrame paymentPageFrame;
    private JPanel paymentPagePanel;
    private JButton cashButton;
    private JButton creditButton;
    private JButton debitButton;
    private JButton notifyAttendantButton;
    private JButton backToCartButton;
    private JButton finishCheckoutButton;
    private JLabel totalPriceLabel;
    private JLabel itemsInCartLabel;
    private JLabel selectPaymentLabel;
	private JLabel receiptLabel;
	private String receipt = "This is a receipt";
    private DefaultListModel<String> cartItemModel = new DefaultListModel<>();
    private JList<String> cartItemList;
    private CentralStationLogic logic;
    private MainGUI mainGUI;
    private ArrayList<String> receiptListNames = new ArrayList<>();
	private ArrayList<String> receiptListPrices = new ArrayList<>();
    private JPanel listPanel = new JPanel(new BorderLayout());
    JPanel rightPanel = new JPanel();
    private float totalPrice = 0;
    private JTextArea totalPriceText;

	public PaymentScreenGUI(MainGUI m, CentralStationLogic l) { 
    	mainGUI = m;
    	logic = l;
        paymentPageFrame = new JFrame("The LocalMarketplace Self-Checkout Station");
        paymentPagePanel = new JPanel();

        addWidgets();

        paymentPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        paymentPageFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        paymentPageFrame.setContentPane(paymentPagePanel);
    }

    private void addWidgets() {
    	

        paymentPagePanel.setLayout(new BorderLayout());

        // top panel
        JPanel topPanel = new JPanel();
        selectPaymentLabel = new JLabel("Select Payment Method"); 
        selectPaymentLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(selectPaymentLabel);
        paymentPagePanel.add(topPanel, BorderLayout.NORTH);


        // center panel (containing left and right panels)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 2));

        // left panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(5, 1, 0, 20));

        // buttons
        cashButton = new JButton("Cash");
        creditButton = new JButton("Credit");
        debitButton = new JButton("Debit");
        notifyAttendantButton = new JButton("Notify Attendant");
        backToCartButton = new JButton("Back to cart");

        buttonsPanel.add(cashButton);
        buttonsPanel.add(creditButton);
        buttonsPanel.add(debitButton);
        buttonsPanel.add(notifyAttendantButton);
        buttonsPanel.add(backToCartButton);

        centerPanel.add(buttonsPanel);
        

        
        // right panel
        
        rightPanel.setLayout(new BorderLayout());

        finishCheckoutButton = new JButton("Finish Checkout");
        finishCheckoutButton.setFont(new Font("Arial", Font.BOLD, 15));
        finishCheckoutButton.setPreferredSize(new Dimension(150, 50));
        itemsInCartLabel = new JLabel("Items in cart:");
        itemsInCartLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        listPanel.add(itemsInCartLabel, BorderLayout.NORTH);
        
        
        
        

        rightPanel.add(listPanel, BorderLayout.CENTER);
        rightPanel.add(finishCheckoutButton, BorderLayout.SOUTH);

        centerPanel.add(rightPanel);

        paymentPagePanel.add(centerPanel, BorderLayout.CENTER);
        
        cashButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	getPaymentPageFrame().dispose();
                logic.selectPaymentMethod(PaymentMethods.CASH);
            	mainGUI.getCardLayout().show(mainGUI.getMainPanel(), "cash");
            }
        });
        
        debitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	getPaymentPageFrame().dispose();
                logic.selectPaymentMethod(PaymentMethods.DEBIT);
            	mainGUI.getCardLayout().show(mainGUI.getMainPanel(), "debit");
            }
        });
        
        creditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	getPaymentPageFrame().dispose();
                logic.selectPaymentMethod(PaymentMethods.CREDIT);
            	mainGUI.getCardLayout().show(mainGUI.getMainPanel(), "credit");
            }
        });
        
        backToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	getPaymentPageFrame().dispose();
            	mainGUI.getCardLayout().show(mainGUI.getMainPanel(), "addItem");
            }
        });
        
        // notify attendant
        notifyAttendantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO attendantScreen.dosomething()
            }
        });
        
        finishCheckoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(logic.cartLogic.getBalanceOwed().intValue() == 0) {
                	BigDecimal missed = logic.coinPaymentController.getMissed().add(logic.cashPaymentController.getMissed());
                	logic.receiptPrintingController.handlePrintReceipt(missed);
                    
                	// TODO: Implement Receipt popup
                	JFrame receiptWindow = new JFrame("Customer Receipt");
                    receiptWindow.setSize(400, 300); // Increased window size
                    receiptWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                	
                    JPanel receiptPanel = new JPanel();
                    receiptPanel.setLayout(new BoxLayout(receiptPanel, BoxLayout.Y_AXIS));
                	receiptPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
                	receiptPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
                    
                    JLabel label = new JLabel(receipt);
                    label.setAlignmentX(Component.CENTER_ALIGNMENT);
                    label.setFont(new Font("Arial", Font.BOLD, 24)); // Increased font size
                   
                    String receipt = logic.receiptPrintingController.createPaymentRecord(new BigDecimal(0));
                    String[] splitReceipt = receipt.split("\n");
                    for(String line : splitReceipt) {
                    	label = new JLabel(line);
                        receiptPanel.add(label);
                    }

                    receiptWindow.add(receiptPanel);
                    receiptWindow.setVisible(true);
                	
                	paymentPageFrame.dispose();
                    mainGUI.getCardLayout().show(mainGUI.getMainPanel(), "completion");
                    
            		new java.util.Timer().schedule( 
            		        new java.util.TimerTask() {
            		            @Override
            		            public void run() {
            		            	MainGUI.restartGUI();
            		            	mainGUI.getMainFrame().dispose();
            		            	mainGUI.getAttendantFrame().dispose();
            		            }
            		        }, 
            		        7000 
            		);
            	}

            	
            }
        });

        totalPriceText = new JTextArea("");
        getRightPanel().add(totalPriceText, BorderLayout.NORTH);
    }
    
    public void setReceiptText(String receiptText) {
    	receipt = receiptText;
    	
    }
    
    
    
    private void notifyAttendant() {
        JOptionPane.showMessageDialog(paymentPageFrame, "Attendant notified. Please wait for assistance.");
    }

	public JButton getCashButton() {
		return cashButton;
	}
	public JButton getCreditButton() {
		return creditButton;
	}
	public JButton getDebitButton() {
		return debitButton;
	}	
	public JButton getNotifyAttendantButton() {
		return notifyAttendantButton;
	}
	public JFrame getPaymentPageFrame() {
		return paymentPageFrame;
	}
	public JPanel getPanel() {
		return paymentPagePanel;
	}

    public JList<String> getCartItemList() {
        return cartItemList;
    }

    // Getter for totalPrice
    public float getTotalPrice() {
        return this.totalPrice;
    }

    // Setter for totalPrice
    public void setTotalPrice(float newTotalPrice) {
        this.totalPrice = newTotalPrice;
    }


    // Setter for cartItemList
    public void setCartItemList(ArrayList<String> names, ArrayList<String> prices) {
        

        // Ensure both lists have the same size
        int minSize = Math.min(names.size(), prices.size());
        DefaultListModel<String> newCartModel = new DefaultListModel<String>();

        for (int i = 0; i < minSize; i++) {
            String concatenatedItem = names.get(i) + " " + prices.get(i);
            newCartModel.addElement(concatenatedItem);
        }

        setCartItemModel(newCartModel);


    }
    
    

    // Getter method for cartItemModel
    public DefaultListModel<String> getCartItemModel() {
        return this.cartItemModel;
    }

    // Setter method for cartItemModel
    public void setCartItemModel(DefaultListModel<String> newModel) {
        cartItemModel = newModel;
        cartItemList = new JList<>(cartItemModel);
    }

    // Setter for updating receiptListNames with DefaultListModel
    public void updateReceiptListNames(DefaultListModel<String> nameModel) {
        getReceiptListNames().clear(); // Clear existing names
        for (int i = 0; i < nameModel.getSize(); i++) {
            this.receiptListNames.add(nameModel.getElementAt(i));
        }
		System.out.println("NAMES MAINGUI: ");
		System.out.println(this.receiptListNames);
    }

    // Setter for updating receiptListPrices with DefaultListModel
    public void updateReceiptListPrices(DefaultListModel<String> priceModel) {
        getReceiptListPrices().clear(); // Clear existing prices
        for (int i = 0; i < priceModel.getSize(); i++) {
            this.receiptListPrices.add(priceModel.getElementAt(i));
        }
		System.out.println("PRICES MAINGUI: ");
		System.out.println(this.receiptListPrices);
    }

    // Getter methods to access the lists
    public ArrayList<String> getReceiptListNames() {
        return this.receiptListNames;
    }

    public ArrayList<String> getReceiptListPrices() {
        return this.receiptListPrices;
    }

    // Setter for updating cartItemList
    public void updateCartItemList(DefaultListModel<String> newModel) {
        getCartItemList().setModel(newModel); // set the cartItemList to the new model

    }

    // Getter for listPanel
    public JPanel getListPanel() {
        return this.listPanel;
    }

    // Setter for listPanel
    public void setListPanel(JPanel newPanel) {
        listPanel = newPanel;
    }

    public void updateCartScrollPanel(){
        JList<String> cartListObjt = getCartItemList();
        cartListObjt.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        cartListObjt.setLayoutOrientation(JList.VERTICAL_WRAP); 
        cartListObjt.setVisibleRowCount(-1);

        cartListObjt.setFont(new Font("Arial", Font.PLAIN, 20));
        
        JScrollPane cartScrollPane = new JScrollPane(cartListObjt);
        getListPanel().add(cartScrollPane, BorderLayout.CENTER);

    }

    public void updateTotalPriceText(String price) {
        String totalPriceString = "Total Price ($): ";
        totalPriceText.setEditable(false);
        totalPriceText.setFont(new Font("Arial", Font.BOLD, 20));
        totalPriceText.setText(totalPriceString + price);

    }

    // Getter for rightPanel
    public JPanel getRightPanel() {
        return rightPanel;
    }

    // Setter for rightPanel
    public void setRightPanel(JPanel newPanel) {
        rightPanel = newPanel;
    }


    public JTextArea getTotalPriceText() {
		return totalPriceText;
	}

	public void setTotalPriceText(JTextArea totalPriceText) {
		this.totalPriceText = totalPriceText;
	}
	
	private void centerWindowOnFrame(JFrame window, JFrame frame) {
        // Center the window on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = frame.getX() + (frame.getWidth() - window.getWidth()) / 2;
        int y = frame.getY() + (frame.getHeight() - window.getHeight()) / 2;
        window.setLocation(x, y);
    }
	

}
