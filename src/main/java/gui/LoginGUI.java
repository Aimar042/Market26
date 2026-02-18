package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Seller;

public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldLogin;
	private JPasswordField passwordFieldPass;

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
		lblPass.setBounds(98, 109, 81, 14);
		contentPane.add(lblPass);
		lblPass.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Password"));

		passwordFieldPass = new JPasswordField();
		passwordFieldPass.setBounds(221, 106, 86, 20);
		contentPane.add(passwordFieldPass);
		passwordFieldPass.setColumns(10);
		
		JLabel lblWarning = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		lblWarning.setBounds(128, 153, 209, 17);
		contentPane.add(lblWarning);

		JButton jButtonLoginEgin = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Login"));
		jButtonLoginEgin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblWarning.setText("");
				String error = check_Field_Errors();
				if (error != null) {
					lblWarning.setText(error);
				}else {
					BLFacade facade = MainGUIErregistratua.getBusinessLogic();
					Seller s = facade.isLogged(textFieldLogin.getText(), passwordFieldPass.getText());
					if (s != null) {
						new MainGUIErregistratua(s.getEmail()).setVisible(true);
					}
				}
			}
		});
		jButtonLoginEgin.setBounds(150, 199, 157, 23);
		contentPane.add(jButtonLoginEgin);

	}
	
	private String check_Field_Errors() {
		if (textFieldLogin.getText().length()==0 || passwordFieldPass.getText().length()==0) {
			return ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.ErrorEmpty");
		}
		return null;
	}
}
