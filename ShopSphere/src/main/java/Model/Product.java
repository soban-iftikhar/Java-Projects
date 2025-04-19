package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Product implements Serializable {
    private static final long serialVersionUID = 5021507558065239732L;
    private static final String FILE_NAME = "products.txt";
    private static List<Product> productList = new ArrayList<>();

    private int id;
    private String name;
    private String company;
    private double price;
    private int stock;

    // Default constructor
    public Product() {
    }

    // Constructor
    public Product(int id, String name, String company, double price, int stock) {
        if (id <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive integer.");
        }
        this.id = id;
        this.name = name;
        this.company = company;
        this.price = price;
        this.stock = stock;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // Methods for File Operations
    public static void saveProductsToFile() {
        if (productList == null || productList.isEmpty()) {
            System.out.println("Product list is empty. Nothing to save.");
            return;
        }

        System.out.println("Saving " + productList.size() + " products to file."); //Added debug print statement
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(productList);
            System.out.println("Products saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving products: " + e.getMessage());
        }
    }

    public static void loadProductsFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("File not found. Initializing empty product list.");
            productList = new ArrayList<>(); // Avoid overwriting the list if file doesn't exist
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            productList = (List<Product>) ois.readObject();
            System.out.println("Loaded " + productList.size() + " products from file."); //Added debug print statement
            System.out.println("Products loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading products: " + e.getMessage());
        }
    }
    public static boolean addOrUpdateProduct(Product newProduct) throws IllegalArgumentException {
        for (Product existingProduct : productList) {
            if (existingProduct.getId() == newProduct.getId()) {
                if (existingProduct.getName().equals(newProduct.getName()) &&
                        existingProduct.getCompany().equals(newProduct.getCompany()) &&
                        Double.compare(existingProduct.getPrice(), newProduct.getPrice()) == 0) {
                    // Update stock if all other fields match
                    existingProduct.setStock(existingProduct.getStock() + newProduct.getStock());
                    saveProductsToFile();
                    return true;
                } else {
                    // Throw exception if ID exists but other fields are different
                    throw new IllegalArgumentException("A product with this ID already exists with different details.");
                }
            }
        }
        // Add new product if no matching ID found
        productList.add(newProduct);
        saveProductsToFile();
        return true;
    }
    // Method to Add a Model.Product
    public static boolean addProduct(Product product) throws IllegalArgumentException {
        if (findProductById(product.getId()) != null) {
            throw new IllegalArgumentException("A product with this ID already exists.");
        }
        productList.add(product);
        saveProductsToFile();
        return true;
    }

    // Method to Find a Model.Product by ID
    public static Product findProductById(int id) {
        for (Product product : productList) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    // Method to Get All Products
    public static List<Product> getAllProducts() {
        return productList;
    }

    public static void updateStock(int productId, int newStock) {
        for (Product product : productList) {
            if (product.getId() == productId) {
                product.setStock(newStock);
                break;
            }
        }
        saveProductsToFile();
    }

    static {
        loadProductsFromFile();
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Company: " + company + ", Price: " + price + ", Stock: " + stock;
    }
}

