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
			this.ToolStripMenuItem_Exit = new System.Windows.Forms.ToolStripMenuItem();
			this.toolStripMenuItem_Help = new System.Windows.Forms.ToolStripMenuItem();
			this.ToolStripMenuItem_About = new System.Windows.Forms.ToolStripMenuItem();
			this.ToolStripMenuItemSetPath = new System.Windows.Forms.ToolStripMenuItem();
			this.toolStripSeparatorFile = new System.Windows.Forms.ToolStripSeparator();
			this.menuStripMain.SuspendLayout();
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
			// MainForm
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.ClientSize = new System.Drawing.Size(484, 462);
			this.Controls.Add(this.menuStripMain);
			this.MainMenuStrip = this.menuStripMain;
			this.Name = "MainForm";
			this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
			this.Text = "L1Tool :: Programmed By ChrisLiu";
			this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.onFormClosing);
			this.menuStripMain.ResumeLayout(false);
			this.menuStripMain.PerformLayout();
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
    }
}

