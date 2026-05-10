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
import domain.Notification;


public class QueryNotificationsGUI extends JFrame { 	
	private static final long serialVersionUID = 1L;
	private final JLabel jLabelNotifications = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("QueryNotificationsGUI.Sakatu")); 

	private JButton jButtonSearch = new JButton(ResourceBundle.getBundle("Etiquetas").getString("See")); 
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	private JScrollPane scrollPanelReports = new JScrollPane();
	private JTable tableReports= new JTable();

	private DefaultTableModel tableModelReports;

	private JFrame jFather;

	private String[] columnNamesProducts = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QueryNotificationsGUI.Header"), 
			ResourceBundle.getBundle("Etiquetas").getString("QueryNotificationsGUI.Description"),
	};
	
	private String name;

	public QueryNotificationsGUI(JFrame jFather, String name) {
		tableReports.setEnabled(false);
		this.jFather = jFather;
		this.name = name;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("QueryNotificationsGUI.Title"));
		jLabelNotifications.setBounds(108, 71, 427, 16);
		this.getContentPane().add(jLabelNotifications);
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
		tableModelReports.setColumnCount(3); // another column added to allocate ride objects

		tableReports.getColumnModel().getColumn(0).setPreferredWidth(150);
		tableReports.getColumnModel().getColumn(1).setPreferredWidth(342);


		tableReports.getColumnModel().removeColumn(tableReports.getColumnModel().getColumn(2)); // not shown in JTable

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
		            	Notification n=(Notification) tableModelReports.getValueAt(row, 2);
		            	JFrame a = new ShowNotificationGUI(n, getQueryNotificationsGUI());
		            	a.setVisible(true);
		            }
		        }
		 });
	}
	
	public void updateQuery() {
		try {
			tableModelReports.setDataVector(null, columnNamesProducts);
			tableModelReports.setColumnCount(3); // another column added to allocate product object

			tableReports.getColumnModel().getColumn(0).setPreferredWidth(150);
			tableReports.getColumnModel().getColumn(1).setPreferredWidth(342);
			
			BLFacade facade = MainGUI.getBusinessLogic();

			List<Notification> notifications=facade.getUserNotifications(this.getName());

			
			if (notifications.isEmpty()) jLabelNotifications.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryNotificationsGUI.Empty"));
			else {
				jLabelNotifications.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryNotificationsGUI.ThereAre"));
				for (Notification n:notifications){
					if(!n.isReaded()) {
						Vector<Object> row = new Vector<Object>();
						row.add(n.getTitle());
						row.add(n.getDescription());
						row.add(n); // product object added in order to obtain it with tableModelProducts.getValueAt(i,2)
						tableModelReports.addRow(row);
					}
				}
			}
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		tableReports.getColumnModel().removeColumn(tableReports.getColumnModel().getColumn(2)); // not shown in JTable
	}
	
	public QueryNotificationsGUI getQueryNotificationsGUI() {
		return this;
	}
	
	public String getName() {
		return this.name;
	}
}
