package gui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Report;
import domain.Request;
import domain.User;


public class QueryRequestsGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private final JLabel jLabelRequests = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("QueryRequestGUI.Requests")); 

	private JButton jButtonSearch = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QueryReportsGUI.Search")); 
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	private JScrollPane scrollPanelRequests = new JScrollPane();
	private JTable tableReports= new JTable();

	private DefaultTableModel tableModelReports;

	private JFrame jFather;

	private String[] columnNamesProducts = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QueryReportsGUI.Name"), 
			ResourceBundle.getBundle("Etiquetas").getString("QueryReportsGUI.Header"),
			ResourceBundle.getBundle("Etiquetas").getString("QueryReportsGUI.Description"),

	};
	
	private User u;
	
	private boolean isSeller;
	

	public QueryRequestsGUI(JFrame jFather, User u, boolean isSeller) { // TODO Hemen ere Etiketak aldatu behar
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		tableReports.setEnabled(false);
		this.jFather = jFather;
		this.u = u;
		this.isSeller = isSeller;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("QueryReportsGUI.Title"));
		jLabelRequests.setBounds(108, 71, 427, 16);
		this.getContentPane().add(jLabelRequests);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		jButtonClose.setBounds(new Rectangle(276, 342, 130, 30));

		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jFather.setVisible(true);
				dispose();
			}
		});		
		
		this.getContentPane().add(jButtonClose, null);

		scrollPanelRequests.setBounds(new Rectangle(108, 100, 492, 150));

		scrollPanelRequests.setViewportView(tableReports);
		tableModelReports = new DefaultTableModel(null, columnNamesProducts);

		tableReports.setModel(tableModelReports);

		tableModelReports.setDataVector(null, columnNamesProducts);
		tableModelReports.setColumnCount(3); // another column added to allocate ride objects

		tableReports.getColumnModel().getColumn(0).setPreferredWidth(150);
		tableReports.getColumnModel().getColumn(1).setPreferredWidth(342);

		tableReports.getColumnModel().removeColumn(tableReports.getColumnModel().getColumn(2)); // not shown in JTable

		this.getContentPane().add(scrollPanelRequests, null);
		
		 jButtonSearch.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		updateQuery();
		 	}
		 });
		jButtonSearch.setBounds(483, 65, 117, 29);
		getContentPane().add(jButtonSearch);
		
		tableReports.addMouseListener(new MouseAdapter() {
		        @Override
		        public void mousePressed(MouseEvent mouseEvent) {
		            
		            if(mouseEvent.getClickCount() == 2)
		            {
		            	JTable table =(JTable) mouseEvent.getSource();
		            	Point point = mouseEvent.getPoint();
				        int row = table.rowAtPoint(point);
		            	Request r = (Request) tableModelReports.getValueAt(row, 2);
		            	JFrame a;
		            		
		            	if(isSeller) {
		            		a = new CreateSaleGUI(u.getName(), false, r);
		            	}else {
		            		a = new QueryOffersGUI(QueryRequestsGUI.this, u, r, false);
		            		setVisible(false);
		            	}
		            	a.setVisible(true);
		            }
		        }
		 });
	}
	
	public void updateQuery() {
		try {
			tableModelReports.setDataVector(null, columnNamesProducts);
			tableModelReports.setColumnCount(3); // another column added to allocate product object

			BLFacade facade = MainGUI.getBusinessLogic();

			List<Request> requests;
			if(this.isSeller()) {
				requests=facade.getAllRequests(getUser().getName());
			}else {
				requests=facade.getUserRequests(getUser().getName());
			}

			
			if (requests == null) jLabelRequests.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryRequestsGUI.Empty"));
			else {
				jLabelRequests.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryRequestsGUI.ThereAre"));
				for (Request r : requests){
					Vector<Object> row = new Vector<Object>();
					row.add(r.getTitle());
					row.add(r.getDescription());
					row.add(r); // product object added in order to obtain it with tableModelProducts.getValueAt(i,2)
					tableModelReports.addRow(row);
				}
			}
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		tableReports.getColumnModel().getColumn(0).setPreferredWidth(150);
		tableReports.getColumnModel().getColumn(1).setPreferredWidth(342);
		
		tableReports.getColumnModel().removeColumn(tableReports.getColumnModel().getColumn(2)); // not shown in JTable
	}
	
	public QueryRequestsGUI getQueryRequestsGUI() {
		return this;
	}
	
	public User getUser() {
		return this.u;
	}
	
	public boolean isSeller() {
		return this.isSeller;
	}
}
