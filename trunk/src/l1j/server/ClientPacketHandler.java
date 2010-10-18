/**
 * 
 */
package l1j.server;

import java.util.logging.Logger;

import l1j.Config;
import l1j.Opcodes;
import l1j.server.clientpacket.C_ClinetVersion;

/**
 * @author ChrisLiu
 */
public class ClientPacketHandler {

	private final ClientThread _client;

	/**
	 * PacketHandler 的建構子
	 * 
	 * @param clientThread
	 */
	public ClientPacketHandler(ClientThread clientThread) {
		_client = clientThread;
	}

	/**
	 * 處理收到且解密過的 client 封包
	 * 
	 * @param abyte0
	 * @param object
	 * @throws Exception
	 */
	public void handlePacket(byte abyte0[]) throws Exception {
		int oc = abyte0[0] & 0xff;

		// XXX: ChrisLiu.2010/10/18: 開發、DEBUG 時用的
		// 因為 switch 不能用非 inline 的變數，所以要測試時用 if 來判斷
		if (Config.DEBUG) {
			if (oc == Opcodes.DEBUG_C_OPCODE_CLIENT_VERSION) {
				new C_ClinetVersion(abyte0, _client);
			}
		} else {
			switch (oc) {
				case Opcodes.C_OPCODE_CLIENT_VERSION:
					new C_ClinetVersion(abyte0, _client);
					break;
			}
		}
	}

	private static Logger _log = Logger
			.getLogger(ClientPacketHandler.class.getName());
}
