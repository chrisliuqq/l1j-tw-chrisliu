/**
 * 
 */
package l1j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author ChrisLiu
 */
// XXX: ChrisLiu.2010/10/15: 將 OPCODE 改為用檔案設定，方便開發者測試 OPCODE，改 OPCODE 不用在重編核心了
public final class Opcodes {

	private Opcodes() throws Exception {
	}

	public final int i = 0;
	public static final int S_OPCODE_INITIAL_KEY;

	static {
		InputStream _is = null;
		Properties _properties = null;
		// 讀取遊戲伺服器相關的設定

		try {
			_is = new FileInputStream(new File(Config.OPCODE_FILE));
			_properties = new Properties();
			_properties.load(_is);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		S_OPCODE_INITIAL_KEY = Integer.parseInt(_properties.getProperty(
				"S_OPCODE_INITIAL_KEY", "30"));

	}

	private static final Logger _log = Logger
			.getLogger(Opcodes.class.getName());
}
