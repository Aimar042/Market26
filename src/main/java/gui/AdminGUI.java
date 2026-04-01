package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class AdminGUI extends JFrame {
	
    private String userMail;
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jInsert = null;
	private JButton jReclamations;
	private JButton jWithDraw = null;
	private JButton jReports;

	protected JLabel jLabelSelectOption;


	
	/**
	 * This is the default constructor
	 */
	public AdminGUI(String email, String name) {
		super();
		
		this.userMail = email;
		this.setSize(495, 290);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		jReclamations = new JButton();
		jReclamations.setText(ResourceBundle.getBundle("Etiquetas").getString("BalanceManagerGUI.Insert"));
		jReclamations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new InsertMoneyGUI(AdminGUI.this, name);
				a.setVisible(true);
				dispose();
			}
		});
		
		jContentPane = new JPanel();
		jContentPane.setLayout(new GridLayout(2, 1, 0, 0));
		
		jReports = new JButton();
		jReports.setText(ResourceBundle.getBundle("Etiquetas").getString("BalanceManagerGUI.Withdraw"));
		jReports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new QueryReportsGUI(AdminGUI.this, name);
				a.setVisible(true);
				dispose();
			}
		});
		jContentPane.add(jReports);
		jContentPane.add(jReclamations);

		setContentPane(jContentPane);

		setTitle(userMail);
	}
	
} // @jve:decl-index=0:visual-constraint="0,0"