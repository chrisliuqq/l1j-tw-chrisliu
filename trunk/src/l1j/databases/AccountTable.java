/**
 * 
 */
package l1j.databases;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import l1j.Config;
import l1j.server.gametime.GameTime;
import l1j.server.model.Account;
import l1j.util.Base64;

/**
 * @author ChrisLiu
 */
public class AccountTable {

	private static MessageDigest _md;

	private static AccountTable _instance;

	private AccountTable() {
	}

	/**
	 * 取得 AccountTable 的實體
	 * 
	 * @return
	 */
	public static AccountTable getInstance() {
		if (null == _instance) {
			_instance = new AccountTable();
		}
		return _instance;
	}

	/**
	 * 建立帳號
	 * 
	 * @param username
	 *            帳號名稱
	 * @param password
	 *            未加密前的密碼
	 * @param ip
	 *            來源
	 */
	public void createAccount(String username, String password, String ip) {
		String sql = null;
		try {
			sql = "INSERT INTO `account` SET `username`=?, `password`=?, `last_login`=?, `ip`=?, `register_date`=?";
			String date = GameTime.getCurrentYMDHMS();
			Object[] params = {
					username,
					password,
					makeSHA256(Config.SERVER_PASSWORD_SALT + password
							+ makeMD5(username)), date, ip, date };
			Database.getInstance().execute(sql, params);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 讀取指定的帳號
	 * 
	 * @param username
	 * @return
	 */
	public Account loadAccount(String username) {
		Account account = new Account();
		String sql = null;
		sql = "SELECT * FROM `account` WHERE `username`=? LIMIT 1";
		account = (Account) Database.getInstance().executeQuery(sql,
				new Object[] { username }, account);
		return account;
	}

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
	private static String makeSHA256(String rawPassword)
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
	private static String makeMD5(String rawPassword)
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
	private static String encodePassword(final String rawPassword)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		byte[] buf = rawPassword.getBytes("UTF-8");
		buf = MessageDigest.getInstance("SHA").digest(buf);

		return Base64.encodeBytes(buf);
	}

	/**
	 * 清除所有帳號的上線狀態
	 */
	public static void resetOnlineStatus() {

		Database.getInstance().execute(
				"UPDATE `account` SET `online_status`='0'");

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

	private static Logger _log = Logger.getLogger(AccountTable.class.getName());

}
