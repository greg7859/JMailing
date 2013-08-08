package org.jmailing.io.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.jasypt.encryption.pbe.PBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.PropertyValueEncryptionUtils;

public class EncryptedStringXmlAdapter extends XmlAdapter<String, String> {

	private final PBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

	/**
	 * Constructor. Reads the password from a key file on disk
	 */
	public EncryptedStringXmlAdapter() {
		// String keyFile = Environment
		// .getProperty(PropertyConstants.MASTER_KEY_FILE);
		// try {
		// byte[] bytes = FileUtils.readFileToByteArray(new File(keyFile));
		// String key = new String(bytes);
		String key = "Basket89";
		encryptor.setPassword(key);
		// } catch (IOException e) {
		// System.err.println("Could not load key from " + keyFile);
		// }
	}

	/**
	 * Encrypts the value to be placed back in XML
	 */
	@Override
	public String marshal(String plaintext) throws Exception {
		// This encrypts and adds the ENC(...)
		return PropertyValueEncryptionUtils.encrypt(plaintext, encryptor);
	}

	/**
	 * Decrypts the string value
	 */
	@Override
	public String unmarshal(String cyphertext) throws Exception {

		// Perform decryption operations as needed and store the new values
		if (PropertyValueEncryptionUtils.isEncryptedValue(cyphertext))
			return PropertyValueEncryptionUtils.decrypt(cyphertext, encryptor);

		return cyphertext;
	}
}
