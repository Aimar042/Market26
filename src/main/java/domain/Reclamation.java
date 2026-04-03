package domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Reclamation implements Serializable{
	@XmlID
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private int reclamationNumber;
	private int saleNumber;
	private String description;
	private String header;
	private String userName;
	private boolean status;

	public Reclamation() {
		super();
	}

	public Reclamation(String header, String description, boolean status, String userName, int saleNumber) {
		this.description = description;
		this.header = header;
		this.status = status;
		this.userName = userName;
		this.saleNumber = saleNumber;
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

	public int getReclamationNumber() {
		return reclamationNumber;
	}

	public void setReclamationNumber(int reclamationNumber) {
		this.reclamationNumber = reclamationNumber;
	}

	public int getSaleNumber() {
		return saleNumber;
	}

	public void setSaleNumber(int saleNumber) {
		this.saleNumber = saleNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
