/**
 * 
 */
package l1j.server;

import java.util.logging.Logger;

/**
 * @author ChrisLiu
 */
public class PacketHandler {

	private final ClientThread _client;

	public PacketHandler(ClientThread clientThread) {
		_client = clientThread;
	}

	private static Logger _log = Logger
			.getLogger(PacketHandler.class.getName());
}
