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
import domain.Reclamation;
import domain.Report;
import domain.Sale;
import domain.User;


public class QueryReclamationsGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private final JLabel jLabelReclamations = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.Products")); 

	private JButton jButtonSearch = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.Search")); 
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	private JScrollPane scrollPanelReclamations = new JScrollPane();
	private JTable tableReclamations= new JTable();

	private DefaultTableModel tableModelReclamations;

	private JFrame jFather;

	// TODO Hay que cambiar las etiquetas (todas)
	private String[] columnNamesProducts = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Title"), 
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Price"),
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.PublicationDate"),

	};
	

	public QueryReclamationsGUI(JFrame jFather, String name) {
		tableReclamations.setEnabled(false);
		this.jFather = jFather;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.FindProducts"));
		jLabelReclamations.setBounds(108, 71, 427, 16);
		this.getContentPane().add(jLabelReclamations);
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

		scrollPanelReclamations.setBounds(new Rectangle(108, 100, 492, 150));

		scrollPanelReclamations.setViewportView(tableReclamations);
		tableModelReclamations = new DefaultTableModel(null, columnNamesProducts);

		tableReclamations.setModel(tableModelReclamations);

		tableModelReclamations.setDataVector(null, columnNamesProducts);
		tableModelReclamations.setColumnCount(4); // another column added to allocate ride objects

		tableReclamations.getColumnModel().getColumn(0).setPreferredWidth(100);
		tableReclamations.getColumnModel().getColumn(1).setPreferredWidth(150);
		tableReclamations.getColumnModel().getColumn(1).setPreferredWidth(240);


		tableReclamations.getColumnModel().removeColumn(tableReclamations.getColumnModel().getColumn(3)); // not shown in JTable

		this.getContentPane().add(scrollPanelReclamations, null);
		
		 jButtonSearch.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		updateQuery();
		 	}
		 });
		jButtonSearch.setBounds(483, 65, 117, 29);
		getContentPane().add(jButtonSearch);
		
	    
		tableReclamations.addMouseListener(new MouseAdapter() {
		        @Override
		        public void mousePressed(MouseEvent mouseEvent) {
		            
		            if(mouseEvent.getClickCount() == 2)
		            {
				        JTable table =(JTable) mouseEvent.getSource();
		            	Point point = mouseEvent.getPoint();
				        int row = table.rowAtPoint(point);
		            	Reclamation r=(Reclamation) tableModelReclamations.getValueAt(row, 3);
		            	JFrame a = new ShowReclamationGUI(r, getQueryReclamationsGUI());
		            	a.setVisible(true);
		            }
		        }
		 });
	}
	
	public void updateQuery() {
		try {
			tableModelReclamations.setDataVector(null, columnNamesProducts);
			tableModelReclamations.setColumnCount(4); // another column added to allocate product object

			BLFacade facade = MainGUI.getBusinessLogic();

			List<Reclamation> reclamations=facade.getAllReclamations();
			Reclamation r;
			
			if (reclamations == null) jLabelReclamations.setText("Hutsik");
			else {
				jLabelReclamations.setText("Badaude:");
				for (Reclamation temp:reclamations){
					Vector<Object> row = new Vector<Object>();
					r = facade.getReclamation(temp.getReclamationNumber());
					row.add(r.getUserName() + " (" + r.isStatus() + ")");
					row.add(r.getHeader());
					row.add(r.getDescription());
					row.add(r); // product object added in order to obtain it with tableModelProducts.getValueAt(i,2)
					tableModelReclamations.addRow(row);
				}
			}
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		tableReclamations.getColumnModel().removeColumn(tableReclamations.getColumnModel().getColumn(3)); // not shown in JTable
	}
	
	public QueryReclamationsGUI getQueryReclamationsGUI() {
		return this;
	}
}
