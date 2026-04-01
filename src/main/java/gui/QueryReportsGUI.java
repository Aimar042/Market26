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
import domain.Report;
import domain.Sale;
import domain.User;


public class QueryReportsGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private final JLabel jLabelReports = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.Products")); 

	private JButton jButtonSearch = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.Search")); 
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	private JScrollPane scrollPanelReports = new JScrollPane();
	private JTable tableReports= new JTable();

	private DefaultTableModel tableModelReports;

	private JFrame jFather;

	// TODO Hay que cambiar las etiquetas (todas)
	private String[] columnNamesProducts = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Title"), 
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Price"),
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.PublicationDate"),

	};
	

	public QueryReportsGUI(JFrame jFather, String name) {
		tableReports.setEnabled(false);
		this.jFather = jFather;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.FindProducts"));
		jLabelReports.setBounds(108, 71, 427, 16);
		this.getContentPane().add(jLabelReports);
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

		scrollPanelReports.setBounds(new Rectangle(108, 100, 492, 150));

		scrollPanelReports.setViewportView(tableReports);
		tableModelReports = new DefaultTableModel(null, columnNamesProducts);

		tableReports.setModel(tableModelReports);

		tableModelReports.setDataVector(null, columnNamesProducts);
		tableModelReports.setColumnCount(4); // another column added to allocate ride objects

		tableReports.getColumnModel().getColumn(0).setPreferredWidth(100);
		tableReports.getColumnModel().getColumn(1).setPreferredWidth(150);
		tableReports.getColumnModel().getColumn(2).setPreferredWidth(240);


		tableReports.getColumnModel().removeColumn(tableReports.getColumnModel().getColumn(3)); // not shown in JTable

		this.getContentPane().add(scrollPanelReports, null);
		
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
		            	Report r=(Report) tableModelReports.getValueAt(row, 3);
		            	JFrame a = new ShowReportGUI(r, getQueryReportsGUI());
		            	a.setVisible(true);
		            }
		        }
		 });
	}
	
	public void updateQuery() {
		try {
			tableModelReports.setDataVector(null, columnNamesProducts);
			tableModelReports.setColumnCount(4); // another column added to allocate product object

			BLFacade facade = MainGUI.getBusinessLogic();

			List<Report> reports=facade.getAllReports();

			
			if (reports == null) jLabelReports.setText("Hutsik");
			else {
				jLabelReports.setText("Badaude:");
				for (Report r:reports){
					Vector<Object> row = new Vector<Object>();
					row.add(r.getUserName());
					row.add(r.getHeader());
					row.add(r.getDescription());
					row.add(r); // product object added in order to obtain it with tableModelProducts.getValueAt(i,2)
					tableModelReports.addRow(row);
				}
			}
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		tableReports.getColumnModel().getColumn(0).setPreferredWidth(200);
		tableReports.getColumnModel().getColumn(1).setPreferredWidth(10);
		tableReports.getColumnModel().getColumn(1).setPreferredWidth(70);

		tableReports.getColumnModel().removeColumn(tableReports.getColumnModel().getColumn(3)); // not shown in JTable
	}
	
	public QueryReportsGUI getQueryReportsGUI() {
		return this;
	}
}
