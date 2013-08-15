package org.jmailing.ui.smtp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import org.jmailing.io.smtp.SmtpIO;
import org.jmailing.model.smtp.Smtp;

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
	private Smtp smtp = null;
	private SmtpIO smtpIO = null;

	/**
	 * Create the dialog.
	 */
	public SmtpDialog(Smtp smtp, SmtpIO smtpIO) {
		this.smtp = smtp;

		this.smtpIO = smtpIO;
		setTitle("SMTP Configuration");
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		JPanel contentPanel = createContent();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);

		getContentPane().add(createButtons(), BorderLayout.SOUTH);
	}

	private JPanel createContent() {
		JPanel content = new JPanel();
		MigLayout layout = new MigLayout("", "[right][grow,fill]");
		content.setLayout(layout);

		addSeparator(content, "SMTP Parameters");

		JLabel lblServeur = new JLabel("Host");
		content.add(lblServeur);

		hostTF = new JTextField(smtp.getHost());
		content.add(hostTF, "wrap");

		JLabel lblPort = new JLabel("Port");
		content.add(lblPort);

		portTF = new JTextField(smtp.getPort());
		content.add(portTF, "growx,wrap");

		JLabel lblUsername = new JLabel("Username");
		content.add(lblUsername);

		loginTF = new JTextField(smtp.getLogin());
		content.add(loginTF, "growx,wrap");

		JLabel lblPassword = new JLabel("Password");
		content.add(lblPassword);

		passwordTF = new JTextField(smtp.getPassword());
		content.add(passwordTF, "growx,wrap");

		addSeparator(content, "From parameters");

		JLabel lblFromLabelAddr = new JLabel("Label address");
		content.add(lblFromLabelAddr);

		fromLabelAddrTF = new JTextField(smtp.getFromAddress());
		content.add(fromLabelAddrTF, "growx,wrap");

		JLabel lblEmailAddress = new JLabel("Email address");
		content.add(lblEmailAddress);

		fromEmailTF = new JTextField(smtp.getFromLabel());
		content.add(fromEmailTF, "growx,wrap");

		addSeparator(content, "");

		return content;
	}

	private JPanel createButtons() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

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
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					copyValues();
					smtpIO.save();
					dispose();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

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

	private void copyValues() {
		smtp.setHost(hostTF.getText());
		smtp.setPort(portTF.getText());
		smtp.setLogin(loginTF.getText());
		smtp.setPassword(passwordTF.getText());
		smtp.setFromAddress(fromEmailTF.getText());
		smtp.setFromLabel(fromLabelAddrTF.getText());
	}

}
