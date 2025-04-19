package GUI;

import Model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

public class AdminGUI extends JFrame {
    private Admin admin;
    private JFrame loginFrame;
    private JFrame adminFrame;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JPanel mainPanel;
    private JPanel contentPanel;

    public AdminGUI() {
        admin = new Admin();
        tableModel = new DefaultTableModel();
        showLoginScreen();
    }

    private void showLoginScreen() {
        loginFrame = new JFrame("E-commerce Application");
        loginFrame.setSize(800, 600);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setResizable(false);
        loginFrame.setLayout(null);
        loginFrame.setLocationRelativeTo(null);

        // Load the background image
        ImageIcon backgroundImage = new ImageIcon("ninyy.jpg");

        // Create a JLabel for the background image
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 800, 600);
        loginFrame.add(backgroundLabel);

        // Create a transparent label for the welcome message
        JLabel label = new JLabel("Welcome to ShopSphere");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(new Color(199, 227, 225));
        label.setBounds(0, 50, 800, 50);
        backgroundLabel.add(label);

        // Glass panel for login fields
        JPanel glassPanel = new JPanel();
        glassPanel.setBounds(250, 150, 300, 250);
        glassPanel.setBackground(new Color(80, 114, 123, 200));
        glassPanel.setBorder(BorderFactory.createLineBorder(new Color(120, 160, 131), 2));
        glassPanel.setLayout(null);

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

        // Back to Landing Page button at the left bottom corner
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.setBackground(new Color(34, 139, 34)); // Green color
        backButton.setForeground(Color.WHITE);
        backButton.setBounds(10, 510, 100, 30); // Positioned at the left bottom corner
        backgroundLabel.add(backButton); // Added to backgroundLabel instead of glassPanel

