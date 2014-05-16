package org.jmailing.model.email;

public interface Attachment {
	/**
	 * @return the path
	 */
	String getPath();

	/**
	 * @param path
	 *            the path to set
	 */
	void setPath(String path);

	/**
	 * @return the name
	 */
	String getName();

	/**
	 * @param name
	 *            the name to set
	 */
	void setName(String name);
}
