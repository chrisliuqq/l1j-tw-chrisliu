/**
 * 
 */
package l1j.server.clientpacket;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.Config;
import l1j.databases.AccountTable;
import l1j.server.Announcements;
import l1j.server.ClientThread;
import l1j.server.model.Account;
import l1j.server.serverpacket.S_LoginResult;

/**
 * @author ChrisLiu
 */
public class C_AuthLogin extends ClientBasePacket {

	/**
	 * 客戶端登入，需要認證帳號密碼與線上狀態
	 * 
	 * @param data
	 * @param client
	 */
	public C_AuthLogin(byte[] data, ClientThread client) {
		read(data);

		// 取得帳號
		String username = readString().toLowerCase();
		// 取得密碼
		String password = readString();
		Account account = AccountTable.getInstance().load(username);
		// 如果帳號不存在
		// XXX: ChrisLiu.2010/10/24: 以後在加密碼錯三次要踢掉。
		if (null == account) {
			if (Config.AUTO_CREATE_ACCOUNT) {
				client.sendPacket(new S_LoginResult(
						S_LoginResult.EVENT_REGISTER_ACCOUNTS));
				return;
			}
			client.sendPacket(new S_LoginResult(
					S_LoginResult.EVENT_PASSWORD_MISTAKE));
			return;
		} else {
			// 驗證密碼
			try {
				if (!account.validatePassword(password)) {
					// 送出帳號密碼錯誤的封包。
					client.sendPacket(new S_LoginResult(
							S_LoginResult.EVENT_PASSWORD_MISTAKE));
					return;
				}

			} catch (NoSuchAlgorithmException e) {
				_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			} catch (UnsupportedEncodingException e) {
				_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			}
		}
		// 如果帳號存在且密碼正確的話就繼續判斷

		// 如果帳號已經在線上了
		if (account.isOnline()) {
			// 使用中
			client.sendPacket(new S_LoginResult(S_LoginResult.EVENT_IN_USE));
			account.updateOnlineStatus(0);
			// 踢除登入用戶
			try {
				/*
				 * 同時 踢除線上用戶 以及 踢除登入用戶 理由: 防止盜用帳號登入後踢除人物可立即上線
				 */
				Thread.sleep(1000);
				client.kick(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}

		client.sendPacket(new S_LoginResult(S_LoginResult.EVENT_LOGIN_OK));

		// 如果有公告的話
		if (Announcements.getInstance().getCount() > 0) {
			// foreach 送出公告
			// XXX: ChrisLiu.2010/10/24: 暫時寫到這，最近要後製影片+轉檔，都沒時間了 Q_____Q
			// 下次要從這邊開始寫
		}
	}

	private static Logger _log = Logger.getLogger(C_AuthLogin.class.getName());
}
