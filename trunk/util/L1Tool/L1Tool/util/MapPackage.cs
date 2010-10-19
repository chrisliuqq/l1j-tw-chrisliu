using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace L1Tool.util
{
	class MapPackage
	{
		private DirectoryInfo[] _mapFolderInfo;

		public MapPackage(DirectoryInfo[] map)
		{
			this._mapFolderInfo = map;
		}
	}
}
