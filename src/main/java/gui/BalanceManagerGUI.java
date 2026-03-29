package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class BalanceManagerGUI extends JFrame {
	
    private String userMail;
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jInsert = null;
	private JButton jWithDraw = null;

	protected JLabel jLabelSelectOption;

	private JFrame jFather;


	
	/**
	 * This is the default constructor
	 */
	public BalanceManagerGUI(JFrame jFather, String email, String name) {
		super();
		
		this.userMail = email;
		this.setSize(495, 290);
		this.jFather = jFather;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		jInsert = new JButton();
		jInsert.setText(ResourceBundle.getBundle("Etiquetas").getString("BalanceManagerGUI.Insert"));
		jInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new InsertMoneyGUI(BalanceManagerGUI.this, name);
				a.setVisible(true);
				dispose();
			}
		});
		
		jContentPane = new JPanel();
		jContentPane.setLayout(new GridLayout(3, 1, 0, 0));
		
		jWithDraw = new JButton();
		jWithDraw.setText(ResourceBundle.getBundle("Etiquetas").getString("BalanceManagerGUI.Withdraw"));
		jWithDraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new WithdrawMoneyGUI(BalanceManagerGUI.this, name);
				a.setVisible(true);
				dispose();
			}
		});
		jContentPane.add(jWithDraw);
		jContentPane.add(jInsert);

		setContentPane(jContentPane);
		
		JButton jAtzeraEgin = new JButton();
		jAtzeraEgin.setText(ResourceBundle.getBundle("Etiquetas").getString("BalanceManagerGUI.Close"));
		jAtzeraEgin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jFather.setVisible(true);
				dispose();
		  }
	    });
		jContentPane.add(jAtzeraEgin);

		setTitle(userMail);
	}
	
} // @jve:decl-index=0:visual-constraint="0,0"