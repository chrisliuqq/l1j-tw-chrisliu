package l1j.server;

import static l1j.Config.AUTOMATIC_KICK;
import static l1j.Config.HOSTNAME_LOOKUPS;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import l1j.server.encryption.LineageEncryption;
import l1j.server.encryption.LineageKeys;
import l1j.server.model.instance.L1PcInstance;
import l1j.server.serverpacket.S_Disconnect;
import l1j.server.serverpacket.S_InitialKey;
import l1j.server.serverpacket.ServerBasePacket;
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
	private ClientPacketHandler _handler;
	private L1PcInstance _activeChar;
	private LineageKeys _clkey;
	private boolean _isStopClientLoop = false;
	private String _username;

	private ArrayList<byte[]> _byteBox;// 位元盒子
	/**
	 * 最大位元數
	 */
	public static final int _maxBytes = 1440 - 2; // 最大位元數 1440 - 2 拿來放封包大小

	private short packageLength = 0;

	/**
	 * 使用與 clinet 連線來建立執行序的建構子
	 * 
	 * @param socket
	 *            與 client 的連線
	 * @throws IOException
	 */
	public ClientThread(Socket socket) throws IOException {
		_csocket = socket;
		// 取得輸入的資料流
		_in = socket.getInputStream();
		// 取得輸出的資料流
		_out = new BufferedOutputStream(socket.getOutputStream());
		// 取得來源ip
		_ip = socket.getInetAddress().getHostAddress();
		_handler = new ClientPacketHandler(this);

		if (HOSTNAME_LOOKUPS) {
			_hostname = socket.getInetAddress().getHostName();
		} else {
			_hostname = _ip;
		}
	}

	// XXX: ChrisLiu.2010/10/18: 此部份主要參考小k的寫法後稍作修改。
	@Override
	public void run() {
		_log.info("客戶端 (" + _hostname + ") 連線開始。");
		_log.info("記憶體使用量: " + SystemUtil.getUsedMemoryMB() + "MB");

		// XXX: ChrisLiu.2010/10/14: 2021原有的，先註解起來慢慢研究
		// HcPacket movePacket = new HcPacket(M_CAPACITY);
		// HcPacket hcPacket = new HcPacket(H_CAPACITY);
		// GeneralThreadPool.getInstance().execute(movePacket);
		// GeneralThreadPool.getInstance().execute(hcPacket);
		// XXX: ChrisLiu.2010/10/18: 發送封包的方式改用排程去處理了，慢慢在考慮這樣好不好，先不實作
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

		try {
			initKey = new S_InitialKey();
			sendPacket(initKey);
			LineageEncryption.initKeys(_clkey, initKey.getKey());
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			initKey = null;
		}

		int hiByte = 0;
		int loByte = 0;
		byte data[] = null;
		// 開始作處理 client 封包的循環
		while (!_isStopClientLoop) {

			try {
				// 取得資料長度(從此輸入流中讀取下一個數據位元組。)
				hiByte = _in.read();
				loByte = _in.read();

				// 判斷 loByte 值是否為 -1
				// -1 表示連線中斷
				if (loByte == -1) {
					break;
				}

				// 取得真實資料長度
				hiByte |= loByte << 8 & 0xFF;
				hiByte -= 2;
				// 設定陣列大小
				data = new byte[hiByte];
				// 取得資料(從此輸入流中將 data.length 個位元組的數據讀入一個 data 陣列中。)
				loByte = _in.read(data);

				// 將加密的資料解密
				data = LineageEncryption.decrypt(data, _clkey);

				// XXX: ChrisLiu.2010/10/18: 在這邊先將最常用的封包處理掉
				/*
				 * if (data[0] != C_OPCODE_KEEPALIVE) { }
				 */
				_handler.handlePacket(data);
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				data = null;
			}
		}
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
		sendPacket(new S_Disconnect(msgId));
		clientStop();
		StreamUtil.close(_out, _in);
	}

	private void clientStop() {
		if (_isStopClientLoop) return;

		// 清空所有資料
		try {
			// 此連結的輸入流置于“流的末尾”。
			_csocket.shutdownInput();
			// 禁用此連結的輸出流。
			_csocket.shutdownOutput();
			// 關閉此連結。
			_csocket.close();
			// 鑰匙取消登記
			LineageEncryption.KeyPut.remove(_clkey);
		} catch (Exception e) {
		}
		// XXX: ChrisLiu.2010/10/24: 尚未實做
		/*
		 * String userName = getUserName(); if
		 * (_userAll.inLoginServer(userName)) {
		 * _userAll.removeLoginServer(userName); } else if
		 * (_userAll.inGameServer(userName)) {
		 * _userAll.removeGameServer(userName); // 將角色移除
		 * getPlayer().deleteObject(); } if (userName != null) { // 帳號狀態變更
		 * D_Account.getAccount().upOnLine(userName, 0); }
		 */
		// 清空用戶來源位址
		_ip = null;
		// 清空回應者
		_handler = null;
		// 清空加密與解密種子
		_clkey = null;
		// 清空封包傳輸組
		_out = null;
		// 清空客戶端封包
		_csocket = null;
		// 清空客戶端輸入流
		_out = null;
		// 已停止為真
		_isStopClientLoop = true;
	}

	/**
	 * 送出伺服器封包
	 * 
	 * @param packet
	 *            伺服器封包
	 */
	public void sendPacket(ServerBasePacket packet) {
		sendPacket(packet, true);
	}

	/**
	 * 送出封包(封包是否被送出)
	 * 
	 * @param packet
	 *            封包
	 * @param sendOut
	 *            是否被送出(true立即送出 false累積封包資料等待送出)
	 */
	public void sendPacket(ServerBasePacket packet, boolean sendOut) {
		sendPacket(packet.getBytes(), sendOut);
	}

	/**
	 * 送出封包(封包是否被送出)
	 * 
	 * @param data
	 *            封包
	 * @param sendOut
	 *            是否被送出(true立即送出 false累積封包資料等待送出)
	 */
	public void sendPacket(byte[] data, boolean sendOut) {
		// XXX: ChrisLiu.2010/11/7: 超過 1440 的封包大小時就自動送出，必且將此封包加到下一個 queue 中
		boolean reAddPackage = false;

		if (data != null) {

			synchronized (this) {
				try {

					if (_clkey != null) {
						data = LineageEncryption.encrypt(data, _clkey);
					}

					if (packageLength + data.length < _maxBytes) {
						packageLength += data.length;
						_byteBox.add(data);
					} else {
						reAddPackage = true;
						sendOut = true;
					}

				} catch (Exception e) {
					_log.severe(e.getLocalizedMessage());
				}
			}
		}

		if (sendOut) {
			try {
				// 將此 byte 陣列輸出流的全部內容寫入到指定的輸出流參數中
				for (byte[] encryptData : _byteBox) {
					// 封包長度 + 2 , 請勿變動
					int length = encryptData.length + 2;

					// 將資料寫入緩衝器中
					_out.write(length & 0xFF);
					_out.write(length >> 8 & 0xFF);
					_out.write(encryptData);
				}

				// 將緩衝器內的資料送出並且將緩衝器內的資料清除
				_out.flush();

				// 將此 ByteBox 陣列輸出流的 count 字段重置為零，從而丟棄輸出流中目前已累積的所有輸出。
				_byteBox.clear();// 清空資料

				if (reAddPackage) {
					packageLength += data.length;
					_byteBox.add(data);
				} else {
					packageLength = 0;
				}

			} catch (Exception e) {
				_log.severe(e.getLocalizedMessage());
			}

		}
	}

	/**
	 * 取得客戶端連線的ip
	 * 
	 * @return
	 */
	public String getAddress() {
		return _ip;
	}

	/**
	 * @param _username
	 *            the _username to set
	 */
	public void setUsername(String _username) {
		this._username = _username;
	}

	/**
	 * @return the _username
	 */
	public String getUsername() {
		return _username;
	}

	private static Logger _log = Logger.getLogger(ClientThread.class.getName());

}
