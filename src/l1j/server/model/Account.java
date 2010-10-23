package l1j.server.model;

import java.util.logging.Logger;

public class Account {

	/** 帳號名稱 */
	private String username;

	/** 密碼(已加密). */
	private String encodedPassword;

	private int permission;

	private boolean _onlineStatus = false;

	/** 訊息記錄 */
	private static Logger _log = Logger.getLogger(Account.class.getName());

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}

	public int getPermission() {
		return permission;
	}
}
