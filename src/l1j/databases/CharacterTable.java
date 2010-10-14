/**
 * 
 */
package l1j.databases;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ChrisLiu
 * 
 */
public class CharacterTable {

	

	/**
	 * 清除所有角色的上線狀態
	 */
	public static void clearOnlineStatus() {
		try {
		Database.getInstance().executeQuery("UPDATE `characters` SET `OnlineStatus`=0");
		} catch (SQLException e) {
			_log.log(Level.SEVERE, "清除所有角色的上線狀態時發生錯誤", e);}
		
	}
	
	private static Logger _log = Logger.getLogger(CharacterTable.class
			.getName());
}
