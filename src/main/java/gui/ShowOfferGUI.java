package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import businessLogic.BLFacade;
import domain.Offer;
import domain.Sale;
import domain.User;


public class ShowOfferGUI extends JFrame {
	
    File targetFile;
    BufferedImage targetImg;
    public JPanel panel_1;
    private static final int baseSize = 160;
	private static final String basePath="src/main/resources/images/";
	
	private static final long serialVersionUID = 1L;

	private JTextField fieldTitle=new JTextField();
	private JTextField fieldDescription=new JTextField();
	
	JLabel labelStatus = new JLabel(); 

	private JLabel jLabelTitle = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.Title"));
	private JLabel jLabelDescription = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Description")); 
	private JLabel jLabelProductStatus = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Status"));
	private JLabel jLabelPrice = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Price"));
	private JTextField fieldPrice = new JTextField();
	private File selectedFile;
    private String irudia;

	private JScrollPane scrollPaneEvents = new JScrollPane();
	DefaultComboBoxModel<String> statusOptions = new DefaultComboBoxModel<String>();
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel jLabelError = new JLabel();
	private JLabel statusField=new JLabel();
	private JFrame jFather;
	private JPopupMenu popupMenu;
	private JButton btnOptions;
	private JMenuItem JMenuReport;
	private JMenuItem JMenuReclamation;
	private final JButton jButtonBuy = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.Buy"));
	
	private Offer o;
	
	public ShowOfferGUI(Offer offer, String name, QueryOffersGUI q, QueryRequestsGUI qr, int requestNumber, boolean isBought) { // TODO Etiketa aldatu Erosi -> Cart-era sartu
		this.o = offer;
		this.jFather = q; 
		this.setVisible(true);
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		//this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateProductGUI.CreateProduct")); //

		fieldTitle.setText(offer.getTitle());
		fieldDescription.setText(offer.getDescription());

		fieldPrice.setText(Float.toString(offer.getPrice()));		
		
		labelStatus.setText(new SimpleDateFormat("dd-MM-yyyy").format(offer.getPublicationDate()));
		
		jLabelTitle.setBounds(new Rectangle(6, 56, 140, 20));
		
		jLabelPrice.setBounds(new Rectangle(6, 166, 101, 20));
		fieldPrice.setEditable(false);
		fieldPrice.setBounds(new Rectangle(137, 166, 60, 20));

		
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));
		
		jButtonClose.setBounds(new Rectangle(16, 268, 114, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!isBought) {
					jFather.setVisible(true);
					q.updateQuery();
				}
				dispose();
			}
		});
		
		jButtonBuy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(name != null) {
					BLFacade facade = MainGUI.getBusinessLogic();
					User u = facade.getUser(name);
					System.out.println("Saldoa: " + u.getBalance());
					if(u.getBalance() >= offer.getPrice()) {
						facade.addOfferToBuyer(u.getName(), offer, requestNumber);
						jButtonBuy.setEnabled(false);
						setJFather(qr);
						qr.updateQuery();
						System.out.println("Kendu da");
					}else {
						jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.BalanceError") + " " + u.getBalance());
					}
				}else {
					System.out.println("Erregistratu edo Login egin mesedez");
				}
				jButtonBuy.setEnabled(false);
			}
		});
		
		jButtonBuy.setBounds(178, 268, 105, 31);

		jLabelError.setBounds(new Rectangle(16, 236, 299, 20));
		jLabelError.setForeground(Color.red);
		

		this.getContentPane().add(jLabelError, null);

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jButtonBuy, null);
		this.getContentPane().add(jLabelTitle, null);
		
		
		this.getContentPane().add(jLabelPrice, null);
		this.getContentPane().add(fieldPrice, null);
		
		jLabelProductStatus.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelProductStatus.setBounds(6, 187, 140, 25);
		getContentPane().add(jLabelProductStatus);
		
		jLabelDescription.setBounds(6, 81, 140, 16);
		getContentPane().add(jLabelDescription);
		fieldTitle.setEditable(false);
		
		
		fieldTitle.setBounds(163, 54, 370, 26);
		getContentPane().add(fieldTitle);
		fieldTitle.setColumns(10);
		fieldDescription.setEditable(false);
		
		
		fieldDescription.setBounds(163, 81, 371, 73);
		getContentPane().add(fieldDescription);
		fieldDescription.setColumns(10);
		
		panel_1 = new JPanel();
		panel_1.setBounds(318, 166, 180, 160);
		getContentPane().add(panel_1);
		
		labelStatus.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		labelStatus.setBounds(6, 218, 289, 16);
		getContentPane().add(labelStatus);
		
		
		BLFacade facade = MainGUI.getBusinessLogic();
		String file=offer.getFile();
		if (file!=null) {
			Image img=facade.downloadImage(file);
			targetImg = rescale((BufferedImage)img);
			panel_1.setLayout(new BorderLayout(0, 0));
			panel_1.add(new JLabel(new ImageIcon(targetImg))); 
		}
		System.out.println("status: "+offer.getStatus());
		statusField = new JLabel(Utils.getStatus(offer.getStatus())); 
		statusField.setBounds(137, 191, 92, 16);
		getContentPane().add(statusField);
		
		
		setVisible(true);
		
		btnOptions = new JButton("\u22EE");
		btnOptions.setBounds(555, 24, 37, 36);
		
		JMenuReport = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.MenuReport"));
		JMenuReclamation = new JMenuItem(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.MenuReclamation"));
		
		popupMenu = new JPopupMenu();
		popupMenu.add(JMenuReport);
		popupMenu.add(JMenuReclamation);
		
		/*
		JMenuReport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		        JFrame a = new ReportGUI(name, o);
		        a.setVisible(true);
		    }
		});
		
		JMenuReclamation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		        JFrame a = new ReclamationGUI(name, o);
		        a.setVisible(true);
		    }
		});

		btnOptions.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        popupMenu.show(btnOptions, 0, btnOptions.getHeight()); 
		    }
		});
		
		if(name == null) {
			btnOptions.setEnabled(false);
			jButtonCart.setEnabled(false);
		}
		*/
		
		if(isBought) {
			jButtonBuy.setVisible(false);
			JMenuReport.setVisible(false);;
		}else {
			JMenuReclamation.setVisible(false);
		}
		
		// getContentPane().add(btnOptions);
	}
	
	public BufferedImage rescale(BufferedImage originalImage)
    {
        BufferedImage resizedImage = new BufferedImage(baseSize, baseSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, baseSize, baseSize, null);
        g.dispose();
        return resizedImage;
    }
	
	public Date getDate() {
		return (Date) this.o.getPublicationDate();
	}
	
	public void setJFather(QueryRequestsGUI qr) {
		this.jFather = qr;
	}
}

