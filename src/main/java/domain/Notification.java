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
	private int notiNumber;
	private boolean readed;
	private String description;
	private String title;
	private String userName;

	public Notification() {
		super();
	}

	public Notification(String title, String description, String userName, boolean readed) {
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

	public int getNotiNUmber() {
		return notiNumber;
	}

	public void setNotiNUmber(int notiNUmber) {
		this.notiNumber = notiNUmber;
	}

	public boolean isReaded() {
		return readed;
	}

	public void setReaded(boolean readed) {
		this.readed = readed;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
