package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Software Engineering teachers
 */
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import businessLogic.BLFacade;
import domain.User;


public class MainGUIBuyer extends JFrame {
	
    private String userMail;
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonChangeMode = null;
	private JButton jButtonQueryQueries = null;

    private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade facade){
		appFacadeInterface=facade;
	}
	protected JLabel jLabelSelectOption;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton jButtonShowPurchased;
	
	/**
	 * This is the default constructor
	 */
	public MainGUIBuyer(String mail, User u) {
		super();

		this.userMail=mail;
		
		this.setSize(495, 290);
		jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Buyer"));
		jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSelectOption.setForeground(Color.BLACK);
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		
		jButtonChangeMode = new JButton();
		jButtonChangeMode.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.ChangeToSeller"));
		jButtonChangeMode.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new MainGUISeller(userMail, u);
				a.setVisible(true);
				dispose();
			}
		});
		
		jContentPane = new JPanel();
		jContentPane.setLayout(new GridLayout(5, 1, 0, 0));
		jContentPane.add(jLabelSelectOption);
		
		jButtonQueryQueries = new JButton();
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QuerySales"));
		jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new QuerySalesGUI(u);

				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonQueryQueries);
		jContentPane.add(jButtonChangeMode);
		
		
		setContentPane(jContentPane);
		
		jButtonShowPurchased = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUIBuyer.ShowPurchased")); //$NON-NLS-1$ //$NON-NLS-2$
		jButtonShowPurchased.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new ShowPurchasedListGUI(u);
				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonShowPurchased);
		
		rdbtnNewRadioButton = new JRadioButton("English");
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("en"));
				paintAgain();				}
		});
		buttonGroup.add(rdbtnNewRadioButton);
		
		rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
		rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Locale.setDefault(new Locale("eus"));
				paintAgain();				}
		});
		buttonGroup.add(rdbtnNewRadioButton_1);
		
		rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
		rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("es"));
				paintAgain();
			}
		});
		buttonGroup.add(rdbtnNewRadioButton_2);
		
			panel = new JPanel();
			panel.add(rdbtnNewRadioButton_1);
			panel.add(rdbtnNewRadioButton_2);
			panel.add(rdbtnNewRadioButton);
			jContentPane.add(panel);
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle") +": "+userMail);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});
	}
	
	private void paintAgain() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QuerySales"));
		jButtonChangeMode.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.ChangeToSeller"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle")+ ": "+userMail);
	}
	
} // @jve:decl-index=0:visual-constraint="0,0"