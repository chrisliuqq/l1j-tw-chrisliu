package l1j.server.clientpacket;

import l1j.server.ClientThread;

/**
 * @author ChrisLiu
 */
public class C_ClinetVersion extends ClientBasePacket {

	/**
	 * 收到從客戶端傳來的客戶端版本
	 * 
	 * @param abyte0
	 * @param _client
	 */
	public C_ClinetVersion(byte[] abyte0, ClientThread _client) {
		read(abyte0);
	}

}
