package businessLogic;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.jws.WebMethod;
import javax.jws.WebService;

import dataAccess.DataAccess;
import domain.Sale;
import domain.User;
import exceptions.FileNotUploadedException;
import exceptions.MustBeLaterThanTodayException;
import exceptions.SaleAlreadyExistException;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation implements BLFacade {
	private static final int baseSize = 160;

	private static final String basePath = "src/main/resources/images/";
	DataAccess dbManager;

	public BLFacadeImplementation() {
		System.out.println("Creating BLFacadeImplementation instance");
		dbManager = new DataAccess();
	}

	public BLFacadeImplementation(DataAccess da) {
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		dbManager = da;
	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public Sale createSale(String title, String description, int status, float price, Date pubDate, String sellerEmail,
			File file) throws FileNotUploadedException, MustBeLaterThanTodayException, SaleAlreadyExistException {
		dbManager.open();
		Sale product = dbManager.createSale(title, description, status, price, pubDate, sellerEmail, file);
		dbManager.close();
		return product;
	};

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public List<Sale> getSales(String desc) {
		dbManager.open();
		List<Sale> rides = dbManager.getSales(desc);
		dbManager.close();
		return rides;
	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public List<Sale> getPublishedSales(String desc, Date pubDate) {
		dbManager.open();
		List<Sale> rides = dbManager.getPublishedSales(desc, pubDate);
		dbManager.close();
		return rides;
	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public BufferedImage getFile(String fileName) {
		return dbManager.getFile(fileName);
	}

	public void close() {
		DataAccess dB4oManager = new DataAccess();
		dB4oManager.close();

	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public void initializeBD() {
		dbManager.open();
		dbManager.initializeDB();
		dbManager.close();
	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public Image downloadImage(String imageName) {
		File image = new File(basePath + imageName);
		try {
			return ImageIO.read(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@WebMethod
	public User isLogged(String log, String pass) {
		dbManager.open();
		User u = dbManager.isLogged(log, pass);
		dbManager.close();
		return u;
	}

	@WebMethod
	public User isRegister(String reg, String pass1) {
		dbManager.open();
		User u = dbManager.isRegistered(reg);
		dbManager.close();
		return u;
	}
	
	@WebMethod
	public void register(String email, String reg, String pass) {
		dbManager.open();
		dbManager.register(email, reg, pass);
		dbManager.close();
	}
	
	@WebMethod
	public Sale getExactSale(String title, Date pubDate) {
		dbManager.open();
		Sale sale = dbManager.getExactSale(title, pubDate);
		dbManager.close();
		return sale;
	}

}
