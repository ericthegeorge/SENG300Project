package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import com.jjjwelectronics.Item;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;

import java.awt.*;
import java.awt.Taskbar.State;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.Map;

/**
 * GUI for the Attendant Station
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

public class AttendantStationGUI {
	public CentralStationLogic logic;
	MainGUI mainGUI;
	JPanel mainPanel;
	private JFrame mainFrame;
    private String[] data;
    private DefaultListModel searchList = new DefaultListModel();
    StationObject[] stationObjects;

    private static JComboBox<StationObject> comboBox;

    
    
    // Fake class to represent Station objects
    class StationObject {
        private int stationNumber;
        private Map<Character, Color> circleColors; // Map to store colors for circles\
        private JPanel square;


        private boolean isEnabled;

        private double weight;

        public StationObject(int stationNumber) {
            this.stationNumber = stationNumber;
            this.circleColors = new HashMap<>();
            initializeCircleColors();
        }

        private void initializeCircleColors() {
            // Initialize default colors for circles
            circleColors.put('I', Color.YELLOW);
            circleColors.put('P', Color.YELLOW);
            circleColors.put('C', Color.YELLOW);
            circleColors.put('B', Color.YELLOW);
            circleColors.put('S', Color.YELLOW);
            circleColors.put('H', Color.YELLOW);
        }

        public int getStationNumber() {
            return stationNumber;
        }

        public Color getCircleColor(char label) {
            return circleColors.get(label);
        }

        public void setCircleColor(char label, Color color) {
            circleColors.put(label, color);
        }

        public void setCircleColorYellow(char label) {
            setCircleColor(label, Color.YELLOW);
        }

        public void setCircleColorRed(char label) {
            setCircleColor(label, Color.RED);
        }

        public void setCircleColorGreen(char label) {
            setCircleColor(label, Color.GREEN);
        }

     
        @Override
        public String toString() {
            return "Station #" + stationNumber;
        }

        // Add a getter and setter for isEnabled
        public boolean isEnabled() {
            return isEnabled;
        }

        public void setEnabled(boolean enabled) {
            isEnabled = enabled;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

		public JPanel getSquare() {
			return square;
		}

    }

    private void updateUIForSelectedStation(StationObject selectedStation) {

        // Change the colors based on any condition. Here I have them all set to red.
        selectedStation.setCircleColorRed('I');
        selectedStation.setCircleColorRed('P');
        selectedStation.setCircleColorRed('C');
        selectedStation.setCircleColorRed('B');
        selectedStation.setCircleColorRed('S');
        selectedStation.setCircleColorRed('H');

        SwingUtilities.invokeLater(() -> comboBox.repaint());
    }
    
    private void createFrame(StationObject[] stationObjects) {

        JFrame frame = new JFrame("Attendant Station");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Create a JLabel for the title
        JLabel titleLabel = new JLabel("Attendant Station");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 70)); // Customize font and size
        titleLabel.setHorizontalAlignment(JLabel.CENTER); // Center align the title

        // Add the title label to the top of the frame
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.NORTH);

        // Create panels for the left and right sections with FlowLayout
        JPanel leftPanel = new JPanel(new GridBagLayout());
        // Set margin to reduce spacing on the left
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        // Create a panel for the button and dropdown menu on the right
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        comboBox = new JComboBox<>(); // Use the class variable, not local variable
        // Create a JComboBox (button with a dropdown menu) for the right panel
        updateComboBoxOptions(stationObjects); // Initialize JComboBox options
        comboBox.setEditable(false); // Make the combo box not editable

        // Set the preferred size of the JComboBox
        Dimension comboBoxSize = new Dimension(200, 10);
        comboBox.setPreferredSize(comboBoxSize);

        // Set a custom font for the JComboBox text with a smaller size
        Font comboBoxFont = new Font("Arial", Font.PLAIN, 40); // Change font and size as needed
        comboBox.setFont(comboBoxFont);

        // Add vertical glue to center the JComboBox vertically
        buttonPanel.add(Box.createVerticalGlue());

        // Add padding around the JComboBox
        int padding = 10;
        comboBox.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        // Add the JComboBox to the buttonPanel
        buttonPanel.add(comboBox);

        // Add a smaller rigid area to reduce space
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        String[] buttonLabels = {
                "Add Item by Text Search",
                "Maintain Ink",
                "Maintain Paper",
                "Maintain Coins",
                "Maintain Banknotes"						
        };

        // Set fixed size for all buttons
        Dimension buttonSize = new Dimension(500, 100);

        // Add buttons below the JComboBox
        for (String label : buttonLabels) {
            JButton button = new JButton(label);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle button click
                    handleButtonClick(label, frame);
                }
            });

            // Set a custom font for the button text
            Font buttonFont = new Font("Arial", Font.PLAIN, 30); // Change font and size as needed
            button.setFont(buttonFont);

            button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button horizontally
            button.setMaximumSize(buttonSize);
            button.setPreferredSize(buttonSize);
            button.setMinimumSize(buttonSize);

            buttonPanel.add(button);
        }

        // Add buttonPanel to rightPanel with vertical glue for centering
        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(buttonPanel);
        rightPanel.add(Box.createVerticalGlue());

        // Create a JSplitPane with the left and right panels
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);

        // Set the resize weight to 0.7 to make the left panel larger
        splitPane.setResizeWeight(0.96);

        // Set the divider to be unchangeable
        splitPane.setEnabled(false);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        // Set layout manager for the frame content pane
        frame.setLayout(new BorderLayout());

        // Add the split pane to the center of the frame
        frame.add(mainPanel, BorderLayout.CENTER);

        // Set frame properties
        frame.setSize(800, 600);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);

        // Initial creation of squares (hardcoded)
        createSquares(leftPanel, stationObjects); // Pass the number of squares as a parameter
    }

    // Method to update JComboBox options based on the given number of squares
    private static void updateComboBoxOptions(StationObject[] stationObjects) {
        comboBox.setModel(new DefaultComboBoxModel<>(stationObjects));
    }


    // Method to create squares based on the given array of StationObjects
    private void createSquares(JPanel panel, StationObject[] stationObjects) {
        panel.removeAll(); // Clear existing components

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); // Increased padding between components

        int rows = (int) Math.ceil((double) stationObjects.length / 4);

        for (int i = 0; i < stationObjects.length; i++) {
            stationObjects[i].square = new JPanel(new BorderLayout());
            stationObjects[i].square.setPreferredSize(new Dimension(260, 220)); // Increased size

            // Set the default background color of the square to blue
            stationObjects[i].square.setBackground(Color.BLUE);

            // Add a switch to enable/disable the station
            JToggleButton enableSwitch = new JToggleButton("Enable Station");
            enableSwitch.setSelected(stationObjects[i].isEnabled());
            int finalI1 = i;
            enableSwitch.addActionListener(e -> {
                stationObjects[finalI1].setEnabled(enableSwitch.isSelected());
            });

            // Add a "Solve" button below the square
            JButton solveButton = new JButton("Solve Weight");
            solveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    solveWeightDiscrepancy();
                }
            });

            Font smallFont = new Font(solveButton.getFont().getName(), Font.BOLD, 11); // You can adjust the size (12 in this case)
            solveButton.setFont(smallFont);

            stationObjects[i].square.add(solveButton, BorderLayout.EAST);

            // Add the enableSwitch to the top of the square
            stationObjects[i].square.add(enableSwitch, BorderLayout.NORTH);

            // Center the square in the panel using GridBagConstraints
            gbc.gridx = i % 4;
            gbc.gridy = i / 4;

            // Add labeled lights under the square
            JPanel lightsPanel = new JPanel();
            GridLayout lightsLayout = new GridLayout(1, 4);
            lightsLayout.setHgap(10); // Set horizontal gap between lights
            lightsPanel.setLayout(lightsLayout);

            for (char label : new char[]{'I', 'P', 'C', 'B', 'S', 'H'}) {
                int finalI = i;
                JPanel light = new JPanel(new GridBagLayout()) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2d = (Graphics2D) g.create();
                        int diameter = Math.min(getWidth() - 4, getHeight() - 4); // Adjusted diameter
                        int x = (getWidth() - diameter) / 2;
                        int y = (getHeight() - diameter) / 2;
                        Ellipse2D.Double ellipse = new Ellipse2D.Double(x, y, diameter, diameter);

                        // Set the color of lights based on the color stored in the StationObject
                        g2d.setColor(stationObjects[finalI].getCircleColor(label));

                        g2d.fill(ellipse);
                        g2d.dispose();
                    }

                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(60, 30); // Increased size of lights
                    }
                };

                JLabel lightLabel = new JLabel(String.valueOf(label));
                lightLabel.setForeground(Color.BLACK); // Set text color to black

                light.add(lightLabel, new GridBagConstraints());

                lightsPanel.add(light);
            }

            stationObjects[i].square.add(lightsPanel, BorderLayout.SOUTH);

            // Add a label with the text "Station #" and the current number
            JLabel label = new JLabel("Station #" + stationObjects[i].getStationNumber());
            label.setForeground(Color.WHITE); // Set text color to white
            label.setHorizontalAlignment(JLabel.CENTER); // Center align the text
            stationObjects[i].square.add(label, BorderLayout.CENTER);

            // Set a custom font for the "Station #" text with a larger size
            Font stationFont = new Font("Arial", Font.BOLD, 30); // Change font and size as needed
            label.setFont(stationFont);

            panel.add(stationObjects[i].square, gbc);
        }

        // Add empty panels to fill the remaining space
        for (int i = stationObjects.length; i < rows * 4; i++) {
            JPanel emptySquare = new JPanel();
            emptySquare.setPreferredSize(new Dimension(220, 220)); // Adjusted for larger size
            emptySquare.setBackground(Color.LIGHT_GRAY); // Set background color for empty squares
            panel.add(emptySquare, gbc);
        }

        panel.revalidate(); // Refresh the panel
        panel.repaint(); // Repaint the panel
    }

    // Method to create a window for maintaining paper
    private void createMaintainPaperWindow(JFrame mainFrame) {
        JFrame window = new JFrame("Maintain Paper");
        window.setSize(400, 200);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField quantityTextField = new JTextField();
        quantityTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        quantityTextField.setMaximumSize(new Dimension(300, 40));

        JButton enterButton = new JButton("Enter");
        enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        enterButton.setFont(new Font("Arial", Font.PLAIN, 20));

        enterButton.addActionListener(e -> {
            String quantity = quantityTextField.getText();
            handleEnteredQuantity("Maintain Paper", quantity);
            window.dispose();
        });

        JLabel label = new JLabel("Enter quantity:");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", Font.BOLD, 24));

        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));  // Increased spacing
        panel.add(quantityTextField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));  // Increased spacing
        panel.add(enterButton);

        // Center the panel within the window
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        window.add(panel);
        centerWindowOnFrame(window, mainFrame);
        window.setVisible(true);
    }

    // Method to create a window for maintaining ink
    private void createMaintainInkWindow(JFrame mainFrame) {
        JFrame window = new JFrame("Maintain Ink");
        window.setSize(400, 200);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField quantityTextField = new JTextField();
        quantityTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        quantityTextField.setMaximumSize(new Dimension(300, 40));

        JButton enterButton = new JButton("Enter");
        enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        enterButton.setFont(new Font("Arial", Font.PLAIN, 20));

        enterButton.addActionListener(e -> {
            String quantity = quantityTextField.getText();
            handleEnteredQuantity("Maintain Ink", quantity);
            window.dispose();
        });

        JLabel label = new JLabel("Enter quantity:");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", Font.BOLD, 24));

        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));  // Increased spacing
        panel.add(quantityTextField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));  // Increased spacing
        panel.add(enterButton);

        // Center the panel within the window
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        window.add(panel);
        centerWindowOnFrame(window, mainFrame);
        window.setVisible(true);
    }

    // Method to create a window for maintaining coins
    private void createMaintainCoinsWindow(JFrame mainFrame) {
        JFrame coinsWindow = new JFrame("Maintain Coins");
        coinsWindow.setSize(600, 300);
        coinsWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        String[] denominations = {"1 cent", "5 cents", "10 cents", "25 cents"};
        JTextField[] quantityTextFields = new JTextField[denominations.length];

        for (int i = 0; i < denominations.length; i++) {
            JPanel slotPanel = new JPanel();
            slotPanel.setLayout(new FlowLayout());

            JLabel denominationLabel = new JLabel(denominations[i]);
            denominationLabel.setFont(new Font("Arial", Font.PLAIN, 16));  // Increased font size

            JTextField quantityTextField = new JTextField();
            quantityTextField.setPreferredSize(new Dimension(80, 40));

            quantityTextFields[i] = quantityTextField;

            slotPanel.add(denominationLabel);
            slotPanel.add(quantityTextField);

            panel.add(slotPanel);
        }

        JButton enterButton = new JButton("Enter");
        enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        enterButton.setFont(new Font("Arial", Font.PLAIN, 20));

        enterButton.addActionListener(e -> {
            int[] quantities = new int[denominations.length];
            for (int i = 0; i < denominations.length; i++) {
                String quantityText = quantityTextFields[i].getText();
                quantities[i] = Integer.parseInt(quantityText);
            }
            handleEnteredQuantities("Maintain Coins", denominations, quantities);
            coinsWindow.dispose();
        });

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(enterButton);

        coinsWindow.add(panel);
        centerWindowOnFrame(coinsWindow, mainFrame);
        coinsWindow.setVisible(true);
    }

    // Method to create a window for maintaining banknotes
    private void createMaintainBanknotesWindow(JFrame mainFrame) {
        JFrame banknotesWindow = new JFrame("Maintain Banknotes");
        banknotesWindow.setSize(600, 300);
        banknotesWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        String[] denominations = {"$1", "$5", "$10", "$20", "$50", "$100"};
        JTextField[] quantityTextFields = new JTextField[denominations.length];

        for (int i = 0; i < denominations.length; i++) {
            JPanel slotPanel = new JPanel();
            slotPanel.setLayout(new FlowLayout());

            JLabel denominationLabel = new JLabel(denominations[i]);
            denominationLabel.setFont(new Font("Arial", Font.PLAIN, 16));  // Increased font size

            JTextField quantityTextField = new JTextField();
            quantityTextField.setPreferredSize(new Dimension(80, 40));

            quantityTextFields[i] = quantityTextField;

            slotPanel.add(denominationLabel);
            slotPanel.add(quantityTextField);

            panel.add(slotPanel);
        }

        JButton enterButton = new JButton("Enter");
        enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        enterButton.setFont(new Font("Arial", Font.PLAIN, 20));

        enterButton.addActionListener(e -> {
            int[] quantities = new int[denominations.length];
            for (int i = 0; i < denominations.length; i++) {
                String quantityText = quantityTextFields[i].getText();
                quantities[i] = Integer.parseInt(quantityText);
            }
            handleEnteredQuantities("Maintain Banknotes", denominations, quantities);
            banknotesWindow.dispose();
        });

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(enterButton);

        banknotesWindow.add(panel);
        centerWindowOnFrame(banknotesWindow, mainFrame);
        banknotesWindow.setVisible(true);
    }

    // Method to create a window for text search
    private void createTextSearchWindow(JFrame mainFrame) {
        JFrame textSearchWindow = new JFrame("Add Item by Text Search");
        textSearchWindow.setSize(400, 300); // Increased window size
        textSearchWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        
        JList list = new JList(searchList);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(300, 300));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(scrollPane);

        JLabel label = new JLabel("Enter search text:");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", Font.BOLD, 24)); // Increased font size
        panel.add(label);

        JTextField searchTextfield = new JTextField();
        searchTextfield.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchTextfield.setMaximumSize(new Dimension(300, 40)); // Increased text field size
        
        JButton addItemButton = new JButton("Add Item To Order");
        addItemButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addItemButton.setFont(new Font("Arial", Font.PLAIN, 20));
        
        
        JButton enterButton = new JButton("Enter");
        enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        enterButton.setFont(new Font("Arial", Font.PLAIN, 20)); // Increased font size

        // Add action listener to the Enter button
        enterButton.addActionListener(e -> {
            // Handle the entered text (you can use the text from searchTextfield)
            String searchText = searchTextfield.getText();
            searchList.removeAllElements();
            // Perform actions based on the entered text
            handleEnteredText(searchText);

            // Close the window after processing
            //textSearchWindow.dispose();
        });
        
     // Add action listener to the Add Item button
        addItemButton.addActionListener(e -> {
            // Handle the entered text (you can use the text from searchTextfield)
            String itemText = (String) list.getSelectedValue();
            // Perform actions based on the entered text
            handleAddItemText(itemText);
            
            // Close the window after processing
            textSearchWindow.dispose();
        });

        panel.add(Box.createRigidArea(new Dimension(0, 10)));  // Increased spacing
        panel.add(searchTextfield);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));  // Increased spacing
        panel.add(enterButton);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(addItemButton);
        panel.add(Box.createRigidArea(new Dimension(0,10)));

        textSearchWindow.add(panel);
        centerWindowOnFrame(textSearchWindow, mainFrame);

        textSearchWindow.setVisible(true);
    }

    private void solveWeightDiscrepancy() {
    	logic.weightLogic.setExpectedWeight(logic.weightLogic.getActualWeight());
    	logic.stateLogic.gotoState(States.NORMAL);
        // Add your logic to solve the weight discrepancy for the given station
        // You can access the station object and perform necessary actions
        // For demonstration purposes, print a message
        //System.out.println("Solving weight discrepancy for Station #" + station.getStationNumber());
    }

    // Method to handle button click events
    private void handleButtonClick(String buttonLabel, JFrame frame) {

        if ("Add Item by Text Search".equals(buttonLabel)) {
            // Handle the "Add Item by Text Search" button click
            createTextSearchWindow(frame);

        } else if ("Maintain Coins".equals(buttonLabel)) {
            // Handle the "Maintain Coins" button click
            createMaintainCoinsWindow(frame);
        } else if ("Maintain Banknotes".equals(buttonLabel)) {
            // Handle the "Maintain Banknotes" button click
            createMaintainBanknotesWindow(frame);

        } else if ("Maintain Ink".equals(buttonLabel)) {

            createMaintainInkWindow(frame);
        } else if ("Maintain Paper".equals(buttonLabel)) {

            createMaintainPaperWindow(frame);
        }
    }

    // Method to handle entered quantities for coins or banknotes
    private void handleEnteredQuantities(String buttonLabel, String[] denominations, int[] quantities) {
        // Add your logic here based on the buttonLabel, denominations, and quantities
        // For example, you can print the values for demonstration purposes
        System.out.println(buttonLabel + " - Quantities:");
        for (int i = 0; i < denominations.length; i++) {
            System.out.println(denominations[i] + ": " + quantities[i]);
        }
    }

    private void handleEnteredQuantity(String buttonLabel, String quantity) {
        // Add your logic here based on the buttonLabel and entered quantity
        // For example, you can print the values for demonstration purposes
        System.out.println("Button: " + buttonLabel + ", Quantity: " + quantity);
    }

    // Method to handle entered text for text search
    private void handleEnteredText(String searchText) {
        // Add your logic here based on the entered text
        // For example, you can print the entered text for demonstration purposes
        System.out.println("Search Text: " + searchText);
        
        for(int i = 0; i < data.length; i++)
            if(data[i].toLowerCase().contains(searchText.toLowerCase())) {
                searchList.addElement(data[i]);
            }
        // For example, you can print the entered text for demonstration purposes
        System.out.println("Search Text: " + searchText);
    }
    
    
    
    private void handleAddItemText(String itemText) {
        if(itemText == null) return;

        for(Item i : mainGUI.getItemsInCart()) {
            if (i instanceof BarcodedItem) {
                BarcodedItem bitem = (BarcodedItem) i;
                BarcodedProduct bproduct = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(bitem.getBarcode());
                if(bproduct.getDescription().equals(itemText)) {
                    logic.addBarcodedProductController.addBarcode(bitem.getBarcode());
                }
            } else if (i instanceof PLUCodedItem) {
                PLUCodedItem pitem = (PLUCodedItem) i;
                PLUCodedProduct pproduct = ProductDatabases.PLU_PRODUCT_DATABASE.get(pitem.getPLUCode());
                if(pproduct.getDescription().equals(itemText)) {
                    logic.addPLUCodedProductController.addPLUCode(pitem.getPLUCode());
                }
            }
        }

        System.out.println("Added to Order: " + itemText);
    }

    private void centerWindowOnFrame(JFrame window, JFrame mainFrame) {
        // Center the window on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = mainFrame.getX() + (mainFrame.getWidth() - window.getWidth()) / 2;
        int y = mainFrame.getY() + (mainFrame.getHeight() - window.getHeight()) / 2;
        window.setLocation(x, y);
    }

    public AttendantStationGUI(MainGUI m, CentralStationLogic l) {
        int numStations = 3; // Set the initial number of stations
        comboBox = new JComboBox<StationObject>();
        mainGUI = m;
        logic = l;

        data = new String[SimulatedItems.simulatedItems.size()];
        int index = 0;
        for(Item i: SimulatedItems.simulatedItems) {
            data[index] = mainGUI.getDescriptionOfItem(i);
            System.out.println(mainGUI.getDescriptionOfItem(i));
            index++;
        }
        
        // Create an array of StationObject instances
        stationObjects = new StationObject[numStations];
        for (int i = 0; i < numStations; i++) {
            stationObjects[i] = new StationObject(i + 1);
        }

        // Create an instance of AttendantStationGUI
        createFrame(stationObjects);
    }

	public JPanel getPanel() {
		return mainPanel;
	}

	public StationObject getStationObjects(int i) {
		return stationObjects[i];
	}
}

