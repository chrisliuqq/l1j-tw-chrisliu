/**
 * 
 */
package l1j.server.serverpacket;

import java.io.ByteArrayOutputStream;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.Config;
import l1j.Opcodes;

/**
 * @author ChrisLiu
 */
public class ServerBasePacket extends Opcodes {

	protected final static String CLIENT_LANGUAGE_CODE = Config.CLIENT_LANGUAGE_CODE;
	protected Random _random = new Random();
	protected ByteArrayOutputStream _bao = new ByteArrayOutputStream();

	protected void writeInt(int value) {
		_bao.write(value & 0xff);
		_bao.write(value >> 8 & 0xff);
		_bao.write(value >> 16 & 0xff);
		_bao.write(value >> 24 & 0xff);
	}

	protected void writeShort(int value) {
		_bao.write(value & 0xff);
		_bao.write(value >> 8 & 0xff);
	}

	protected void writeByte(int value) {
		_bao.write(value & 0xff);
	}

	// XXX: ChrisLiu.2010/10/15: 暫時移掉，不知道哪會用到
	// protected void writePureInt(int value) {
	// _bao.write(value);
	// }

	protected void writeF(double org) {
		long value = Double.doubleToRawLongBits(org);
		_bao.write((int) (value & 0xff));
		_bao.write((int) (value >> 8 & 0xff));
		_bao.write((int) (value >> 16 & 0xff));
		_bao.write((int) (value >> 24 & 0xff));
		_bao.write((int) (value >> 32 & 0xff));
		_bao.write((int) (value >> 40 & 0xff));
		_bao.write((int) (value >> 48 & 0xff));
		_bao.write((int) (value >> 56 & 0xff));
	}

	protected void writeS(String text) {
		try {
			if (text != null) {
				_bao.write(text.getBytes(CLIENT_LANGUAGE_CODE));
			}
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}

		_bao.write(0);
	}

	protected void writeByte(byte[] text) {
		try {
			if (text != null) {
				_bao.write(text);
			}
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 取得封包內容
	 * 
	 * @return 回傳封包內容
	 */
	public byte[] getBytes() {
		int padding = _bao.size() % 4;

		if (padding != 0) {
			for (int i = padding; i < 4; i++) {
				writeByte(0x00);
			}
		}

		// 定義暫存資料
		byte[] Bytes = _bao.toByteArray().clone();
		// 清除資料
		_bao.reset();
		_bao = null;

		return Bytes;
	}

	protected void randomByte() {
		// 1 x 1
		writeByte((byte) _random.nextInt(256));
	}

	protected void randomShort() {
		// 1 x 2
		randomByte();
		randomByte();
	}

	protected void randomInt() {
		// 2 x 2
		randomShort();
		randomShort();
	}

	/**
	 * 回傳此類別的名稱
	 * 
	 * @return 此類別的名稱
	 */
	public String getType() {
		return "[S] " + this.getClass().getSimpleName();
	}

	private static Logger _log = Logger.getLogger(ServerBasePacket.class
			.getName());
}
