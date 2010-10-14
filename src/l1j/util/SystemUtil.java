/**
 * 
 */
package l1j.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author ChrisLiu
 */
public class SystemUtil {
	private static Runtime runTime = Runtime.getRuntime();

	/**
	 * 傳回記憶體使用量
	 * 
	 * @return 記憶體使用量
	 */
	public static long getUsedMemoryMB() {
		return (runTime.totalMemory() - runTime.freeMemory()) / 1024L / 1024L;
	}

	/**
	 * 傳回記憶體使用量上限
	 * 
	 * @return 記憶體最大可用量
	 */
	public static long getTotalMemoryMB() {
		return runTime.totalMemory() / 1024L;
	}

	/**
	 * 傳回記憶體尚未使用量
	 * 
	 * @return 記憶體尚未使用量
	 */
	public static long getFreeMemoryMB() {
		return runTime.freeMemory() / 1024L;
	}
	// XXX: ChrisLiu.20101014: 沒用到暫時註解起來
	/*
	 * public static String getStackTrace(Throwable t) { StringWriter sw = new
	 * StringWriter(); t.printStackTrace(new PrintWriter(sw)); return
	 * sw.toString(); }
	 */
}
