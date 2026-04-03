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
import domain.Reclamation;

public class ShowReclamationGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea textReclamation;
	private JScrollPane scrollPane;
	private JLabel lblTitle;
	private JTextArea textAreaTitle;
	private JButton btnRemove;
	private JLabel lblWarning;
	private JButton btnStatus;

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
	public ShowReclamationGUI(Reclamation r, QueryReclamationsGUI q) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblReason = new JLabel();
		lblReason.setBounds(12, 67, 185, 17);
		lblReason.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowReclamationGUI.Reason"));
		contentPane.add(lblReason);
		
		JButton btnGoBack = new JButton();
		btnGoBack.setBounds(30, 202, 106, 27);
		btnGoBack.setText(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				dispose();
			}
		});
		
		JTextArea textAreaName = new JTextArea();
		textAreaName.setEditable(false);
		textAreaName.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		textAreaName.setBounds(195, 9, 239, 17);
		textAreaName.setText(r.getUserName() + " (" + r.isStatus() + ")");
		contentPane.add(textAreaName);
		
		lblTitle = new JLabel();
		lblTitle.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowReclamationGUI.Title")); 
		lblTitle.setBounds(12, 39, 167, 17);
		contentPane.add(lblTitle);
		contentPane.add(btnGoBack);
		
		textReclamation = new JTextArea();
		textReclamation.setText(r.getDescription());
		textReclamation.setEditable(false);

		scrollPane = new JScrollPane(textReclamation);
		scrollPane.setBounds(195, 67, 240, 80);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		contentPane.add(scrollPane); 
		
		textAreaTitle = new JTextArea();
		textAreaTitle.setEditable(false);
		textAreaTitle.setBounds(196, 38, 239, 17);
		textAreaTitle.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		textAreaTitle.setText(r.getHeader());
		contentPane.add(textAreaTitle);
		
		lblWarning = new JLabel();
		lblWarning.setBounds(154, 207, 281, 17);
		contentPane.add(lblWarning);
		
		btnRemove = new JButton(); 
		btnRemove.setBounds(328, 168, 106, 27);
		btnRemove.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowReclamationGUI.Remove"));
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				BLFacade facade = MainGUI.getBusinessLogic();
				facade.removeReclamation(r.getSaleNumber(), r.getReclamationNumber());;
				btnRemove.setEnabled(false);
				btnStatus.setEnabled(false);
				lblWarning.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowReclamationGUI.WarningR"));
				System.out.println("Kendu Da");
				q.updateQuery();
			}
		});
		contentPane.add(btnRemove);
		
		JLabel lblName = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ShowReclamationGUI.Name"));
		lblName.setBounds(12, 10, 167, 17);
		contentPane.add(lblName);
		
		btnStatus = new JButton();
		btnStatus.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowReclamationGUI.Status"));
		btnStatus.setBounds(154, 168, 147, 27);
		btnStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				BLFacade facade = MainGUI.getBusinessLogic();
				Reclamation temp = facade.getReclamation(r.getReclamationNumber());
				facade.changeStatus(r.getReclamationNumber(), !temp.isStatus());;
				lblWarning.setText(ResourceBundle.getBundle("Etiquetas").getString("ShowReclamationGUI.WarningS"));
				textAreaName.setText(r.getUserName() + " (" + !temp.isStatus() + ")");
				System.out.println("Aldatu da");
				q.updateQuery();
			}
		});
		contentPane.add(btnStatus);
	}
}
