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

		// FIXME: ChrisLiu.2010/10/18: 優先修改 ClientThread 的架構，後面才好寫下去
		client.sendPacket(new S_ServerVersion());
	}

}
