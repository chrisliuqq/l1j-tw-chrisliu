using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace L1Tool.util
{
	class MapPackage
	{
		private DirectoryInfo[] _mapFolderInfo;
		private Hashtable _mapSets;
		private Hashtable _bufTable;
		private string _mapDataPath;
		private ArrayList _mapIdList;
		
		public MapPackage(DirectoryInfo[] map)
		{
			_mapFolderInfo = map;
			_mapDataPath = @"map.dat";
			_mapIdList = new ArrayList();
			_mapSets = new Hashtable();
		}

		public void loadClientMap(bool readTile)
		{
			MapSet mapSet = null;
			foreach (DirectoryInfo di in _mapFolderInfo)
			{
				mapSet = new MapSet(int.Parse(di.Name));
				readMap(di, ref mapSet, readTile);
				_mapSets.Add(int.Parse(di.Name), mapSet);
				_mapIdList.Add(int.Parse(di.Name));
			}

			loadMapSets(ref _mapSets);
			//_mapIdList.Sort(new myComparer(myComparer.CompareType.intType));
			_mapIdList.Sort(new myComparer());
		}

		private void loadMapSets(ref Hashtable mapSet)
		{
			checkMapData();
			// Application.StartupPath + @"\settings.ini"
			StreamReader objReader = new StreamReader(_mapDataPath);
			string bufLine = "";
			string[] bufArray;

			while (bufLine != null)
			{
				bufLine = objReader.ReadLine();
				if (bufLine != null)
				{
					bufArray = bufLine.Split(',');
					//_mapSets.Add(int.Parse(bufArray[0]), new MapSet(int.Parse(bufArray[0]), bufArray[1], int.Parse(bufArray[2])));
					if (mapSet.ContainsKey(int.Parse(bufArray[0])))
					{
						((MapSet)mapSet[int.Parse(bufArray[0])]).mapName = bufArray[1];
						((MapSet)mapSet[int.Parse(bufArray[0])]).mapAttr = int.Parse(bufArray[2]);
					}
				}
			}



		}

		private void checkMapData()
		{
			if (!File.Exists(_mapDataPath))
			{
				File.Copy(@"defaultMap.dat", "map.dat");
			}
		}

		public ArrayList getMapIdList()
		{
			return _mapIdList;
		}

		public MapSet getMapSet(int mapId)
		{
			return (MapSet) _mapSets[mapId];
		}

		private void readMap(DirectoryInfo di, ref MapSet ms, bool isReadTile)
		{
			
			int min_areax = 65535;
			int max_areax = 0;
			int min_areay = 65535;
			int max_areay = 0;
			int area_x = 0;
			int area_y = 0;
			
			FileSystemInfo[] _mapFileInfo;
			string bufFileName = null;
			string index = null;
			_bufTable = new Hashtable();
			
			_mapFileInfo = di.GetFileSystemInfos();

			// 先取得地圖的大小
			foreach (FileSystemInfo fi in _mapFileInfo)
			{
				// 轉換成小寫
				bufFileName = fi.Name.ToLower();
				if (bufFileName.Length == 12 && !bufFileName.EndsWith("ini"))
				{
					index = bufFileName.Substring(0, 8).ToLower();

					if (!_bufTable.ContainsKey(index))
					{
						_bufTable.Add(index, bufFileName);
						area_x = Convert.ToInt32(bufFileName.Substring(0, 4), 16);
						area_y = Convert.ToInt32(bufFileName.Substring(4, 4), 16);
						min_areax = Math.Min(min_areax, area_x);
						min_areay = Math.Min(min_areay, area_y);
						max_areax = Math.Max(max_areax, area_x);
						max_areay = Math.Max(max_areay, area_y);
					}
				}
			}

			if (_bufTable.Count == 0)
			{
				return;
			}

			// 借用 area_x 和 area_y 來當作 length_x 和 length_y
			area_x = (max_areax - min_areax + 1) * 64;
			area_y = (max_areay - min_areay + 1) * 64;

			ms.lengthX = (max_areax - min_areax + 1) * 64;
			ms.lengthY = (max_areay - min_areay + 1) * 64;
			ms.endX = (max_areax - 0x7fff) * 64 + 32767;
			ms.endY = (max_areay - 0x7fff) * 64 + 32767;
			ms.startX = ms.endX - area_x + 1;
			ms.startY = ms.endY - area_y + 1;
			ms.mapFolder = di;
			ms.minAreaX = max_areax;
			ms.minAreaY = max_areay;

			if (!isReadTile)
			{
				return;
			}

			ms.tile = readTile(ms);
		}

		private int[,] readTile(MapSet ms)
		{
			int[,] bufTile;
			byte[] decrypt;
			bufTile = new int[ms.lengthX, ms.lengthY];
			FileSystemInfo[] _mapFileInfo;
			string bufFileName = null;
			string index = null;		
			_mapFileInfo = ms.mapFolder.GetFileSystemInfos();
			int bufx, bufy;


			// 讀取地圖檔內容
			foreach (FileSystemInfo fi in _mapFileInfo)
			{
				//取得地圖名稱           
				bufFileName = fi.Name.ToLower();
				index = bufFileName.Substring(0, 8).ToLower();
				if (_bufTable.ContainsKey(index))
				{
					bufx = Convert.ToInt32(index.Substring(0, 4), 16);
					bufy = Convert.ToInt32(index.Substring(4, 4), 16);
					decrypt = File.ReadAllBytes(fi.FullName);

					if (bufFileName.EndsWith(".s32"))
					{
						readS32(decrypt, bufx - ms.minAreaX, bufy - ms.minAreaY, ref bufTile);
					}
					else if (bufFileName.EndsWith(".seg"))
					{
						readSeg(decrypt, bufx - ms.minAreaX, bufy - ms.minAreaY, ref bufTile);
					}
					_bufTable.Remove(index);
				}
			}

			//ms.tile = bufTile;
			//bufTile = null;
			return bufTile;
		}

		private void readS32(byte[] data, int x_length, int y_length, ref int[,] tile)
		{
			MemoryStream ms = new MemoryStream(data);
			BinaryReader br = new BinaryReader(ms);
			int off = 32768;
			br.BaseStream.Seek(off, SeekOrigin.Begin);
			int unknow1 = br.ReadUInt16();
			off += unknow1 * 6;
			br.BaseStream.Seek(off + 2, SeekOrigin.Begin);

			int d1 = 0, d2 = 0, x = 0, y = 0;
			int count = 0;
			int count2 = -1;
			while (true)
			{
				if (count % 64 == 0)
				{
					count2++;
					count = 0;
				}
				if (count2 >= 64)
				{
					break;
				}
				d1 = br.ReadByte();
				br.ReadByte();
				d2 = br.ReadByte();
				br.ReadByte();
				x = count + x_length * 64;
				y = count2 + y_length * 64;
				tile[x, y] = decryptData(d1, d2);
				count++;
			}
			ms.Dispose();
			br.Close();
		}

		private void readSeg(byte[] data, int x_length, int y_length, ref int[,] tile)
		{
			MemoryStream ms = new MemoryStream(data);
			BinaryReader br = new BinaryReader(ms);
			int off = 16384;
			br.BaseStream.Seek(off, SeekOrigin.Begin);
			int unknow1 = br.ReadUInt16();
			off += unknow1 * 4;
			br.BaseStream.Seek(off + 2, SeekOrigin.Begin);

			int d1 = 0, d2 = 0, x = 0, y = 0;
			int count = 0;
			int count2 = -1;
			while (true)
			{
				if (count % 64 == 0)
				{
					count2++;
					count = 0;
				}
				if (count2 >= 64)
				{
					break;
				}
				d1 = br.ReadByte();
				d2 = br.ReadByte();

				x = count + x_length * 64;
				y = count2 + y_length * 64;
				tile[x, y] = decryptData(d1, d2);
				count++;
			}
			ms.Dispose();
			br.Close();
		}

		// FIXME: ChrisLiu.2010/10/20: 懶得改了，有空在修= =
		private int decryptData(int t1, int t3)
		{
			int score = 0;
			string s0, s1;
			string st1 = t1.ToString("X2");
			s0 = st1.Substring(0, 1);
			s1 = st1.Substring(1, 1);
			//一般
			if (s1.Equals("0") || s1.Equals("1") || s1.Equals("2") || s1.Equals("3"))
			{
				score += 0;
				//安全	
			}
			else if (s1.Equals("4") || s1.Equals("5") || s1.Equals("6") || s1.Equals("7")
					|| s1.Equals("C") || s1.Equals("D") || s1.Equals("E") || s1.Equals("F"))
			{
				score += 16;
				//戰鬥	
			}
			else if (s1.Equals("8") || s1.Equals("9") || s1.Equals("A") || s1.Equals("B"))
			{
				score += 32;
			}
			if (t1 < 16)
			{
				//可移動 & 箭可通過  ?( 不確定)
				if ((t1 & 1) == 0)
				{
					score += 10;
				}
			}
			else
			{

				//箭可通過
				if ((Convert.ToInt32(s0, 16) & 1) == 0)
				{
					score += 8;
				}
				//可移動
				if ((Convert.ToInt32(s1, 16) & 1) == 0)
				{
					score += 2;
				}


			}

			string st3 = t3.ToString("X2");
			s0 = st3.Substring(0, 1);
			s1 = st3.Substring(1, 1);


			if (t3 < 16)
			{
				//可移動 & 箭可通過  ?( 不確定)
				if ((t3 & 1) == 0)
				{
					score += 5;
				}
			}
			else
			{

				//箭可通過
				if ((Convert.ToInt32(s0, 16) & 1) == 0)
				{
					score += 4;
				}
				//可移動
				if ((Convert.ToInt32(s1, 16) & 1) == 0)
				{
					score += 1;
				}

			}
			return score;
		}


	}

	class MapSet
	{
		public MapSet(int _mapId)
		{
			this.mapId = _mapId;
		}

		public MapSet(int _mapId, string _mapName, int _mapAttr)
		{
			this.mapId = _mapId;
			this.mapName = _mapName;
			this.mapAttr = _mapAttr;
		}

		public int mapId { set; get; }
		public string mapName { set; get; }
		public int mapAttr { set; get; }
		public int startX { set; get; }
		public int startY { set; get; }
		public int endX { set; get; }
		public int endY { set; get; }
		public int lengthX { set; get; }
		public int lengthY { set; get; }
		public int minAreaX { set; get; }
		public int minAreaY { set; get; }
		public int[,] tile { set; get; }
		public DirectoryInfo mapFolder { set; get; }
	}

	public class myComparer : System.Collections.IComparer
	{
		public int Compare(object x, object y)
		{
			if (x == null && y == null)
			{
				return 0;
			}
			if (x == null)
			{
				return -1;
			}
			if (y == null)
			{
				return 1;
			}

			int ix = int.Parse(x.ToString());
			int iy = int.Parse(y.ToString());
			int result = 0;
			if (ix > iy) result = 1;
			if (iy > ix) result = -1;
			//依名稱排序  
			return result;//遞增   
		}
	}
}
