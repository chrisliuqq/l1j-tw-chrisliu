<?
	header ('Content-type: text/html; charset=utf-8');
	$db = mysql_connect('localhost', 'root', '');
	mysql_select_db('l1jdb', $db);
	
	$sql = 'SELECT * FROM `mapids`';
	mysql_query('SET NAMES `utf8`');
	$result = mysql_query($sql);
	
	$string_format = "%d,%s,%d\n";
	echo '<pre>';
	while ($row = mysql_fetch_array($result))
	{
		$attr = 0;
		//var_dump($row);
		if ($row['underwater'] == 1) $attr |= 0x0001;
		if ($row['markable'] == 1) $attr |= 0x0002;
		if ($row['teleportable'] == 1) $attr |= 0x0004;
		if ($row['escapable'] == 1) $attr |= 0x0008;
		if ($row['resurrection'] == 1) $attr |= 0x0010;
		if ($row['painwand'] == 1) $attr |= 0x0020;
		if ($row['penalty'] == 1) $attr |= 0x0040;
		if ($row['take_pets'] == 1) $attr |= 0x0080;
		if ($row['recall_pets'] == 1) $attr |= 0x0100;
		if ($row['usable_item'] == 1) $attr |= 0x0200;
		if ($row['usable_skill'] == 1) $attr |= 0x0400;
		printf($string_format, $row['mapid'], $row['locationname'], $attr);
	}
	echo '</pre>';
	mysql_close($db);
?>
