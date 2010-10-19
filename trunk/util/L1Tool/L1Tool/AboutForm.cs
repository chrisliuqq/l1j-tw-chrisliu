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
    public partial class AboutForm : Form
    {
        private static AboutForm _instance;

        public static AboutForm getInstance()
        {
            if (null == _instance)
            {
                _instance = new AboutForm();
            }

            return _instance;
        }

        private AboutForm()
        {
            InitializeComponent();
			labelVersion.Text = "版本 " + Application.ProductVersion.ToString();
            linkLabelLicense.Links.Add(12, 3, @"http://creativecommons.org/licenses/MIT/deed.zh_TW");
            linkLabelLicense.Links.Add(31, 3, @"http://opensource.org/licenses/mit-license.php");
        }

        private void buttonExit_Click(object sender, EventArgs e)
        {
            this.Hide();
        }

        private void onLinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
        {
            string url = e.Link.LinkData.ToString();
            System.Diagnostics.Process.Start(url);
        }
    }
}
