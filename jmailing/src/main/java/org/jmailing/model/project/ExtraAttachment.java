package org.jmailing.model.project;

public interface ExtraAttachment {

	/**
	 * The file name which will be display in the email
	 * 
	 * @param filename
	 *            the attached filename
	 */
	void setFilename(String filename);

	/**
	 * Get the filename which will be display in the email
	 * 
	 * @return the filename
	 */
	String getFilename();

	/**
	 * Set the full file path to attach in the email
	 * 
	 * @param file
	 *            the file to attach in the email
	 */
	void setFileToAttach(String file);

	/**
	 * Get the full path to the file to attach in the email
	 * 
	 * @return the full path to the file
	 */
	String getFileToAttach();

}
