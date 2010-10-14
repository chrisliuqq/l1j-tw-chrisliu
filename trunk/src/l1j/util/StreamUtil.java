/**
 * 
 */
package l1j.util;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ChrisLiu
 *
 */
public class StreamUtil {

	/**
	 * 關閉 Stream 系列的資料流
	 * @param closeables 已經開啟的資料流
	 */
	public static void close(Closeable... closeables) {
		for (Closeable c : closeables) {
			try {
				if (c != null) {
					c.close();
				}
			} catch (IOException e) {
				_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			}
		}
	}
	
	private final static Logger _log = Logger.getLogger(StreamUtil.class.getName());
}
