package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Sale;

public class ReclamationGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea textReclamation;
	private JScrollPane scrollPane;
	private JLabel lblTitle;
	private JTextArea textAreaTitle;
	private JButton btnReclamation;
	private JLabel lblWarning;

	/**
	 * Launch the application.
	 *
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReportGUI frame = new ReportGUI();
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
	public ReclamationGUI(String name, Sale s) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblReason = new JLabel();
		lblReason.setBounds(30, 45, 167, 17);
		lblReason.setText(ResourceBundle.getBundle("Etiquetas").getString("ReclamationGUI.Reason"));
		contentPane.add(lblReason);
		
		JButton btnGoBack = new JButton();
		btnGoBack.setBounds(30, 202, 106, 27);
		btnGoBack.setText(ResourceBundle.getBundle("Etiquetas").getString("ReclamationGUI.Close"));
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				dispose();
			}
		});
		
		lblTitle = new JLabel();
		lblTitle.setText(ResourceBundle.getBundle("Etiquetas").getString("ReclamationGUI.Header")); 
		lblTitle.setBounds(30, 12, 167, 17);
		contentPane.add(lblTitle);
		contentPane.add(btnGoBack);
		
		textReclamation = new JTextArea();
		textReclamation.setEditable(true);

		scrollPane = new JScrollPane(textReclamation);
		scrollPane.setBounds(195, 45, 240, 100);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		contentPane.add(scrollPane); 
		
		textAreaTitle = new JTextArea();
		textAreaTitle.setEditable(true);
		textAreaTitle.setBounds(196, 12, 239, 17);
		textAreaTitle.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		contentPane.add(textAreaTitle);
		
		lblWarning = new JLabel();
		lblWarning.setBounds(154, 207, 281, 17);
		contentPane.add(lblWarning);
		
		btnReclamation = new JButton(); 
		btnReclamation.setBounds(248, 156, 128, 27);
		btnReclamation.setText(ResourceBundle.getBundle("Etiquetas").getString("ReclamationGUI.Reclamation"));
		btnReclamation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				String error = check_Field_Errors();
				if (error != null) {
					lblWarning.setText(error);
				}else {
					BLFacade facade = MainGUI.getBusinessLogic();
					Sale sale = facade.addReclamation(textAreaTitle.getText(), textReclamation.getText(), s, name);
					btnReclamation.setEnabled(false);
					lblWarning.setText(ResourceBundle.getBundle("Etiquetas").getString("ReclamationGUI.AllGood"));
					System.out.println(sale.getReclamations().size());
				}
			}
		});
		contentPane.add(btnReclamation);
	}
	
	private String check_Field_Errors() {
		if (textAreaTitle.getText().length() == 0 || textReclamation.getText().length()==0) {
			return ResourceBundle.getBundle("Etiquetas").getString("ReclamationGUI.EmptyError");
		}
		return null;
	}
}
