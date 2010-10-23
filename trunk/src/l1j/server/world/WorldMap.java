/**
 * 
 */
package l1j.server.world;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.util.PerformanceTimer;

/**
 * @author ChrisLiu
 */
public class WorldMap {
	private static WorldMap _instance;
	private Map<Integer, L1Map> _maps;

	private WorldMap() {
		PerformanceTimer timer = new PerformanceTimer();
		_log.info("正在讀取地圖檔案…");

		// TextMapReader in = new TextMapReader();
		BinaryMapReader in = new BinaryMapReader();
		try {
			_maps = in.read();
			if (_maps == null) throw new RuntimeException("讀取失敗");
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			System.exit(0);
		}
		_log.info("讀取地圖檔案共花費 " + timer.get() + " ms。");
	}

	/**
	 * @return 取得 WorldMap 的實體
	 */
	public static WorldMap getInstance() {
		if (null == _instance) {
			_instance = new WorldMap();
		}
		return _instance;
	}

	private final static Logger _log = Logger.getLogger(WorldMap.class
			.getName());
}
