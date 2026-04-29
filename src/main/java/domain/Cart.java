package domain;

import javax.persistence.GeneratedValue;

public class Cart {

    @GeneratedValue
    private int cartNumber;

    private int saleNumber;
    private float price;
    private int amount;

    public Cart(float price, int amount) {
        this.price = price;
        this.amount = amount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getSaleNumber() {
        return this.saleNumber;
    }

    public void setSaleNumber(int saleNumber) {
        this.saleNumber = saleNumber;
    }

    public int getCartNumber() {
        return cartNumber;
    }

    public void setCartNumber(int cartNumber) {
        this.cartNumber = cartNumber;
    }

    @Override
    public String toString() {
        return "Cart [price=" + price + ", amount=" + amount + "]";
    }
}
