import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

public class AttendantStationGUI {

    private static JComboBox<String> comboBox;

    static void createFrame(int numSquares) {
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
        updateComboBoxOptions(numSquares); // Initialize JComboBox options
        comboBox.setEditable(false);

        // Create a JComboBox (button with a dropdown menu) for the right panel
        updateComboBoxOptions(numSquares); // Initialize JComboBox options
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

        JPanel mainPanel = new JPanel(new BorderLayout());
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
        frame.setVisible(true);

        // Initial creation of squares (hardcoded)
        createSquares(leftPanel, numSquares); // Pass the number of squares as a parameter
    }

    // Method to update JComboBox options based on the given number of squares
    private static void updateComboBoxOptions(int numSquares) {
        String[] comboBoxOptions = new String[numSquares];
        for (int i = 0; i < numSquares; i++) {
            comboBoxOptions[i] = "Station " + (i + 1);
        }
        comboBox.setModel(new DefaultComboBoxModel<>(comboBoxOptions));
    }

    // Method to create squares based on the given number
    // Method to create squares based on the given number
    private static void createSquares(JPanel panel, int numSquares) {
        panel.removeAll(); // Clear existing components

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); // Increased padding between components

        int rows = (int) Math.ceil((double) numSquares / 4);

        for (int i = 0; i < numSquares; i++) {
            JPanel square = new JPanel(new BorderLayout());
            square.setPreferredSize(new Dimension(220, 220)); // Increased size
            square.setBackground(Color.BLUE); // You can customize the color

            // Center the square in the panel using GridBagConstraints
            gbc.gridx = i % 4;
            gbc.gridy = i / 4;

            // Add labeled lights under the square
            JPanel lightsPanel = new JPanel();
            GridLayout lightsLayout = new GridLayout(1, 4);
            lightsLayout.setHgap(10); // Set horizontal gap between lights
            lightsPanel.setLayout(lightsLayout);

            for (char label : new char[]{'I', 'P', 'C', 'B'}) {
                JPanel light = new JPanel(new GridBagLayout()) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2d = (Graphics2D) g.create();
                        int diameter = Math.min(getWidth() - 4, getHeight() - 4); // Adjusted diameter
                        int x = (getWidth() - diameter) / 2;
                        int y = (getHeight() - diameter) / 2;
                        Ellipse2D.Double ellipse = new Ellipse2D.Double(x, y, diameter, diameter);
                        g2d.setColor(Color.YELLOW); // Set color of lights
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

            square.add(lightsPanel, BorderLayout.SOUTH);

            // Add a label with the text "Station #" and the current number
            JLabel label = new JLabel("Station #" + (i + 1));
            label.setForeground(Color.WHITE); // Set text color to white
            label.setHorizontalAlignment(JLabel.CENTER); // Center align the text
            square.add(label, BorderLayout.CENTER);

            // Set a custom font for the "Station #" text with a larger size
            Font stationFont = new Font("Arial", Font.BOLD, 30); // Change font and size as needed
            label.setFont(stationFont);

            square.add(label, BorderLayout.CENTER);

            panel.add(square, gbc);
        }

        // Add empty panels to fill the remaining space
        for (int i = numSquares; i < rows * 4; i++) {
            JPanel emptySquare = new JPanel();
            emptySquare.setPreferredSize(new Dimension(220, 220)); // Adjusted for larger size
            emptySquare.setBackground(Color.LIGHT_GRAY); // Set background color for empty squares
            panel.add(emptySquare, gbc);
        }

        panel.revalidate(); // Refresh the panel
        panel.repaint(); // Repaint the panel
    }


