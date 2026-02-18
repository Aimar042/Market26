package gui;

import java.awt.EventQueue;

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
		lblRegister.setBounds(67, 32, 60, 17);
		contentPane.add(lblRegister);

		JLabel lblPass1 = new JLabel("New label");
		lblPass1.setBounds(67, 83, 60, 17);
		contentPane.add(lblPass1);

		JLabel lblPass2 = new JLabel("New label");
		lblPass2.setBounds(67, 137, 60, 17);
		contentPane.add(lblPass2);

		textFieldRegister = new JTextField();
		textFieldRegister.setBounds(173, 30, 114, 21);
		contentPane.add(textFieldRegister);
		textFieldRegister.setColumns(10);

		passwordFieldPass1 = new JPasswordField();
		passwordFieldPass1.setBounds(173, 81, 114, 21);
		contentPane.add(passwordFieldPass1);

		passwordFieldPass2 = new JPasswordField();
		passwordFieldPass2.setBounds(173, 135, 114, 21);
		contentPane.add(passwordFieldPass2);

		JButton jButtonErregistratu = new JButton("New button");
		jButtonErregistratu.setBounds(164, 215, 105, 27);
		contentPane.add(jButtonErregistratu);

	}
}
