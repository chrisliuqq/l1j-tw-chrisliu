/**
 * 
 */
package l1j.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.Config;
import l1j.util.StreamUtil;

/**
 * @author ChrisLiu
 */
// XXX: ChrisLiu.2010/10/24: 未來考慮加上跟網頁結合實現多筆公告，並作個管理公告的介面。
// XXX: ChrisLiu.2010/10/24: 短期內先改成讀取公告資料夾底下的所有 txt 檔並顯示。
public class Announcements {

	private Announcements() {
		loadAnnouncements();
	}

	private ArrayList<String> news;
	private int count = 1;
	private final List<String> _messages = new ArrayList<String>();

	/**
	 * 取得 Announcements 的實體
	 * 
	 * @return Announcements 的實體
	 */
	public static Announcements getInstance() {
		return SingletonHolder._instance;
	}

	private void loadAnnouncements() {
		_messages.clear();
		File file = new File(Config.DIR_ANNOUNCEMENT);
		if (file.exists()) {
			readFromDisk(file);
		} else {
			_log.config("data/announcements.txt doesn't exist");
		}
	}

	private void readFromDisk(File file) {
		LineNumberReader lnr = null;
		try {
			int i = 0;
			String line = null;
			lnr = new LineNumberReader(new FileReader(file));
			while ((line = lnr.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "\n\r");
				if (st.hasMoreTokens()) {
					String announcement = st.nextToken();
					_messages.add(announcement);
					i++;
				}
			}
			if (Config.DEBUG) {
				_log.config("讀取了 " + i + " 件公告.");
			}
		} catch (IOException e) {
			_log.log(Level.SEVERE, "讀取公告時發生錯誤", e);
		} finally {
			StreamUtil.close(lnr);
		}
	}

	private void loadFromFile() {
		LineNumberReader in = null;
		String line;

		try {
			in = new LineNumberReader(new BufferedReader(new FileReader(
					"etc/news/announcements.txt")));

			while ((line = in.readLine()) != null) {
				if (line.trim().length() == 0 || line.startsWith("#")) {
					continue; // 忽略空行和註解
				}
				news.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	private static class SingletonHolder {
		protected static final Announcements _instance = new Announcements();
	}

	private final static Logger _log = Logger.getLogger(Announcements.class
			.getName());
}
