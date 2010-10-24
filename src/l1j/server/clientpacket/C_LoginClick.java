/**
 * 
 */
package l1j.server.clientpacket;

import java.io.IOException;

import l1j.databases.CharacterTable;
import l1j.server.ClientThread;
import l1j.server.serverpacket.S_CharacterList;

/**
 * @author ChrisLiu
 */
public class C_LoginClick extends ClientBasePacket {
	public C_LoginClick(byte[] data, ClientThread client) throws IOException {
		// 進入角色清單
		// if (hasMoreAnnoucement)
		// 如果還有公告，則送出公告，繼續看公告
		// 如果沒有則送出角色清單
		// else
		// 進入角色清單
		S_CharacterList[] chars = CharacterTable.getCharacterListPackage(client
				.getUsername());
		client.sendPacket(new S_CharacterList().getCount(chars.length), false);
		for (S_CharacterList charPack : chars) {
			client.sendPacket(charPack, false);
		}
		client.sendPacket((byte[]) null, true);
	}
}
