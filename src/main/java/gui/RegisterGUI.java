package gui;

import java.awt.EventQueue;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class RegisterGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldRegister;
	private JPasswordField passwordFieldPass1;
	private JPasswordField passwordFieldPass2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
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
	public RegisterGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblRegister = new JLabel("New label");
		lblRegister.setBounds(33, 40, 120, 17);
		contentPane.add(lblRegister);
		lblRegister.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.NewUser"));

		JLabel lblPass1 = new JLabel("New label");
		lblPass1.setBounds(33, 80, 120, 17);
		contentPane.add(lblPass1);
		lblPass1.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Password1"));

		JLabel lblPass2 = new JLabel("New label");
		lblPass2.setBounds(33, 120, 120, 17);
		contentPane.add(lblPass2);
		lblPass2.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Password2"));

		textFieldRegister = new JTextField();
		textFieldRegister.setBounds(175, 40, 114, 21);
		contentPane.add(textFieldRegister);
		textFieldRegister.setColumns(10);

		passwordFieldPass1 = new JPasswordField();
		passwordFieldPass1.setBounds(175, 80, 114, 21);
		contentPane.add(passwordFieldPass1);

		passwordFieldPass2 = new JPasswordField();
		passwordFieldPass2.setBounds(175, 120, 114, 21);
		contentPane.add(passwordFieldPass2);

		JButton jButtonErregistratu = new JButton("New button");
		jButtonErregistratu.setBounds(175, 205, 114, 27);
		contentPane.add(jButtonErregistratu);

	}
}
