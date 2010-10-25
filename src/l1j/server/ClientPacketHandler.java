/**
 * 
 */
package l1j.server;

import java.util.logging.Logger;

import l1j.Opcodes;
import l1j.server.clientpacket.C_AuthLogin;
import l1j.server.clientpacket.C_ClinetVersion;
import l1j.server.clientpacket.C_LoginClick;
import l1j.server.clientpacket.C_LoginToGame;

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
	 * @throws Exception
	 */
	public void handlePacket(byte data[]) throws Exception {
		int oc = data[0] & 0xff;

		switch (oc) {
			case Opcodes.C_OPCODE_REQUEST_LOGIN:
				new C_AuthLogin(data, _client);
				break;
			case Opcodes.C_OPCODE_CLIENT_VERSION:
				new C_ClinetVersion(data, _client);
				break;
			case Opcodes.C_OPCODE_LOGIN_CLICK:
				new C_LoginClick(data, _client);
				break;
			case Opcodes.C_OPCODE_LOGIM_TO_SERVER:
				new C_LoginToGame(data, _client);
				break;
		}
	}

	@SuppressWarnings("unused")
	private static Logger _log = Logger.getLogger(ClientPacketHandler.class
			.getName());
}
