/**
 * 
 */
package org.jmailing.ui.project.history;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.inject.Inject;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.jmailing.model.history.History;
import org.jmailing.service.history.HistoryService;

/**
 * @author gregory
 *
 */
public class HistoryLogPanel extends JPanel implements HistoryLogListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -750241007655889957L;
	JTextPane textPane;

	@Inject
	private HistoryService historyService;

	@Inject
	public void initPanel() {
		BorderLayout bl = new BorderLayout();
		setLayout(bl);
		textPane = new JTextPane();
		JScrollPane scrollPane = new JScrollPane(textPane);
		add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportView(textPane);

		JLabel lblLog = new JLabel("Log");
		scrollPane.setColumnHeaderView(lblLog);
	}

	@Override
	public void historyLogChanged(History h) {
		if (h != null) {
			String log;
			try {
				log = historyService.loadLog(h);
				textPane.setText(log);
			} catch (IOException e) {
				textPane.setText(e.getMessage());
			}
		} else {
			textPane.setText("");
		}
	}
}
