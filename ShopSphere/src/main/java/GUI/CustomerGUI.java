package GUI;

import Model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class CustomerGUI extends JFrame {
    private Customer customer;
    private JFrame loginFrame;
    private JFrame customerFrame;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JPanel mainPanel;
    private JPanel contentPanel;

    public CustomerGUI() {
        customer = new Customer();
        showLoginScreen();
    }

    private void showLoginScreen() {
        loginFrame = new JFrame("E-commerce Application - Customer Login");
        loginFrame.setSize(800, 600);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setResizable(false);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setLayout(null);

        // Load background image
        ImageIcon backgroundImage = new ImageIcon("ninyy.jpg"); // Replace with your image
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 800, 600); // Set the background image size to fit the frame
        loginFrame.add(backgroundLabel);

        JLabel label = new JLabel("Welcome to ShopSphere - Customer Portal");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(new Color(199, 227, 225));
        label.setBounds(0, 50, 800, 50);
        backgroundLabel.add(label); // Add label to the background panel

        JPanel glassPanel = new JPanel();
        glassPanel.setBounds(250, 150, 300, 220); // Adjusted size to fit the components
        glassPanel.setBackground(new Color(80, 114, 123, 255)); // Semi-transparent background
        glassPanel.setBorder(BorderFactory.createLineBorder(new Color(120, 160, 131), 2));
        glassPanel.setLayout(null);
        backgroundLabel.add(glassPanel); // Add glass panel to the background

        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));
        usernameField.setBounds(50, 30, 200, 40);
        glassPanel.add(usernameField);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        passwordField.setBounds(50, 90, 200, 40);
        glassPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(0, 120, 215));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBounds(100, 150, 100, 30);
        glassPanel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (customer.signIn(username, password)) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    loginFrame.dispose();
                    showCustomerDashboard();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.");
                }
            }
        });

        // Back Button (Green color) added directly to the backgroundLabel
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(39, 174, 96)); // Green color
        backButton.setForeground(Color.WHITE);
        backButton.setBounds(10, 530, 80, 30); // Positioned at the bottom left of the entire screen
        backgroundLabel.add(backButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginFrame.dispose(); // Close current login screen
                new LandingPage(); // Go back to the main landing page screen
            }
        });

        JLabel signupLabel = new JLabel("Don't have an account? Sign up");
        signupLabel.setForeground(new Color(199, 227, 225));
        signupLabel.setBounds(300, 460, 200, 30);
        backgroundLabel.add(signupLabel); // Add signup label to the background

        signupLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signupLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loginFrame.dispose();
                showSignupScreen();
            }
        });

        loginFrame.setVisible(true);
    }

    private void showSignupScreen() {
        JFrame signupFrame = new JFrame("E-commerce Application - Customer Signup");
        signupFrame.setSize(800, 600);
        signupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        signupFrame.setResizable(false);
        signupFrame.getContentPane().setBackground(new Color(52, 73, 85));
        signupFrame.setLayout(null);

        JLabel label = new JLabel("Sign Up for ShopSphere");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(new Color(199, 227, 225));
        label.setBounds(0, 50, 800, 50);
        signupFrame.add(label);

        JPanel glassPanel = new JPanel();
        int glassPanelWidth = 300;
        int glassPanelHeight = 400;
        glassPanel.setBounds((signupFrame.getWidth() - glassPanelWidth) / 2, (signupFrame.getHeight() - glassPanelHeight) / 2, glassPanelWidth, glassPanelHeight);
        glassPanel.setBackground(new Color(80, 114, 123, 255));
        glassPanel.setBorder(BorderFactory.createLineBorder(new Color(120, 160, 131), 2));
        glassPanel.setLayout(null);

        JTextField nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        nameField.setBorder(BorderFactory.createTitledBorder("Name"));
        nameField.setBounds(50, 30, 200, 40);
        glassPanel.add(nameField);

        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));
        usernameField.setBounds(50, 80, 200, 40);
        glassPanel.add(usernameField);

        JTextField emailField = new JTextField();
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));
        emailField.setBorder(BorderFactory.createTitledBorder("Email"));
        emailField.setBounds(50, 130, 200, 40);
        glassPanel.add(emailField);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        passwordField.setBounds(50, 180, 200, 40);
        glassPanel.add(passwordField);

        JTextField phoneField = new JTextField();
        phoneField.setFont(new Font("Arial", Font.PLAIN, 16));
        phoneField.setBorder(BorderFactory.createTitledBorder("Phone"));
        phoneField.setBounds(50, 230, 200, 40);
        glassPanel.add(phoneField);

        JTextField addressField = new JTextField();
        addressField.setFont(new Font("Arial", Font.PLAIN, 16));
        addressField.setBorder(BorderFactory.createTitledBorder("Address"));
        addressField.setBounds(50, 280, 200, 40);
        glassPanel.add(addressField);

        JButton signupButton = new JButton("Sign Up");
        signupButton.setFont(new Font("Arial", Font.BOLD, 16));
        signupButton.setBackground(new Color(0, 120, 215));
        signupButton.setForeground(Color.WHITE);
        signupButton.setBounds(100, 330, 100, 30); // Adjusted to prevent clipping
        glassPanel.add(signupButton);

        // Add the glass panel to the frame
        signupFrame.add(glassPanel);

        // Back Button outside glass panel, positioned at bottom-left of the screen
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(39, 174, 96)); // Green color for the Back button
        backButton.setForeground(Color.WHITE);
        backButton.setBounds(10, 530, 80, 30); // Positioned at bottom-left corner of entire screen
        signupFrame.add(backButton);

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String username = usernameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String phone = phoneField.getText();
                String address = addressField.getText();

                try {
                    customer.signup(username, email, password);
                    JOptionPane.showMessageDialog(null, "Signup successful!");
                    signupFrame.dispose();
                    showLoginScreen(); // Go to the login screen after successful signup
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        // Action listener for Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signupFrame.dispose(); // Close the signup screen
                showLoginScreen(); // Go to the login screen
            }
        });

        signupFrame.setLocationRelativeTo(null);
        signupFrame.setVisible(true);
    }

    private void showCustomerDashboard() {
        customerFrame = new JFrame("Customer Dashboard - ShopSphere");
        customerFrame.setSize(1000, 600);
        customerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        customerFrame.setLocationRelativeTo(null);

        // Load the background image
        ImageIcon backgroundImage = new ImageIcon("nin.jpg");

        // Create a JLabel with the image as its icon
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new BorderLayout()); // Allow adding components over the background

        // Main panel for the dashboard
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false); // Make the panel transparent to show the background image

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false); // Transparent button panel
        addButtons(buttonPanel);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false); // Transparent content panel
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Add mainPanel to backgroundLabel
        backgroundLabel.add(mainPanel);

        // Add backgroundLabel to the frame
        customerFrame.add(backgroundLabel);
        customerFrame.setVisible(true);
    }

    private void setupProductTable() {
        contentPanel.removeAll();

        String[] columns = { "ID", "Name", "Company", "Price", "Stock" };
        tableModel = new DefaultTableModel(columns, 0);
        productTable = new JTable(tableModel);
        refreshProductTable();

        JScrollPane scrollPane = new JScrollPane(productTable);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setFont(new Font("Arial", Font.BOLD, 16));
        addToCartButton.setPreferredSize(new Dimension(150, 40));
        addToCartButton.addActionListener(e -> addToCart());
        buttonPanel.add(addToCartButton);

        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.addActionListener(e -> showMainMenu());
        buttonPanel.add(backButton);

        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showMainMenu() {
        contentPanel.removeAll();
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void refreshProductTable() {
        tableModel.setRowCount(0);
        List<Product> products = customer.getProducts();
        System.out.println("Refreshing product table with " + products.size() + " products.");
        for (Product product : products) {
            tableModel.addRow(new Object[] {
                    product.getId(),
                    product.getName(),
                    product.getCompany(),
                    product.getPrice(),
                    product.getStock()
            });
        }
        System.out.println("Product table refreshed with " + tableModel.getRowCount() + " rows.");
    }

    private void addButtons(JPanel buttonPanel) {
        buttonPanel.setLayout(new GridLayout(2, 3, 20, 20)); // 2 rows, 3 columns, with spacing
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // Add padding around the panel

        JButton showProductsButton = new JButton("Show Products");
        JButton viewCartButton = new JButton("View Cart");
        JButton addToCartButton = new JButton("Add to Cart");
        JButton placeOrderButton = new JButton("Place Order");
        JButton viewOrdersButton = new JButton("View Orders");
        JButton logoutButton = new JButton("Logout");

        // Set button properties to make them large and visually appealing
        JButton[] buttons = {
                showProductsButton, viewCartButton, addToCartButton, placeOrderButton, viewOrdersButton, logoutButton
        };

        for (JButton button : buttons) {
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.setPreferredSize(new Dimension(200, 100));
            buttonPanel.add(button);
        }

        // Adding action listeners
        showProductsButton.addActionListener(e -> setupProductTable());
        viewCartButton.addActionListener(e -> viewCart());
        addToCartButton.addActionListener(e -> addToCart());
        placeOrderButton.addActionListener(e -> placeOrder());
        viewOrdersButton.addActionListener(e -> viewOrders());
        logoutButton.addActionListener(e -> logout());
    }

    private void addToCart() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            int productId = (int) tableModel.getValueAt(selectedRow, 0);
            String productName = (String) tableModel.getValueAt(selectedRow, 1);
            double productPrice = (double) tableModel.getValueAt(selectedRow, 3);
            int currentStock = (int) tableModel.getValueAt(selectedRow, 4);

            if (currentStock > 0) {
                String quantityString = JOptionPane.showInputDialog(customerFrame, "Enter Quantity:");
                try {
                    int quantity = Integer.parseInt(quantityString);
                    if (quantity > 0 && quantity <= currentStock) {
                        OrderItem item = new OrderItem(productId, productName, BigDecimal.valueOf(productPrice), quantity);
                        customer.addItemToCart(item);
                        customer.updateProductStock(productId, currentStock - quantity);
                        JOptionPane.showMessageDialog(customerFrame, "Product added to cart!");
                        refreshProductTable(); // Refresh to show updated stock
                    } else {
                        JOptionPane.showMessageDialog(customerFrame, "Invalid quantity. Please enter a number between 1 and " + currentStock + ".");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(customerFrame, "Please enter a valid quantity.");
                }
            } else {
                JOptionPane.showMessageDialog(customerFrame, "This product is out of stock.");
            }
        } else {
            JOptionPane.showMessageDialog(customerFrame, "Please select a product to add to cart.");
        }
    }

    private void placeOrder() {
        if (customer.getTotalCartPrice() > 0) {
            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Enter shipping address:"));
            JTextField addressField = new JTextField(20);
            panel.add(addressField);

            panel.add(new JLabel("Select payment method:"));
            String[] paymentMethods = {"Easypaisa/Jazzcash", "Credit or Debit Card", "Cash on Delivery"};
            JComboBox<String> paymentMethodCombo = new JComboBox<>(paymentMethods);
            panel.add(paymentMethodCombo);

            int result = JOptionPane.showConfirmDialog(customerFrame, panel, "Checkout", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String address = addressField.getText().trim();
                String paymentMethod = (String) paymentMethodCombo.getSelectedItem();

                if (!address.isEmpty() && paymentMethod != null) {
                    try {
                        customer.placeOrder(address, paymentMethod);
                        JOptionPane.showMessageDialog(customerFrame, "Order placed successfully!\nShipping Address: " + address + "\nPayment Method: " + paymentMethod);
                        showMainMenu();
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(customerFrame, "Error placing order: " + e.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(customerFrame, "Please enter shipping address and select a payment method.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(customerFrame, "Your cart is empty. Add products to the cart before placing an order.");
        }
    }


    private void viewCart() {
        contentPanel.removeAll();

        String[] columns = { "Product", "Quantity", "Price" };
        DefaultTableModel cartTableModel = new DefaultTableModel(columns, 0);
        JTable cartTable = new JTable(cartTableModel);

        List<OrderItem> cartItems = customer.viewCartItems();
        for (OrderItem item : cartItems) {
            cartTableModel.addRow(new Object[] {
                    item.getProductName(),
                    item.getQuantity(),
                    item.getTotalPrice()
            });
        }

        JScrollPane scrollPane = new JScrollPane(cartTable);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton checkoutButton = new JButton("Checkout");
        JButton backButton = new JButton("Back to Menu");
        JButton removeButton = new JButton("Remove from Cart");

        checkoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        removeButton.setFont(new Font("Arial", Font.BOLD, 16));

        checkoutButton.setPreferredSize(new Dimension(150, 40));
        backButton.setPreferredSize(new Dimension(150, 40));
        removeButton.setPreferredSize(new Dimension(200, 40));

        checkoutButton.addActionListener(e -> checkout());
        backButton.addActionListener(e -> showMainMenu());
        removeButton.addActionListener(e -> removeFromCart(cartTable));

        bottomPanel.add(checkoutButton);
        bottomPanel.add(removeButton);
        bottomPanel.add(backButton);

        contentPanel.add(bottomPanel, BorderLayout.SOUTH);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void checkout() {
        if (customer.getTotalCartPrice() > 0) {
            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Enter shipping address:"));
            JTextField addressField = new JTextField(20);
            panel.add(addressField);
            panel.add(new JLabel("Enter payment method:"));
            JTextField paymentField = new JTextField(20);
            panel.add(paymentField);

            int result = JOptionPane.showConfirmDialog(customerFrame, panel, "Checkout", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String address = addressField.getText().trim();
                String paymentMethod = paymentField.getText().trim();

                if (!address.isEmpty() && !paymentMethod.isEmpty()) {
                    try {
                        customer.placeOrder(address, paymentMethod);
                        JOptionPane.showMessageDialog(customerFrame, "Order placed successfully!\nShipping Address: " + address + "\nPayment Method: " + paymentMethod);
                        showMainMenu();
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(customerFrame, "Error placing order: " + e.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(customerFrame, "Please enter both shipping address and payment method.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(customerFrame, "Your cart is empty. Add products to the cart before checking out.");
        }
    }

    private void removeFromCart(JTable cartTable) {
        int selectedRow = cartTable.getSelectedRow();
        if (selectedRow != -1) {
            String productName = (String) cartTable.getValueAt(selectedRow, 0);
            int productId = -1;
            for (OrderItem item : customer.viewCartItems()) {
                if (item.getProductName().equals(productName)) {
                    productId = item.getProductId();
                    break;
                }
            }
            if (productId != -1) {
                customer.removeItemFromCart(productId);
                JOptionPane.showMessageDialog(customerFrame, "Product removed from cart!");
                viewCart(); // Refresh the cart view
            }
        } else {
            JOptionPane.showMessageDialog(customerFrame, "Please select a product to remove from the cart.");
        }
    }
    private void viewOrders() {
        contentPanel.removeAll();

        String[] columns = { "Order ID", "Date", "Total", "Status" };
        DefaultTableModel orderTableModel = new DefaultTableModel(columns, 0);
        JTable orderTable = new JTable(orderTableModel);

        try {
            List<Order> orders = customer.viewOrders();
            for (Order order : orders) {
                orderTableModel.addRow(new Object[] {
                        order.getOrderId(),
                        order.getOrderDate(),
                        calculateOrderTotal(order),
                        order.getOrderStatus()
                });
            }
        }catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(customerFrame, "Error loading orders: " + e.getMessage());
        }

        JScrollPane scrollPane = new JScrollPane(orderTable);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.addActionListener(e -> showMainMenu());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private double calculateOrderTotal(Order order) {
        double total = 0;
        for (OrderItem item : order.getItems()) {
            total += item.getTotalPrice().doubleValue();
        }
        return total;
    }

    private void logout() {
        customer.logout();
        customerFrame.dispose();
        showLoginScreen();
    }

}

