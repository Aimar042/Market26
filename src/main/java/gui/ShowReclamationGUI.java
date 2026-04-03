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
import domain.Report;
import domain.Sale;

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
		// TODO Etiketak Jartzea
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblReason = new JLabel();
		lblReason.setBounds(30, 67, 167, 17);
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
		
		JTextArea textAreaName = new JTextArea();
		textAreaName.setEditable(false);
		textAreaName.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		textAreaName.setBounds(195, 9, 239, 17);
		textAreaName.setText(r.getUserName() + " (" + r.isStatus() + ")");
		contentPane.add(textAreaName);
		
		lblTitle = new JLabel();
		lblTitle.setText(ResourceBundle.getBundle("Etiquetas").getString("ReportGUI.Header")); 
		lblTitle.setBounds(30, 38, 167, 17);
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
		btnRemove.setText(ResourceBundle.getBundle("Etiquetas").getString("ReportGUI.Report"));
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				BLFacade facade = MainGUI.getBusinessLogic();
				facade.removeReclamation(r.getSaleNumber(), r.getReclamationNumber());;
				btnRemove.setEnabled(false);
				btnStatus.setEnabled(false);
				lblWarning.setText(ResourceBundle.getBundle("Etiquetas").getString("ReportGUI.AllGood"));
				System.out.println("Kendu Da");
				q.updateQuery();
			}
		});
		contentPane.add(btnRemove);
		
		JLabel lblName = new JLabel("Name:"); //$NON-NLS-1$ //$NON-NLS-2$
		lblName.setBounds(30, 9, 167, 17);
		contentPane.add(lblName);
		
		btnStatus = new JButton();
		btnStatus.setText("Aldatu");
		btnStatus.setBounds(195, 168, 106, 27);
		btnStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				BLFacade facade = MainGUI.getBusinessLogic();
				Reclamation temp = facade.getReclamation(r.getReclamationNumber());
				facade.changeStatus(r.getReclamationNumber(), !temp.isStatus());;
				lblWarning.setText(ResourceBundle.getBundle("Etiquetas").getString("ReportGUI.AllGood"));
				textAreaName.setText(r.getUserName() + " (" + !temp.isStatus() + ")");
				System.out.println("Aldatu da");
				q.updateQuery();
			}
		});
		contentPane.add(btnStatus);
	}
}
