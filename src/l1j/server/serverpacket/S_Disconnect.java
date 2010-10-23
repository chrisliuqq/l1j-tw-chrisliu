package l1j.server.serverpacket;

import l1j.Opcodes;

public class S_Disconnect extends ServerBasePacket {

	/**
	 * 0~21, 連線中斷 22, 有人以同樣的帳號登入，請注意，您的密碼可能已經外洩
	 * 
	 * @param msgId
	 *            原因
	 */
	public S_Disconnect(int msgId) {
		writeByte(Opcodes.S_OPCODE_DISCONNECT);
		writeShort(msgId);
		randomShort();
		randomByte();
	}

}
