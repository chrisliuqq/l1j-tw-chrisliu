/**
 * 
 */
package l1j.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author ChrisLiu
 *
 */
public class SQLUtil {
	/**
	 * 一次關閉三個
	 * @param rs
	 * @param pstm
	 * @param con
	 */
	public static void close(ResultSet rs, PreparedStatement pstm, Connection con) {
		close(rs);
		close(pstm);
		close(con);
	}

	/**
	 * 一次關閉三個
	 * @param rs
	 * @param ps
	 * @param con
	 */
	public static void close(ResultSet rs, Statement ps, Connection con) {
		close(rs);
		close(ps);
		close(con);
	}

	/**
	 * 一次關閉兩個
	 * @param pstm
	 * @param con
	 */
	public static void close(PreparedStatement pstm, Connection con) {
		close(pstm);
		close(con);
	}

	/**
	 * 一次關閉兩個
	 * @param ps
	 * @param con
	 */
	public static void close(Statement ps, Connection con) {
		close(ps);
		close(con);
	}

	/**
	 * 關閉 Connection
	 * @param con
	 * @return null or SQLException
	 */
	public static SQLException close(Connection con) {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			return e;
		}
		return null;
	}
	/**
	 * 關閉 PreparedStatement
	 * @param pstm
	 * @return null or SQLException
	 */
	public static SQLException close(PreparedStatement pstm) {
		try {
			if (pstm != null) {
				pstm.close();
			}
		} catch (SQLException e) {
			return e;
		}
		return null;
	}
	/**
	 * 關閉 Statement
	 * @param ps
	 * @return null or SQLException
	 */
	public static SQLException close(Statement ps) {
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException e) {
			return e;
		}
		return null;
	}
	/**
	 * 關閉 ResultSet
	 * @param rs
	 * @return null or SQLException
	 */
	public static SQLException close(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			return e;
		}
		return null;
	}
}
