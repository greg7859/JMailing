package org.jmailing.ui.project.campaign;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.apache.commons.lang.StringUtils;
import org.jmailing.injector.annotation.Excel;
import org.jmailing.injector.annotation.Pdf;
import org.jmailing.io.csv.DataFileReader;
import org.jmailing.model.source.Data;
import org.jmailing.service.mailing.MailingGenerator;
import org.jmailing.service.mailing.MailingGeneratorListener;
import org.jmailing.ui.common.panel.FilePanel;
import org.jmailing.ui.common.panel.FilePanelListener;

public class CampaignPanel extends JPanel implements FilePanelListener,
		MailingGeneratorListener {
	@Inject
	private DataFileReader reader;

	@Inject
	@Excel
	private FilePanel srcFilePanel;

	@Inject
	@Pdf
	private FilePanel pdfFilePanel;

	@Inject
	private CampaignTablePanel cTablePanel;

	@Inject
	private MailingGenerator mailingGenerator = null;

	private String dataSourceFile = null;
	private String pdfFile = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 744750628941813691L;

	private List<Data> sourceData;

	private JButton sendBtn;

	private boolean ignoreFirstLine = false;

	private JLabel label;

	private JProgressBar progressBar;

	public CampaignPanel() {
	}

	@Inject
	public void initPanel() {
		mailingGenerator.addListener(this);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		GridBagConstraints gbc_CsvPanel = new GridBagConstraints();
		gbc_CsvPanel.anchor = GridBagConstraints.CENTER;
		gbc_CsvPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_CsvPanel.insets = new Insets(0, 0, 5, 5);
		gbc_CsvPanel.gridx = 0;
		gbc_CsvPanel.gridy = 0;
		add(srcFilePanel, gbc_CsvPanel);
		srcFilePanel.addListener(this);

		GridBagConstraints gbc_PdfPanel = new GridBagConstraints();
		gbc_PdfPanel.anchor = GridBagConstraints.CENTER;
		gbc_PdfPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_PdfPanel.insets = new Insets(0, 0, 5, 5);
		gbc_PdfPanel.gridx = 0;
		gbc_PdfPanel.gridy = 1;
		add(pdfFilePanel, gbc_PdfPanel);
		pdfFilePanel.addListener(this);

		GridBagConstraints gbc_CheckBox = new GridBagConstraints();
		gbc_CheckBox.anchor = GridBagConstraints.CENTER;
		gbc_CheckBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_CheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_CheckBox.gridx = 0;
		gbc_CheckBox.gridy = 2;
		final JCheckBox checkBox = new JCheckBox(
				"Ignore first line in data source file", ignoreFirstLine);
		add(checkBox, gbc_CheckBox);
		checkBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				ignoreFirstLine = checkBox.isSelected();
				loadData();
			}
		});

		GridBagConstraints gbc_ButtonPanel = new GridBagConstraints();
		gbc_ButtonPanel.anchor = GridBagConstraints.EAST;
		// gbc_ButtonPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_ButtonPanel.insets = new Insets(0, 0, 5, 5);
		gbc_ButtonPanel.gridx = 0;
		gbc_ButtonPanel.gridy = 3;
		sendBtn = new JButton("Send");
		sendBtn.setEnabled(false);
		sendBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeStateButton(false);
				mailingGenerator.sendCampaign(sourceData, pdfFile);
			}
		});
		add(sendBtn, gbc_ButtonPanel);

		GridBagConstraints gbc_ProgressPanel = new GridBagConstraints();
		gbc_ProgressPanel.anchor = GridBagConstraints.CENTER;
		gbc_ProgressPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_ProgressPanel.insets = new Insets(0, 0, 5, 5);
		gbc_ProgressPanel.gridx = 0;
		gbc_ProgressPanel.gridy = 4;
		JPanel progressPanel = initProgressPanel();
		add(progressPanel, gbc_ProgressPanel);

		GridBagConstraints gbc_Table = new GridBagConstraints();
		gbc_Table.anchor = GridBagConstraints.CENTER;
		gbc_Table.fill = GridBagConstraints.BOTH;
		gbc_Table.insets = new Insets(0, 0, 5, 5);
		gbc_Table.gridx = 0;
		gbc_Table.gridy = 5;
		add(cTablePanel, gbc_Table);
		cTablePanel.setVisible(false);
		updateLabel();
	}

	@Override
	public void fileSelected(String selectedFile) {
		if (selectedFile.toLowerCase().endsWith("xlsx")) {
			dataSourceFile = selectedFile;
			loadData();
		} else {
			pdfFile = selectedFile;
		}

		if (StringUtils.isNotBlank(dataSourceFile)
				&& StringUtils.isNotBlank(pdfFile)) {
			sendBtn.setEnabled(true);
		}
		updateLabel();
		progressBar.setValue(0);
	}

	@Override
	public void done() {
		changeStateButton(true);
		label.setText("Campaign done !");
		JOptionPane.showMessageDialog(this, "Campaign done !");
	}

	@Override
	public void progress(int index, int progress, boolean state) {
		label.setText("Send " + (index + 1) + " on " + sourceData.size());
		progressBar.setValue(progress);
		progressBar.setString(progress + " %");
		// System.out.println("Progress=" + progress + " %. Send " + (index + 1)
		// + " on " + csvData.size());
	}

	private void loadData() {
		if (dataSourceFile != null) {
			try {
				sourceData = reader.readAll(dataSourceFile, ignoreFirstLine);
				cTablePanel.setData(sourceData);
				cTablePanel.setVisible(true);
				checkConsistencyData();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void changeStateButton(boolean b) {
		sendBtn.setEnabled(b);
		srcFilePanel.setEnabled(b);
		pdfFilePanel.setEnabled(b);
	}

	private void checkConsistencyData() {
		int size = -1;
		for (Data data : sourceData) {
			if (size == -1)
				size = data.size();
			else if (size != data.size()) {
				JOptionPane.showMessageDialog(this, "Some data are uncomplete",
						"Quality control", JOptionPane.WARNING_MESSAGE);
				break;
			}
		}
	}

	private void updateLabel() {
		if (StringUtils.isNotBlank(dataSourceFile)
				&& StringUtils.isNotBlank(pdfFile)) {
			sendBtn.setEnabled(true);
			label.setText("Click on button \"Send\" to start the mailing.");
		} else if (StringUtils.isBlank(dataSourceFile)) {
			label.setText("Select a CSV File.");
		} else if (StringUtils.isBlank(pdfFile)) {
			label.setText("Select a PDF File.");
		}
	}

	private JPanel initProgressPanel() {
		JPanel p = new JPanel();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.5, 0.5, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		p.setLayout(gridBagLayout);

		label = new JLabel();
		GridBagConstraints gbc_Label = new GridBagConstraints();
		gbc_Label.anchor = GridBagConstraints.CENTER;
		gbc_Label.fill = GridBagConstraints.HORIZONTAL;
		gbc_Label.insets = new Insets(0, 0, 5, 5);
		gbc_Label.gridx = 0;
		gbc_Label.gridy = 0;
		p.add(label, gbc_Label);

		GridBagConstraints gbc_ProgressMonitor = new GridBagConstraints();
		gbc_ProgressMonitor.anchor = GridBagConstraints.CENTER;
		gbc_ProgressMonitor.fill = GridBagConstraints.HORIZONTAL;
		gbc_ProgressMonitor.insets = new Insets(0, 0, 5, 5);
		gbc_ProgressMonitor.gridx = 1;
		gbc_ProgressMonitor.gridy = 0;
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		p.add(progressBar, gbc_ProgressMonitor);

		return p;
	}

}
