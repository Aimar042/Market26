package gui;

import businessLogic.BLFacade;
import domain.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WithdrawMoneyGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldNumber;
	private JTextField textFieldAmount;
	private JButton btnInsert;
	private JLabel lblWarning;

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
	public WithdrawMoneyGUI(String name) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAccountNumber = new JLabel();
		lblAccountNumber.setBounds(30, 34, 153, 17);
		lblAccountNumber.setText("Sartu kontu zenbakia:");
		contentPane.add(lblAccountNumber);
		
		JLabel lblAmount = new JLabel();
		lblAmount.setBounds(30, 82, 153, 17);
		lblAmount.setText("Zenbat atera nahi duzu?");
		contentPane.add(lblAmount);
		
		textFieldNumber = new JTextField();
		textFieldNumber.setBounds(201, 32, 114, 21);
		contentPane.add(textFieldNumber);
		textFieldNumber.setColumns(10);
		
		textFieldAmount = new JTextField();
		textFieldAmount.setColumns(10);
		textFieldAmount.setBounds(201, 80, 57, 21);
		contentPane.add(textFieldAmount);
		
		btnInsert = new JButton("New button");
		btnInsert.setBounds(172, 141, 106, 27);
		btnInsert.setText("Atera");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				// TODO (Zenbaki bat dela ziurtatzea eta float-aren castin-a egin)
				BLFacade facade = MainGUI.getBusinessLogic();
				User z = facade.getUser(name);
				if(Float.parseFloat(textFieldAmount.getText()) <= z.getBalance()) {
					float balance = facade.changeBalance(name, false, Float.parseFloat(textFieldAmount.getText()));
					System.out.println("Balance berria: " + balance);
				}else {
					System.out.println("Ez dago saldo nahikorik, dagoena: " + z.getBalance());
				}
			}
		});
		
		lblWarning = new JLabel("");
		lblWarning.setBounds(116, 112, 209, 17);
		contentPane.add(lblWarning);
		contentPane.add(btnInsert);
		
		JButton btnGoBack = new JButton("New button");
		btnGoBack.setBounds(30, 202, 106, 27);
		btnGoBack.setText("Itzuli");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				setEnabled(false);
				dispose();
			}
		});
		contentPane.add(btnGoBack);

	}
}
