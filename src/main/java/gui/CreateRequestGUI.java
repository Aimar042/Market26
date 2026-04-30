package gui;

import businessLogic.BLFacade;
import com.toedter.calendar.JCalendar;
import configuration.UtilDate;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class CreateRequestGUI extends JFrame {
	
    File targetFile;
    BufferedImage targetImg;
    String encodedfile = null;
    private static final int baseSize = 128;
	private static final String basePath="src/main/resources/images/";

	
	private static final long serialVersionUID = 1L;

	private String sellerMail;
	private JTextField fieldTitle=new JTextField();
	private JTextField fieldDescription=new JTextField();
	
	private JLabel jLabelTitle = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRequestGUI.Title"));
	private JLabel jLabelDescription = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRequestGUI.Description")); 

	private JScrollPane scrollPaneEvents = new JScrollPane();
	DefaultComboBoxModel<String> statusOptions = new DefaultComboBoxModel<String>();
	List<String> status;


	private JButton jButtonCreate = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.CreateProduct"));
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel jLabelMsg = new JLabel();
	private JLabel jLabelError = new JLabel();
	private JFrame thisFrame;
	private final JButton btnNewButton_2 = new JButton("grabar Imagen"); //$NON-NLS-1$ //$NON-NLS-2$

	public CreateRequestGUI(String mail) {

		thisFrame=this;
		this.sellerMail=mail;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.CreateProduct"));

		jLabelTitle.setBounds(new Rectangle(16, 23, 92, 20));

		
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));
		jButtonCreate.setFont(new Font("Lucida Grande", Font.BOLD, 15));

		jButtonCreate.setBounds(new Rectangle(198, 269, 216, 41));

		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jLabelMsg.setText("");
				String error=check_fields_Errors();
				if (error!=null) 
					jLabelMsg.setText(error);
				else
					try {
						BLFacade facade = MainGUI.getBusinessLogic();
						facade.createRequest(fieldTitle.getText(), fieldDescription.getText(), sellerMail, targetFile, true);
						jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.ProductCreated"));
					
					} catch (Exception e1) {

						// TODO Auto-generated catch block
						jLabelMsg.setText(e1.getMessage());
					}
			}
		});
		jButtonClose.setBounds(new Rectangle(467, 275, 101, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.setVisible(false);			}
		});

		jLabelMsg.setBounds(new Rectangle(26, 275, 377, 20));
		jLabelMsg.setForeground(Color.red);

		jLabelError.setBounds(new Rectangle(16, 275, 384, 20));
		jLabelError.setForeground(Color.red);
		
	    status=Utils.getStatus();
		for(String s:status) statusOptions.addElement(s);

		this.getContentPane().add(jLabelMsg, null);
		this.getContentPane().add(jLabelError, null);

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jButtonCreate, null);
		this.getContentPane().add(jLabelTitle, null);
		
		jLabelDescription.setBounds(12, 74, 109, 16);
		getContentPane().add(jLabelDescription);
		
		
		fieldTitle.setBounds(109, 21, 250, 26);
		getContentPane().add(fieldTitle);
		fieldTitle.setColumns(10);
		
		
		fieldDescription.setBounds(109, 74, 404, 161);
		getContentPane().add(fieldDescription);
		fieldDescription.setColumns(10);
		
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				try {
					BufferedImage img = ImageIO.read(targetFile);
					
				    File outputfile = new File(basePath+targetFile.getName());

				   ImageIO.write(img, "png", outputfile);  // ignore returned boolean
				   System.out.println("file stored "+img);
				} catch(IOException ex) {
				 //System.out.println("Write error for " + outputfile.getPath()  ": " + ex.getMessage());
				  }
				
			}
		});
		btnNewButton_2.setBounds(137, 350, 117, 29);
		
		getContentPane().add(btnNewButton_2);
		
	}	 

	public BufferedImage rescale(BufferedImage originalImage)
    {
        BufferedImage resizedImage = new BufferedImage(baseSize, baseSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, baseSize, baseSize, null);
        g.dispose();
        return resizedImage;
    }
	private String check_fields_Errors() {
		
		try {
			if ((fieldTitle.getText().length()==0) || (fieldDescription.getText().length()==0)  || (jTextFieldPrice.getText().length()==0))
				return ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.ErrorQuery");
			else {

				// trigger an exception if the introduced string is not a number
					float price = Float.parseFloat(jTextFieldPrice.getText());
					if (price <= 0) 
						return ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.PriceMustBeGreaterThan0");
					
					else 
						return null;
			}
		} catch (java.lang.NumberFormatException e1) {

			return  ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.ErrorNumber");		
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;

		}
	}
	
	
public  String encodeFileToBase64Binary(File file){
        try {
            @SuppressWarnings("resource")
			FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile=new String(Base64.getEncoder().encode(bytes));

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return encodedfile;
    }
}
