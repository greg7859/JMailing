package org.jmailing.model.smtp.impl;

import javax.inject.Singleton;

import org.jmailing.model.smtp.Smtp;

@Singleton
public class SmtpImpl implements Smtp {
	private String host;

	private String port = "25";

	private String fromAddress;

	private String login;

	private String password;

	private String fromLabel;

	private boolean ssl = false;

	private boolean authentication = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jmailing.model.impl.Smtp#getHost()
	 */
	@Override
	public String getHost() {
		return host;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jmailing.model.impl.Smtp#setHost(java.lang.String)
	 */
	@Override
	public void setHost(String host) {
		this.host = host;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jmailing.model.impl.Smtp#getPort()
	 */
	@Override
	public String getPort() {
		return port;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jmailing.model.impl.Smtp#setPort(int)
	 */
	@Override
	public void setPort(String port) {
		this.port = port;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jmailing.model.impl.Smtp#getLogin()
	 */
	@Override
	public String getLogin() {
		return login;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jmailing.model.impl.Smtp#setLogin(java.lang.String)
	 */
	@Override
	public void setLogin(String login) {
		this.login = login;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jmailing.model.impl.Smtp#getPassword()
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jmailing.model.impl.Smtp#setPassword(java.lang.String)
	 */
	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void setFromAddress(String from) {
		this.fromAddress = from;
	}

	@Override
	public String getFromAddress() {
		return this.fromAddress;
	}

	@Override
	public void setFromLabel(String from) {
		this.fromLabel = from;

	}

	@Override
	public String getFromLabel() {
		return this.fromLabel;
	}

	@Override
	public void setSSL(boolean state) {
		this.ssl = state;
	}

	@Override
	public boolean getSSL() {
		return this.ssl;
	}

	@Override
	public void setAuthentication(boolean state) {
		this.authentication = state;

	}

	@Override
	public boolean getAuthentication() {
		return this.authentication;
	}

}
