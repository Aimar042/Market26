package gui;

import java.awt.EventQueue;
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
import domain.Seller;

public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldLogin;
	private JTextField textFieldPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textFieldLogin = new JTextField();
		textFieldLogin.setBounds(221, 57, 86, 20);
		contentPane.add(textFieldLogin);
		textFieldLogin.setColumns(10);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(98, 60, 81, 14);
		contentPane.add(lblLogin);
		lblLogin.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.UserName"));

		JLabel lblPass = new JLabel("Pass");
		lblPass.setBounds(98, 140, 81, 14);
		contentPane.add(lblPass);
		lblPass.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Password"));

		textFieldPass = new JTextField();
		textFieldPass.setBounds(221, 137, 86, 20);
		contentPane.add(textFieldPass);
		textFieldPass.setColumns(10);

		JButton jButtonLoginEgin = new JButton("Login Egin");
		jButtonLoginEgin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUIErregistratua.getBusinessLogic();
				Seller s = facade.isLogged(textFieldLogin.getText(), textFieldPass.getText());
				if (s != null) {
					new MainGUI(s.getEmail()).setVisible(true);
				}
			}
		});
		jButtonLoginEgin.setBounds(150, 199, 157, 23);
		contentPane.add(jButtonLoginEgin);

	}
}
