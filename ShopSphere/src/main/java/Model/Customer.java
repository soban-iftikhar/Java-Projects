package Model;

import java.io.*;
import java.util.*;

public class Customer extends Person implements Serializable {
    private static final String FILE_NAME = "credentials.txt";

    private final ArrayList<String> usernames = new ArrayList<>();
    private final ArrayList<String> emails = new ArrayList<>();
    private final ArrayList<String> passwords = new ArrayList<>();
    private Cart cart;
    private String currentUsername; // Add this line

    public Customer() {
        this.cart = new Cart();
        loadUserCredentials();
        Product.loadProductsFromFile();
    }

    public Customer(String name, String username, String email, String password, String phone, String address) {
        super(name, username, email, password, phone, address);
        this.cart = new Cart();
        loadUserCredentials();
    }

    @Override
    public void signup(String username, String email, String password) {
        if (usernames.contains(username)) {
            throw new IllegalArgumentException("Username already exists. Please choose a different one.");
        }
        usernames.add(username);
        emails.add(email);
        passwords.add(password);
        saveUserCredentials();
    }

    @Override
    public boolean signIn(String username, String password) {
        for (int i = 0; i < usernames.size(); i++) {
            if ((username.equals(usernames.get(i)) || username.equals(emails.get(i))) && password.equals(passwords.get(i))) {
                currentUsername = usernames.get(i); // Set current username
                return true;
            }
        }
        return false;
    }

    @Override
    public void logout() {
        currentUsername = null; // Clear current username
    }

    @Override
    public List<Product> getProducts() {
        return Product.getAllProducts();
    }

    @Override
    public Product getProductById(int id) {
        return Product.findProductById(id);
    }

    private void saveUserCredentials() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(usernames);
            oos.writeObject(emails);
            oos.writeObject(passwords);
        } catch (IOException e) {
            throw new RuntimeException("Error saving credentials: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadUserCredentials() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            usernames.clear();
            emails.clear();
            passwords.clear();
            usernames.addAll((ArrayList<String>) ois.readObject());
            emails.addAll((ArrayList<String>) ois.readObject());
            passwords.addAll((ArrayList<String>) ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous credentials found. Starting fresh.");
        }
    }

    public void addItemToCart(OrderItem item) {
        cart.addItem(item);
    }

    public void removeItemFromCart(int productId) {
        cart.removeItem(productId);
    }

    public void updateItemQuantityInCart(int productId, int newQuantity) {
        cart.updateItemQuantity(productId, newQuantity);
    }

    public void clearCart() {
        cart.clearCart();
    }

    public List<OrderItem> viewCartItems() {
        return cart.getItems();
    }

    public double getTotalCartPrice() {
        return cart.getTotalPrice();
    }

    public void placeOrder(String customerAddress, String payementMethod) throws IOException {
        if (currentUsername == null) {
            throw new IllegalStateException("Customer is not logged in.");
        }
        Order order = new Order(currentUsername, customerAddress, payementMethod);
        for (OrderItem item : cart.getItems()) {
            order.addItem(item);
        }
        order.saveOrder();
        clearCart();
    }

    public List<Order> viewOrders() throws IOException, ClassNotFoundException {
        if (currentUsername == null) {
            throw new IllegalStateException("Customer is not logged in.");
        }
        List<Order> allOrders = Order.loadOrders();
        List<Order> customerOrders = new ArrayList<>();
        for (Order order : allOrders) {
            if (order.getCustomerUsername().equals(currentUsername)) {
                customerOrders.add(order);
            }
        }
        return customerOrders;
    }

    public void updateProductStock(int productId, int newStock) {
        Product.updateStock(productId, newStock);
    }
}

