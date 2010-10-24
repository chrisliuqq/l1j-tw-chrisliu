/**
 * 
 */
package l1j.databases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Logger;

import l1j.server.model.instance.L1PcInstance;
import l1j.server.serverpacket.S_CharacterList;

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

	/**
	 * 更新角色上線的狀態
	 * 
	 * @param charName
	 *            角色的名稱
	 * @param status
	 *            狀態
	 */
	public static void updateOnlineStatus(String charName, int status) {
		Database.getInstance().executeUpdate(
				"UPDATE `characters` SET `OnlineStatus`=? WHERE `char_name`=?",
				new Object[] { status, charName });
	}

	/**
	 * 取得帳號中所有的角色清單，只用於登入時的人物列表
	 * 
	 * @param account
	 * @return
	 */
	public static S_CharacterList[] getCharacterListPackage(String account) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<S_CharacterList> characterPacks = new ArrayList<S_CharacterList>();
		try {
			con = Database.getInstance().getConnection();
			pstm = con
					.prepareStatement("SELECT * FROM `character` WHERE `account`=? ORDER BY `objid` LIMIT 8");
			pstm.setString(1, account);
			rs = pstm.executeQuery();

			while (rs.next()) {

				characterPacks.add(new S_CharacterList(rs.getString("name"), rs
						.getString("clan_name"), rs.getByte("type"), rs
						.getByte("sex"), rs.getShort("lawful"), rs
						.getShort("curhp"), rs.getShort("curmp"), rs
						.getByte("def"), rs.getByte("level"),
						rs.getByte("str"), rs.getByte("dex"),
						rs.getByte("con"), rs.getByte("cha"),
						rs.getByte("wis"), rs.getByte("intel"), rs
								.getByte("perm")));
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return characterPacks
				.toArray(new S_CharacterList[characterPacks.size()]);
	}

	public static L1PcInstance loadCharacter(String charName) {
		L1PcInstance pc = new L1PcInstance();
		Database.getInstance().executeQuery(
				"SELECT * FROM `characters` WHERE `char_name`=? LIMIT 1",
				new Object[] { charName }, pc);
		return pc;
	}

	private static Logger _log = Logger.getLogger(CharacterTable.class
			.getName());
}
