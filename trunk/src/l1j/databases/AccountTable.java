/**
 * 
 */
package l1j.databases;

import java.util.logging.Logger;

import l1j.Config;
import l1j.server.gametime.GameTime;
import l1j.server.model.Account;
import l1j.util.DigestUtil;

/**
 * @author ChrisLiu
 */
public class AccountTable {

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
	public void create(String username, String password, String ip) {
		String sql = null;
		try {
			sql = "INSERT INTO `account` SET `username`=?, `password`=?, `last_login`=?, `ip`=?, `register_date`=?";
			String date = GameTime.getCurrentYMDHMS();
			Object[] params = {
					username,
					password,
					DigestUtil.makeSHA256(Config.SERVER_PASSWORD_SALT
							+ password + DigestUtil.makeMD5(username)), date,
					ip, date };
			Database.getInstance().execute(sql, params);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 讀取指定的帳號
	 * 
	 * @param username
	 * @return Account
	 */
	public Account load(String username) {
		Account account = new Account();
		String sql = null;
		sql = "SELECT * FROM `account` WHERE `username`=? LIMIT 1";
		account = (Account) Database.getInstance().executeQuery(sql,
				new Object[] { username }, account);
		return account;
	}

	/**
	 * 清除所有帳號的上線狀態
	 */
	public static void resetOnlineStatus() {
		Database.getInstance().execute(
				"UPDATE `account` SET `online_status`='0'");
	}

	private static Logger _log = Logger.getLogger(AccountTable.class.getName());

}
