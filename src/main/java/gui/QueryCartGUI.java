package gui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Cart;
import domain.Sale;
import domain.User;


public class QueryCartGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private final JLabel jLabelCart = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("QueryCartGUI.See"));
	private JLabel jLabelTotal = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("QueryCartGUI.Total"));
	private JLabel jLabelToPay = new JLabel();

	private JButton jButtonSearch = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.Search"));
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JButton jButtonBuy = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ShowSaleGUI.Buy"));

	private JScrollPane scrollPanelProducts = new JScrollPane();
	private JTable tableProducts= new JTable();

	private DefaultTableModel tableModelProducts;

	private JFrame jFather;

	private String[] columnNamesProducts = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Title"),
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Price"),
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.PublicationDate"),

	};
	private JTextField jTextFieldSearch;
	
	private User u;

	float total;

	public QueryCartGUI(JFrame jFather, User u, QuerySalesGUI q) { // TODO Botoi berrien etiketak jartzea eta update-eko etiketak ere
		tableProducts.setEnabled(false);
		this.jFather = jFather;
		this.u = u;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.FindProducts"));
		jLabelCart.setBounds(52, 108, 427, 16);
		this.getContentPane().add(jLabelCart);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		jButtonClose.setBounds(new Rectangle(220, 379, 130, 30));

		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jFather.setVisible(true);
				q.updateQuery();
				dispose();
			}
		});

		this.getContentPane().add(jButtonClose, null);

		scrollPanelProducts.setBounds(new Rectangle(52, 137, 459, 150));

		scrollPanelProducts.setViewportView(tableProducts);
		tableModelProducts = new DefaultTableModel(null, columnNamesProducts);

		tableProducts.setModel(tableModelProducts);

		tableModelProducts.setDataVector(null, columnNamesProducts);
		tableModelProducts.setColumnCount(3); // another column added to allocate ride objects

		tableProducts.getColumnModel().getColumn(0).setPreferredWidth(200);
		tableProducts.getColumnModel().getColumn(1).setPreferredWidth(70);


		tableProducts.getColumnModel().removeColumn(tableProducts.getColumnModel().getColumn(2)); // not shown in JTable

		this.getContentPane().add(scrollPanelProducts, null);

		jTextFieldSearch = new JTextField();
		jTextFieldSearch.setBounds(52, 56, 357, 26);
		getContentPane().add(jTextFieldSearch);
		jTextFieldSearch.setColumns(10);

		 jButtonSearch.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		updateQuery();
		 	}
		 });
		jButtonSearch.setBounds(427, 56, 117, 29);
		getContentPane().add(jButtonSearch);
		
		jButtonBuy.setBounds(new Rectangle(220, 379, 130, 30));
		jButtonBuy.setBounds(220, 337, 130, 30);
		jButtonBuy.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		BLFacade facade = MainGUI.getBusinessLogic();
		 		User user = facade.getUser(u.getName());
		 		
		 		if(user.getBalance() >= total) {
		 			facade.addCartToBuyer(user.getName());
		 			updateQuery();
		 		}else {
		 			// TODO Abisatu, ez duela nahiko dirurik
		 		}
		 	}
		});
		getContentPane().add(jButtonBuy);
		
		jLabelTotal.setBounds(52, 299, 85, 16);
		getContentPane().add(jLabelTotal);

		jLabelToPay.setBounds(136, 298, 76, 17);
		getContentPane().add(jLabelToPay);


		tableProducts.addMouseListener(new MouseAdapter() {
		        @Override
		        public void mousePressed(MouseEvent mouseEvent) {

		            if(mouseEvent.getClickCount() >= 2)
		            {
				        JTable table =(JTable) mouseEvent.getSource();
		            	Point point = mouseEvent.getPoint();
				        int row = table.rowAtPoint(point);
		            	Cart c=(Cart) tableModelProducts.getValueAt(row, 2);
		            	BLFacade facade = MainGUI.getBusinessLogic();
		            	facade.removeCart(u.getName(), c.getCartNumber());
		            	updateQuery();
		            }
		        }
		 });
		
		if((u.getCarts() == null) || (u.getCarts().isEmpty())) {
			jButtonBuy.setVisible(false);
		}
	}

	public void updateQuery() {
		total = 0;
		try {
			tableModelProducts.setDataVector(null, columnNamesProducts);
			tableModelProducts.setColumnCount(3); // another column added to allocate product object

			BLFacade facade = MainGUI.getBusinessLogic();
			Date today = UtilDate.trim(new Date());
			
			List<domain.Cart> cart=facade.getUserCart(getUser().getName());
			
			if ((cart == null) || (cart.isEmpty())) {
				jLabelCart.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryCartGUI.NoProducts"));
				jButtonBuy.setVisible(false);
			}
			else {
				jLabelCart.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryCartGUI.Products"));
				jButtonBuy.setVisible(true);
				for (domain.Cart c:cart){
					Vector<Object> row = new Vector<Object>();
					row.add(c.getTitle());
					row.add(c.getPrice());
					row.add(c); // product object added in order to obtain it with tableModelProducts.getValueAt(i,2)
					tableModelProducts.addRow(row);
					total += c.getPrice();
				}
			}
			
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		tableProducts.getColumnModel().getColumn(0).setPreferredWidth(200);
		tableProducts.getColumnModel().getColumn(1).setPreferredWidth(70);

		tableProducts.getColumnModel().removeColumn(tableProducts.getColumnModel().getColumn(2)); // not shown in JTable
		
		jLabelToPay.setText(valueOf(total));
	}
	
	public User getUser() {
		return this.u;
	}
	
	public String valueOf(float total) {
		return "" + total;
	}
}
