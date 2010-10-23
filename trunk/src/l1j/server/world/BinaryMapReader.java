/**
 * 
 */
package l1j.server.world;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.Config;

/**
 * @author ChrisLiu
 */
public class BinaryMapReader {
	/**
	 * 讀取指定地圖編號的文字地圖
	 * 
	 * @param mapId
	 *            地圖編號
	 * @return byte[][]
	 * @throws IOException
	 */
	public L1Map read(final int mapId) throws IOException {

		L1Map map;
		int mapid, attribute, startX, startY, endX, endY, x, y, lengthX, lengthY;
		byte tile;
		/*
		 * LineNumberReader in = new LineNumberReader(new BufferedReader( new
		 * FileReader(Config.DIR_MAP + mapId + ".txt")));
		 */

		DataInputStream dis = new DataInputStream(new FileInputStream(
				Config.DIR_MAP + mapId + ".txt"));

		// XXX: ChrisLiu.20101021: 全部改為 binary 的方式，因為長度固定，所以不用間隔了
		// mapId, startX, endX, startY, endY, 屬性

		mapid = dis.readUnsignedShort();
		startX = dis.readUnsignedShort();
		endX = dis.readUnsignedShort();
		startY = dis.readUnsignedShort();
		endY = dis.readUnsignedShort();
		attribute = dis.readUnsignedShort();
		lengthX = endX - startX + 1;
		lengthY = endY - startY + 1;
		// XXX: ChrisLiu.2010/10/22: DEBUG 用
		/*
		 * System.out.println("mapid : " + mapid); System.out.println(startX +
		 * "," + endX); System.out.println(startY + "," + endY);
		 * System.out.println(lengthX + "," + lengthY);
		 * System.out.println("attribute : " + attribute);
		 */
		byte[][] mapTile = new byte[lengthX][lengthY];
		y = 0;
		while (y < lengthY) {
			x = 0;
			while (x < lengthX) {
				if ((tile = (byte) dis.readUnsignedByte()) > -1) {
					mapTile[x][y] = tile;
					x++;
				}

			}
			System.out.println(x + "," + y);
			y++;
		}
		map = new L1Map(mapid, mapTile, startX, startY, attribute);
		dis.close();
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
			// 跳過 .svn 資料夾
			if (filename.startsWith(".")) {
				continue;
			}
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
