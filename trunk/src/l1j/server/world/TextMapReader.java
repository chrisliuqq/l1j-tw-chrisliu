package l1j.server.world;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.Config;

/**
 * @author ChrisLiu
 */
public class TextMapReader {

	/**
	 * 讀取指定地圖編號的文字地圖
	 * 
	 * @param mapId
	 *            地圖編號
	 * @param xSize
	 *            X座標的大小
	 * @param ySize
	 *            Y座標的大小
	 * @return byte[][]
	 * @throws IOException
	 */
	public V1Map read(final int mapId) throws IOException {

		V1Map map;
		int attribute, startX, startY, endX, endY;
		LineNumberReader in = new LineNumberReader(new BufferedReader(
				new FileReader(Config.DIR_MAP + mapId + ".txt")));

		int y = 0;
		String line;
		StringTokenizer tok;

		// XXX: ChrisLiu.20101014: 取得第一行，格式如下
		// 屬性, startX, endX, startY, endY
		line = in.readLine();
		tok = new StringTokenizer(line, ",");
		attribute = Integer.parseInt(tok.nextToken());
		startX = Integer.parseInt(tok.nextToken());
		endX = Integer.parseInt(tok.nextToken());
		startY = Integer.parseInt(tok.nextToken());
		endY = Integer.parseInt(tok.nextToken());

		byte[][] mapTile = new byte[endX - startX + 1][endY - startY + 1];
		while ((line = in.readLine()) != null) {
			if (line.trim().length() == 0 || line.startsWith("#")) {
				continue; // 忽略空行和註解
			}

			int x = 0;
			tok = new StringTokenizer(line, ",");
			while (tok.hasMoreTokens()) {
				byte tile = Byte.parseByte(tok.nextToken());
				mapTile[x][y] = tile;

				x++;
			}
			y++;
		}
		map = new V1Map(mapId, mapTile, startX, startY, attribute);
		in.close();
		return map;
	}

	/**
	 * 讀取所有地圖編號的地圖內容
	 * 
	 * @return 地圖編號與地圖內容的 HashMap
	 * @throws IOException
	 */
	public Map<Integer, L1Map> read() throws IOException {
		Map<Integer, L1Map> maps = new HashMap<Integer, L1Map>();

		// XXX: ChrisLiu.20101015: 改為直接去撈資料夾裡面的所有地圖了
		File folder = new java.io.File(Config.DIR_MAP);
		String[] list = folder.list();
		for (String filename : list) {
			int mapId = Integer.parseInt(filename.substring(0,
					filename.indexOf(46)));
			try {
				maps.put(mapId, read(mapId));
			} catch (IOException e) {
				_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			}
			_log.info("共讀取了 " + list.length + " 個地圖檔案。");
		}

		return maps;
	}

	private final static Logger _log = Logger.getLogger(TextMapReader.class
			.getName());
}
