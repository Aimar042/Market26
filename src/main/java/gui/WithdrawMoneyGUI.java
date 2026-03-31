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
import domain.User;

public class WithdrawMoneyGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldNumber;
	private JTextField textFieldAmount;
	private JButton btnWithdraw;
	private JLabel lblWarning;
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
	public WithdrawMoneyGUI(JFrame jFather, String name) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.jFather = jFather;
		
		JLabel lblAccountNumber = new JLabel();
		lblAccountNumber.setBounds(30, 34, 176, 17);
		lblAccountNumber.setText(ResourceBundle.getBundle("Etiquetas").getString("WithdrawMoneyGUI.CreditCard"));
		contentPane.add(lblAccountNumber);
		
		JLabel lblAmount = new JLabel();
		lblAmount.setBounds(30, 82, 153, 17);
		lblAmount.setText(ResourceBundle.getBundle("Etiquetas").getString("WithdrawMoneyGUI.Amount"));
		contentPane.add(lblAmount);
		
		textFieldNumber = new JTextField();
		textFieldNumber.setBounds(224, 32, 114, 21);
		contentPane.add(textFieldNumber);
		textFieldNumber.setColumns(10);
		
		textFieldAmount = new JTextField();
		textFieldAmount.setColumns(10);
		textFieldAmount.setBounds(201, 80, 57, 21);
		contentPane.add(textFieldAmount);
		
		lblWarning = new JLabel();
		lblWarning.setBounds(61, 134, 333, 17);
		lblWarning.setText("");
		contentPane.add(lblWarning);
		
		btnWithdraw = new JButton();
		btnWithdraw.setBounds(126, 163, 190, 27);
		btnWithdraw.setText(ResourceBundle.getBundle("Etiquetas").getString("WithdrawMoneyGUI.Withdraw"));
		btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				lblWarning.setText("");
				String error = check_Field_Errors();
				if (error != null) {
					lblWarning.setText(error);
				}else {
					BLFacade facade = MainGUI.getBusinessLogic();
					User z = facade.getUser(name);
					if(Float.parseFloat(textFieldAmount.getText()) <= z.getBalance()) {
						float balance = facade.changeBalance(name, false, Float.parseFloat(textFieldAmount.getText()));
						lblWarning.setText(ResourceBundle.getBundle("Etiquetas").getString("WithdrawMoneyGUI.AllGood") + " " + balance);
					}else {
						lblWarning.setText(ResourceBundle.getBundle("Etiquetas").getString("WithdrawMoneyGUI.BalanceError") + " " + z.getBalance());
					}
				}
			}
		});
		contentPane.add(btnWithdraw);
		
		JButton btnGoBack = new JButton("New button");
		btnGoBack.setBounds(30, 233, 106, 27);
		btnGoBack.setText(ResourceBundle.getBundle("Etiquetas").getString("WithdrawMoneyGUI.Close"));
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				setEnabled(false);
				jFather.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnGoBack);

	}
	
	private String check_Field_Errors() {
		if (textFieldNumber.getText().length() == 0 || textFieldAmount.getText().length()==0) {
			return ResourceBundle.getBundle("Etiquetas").getString("WithdrawMoneyGUI.EmptyError");
		}else if(textFieldNumber.getText().length() != 16) {
			return ResourceBundle.getBundle("Etiquetas").getString("WithdrawMoneyGUI.NumberError");	
		}else {
			try {
				Float num = Float.parseFloat(textFieldNumber.getText());
				Float am = Float.parseFloat(textFieldAmount.getText());
			}catch(java.lang.NumberFormatException e) {
				return ResourceBundle.getBundle("Etiquetas").getString("WithdrawMoneyGUI.FormatError");	
			}
		}
		return null;
	}
}
