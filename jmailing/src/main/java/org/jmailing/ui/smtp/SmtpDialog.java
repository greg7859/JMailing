package org.jmailing.ui.smtp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class SmtpDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4614372002431187506L;
	private JTextField hostTF;
	private JTextField portTF;
	private JTextField loginTF;
	private JTextField passwordTF;
	private JTextField fromLabelAddrTF;
	private JTextField fromEmailTF;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SmtpDialog dialog = new SmtpDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SmtpDialog() {
		setTitle("SMTP Configuration");
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		JPanel  contentPanel=createContent();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		

		getContentPane().add(createButtons(),BorderLayout.SOUTH);

	}

	private JPanel createContent() {
		JPanel content = new JPanel();
		MigLayout layout = new MigLayout("", "[right][grow,fill]");
		content.setLayout(layout);

		addSeparator(content, "SMTP");

		JLabel lblServeur = new JLabel("Host");
		content.add(lblServeur);

		hostTF = new JTextField();
		content.add(hostTF, "wrap");

		JLabel lblPort = new JLabel("Port");
		content.add(lblPort);

		portTF = new JTextField();
		content.add(portTF, "growx,wrap");

		JLabel lblIdentifiant = new JLabel("Username");
		content.add(lblIdentifiant);

		loginTF = new JTextField();
		content.add(loginTF, "growx,wrap");

		JLabel lblMotDePasse = new JLabel("Password");
		content.add(lblMotDePasse);

		passwordTF = new JTextField();
		content.add(passwordTF, "growx,wrap");


		addSeparator(content, "Email");

		JLabel lblFromLabelAddr = new JLabel("From label address");
		content.add(lblFromLabelAddr);

		fromLabelAddrTF = new JTextField();
		content.add(fromLabelAddrTF, "growx,wrap");


		JLabel lblAdresseMailDenvoi = new JLabel("From email address");
		content.add(lblAdresseMailDenvoi);

		fromEmailTF = new JTextField();
		content.add(fromEmailTF, "growx,wrap");
		
		addSeparator(content, "");
	
		return content;
	}

	private JPanel createButtons() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// Send email test
		JButton checkParamButton = new JButton("Check parameters");
		checkParamButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
		buttonPanel.add(checkParamButton);

		// Ok
		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPanel.add(okButton);
		getRootPane().setDefaultButton(okButton);

		// Cancel
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPanel.add(cancelButton);

		return buttonPanel;
	}

	private void addSeparator(JPanel p, String text) {
		p.add(new JLabel(text), "split 2, span");
		JSeparator separator = new JSeparator();
		p.add(separator, "growx, wrap");
	}

}
