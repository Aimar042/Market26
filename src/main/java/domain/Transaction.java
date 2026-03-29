package domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Transaction implements Serializable{
	private String description;
	private float amount;

	public Transaction() {
		super();
	}

	public Transaction(String description, float amount) {
		this.description = description;
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String toString() {
		return description + amount + "\n";
	}
}
