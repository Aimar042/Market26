package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.User;


public class BoughtGUI extends JFrame {
	
    private String userMail;
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jInsert = null;
	private JButton jOffers;
	private JButton jWithDraw = null;
	private JButton jSales;

	protected JLabel jLabelSelectOption;

	private JFrame jFather;


	
	/**
	 * This is the default constructor
	 */
	public BoughtGUI(JFrame jFather, User u) { // TODO Etiketak
		super();
		
		this.userMail = u.getEmail();
		this.setSize(495, 290);
		this.jFather = jFather;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		jOffers = new JButton();
		jOffers.setText(ResourceBundle.getBundle("Etiquetas").getString("BoughtGUI.offers"));
		jOffers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					JFrame a = new ShowPurchasedOffersListGUI(BoughtGUI.this, u);
					a.setVisible(true);
					setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		jContentPane = new JPanel();
		jContentPane.setLayout(new GridLayout(3, 1, 0, 0));
		jContentPane.add(jOffers);

		setContentPane(jContentPane);
		
		JButton jAtzeraEgin = new JButton();
		jAtzeraEgin.setText(ResourceBundle.getBundle("Etiquetas").getString("BalanceManagerGUI.Close"));
		jAtzeraEgin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jFather.setVisible(true);
				dispose();
		  }
	    });
		
		jSales = new JButton();
		jSales.setText(ResourceBundle.getBundle("Etiquetas").getString("BoughtGUI.sales"));
		jSales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					JFrame a = new ShowPurchasedSalesListGUI(BoughtGUI.this, u);
					a.setVisible(true);
					setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		jContentPane.add(jSales);
		jContentPane.add(jAtzeraEgin);

		setTitle(userMail);
	}
	
} // @jve:decl-index=0:visual-constraint="0,0"