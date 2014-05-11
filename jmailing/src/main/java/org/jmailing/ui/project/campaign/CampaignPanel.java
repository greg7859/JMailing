package org.jmailing.ui.project.campaign;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.swing.JPanel;

import org.jmailing.injector.annotation.Csv;
import org.jmailing.injector.annotation.Pdf;
import org.jmailing.io.csv.DataFileReader;
import org.jmailing.model.source.Data;
import org.jmailing.ui.common.panel.FilePanel;
import org.jmailing.ui.common.panel.FilePanelListener;

public class CampaignPanel extends JPanel implements FilePanelListener {
	@Inject
	private DataFileReader reader;

	@Inject
	@Csv
	FilePanel csvFilePanel;

	@Inject
	@Pdf
	FilePanel pdfFilePanel;

	private String cvsFile = null;
	private String pdfFile = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 744750628941813691L;

	public CampaignPanel() {
	}

	@Inject
	public void initPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0,Double.MIN_VALUE };
		setLayout(gridBagLayout);

		GridBagConstraints gbc_CsvPanel = new GridBagConstraints();
		gbc_CsvPanel.anchor = GridBagConstraints.CENTER;
		gbc_CsvPanel.insets = new Insets(0, 0, 5, 5);
		gbc_CsvPanel.gridx = 0;
		gbc_CsvPanel.gridy = 0;
		gbc_CsvPanel.ipadx = 2;
		add(csvFilePanel, gbc_CsvPanel);

		GridBagConstraints gbc_PdfPanel = new GridBagConstraints();
		gbc_PdfPanel.anchor = GridBagConstraints.CENTER;
		gbc_PdfPanel.insets = new Insets(0, 0, 5, 5);
		gbc_PdfPanel.gridx = 0;
		gbc_PdfPanel.gridy = 1;
		gbc_PdfPanel.ipadx = 2;
		add(pdfFilePanel, gbc_PdfPanel);

	}

	@Override
	public void fileSelected(String selectedFile) {
		if (selectedFile.toLowerCase().endsWith("cvs")) {
			cvsFile = selectedFile;
			try {
				List<Data> data = reader.read(selectedFile, 20);
				// sourceTablePanel.setData(data);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			pdfFile = selectedFile;
		}

	}

}
