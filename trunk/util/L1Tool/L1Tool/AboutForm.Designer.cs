namespace L1Tool
{
    partial class AboutForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
			this.labelTitle = new System.Windows.Forms.Label();
			this.labelVersion = new System.Windows.Forms.Label();
			this.linkLabelLicense = new System.Windows.Forms.LinkLabel();
			this.buttonExit = new System.Windows.Forms.Button();
			this.groupBoxSpecialThanks = new System.Windows.Forms.GroupBox();
			this.textBoxSpecialThanks = new System.Windows.Forms.TextBox();
			this.groupBoxBottom = new System.Windows.Forms.GroupBox();
			this.labelAuthor = new System.Windows.Forms.Label();
			this.groupBoxSpecialThanks.SuspendLayout();
			this.groupBoxBottom.SuspendLayout();
			this.SuspendLayout();
			// 
			// labelTitle
			// 
			this.labelTitle.AutoSize = true;
			this.labelTitle.Font = new System.Drawing.Font("Arial Unicode MS", 24F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(136)));
			this.labelTitle.ForeColor = System.Drawing.Color.DarkViolet;
			this.labelTitle.Location = new System.Drawing.Point(12, 9);
			this.labelTitle.Name = "labelTitle";
			this.labelTitle.Size = new System.Drawing.Size(118, 43);
			this.labelTitle.TabIndex = 0;
			this.labelTitle.Text = "L1Tool";
			// 
			// labelVersion
			// 
			this.labelVersion.AutoSize = true;
			this.labelVersion.Font = new System.Drawing.Font("新細明體", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(136)));
			this.labelVersion.ForeColor = System.Drawing.Color.MediumPurple;
			this.labelVersion.Location = new System.Drawing.Point(18, 52);
			this.labelVersion.Name = "labelVersion";
			this.labelVersion.Size = new System.Drawing.Size(53, 12);
			this.labelVersion.TabIndex = 1;
			this.labelVersion.Text = "版本 1.0";
			// 
			// linkLabelLicense
			// 
			this.linkLabelLicense.AutoEllipsis = true;
			this.linkLabelLicense.Location = new System.Drawing.Point(18, 118);
			this.linkLabelLicense.Name = "linkLabelLicense";
			this.linkLabelLicense.Size = new System.Drawing.Size(252, 32);
			this.linkLabelLicense.TabIndex = 3;
			this.linkLabelLicense.TabStop = true;
			this.linkLabelLicense.Text = "L1Tool 專案採用 MIT 授權，詳細的授權內容您可以參考此網站以瞭解您的權利。";
			this.linkLabelLicense.LinkClicked += new System.Windows.Forms.LinkLabelLinkClickedEventHandler(this.onLinkClicked);
			// 
			// buttonExit
			// 
			this.buttonExit.Location = new System.Drawing.Point(197, 21);
			this.buttonExit.Name = "buttonExit";
			this.buttonExit.Size = new System.Drawing.Size(75, 23);
			this.buttonExit.TabIndex = 4;
			this.buttonExit.Text = "確定";
			this.buttonExit.UseVisualStyleBackColor = true;
			this.buttonExit.Click += new System.EventHandler(this.buttonExit_Click);
			// 
			// groupBoxSpecialThanks
			// 
			this.groupBoxSpecialThanks.Controls.Add(this.textBoxSpecialThanks);
			this.groupBoxSpecialThanks.Location = new System.Drawing.Point(20, 153);
			this.groupBoxSpecialThanks.Name = "groupBoxSpecialThanks";
			this.groupBoxSpecialThanks.Size = new System.Drawing.Size(252, 82);
			this.groupBoxSpecialThanks.TabIndex = 5;
			this.groupBoxSpecialThanks.TabStop = false;
			this.groupBoxSpecialThanks.Text = "特別感謝";
			// 
			// textBoxSpecialThanks
			// 
			this.textBoxSpecialThanks.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
			this.textBoxSpecialThanks.Cursor = System.Windows.Forms.Cursors.Default;
			this.textBoxSpecialThanks.Location = new System.Drawing.Point(6, 21);
			this.textBoxSpecialThanks.Multiline = true;
			this.textBoxSpecialThanks.Name = "textBoxSpecialThanks";
			this.textBoxSpecialThanks.ReadOnly = true;
			this.textBoxSpecialThanks.Size = new System.Drawing.Size(240, 55);
			this.textBoxSpecialThanks.TabIndex = 6;
			this.textBoxSpecialThanks.Text = "srwh：L1MapTool 作者\r\nmoore：PakViewer 作者";
			// 
			// groupBoxBottom
			// 
			this.groupBoxBottom.Controls.Add(this.buttonExit);
			this.groupBoxBottom.Dock = System.Windows.Forms.DockStyle.Bottom;
			this.groupBoxBottom.Location = new System.Drawing.Point(0, 241);
			this.groupBoxBottom.Name = "groupBoxBottom";
			this.groupBoxBottom.Size = new System.Drawing.Size(284, 56);
			this.groupBoxBottom.TabIndex = 6;
			this.groupBoxBottom.TabStop = false;
			// 
			// labelAuthor
			// 
			this.labelAuthor.AutoSize = true;
			this.labelAuthor.Location = new System.Drawing.Point(18, 74);
			this.labelAuthor.Name = "labelAuthor";
			this.labelAuthor.Size = new System.Drawing.Size(82, 12);
			this.labelAuthor.TabIndex = 7;
			this.labelAuthor.Text = "作者：ChrisLiu";
			// 
			// AboutForm
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.BackColor = System.Drawing.Color.White;
			this.ClientSize = new System.Drawing.Size(284, 297);
			this.Controls.Add(this.labelAuthor);
			this.Controls.Add(this.groupBoxBottom);
			this.Controls.Add(this.groupBoxSpecialThanks);
			this.Controls.Add(this.linkLabelLicense);
			this.Controls.Add(this.labelVersion);
			this.Controls.Add(this.labelTitle);
			this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
			this.Name = "AboutForm";
			this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
			this.Text = "關於 L1Tool";
			this.groupBoxSpecialThanks.ResumeLayout(false);
			this.groupBoxSpecialThanks.PerformLayout();
			this.groupBoxBottom.ResumeLayout(false);
			this.ResumeLayout(false);
			this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label labelTitle;
        private System.Windows.Forms.Label labelVersion;
        private System.Windows.Forms.LinkLabel linkLabelLicense;
        private System.Windows.Forms.Button buttonExit;
        private System.Windows.Forms.GroupBox groupBoxSpecialThanks;
        private System.Windows.Forms.TextBox textBoxSpecialThanks;
        private System.Windows.Forms.GroupBox groupBoxBottom;
		private System.Windows.Forms.Label labelAuthor;
    }
}