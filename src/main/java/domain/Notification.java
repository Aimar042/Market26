package domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Notification implements Serializable{
	@XmlID
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private String description;
	private String title;
	private String userName;
	private boolean readed;

	public Notification() {
		super();
	}

	public Notification(String title, String description, boolean readed, String userName) {
		this.description = description;
		this.title = title;
		this.readed = readed;
		this.userName = userName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHeader() {
		return title;
	}

	public void setHeader(String header) {
		this.title = header;
	}

	public boolean isStatus() {
		return readed;
	}

	public void setStatus(boolean status) {
		this.readed = status;
	}
	
	public String toString() {
		return description + "\n" + title;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
