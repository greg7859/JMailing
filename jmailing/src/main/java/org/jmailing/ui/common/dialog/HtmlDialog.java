package org.jmailing.ui.common.dialog;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.html.HTMLEditorKit;

public class HtmlDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4512051010014449901L;

	/**
	 * Create the dialog.
	 */
	public HtmlDialog(String html) {
		setTitle("About");
		setModal(true);
		setAlwaysOnTop(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JTextPane lblCeLogicielPermet = new JTextPane();
		lblCeLogicielPermet.setEditable(false);
		lblCeLogicielPermet.setEditorKit(new HTMLEditorKit());
		lblCeLogicielPermet.setText(html);
		JScrollPane jsp = new JScrollPane();
		jsp.setViewportView(lblCeLogicielPermet);
		getContentPane().add(jsp, BorderLayout.CENTER);

		JButton btnOk = new JButton("OK");
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				HtmlDialog.this.dispose();
			}
		});
		getContentPane().add(btnOk, BorderLayout.SOUTH);

	}
}
