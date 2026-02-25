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

public class RegisterGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldRegister;
	private JPasswordField passwordFieldPass1;
	private JPasswordField passwordFieldPass2;
	private JLabel lblWarning = new JLabel();
	private JTextField textFieldEmail;
	private JFrame jFather;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI(jFather);
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
	public RegisterGUI(JFrame jFather) {
		this.jFather = jFather;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblRegister = new JLabel("New label");
		lblRegister.setBounds(29, 39, 150, 17);
		contentPane.add(lblRegister);
		lblRegister.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.NewUser"));

		JLabel lblPass1 = new JLabel("New label");
		lblPass1.setBounds(29, 81, 150, 17);
		contentPane.add(lblPass1);
		lblPass1.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Password1"));

		JLabel lblPass2 = new JLabel("New label");
		lblPass2.setBounds(29, 110, 150, 17);
		contentPane.add(lblPass2);
		lblPass2.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Password2"));

		textFieldRegister = new JTextField();
		textFieldRegister.setBounds(197, 39, 114, 21);
		contentPane.add(textFieldRegister);
		textFieldRegister.setColumns(10);

		passwordFieldPass1 = new JPasswordField();
		passwordFieldPass1.setBounds(197, 81, 114, 21);
		contentPane.add(passwordFieldPass1);

		passwordFieldPass2 = new JPasswordField();
		passwordFieldPass2.setBounds(197, 108, 114, 21);
		contentPane.add(passwordFieldPass2);
		
		lblWarning = new JLabel();
		lblWarning.setBounds(78, 192, 282, 17);
		contentPane.add(lblWarning);

		JButton jButtonErregistratu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Register")); //$NON-NLS-1$ //$NON-NLS-2$
		jButtonErregistratu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblWarning.setText("");
				String error = check_Field_Errors();
				if (error != null) {
					lblWarning.setText(error);
				}else {
					
					BLFacade facade = MainGUI.getBusinessLogic();
					User u = facade.isRegister(textFieldRegister.getText(), passwordFieldPass1.getPassword().toString());
					if (u == null) {
						u = new User(textFieldEmail.getText(), textFieldRegister.getText(), passwordFieldPass1.getPassword().toString());
						facade.register(textFieldEmail.getText(), textFieldRegister.getText(), passwordFieldPass1.getPassword().toString());
						new MainGUIBuyer(u.getEmail()).setVisible(true);
						jFather.dispose();
						dispose();
					}else {
						lblWarning.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Exist"));
					}
				}
			}
		});
		jButtonErregistratu.setBounds(175, 221, 114, 27);
		contentPane.add(jButtonErregistratu);
		
		JLabel lblAlready = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.AlreadyRegistered")); //$NON-NLS-1$ //$NON-NLS-2$
		lblAlready.setBounds(29, 158, 222, 17);
		contentPane.add(lblAlready);
		
		JButton jButtonLogin = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Login")); //$NON-NLS-1$ //$NON-NLS-2$
		jButtonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new LoginGUI(jFather);
				a.setVisible(true);
				dispose();
			}
		});
		jButtonLogin.setBounds(292, 153, 127, 27);
		contentPane.add(jButtonLogin);
		
		JLabel lblEmail = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Email")); //$NON-NLS-1$ //$NON-NLS-2$
		lblEmail.setBounds(29, 12, 150, 17);
		contentPane.add(lblEmail);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(197, 12, 114, 21);
		contentPane.add(textFieldEmail);
		
		JButton jButtonAtzera = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Back")); //$NON-NLS-1$ //$NON-NLS-2$
		jButtonAtzera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jFather.setVisible(true);
				dispose();
			}
		});
		jButtonAtzera.setBounds(12, 221, 105, 27);
		contentPane.add(jButtonAtzera);

		

	}

	private String check_Field_Errors() {
		if (textFieldRegister.getText().length() == 0 || passwordFieldPass1.getText().length() == 0 || passwordFieldPass2.getText().length() == 0) {
			return ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.ErrorEmpty");
		}
		if (!passwordFieldPass1.getText().equals(passwordFieldPass2.getText())) {
			return ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.ErrorPass");
		}
		return null;
	}
}
