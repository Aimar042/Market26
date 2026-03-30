package domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Report implements Serializable{
	private String description;
	private String header;

	public Report() {
		super();
	}

	public Report(String description, String amount) {
		this.description = description;
		this.header = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAmount() {
		return header;
	}

	public void setAmount(String amount) {
		this.header = amount;
	}

	public String toString() {
		return description + "\n" + header;
	}
}
