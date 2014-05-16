package org.jmailing.service.mailing;

import java.io.IOException;

public interface AttachmentSplitter {

	/**
	 * Split the file by nbPage
	 * 
	 * @param filename
	 *            the file to split
	 * @param outputPath
	 *            the output path to store the splitted file
	 * @param splitPrefix
	 *            the prefix filename for the output
	 * @param nbPage
	 *            the number of page to split
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws AttachmentSplitterException
	 */
	void split(String filename, String outputPath, String splitPrefix, int nbPage)
			throws IOException, IllegalArgumentException,
			AttachmentSplitterException;

}
