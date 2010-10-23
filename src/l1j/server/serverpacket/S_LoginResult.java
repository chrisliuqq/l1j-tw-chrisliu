package l1j.server.serverpacket;

import l1j.Opcodes;

/**
 * 登入的結果
 * 
 * @author ChrisLiu
 */
public class S_LoginResult extends ServerBasePacket {

	/**
	 * @param status
	 */
	public S_LoginResult(int status) {
		if (status == 0) {
			writeByte(Opcodes.S_OPCODE_LOGIN_OK);
		} else {
			writeByte(Opcodes.S_OPCODE_LOGIN_FAILD);
		}

		writeByte(status);

		for (int Count = 0; Count < 3; Count++) {
			writeInt(0x00000000);
		}
	}

	// XXX: ChrisLiu.2010/10/24: 以下訊息由 kiusbt 提供
	/**
	 * 登入伺服器成功 ( 無訊息 )
	 */
	public static final byte EVENT_LOGIN_OK = 0x00;

	/**
	 * 無法登入的原因如下： 1.您的帳號密碼輸入錯誤。 2.帳號受晶片卡保護但未使用晶片卡登入。 若仍有疑問請洽客服中心02-8024-2002
	 */
	public static final byte EVENT_PASSWORD_MISTAKE = 0x08;

	/**
	 * 已經使用中。
	 */
	public static final byte EVENT_IN_USE = 0x16;

	/**
	 * 您的帳號目前無法使用，請至GASH會員中心 『大事紀』查詢原因。GASH會員中心網址： http://tw。gamania。com/
	 */
	public static final byte EVENT_CANT_USE = 0x1a;

	/**
	 * 您無法順利登入，可能原因如下： 使用期間結束了。請在GASH會員中心 (http://tw。gamania。com/)延長使用時間 。
	 */
	public static final byte EVENT_CANT_LOGIN = 0x1c;

	/**
	 * 用戶註冊介面
	 */
	public static final byte EVENT_REGISTER_ACCOUNTS = 0x2f;

	/**
	 * 無法順利取得連線
	 */
	public static final byte EVENT_LAN_ERROR = 0x27;

	/**
	 * 您的電子郵件是無效的。請再輸入電子郵件的正確的地址。
	 */
	public static final byte EVENT_MAIL_ERROR = 0x0b;

	/**
	 * 已經有同樣的帳號。請重新輸入。
	 */
	public static final byte EVENT_ACC_ERROR = 0x07;

	/**
	 * 國家名稱是無效的。請重新輸入國家名稱。
	 */
	public static final byte EVENT_C_ERROR = 0x11;

	/**
	 * 回到登入畫面
	 */
	public static final byte EVENT_RE_LOGIN = 0x04;

	/**
	 * 您的天堂點數已經使用完畢，請至開卡中心 確認剩餘點數並轉移點數至天堂遊戲中。
	 */
	public static final byte EVENT_CLOCK_ERROR = 0x32;

	/**
	 * 這個角色名稱是不合法的。
	 */
	public static final byte EVENT_NAME_ERROR = 0x34;

	/**
	 * 您無法在此變更密碼，請上天堂網頁開卡中心變更。
	 */
	public static final byte EVENT_PASS_ERROR = 0x35;

	/**
	 * 您輸入的密碼錯誤。再次確認您所輸入的密碼是否正確，或電洽遊戲橘子客服中心(02)8024-2002。
	 */

	public static final byte EVENT_PASS_CHECK = 0x0a;
}
