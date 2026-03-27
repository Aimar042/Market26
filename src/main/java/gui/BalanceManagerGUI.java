package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
	public BalanceManagerGUI(JFrame jFather, String email) {
		super();
		
		this.userMail = email;
		this.setSize(495, 290);
		this.jFather = jFather;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		jInsert = new JButton();
		jInsert.setText("Sartu Dirua");
		jInsert.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
			}
		});
		
		jContentPane = new JPanel();
		jContentPane.setLayout(new GridLayout(3, 1, 0, 0));
		
		jWithDraw = new JButton();
		jWithDraw.setText("Atera Dirua");
		jWithDraw.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
			}
		});
		jContentPane.add(jWithDraw);
		jContentPane.add(jInsert);
		
		
		setContentPane(jContentPane);
		
		JButton jAtzeraEgin = new JButton();
		jAtzeraEgin.setText("Itzuli");
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