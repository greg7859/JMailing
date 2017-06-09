package org.jmailing.model.project;

public interface AttachmentMailingProjectPart {

	/**
	 * Set the number of page of split
	 * 
	 * @param nbPage
	 *            the number of page to set
	 */
	void setNumberOfPageOfSplit(int nbPage);

	/**
	 * Return the number of page of split
	 * 
	 * @return he number of page
	 */
	int getNumberOfPageOfSplit();

	/**
	 * Set the filename format. This format can include the variable defined in
	 * the source
	 * 
	 * @param format
	 *            the format
	 */
	void setFilenameFormat(String format);

	/**
	 * Return the filename format.
	 * 
	 * @return the format
	 */
	String getFilenameFormat();

	/**
	 * Add an extra attachment. This file will be attached to each email
	 * 
	 * @param extraAttachment
	 *            an extra attachment
	 */
	void addExtraAttachment(ExtraAttachment extraAttachment);

	/**
	 * Return the extra attachment at the index position
	 * 
	 * @param index
	 *            the index
	 * @return the extra attachment
	 */
	ExtraAttachment getExtraAttachment(int index);

	/**
	 * Return the number of extra attachment
	 * 
	 * @return the size of the extra attachment list
	 */
	int getExtraAttachmentSize();

	/**
	 * Remove all extra attachment
	 */
	void removeAllExtraAttachment();

}
