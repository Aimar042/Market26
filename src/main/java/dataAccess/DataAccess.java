package dataAccess;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Admin;
import domain.Reclamation;
import domain.Report;
import domain.Sale;
import domain.Transaction;
import domain.User;
import exceptions.FileNotUploadedException;
import exceptions.MustBeLaterThanTodayException;
import exceptions.SaleAlreadyExistException;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess  {
	private  EntityManager  db;
	private  EntityManagerFactory emf;
    private static final int baseSize = 160;

	private static final String basePath="src/main/resources/images/";



	ConfigXML c=ConfigXML.getInstance();

     public DataAccess()  {
		if (c.isDatabaseInitialized()) {
			String fileName=c.getDbFilename();

			File fileToDelete= new File(fileName);
			if(fileToDelete.delete()){
				File fileToDeleteTemp= new File(fileName+"$");
				fileToDeleteTemp.delete();
				System.out.println("File deleted");
			 } else {
				 System.out.println("Operation failed");
				}
		}
		open();
		if  (c.isDatabaseInitialized()) 
			initializeDB();
		System.out.println("DataAccess created => isDatabaseLocal: "+c.isDatabaseLocal()+" isDatabaseInitialized: "+c.isDatabaseInitialized());

		close();

	}
     
    public DataAccess(EntityManager db) {
    	this.db=db;
    }

	
	
	/**
	 * This method  initializes the database with some products and sellers.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){
		
		db.getTransaction().begin();

		try { 
	       
			Admin admin1 = new Admin("a", "a", "a");
			
		    //Create sellers 
			User user1=new User("seller1@gmail.com","Aitor Fernandez", "Bibi");
			User user2=new User("seller22@gmail.com","Ane Gaztañaga", "Bibi");
			User user3=new User("seller3@gmail.com","Test Seller", "Bibi");

			//Create products
			Date today = UtilDate.trim(new Date());
		
			
			user1.addSale("futbol baloia", "oso polita, gutxi erabilita", 2, 10,  today, null,true);
			user1.addSale("salomon mendiko botak", "44 zenbakia, 3 ateraldi",2,  20,  today, null,true);
			user1.addSale("samsung 42\" telebista", "berria, erabili gabe", 1, 175,  today, null,true);


			user2.addSale("imac 27", "7 urte, dena ondo dabil", 1, 200,today, null,true);
			user2.addSale("iphone 17", "oso gutxi erabilita", 2, 400, today, null,true);
			user2.addSale("orbea mendiko bizikleta", "29\" 10 urte, mantenua behar du", 3,225, today, null,true);
			user2.addSale("polar kilor erlojua", "Vantage M, ondo dago", 3, 30, today, null,true);

			user3.addSale("sukaldeko mahaia", "1.8*0.8, 4 aulkiekin. Prezio finkoa", 3,45, today, null,true);

		
			db.persist(user1);
			db.persist(user2);
			db.persist(user3);

			db.persist(admin1);
			
			db.getTransaction().commit();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This method creates/adds a product to a seller
	 * 
	 * @param title of the product
	 * @param description of the product
	 * @param status 
	 * @param selling price
	 * @param category of a product
	 * @param publicationDate
	 * @return Product
 	 * @throws SaleAlreadyExistException if the same product already exists for the seller
	 */
	public Sale createSale(String title, String description, int status, float price,  Date pubDate, String sellerEmail, File file, boolean onSale) throws  FileNotUploadedException, MustBeLaterThanTodayException, SaleAlreadyExistException {
		

		System.out.println(">> DataAccess: createProduct=> title= "+title+" seller="+sellerEmail);
		try {
		

			if(pubDate.before(UtilDate.trim(new Date()))) {
				throw new MustBeLaterThanTodayException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.ErrorSaleMustBeLaterThanToday"));
			}
			if (file==null)
				throw new FileNotUploadedException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.ErrorFileNotUploadedException"));

			db.getTransaction().begin();
			
			User userS = db.find(User.class, sellerEmail);
			if (userS.doesSaleExist(title)) {
				db.getTransaction().commit();
				throw new SaleAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.SaleAlreadyExist"));
			}

			Sale sale = userS.addSale(title, description, status, price, pubDate, file, true);
			//next instruction can be obviated

			db.persist(userS); 
			db.getTransaction().commit();
			 System.out.println("sale stored "+sale+ " "+userS);

			

			   System.out.println("hasta aqui");

			return sale;
		} catch (NullPointerException e) {
			   e.printStackTrace();
			// TODO Auto-generated catch block
			db.getTransaction().commit();
			return null;
		}
		
		
	}
	
	/**
	 * This method retrieves all the products that contain a desc text in a title
	 * 
	 * @param desc the text to search
	 * @return collection of products that contain desc in a title
	 */
	public List<Sale> getSales(String desc) {
		System.out.println(">> DataAccess: getProducts=> from= "+desc);

		List<Sale> res = new ArrayList<Sale>();	
		TypedQuery<Sale> query = db.createQuery("SELECT s FROM Sale s WHERE s.title LIKE ?1",Sale.class);   
		query.setParameter(1, "%"+desc+"%");
		
		List<Sale> sales = query.getResultList();
	 	 for (Sale sale:sales){
		   res.add(sale);
		  }
	 	return res;
	}
	
	/**
	 * This method retrieves the products that contain a desc text in a title and the publicationDate today or before
	 * 
	 * @param desc the text to search
	 * @return collection of products that contain desc in a title
	 */
	public List<Sale> getPublishedSales(String desc, Date pubDate) {
		System.out.println(">> DataAccess: getProducts=> from= "+desc);

		List<Sale> res = new ArrayList<Sale>();	
		TypedQuery<Sale> query = db.createQuery("SELECT s FROM Sale s WHERE s.title LIKE ?1 AND s.pubDate <=?2",Sale.class);   
		query.setParameter(1, "%"+desc+"%");
		query.setParameter(2,pubDate);
		
		List<Sale> sales = query.getResultList();
	 	 for (Sale sale:sales){
		   res.add(sale);
		  }
	 	return res;
	}

public void open(){
		
		String fileName=c.getDbFilename();
		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);
			  db = emf.createEntityManager();
    	   }
		System.out.println("DataAccess opened => isDatabaseLocal: "+c.isDatabaseLocal());

		
	}

	public BufferedImage getFile(String fileName) {
		File file=new File(basePath+fileName);
		BufferedImage targetImg=null;
		try {
             targetImg = rescale(ImageIO.read(file));
        } catch (IOException ex) {
            //Logger.getLogger(MainAppFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
		return targetImg;

	}
	
	public BufferedImage rescale(BufferedImage originalImage)
    {
		System.out.println("rescale "+originalImage);
        BufferedImage resizedImage = new BufferedImage(baseSize, baseSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, baseSize, baseSize, null);
        g.dispose();
        return resizedImage;
    }
	
	public User isLogged(String log, String pass) {
		TypedQuery<User> query = db.createQuery("SELECT u FROM User u WHERE u.name=?1 AND u.pass=?2",User.class);   
		query.setParameter(1, log);
		query.setParameter(2, pass);
		if (!query.getResultList().isEmpty()) return query.getResultList().get(0);
		else return null;
	}
	
	public Admin isAdmin(String log, String pass) {
		TypedQuery<Admin> query = db.createQuery("SELECT a FROM Admin a WHERE a.name=?1 AND a.pass=?2",Admin.class);   
		query.setParameter(1, log);
		query.setParameter(2, pass);
		if (!query.getResultList().isEmpty()) return query.getResultList().get(0);
		else return null;
	}
	
	public void register (String email, String reg, String pass) {
		db.getTransaction().begin();
		// TODO
		try { 
		       
		    //Add user to DB 
			User NUser = new User(email, reg, pass);
			db.persist(NUser);
			db.getTransaction().commit();
			
			System.out.println("Db initialized");
			System.out.println("Sortu da");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public User isRegistered(String user) {
		TypedQuery<User> query = db.createQuery("SELECT u FROM User u WHERE u.name=?1", User.class);
		query.setParameter(1, user);
		if (!query.getResultList().isEmpty()) return query.getResultList().get(0);
		else return null;
	}
	
	public void close(){
		db.close();
		System.out.println("DataAcess closed");
	}
	
	public Sale getExactSale(String title, Date pubDate) {
		System.out.println(">> DataAccess: getProduct=> from= "+title);

		Sale sale;	
		TypedQuery<Sale> query = db.createQuery("SELECT s FROM Sale s WHERE s.title LIKE ?1 AND s.pubDate <=?2",Sale.class);   
		query.setParameter(1, title);
		query.setParameter(2,pubDate);
		
		sale = query.getResultList().get(0);

	 	return sale;
	}
	
	public List<Sale> getPurchasedSales(User u) {
		System.out.println(">> DataAccess: getProducts=> from= "+u.getName());

		List<Sale> res = new ArrayList<Sale>();	
		TypedQuery<User> query = db.createQuery("SELECT u FROM User u WHERE u.name=?1",User.class);
		query.setParameter(1, u.getName());
		List<Sale> purchaseds = query.getResultList().get(0).getBought();
	 	 for (Sale purchased:purchaseds){
		   res.add(purchased);
		  }
	 	return res;
	}
	
	public void addSaleToBuyer(User u, Sale s) {
		try {
			db.getTransaction().begin();

		    Sale dbs = db.find(Sale.class, s.getSaleNumber());
		    dbs.setOnSale(false);
		    
		    User dbu = db.find(User.class, u.getName());
		    dbu.addSale(dbs);
			dbu.setBalance(dbu.getBalance() - dbs.getPrice());

			Transaction tran = createTransaction(dbu.getName(), dbs, dbs.getPrice(), false);
			dbu.addTransaction(tran);

		    db.getTransaction().commit();		
		}catch (NullPointerException e) {
			   e.printStackTrace();
			db.getTransaction().commit();
		}
	}

	public User getUser(String name) {
		User dbu = null;
		try {
			db.getTransaction().begin();;

			dbu = db.find(User.class, name);
			return dbu;
		}catch (NullPointerException e) {
			e.printStackTrace();
		}finally {
			db.getTransaction().commit();
		}
		return dbu;
	}

	public float changeBalance(String name, boolean isInsert, float amount) {
		float ema = Float.MIN_VALUE;
		try {
			db.getTransaction().begin();;

			User dbu = db.find(User.class, name);
			if(isInsert) {
				dbu.setBalance(dbu.getBalance() + amount);
			}else {
				dbu.setBalance(dbu.getBalance() - amount);
			}
			Transaction tran = createTransaction(name, null, amount, isInsert);
			dbu.addTransaction(tran);

			ema = dbu.getBalance();
			
		}catch (NullPointerException e) {
			e.printStackTrace();
		}finally {
			db.getTransaction().commit();
		}

		return ema;
	}

	public Transaction createTransaction(String name, Sale s, float amount, boolean isInsert) {
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

		return new Transaction(tran, amount);
	}
	
	public Sale addReport(String header, String description, Sale s, String userName) {
		Sale dbs = null;
		try {
			db.getTransaction().begin();;

			dbs = db.find(Sale.class, s.getSaleNumber());
			dbs.addRport(header, description, userName);
			
			System.out.println("Ezarri da Report-a");
			
		}catch (NullPointerException e) {
			e.printStackTrace();
		}finally {
			db.getTransaction().commit();
		}
		
		return dbs;
	}

	public Sale addReclamation(String header, String description, Sale s, String userName) {
		Sale dbs = null;
		try {
			db.getTransaction().begin();;

			dbs = db.find(Sale.class, s.getSaleNumber());
			dbs.addReclamation(header, description, false, userName);
			
			System.out.println("Ezarri da Report-a");
			
		}catch (NullPointerException e) {
			e.printStackTrace();
		}finally {
			db.getTransaction().commit();
		}
		
		return dbs;
	}
	
	public List<Report> getAllReports() {
	    TypedQuery<Report> query = db.createQuery("SELECT r FROM Report r", Report.class);
	    if(!query.getResultList().isEmpty()) return query.getResultList();
	    return null;
	}
	
	public void removeReport(int saleNumber, int reportNumber) {
		try {
			db.getTransaction().begin();;

			Sale dbs = db.find(Sale.class, saleNumber);
			Report dbr = db.find(Report.class, reportNumber);
			
			dbs.getRports().remove(dbr);
			
			System.out.println("Kendu da Report-a");
			
		}catch (NullPointerException e) {
			e.printStackTrace();
		}finally {
			db.getTransaction().commit();
		}
	}
	
	public List<Reclamation> getAllReclamations() {
	    TypedQuery<Reclamation> query = db.createQuery("SELECT r FROM Reclamation r", Reclamation.class);
	    if(!query.getResultList().isEmpty()) return query.getResultList();
	    return null;
	}
	
	public void removeReclamaton(int saleNumber, int reclamationNumber) {
		try {
			db.getTransaction().begin();;

			Sale dbs = db.find(Sale.class, saleNumber);
			Reclamation dbr = db.find(Reclamation.class, reclamationNumber);
			
			dbs.getReclamations().remove(dbr);
			
			System.out.println("Kendu da Reclamation-a");
			
		}catch (NullPointerException e) {
			e.printStackTrace();
		}finally {
			db.getTransaction().commit();
		}
	}
	
	public Reclamation changeStatus(int reclamationNumber, boolean status) {
		Reclamation dbr = null;
		try {
			db.getTransaction().begin();;
			
			dbr = db.find(Reclamation.class, reclamationNumber);
			dbr.setStatus(status);
			
			System.out.println("Aldatu da Reclamation-aren status: " + dbr.isStatus());
			
		}catch (NullPointerException e) {
			e.printStackTrace();
		}finally {
			db.getTransaction().commit();
		}
		
		return dbr;
	}
	
	public Reclamation getReclamation(int reclamationNumber) {
		Reclamation dbr = null;
		try {
			db.getTransaction().begin();;
			
			dbr = db.find(Reclamation.class, reclamationNumber);
			
		}catch (NullPointerException e) {
			e.printStackTrace();
		}finally {
			db.getTransaction().commit();
		}
		
		return dbr;
	}
	
}
