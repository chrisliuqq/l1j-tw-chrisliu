/**
 * 
 */
package l1j.server.serverpacket;

import java.io.IOException;

import l1j.Opcodes;

/**
 * @author ChrisLiu
 */
// FIXME: ChrisLiu.2010/10/15: 這邊優先要改，尚未完成
public class S_InitialKey extends ServerBasePacket {

	private byte[] bs = new byte[15];

	/**
	 * 初始化金鑰的建構子
	 */
	public S_InitialKey() {
		RandomArrayList.getByte(bs);

		writeC(Opcodes.S_OPCODE_INITIAL_KEY);
		writeByte(bs);
	}

	/**
	 * 取得加密的金鑰
	 * 
	 * @return long 回傳加密的key
	 */
	public long getCipherKey() {
		return fromLong64(((bs[3] & 0xff) << 24) | ((bs[2] & 0xff) << 16)
				| ((bs[1] & 0xff) << 8) | (bs[0] & 0xff));
	}

	@Override
	public byte[] getContent() throws IOException {
		return getBytes();
	}

}
