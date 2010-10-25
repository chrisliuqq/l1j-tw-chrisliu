package l1j.server.serverpacket;

import java.util.logging.Logger;

import l1j.Opcodes;

public class S_Announcement extends ServerBasePacket {

	/**
	 * 送出登入時的公告
	 * 
	 * @param s
	 *            字串
	 */
	public S_Announcement(String s) {
		writeByte(Opcodes.S_OPCODE_ANNOUNCEMENT);
		writeString(s);
	}

	private final static Logger _log = Logger.getLogger(S_Announcement.class
			.getName());
}
