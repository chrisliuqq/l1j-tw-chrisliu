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

	private int seed = 0;

	/**
	 * 初始化金鑰的建構子
	 * 
	 * @return
	 */
	public S_InitialKey() {
		writeByte(Opcodes.S_OPCODE_INITIAL_KEY);
		randomInt(); // 加解密種子
		randomInt(); // 未知
		randomInt(); // 未知
		randomShort(); // 未知
		randomByte(); // 未知

		seed |= getBytes()[1] << 0 & 0xff;
		seed |= getBytes()[2] << 8 & 0xff00;
		seed |= getBytes()[3] << 16 & 0xff0000;
		seed |= getBytes()[4] << 24 & 0xff000000;
	}

	public int getKey() {
		return seed;
	}

}
