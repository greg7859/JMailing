package org.jmailing.ui.project.history;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jmailing.model.history.History;
import org.jmailing.service.history.HistoryService;

public class HistoryLogListPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 934637452278114893L;

	private List<HistoryLogListener> listeners = new ArrayList<HistoryLogListener>();

	private JList<History> historyList;

	@Inject
	private HistoryService historyService;

	@Inject
	public void initPanel() {
		BorderLayout bl = new BorderLayout();
		setLayout(bl);
		// History List
		historyList = new JList<>();
		historyList.setCellRenderer(new HistoryListRenderer());
		historyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		historyList
				.addListSelectionListener(new HistoryListSelectionListener());
		historyList.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Histories",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(historyList, BorderLayout.CENTER);
	}

	public void refresh() {
		DefaultListModel<History> dlm = new DefaultListModel<History>();
		historyList.setModel(dlm);

		List<History> histories = historyService.load();
		Collections.sort(histories, new HistoryComparable());
		for (History history : histories) {
			dlm.addElement(history);
		}
	}

	public void addHistoryLogListener(HistoryLogListener l) {
		listeners.add(l);
	}

	public void removeHistoryLogListener(HistoryLogListener l) {
		listeners.remove(l);
	}

	private void notifyHistoryLogChanged(History h) {
		for (HistoryLogListener historyLogListener : listeners) {
			historyLogListener.historyLogChanged(h);
		}
	}

	class HistoryListRenderer extends JLabel implements
			ListCellRenderer<History> {
		/**
 * 
 */
		private static final long serialVersionUID = 3867942167379954624L;

		@Override
		public Component getListCellRendererComponent(
				JList<? extends History> list, History value, int index,
				boolean isSelected, boolean cellHasFocus) {
			String s = value.getReference();
			setText(s);
			if (isSelected) {
				setBackground(list.getSelectionBackground());
			} else {
				setBackground(list.getBackground());
			}
			setEnabled(list.isEnabled());
			setFont(list.getFont());
			setOpaque(true);
			return this;
		}
	}

	class HistoryListSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (e.getValueIsAdjusting() == false) {
				History h = historyList.getSelectedValue();
				notifyHistoryLogChanged(h);
			}
		}

	}

	class HistoryComparable implements Comparator<History> {

		@Override
		public int compare(History h1, History h2) {
			return h1.getReference().compareTo(h2.getReference());
		}
	}

}
