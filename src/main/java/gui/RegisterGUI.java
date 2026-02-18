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

public class RegisterGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldRegister;
	private JPasswordField passwordFieldPass1;
	private JPasswordField passwordFieldPass2;
	private JLabel lblWarning = new JLabel();

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
		lblRegister.setBounds(33, 40, 150, 17);
		contentPane.add(lblRegister);
		lblRegister.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.NewUser"));

		JLabel lblPass1 = new JLabel("New label");
		lblPass1.setBounds(33, 80, 150, 17);
		contentPane.add(lblPass1);
		lblPass1.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Password1"));

		JLabel lblPass2 = new JLabel("New label");
		lblPass2.setBounds(33, 120, 150, 17);
		contentPane.add(lblPass2);
		lblPass2.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Password2"));

		textFieldRegister = new JTextField();
		textFieldRegister.setBounds(201, 38, 114, 21);
		contentPane.add(textFieldRegister);
		textFieldRegister.setColumns(10);

		passwordFieldPass1 = new JPasswordField();
		passwordFieldPass1.setBounds(201, 78, 114, 21);
		contentPane.add(passwordFieldPass1);

		passwordFieldPass2 = new JPasswordField();
		passwordFieldPass2.setBounds(201, 118, 114, 21);
		contentPane.add(passwordFieldPass2);
		
		lblWarning = new JLabel();
		lblWarning.setBounds(74, 164, 282, 17);
		contentPane.add(lblWarning);

		JButton jButtonErregistratu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Register")); //$NON-NLS-1$ //$NON-NLS-2$
		jButtonErregistratu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblWarning.setText("");
				String error = check_Field_Errors();
				if (error != null) {
					lblWarning.setText(error);
				}else {
					
					BLFacade facade = MainGUIErregistratua.getBusinessLogic();
					Seller s = facade.isRegister(textFieldRegister.getText(), passwordFieldPass1.getPassword().toString());
					if (s == null) {
						s = new Seller(null, "null", "null");
						new MainGUIErregistratua(s.getEmail()).setVisible(true);
						// TODO register() BLFacade klaseko funtzioari deitu erregistratzeko eta beste leku batera eramateko
					}else {
						lblWarning.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Exist"));
					}
				}
			}
		});
		jButtonErregistratu.setBounds(175, 205, 114, 27);
		contentPane.add(jButtonErregistratu);

		

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
	
	
	//private boolean check
}
