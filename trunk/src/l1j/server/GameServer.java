/**
 * 
 */
package l1j.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import l1j.Config;
import l1j.databases.AccountTable;
import l1j.databases.CharacterTable;
import l1j.databases.IdFactory;
import l1j.databases.NpcTable;
import l1j.server.gametime.GameTimeClock;
import l1j.server.thread.ThreadPoolManager;
import l1j.server.world.WorldMap;
import l1j.util.SystemUtil;

/**
 * @author ChrisLiu
 */
public class GameServer extends Thread {

	private static GameServer _gameServer;
	private ServerSocket _serverSocket;

	private GameServer() throws Exception {
		super("GameServer");

		_gameServer = this;
		_log.info("伺服器啟動中…");
		/**
		 * _log.info("-------------------------------------------");
		 * _log.info("| L1JC Programmed by ChrisLiu 2010        |");
		 * _log.info("|                      Release 2010.10.13 |");
		 * _log.info("-------------------------------------------");
		 * _log.info("經 驗 值： " + Config.RATE_EXP + " 倍"); _log.info("正 義 值： " +
		 * Config.RATE_LAWFUL + " 倍"); _log.info("友 好 度： " + Config.RATE_KARMA +
		 * " 倍"); _log.info("物品掉落： " + Config.RATE_DROP_ITEM + " 倍");
		 * _log.info("金幣掉落：" + Config.RATE_DROP_ADENA + " 倍");
		 * _log.info("廣播等級： " + Config.ALT_GLOBAL_CHAT_LEVEL + " 倍");
		 * _log.info("線上玩家： " + Config.GAME_SERVER_MAX_ONLINE_USER + " 倍");
		 * _log.info("ＰＫ系統： " + Config.RATE_EXP + "");
		 * _log.info("-------------------------------------------");
		 */
		System.out.println("-------------------------------------------");
		System.out.println("| L1JC Programmed by ChrisLiu 2010        |");
		System.out.println("|                      Release 2010.10.13 |");
		System.out.println("-------------------------------------------");
		System.out.println("經 驗 值： " + Config.RATE_EXP + " 倍");
		System.out.println("正 義 值： " + Config.RATE_LAWFUL + " 倍");
		System.out.println("友 好 度： " + Config.RATE_KARMA + " 倍");
		System.out.println("物品掉落： " + Config.RATE_DROP_ITEM + " 倍");
		System.out.println("金幣掉落：" + Config.RATE_DROP_ADENA + " 倍");
		System.out.println("廣播等級： " + Config.ALT_GLOBAL_CHAT_LEVEL + " 倍");
		System.out
				.println("線上玩家： " + Config.GAME_SERVER_MAX_ONLINE_USER + " 倍");
		System.out.println("ＰＫ系統： " + Config.RATE_EXP + "");
		System.out.println("-------------------------------------------");

		// 初始化唯一的 key
		IdFactory.getInstance();
		// 初始化 ThreadPool
		ThreadPoolManager.getInstance();
		// 初始化伺服器時間
		GameTimeClock.getInstance();
		// 初始化世界地圖
		WorldMap.getInstance();
		// 讀取所有角色名稱
		// XXX: ChrisLiu.20101014: 為什麼要讀取？
		// CharacterTable.getInstance().loadAllCharName();
		// 清除角色上線狀態
		CharacterTable.clearOnlineStatus();
		// 清除帳號上線狀態
		AccountTable.resetOnlineStatus();

		// 遊戲帳號驗證控制器
		LoginController.getInstance();
		LoginController.getInstance().setMaxAllowedOnlinePlayers(
				Config.GAME_SERVER_MAX_ONLINE_USER);

		// FIXME: ChrisLiu.20101014: 先跳過這些
		/*
		 * // 無限大戰時間控制器 UbTimeController ubTimeContoroller =
		 * UbTimeController.getInstance();
		 * ThreadPoolManager.getInstance().execute(ubTimeContoroller); //
		 * 攻城戰爭時間控制器 WarTimeController warTimeController =
		 * WarTimeController.getInstance();
		 * ThreadPoolManager.getInstance().execute(warTimeController); //
		 * 妖精森林產生元素石 if (Config.ELEMENTAL_STONE_AMOUNT > 0) {
		 * ElementalStoneGenerator elementalStoneGenerator =
		 * ElementalStoneGenerator.getInstance();
		 * ThreadPoolManager.getInstance().execute(elementalStoneGenerator); }
		 * // 村莊系統時間控制器 HomeTownTimeController.getInstance(); // 盟屋拍賣時間控制器
		 * AuctionTimeController auctionTimeController =
		 * AuctionTimeController.getInstance();
		 * ThreadPoolManager.getInstance().execute(auctionTimeController); //
		 * 盟屋稅金時間控制器 HouseTaxTimeController houseTaxTimeController =
		 * HouseTaxTimeController.getInstance();
		 * ThreadPoolManager.getInstance().execute(houseTaxTimeController); //
		 * 釣魚系統時間控制器 FishingTimeController fishingTimeController =
		 * FishingTimeController.getInstance();
		 * ThreadPoolManager.getInstance().execute(fishingTimeController); //
		 * NPC 說話時間控制器 NpcChatTimeController npcChatTimeController =
		 * NpcChatTimeController.getInstance();
		 * ThreadPoolManager.getInstance().execute(npcChatTimeController); //
		 * 光線變化時間控制器 LightTimeController lightTimeController =
		 * LightTimeController.getInstance();
		 * ThreadPoolManager.getInstance().execute(lightTimeController); //
		 * 時空裂痕時間控制器 CrackTimeController crackTimeController =
		 * CrackTimeController.getStart();
		 * ThreadPoolManager.getInstance().execute(crackTimeController);
		 */

		// 初始化公告
		Announcements.getInstance();
		// 初始化 NPC
		NpcTable.getInstance();

		if (!NpcTable.getInstance().isInitialized())
			throw new Exception("初始化 NpcTable 的時候發生問題。");

		// FIXME: ChrisLiu.20101014: 先跳過這些
		/*
		 * L1DeleteItemOnGround deleteitem = new L1DeleteItemOnGround();
		 * deleteitem.initialize();
		 */
		/*
		 * SpawnTable.getInstance(); MobGroupTable.getInstance();
		 * SkillsTable.getInstance(); PolyTable.getInstance();
		 * ItemTable.getInstance(); DropTable.getInstance();
		 * DropItemTable.getInstance(); ShopTable.getInstance();
		 * NPCTalkDataTable.getInstance(); L1World.getInstance();
		 * L1WorldTraps.getInstance(); Dungeon.getInstance();
		 * NpcSpawnTable.getInstance(); IpTable.getInstance();
		 * MapsTable.getInstance(); UBSpawnTable.getInstance();
		 * PetTable.getInstance(); ClanTable.getInstance();
		 * CastleTable.getInstance(); L1CastleLocation.setCastleTaxRate(); //
		 * 必須在 CastleTable 初始化之後 GetBackRestartTable.getInstance();
		 * DoorSpawnTable.getInstance(); L1NpcRegenerationTimer.getInstance();
		 * WeaponSkillTable.getInstance(); NpcActionTable.load();
		 * GMCommandsConfig.load(); Getback.loadGetBack(); PetTypeTable.load();
		 * L1BossCycle.load(); TreasureBox.load(); SprTable.getInstance();
		 * ResolventTable.getInstance(); FurnitureSpawnTable.getInstance();
		 * NpcChatTable.getInstance(); MailTable.getInstance();
		 */
		System.gc();
		_log.info("伺服器初始化完畢");

		if (!Config.GAME_SERVER_HOST_NAME.equals("*")) {
			InetAddress inetAddress = InetAddress
					.getByName(Config.GAME_SERVER_HOST_NAME);
			inetAddress.getHostAddress();
			_serverSocket = new ServerSocket(Config.GAME_SERVER_PORT, 50,
					inetAddress);
		} else {
			_serverSocket = new ServerSocket(Config.GAME_SERVER_PORT);
		}

		_log.info("伺服器成功建立 Socket。");

		this.start();
	}

	/**
	 * @return 遊戲伺服器的實體
	 * @throws Exception
	 */
	public static GameServer getInstance() throws Exception {
		if (null == _gameServer) {
			_gameServer = new GameServer();
		}
		return _gameServer;
	}

	@Override
	public void run() {
		System.out.println("記憶體使用量: " + SystemUtil.getUsedMemoryMB() + "MB");
		System.out.println("等待客戶端連線中...");

		while (true) {
			try {
				Socket socket = _serverSocket.accept();
				_log.info("IP " + socket.getInetAddress() + "連進來伺服器。");
				// XXX: ChrisLiu.20101014: 先跳過 IpTable，反正現在有能力的人不多…
				/*
				 * String host = socket.getInetAddress().getHostAddress(); if
				 * (IpTable.getInstance().isBannedIp(host)) {
				 * _log.info("banned IP(" + host + ")"); } else { ClientThread
				 * client = new ClientThread(socket);
				 * ThreadPoolManager.getInstance().execute(client); }
				 */
				ClientThread client = new ClientThread(socket);
				ThreadPoolManager.getInstance().execute(client);
			} catch (IOException e) {
			}
		}

	}

	private static final Logger _log = Logger.getLogger(GameServer.class
			.getName());
}