    // Method to handle button click events
    private static void handleButtonClick(String buttonLabel, JFrame frame) {

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

    private static void createMaintainPaperWindow(JFrame mainFrame) {

        // For other buttons, you can add your logic here
        JFrame window = new JFrame("Maintain Paper");
        window.setSize(300, 150);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField quantityTextField = new JTextField();
        quantityTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        quantityTextField.setMaximumSize(new Dimension(200, 30));

        JButton enterButton = new JButton("Enter");
        enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add action listener to the Enter button
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the entered quantity (you can use the text from quantityTextField)
                String quantity = quantityTextField.getText();
                // Perform actions based on the buttonLabel and entered quantity
                handleEnteredQuantity("Maintain Paper", quantity);

                // Close the window after processing
                window.dispose();
            }
        });

        JLabel label = new JLabel("Enter quantity:");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(label);
        panel.add(quantityTextField);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(enterButton);

        // Center the panel within the window
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        window.add(panel);

        // Center the window on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - window.getWidth()) / 2;
        int y = (screenSize.height - window.getHeight()) / 2;
        window.setLocation(x, y);

        window.setVisible(true);


    }

    private static void createMaintainInkWindow(JFrame mainFrame) {

        // For other buttons, you can add your logic here
        JFrame window = new JFrame("Maintain Ink");
        window.setSize(300, 150);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField quantityTextField = new JTextField();
        quantityTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        quantityTextField.setMaximumSize(new Dimension(200, 30));

        JButton enterButton = new JButton("Enter");
        enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add action listener to the Enter button
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the entered quantity (you can use the text from quantityTextField)
                String quantity = quantityTextField.getText();
                // Perform actions based on the buttonLabel and entered quantity
                handleEnteredQuantity("Maintain Ink", quantity);

                // Close the window after processing
                window.dispose();
            }
        });

        JLabel label = new JLabel("Enter quantity:");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(label);
        panel.add(quantityTextField);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(enterButton);

        // Center the panel within the window
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        window.add(panel);

        // Center the window on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - window.getWidth()) / 2;
        int y = (screenSize.height - window.getHeight()) / 2;
        window.setLocation(x, y);

        window.setVisible(true);


    }

    // Method to create a window for maintaining coins
    private static void createMaintainCoinsWindow(JFrame mainFrame) {
        JFrame coinsWindow = new JFrame("Maintain Coins");
        coinsWindow.setSize(400, 250);
        coinsWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create multiple slots for different denominations
        String[] denominations = {"1 cent", "5 cents", "10 cents", "25 cents"};
        JTextField[] quantityTextFields = new JTextField[denominations.length];

        for (int i = 0; i < denominations.length; i++) {
            JPanel slotPanel = new JPanel();
            slotPanel.setLayout(new FlowLayout());

            JLabel denominationLabel = new JLabel(denominations[i]);
            JTextField quantityTextField = new JTextField();
            quantityTextField.setPreferredSize(new Dimension(50, 30));  // Fixed size

            quantityTextFields[i] = quantityTextField;

            slotPanel.add(denominationLabel);
            slotPanel.add(quantityTextField);

            panel.add(slotPanel);
        }

        JButton enterButton = new JButton("Enter");

        // Add action listener to the Enter button
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the entered quantities for each denomination
                int[] quantities = new int[denominations.length];
                for (int i = 0; i < denominations.length; i++) {
                    String quantityText = quantityTextFields[i].getText();
                    quantities[i] = Integer.parseInt(quantityText);
                }

                // Perform actions based on the entered quantities
                handleEnteredQuantities("Maintain Coins", denominations, quantities);

                // Close the window after processing
                coinsWindow.dispose();
            }
        });

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(enterButton);

        coinsWindow.add(panel);
        coinsWindow.setVisible(true);

        // Center the window on the main frame
        centerWindowOnFrame(coinsWindow, mainFrame);

        coinsWindow.setVisible(true);
    }

    // Method to create a window for maintaining banknotes
    private static void createMaintainBanknotesWindow(JFrame mainFrame) {
        JFrame banknotesWindow = new JFrame("Maintain Banknotes");
        banknotesWindow.setSize(400, 250);
        banknotesWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create multiple slots for different denominations
        String[] denominations = {"$1", "$5", "$10", "$20", "$50", "$100"};
        JTextField[] quantityTextFields = new JTextField[denominations.length];

        for (int i = 0; i < denominations.length; i++) {
            JPanel slotPanel = new JPanel();
            slotPanel.setLayout(new FlowLayout());

            JLabel denominationLabel = new JLabel(denominations[i]);
            JTextField quantityTextField = new JTextField();
            quantityTextField.setPreferredSize(new Dimension(50, 30));  // Fixed size

            quantityTextFields[i] = quantityTextField;

            slotPanel.add(denominationLabel);
            slotPanel.add(quantityTextField);

            panel.add(slotPanel);
        }

        JButton enterButton = new JButton("Enter");

        // Add action listener to the Enter button
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the entered quantities for each denomination
                int[] quantities = new int[denominations.length];
                for (int i = 0; i < denominations.length; i++) {
                    String quantityText = quantityTextFields[i].getText();
                    quantities[i] = Integer.parseInt(quantityText);
                }

                // Perform actions based on the entered quantities
                handleEnteredQuantities("Maintain Banknotes", denominations, quantities);

                // Close the window after processing
                banknotesWindow.dispose();
            }
        });

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(enterButton);

        banknotesWindow.add(panel);
        banknotesWindow.setVisible(true);

        // Center the window on the main frame
        centerWindowOnFrame(banknotesWindow, mainFrame);

        banknotesWindow.setVisible(true);
    }


    // Method to handle entered quantities for coins or banknotes
    private static void handleEnteredQuantities(String buttonLabel, String[] denominations, int[] quantities) {
        // Add your logic here based on the buttonLabel, denominations, and quantities
        // For example, you can print the values for demonstration purposes
        System.out.println(buttonLabel + " - Quantities:");
        for (int i = 0; i < denominations.length; i++) {
            System.out.println(denominations[i] + ": " + quantities[i]);
        }
    }

    private static void handleEnteredQuantity(String buttonLabel, String quantity) {
        // Add your logic here based on the buttonLabel and entered quantity
        // For example, you can print the values for demonstration purposes
        System.out.println("Button: " + buttonLabel + ", Quantity: " + quantity);
    }

    // Method to create a window for text search
    // Method to create a window for text search
    // Method to create a window for text search
    private static void createTextSearchWindow(JFrame mainFrame) {
        JFrame textSearchWindow = new JFrame("Add Item by Text Search");
        textSearchWindow.setSize(300, 150);
        textSearchWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Enter search text:");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);

        JTextField searchTextfield = new JTextField();
        searchTextfield.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchTextfield.setMaximumSize(new Dimension(200, 30));

        JButton enterButton = new JButton("Enter");
        enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add action listener to the Enter button
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the entered text (you can use the text from searchTextfield)
                String searchText = searchTextfield.getText();
                // Perform actions based on the entered text
                handleEnteredText(searchText);

                // Close the window after processing
                textSearchWindow.dispose();
            }
        });

        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(searchTextfield);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(enterButton);

        textSearchWindow.add(panel);
        textSearchWindow.setVisible(true);

        centerWindowOnFrame(textSearchWindow, mainFrame);

        textSearchWindow.setVisible(true);
    }


    // Method to handle entered text for text search
    private static void handleEnteredText(String searchText) {
        // Add your logic here based on the entered text
        // For example, you can print the entered text for demonstration purposes
        System.out.println("Search Text: " + searchText);
    }

    private static void centerWindowOnFrame(JFrame window, JFrame mainFrame) {
        // Center the window on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = mainFrame.getX() + (mainFrame.getWidth() - window.getWidth()) / 2;
        int y = mainFrame.getY() + (mainFrame.getHeight() - window.getHeight()) / 2;
        window.setLocation(x, y);
    }

    public static void main(String[] args) {
        int numSquares = 5; // Set the initial number of squares
        SwingUtilities.invokeLater(() -> createFrame(numSquares));
    }

}
