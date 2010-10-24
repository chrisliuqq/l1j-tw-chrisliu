/**
 * 
 */
package l1j.databases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.Config;
import l1j.server.model.Account;
import l1j.server.model.instance.L1PcInstance;
import l1j.util.SQLUtil;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @author ChrisLiu
 */
public class Database {

	/**
	 * 資料庫的實例
	 */
	private static Database _instance;

	/**
	 * 資料庫連結的來源
	 */
	private ComboPooledDataSource _source;

	private PreparedStatement pstm = null;

	private Database() {

		if (Config.DB_MAX_CONNECTIONS < 2) {
			Config.DB_MAX_CONNECTIONS = 2;
			_log.warning("A minimum of " + Config.DB_MAX_CONNECTIONS
					+ " db connections are required.");
		}

		try {
			_source = new ComboPooledDataSource();
			_source.setAutoCommitOnClose(true);

			_source.setInitialPoolSize(10);
			_source.setMinPoolSize(10);
			_source.setMaxPoolSize(Math.max(10, Config.DB_MAX_CONNECTIONS));
			// try to obtain connections indefinitely (0 = never quit)
			_source.setAcquireRetryAttempts(0);
			// 500 miliseconds wait before try to acquire connection again
			_source.setAcquireRetryDelay(500);
			// 0 = wait indefinitely for new connection if pool is exhausted
			_source.setCheckoutTimeout(0);
			// if pool is exhausted, get 5 more connections at a time cause
			// there is
			// a "long" delay on acquire connection so taking more than one
			// connection at once will make connection pooling more effective.
			_source.setAcquireIncrement(5);
			// this "connection_test_table" is automatically created if not
			// already
			// there
			_source.setAutomaticTestTable("connection_test_table");
			_source.setTestConnectionOnCheckin(false);
			// test idle connection every 60 sec
			_source.setIdleConnectionTestPeriod(3600);
			// 0 = idle connections never expire
			// *THANKS* to connection testing configured above
			// but I prefer to disconnect all connections not used
			// for more than 1 hour
			_source.setMaxIdleTime(Config.DB_MAX_IDLE_TIME);
			// enables statement caching, there is a "semi-bug" in c3p0 0.9.0
			// but in 0.9.0.2 and later it's fixed
			_source.setMaxStatementsPerConnection(100);
			// never fail if any way possible
			_source.setBreakAfterAcquireFailure(false);
			// 設定登入資訊
			_source.setDriverClass(Config.DB_DRIVER);
			_source.setJdbcUrl(Config.DB_URL);
			_source.setUser(Config.DB_USER);
			_source.setPassword(Config.DB_PASSWORD);

			/* Test the connection */
			_source.getConnection().close();

		} catch (SQLException e) {
			int error = e.getErrorCode();
			String msg = null;
			switch (error) {
				case 0:
					msg = "驅動程式錯誤";
					break;
				case 1045:
					msg = "使用者名稱或密碼錯誤";
					break;
				case 1049:
					msg = "資料庫錯誤";
					break;
				case 1130:
					msg = "遠端連線錯誤";
					break;
				default:
					msg = "SQLException";
					break;
			}
			_log.log(Level.SEVERE, msg, e);
		} catch (Exception e) {
			_log.log(Level.SEVERE, "初始化資料庫連線時發生錯誤", e);
		}
	}

	/**
	 * 取得資料庫連線的實體
	 * 
	 * @return Database 資料庫連線的實體
	 * @throws SQLException
	 */
	public static Database getInstance() {
		synchronized (Database.class) {
			if (_instance == null) {
				_instance = new Database();
			}
		}
		return _instance;
	}

	// XXX: ChrisLiu.20101013: 拿掉 try catch，這邊感覺不一定會跳 exception，有遇到在加上去。
	/**
	 * 關閉資料庫連線
	 */
	public void shutdown() {
		_source.close();
		_source = null;
	}

