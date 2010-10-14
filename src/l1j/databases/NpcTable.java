/**
 * 
 */
package l1j.databases;

import java.util.logging.Logger;

/**
 * @author ChrisLiu
 *
 */
public class NpcTable {

	private static NpcTable _instance;
	private final boolean _initialized;
	
	private NpcTable() {
		loadNpcData();
		_initialized = true;
	}

	/**
	 * 取得 NpcTable 的實體
	 * @return 回傳 NpcTable 的實體
	 */
	public static NpcTable getInstance() {
		if (null == _instance){ _instance = new NpcTable();}
		return _instance;
	}
	
	// FIXME: ChrisLiu.20101014: 先把程式架構寫好，再來依照資料庫重整
	private void loadNpcData() {
		
	}


	/**
	 * 取得 NpcTable 是否初始化
	 * @return bool NpcTable 是否成功初始化
	 */
	public boolean isInitialized() {
		return _initialized;
	}
	
	private final static Logger _log = Logger.getLogger(NpcTable.class.getName());
}
