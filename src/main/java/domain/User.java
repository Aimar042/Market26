package domain;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

import domain.Transaction;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String email;
	@XmlID
	@Id
	private String name;
	private String pass;
	private float balance = 50000;

	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Sale> sales=new ArrayList<Sale>();
	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Sale> bought=new ArrayList<Sale>();

	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Transaction> transactions = new ArrayList<Transaction>();

	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Cart> carts = new ArrayList<Cart>();
	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Request> requests = new ArrayList<Request>();

	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Offer> offers = new ArrayList<Offer>();

	public User() {
		super();
	}

	public User(String email, String name, String pass) {
		this.email = email;
		this.name = name;
		this.pass = pass;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return email + ";" + name + ";" + sales;
	}

	public float getBalance() {
		return this.balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}
	
	public int getFirstCartSale() {
		if(!this.getCarts().isEmpty()) return this.getCarts().get(0).getSaleNumber();
		return Integer.MIN_VALUE;
	}
	
	public Cart getCart(int cartNumber) {
		for(Cart c : carts) {
			if(c.getCartNumber() == cartNumber) {
				return c;
			}
		}
		return null;
	}

	public List<Transaction> geTransactions() {
		return transactions;
	}

	public List<Sale> getBought(){
		return this.bought;
	}

	public List<Sale> getSales(){
		return this.sales;
	}

	public List<Cart> getCarts() {
		return this.carts;
	}
	
	public List<Request> getRequests() {
		return this.requests;
	}
	
	public List<Offer> getOffers() {
		return this.offers;
	}

	/**
	 * This method creates/adds a sale to a seller
	 * 
	 * @param title           of the sale
	 * @param description     of the sale
	 * @param status
	 * @param selling         price
	 * @param publicationDate
	 * @return Sale
	 */
	public Sale addSale(String title, String description, int status, float price, Date pubDate, File file,
			boolean onSale) {

		Sale sale = new Sale(title, description, status, price, pubDate, file, this, onSale);
		sales.add(sale);
		return sale;
	}

	// TODO Erabaki kendu edo ez
	public Sale addSale(Sale sale)  {
		bought.add(sale);
		return sale;
	}
	
	public Offer addOffer(String title, String description, int status, float price, Date pubDate, File file, User u)  {
		Offer o = new Offer(title, description, status, price, pubDate, file, u);
		offers.add(o);
		return o;
	}
	
	public void createTransaction(String name, Sale s, float amount, boolean isInsert) {
		String tran = "\n" + name + "\n";
		if(s != null) {
			tran += s.getSaleNumber() + "\n";
			tran += s.getTitle() + "\n";
		}else {
			if(isInsert) {
				tran += "Money Inserted\n";
			}else {
				tran += "Money Withdrawed\n";
			}
		}

		transactions.add(new Transaction(tran, amount));
	}
	
	public void createTransaction(String name, Offer o, float amount, boolean isInsert) {
		String tran = "\n" + name + "\n";
		if(o != null) {
			tran += o.getOfferNumber() + "\n";
			tran += o.getTitle() + "\n";
		}else {
			if(isInsert) {
				tran += "Money Inserted\n";
			}else {
				tran += "Money Withdrawed\n";
			}
		}

		transactions.add(new Transaction(tran, amount));
	}

	public Cart addCart(float price, String title, int saleNumber) {
		Cart c = new Cart(price, title, saleNumber);
		carts.add(c);
		return c;
	}
	
	public Request addRequest(String title, String description) {
		Request r = new Request(title, description, this.getName());
		requests.add(r);
		return r;
	}
	
	public void removeCarts() {
		int i = carts.size();
		while(i > 0) {
			carts.remove(i - 1);
			i--;
		}
	}
	
	public void removeRequest(int requestNumber) {
		int i = 0;
		boolean found = false;
		
		while(!found && i < getRequests().size()) {
			if(getRequests().get(i).getRequestNumber() == requestNumber) {
				found = true;
				this.getRequests().remove(i);
			}
			i++;
		}
	}
	
	/**
	 * This method checks if the ride already exists for that driver
	 * 
	 * @param from the origin location
	 * @param to   the destination location
	 * @param date the date of the ride
	 * @return true if the ride exists and false in other case
	 */
	public boolean doesSaleExist(String title) {
		for (Sale s : sales)
			if (s.getTitle().compareTo(title) == 0)
				return true;
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email != other.email)
			return false;
		return true;
	}
}
