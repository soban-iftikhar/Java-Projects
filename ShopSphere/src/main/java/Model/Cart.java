package Model;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class Cart implements Serializable {
    private static final String CART_FILE = "cart.txt";

    private List<OrderItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addItem(OrderItem item) {
        for (OrderItem existingItem : items) {
            if (existingItem.getProductId() == item.getProductId()) {
                int newQuantity = existingItem.getQuantity() + item.getQuantity();
                items.set(items.indexOf(existingItem), new OrderItem(
                        existingItem.getProductId(),
                        existingItem.getProductName(),
                        existingItem.getProductPrice(),
                        newQuantity
                ));
                return;
            }
        }
        items.add(item);
    }

    public void removeItem(int productId) {
        items.removeIf(item -> item.getProductId() == productId);
    }

    public void updateItemQuantity(int productId, int newQuantity) {
        for (int i = 0; i < items.size(); i++) {
            OrderItem item = items.get(i);
            if (item.getProductId() == productId) {
                if (newQuantity <= 0) {
                    items.remove(i);
                } else {
                    items.set(i, new OrderItem(
                            item.getProductId(),
                            item.getProductName(),
                            item.getProductPrice(),
                            newQuantity
                    ));
                }
                return;
            }
        }
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void clearCart() {
        items.clear();
    }

    public double getTotalPrice() {
        return items.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .doubleValue();
    }

    public int getTotalItems() {
        return items.stream().mapToInt(OrderItem::getQuantity).sum();
    }

    public void saveCart() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CART_FILE))) {
            oos.writeObject(this);
        }
    }

    public static Cart loadCart() throws IOException, ClassNotFoundException {
        File file = new File(CART_FILE);
        if (!file.exists()) {
            return new Cart();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Cart) ois.readObject();
        }
    }

    @Override
    public String toString() {
        return "Model.Cart[items=" + items + "]";
    }
}

