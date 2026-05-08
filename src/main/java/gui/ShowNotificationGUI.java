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
import domain.Notification;
import domain.Report;

public class ShowNotificationGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea textDescription;
	private JScrollPane scrollPane;
	private JLabel lblTitle;
	private JTextArea textAreaTitle;
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
	public ShowNotificationGUI(Notification n, QueryNotificationsGUI q) { // TODO ETIKETAK
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDescription = new JLabel();
		lblDescription.setBounds(12, 42, 185, 17);
		lblDescription.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowReportGUI.Reason")); //$NON-NLS-1$ //$NON-NLS-2$
		contentPane.add(lblDescription);
		
		JButton btnGoBack = new JButton();
		btnGoBack.setBounds(30, 159, 106, 27);
		btnGoBack.setText(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				BLFacade facade = MainGUI.getBusinessLogic();
				facade.changeNotificationStatus(n);
				q.updateQuery();
				dispose();
			}
		});
		
		lblTitle = new JLabel();
		lblTitle.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowReportGUI.Title")); 
		lblTitle.setBounds(12, 13, 167, 17);
		contentPane.add(lblTitle);
		contentPane.add(btnGoBack);
		
		textDescription = new JTextArea();
		textDescription.setText(n.getDescription());
		textDescription.setEditable(false);

		scrollPane = new JScrollPane(textDescription);
		scrollPane.setBounds(195, 41, 240, 100);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		contentPane.add(scrollPane); 
		
		textAreaTitle = new JTextArea();
		textAreaTitle.setEditable(false);
		textAreaTitle.setBounds(196, 12, 239, 17);
		textAreaTitle.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		textAreaTitle.setText(n.getTitle());
		contentPane.add(textAreaTitle);
		
		lblWarning = new JLabel();
		lblWarning.setBounds(154, 207, 281, 17);
		contentPane.add(lblWarning);
	}
	
	/*
	private String check_Field_Errors() {
		if (textAreaTitle.getText().length() == 0 || textReport.getText().length()==0) {
			return ResourceBundle.getBundle("Etiquetas").getString("ReportGUI.EmptyError");
		}
		return null;
	}
	*/
}
