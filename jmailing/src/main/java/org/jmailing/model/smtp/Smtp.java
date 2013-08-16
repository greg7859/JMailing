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
	 * @param state the SSL state to set
	 */
	public void setSSL(boolean state);

	
	/**
	 * @return the SSL state
	 */
	public boolean getSSL();
	
	/**
	 * @param state the Authentication state to set
	 */
	public void setAuthentication(boolean state);

	
	/**
	 * @return the Authentication state
	 */
	public boolean getAuthentication();
	
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
	
	/**
	 * @param from the From Label to set
	 */
	public void setFromLabel(String from);

	
	/**
	 * @return the From Label
	 */
	public String getFromLabel();


}