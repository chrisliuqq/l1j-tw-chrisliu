/**
 * 
 */
package l1j.server.serverpacket;

import l1j.Opcodes;

/**
 * @author ChrisLiu
 */
// FIXME: ChrisLiu.2010/10/15: 這邊優先要改，尚未完成
public class S_InitialKey extends ServerBasePacket {

	/**
	 * 初始化金鑰的建構子
	 * 
	 * @return
	 */
	public byte[] S_InitialKey() {
		writeByte(Opcodes.S_OPCODE_INITIAL_KEY);
		randomInt(); // 加解密種子
		randomInt(); // 未知
		randomInt(); // 未知
		randomShort(); // 未知
		randomByte(); // 未知
		return getBytes();
	}

}
