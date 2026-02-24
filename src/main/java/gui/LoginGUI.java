package gui;

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
import domain.User;

public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldLogin;
	private JPasswordField passwordFieldPass;
	private JFrame jFather;

	/**
	 * Launch the application.
	 */
	/*
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
	*/

	/**
	 * Create the frame.
	 */
	public LoginGUI(JFrame jFather) {
		this.jFather = jFather;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textFieldLogin = new JTextField();
		textFieldLogin.setBounds(220, 26, 86, 20);
		contentPane.add(textFieldLogin);
		textFieldLogin.setColumns(10);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(97, 29, 81, 14);
		contentPane.add(lblLogin);
		lblLogin.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.UserName"));

		JLabel lblPass = new JLabel("Pass");
		lblPass.setBounds(97, 78, 81, 14);
		contentPane.add(lblPass);
		lblPass.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Password"));

		passwordFieldPass = new JPasswordField();
		passwordFieldPass.setBounds(220, 75, 86, 20);
		contentPane.add(passwordFieldPass);
		passwordFieldPass.setColumns(10);
		
		JLabel lblWarning = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		lblWarning.setBounds(127, 120, 209, 17);
		contentPane.add(lblWarning);

		JButton jButtonLoginEgin = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Login"));
		jButtonLoginEgin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblWarning.setText("");
				String error = check_Field_Errors();
				if (error != null) {
					lblWarning.setText(error);
				}else {
					BLFacade facade = MainGUI.getBusinessLogic();
					User u = facade.isLogged(textFieldLogin.getText(), passwordFieldPass.getText());
					if (u != null) {
						new MainGUISeller(u.getEmail()).setVisible(true);
						// TODO beste leku batera eramatea
						//Egina dago jada goiko TODO-a?
						jFather.dispose();
						dispose();
					}else {
						lblWarning.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.NotExist"));
					}
				}
			}
		});
		jButtonLoginEgin.setBounds(162, 200, 157, 23);
		contentPane.add(jButtonLoginEgin);
		
		JLabel lblAlready = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.LblRegister")); //$NON-NLS-1$ //$NON-NLS-2$
		lblAlready.setBounds(24, 154, 245, 17);
		contentPane.add(lblAlready);
		
		JButton jButtonRegister = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Register")); //$NON-NLS-1$ //$NON-NLS-2$
		jButtonRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new RegisterGUI(jFather);
				a.setVisible(true);
				dispose();
			}
		});
		jButtonRegister.setBounds(287, 149, 130, 27);
		contentPane.add(jButtonRegister);
		
		JButton jButtonAtzera = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Back")); //$NON-NLS-1$ //$NON-NLS-2$
		jButtonAtzera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jFather.setVisible(true);
				dispose();
			}
		});
		jButtonAtzera.setBounds(12, 218, 105, 27);
		contentPane.add(jButtonAtzera);

	}
	
	private String check_Field_Errors() {
		if (textFieldLogin.getText().length()==0 || passwordFieldPass.getText().length()==0) {
			return ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.ErrorEmpty");
		}
		return null;
	}
}
