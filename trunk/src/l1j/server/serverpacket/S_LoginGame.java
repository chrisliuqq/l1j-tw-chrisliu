/**
 * 
 */
package l1j.server.serverpacket;

import l1j.Opcodes;

/**
 * @author ChrisLiu
 */
public class S_LoginGame extends ServerBasePacket {

	/**
	 * 回傳登入時的封包
	 * 
	 * @param type
	 */
	public S_LoginGame() {
		// if (type == 1) {
		writeByte(Opcodes.S_OPCODE_LOGIN_GAME);
		writeByte(0x03);
		// } else {
		// writeByte(Opcodes.S_OPCODE_PACKETBOX);
		// writeByte(0xFF);
		// writeByte(0x7F);
		// writeByte(0x03);
		// }
	}
}
