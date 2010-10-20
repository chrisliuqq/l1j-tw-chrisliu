/**
 * 
 */
package l1j.server.world;

/**
 * @author ChrisLiu
 */
public class L1Map {
	private int _mapId;
	private int _startX;
	private int _startY;
	private int _endX;
	private int _endY;
	private byte _map[][];
	private boolean _isUnderwater;
	private boolean _isMarkable;
	private boolean _isTeleportable;
	private boolean _isEscapable;
	private boolean _isUseResurrection;
	private boolean _isUsePainwand;
	private boolean _isEnabledDeathPenalty;
	private boolean _isTakePet;
	private boolean _isRecallPet;
	private boolean _isUsableItem;
	private boolean _isUsableSkill;

	// XXX: ChrisLiu.20101013: 決定採用 bitwise
	private final int UNDER_WATER = 0x0001;
	private final int MARLABLE = 0x0002;
	private final int TELEPORTABLE = 0x0004;
	private final int ESCAPABLE = 0x0008;
	private final int USE_RESURRECTION = 0x0010;
	private final int USE_PAINWAND = 0x0020;
	private final int ENABLED_DEATH_PENALTY = 0x0040;
	private final int TALE_PET = 0x0080;
	private final int RECALL_PET = 0x0100;
	private final int USABLE_ITEM = 0x0200;
	private final int USABLE_SKILL = 0x0400;

	private static final byte BITFLAG_IS_IMPASSABLE = (byte) 128; // 1000 0000

	protected L1Map() {
	}

	/**
	 * V1Map 的建構子
	 * 
	 * @param mapId
	 *            地圖編號
	 * @param map
	 *            地圖 raw data
	 * @param startX
	 *            X軸的起始座標
	 * @param startY
	 *            Y軸的起始座標
	 * @param attribute
	 *            地圖的屬性
	 */
	public L1Map(int mapId, byte map[][], int startX, int startY, int attribute) {
		_mapId = mapId;
		_map = map;
		_startX = startX;
		_startY = startY;

		_endX = startX + map.length - 1;
		_endY = startY + map[0].length - 1;

		_isUnderwater = (attribute & UNDER_WATER) == 1 ? true : false;
		_isMarkable = (attribute & MARLABLE) == 1 ? true : false;
		_isTeleportable = (attribute & TELEPORTABLE) == 1 ? true : false;
		_isEscapable = (attribute & ESCAPABLE) == 1 ? true : false;
		_isUseResurrection = (attribute & USE_RESURRECTION) == 1 ? true : false;
		_isUsePainwand = (attribute & USE_PAINWAND) == 1 ? true : false;
		_isEnabledDeathPenalty = (attribute & ENABLED_DEATH_PENALTY) == 1 ? true
				: false;
		_isTakePet = (attribute & TALE_PET) == 1 ? true : false;
		_isRecallPet = (attribute & RECALL_PET) == 1 ? true : false;
		_isUsableItem = (attribute & USABLE_ITEM) == 1 ? true : false;
		_isUsableSkill = (attribute & USABLE_SKILL) == 1 ? true : false;
	}

	/**
	 * @return the _mapId
	 */
	public int get_mapId() {
		return _mapId;
	}

	/**
	 * @param _mapId
	 *            the _mapId to set
	 */
	public void set_mapId(int _mapId) {
		this._mapId = _mapId;
	}

	/**
	 * @return the _startX
	 */
	public int get_startX() {
		return _startX;
	}

	/**
	 * @param _startX
	 *            the _startX to set
	 */
	public void set_startX(int _startX) {
		this._startX = _startX;
	}

	/**
	 * @return the _startY
	 */
	public int get_startY() {
		return _startY;
	}

	/**
	 * @param _startY
	 *            the _startY to set
	 */
	public void set_startY(int _startY) {
		this._startY = _startY;
	}

	/**
	 * @return the _endX
	 */
	public int get_endX() {
		return _endX;
	}

	/**
	 * @param _endX
	 *            the _endX to set
	 */
	public void set_endX(int _endX) {
		this._endX = _endX;
	}

	/**
	 * @return the _endY
	 */
	public int get_endY() {
		return _endY;
	}

	/**
	 * @param _endY
	 *            the _endY to set
	 */
	public void set_endY(int _endY) {
		this._endY = _endY;
	}

	/**
	 * @return the _map
	 */
	public byte[][] getRawTiles() {
		return _map;
	}

