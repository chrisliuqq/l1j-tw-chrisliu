using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace L1Tool
{
    public partial class MainForm : Form
    {

		private FolderBrowserDialog _fbd;
		// XXX: ChrisLiu.20101020: 雖然可以採用內建的 Resource 來儲存設定，但是我還是喜歡 ini 的方法，可以直接用文字編輯器修改是很大的誘因。
		private Setting _setting;
		private string lineage_path;

        public MainForm()
        {
            InitializeComponent();
			_setting = new Setting(Application.StartupPath + @"\settings.ini");
			_fbd = new FolderBrowserDialog();
			_fbd.Description = "設定天堂的遊戲目錄";
			_fbd.ShowNewFolderButton = false;
			if (_setting.getLineagePath() != "")
			{
				_fbd.SelectedPath = _setting.getLineagePath();
			}

        }

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
    }
}
