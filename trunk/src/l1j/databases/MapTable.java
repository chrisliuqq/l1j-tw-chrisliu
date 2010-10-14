/**
 * 
 */
package l1j.databases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.util.SQLUtil;

/**
 * @author ChrisLiu
 * 
 */
// FIXME: ChrisLiu.20101014: 先照舊，應該可以把 WorldMap 和 MapTable 合在一起，先看看整體的架構
public class MapTable {

	private static MapTable _instance;

	private final Map<Integer, MapData> _maps = new HashMap<Integer, MapData>();
	
	private class MapData {
		public int startX = 0;
		public int endX = 0;
		public int startY = 0;
		public int endY = 0;
		public double monster_amount = 1;
		public double dropRate = 1;
		public int attribute = 0;
	}
	
	private MapTable() {
		loadMapsFromDatabase();
	}

	/**
	 * @return 回傳 MapTable 的實體
	 */
	public static MapTable getInstance() {
		if (null == _instance) {
			_instance = new MapTable();
		}
		return _instance;
	}

	private void loadMapsFromDatabase() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = Database.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM `mapids`");

			for (rs = pstm.executeQuery(); rs.next();) {
				MapData data = new MapData();
				int mapId = rs.getInt("mapid");
				data.startX = rs.getInt("startX");
				data.endX = rs.getInt("endX");
				data.startY = rs.getInt("startY");
				data.endY = rs.getInt("endY");
				data.monster_amount = rs.getDouble("monster_amount");
				data.dropRate = rs.getDouble("drop_rate");
				data.attribute = rs.getInt("attribute");

				_maps.put(new Integer(mapId), data);
			}

			_log.config("Maps " + _maps.size());
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs, pstm, con);
		}
	}
	
	/**
	 * @param mapId 要取得地圖屬性的地圖編號
	 * @return 回傳此地圖的屬性
	 */
	public int getAttribute(int mapId) {
		MapData map = _maps.get(mapId);
		if (map == null) {
			return 0;
		}
		return map.attribute;
	}
	
	private final static Logger _log = Logger.getLogger(MapTable.class
			.getName());
}
