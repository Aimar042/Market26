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
import domain.Reclamation;


public class NotificationGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private final JLabel jLabelNotifications = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("QueryReclamationsGUI.Reclamation")); 

	private JButton jButtonSearch = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QueryReclamationsGUI.Search")); 
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	private JScrollPane scrollPanelNotifications = new JScrollPane();
	private JTable tableNotifications= new JTable();

	private DefaultTableModel tableModelReclamations;

	private JFrame jFather;

	private String[] columnNamesProducts = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QueryReclamationsGUI.Name"), 
			ResourceBundle.getBundle("Etiquetas").getString("QueryReclamationsGUI.Header"),
			ResourceBundle.getBundle("Etiquetas").getString("QueryReclamationsGUI.Description"),

	};
	

	public NotificationGUI(JFrame jFather, String name) {
		tableNotifications.setEnabled(false);
		this.jFather = jFather;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("QueryReclamationsGUI.Title"));
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

		scrollPanelNotifications.setBounds(new Rectangle(108, 100, 492, 150));

		scrollPanelNotifications.setViewportView(tableNotifications);
		tableModelReclamations = new DefaultTableModel(null, columnNamesProducts);

		tableNotifications.setModel(tableModelReclamations);

		tableModelReclamations.setDataVector(null, columnNamesProducts);
		tableModelReclamations.setColumnCount(4); // another column added to allocate ride objects

		tableNotifications.getColumnModel().getColumn(0).setPreferredWidth(100);
		tableNotifications.getColumnModel().getColumn(1).setPreferredWidth(150);
		tableNotifications.getColumnModel().getColumn(1).setPreferredWidth(240);


		tableNotifications.getColumnModel().removeColumn(tableNotifications.getColumnModel().getColumn(3)); // not shown in JTable

		this.getContentPane().add(scrollPanelNotifications, null);
		
		 jButtonSearch.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		updateQuery();
		 	}
		 });
		jButtonSearch.setBounds(483, 65, 117, 29);
		getContentPane().add(jButtonSearch);
		
	    
		tableNotifications.addMouseListener(new MouseAdapter() {
		        @Override
		        public void mousePressed(MouseEvent mouseEvent) {
		            
		            if(mouseEvent.getClickCount() == 2)
		            {
				        JTable table =(JTable) mouseEvent.getSource();
		            	Point point = mouseEvent.getPoint();
				        int row = table.rowAtPoint(point);
		            	Reclamation r=(Reclamation) tableModelReclamations.getValueAt(row, 3);
		            	JFrame a = new NotificationGUI(r, NotificationGUI());
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
			
			if (reclamations == null) jLabelNotifications.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryReclamationsGUI.Empty"));
			else {
				jLabelNotifications.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryReclamationsGUI.ThereAre"));
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

		tableNotifications.getColumnModel().removeColumn(tableNotifications.getColumnModel().getColumn(3)); // not shown in JTable
	}
	
	public NotificationGUI getQueryReclamationsGUI() {
		return this;
	}
}
