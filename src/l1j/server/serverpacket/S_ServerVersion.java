/**
 * 
 */
package l1j.server.serverpacket;

import l1j.Config;
import l1j.Opcodes;

/**
 * @author ChrisLiu
 */
public class S_ServerVersion extends ServerBasePacket {

	/**
	 * 伺服器版本的建構子
	 */
	public S_ServerVersion() {
		writeByte(Opcodes.S_OPCODE_SERVER_VERSION);
		// Auth Check client Version
		// 1 = Check
		// 0 = no check
		// > 1 no check
		// type : boolean
		writeBoolean(false);
		// your server id, first id = 2
		// id = 0, ????
		// id = 1, ????
		writeByte(0x02);
		// all version
		// If the user level is a administrator,
		// inputs /ver to be able to print out all version in game
		// If the user level isn't a administrator
		// inputs /ver to be able to print out client version in game
		writeInt(0x00013a83); // Server 版本
		writeInt(0x000138fd); // Cache 版本
		writeInt(0x0000ee01); // Auth 版本
		writeInt(0x00013a2d); // NPC 版本

		// ↓ unknown
		// ↓ Old 270Lin.bin
		// ↓ New 270Lin.bin
		// ↓ isn't game time
		writeInt(0x48324d10);

		// 未知
		writeByte(0x00);
		writeByte(0x00);

		// 國家
		writeByte(Config.CLIENT_LANGUAGE);

		randomInt();
	}

}