	/**
	 * @param _map
	 *            the _map to set
	 */
	// FIXME: ChrisLiu.20101014: 研究並改寫他
	/*
	 * private void setTile(int x, int y, int tile) { if (!isInMap(x, y)) { //
	 * XXX とりあえずチェックする。これは良くない。 return; } _map[x - _worldTopLeftX][y -
	 * _worldTopLeftY] = (byte) tile; }
	 */
	public void setTile(byte[][] _map) {
		this._map = _map;
	}

	/**
	 * @return the _isUnderwater
	 */
	public boolean is_isUnderwater() {
		return _isUnderwater;
	}

	/**
	 * @param _isUnderwater
	 *            the _isUnderwater to set
	 */
	public void set_isUnderwater(boolean _isUnderwater) {
		this._isUnderwater = _isUnderwater;
	}

	/**
	 * @return the _isMarkable
	 */
	public boolean is_isMarkable() {
		return _isMarkable;
	}

	/**
	 * @param _isMarkable
	 *            the _isMarkable to set
	 */
	public void set_isMarkable(boolean _isMarkable) {
		this._isMarkable = _isMarkable;
	}

	/**
	 * @return the _isTeleportable
	 */
	public boolean is_isTeleportable() {
		return _isTeleportable;
	}

	/**
	 * @param _isTeleportable
	 *            the _isTeleportable to set
	 */
	public void set_isTeleportable(boolean _isTeleportable) {
		this._isTeleportable = _isTeleportable;
	}

	/**
	 * @return the _isEscapable
	 */
	public boolean is_isEscapable() {
		return _isEscapable;
	}

	/**
	 * @param _isEscapable
	 *            the _isEscapable to set
	 */
	public void set_isEscapable(boolean _isEscapable) {
		this._isEscapable = _isEscapable;
	}

	/**
	 * @return the _isUseResurrection
	 */
	public boolean is_isUseResurrection() {
		return _isUseResurrection;
	}

	/**
	 * @param _isUseResurrection
	 *            the _isUseResurrection to set
	 */
	public void set_isUseResurrection(boolean _isUseResurrection) {
		this._isUseResurrection = _isUseResurrection;
	}

	/**
	 * @return the _isUsePainwand
	 */
	public boolean is_isUsePainwand() {
		return _isUsePainwand;
	}

	/**
	 * @param _isUsePainwand
	 *            the _isUsePainwand to set
	 */
	public void set_isUsePainwand(boolean _isUsePainwand) {
		this._isUsePainwand = _isUsePainwand;
	}

	/**
	 * @return the _isEnabledDeathPenalty
	 */
	public boolean is_isEnabledDeathPenalty() {
		return _isEnabledDeathPenalty;
	}

	/**
	 * @param _isEnabledDeathPenalty
	 *            the _isEnabledDeathPenalty to set
	 */
	public void set_isEnabledDeathPenalty(boolean _isEnabledDeathPenalty) {
		this._isEnabledDeathPenalty = _isEnabledDeathPenalty;
	}

	/**
	 * @return the _isTakePet
	 */
	public boolean is_isTakePet() {
		return _isTakePet;
	}

	/**
	 * @param _isTakePet
	 *            the _isTakePet to set
	 */
	public void set_isTakePet(boolean _isTakePet) {
		this._isTakePet = _isTakePet;
	}

	/**
	 * @return the _isRecallPet
	 */
	public boolean is_isRecallPet() {
		return _isRecallPet;
	}

	/**
	 * @param _isRecallPet
	 *            the _isRecallPet to set
	 */
	public void set_isRecallPet(boolean _isRecallPet) {
		this._isRecallPet = _isRecallPet;
	}

	/**
	 * @return the _isUsableItem
	 */
	public boolean is_isUsableItem() {
		return _isUsableItem;
	}

	/**
	 * @param _isUsableItem
	 *            the _isUsableItem to set
	 */
	public void set_isUsableItem(boolean _isUsableItem) {
		this._isUsableItem = _isUsableItem;
	}

	/**
	 * @return the _isUsableSkill
	 */
	public boolean is_isUsableSkill() {
		return _isUsableSkill;
	}

	/**
	 * @param _isUsableSkill
	 *            the _isUsableSkill to set
	 */
	public void set_isUsableSkill(boolean _isUsableSkill) {
		this._isUsableSkill = _isUsableSkill;
	}
}
