package org.jmailing.ui.about;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class AboutDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8491616872723200050L;

	/**
	 * Create the dialog.
	 */
	public AboutDialog() {
		setModal(true);
		setAlwaysOnTop(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("JMailing V0.1");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		getContentPane().add(lblNewLabel, BorderLayout.NORTH);
		
		JLabel lblCeLogicielPermet = new JLabel("This software helps you to send a mailing with PDF attachment file.\n The information come from CSV File");
		getContentPane().add(lblCeLogicielPermet, BorderLayout.WEST);
		
		JButton btnOk = new JButton("OK");
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				AboutDialog.this.dispose();
			}
		});
		getContentPane().add(btnOk, BorderLayout.SOUTH);

	}

}
