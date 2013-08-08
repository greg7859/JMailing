package org.jmailing.model.smtp.impl;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jmailing.io.adapter.EncryptedStringXmlAdapter;
import org.jmailing.model.smtp.Smtp;

@XmlRootElement(name = "smtp-config")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class SmtpImpl implements Smtp {
	@XmlAttribute(required = true)
	String host;

	@XmlAttribute(required = true)
	String port;

	@XmlAttribute(required = true)
	String fromAddress;

	@XmlElement(required = true)
	@XmlJavaTypeAdapter(value = EncryptedStringXmlAdapter.class)
	String login;

	@XmlElement(required = true)
	@XmlJavaTypeAdapter(value = EncryptedStringXmlAdapter.class)
	String password;

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
		this.fromAddress=from;
	}

	@Override
	public String getFromAddress() {
		return this.fromAddress;
	}
}