        // Action for login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (admin.signIn(username, password)) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    loginFrame.dispose();
                    showAdminDashboard();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.");
                }
            }
        });

        // Action for back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the login screen and show the landing page
                loginFrame.dispose();
                new LandingPage(); // Implement this method to show the landing page
            }
        });

        // Add glass panel to the background label
        backgroundLabel.add(glassPanel);

        loginFrame.setVisible(true);
    }

    private void showAdminDashboard() {
        adminFrame = new JFrame("Admin Dashboard - ShopSphere");
        adminFrame.setSize(1000, 600);
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setLocationRelativeTo(null);

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
        adminFrame.add(backgroundLabel);
        adminFrame.setVisible(true);
    }

    private void setupProductTable() {
        contentPanel.removeAll();

        String[] columns = { "ID", "Name", "Company", "Price", "Stock" };
        tableModel = new DefaultTableModel(columns, 0);
        productTable = new JTable(tableModel);
        refreshProductTable();

        JScrollPane scrollPane = new JScrollPane(productTable);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> showMainMenu());
        contentPanel.add(backButton, BorderLayout.SOUTH);

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
        List<Product> products = admin.getProducts();
        for (Product product : products) {
            tableModel.addRow(new Object[] {
                    product.getId(),
                    product.getName(),
                    product.getCompany(),
                    product.getPrice(),
                    product.getStock()
            });
        }
    }

    private void addButtons(JPanel buttonPanel) {
        buttonPanel.setLayout(new GridLayout(2, 3, 20, 20)); // 2 rows, 3 columns, with spacing
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // Add padding around the panel

        JButton showProductsButton = new JButton("Show Products");
        JButton addButton = new JButton("Add Product");
        JButton removeButton = new JButton("Remove Product");
        JButton updateButton = new JButton("Update Product");
        JButton viewOrdersButton = new JButton("View Orders");
        JButton logoutButton = new JButton("Logout");

        // Set button properties to make them large and visually appealing
        JButton[] buttons = {
                showProductsButton, addButton, removeButton, updateButton, viewOrdersButton, logoutButton
        };

        for (JButton button : buttons) {
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.setPreferredSize(new Dimension(200, 100));
            buttonPanel.add(button);
        }

        // Add action listeners for buttons
        showProductsButton.addActionListener(e -> setupProductTable());
        addButton.addActionListener(e -> showAddProductDialog());
        removeButton.addActionListener(e -> showRemoveProductDialog());
        updateButton.addActionListener(e -> showUpdateProductDialog());
        viewOrdersButton.addActionListener(e -> showOrders());
        logoutButton.addActionListener(e -> logout());
    }

    private void showAddProductDialog() {
        JDialog dialog = new JDialog(adminFrame, "Add Product", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField idField = new JTextField(20);
        JTextField nameField = new JTextField(20);
        JTextField companyField = new JTextField(20);
        JTextField priceField = new JTextField(20);
        JTextField stockField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(new JLabel("Product ID:"), gbc);
        gbc.gridx = 1;
        dialog.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        dialog.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        dialog.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        dialog.add(new JLabel("Company:"), gbc);
        gbc.gridx = 1;
        dialog.add(companyField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        dialog.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1;
        dialog.add(priceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        dialog.add(new JLabel("Stock:"), gbc);
        gbc.gridx = 1;
        dialog.add(stockField, gbc);

        JButton submitButton = new JButton("Add/Update");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        dialog.add(submitButton, gbc);

        submitButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String company = companyField.getText();
                double price = Double.parseDouble(priceField.getText());
                int stock = Integer.parseInt(stockField.getText());

                admin.addOrUpdateProduct(id, name, company, price, stock);
                refreshProductTable();
                dialog.dispose();
                JOptionPane.showMessageDialog(adminFrame, "Product added/updated successfully");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter valid numbers for ID, price, and stock");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage());
            }
        });

        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(adminFrame);
        dialog.setVisible(true);
    }
    private void showRemoveProductDialog() {
        String idStr = JOptionPane.showInputDialog(adminFrame, "Enter Product ID to remove:");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                if (admin.removeProduct(id)) {
                    refreshProductTable();
                    JOptionPane.showMessageDialog(adminFrame, "Product removed successfully");
                } else {
                    JOptionPane.showMessageDialog(adminFrame, "Product not found");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(adminFrame, "Please enter a valid ID");
            }
        }
    }

    private void showUpdateProductDialog() {
        String idStr = JOptionPane.showInputDialog(adminFrame, "Enter Product ID to update:");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                Product product = admin.getProductById(id);
                if (product != null) {
                    JDialog dialog = new JDialog(adminFrame, "Update Product", true);
                    dialog.setLayout(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.insets = new Insets(5, 5, 5, 5);
                    gbc.fill = GridBagConstraints.HORIZONTAL;

                    JTextField nameField = new JTextField(product.getName(), 20);
                    JTextField companyField = new JTextField(product.getCompany(), 20);
                    JTextField priceField = new JTextField(String.valueOf(product.getPrice()), 20);
                    JTextField stockField = new JTextField(String.valueOf(product.getStock()), 20);

                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    dialog.add(new JLabel("Name:"), gbc);
                    gbc.gridx = 1;
                    dialog.add(nameField, gbc);

                    gbc.gridx = 0;
                    gbc.gridy = 1;
                    dialog.add(new JLabel("Company:"), gbc);
                    gbc.gridx = 1;
                    dialog.add(companyField, gbc);

                    gbc.gridx = 0;
                    gbc.gridy = 2;
                    dialog.add(new JLabel("Price:"), gbc);
                    gbc.gridx = 1;
                    dialog.add(priceField, gbc);

                    gbc.gridx = 0;
                    gbc.gridy = 3;
                    dialog.add(new JLabel("Stock:"), gbc);
                    gbc.gridx = 1;
                    dialog.add(stockField, gbc);

                    JButton submitButton = new JButton("Update");
                    gbc.gridx = 0;
                    gbc.gridy = 4;
                    gbc.gridwidth = 2;
                    gbc.anchor = GridBagConstraints.CENTER;
                    dialog.add(submitButton, gbc);

                    submitButton.addActionListener(e -> {
                        try {
                            String name = nameField.getText();
                            String company = companyField.getText();
                            double price = Double.parseDouble(priceField.getText());
                            int stock = Integer.parseInt(stockField.getText());

                            if (admin.updateProduct(id, name, company, price, stock)) {
                                refreshProductTable();
                                dialog.dispose();
                                JOptionPane.showMessageDialog(adminFrame, "Product updated successfully");
                            } else {
                                JOptionPane.showMessageDialog(dialog, "Failed to update product");
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(dialog, "Please enter valid numbers for price and stock");
                        } catch (IllegalArgumentException ex) {
                            JOptionPane.showMessageDialog(dialog, ex.getMessage());
                        }
                    });

                    dialog.setSize(400, 300);
                    dialog.setLocationRelativeTo(adminFrame);
                    dialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(adminFrame, "Product not found");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(adminFrame, "Please enter a valid Product ID");
            }
        }
    }
    private void showOrders() {
        try {
            List<Order> orders = admin.viewOrders();
            JDialog ordersDialog = new JDialog(adminFrame, "Orders", true);
            ordersDialog.setLayout(new BorderLayout());

            String[] columns = { "Order ID", "Customer", "Date", "Total", "Status", "Address", "Payment Method" };
            DefaultTableModel orderTableModel = new DefaultTableModel(columns, 0);
            JTable orderTable = new JTable(orderTableModel);

            for (Order order : orders) {
                orderTableModel.addRow(new Object[]{
                        order.getOrderId(),
                        order.getCustomerUsername(),
                        order.getOrderDate(),
                        calculateOrderTotal(order),
                        order.getOrderStatus(),
                        order.getCustomerAddress(),
                        order.getPaymentMethod()
                });
            }

            JScrollPane scrollPane = new JScrollPane(orderTable);
            ordersDialog.add(scrollPane, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            JButton updateStatusButton = new JButton("Update Status");
            updateStatusButton.addActionListener(e -> updateOrderStatus(orderTable));
            buttonPanel.add(updateStatusButton);

            JButton viewDetailsButton = new JButton("View Order Details");
            viewDetailsButton.addActionListener(e -> viewOrderDetails(orderTable));
            buttonPanel.add(viewDetailsButton);

            ordersDialog.add(buttonPanel, BorderLayout.SOUTH);

            ordersDialog.setSize(800, 400);
            ordersDialog.setLocationRelativeTo(adminFrame);
            ordersDialog.setVisible(true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(adminFrame, "Error loading orders: " + e.getMessage());
        }
    }
    private void viewOrderDetails(JTable orderTable) {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow != -1) {
            String orderId = (String) orderTable.getValueAt(selectedRow, 0);
            try {
                Order order = admin.getOrderById(orderId);
                if (order != null) {
                    JDialog detailsDialog = new JDialog(adminFrame, "Order Details", true);
                    detailsDialog.setLayout(new BorderLayout());

                    JPanel detailsPanel = new JPanel(new GridLayout(0, 2, 5, 5));
                    detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                    addDetailRow(detailsPanel, "Order ID:", order.getOrderId());
                    addDetailRow(detailsPanel, "Customer:", order.getCustomerUsername());
                    addDetailRow(detailsPanel, "Date:", order.getOrderDate());
                    addDetailRow(detailsPanel, "Time:", order.getOrderTime());
                    addDetailRow(detailsPanel, "Status:", order.getOrderStatus());
                    addDetailRow(detailsPanel, "Address:", order.getCustomerAddress());
                    addDetailRow(detailsPanel, "Payment Method:", order.getPaymentMethod());
                    addDetailRow(detailsPanel, "Total:", String.format("$%.2f", calculateOrderTotal(order)));

                    detailsDialog.add(detailsPanel, BorderLayout.NORTH);

                    String[] columns = { "Product", "Quantity", "Price", "Total" };
                    DefaultTableModel itemsTableModel = new DefaultTableModel(columns, 0);
                    JTable itemsTable = new JTable(itemsTableModel);

                    for (OrderItem item : order.getItems()) {
                        itemsTableModel.addRow(new Object[]{
                                item.getProductName(),
                                item.getQuantity(),
                                String.format("$%.2f", item.getPrice()),
                                String.format("$%.2f", item.getTotalPrice())
                        });
                    }

                    JScrollPane scrollPane = new JScrollPane(itemsTable);
                    detailsDialog.add(scrollPane, BorderLayout.CENTER);

                    detailsDialog.setSize(600, 400);
                    detailsDialog.setLocationRelativeTo(adminFrame);
                    detailsDialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(adminFrame, "Order not found.");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(adminFrame, "Error loading order details: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(adminFrame, "Please select an order to view details.");
        }
    }

    private void addDetailRow(JPanel panel, String label, String value) {
        panel.add(new JLabel(label));
        panel.add(new JLabel(value));
    }

    private double calculateOrderTotal(Order order) {
        double total = 0;
        for (OrderItem item : order.getItems()) {
            total += item.getTotalPrice().doubleValue();
        }
        return total;
    }

    private void updateOrderStatus(JTable orderTable) {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow != -1) {
            String orderId = (String) orderTable.getValueAt(selectedRow, 0);
            String[] options = {"Processed", "Shipped", "Delivered"};
            String newStatus = (String) JOptionPane.showInputDialog(adminFrame,
                    "Select new status:", "Update Order Status",
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (newStatus != null) {
                try {
                    if (admin.updateOrderStatus(orderId, newStatus)) {
                        JOptionPane.showMessageDialog(adminFrame, "Order status updated successfully.");
                        orderTable.setValueAt(newStatus, selectedRow, 4);
                    } else {
                        JOptionPane.showMessageDialog(adminFrame, "Failed to update order status.");
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(adminFrame, "Error updating order status: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(adminFrame, "Please select an order to update.");
        }
    }


    private void logout() {
        admin.logout();
        adminFrame.dispose();
        showLoginScreen();
    }
}
