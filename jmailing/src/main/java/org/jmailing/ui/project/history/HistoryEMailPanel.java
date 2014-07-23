/**
 * 
 */
package org.jmailing.ui.project.history;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jmailing.model.email.EMail;
import org.jmailing.model.history.History;
import org.jmailing.service.history.HistoryService;
import org.jmailing.ui.layout.TableUtilities;

/**
 * @author gregory
 *
 */
public class HistoryEMailPanel extends JPanel implements HistoryLogListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3995659450171079410L;

	JTable emailTable;
	HistoryEMailTableModel model;
	@Inject
	private HistoryService historyService;

	@Inject
	public void HistoryEMailPanel(HistoryEMailTableModel model) {
		BorderLayout bl = new BorderLayout();
		setLayout(bl);
		emailTable = new JTable(model);
		TableUtilities.setColumnWidths(emailTable, new Insets(0, 4, 0, 4), true,
				false);
		emailTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		this.model=model;
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportView(emailTable);

		JLabel lblEMail = new JLabel("EMail List");
		scrollPane.setColumnHeaderView(lblEMail);
	}

	@Override
	public void historyLogChanged(History h) {
		if (h != null) {
			List<EMail> emails;
			try {
				emails = historyService.loadEMails(h);
				model.setEMail(emails);
			} catch (IOException e) {
				model.setEMail(null);
			}
		} else {
			model.setEMail(null);
		}
		TableUtilities.setColumnWidths(emailTable, new Insets(0, 4, 0, 4), true,
				false);
	}
}
