package org.jmailing.model.smtp;

public interface Smtp {

	/**
	 * @return the host
	 */
	public String getHost();

	/**
	 * @param host the host to set
	 */
	public void setHost(String host);

	/**
	 * @return the port
	 */
	public String getPort();

	/**
	 * @param port the port to set
	 */
	public void setPort(String port);

	/**
	 * @return the login
	 */
	public String getLogin();

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login);

	/**
	 * @return the password
	 */
	public String getPassword();

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password);
	
	/**
	 * @param from the From Address to set
	 */
	public void setFromAddress(String from);

	
	/**
	 * @return the From Address
	 */
	public String getFromAddress();


}