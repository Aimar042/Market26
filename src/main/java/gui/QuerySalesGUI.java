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
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Cart;
import domain.Sale;
import domain.User;


public class QuerySalesGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private final JLabel jLabelProducts = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.Products"));

	private JButton jButtonSearch = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.Search"));
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JButton jButtonCart = new JButton("Cart");
	private JButton jButtonRequest = new JButton("Request");
	private JButton btnOptions;

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
	
	private JMenuItem JMenuShowRequests = new JMenuItem("Requests");
	
	private JPopupMenu popupMenu;

	public QuerySalesGUI(JFrame jFather, User u) { // TODO Botoi berrien etiketak jartzea eta JMenuItem-ena era
		tableProducts.setEnabled(false);
		this.jFather = jFather;
		this.u = u;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.FindProducts"));
		jLabelProducts.setBounds(52, 108, 427, 16);
		this.getContentPane().add(jLabelProducts);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		jButtonClose.setBounds(new Rectangle(220, 379, 130, 30));

		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jFather.setVisible(true);
				dispose();
			}
		});

		this.getContentPane().add(jButtonClose, null);

		scrollPanelProducts.setBounds(new Rectangle(52, 137, 459, 150));

		scrollPanelProducts.setViewportView(tableProducts);
		tableModelProducts = new DefaultTableModel(null, columnNamesProducts);

		tableProducts.setModel(tableModelProducts);

		tableModelProducts.setDataVector(null, columnNamesProducts);
		tableModelProducts.setColumnCount(4); // another column added to allocate ride objects

		tableProducts.getColumnModel().getColumn(0).setPreferredWidth(200);
		tableProducts.getColumnModel().getColumn(1).setPreferredWidth(10);
		tableProducts.getColumnModel().getColumn(1).setPreferredWidth(70);


		tableProducts.getColumnModel().removeColumn(tableProducts.getColumnModel().getColumn(3)); // not shown in JTable

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

		jButtonCart.setBounds(new Rectangle(220, 379, 130, 30));
		jButtonCart.setText("Cart");
		jButtonCart.setBounds(122, 319, 130, 30);
		jButtonCart.setVisible(false);
		jButtonCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		 		JFrame a = new QueryCartGUI(QuerySalesGUI.this, u, getQuerySalesGUI());
		 		a.setVisible(true);
		 		setVisible(false);
		 	}
		});
		getContentPane().add(jButtonCart);

		jButtonRequest.setBounds(new Rectangle(220, 379, 130, 30));
		jButtonRequest.setBounds(314, 319, 130, 30);
		jButtonRequest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		        JFrame a = new RequestGUI(QuerySalesGUI.this, u);
		        a.setVisible(true);
		        setVisible(false);
		    }
		});
		getContentPane().add(jButtonRequest);


		tableProducts.addMouseListener(new MouseAdapter() {
		        @Override
		        public void mousePressed(MouseEvent mouseEvent) {

		            if(mouseEvent.getClickCount() >= 2)
		            {
				        JTable table =(JTable) mouseEvent.getSource();
		            	Point point = mouseEvent.getPoint();
				        int row = table.rowAtPoint(point);
		            	Sale s=(Sale) tableModelProducts.getValueAt(row, 3);
						JFrame a;
		            	if(u != null) {
							a = new ShowSaleGUI(s, u.getName(), QuerySalesGUI.this, false);
		            	}else {
		            		a = new ShowSaleGUI(s, null, QuerySalesGUI.this, true);
		            	}
						setVisible(false);
						a.setVisible(true);
		            }
		        }
		 });
		
		btnOptions = new JButton("\u22EE");
		btnOptions.setBounds(628, 12, 37, 36);
		
		popupMenu = new JPopupMenu();
		popupMenu.add(JMenuShowRequests);
		
		JMenuShowRequests.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		        JFrame a = new QueryRequestsGUI(QuerySalesGUI.this, u, false);
		        a.setVisible(true);
		        setVisible(false);
		    }
		});

		btnOptions.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        popupMenu.show(btnOptions, 0, btnOptions.getHeight()); 
		    }
		});
		
		getContentPane().add(btnOptions);
	}

	public void updateQuery() {
		try {
			tableModelProducts.setDataVector(null, columnNamesProducts);
			tableModelProducts.setColumnCount(4); // another column added to allocate product object

			BLFacade facade = MainGUI.getBusinessLogic();
			Date today = UtilDate.trim(new Date());
			
			User user = facade.getUser(getUser().getName());
			
			List<domain.Sale> sales;
			if((user.getCarts() == null) || (user.getCarts().isEmpty())) {
				sales=facade.getPublishedSales(jTextFieldSearch.getText(),today);
				jButtonCart.setVisible(false);
			}else {
				sales=facade.getUserSales(user.getFirstCartSale());
				jButtonCart.setVisible(true);
			}
			
			if (sales.isEmpty() ) jLabelProducts.setText(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.NoProducts"));
			else jLabelProducts.setText(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.Products"));
			for (domain.Sale sale:sales){
				Vector<Object> row = new Vector<Object>();
				if((sale.getOnSale()) && (!sale.getOnCart())) {
					row.add(sale.getTitle());
					row.add(sale.getPrice());
					row.add(new SimpleDateFormat("dd-MM-yyyy").format(sale.getPublicationDate()));
					row.add(sale); // product object added in order to obtain it with tableModelProducts.getValueAt(i,2)
					tableModelProducts.addRow(row);
				}

			}
			
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		tableProducts.getColumnModel().getColumn(0).setPreferredWidth(200);
		tableProducts.getColumnModel().getColumn(1).setPreferredWidth(10);
		tableProducts.getColumnModel().getColumn(1).setPreferredWidth(70);

		tableProducts.getColumnModel().removeColumn(tableProducts.getColumnModel().getColumn(3)); // not shown in JTable
	}

	public QuerySalesGUI getQuerySalesGUI() {
		return this;
	}
	
	public User getUser() {
		return this.u;
	}
}
