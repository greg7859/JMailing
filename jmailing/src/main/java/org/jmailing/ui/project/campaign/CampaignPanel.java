package org.jmailing.ui.project.campaign;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.lang.StringUtils;
import org.jmailing.injector.annotation.Csv;
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
	@Csv
	private FilePanel csvFilePanel;

	@Inject
	@Pdf
	private FilePanel pdfFilePanel;

	@Inject
	private CampaignTablePanel cTablePanel;

	@Inject
	private MailingGenerator mailingGenerator = null;

	private String cvsFile = null;
	private String pdfFile = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 744750628941813691L;

	private List<Data> csvData;

	private JButton sendBtn;

	public CampaignPanel() {
	}

	@Inject
	public void initPanel() {
		mailingGenerator.addListener(this);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		GridBagConstraints gbc_CsvPanel = new GridBagConstraints();
		gbc_CsvPanel.anchor = GridBagConstraints.CENTER;
		gbc_CsvPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_CsvPanel.insets = new Insets(0, 0, 5, 5);
		gbc_CsvPanel.gridx = 0;
		gbc_CsvPanel.gridy = 0;
		add(csvFilePanel, gbc_CsvPanel);
		csvFilePanel.addListener(this);

		GridBagConstraints gbc_PdfPanel = new GridBagConstraints();
		gbc_PdfPanel.anchor = GridBagConstraints.CENTER;
		gbc_PdfPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_PdfPanel.insets = new Insets(0, 0, 5, 5);
		gbc_PdfPanel.gridx = 0;
		gbc_PdfPanel.gridy = 1;
		add(pdfFilePanel, gbc_PdfPanel);
		pdfFilePanel.addListener(this);

		GridBagConstraints gbc_ButtonPanel = new GridBagConstraints();
		gbc_ButtonPanel.anchor = GridBagConstraints.EAST;
		// gbc_ButtonPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_ButtonPanel.insets = new Insets(0, 0, 5, 5);
		gbc_ButtonPanel.gridx = 0;
		gbc_ButtonPanel.gridy = 2;
		sendBtn = new JButton("Send");
		sendBtn.setEnabled(false);
		sendBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeStateButton(false);
				mailingGenerator.sendCampaign(csvData, pdfFile);
			}
		});
		add(sendBtn, gbc_ButtonPanel);

		GridBagConstraints gbc_Table = new GridBagConstraints();
		gbc_Table.anchor = GridBagConstraints.CENTER;
		gbc_Table.fill = GridBagConstraints.BOTH;
		gbc_Table.insets = new Insets(0, 0, 5, 5);
		gbc_Table.gridx = 0;
		gbc_Table.gridy = 3;
		add(cTablePanel, gbc_Table);
		cTablePanel.setVisible(false);
	}

	@Override
	public void fileSelected(String selectedFile) {
		if (selectedFile.toLowerCase().endsWith("csv")) {
			cvsFile = selectedFile;
			try {
				csvData = reader.readAll(selectedFile);
				cTablePanel.setData(csvData);
				cTablePanel.setVisible(true);
				checkConsistencyData();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			pdfFile = selectedFile;
		}

		if (StringUtils.isNotBlank(cvsFile) && StringUtils.isNotBlank(pdfFile)) {
			sendBtn.setEnabled(true);
		}
	}

	@Override
	public void done() {
		changeStateButton(true);
		JOptionPane.showMessageDialog(this, "Campaign done !");
	}

	@Override
	public void progress(int index, int progress, boolean state) {
		System.out.println("Progress=" + progress + " %. Send " + (index + 1)
				+ " on " + csvData.size());
	}

	private void changeStateButton(boolean b) {
		sendBtn.setEnabled(b);
		csvFilePanel.setEnabled(b);
		pdfFilePanel.setEnabled(b);
	}

	private void checkConsistencyData() {
		int size = -1;
		for (Data data : csvData) {
			if (size == -1)
				size = data.size();
			else if (size != data.size()) {
				JOptionPane.showMessageDialog(this, "Some data are uncomplete",
						"Quality control", JOptionPane.WARNING_MESSAGE);
				break;
			}
		}
	}

}
