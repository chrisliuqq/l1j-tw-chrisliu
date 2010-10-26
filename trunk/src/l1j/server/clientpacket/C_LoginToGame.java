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
	/**
	 * 使用者登入後點選完角色，將角色登入到伺服器中
	 * 
	 * @param data
	 * @param client
	 */
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
		pc.setClientThread(client);
		// client.setActiveChar(pc);

		pc.sendPacket(new S_LoginGame(), false);

		// XXX: ChrisLiu.2010/10/27: 考慮要不要加上幸運值，不過希望每次上線都重新隨機產生，固定住就不好完了

		// TODO: ChrisLiu.2010/10/27: 設定角色座標

		// TODO: ChrisLiu.2010/10/27: 取得角色的書籤
		getBookmarks(pc);
		// TODO: ChrisLiu.2010/10/27: 取得角色學得的魔法
		getSkills(pc);
		// TODO: ChrisLiu.2010/10/27: 取得角色的物品
		getItems(pc);
		// TODO: ChrisLiu.2010/10/27: 取得角色身上有的魔法狀態
		getEffects(pc);
	}

	private void getBookmarks(L1PcInstance pc) {

	}

	private void getSkills(L1PcInstance pc) {

	}

	private void getItems(L1PcInstance pc) {

	}

	private void getEffects(L1PcInstance pc) {
	}
}
