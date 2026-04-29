package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Cart {

    @XmlID
    @Id
    @GeneratedValue
    private int cartNumber;

    private int saleNumber;
    private float price;

    public Cart(float price, int saleNumber) {
        this.price = price;
        this.saleNumber = saleNumber;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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
        return "Cart [price=" + price + "]";
    }
}
