/**
 * 
 */
package l1j.server.clientpacket;

import l1j.databases.CharacterTable;
import l1j.server.ClientThread;
import l1j.server.model.instance.L1PcInstance;
import l1j.server.serverpacket.S_LoginGame;

/**
 * @author ChrisLiu
 */
public class C_LoginToGame extends ClientBasePacket {
	public C_LoginToGame(byte[] data, ClientThread client) {
		read(data);

		// 取得角色名稱
		String charName = readString();
		// 登入帳號
		String userName = client.getUsername();
		// XXX: ChrisLiu.2010/10/24: L1PcInstance 尚未實做，整理一下架構之後在考慮資料庫要怎麼規劃
		// 取得角色資料
		L1PcInstance pc = CharacterTable.loadCharacter(charName);
		// 設定上線狀態
		pc.updateOnlineStatus(1);

		pc.sendPacket(new S_LoginGame(), false);
	}
}
