/**
 * 
 */
package l1j.server.gametime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.Logger;

/**
 * @author ChrisLiu
 */
public class GameTime {
	// 將 2010年1月13日 12:00(UTC) 當作標準：1月1日00:00
	private static final long BASE_TIME_IN_MILLIS_REAL = 1263340800000L;
	private final int _time;
	private final Calendar _calendar;

	/**
	 * 使用指定時間初始化 GameTime
	 * 
	 * @param time
	 *            傳入的指定時間
	 */
	public GameTime(int time) {
		_time = time;
		_calendar = makeCalendar(time);
	}

	private Calendar makeCalendar(int time) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.setTimeInMillis(0);
		cal.add(Calendar.SECOND, time);
		return cal;
	}

	/**
	 * 取得現在的遊戲時間
	 * 
	 * @return GameTime
	 */
	public static GameTime getCurrentTime() {
		return GameTime.valueOf(System.currentTimeMillis());
	}

	/**
	 * 取得現在的伺服器時間
	 * 
	 * @return String
	 */
	public static String getCurrentYMDHMS() {
		// 指定格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return sdf.format(System.currentTimeMillis());
	}

	private static GameTime valueOf(long currentTimeMillis) {
		long t1 = currentTimeMillis - BASE_TIME_IN_MILLIS_REAL;
		if (t1 < 0) throw new IllegalArgumentException();
		int t2 = (int) ((t1 * 6) / 1000L);
		int t3 = t2 % 3; // 時間が3の倍數になるように調整
		return new GameTime(t2 - t3);
	}

	private final static Logger _log = Logger.getLogger(GameTime.class
			.getName());
}
