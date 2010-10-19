using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.InteropServices;

namespace L1Tool
{
    class Setting : INIFile
    {
        private string lineage_path;
        private string setting_path;

		public Setting(string filename) : base(filename) 
        {
            setting_path = filename;
			load();
        }

		public string getLineagePath()
		{
			return lineage_path;
		}

		public void setLineagePath(string path)
		{
			lineage_path = path;
		}

        public void load()
        {
			this.lineage_path = ReadString(@"L1ToolSetting", "Lineage_PATH", "");
        }

		public void save()
		{
			WriteString("L1ToolSetting", "Lineage_PATH", lineage_path);
		}
    }

    class INIFile
    {
        private string iniFileName;

		[DllImport("kernel32")]
		private static extern int GetPrivateProfileInt(
		string lpAppName,
		string lpKeyName,
		int nDefault,
		string lpFileName
		);
		[DllImport("kernel32")]
		private static extern int GetPrivateProfileString(
		string lpAppName,
		string lpKeyName,
		string lpDefault,
		StringBuilder lpReturnedString,
		int nSize,
		string lpFileName
		);
		[DllImport("kernel32")]
		private static extern bool WritePrivateProfileString(
		string lpAppName,
		string lpKeyName,
		string lpString,
		string lpFileName
		);

        public INIFile(string filename)
		{
            iniFileName = filename;
		}
		protected int ReadInt(string section, string key, int def)
		{
            return GetPrivateProfileInt(section, key, def, iniFileName);
		}
		protected string ReadString(string section, string key, string def)
		{
			StringBuilder temp = new StringBuilder(1024);
            GetPrivateProfileString(section, key, def, temp, 1024, iniFileName);
			return temp.ToString();
		}
		protected void WriteInt(string section, string key, int iVal)
		{
            WritePrivateProfileString(section, key, iVal.ToString(), iniFileName);
		}
		protected void WriteString(string section, string key, string strVal)
		{
            WritePrivateProfileString(section, key, strVal, iniFileName);
		}
		protected void DelKey(string section, string key)
		{
            WritePrivateProfileString(section, key, null, iniFileName);
		}
		protected void DelSection(string section)
		{
            WritePrivateProfileString(section, null, null, iniFileName);
		}
    }
}
