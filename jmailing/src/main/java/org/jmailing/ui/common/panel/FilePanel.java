package org.jmailing.ui.common.panel;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jmailing.config.ConfigHelper;

public class FilePanel extends JPanel {
	
	ArrayList<FilePanelListener> listeners = new ArrayList<>();

	/**
	 * 
	 */
	private static final long serialVersionUID = -2797488467729326156L;
	private JTextField filenameTxt;
	private JButton selectBtn;

	/**
	 * Create the panel.
	 */
	public FilePanel(String label, final String desc, final String extension) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		JLabel lblFile = new JLabel(label);
		lblFile.setVerticalAlignment(SwingConstants.TOP);
		lblFile.setHorizontalAlignment(SwingConstants.LEFT);
		add(lblFile);

		filenameTxt = new JTextField();
		filenameTxt.setHorizontalAlignment(SwingConstants.LEFT);
		add(filenameTxt);
		filenameTxt.setColumns(255);

		selectBtn = new JButton("Select...");
		selectBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				JFileChooser chooser = new JFileChooser(ConfigHelper
						.getProjectPathFile());
				chooser.setFileFilter(new FileNameExtensionFilter(desc,extension));
				chooser.setAcceptAllFileFilterUsed(false);
				int returnVal = chooser.showOpenDialog(FilePanel.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String filename = chooser.getSelectedFile()
							.getAbsolutePath();
					filenameTxt.setText(filename);
					for (FilePanelListener listener : listeners) {
						listener.fileSelected(filename);
					}
				}
			}
		});
		selectBtn.setVerticalAlignment(SwingConstants.TOP);
		selectBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(selectBtn);

	}
	
	public void setEnabled(boolean b) {
		selectBtn.setEnabled(b);
	}

	public void addListener(FilePanelListener listener) {
		if (listener != null) {
			listeners.add(listener);
		}
	}

	public void removeListener(FilePanelListener listener) {
		if (listener != null) {
			listeners.remove(listener);
		}
	}
}
