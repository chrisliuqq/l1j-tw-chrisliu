/**
 * 
 */
package l1j.server.clientpacket;

import java.util.logging.Logger;

import l1j.Config;

/**
 * @author ChrisLiu
 */
public class ClientBasePacket {

	private static final String CLIENT_LANGUAGE_CODE = Config.CLIENT_LANGUAGE_CODE; // 5.06
	private boolean _check;
	private byte[] _data;
	private int _off;

	/**
	 * @param data
	 */
	public final void read(byte[] data) {
		if (!_check) {
			_check = true;
			_data = data;
			_off = 1;
		}
	}

	protected final int readInt() {
		int i = 0;
		i |= _data[_off++] << 0 & 0xff;
		i |= _data[_off++] << 8 & 0xff00;
		i |= _data[_off++] << 16 & 0xff0000;
		i |= _data[_off++] << 24 & 0xff000000;
		return i;
	}

	protected final byte readByte() {
		return _data[_off++];
	}

	protected final int readShort() {
		int i = 0;
		i |= _data[_off++] << 0 & 0xff;
		i |= _data[_off++] << 8 & 0xff00;
		return i;
	}

	protected final double readDouble() {
		long l = 0;
		l |= _data[_off++] << 0 & 0xff;
		l |= _data[_off++] << 8 & 0xff00;
		l |= _data[_off++] << 16 & 0xff0000;
		l |= _data[_off++] << 24 & 0xff000000;
		l |= (long) _data[_off++] << 32 & 0xff00000000L;
		l |= (long) _data[_off++] << 40 & 0xff0000000000L;
		l |= (long) _data[_off++] << 48 & 0xff000000000000L;
		l |= (long) _data[_off++] << 56 & 0xff00000000000000L;
		return Double.longBitsToDouble(l);
	}

	protected final String readString() {
		String s = null;

		try {
			s = new String(_data, _off, _data.length - _off,
					CLIENT_LANGUAGE_CODE);
			s = s.substring(0, s.indexOf(0));

			_off += s.getBytes(CLIENT_LANGUAGE_CODE).length + 1;
		} catch (Exception e) {
		}

		return s;
	}

	protected final byte[] readBytes() {
		byte[] result = new byte[_data.length - _off];

		try {
			System.arraycopy(_data, _off, result, 0, _data.length - _off);

			_off = _data.length;
		} catch (Exception e) {
		}

		return result;
	}

	private final static Logger _log = Logger.getLogger(ClientBasePacket.class
			.getName());
}
