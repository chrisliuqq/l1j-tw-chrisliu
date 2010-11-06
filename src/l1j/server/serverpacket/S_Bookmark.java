/**
 * 
 */
package l1j.server.serverpacket;

import java.util.logging.Logger;

import l1j.Opcodes;

/**
 * @author ChrisLiu
 */
public class S_Bookmark extends ServerBasePacket {
	/**
	 * 書籤的建構子
	 */
	public S_Bookmark() {
	}

	/**
	 * 書籤的建構子
	 * 
	 * @param name
	 *            地圖名稱
	 * @param map
	 *            地圖編號
	 * @param id
	 *            編號
	 */
	public S_Bookmark(String name, short map, int id) {
		writeByte(Opcodes.S_OPCODE_BOOKMARK);
		writeString(name);
		writeShort(map);
		writeInt(id);
	}

	private final static Logger _log = Logger.getLogger(S_Bookmark.class
			.getName());
}
