package l1j.server.clientpacket;

import l1j.server.ClientThread;
import l1j.server.serverpacket.S_ServerVersion;

/**
 * @author ChrisLiu
 */
public class C_ClinetVersion extends ClientBasePacket {

	/**
	 * 收到從客戶端傳來的客戶端版本
	 * 
	 * @param data
	 * @param client
	 */
	public C_ClinetVersion(byte[] data, ClientThread client) {
		read(data);
		client.sendPacket(new S_ServerVersion());
	}

}
