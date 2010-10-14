/**
 * 
 */
package l1j.databases;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ChrisLiu
 */
public class AccountTable {

	/**
	 * 清除所有帳號的上線狀態
	 */
	public static void resetOnlineStatus() {
		try {
			Database.getInstance()
					.executeQuery(
							"UPDATE `accounts` SET `online_status`='0'");
		} catch (SQLException e) {
			_log.log(Level.SEVERE, "清除所有帳號的上線狀態時發生錯誤", e);
		}

	}

	private static Logger _log = Logger.getLogger(AccountTable.class.getName());

}
