/**
 * 
 */
package l1j.server.encryption;

import java.util.ArrayList;

import l1j.util.type.UByte8;
import l1j.util.type.ULong32;

/**
 * @author ChrisLiu
 */
public class LineageEncryption {
	// Initialized keys - one key per client ID
	public static ArrayList<LineageKeys> KeyPut = new ArrayList<LineageKeys>();

	// The current key to use for encryption/decryption
	// private static LineageKeys currentKeys = null;

	/**
	 * Initializes lineage encrypt/decrypt keys for the given clientID and maps
	 * them to that id.
	 * 
	 * @param EncKey
	 * @param clientID
	 *            the id to map the keys to
	 * @param seed
	 *            a random seed to compute the keys with
	 * @return LineageKeys the generated encrypt/decrypt keys
	 * @throws Exception
	 *             If a client id already is in use
	 */
	public static void initKeys(LineageKeys EncKey, long seed) {
		// 判斷鑰匙是否被登記
		if (KeyPut.contains(EncKey))
			// 符合條件則終止程序
			return;
		else {
			// 不符合條件則登記鑰匙
			KeyPut.add(EncKey);
		}

		LineageKeys keys = new LineageKeys();

		long[] key = { seed, 0x930FD7E2L };

		LineageBlowfish.getSeeds(key);

		keys.encodeKey[0] = keys.decodeKey[0] = key[0];
		keys.encodeKey[1] = keys.decodeKey[1] = key[1];
	}

	/**
	 * Encrypts the data with the prepared keys.
	 * 
	 * @param buf
	 *            the data to be encrypted, this arrays data is overwritten
	 * @return char[] an 8 bit unsigned char array with the encrypted data
	 * @throws Exception
	 *             If no keys have been prepared
	 */
	public static byte[] encrypt(byte[] buf, LineageKeys currentKeys) {

		long mask = ULong32.fromArray(buf);

		_encrypt(buf, currentKeys);

		currentKeys.encodeKey[0] ^= mask;
		currentKeys.encodeKey[1] = ULong32.add(currentKeys.encodeKey[1],
				0x287EFFC3L);

		return buf;
	}

	/**
	 * Decrypts the data with the prepared keys.
	 * 
	 * @param buf
	 *            the data to be decrypted, this arrays data is overwritten
	 * @return char[] an 8 bit unsigned char array with the encrypted data
	 * @throws Exception
	 *             If no keys have been prepared
	 */
	public static byte[] decrypt(byte[] buf, LineageKeys currentKeys) {

		_decrypt(buf, currentKeys);

		long mask = ULong32.fromArray(buf);

		currentKeys.decodeKey[0] ^= mask;
		currentKeys.decodeKey[1] = ULong32.add(currentKeys.decodeKey[1],
				0x287EFFC3L);

		return buf;
	}

	/**
	 * Does the actual hardcore encryption.
	 * 
	 * @param buf
	 *            the data to be encrypted, this arrays data is overwritten
	 * @return char[] an 8 bit unsigned char array with the encrypted data
	 */
	private static byte[] _encrypt(byte[] buf, LineageKeys currentKeys) {
		byte[] ek = UByte8.fromArray(currentKeys.encodeKey);

		buf[0] ^= ek[0];

		for (int i = 1; i < buf.length; i++) {
			buf[i] ^= (buf[i - 1] ^ ek[i & 7]);
		}

		buf[3] = (byte) (buf[3] ^ ek[2]);
		buf[2] = (byte) (buf[2] ^ buf[3] ^ ek[3]);
		buf[1] = (byte) (buf[1] ^ buf[2] ^ ek[4]);
		buf[0] = (byte) (buf[0] ^ buf[1] ^ ek[5]);

		return buf;
	}

	/**
	 * Does the actual hardcore decryption.
	 * 
	 * @param buf
	 *            the data to be decrypted, this arrays data is overwritten
	 * @return char[] an 8 bit unsigned char array with the encrypted data
	 */
	private static byte[] _decrypt(byte[] buf, LineageKeys currentKeys) {
		byte[] dk = UByte8.fromArray(currentKeys.decodeKey);

		byte b3 = buf[3];
		buf[3] ^= dk[2];

		byte b2 = buf[2];
		buf[2] ^= (b3 ^ dk[3]);

		byte b1 = buf[1];
		buf[1] ^= (b2 ^ dk[4]);

		byte k = (byte) (buf[0] ^ b1 ^ dk[5]);
		buf[0] = (byte) (k ^ dk[0]);

		for (int i = 1; i < buf.length; i++) {
			byte t = buf[i];
			buf[i] ^= (dk[i & 7] ^ k);
			k = t;
		}

		return buf;
	}
}
