/**
 * 
 */
package l1j.server.model.instance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import l1j.databases.CharacterTable;
import l1j.server.ClientThread;
import l1j.server.model.Bookmark;
import l1j.server.serverpacket.ServerBasePacket;

/**
 * @author ChrisLiu
 */
public class L1PcInstance {

	private ClientThread _client;
	private int _charId;
	private String _charName;
	private boolean _isOnline;
	private ArrayList<Bookmark> _bookmarks;

	public L1PcInstance() {
		_bookmarks = new ArrayList<Bookmark>();
	}

	/**
	 * 回傳是否在開個人商店
	 * 
	 * @return
	 */
	public boolean isPrivateShop() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 更新此帳號的上線狀態
	 * 
	 * @param status
	 */
	public void updateOnlineStatus(int status) {
		CharacterTable.updateOnlineStatus(this._charName, status);
		this._isOnline = (status == 1) ? true : false;
	}

	/**
	 * 設定此角色的 thread
	 * 
	 * @param client
	 */
	public void setClientThread(ClientThread client) {
		this._client = client;
	}

	/**
	 * 送出伺服器封包
	 * 
	 * @param packet
	 *            伺服器封包
	 */
	public void sendPacket(ServerBasePacket packet) {
		sendPacket(packet, true);
	}

	/**
	 * 送出封包(封包是否被送出)
	 * 
	 * @param packet
	 *            封包
	 * @param sendOut
	 *            是否被送出(true立即送出 false累積封包資料等待送出)
	 */
	public void sendPacket(ServerBasePacket packet, boolean sendOut) {
		sendPacket(packet.getBytes(), sendOut);
	}

	/**
	 * <font color=#827B00>送出封包(立即送出)</font><BR>
	 * 更新自己顯示資訊<BR>
	 * 適用:PC
	 * 
	 * @param data
	 *            封包資料
	 */
	public void sendPacket(final byte[] data) {
		// _lock.lock();
		try {
			if (_client != null) {
				_client.sendPacket(data, true);
			}
		} finally {
			// _lock.unlock();
		}
	}

	/**
	 * <font color=#827B00>送出封包(封包是否被送出)</font><BR>
	 * 更新自己顯示資訊<BR>
	 * 適用:PC
	 * 
	 * @param data
	 *            封包資料
	 * @param sendOut
	 */
	public void sendPacket(final byte[] data, final boolean sendOut) {
		// _lock.lock();

		try {
			if (_client != null) {
				_client.sendPacket(data, sendOut);
			}
		} finally {
			// _lock.unlock();
		}
	}

	/**
	 * @return 回傳角色的專有id
	 */
	public int getCharId() {
		return this._charId;
	}

	/**
	 * @param bookmark
	 */
	public void addBookmark(Bookmark bookmark) {
		this._bookmarks.add(bookmark);
	}

	/**
	 * @param bookmarks
	 */
	public void addBookmark(Bookmark[] bookmarks) {
		this._bookmarks.addAll(Arrays.asList(bookmarks));
	}

	private final static Logger _log = Logger.getLogger(L1PcInstance.class
			.getName());

}
