/**
 * 
 */
package l1j.databases;

import java.util.logging.Logger;

/**
 * @author ChrisLiu
 * 
 */
public class IdFactory {

	private int _curId;

	private final Object _monitor = new Object();

	private static final int FIRST_ID = 0x10000000;

	private static IdFactory _idFactory = null;

	private IdFactory() {
		loadState();
	}

	/**
	 * @return 回傳IdFactory的實體
	 */
	public static IdFactory getInstance() {
		if (null == _idFactory) {
			_idFactory = new IdFactory();
		}
		return _idFactory;
	}

	/**
	 * @return 回傳下一個未被使用的ID
	 */
	public int nextId() {
		synchronized (_monitor) {
			return _curId++;
		}
	}

	private void loadState() {
		int id = 0;
		/*
		 * try { // FIXME: ChrisLiu.20101013: 要來改寫 SQL，這段寫這麼醜… id = Database
		 * .getInstance() .getResultSet(
		 * "SELECT MAX(`id`)+1 AS `nextid` FROM (SELECT `id` FROM `character_items` UNION ALL SELECT `id` FROM `character_teleport` UNION ALL SELECT `id` FROM `character_warehouse` UNION ALL SELECT `id` from character_elf_warehouse union all select objid as id from characters union all select clan_id as id from clan_data union all select id from clan_warehouse union all select objid as id from pets) t"
		 * ); } catch (SQLException e) {
		 * e.printStackTrace(); }
		 */
		if (id < FIRST_ID) {
			id = FIRST_ID;
		}
		_curId = id;
		_log.info("目前的物件ID: " + _curId);

	}

	private static Logger _log = Logger.getLogger(IdFactory.class.getName());
}