	/**
	 * 取得資料庫的連線
	 * 
	 * @return Connection 資料庫的連線
	 */
	public Connection getConnection() {
		Connection con = null;

		while (con == null) {
			try {
				con = _source.getConnection();
			} catch (SQLException e) {
				_log.warning("Database: getConnection() 發生錯誤，自動重試中" + e);
			}
		}

		return con;
	}

	/**
	 * 透過SQL取得只有單一整數結果
	 * 
	 * @param sql
	 *            要執行的SQL
	 * @return 查詢結果
	 */
	public int getSingleInt(String sql) {
		Connection con = getConnection();
		ResultSet rs = null;

		int result = 0;

		try {
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			if (rs.next()) {
				result = rs.getInt(0);
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs, pstm, con);
		}

		return result;
	}

	/**
	 * 執行不返回結果的查詢
	 * 
	 * @param sql
	 *            要查詢的 sql 語法
	 */
	public void execute(String sql) {
		Connection con = getConnection();

		try {
			pstm = con.prepareStatement(sql);
			pstm.execute();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm, con);
		}
	}

	/**
	 * 執行不返回結果的查詢
	 * 
	 * @param sql
	 *            要查詢的 sql 語法
	 * @param params
	 *            參數
	 */
	public void execute(String sql, Object[] params) {
		Connection con = getConnection();

		try {
			pstm = con.prepareStatement(sql);
			setParams(pstm, params);
			pstm.execute();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm, con);
		}
	}

	/**
	 * 執行要返回結果的查詢
	 * 
	 * @param sql
	 *            要查詢的 sql 語法
	 * @param params
	 *            參數
	 * @param object
	 *            要回傳的物件型態
	 * @return 回傳的類型不一定固定，因此用 Object
	 */
	public Object executeQuery(String sql, Object[] params, Object object) {
		Connection con = getConnection();
		ResultSet rs = null;
		try {
			pstm = con.prepareStatement(sql);
			if (null != params && 0 < params.length) {
				setParams(pstm, params);
			}
			rs = pstm.executeQuery();
			object = transRStoObject(object, rs);
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm, con);
		}
		return object;
	}

	/**
	 * 執行更新的查詢
	 * 
	 * @param sql
	 *            要執行的 sql 語法
	 * @param params
	 *            參數
	 * @param object
	 *            要回傳的物件型態
	 * @return 回傳的類型不一定固定，因此用 Object
	 */
	public void executeUpdate(String sql, Object[] params) {
		Connection con = getConnection();
		ResultSet rs = null;
		try {
			pstm = con.prepareStatement(sql);
			if (null != params && 0 < params.length) {
				setParams(pstm, params);
			}
			pstm.executeUpdate();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm, con);
		}
	}

	/**
	 * 將 PreparedStatement 的 SQL 加上對應的參數
	 * 
	 * @param pstmt
	 * @param params
	 */
	private void setParams(PreparedStatement pstmt, Object[] params) {
		if (null != params) {
			for (int i = 0, paramNum = params.length; i < paramNum; i++) {
				try {
					pstmt.setObject(i + 1, params[i]);
				} catch (SQLException e) {
					_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
				}
			}
		}
	}

	/**
	 * 將 ResultSet 結果轉為對應的物件
	 * 
	 * @param object
	 *            物件
	 * @param rs
	 *            ResultSet 物件
	 */
	private Object transRStoObject(Object object, ResultSet rs) {
		try {
			if (!rs.next()) return null;
			if (object instanceof Account) {
				object = new Account();
				((Account) object).setUsername(rs.getString("username"));
				((Account) object).setPermission(rs.getInt("permission"));
			} else if (object instanceof L1PcInstance) {
				object = new L1PcInstance();
				/*
				 * ((L1PcInstance)
				 * object).setUsername(rs.getString("username"));
				 * ((L1PcInstance)
				 * object).setPermission(rs.getInt("permission"));
				 */
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

	private static Logger _log = Logger.getLogger(Database.class.getName());
}
