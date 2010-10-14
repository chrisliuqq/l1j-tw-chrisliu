/**
 * 
 */
package l1j.server.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import l1j.Config;

/**
 * @author ChrisLiu
 * 
 */
// XXX: 暫時沿用 L1J-JP 2021 的寫法，在觀察要不要改用其他寫法
public class ThreadPoolManager {

	private static ThreadPoolManager _instance;

	private static final int SCHEDULED_CORE_POOL_SIZE = 10;
	/**
	 * 通用的 Executor Service
	 */
	private final Executor _executor;
	/**
	 * 通用的 Scheduled Executor Service
	 */
	private final ScheduledExecutorService _scheduler;
	/**
	 * 偵測玩家專用的 Scheduled Executor Service
	 */
	private final ScheduledExecutorService _pcScheduler;

	private final int _pcSchedulerPoolSize = 1 + Config.GAME_SERVER_MAX_ONLINE_USER / 20;

	private ThreadPoolManager() {
		_executor = Executors.newCachedThreadPool();

		_scheduler = Executors
				.newScheduledThreadPool(SCHEDULED_CORE_POOL_SIZE,
						new PriorityThreadFactory("GerenalSTPool",
								Thread.NORM_PRIORITY));
		_pcScheduler = Executors.newScheduledThreadPool(_pcSchedulerPoolSize,
				new PriorityThreadFactory("PcMonitorSTPool",
						Thread.NORM_PRIORITY));

	}

	/**
	 * 取得 ThreadPoolManager 實例
	 * @return ThreadPoolManager 實例
	 */
	public static ThreadPoolManager getInstance() {
		if (null == _instance) {
			_instance = new ThreadPoolManager();
		}
		return _instance;
	}

	private class PriorityThreadFactory implements ThreadFactory {
		private final int _prio;
		private final String _name;
		private final AtomicInteger _threadNumber = new AtomicInteger(1);
		private final ThreadGroup _group;

		public PriorityThreadFactory(String name, int prio) {
			_prio = prio;
			_name = name;
			_group = new ThreadGroup(_name);
		}

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(_group, r);
			t.setName(_name + "-" + _threadNumber.getAndIncrement());
			t.setPriority(_prio);
			return t;
		}

		@SuppressWarnings("unused")
		public ThreadGroup getGroup() {
			return _group;
		}
	}

	protected static final Logger _log = Logger
			.getLogger(ThreadPoolManager.class.getName());

	/**
	 * 使用 Thread 執行
	 * @param r 可執行的介面
	 */
	public void execute(Runnable r) {
		if (null == _executor) {
			Thread t = new Thread(r);
			t.start();
		} else {
			_executor.execute(r);
		}
	}
}
