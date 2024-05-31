package jca.pbe;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EncryptWithPBE {

	private static final String CIPHER_ALGO = "PBEWithHmacSHA256AndAES_128";

	public static void main(String[] args) throws Exception{
		final EncryptWithPBE encryptWithPBE = new EncryptWithPBE();
		encryptWithPBE.encrypt("some content to encrypt", "pwd-secure-pwd");
	}

	private void encrypt(String toEncrypt, String password) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {


		final SecureRandom secureRandom = new SecureRandom();
		byte []salt = new byte[8];
		secureRandom.nextBytes(salt);

		int iterCount = 20;

		PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, iterCount);

		PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
		final SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(CIPHER_ALGO);
		final SecretKey pbeKey = secretKeyFactory.generateSecret(pbeKeySpec);

		// create PBE Cipher
		final Cipher encryptCipher = Cipher.getInstance(CIPHER_ALGO);

		// initialize cipher
		encryptCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParameterSpec);

		final byte[] plainText = toEncrypt.getBytes();
		final byte[] encryptedText = encryptCipher.doFinal(plainText);

		System.out.println("Plain Text");
		printContent(plainText);
		System.out.println();

		System.out.println("Encrypted Text");
		printContent(encryptedText);
		System.out.println();

		// decrypt
		final Cipher decryptCipher = Cipher.getInstance(CIPHER_ALGO);
		decryptCipher.init(Cipher.DECRYPT_MODE, pbeKey, encryptCipher.getParameters());

		final byte[] decrypt_encryptedText = decryptCipher.doFinal(encryptedText);
		System.out.println("Decrypted - plain text");
		printContent(decrypt_encryptedText);

	}

	private void printContent(byte[] bytes) {
		for (byte b : bytes) {
			System.out.print((char)b);
		}
	}

}
