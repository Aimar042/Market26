package gui;

import businessLogic.BLFacade;
import domain.Transaction;
import domain.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class TransactionGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnSearch;
	private JTextArea textTransactions;
	private JScrollPane scrollPane;
	private JFrame jFather;

	/**
	 * Launch the application.

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InsertMoneyGUI frame = new InsertMoneyGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	 */

	/**
	 * Create the frame.
	 */
	public TransactionGUI(JFrame jFather, String name) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.jFather = jFather;
		
		JLabel lblAllTransactions = new JLabel();
		lblAllTransactions.setBounds(12, 35, 167, 17);
		lblAllTransactions.setText("Transakzio osoak ikusi:");
		contentPane.add(lblAllTransactions);
		
		btnSearch = new JButton();
		btnSearch.setBounds(125, 146, 190, 27);
		btnSearch.setText("Bilatu");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				BLFacade facade = MainGUI.getBusinessLogic();
				User u = facade.getUser(name);
				System.out.println(u.getName());
				for(Transaction tran : u.geTransactions()) {
					textTransactions.append("----------------------------------------");
					textTransactions.append(tran.toString());
				}
			}
		});
		contentPane.add(btnSearch);
		
		JButton btnGoBack = new JButton();
		btnGoBack.setBounds(30, 202, 106, 27);
		btnGoBack.setText(ResourceBundle.getBundle("Etiquetas").getString("InsertMoneyGUI.Close"));
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				jFather.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnGoBack);
		
		textTransactions = new JTextArea();
		textTransactions.setEditable(false);

		scrollPane = new JScrollPane(textTransactions);
		scrollPane.setBounds(174, 34, 264, 104);
		contentPane.add(scrollPane); 

	}
}
