package org.jmailing.ui.about;

import org.jmailing.ui.common.dialog.HtmlDialog;

public class AboutDialog extends HtmlDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8491616872723200050L;

	/**
	 * Create the dialog.
	 */
	public AboutDialog() {
		super(
				"<h1>JMailing V0.1</h1><p>This software helps you to send a mailing with PDF attachment file.</p><p>The information come from CSV File</p>");
	}

}
