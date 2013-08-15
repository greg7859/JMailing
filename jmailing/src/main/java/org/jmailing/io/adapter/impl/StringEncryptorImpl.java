package org.jmailing.io.adapter.impl;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.PropertyValueEncryptionUtils;


public class StringEncryptorImpl implements StringEncryptor {

	private final PBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

	/**
	 * Constructor. Reads the password from a key file on disk
	 */
	public StringEncryptorImpl() {
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

	/* (non-Javadoc)
	 * @see org.jmailing.io.adapter.StringEncryptor#marshal(java.lang.String)
	 */
	@Override
	public String encrypt(String plaintext) {
		// This encrypts and adds the ENC(...)
		return PropertyValueEncryptionUtils.encrypt(plaintext, encryptor);
	}

	/* (non-Javadoc)
	 * @see org.jmailing.io.adapter.StringEncryptor#unmarshal(java.lang.String)
	 */

	@Override
	public String decrypt(String cyphertext) {

		// Perform decryption operations as needed and store the new values
		if (PropertyValueEncryptionUtils.isEncryptedValue(cyphertext))
			return PropertyValueEncryptionUtils.decrypt(cyphertext, encryptor);

		return cyphertext;
	}
}
