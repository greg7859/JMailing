/**
 * 
 */
package org.jmailing.service.mailing.impl;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jmailing.injector.annotation.Pdf;
import org.jmailing.service.mailing.AttachmentSplitter;
import org.jmailing.service.mailing.AttachmentSplitterException;
import org.jmailing.utilities.FileNameUtilities;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;

/**
 * @author gregory
 * 
 */
@Singleton
public class PdfAttachmentSplitterImpl implements AttachmentSplitter {

	@Inject
	@Pdf
	private String pdfExtension;

	@Inject
	private FileNameUtilities filenameUtilities;

	@Override
	public void split(String filename, String outputPath, String splitPrefix,
			int nbPage) throws IllegalArgumentException, IOException,
			AttachmentSplitterException {

		PdfReader reader = new PdfReader(filename);
		int n = reader.getNumberOfPages();

		if (n % nbPage != 0) {
			throw new IllegalArgumentException("The file " + filename
					+ " contains " + n
					+ " pages and is not possible to split by " + nbPage);
		}

		int i = 0;
		while (i < n) {
			String outFile = filenameUtilities.build(outputPath, splitPrefix,
					(i / nbPage) + 1, pdfExtension);
			PdfCopy writer;
			try {
				Document document = new Document(
						reader.getPageSizeWithRotation(1));
				writer = new PdfCopy(document, new FileOutputStream(outFile));
				document.open();
				PdfImportedPage page;
				for (int count = 0; count < nbPage; count++) {
					page = writer.getImportedPage(reader, ++i);
					writer.addPage(page);
				}
				document.close();
				// FIXME utiliser les autoclose de JDK7.
				writer.close();
			} catch (DocumentException e) {
				throw new AttachmentSplitterException(e.getMessage());
			}
		}
	}

}
