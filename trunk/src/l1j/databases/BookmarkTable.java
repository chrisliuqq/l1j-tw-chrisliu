/**
 * 
 */
package l1j.databases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Logger;

import l1j.server.model.Bookmark;
import l1j.server.model.instance.L1PcInstance;

/**
 * @author ChrisLiu
 */
public class BookmarkTable {

	/**
	 * 取得角色的書籤，用於登入時
	 * 
	 * @return
	 */
	public static Bookmark[] getBookmarks(L1PcInstance pc) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Bookmark> bookmarks = new ArrayList<Bookmark>();
		try {
			con = Database.getInstance().getConnection();
			pstm = con
					.prepareStatement("SELECT * FROM `character_bookmark` WHERE `character_id`=? ORDER BY `name` ASC");
			pstm.setInt(1, pc.getCharId());
			rs = pstm.executeQuery();

			while (rs.next()) {

				bookmarks.add(new Bookmark(rs.getInt("bookmark_id"), rs
						.getInt("character_id"), rs.getString("name"), rs
						.getInt("locx"), rs.getInt("locY"), rs
						.getShort("mapId")));
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return bookmarks.toArray(new Bookmark[bookmarks.size()]);
	}

	private final static Logger _log = Logger.getLogger(BookmarkTable.class
			.getName());
}
