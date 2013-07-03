package com.thebinaryfox.finecraft;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class Crypt {

	static private final byte[] salt = { (byte) 0x01, (byte) 0x13, (byte) 0x5F, (byte) 0xF3, (byte) 0x61, (byte) 0x97, (byte) 0xAC, (byte) 0xCD };

	/**
	 * Encrypt a string.
	 * 
	 * @param data
	 *            the data to encrypt.
	 * @param password
	 *            the password to encrypt with.
	 * @return the encrypted string as bytes.
	 * @throws Exception
	 */
	static public byte[] encryptString(String data, String password) throws Exception {
		return encryptBytes(data.getBytes("UTF-8"), password);
	}

	/**
	 * Encrypt a byte array.
	 * 
	 * @param data
	 *            the data to encrypt.
	 * @param password
	 *            the password to encrypt with.
	 * @return the encrypted bytes.
	 * @throws Exception
	 */
	static public byte[] encryptBytes(byte[] data, String password) throws Exception {
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
		SecretKey key = keyFactory.generateSecret(new PBEKeySpec(password.toCharArray()));
		Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
		pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(salt, 20));
		return pbeCipher.doFinal(data);
	}

	/**
	 * Decrypt an encrypted string.
	 * 
	 * @param data
	 *            the encrypted data.
	 * @param password
	 *            the password to decrypt with.
	 * @return the decrypted string.
	 * @throws Exception
	 */
	static public String decryptString(byte[] data, String password) throws Exception {
		return new String(decryptBytes(data, password), "UTF-8");
	}

	/**
	 * Decrypt an encrypted byte array.
	 * 
	 * @param data
	 *            the encrypted data.
	 * @param password
	 *            the password to decrypt with.
	 * @return the decrypted bytes.
	 * @throws Exception
	 */
	static public byte[] decryptBytes(byte[] data, String password) throws Exception {
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
		SecretKey key = keyFactory.generateSecret(new PBEKeySpec(password.toCharArray()));
		Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
		pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(salt, 20));
		return pbeCipher.doFinal(data);
	}

}
