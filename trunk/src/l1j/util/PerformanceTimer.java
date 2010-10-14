/**
 * 
 */
package l1j.util;

/**
 * @author ChrisLiu
 * 
 */
public class PerformanceTimer {
	/**
	 * 執行時間
	 */
	private long _begin;

	/**
	 * 物件被創立的時候紀錄時間。
	 */
	public PerformanceTimer()
	{
		_begin = System.currentTimeMillis();
	}
	
	/**
	 * 重設執行時間
	 */
	public void reset() {
		_begin = System.currentTimeMillis();
	}

	/**
	 * @return 執行時間
	 */
	public long get() {
		return System.currentTimeMillis() - _begin;
	}
}
