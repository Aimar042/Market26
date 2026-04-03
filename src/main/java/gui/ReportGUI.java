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

public class ReportGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea textReport;
	private JScrollPane scrollPane;
	private JLabel lblTitle;
	private JTextArea textAreaTitle;
	private JButton btnReport;
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
	public ReportGUI(String name, Sale s) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblReason = new JLabel();
		lblReason.setBounds(30, 45, 167, 17);
		lblReason.setText(ResourceBundle.getBundle("Etiquetas").getString("ReportGUI.Reason")); //$NON-NLS-1$ //$NON-NLS-2$
		contentPane.add(lblReason);
		
		JButton btnGoBack = new JButton();
		btnGoBack.setBounds(30, 202, 106, 27);
		btnGoBack.setText(ResourceBundle.getBundle("Etiquetas").getString("ReportGUI.Close"));
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				dispose();
			}
		});
		
		lblTitle = new JLabel();
		lblTitle.setText(ResourceBundle.getBundle("Etiquetas").getString("ReportGUI.Header")); 
		lblTitle.setBounds(30, 12, 167, 17);
		contentPane.add(lblTitle);
		contentPane.add(btnGoBack);
		
		textReport = new JTextArea();
		textReport.setEditable(true);

		scrollPane = new JScrollPane(textReport);
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
		
		btnReport = new JButton(); 
		btnReport.setBounds(263, 157, 106, 27);
		btnReport.setText(ResourceBundle.getBundle("Etiquetas").getString("ReportGUI.Report"));
		btnReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				String error = check_Field_Errors();
				if (error != null) {
					lblWarning.setText(error);
				}else {
					BLFacade facade = MainGUI.getBusinessLogic();
					Sale sale = facade.addReport(textAreaTitle.getText(), textReport.getText(), s, name);
					btnReport.setEnabled(false);
					lblWarning.setText(ResourceBundle.getBundle("Etiquetas").getString("ReportGUI.AllGood"));
					System.out.println(sale.getRports().size());
				}
			}
		});
		contentPane.add(btnReport);
	}
	
	private String check_Field_Errors() {
		if (textAreaTitle.getText().length() == 0 || textReport.getText().length()==0) {
			return ResourceBundle.getBundle("Etiquetas").getString("ReportGUI.EmptyError");
		}
		return null;
	}
}
