package l1j.server;

import static l1j.Config.AUTOMATIC_KICK;
import static l1j.Config.HOSTNAME_LOOKUPS;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import l1j.server.model.instance.L1PcInstance;
import l1j.server.serverpacket.S_InitialKey;
import l1j.util.StreamUtil;
import l1j.util.SystemUtil;

/**
 * @author ChrisLiu 處理客戶端的執行序
 */
public class ClientThread implements Runnable {

	private Socket _csocket;
	private InputStream _in;
	private BufferedOutputStream _out;
	private String _ip, _hostname;
	private PacketHandler _handler;
	private L1PcInstance _activeChar;
	private int _kick;

	/**
	 * 使用與 clinet 連線來建立執行序的建構子
	 * 
	 * @param socket
	 *            與 client 的連線
	 */
	public ClientThread(Socket socket) throws IOException {
		_csocket = socket;
		// 取得輸入的資料流
		_in = socket.getInputStream();
		// 取得輸出的資料流
		_out = new BufferedOutputStream(socket.getOutputStream());
		// 取得來源ip
		_ip = socket.getInetAddress().getHostAddress();
		_handler = new PacketHandler(this);

		if (HOSTNAME_LOOKUPS) {
			_hostname = socket.getInetAddress().getHostName();
		} else {
			_hostname = _ip;
		}
	}

	@Override
	public void run() {
		_log.info("客戶端 (" + _hostname + ") 連線開始。");
		_log.info("記憶體使用量: " + SystemUtil.getUsedMemoryMB() + "MB");
		Socket socket = _csocket;

		// XXX: ChrisLiu.2010/10/14: 2021原有的，先註解起來慢慢研究
		// HcPacket movePacket = new HcPacket(M_CAPACITY);
		// HcPacket hcPacket = new HcPacket(H_CAPACITY);
		// GeneralThreadPool.getInstance().execute(movePacket);
		// GeneralThreadPool.getInstance().execute(hcPacket);
		// XXX: ChrisLiu.2010/10/14: tw原有的，先註解起來慢慢研究
		// AutoResponse response = new AutoResponse();
		// ThreadPoolManager.getInstance().execute(response);
		ClientThreadObserver observer = null;
		// 是否啟用自動踢人
		if (AUTOMATIC_KICK > 0) {
			// 自動斷線的時間（單位:毫秒）
			observer = new ClientThreadObserver(AUTOMATIC_KICK * 60 * 1000);
			observer.start();
		}

		S_InitialKey initKey;
	}

	private static Timer _observerTimer = new Timer();

	class ClientThreadObserver extends TimerTask {
		// 時間內由 client 收到的封包數目
		private int _recivedClientPacket = 1;
		// 檢查是否強制斷線的時間
		private final int _disconnectTimeMillis;

		public ClientThreadObserver(int disconnectTimeMillis) {
			_disconnectTimeMillis = disconnectTimeMillis;
		}

		public void start() {
			_observerTimer.scheduleAtFixedRate(ClientThreadObserver.this, 0,
					_disconnectTimeMillis);
		}

		@Override
		public void run() {
			if (null == _csocket) {
				cancel();
				return;
			}
			// reset 封包計數器
			if (_recivedClientPacket > 0) {
				_recivedClientPacket = 0;
				return;
			}
			// 選角色之前或在線上但除了個人商店之外其他的發呆時候
			if (null == _activeChar || _activeChar != null
					&& !_activeChar.isPrivateShop()) {
				kick(0);
				_log.warning("一定時間內未收到來自於 " + _hostname + "的回應，所以踢掉他。");
				cancel();
				return;
			}

		}
	}

	/**
	 * 踢人
	 * 
	 * @param msgId
	 *            因為什麼原因被踢
	 */
	public void kick(int msgId) {
		// sendPacket(new S_Disconnect(msgId));
		_kick = 1;
		StreamUtil.close(_out, _in);
	}

	private static Logger _log = Logger.getLogger(ClientThread.class.getName());

}
