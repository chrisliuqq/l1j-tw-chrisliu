using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using L1Tool.util;

namespace L1Tool
{
    public partial class MainForm : Form
    {

		private FolderBrowserDialog _fbd;
		// XXX: ChrisLiu.20101020: 雖然可以採用內建的 Resource 來儲存設定，但是我還是喜歡 ini 的方法，可以直接用文字編輯器修改是很大的誘因。
		private Setting _setting;
		private string lineage_path;
		private string map_path;
		private DirectoryInfo[] mapDirectory;
		private MapPackage mp;

        public MainForm()
        {
            InitializeComponent();
			_setting = new Setting(Application.StartupPath + @"\settings.ini");
			_fbd = new FolderBrowserDialog();
			_fbd.Description = "設定天堂的遊戲目錄";
			_fbd.ShowNewFolderButton = false;
			if (_setting.getLineagePath() != "")
			{
				lineage_path = _setting.getLineagePath();
				map_path = lineage_path + @"\map\";
				_fbd.SelectedPath = lineage_path;
			}

        }



		#region -----地圖相關-----

		private void loadMapDirectoryInfo()
		{
			if (checkMapFolder())
			{
				mapDirectory = new DirectoryInfo(map_path).GetDirectories();
				Ctrl.SetText(this.labelMainMapPath, "是，共包含了 " + mapDirectory.Count() + " 個地圖。");
			}
		}

		private bool checkMapFolder()
		{
			return Directory.Exists(map_path);
		}

		#endregion

		#region -----表單顯示與更新-----

		private void refreshTabMainLabel()
		{
			this.labelMainPath.Text = lineage_path;
			this.labelMainMapPath.Text = checkMapFolder() ? "是" : "否";
		}

		#endregion

		#region -----表單事件-----
		private void ToolStripMenuItem_About_Click(object sender, EventArgs e)
        {
            AboutForm.getInstance().ShowDialog();
        }

        private void onFormClosing(object sender, FormClosingEventArgs e)
        {
			_setting.save();
            AboutForm.getInstance().Dispose();
        }

        private void ToolStripMenuItem_Exit_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

		private void ToolStripMenuItemSetPath_Click(object sender, EventArgs e)
		{
			if (_fbd.ShowDialog() == DialogResult.OK)
			{
				lineage_path = _fbd.SelectedPath;
				_setting.setLineagePath(lineage_path);
				
			}
		}

		private void buttonMapRefresh_Click(object sender, EventArgs e)
		{
			refreshTabMainLabel();
			loadMapDirectoryInfo();
		}

		private void button1_Click(object sender, EventArgs e)
		{
			mp = new MapPackage(mapDirectory);
			mp.loadClientMap(false);
			this.listBoxMapList.Items.AddRange(mp.getMapIdList().ToArray());
		}

		private void listBoxMapList_SelectedIndexChanged(object sender, EventArgs e)
		{
			int select = int.Parse(((ListBox)sender).SelectedItem.ToString());
			MapSet ms = mp.getMapSet(select);
			labelMapInfo.Text = string.Format("地圖編號：{0}\n地圖名稱：{1}\n地圖屬性：{2}", ms.mapId, ms.mapName, ms.mapAttr);
		}

		#endregion





	}

	#region -----跨執行序相關-----
	//CtrlHelper
	public static class Ctrl
	{
		public static void SetValue(
			Control ctrl,
			string property,
			object value)
		{
			if (ctrl.InvokeRequired)
			{
				Action<Control, string, object> d =
					new Action<Control, string, object>(SetValue);
				ctrl.Invoke(d, ctrl, property, value);
				return;
			}
			ctrl.GetType().GetProperty(property)
				.SetValue(ctrl, value, null);
		}
		public static void SetText(
			Control ctrl,
			string value)
		{
			SetValue(ctrl, "Text", value);
		}
	}
	#endregion
}
