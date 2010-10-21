namespace L1Tool
{
    partial class MainForm
    {
        /// <summary>
        /// 設計工具所需的變數。
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 清除任何使用中的資源。
        /// </summary>
        /// <param name="disposing">如果應該處置 Managed 資源則為 true，否則為 false。</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form 設計工具產生的程式碼

        /// <summary>
        /// 此為設計工具支援所需的方法 - 請勿使用程式碼編輯器
        /// 修改這個方法的內容。
        /// </summary>
        private void InitializeComponent()
        {
			this.menuStripMain = new System.Windows.Forms.MenuStrip();
			this.ToolStripMenuItem_File = new System.Windows.Forms.ToolStripMenuItem();
			this.ToolStripMenuItemSetPath = new System.Windows.Forms.ToolStripMenuItem();
			this.toolStripSeparatorFile = new System.Windows.Forms.ToolStripSeparator();
			this.ToolStripMenuItem_Exit = new System.Windows.Forms.ToolStripMenuItem();
			this.toolStripMenuItem_Help = new System.Windows.Forms.ToolStripMenuItem();
			this.ToolStripMenuItem_About = new System.Windows.Forms.ToolStripMenuItem();
			this.tabControlMain = new System.Windows.Forms.TabControl();
			this.tabPageMain = new System.Windows.Forms.TabPage();
			this.buttonMapRefresh = new System.Windows.Forms.Button();
			this.labelMainMapPath = new System.Windows.Forms.Label();
			this.labelMainMapPathDesc = new System.Windows.Forms.Label();
			this.labelMainPath = new System.Windows.Forms.Label();
			this.labelMainPathDesc = new System.Windows.Forms.Label();
			this.tabPageMap = new System.Windows.Forms.TabPage();
			this.labelMapInfo = new System.Windows.Forms.Label();
			this.buttonMapReload = new System.Windows.Forms.Button();
			this.buttonOutput = new System.Windows.Forms.Button();
			this.listBoxMapList = new System.Windows.Forms.ListBox();
			this.statusStripMain = new System.Windows.Forms.StatusStrip();
			this.toolStripProgressBarMain = new System.Windows.Forms.ToolStripProgressBar();
			this.toolStripStatusLabelStatus = new System.Windows.Forms.ToolStripStatusLabel();
			this.menuStripMain.SuspendLayout();
			this.tabControlMain.SuspendLayout();
			this.tabPageMain.SuspendLayout();
			this.tabPageMap.SuspendLayout();
			this.statusStripMain.SuspendLayout();
			this.SuspendLayout();
			// 
			// menuStripMain
			// 
			this.menuStripMain.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.ToolStripMenuItem_File,
            this.toolStripMenuItem_Help});
			this.menuStripMain.Location = new System.Drawing.Point(0, 0);
			this.menuStripMain.Name = "menuStripMain";
			this.menuStripMain.Size = new System.Drawing.Size(484, 24);
			this.menuStripMain.TabIndex = 0;
			this.menuStripMain.Text = "menuStripMain";
			// 
			// ToolStripMenuItem_File
			// 
			this.ToolStripMenuItem_File.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.ToolStripMenuItemSetPath,
            this.toolStripSeparatorFile,
            this.ToolStripMenuItem_Exit});
			this.ToolStripMenuItem_File.Name = "ToolStripMenuItem_File";
			this.ToolStripMenuItem_File.Size = new System.Drawing.Size(58, 20);
			this.ToolStripMenuItem_File.Text = "檔案(&F)";
			// 
			// ToolStripMenuItemSetPath
			// 
			this.ToolStripMenuItemSetPath.Name = "ToolStripMenuItemSetPath";
			this.ToolStripMenuItemSetPath.Size = new System.Drawing.Size(163, 22);
			this.ToolStripMenuItemSetPath.Text = "設定天堂路徑(&S)";
			this.ToolStripMenuItemSetPath.Click += new System.EventHandler(this.ToolStripMenuItemSetPath_Click);
			// 
			// toolStripSeparatorFile
			// 
			this.toolStripSeparatorFile.Name = "toolStripSeparatorFile";
			this.toolStripSeparatorFile.Size = new System.Drawing.Size(160, 6);
			// 
			// ToolStripMenuItem_Exit
			// 
			this.ToolStripMenuItem_Exit.Name = "ToolStripMenuItem_Exit";
			this.ToolStripMenuItem_Exit.Size = new System.Drawing.Size(163, 22);
			this.ToolStripMenuItem_Exit.Text = "結束(&X)";
			this.ToolStripMenuItem_Exit.Click += new System.EventHandler(this.ToolStripMenuItem_Exit_Click);
			// 
			// toolStripMenuItem_Help
			// 
			this.toolStripMenuItem_Help.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.ToolStripMenuItem_About});
			this.toolStripMenuItem_Help.Name = "toolStripMenuItem_Help";
			this.toolStripMenuItem_Help.Size = new System.Drawing.Size(61, 20);
			this.toolStripMenuItem_Help.Text = "說明(&H)";
			// 
			// ToolStripMenuItem_About
			// 
			this.ToolStripMenuItem_About.Name = "ToolStripMenuItem_About";
			this.ToolStripMenuItem_About.Size = new System.Drawing.Size(158, 22);
			this.ToolStripMenuItem_About.Text = "關於 L1Tool(&A)";
			this.ToolStripMenuItem_About.Click += new System.EventHandler(this.ToolStripMenuItem_About_Click);
			// 
			// tabControlMain
			// 
			this.tabControlMain.Controls.Add(this.tabPageMain);
			this.tabControlMain.Controls.Add(this.tabPageMap);
			this.tabControlMain.Location = new System.Drawing.Point(12, 27);
			this.tabControlMain.Name = "tabControlMain";
			this.tabControlMain.SelectedIndex = 0;
			this.tabControlMain.Size = new System.Drawing.Size(460, 410);
			this.tabControlMain.TabIndex = 1;
			// 
			// tabPageMain
			// 
			this.tabPageMain.Controls.Add(this.buttonMapRefresh);
			this.tabPageMain.Controls.Add(this.labelMainMapPath);
			this.tabPageMain.Controls.Add(this.labelMainMapPathDesc);
			this.tabPageMain.Controls.Add(this.labelMainPath);
			this.tabPageMain.Controls.Add(this.labelMainPathDesc);
			this.tabPageMain.Location = new System.Drawing.Point(4, 22);
			this.tabPageMain.Name = "tabPageMain";
			this.tabPageMain.Padding = new System.Windows.Forms.Padding(3);
			this.tabPageMain.Size = new System.Drawing.Size(452, 384);
			this.tabPageMain.TabIndex = 0;
			this.tabPageMain.Text = "首頁";
			this.tabPageMain.UseVisualStyleBackColor = true;
			// 
			// buttonMapRefresh
			// 
			this.buttonMapRefresh.Location = new System.Drawing.Point(371, 355);
			this.buttonMapRefresh.Name = "buttonMapRefresh";
			this.buttonMapRefresh.Size = new System.Drawing.Size(75, 23);
			this.buttonMapRefresh.TabIndex = 4;
			this.buttonMapRefresh.Text = "重新整理";
			this.buttonMapRefresh.UseVisualStyleBackColor = true;
			this.buttonMapRefresh.Click += new System.EventHandler(this.buttonMapRefresh_Click);
			// 
			// labelMainMapPath
			// 
			this.labelMainMapPath.AutoSize = true;
			this.labelMainMapPath.Location = new System.Drawing.Point(184, 40);
			this.labelMainMapPath.Name = "labelMainMapPath";
			this.labelMainMapPath.Size = new System.Drawing.Size(0, 12);
			this.labelMainMapPath.TabIndex = 3;
			// 
			// labelMainMapPathDesc
			// 
			this.labelMainMapPathDesc.AutoSize = true;
			this.labelMainMapPathDesc.Location = new System.Drawing.Point(17, 40);
			this.labelMainMapPathDesc.Name = "labelMainMapPathDesc";
			this.labelMainMapPathDesc.Size = new System.Drawing.Size(161, 12);
			this.labelMainMapPathDesc.TabIndex = 2;
			this.labelMainMapPathDesc.Text = "是否包含正確的地圖資料夾：";
			// 
			// labelMainPath
			// 
			this.labelMainPath.Location = new System.Drawing.Point(124, 18);
			this.labelMainPath.Name = "labelMainPath";
			this.labelMainPath.Size = new System.Drawing.Size(322, 23);
			this.labelMainPath.TabIndex = 1;
			// 
			// labelMainPathDesc
			// 
			this.labelMainPathDesc.AutoSize = true;
			this.labelMainPathDesc.Location = new System.Drawing.Point(17, 18);
			this.labelMainPathDesc.Name = "labelMainPathDesc";
			this.labelMainPathDesc.Size = new System.Drawing.Size(101, 12);
			this.labelMainPathDesc.TabIndex = 0;
			this.labelMainPathDesc.Text = "設定的天堂路徑：";
			// 
			// tabPageMap
			// 
			this.tabPageMap.Controls.Add(this.labelMapInfo);
			this.tabPageMap.Controls.Add(this.buttonMapReload);
			this.tabPageMap.Controls.Add(this.buttonOutput);
			this.tabPageMap.Controls.Add(this.listBoxMapList);
			this.tabPageMap.Location = new System.Drawing.Point(4, 22);
			this.tabPageMap.Name = "tabPageMap";
			this.tabPageMap.Padding = new System.Windows.Forms.Padding(3);
			this.tabPageMap.Size = new System.Drawing.Size(452, 384);
			this.tabPageMap.TabIndex = 1;
			this.tabPageMap.Text = "地圖";
			this.tabPageMap.UseVisualStyleBackColor = true;
			// 
			// labelMapInfo
			// 
			this.labelMapInfo.AutoSize = true;
			this.labelMapInfo.Location = new System.Drawing.Point(148, 21);
			this.labelMapInfo.Name = "labelMapInfo";
			this.labelMapInfo.Size = new System.Drawing.Size(0, 12);
			this.labelMapInfo.TabIndex = 3;
			// 
			// buttonMapReload
			// 
			this.buttonMapReload.Location = new System.Drawing.Point(290, 355);
			this.buttonMapReload.Name = "buttonMapReload";
			this.buttonMapReload.Size = new System.Drawing.Size(75, 23);
			this.buttonMapReload.TabIndex = 2;
			this.buttonMapReload.Text = "重新讀取";
			this.buttonMapReload.UseVisualStyleBackColor = true;
			this.buttonMapReload.Click += new System.EventHandler(this.buttonMapReload_Click);
			// 
			// buttonOutput
			// 
			this.buttonOutput.Location = new System.Drawing.Point(371, 355);
			this.buttonOutput.Name = "buttonOutput";
			this.buttonOutput.Size = new System.Drawing.Size(75, 23);
			this.buttonOutput.TabIndex = 1;
			this.buttonOutput.Text = "輸出";
			this.buttonOutput.UseVisualStyleBackColor = true;
			this.buttonOutput.Click += new System.EventHandler(this.buttonOutput_Click);
			// 
			// listBoxMapList
			// 
			this.listBoxMapList.FormattingEnabled = true;
			this.listBoxMapList.ItemHeight = 12;
			this.listBoxMapList.Location = new System.Drawing.Point(6, 6);
			this.listBoxMapList.Name = "listBoxMapList";
			this.listBoxMapList.Size = new System.Drawing.Size(120, 292);
			this.listBoxMapList.TabIndex = 0;
			this.listBoxMapList.SelectedIndexChanged += new System.EventHandler(this.listBoxMapList_SelectedIndexChanged);
			// 
			// statusStripMain
			// 
			this.statusStripMain.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolStripProgressBarMain,
            this.toolStripStatusLabelStatus});
			this.statusStripMain.Location = new System.Drawing.Point(0, 440);
			this.statusStripMain.Name = "statusStripMain";
			this.statusStripMain.Size = new System.Drawing.Size(484, 22);
			this.statusStripMain.SizingGrip = false;
			this.statusStripMain.TabIndex = 2;
			this.statusStripMain.Text = "statusStripMain";
			// 
			// toolStripProgressBarMain
			// 
			this.toolStripProgressBarMain.Name = "toolStripProgressBarMain";
			this.toolStripProgressBarMain.Size = new System.Drawing.Size(100, 16);
			// 
			// toolStripStatusLabelStatus
			// 
			this.toolStripStatusLabelStatus.Name = "toolStripStatusLabelStatus";
			this.toolStripStatusLabelStatus.Size = new System.Drawing.Size(0, 17);
			// 
			// MainForm
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.ClientSize = new System.Drawing.Size(484, 462);
			this.Controls.Add(this.statusStripMain);
			this.Controls.Add(this.tabControlMain);
			this.Controls.Add(this.menuStripMain);
			this.MainMenuStrip = this.menuStripMain;
			this.MaximizeBox = false;
			this.MaximumSize = new System.Drawing.Size(500, 500);
			this.MinimumSize = new System.Drawing.Size(500, 500);
			this.Name = "MainForm";
			this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
			this.Text = "L1Tool :: Programmed By ChrisLiu";
			this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.onFormClosing);
			this.menuStripMain.ResumeLayout(false);
			this.menuStripMain.PerformLayout();
			this.tabControlMain.ResumeLayout(false);
			this.tabPageMain.ResumeLayout(false);
			this.tabPageMain.PerformLayout();
			this.tabPageMap.ResumeLayout(false);
			this.tabPageMap.PerformLayout();
			this.statusStripMain.ResumeLayout(false);
			this.statusStripMain.PerformLayout();
			this.ResumeLayout(false);
			this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.MenuStrip menuStripMain;
        private System.Windows.Forms.ToolStripMenuItem ToolStripMenuItem_File;
        private System.Windows.Forms.ToolStripMenuItem ToolStripMenuItem_Exit;
        private System.Windows.Forms.ToolStripMenuItem toolStripMenuItem_Help;
        private System.Windows.Forms.ToolStripMenuItem ToolStripMenuItem_About;
		private System.Windows.Forms.ToolStripMenuItem ToolStripMenuItemSetPath;
		private System.Windows.Forms.ToolStripSeparator toolStripSeparatorFile;
		private System.Windows.Forms.TabControl tabControlMain;
		private System.Windows.Forms.TabPage tabPageMain;
		private System.Windows.Forms.TabPage tabPageMap;
		private System.Windows.Forms.StatusStrip statusStripMain;
		private System.Windows.Forms.ToolStripStatusLabel toolStripStatusLabelStatus;
		private System.Windows.Forms.ToolStripProgressBar toolStripProgressBarMain;
		private System.Windows.Forms.Label labelMainPathDesc;
		private System.Windows.Forms.Label labelMainMapPathDesc;
		private System.Windows.Forms.Label labelMainPath;
		private System.Windows.Forms.Label labelMainMapPath;
		private System.Windows.Forms.Button buttonMapRefresh;
		private System.Windows.Forms.ListBox listBoxMapList;
		private System.Windows.Forms.Button buttonOutput;
		private System.Windows.Forms.Button buttonMapReload;
		private System.Windows.Forms.Label labelMapInfo;
    }
}

