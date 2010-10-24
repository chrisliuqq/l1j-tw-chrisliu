/**
 * 
 */
package l1j.server;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * @author ChrisLiu
 */
public class LoginController {

	private static LoginController _instance;
	private int _maxAllowedOnlinePlayers;
	private static HashMap<String, ClientThread> _account;

	private LoginController() {
		_account = new HashMap<String, ClientThread>();
	}

	/**
	 * 取得 LoginController 的實體
	 * 
	 * @return LoginController 的實體
	 */
	public static LoginController getInstance() {
		if (null == _instance) {
			_instance = new LoginController();
		}
		return _instance;

	}

	/**
	 * 設定伺服器線上玩家的數目
	 * 
	 * @param maxAllowedOnlinePlayers
	 *            伺服器線上玩家的數目
	 */
	public void setMaxAllowedOnlinePlayers(int maxAllowedOnlinePlayers) {
		_maxAllowedOnlinePlayers = maxAllowedOnlinePlayers;
	}

	/**
	 * 登入到伺服器中
	 * 
	 * @param username
	 * @param client
	 */
	public void login(String username, ClientThread client) {
		if (!_account.containsKey(username)) {
			_account.put(username, client);
		}

	}

	private final static Logger _log = Logger.getLogger(LoginController.class
			.getName());

}
