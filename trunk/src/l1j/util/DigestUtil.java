package l1j.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestUtil {

	private static MessageDigest _md;

	/**
	 * 密碼使用 SHA256 加密
	 * 
	 * @param rawPassword
	 *            密碼
	 * @return String
	 * @throws NoSuchAlgorithmException
	 *             密碼算法不能使用環境的時候
	 * @throws UnsupportedEncodingException
	 *             文字的編碼沒有被支援的時候
	 */
	public static String makeSHA256(String rawPassword)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		_md = MessageDigest.getInstance("SHA-256");
		byte[] sha256hash = new byte[32];
		_md.update(rawPassword.getBytes("UTF-8"), 0, rawPassword.length());
		sha256hash = _md.digest();

		return convertToString(sha256hash);
	}

	/**
	 * 密碼使用 MD5 加密
	 * 
	 * @param rawPassword
	 *            密碼
	 * @return String
	 * @throws NoSuchAlgorithmException
	 *             密碼算法不能使用環境的時候
	 * @throws UnsupportedEncodingException
	 *             文字的編碼沒有被支援的時候
	 */
	public static String makeMD5(String rawPassword)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		_md = MessageDigest.getInstance("MD5");
		byte[] md5hash = new byte[32];
		_md.update(rawPassword.getBytes("UTF-8"), 0, rawPassword.length());
		md5hash = _md.digest();

		return convertToString(md5hash);
	}

	/**
	 * 密碼使用 SHA 加密
	 * 
	 * @param rawPassword
	 *            密碼
	 * @return String
	 * @throws NoSuchAlgorithmException
	 *             密碼算法不能使用環境的時候
	 * @throws UnsupportedEncodingException
	 *             文字的編碼沒有被支援的時候
	 */
	@SuppressWarnings("unused")
	public static String encodePassword(final String rawPassword)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		byte[] buf = rawPassword.getBytes("UTF-8");
		buf = MessageDigest.getInstance("SHA").digest(buf);

		return Base64.encodeBytes(buf);
	}

	/** Convert from Byte[] to String */
	private static String convertToString(byte[] data) {
		StringBuilder sbString = new StringBuilder();
		for (byte element : data) {
			int halfbyte = (element >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9)) {
					sbString.append((char) ('0' + halfbyte));
				} else {
					sbString.append((char) ('a' + (halfbyte - 10)));
				}
				halfbyte = element & 0x0F;
			} while (two_halfs++ < 1);
		}
		return sbString.toString();
	}
}
