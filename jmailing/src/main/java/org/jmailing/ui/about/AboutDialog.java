package org.jmailing.ui.about;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextPane;
import javax.swing.text.html.HTMLEditorKit;

public class AboutDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8491616872723200050L;

	/**
	 * Create the dialog.
	 */
	public AboutDialog() {
		setTitle("About");
		setModal(true);
		setAlwaysOnTop(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JTextPane lblCeLogicielPermet = new JTextPane();
		lblCeLogicielPermet.setEditable(false);
		lblCeLogicielPermet.setEditorKit(new HTMLEditorKit());
		lblCeLogicielPermet.setText(
				"<h1>JMailing V0.1</h1><p>This software helps you to send a mailing with PDF attachment file.</p><p>The information come from CSV File</p>");
		getContentPane().add(lblCeLogicielPermet, BorderLayout.CENTER);

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
