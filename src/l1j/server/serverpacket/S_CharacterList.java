/**
 * 
 */
package l1j.server.serverpacket;

import l1j.Opcodes;

/**
 * @author ChrisLiu
 */
public class S_CharacterList extends ServerBasePacket {

	/**
	 * 回傳角色數量的封包
	 * 
	 * @param CharCount
	 *            客戶角色數量
	 * @return
	 */
	public byte[] getCount(int CharCount) {
		writeByte(Opcodes.S_OPCODE_CHAR_COUNT);
		writeByte(CharCount);
		randomInt();
		return getBytes();
	}

	/**
	 * 
	 */
	public S_CharacterList() {
	}

	/**
	 * 建立角色清單的封包
	 * 
	 * @param name
	 *            客戶角色名稱
	 * @param clanName
	 * @param type
	 * @param sex
	 * @param lawful
	 * @param curHp
	 * @param curMp
	 * @param def
	 * @param level
	 * @param str
	 * @param dex
	 * @param con
	 * @param cha
	 * @param wis
	 * @param intel
	 * @param perm
	 */
	public S_CharacterList(String name, String clanName, byte type, byte sex,
			short lawful, short curHp, short curMp, int def, byte level,
			byte str, byte dex, byte con, byte cha, byte wis, byte intel,
			int perm) {
		writeByte(Opcodes.S_OPCODE_CHAR_INFO);
		writeString(name); // 客戶角色名稱
		writeString(clanName); // 客戶角色血盟名稱
		writeByte(type); // 客戶角色種族
		writeByte(sex); // 客戶角色性別
		writeShort(lawful); // 客戶角色正義值
		writeShort(curHp); // 客戶角色目前體力
		writeShort(curMp); // 客戶角色目前魔力
		writeByte(def); // 客戶角色防禦力
		writeByte(level); // 客戶角色等級
		writeByte(str); // 客戶角色力量
		writeByte(dex); // 客戶角色敏捷
		writeByte(con); // 客戶角色體質
		writeByte(cha); // 客戶角色魅力
		writeByte(wis); // 客戶角色精神
		writeByte(intel); // 客戶角色智力

		// is Administrator
		// 0 = false
		// 1 = true , can't attack
		// > 1 true , can't attack
		// can use Public GameMaster Command
		/*
		 * if (accessLevel == 200) { writeC(1); } else { writeC(0); }
		 */
		writeByte(perm);
	}
}
