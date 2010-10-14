/**
 * 
 */
package l1j.server.serverpacket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.Config;

/**
 * @author ChrisLiu
 */
public abstract class ServerBasePacket {

	private static final String CLIENT_LANGUAGE_CODE = Config.CLIENT_LANGUAGE_CODE;

	ByteArrayOutputStream _bao = new ByteArrayOutputStream();

	protected ServerBasePacket() {
	}

	protected void writeD(int value) {
		_bao.write(value & 0xff);
		_bao.write(value >> 8 & 0xff);
		_bao.write(value >> 16 & 0xff);
		_bao.write(value >> 24 & 0xff);
	}

	protected void writeH(int value) {
		_bao.write(value & 0xff);
		_bao.write(value >> 8 & 0xff);
	}

	protected void writeC(int value) {
		_bao.write(value & 0xff);
	}

	protected void writeP(int value) {
		_bao.write(value);
	}

	protected void writeL(long value) {
		_bao.write((int) (value & 0xff));
	}

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
				writeC(0x00);
			}
		}

		return _bao.toByteArray();
	}

	/**
	 * 取得封包內容
	 * 
	 * @return 回傳封包內容
	 * @throws IOException
	 */
	public abstract byte[] getContent() throws IOException;

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
