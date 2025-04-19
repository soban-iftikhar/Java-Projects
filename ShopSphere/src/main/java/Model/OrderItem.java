package Model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderItem implements Serializable {
    private int productId;
    private String productName;
    private BigDecimal price;
    private int quantity;

    public OrderItem(int productId, String productName, BigDecimal price, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getTotalPrice() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public String toString() {
        return String.format("OrderItem[productId=%d, productName=%s, price=%s, quantity=%d]",
                productId, productName, price, quantity);
    }

    public BigDecimal getProductPrice() {
        return price;
    }
}

