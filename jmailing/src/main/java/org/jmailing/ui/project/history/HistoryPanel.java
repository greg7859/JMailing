/**
 * 
 */
package org.jmailing.ui.project.history;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.inject.Inject;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * @author gregory
 * 
 */
public class HistoryPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6245259860085606467L;

	@Inject
	private HistoryLogListPanel logListPanel;
	
	@Inject
	private HistoryLogPanel logPanel;

	@Inject
	private HistoryEMailPanel detailPanel;

	public HistoryPanel() {
	}

	@Inject
	public void initPanel() {

		BorderLayout bl = new BorderLayout();
		setLayout(bl);
		
		JSplitPane splitPane = new JSplitPane();
		add(splitPane,BorderLayout.CENTER);

		splitPane.setLeftComponent(logListPanel);
		JSplitPane logContainer = new JSplitPane();
		splitPane.setRightComponent(logContainer);
		
		logContainer.setOrientation(JSplitPane.VERTICAL_SPLIT);
		logContainer.setLeftComponent(logPanel);
		logContainer.setRightComponent(detailPanel);
		
		// Listeners
		logListPanel.addHistoryLogListener(logPanel);
		logListPanel.addHistoryLogListener(detailPanel);

	}

	public void refresh() {
		logListPanel.refresh();
	}


}
