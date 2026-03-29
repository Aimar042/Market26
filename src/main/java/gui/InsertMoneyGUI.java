package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;

public class InsertMoneyGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldNumber;
	private JTextField textFieldAmount;
	private JButton btnInsert;
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
	public InsertMoneyGUI(JFrame jFather, String name) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.jFather = jFather;
		
		JLabel lblAccountNumber = new JLabel();
		lblAccountNumber.setBounds(30, 34, 176, 17);
		lblAccountNumber.setText(ResourceBundle.getBundle("Etiquetas").getString("InsertMoneyGUI.CreditCard"));
		contentPane.add(lblAccountNumber);
		
		JLabel lblAmount = new JLabel();
		lblAmount.setBounds(30, 82, 159, 17);
		lblAmount.setText(ResourceBundle.getBundle("Etiquetas").getString("InsertMoneyGUI.Amount"));
		contentPane.add(lblAmount);
		
		textFieldNumber = new JTextField();
		textFieldNumber.setBounds(224, 32, 114, 21);
		contentPane.add(textFieldNumber);
		textFieldNumber.setColumns(10);
		
		textFieldAmount = new JTextField();
		textFieldAmount.setColumns(10);
		textFieldAmount.setBounds(201, 80, 57, 21);
		contentPane.add(textFieldAmount);
		
		btnInsert = new JButton();
		btnInsert.setBounds(125, 141, 190, 27);
		btnInsert.setText(ResourceBundle.getBundle("Etiquetas").getString("InsertMoneyGUI.Insert"));
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				// TODO (Zenbaki bat dela ziurtatzea eta float-aren castin-a egin)
				BLFacade facade = MainGUI.getBusinessLogic();
				float balance = facade.changeBalance(name, true, Float.parseFloat(textFieldAmount.getText()));
				System.out.println("Balance berria: " + balance);
			}
		});
		contentPane.add(btnInsert);
		
		JButton btnGoBack = new JButton();
		btnGoBack.setBounds(30, 202, 106, 27);
		btnGoBack.setText(ResourceBundle.getBundle("Etiquetas").getString("InsertMoneyGUI.Close"));
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				setEnabled(false);
				jFather.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnGoBack);

	}
}
