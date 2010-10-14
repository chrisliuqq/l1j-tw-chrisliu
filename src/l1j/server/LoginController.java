/**
 * 
 */
package l1j.server;

import java.util.logging.Logger;

/**
 * @author ChrisLiu
 */
public class LoginController {

	private static LoginController _instance;
	private int _maxAllowedOnlinePlayers;
	
	private LoginController() {
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
	 * @param maxAllowedOnlinePlayers 伺服器線上玩家的數目
	 */
	public void setMaxAllowedOnlinePlayers(int maxAllowedOnlinePlayers) {
		_maxAllowedOnlinePlayers = maxAllowedOnlinePlayers;
	}
	
	private final static Logger _log = Logger.getLogger(LoginController.class
			.getName());
}
