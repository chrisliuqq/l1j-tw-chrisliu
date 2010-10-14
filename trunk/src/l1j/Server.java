/**
 * 
 */
package l1j;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogManager;

import l1j.databases.Database;
import l1j.server.GameServer;

/**
 * @author ChrisLiu
 * 
 */
public class Server {

	/**
	 * 主程式
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		Config.Load();

		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(
					Config.LOG_CONFIG_FILE));
			LogManager.getLogManager().readConfiguration(is);
			is.close();
		} catch (IOException e) {
			_log.log(Level.SEVERE, "讀取 " + Config.LOG_CONFIG_FILE + " 時發生錯誤", e);
			System.exit(-1);
		} finally {
			is.close();
		}
		
		// 初始化資料庫伺服器
		Database.getInstance();
		// 初始化遊戲伺服器
		GameServer.getInstance();
	}

	private static Logger _log = Logger.getLogger(Server.class.getName());
}
