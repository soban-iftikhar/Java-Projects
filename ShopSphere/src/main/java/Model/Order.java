package Model;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Order implements Serializable {
    public static final String ORDER_FILE = "orders.txt";

    private String orderId;
    private String customerUsername;
    private String customerAddress;
    private LocalDateTime orderDateTime;
    private List<OrderItem> items;
    private String orderStatus;
    private String paymentMethod;

    public Order(String customerUsername, String customerAddress, String paymentMethod) {
        this.orderId = generateOrderId();
        this.customerUsername = customerUsername;
        this.customerAddress = customerAddress;
        this.orderDateTime = LocalDateTime.now();
        this.items = new ArrayList<>();
        this.orderStatus = "Pending";
        this.paymentMethod = paymentMethod;
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public String getOrderDate() {
        return orderDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getOrderTime() {
        return orderDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    private String generateOrderId() {
        return String.format("%08d", Math.abs(new Random().nextInt(100000000)));
    }

    public static boolean isOrderIdUnique(String orderId) throws IOException {
        List<Order> existingOrders = loadOrders();
        return existingOrders.stream().noneMatch(order -> order.getOrderId().equals(orderId));
    }

    public void saveOrder() throws IOException {
        String newOrderId;
        do {
            newOrderId = generateOrderId();
        } while (!isOrderIdUnique(newOrderId));

        this.orderId = newOrderId;

        List<Order> orders = loadOrders();
        orders.add(this);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ORDER_FILE))) {
            oos.writeObject(orders);
        }
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
    public static List<Order> loadOrders() {
        File file = new File(ORDER_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Order>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public String toString() {
        return String.format("Order[id=%s, customer=%s, date=%s, time=%s, status=%s, items=%s]",
                orderId, customerUsername, getOrderDate(), getOrderTime(), orderStatus, items);
    }
}

