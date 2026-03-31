package domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Reclamation implements Serializable{
	private String description;
	private String header;
	private boolean status;

	public Reclamation() {
		super();
	}

	public Reclamation(String description, String amount, boolean status) {
		this.description = description;
		this.header = amount;
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public String toString() {
		return description + "\n" + header;
	}
}
