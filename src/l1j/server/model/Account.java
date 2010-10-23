package l1j.server.model;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import l1j.Config;
import l1j.databases.Database;
import l1j.server.gametime.GameTime;
import l1j.util.DigestUtil;

public class Account {

	/** 帳號名稱 */
	private String _username;

	/** 密碼(已加密). */
	private String _encodedPassword;

	private int _permission;

	private boolean _onlineStatus = false;

	@SuppressWarnings("unused")
	public void updateOnlineStatus(int status) {
		Database.getInstance().executeUpdate(
				"UPDATE `account` SET `online_status`=? WHERE `username`=?",
				new Object[] { status, _username });
		setOnlineStatus(status == 0 ? false : true);
	}

	@SuppressWarnings("unused")
	private void updateLastLogin(String ip) {
		Database.getInstance()
				.executeUpdate(
						"UPDATE `account` SET `online_status`=1, `last_login`=?, `ip`=? WHERE `username`=?",
						new Object[] { GameTime.getCurrentYMDHMS(), ip,
								_username });
		setOnlineStatus(true);
	}

	public boolean validatePassword(String password)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return _encodedPassword.equals(DigestUtil
				.makeSHA256(Config.SERVER_PASSWORD_SALT + password
						+ DigestUtil.makeMD5(_username)));
	}

	/**
	 * 設定使用者的帳號
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this._username = username;
	}

	/**
	 * 取得使用者的帳號名稱
	 * 
	 * @return
	 */
	public String getUsername() {
		return _username;
	}

	/**
	 * 設定帳號的權限
	 * 
	 * @return
	 */
	public void setPermission(int permission) {
		this._permission = permission;
	}

	/**
	 * 取得帳號的權限
	 * 
	 * @return
	 */
	public int getPermission() {
		return _permission;
	}

	/**
	 * 設定是否在線上
	 * 
	 * @param onlineStatus
	 *            the onlineStatus to set
	 */
	public void setOnlineStatus(boolean onlineStatus) {
		this._onlineStatus = onlineStatus;
	}

	/**
	 * 取得是否在線上
	 * 
	 * @return the onlineStatus
	 */
	public boolean isOnline() {
		return _onlineStatus;
	}

	/** 訊息記錄 */
	private static Logger _log = Logger.getLogger(Account.class.getName());
}
