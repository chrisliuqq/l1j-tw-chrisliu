/**
 * 
 */
package l1j.server.model;

import java.util.logging.Logger;

/**
 * @author ChrisLiu
 */
public class Bookmark {

	private int _charId;

	private int _id;

	private String _name;

	private int _locX;

	private int _locY;

	private short _mapId;

	/**
	 * 書籤的建構子
	 */
	public Bookmark() {
	}

	/**
	 * 書籤的建構子
	 * 
	 * @param charId
	 * @param id
	 * @param name
	 * @param locX
	 * @param locY
	 * @param mapId
	 */
	public Bookmark(int charId, int id, String name, int locX, int locY,
			short mapId) {
		this._charId = charId;
		this._id = id;
		this._name = name;
		this._locX = locX;
		this._locY = locY;
		this._mapId = mapId;
	}

	private final static Logger _log = Logger.getLogger(Bookmark.class
			.getName());
}
