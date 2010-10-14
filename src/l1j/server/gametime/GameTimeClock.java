/**
 * 
 */
package l1j.server.gametime;

import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.thread.ThreadPoolManager;


/**
 * @author ChrisLiu
 * 
 */
public class GameTimeClock {

	private static GameTimeClock _instance;
	private volatile GameTime _currentTime = null;
	private volatile GameTime _previousTime = null;

	private GameTimeClock() {
		_currentTime = GameTime.getCurrentTime();
		ThreadPoolManager.getInstance().execute(new TimeUpdater());
	}

	/**
	 * 取得 GameTimeClock 的實體
	 * @return GameTimeClock 的實體
	 */
	public static GameTimeClock getInstance() {
		if (null == _instance) {
			_instance = new GameTimeClock();
		}
		return _instance;
	}
	
	/**
	 * 取得目前的遊戲時間
	 * @return 目前的遊戲時間
	 */
	public GameTime getCurrentTime() {
		return _currentTime;
	}

	private class TimeUpdater implements Runnable {
		@Override
		public void run() {
			while (true) {
				_previousTime = _currentTime;
				_currentTime = GameTime.getCurrentTime();
				// TODO: ChrisLiu.20101013: 還沒看到要幹麼，先註解起來
				//notifyChanged();

				try {
					// XXX: ChrisLiu.20101013: 本來是 500ms 改成 1000ms 試試看
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
				}
			}
		}
	}
	
	private final static Logger _log = Logger.getLogger(GameTimeClock.class
			.getName());
}
