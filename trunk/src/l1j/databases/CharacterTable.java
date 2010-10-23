/**
 * 
 */
package l1j.databases;

import java.util.logging.Logger;

/**
 * @author ChrisLiu
 */
public class CharacterTable {

	/**
	 * 清除所有角色的上線狀態
	 */
	public static void clearOnlineStatus() {
		Database.getInstance().execute(
				"UPDATE `characters` SET `OnlineStatus`=0");

	}

	private static Logger _log = Logger.getLogger(CharacterTable.class
			.getName());
}
