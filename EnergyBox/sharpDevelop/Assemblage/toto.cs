/*
 * Crée par SharpDevelop.
 * Utilisateur: user
 * Date: 11/02/2016
 * Heure: 11:22
 * 
 * Pour changer ce modèle utiliser Outils | Options | Codage | Editer les en-têtes standards.
 */

using System;
using System.Drawing;
using System.Windows.Forms;

using WComp.Beans;

namespace DefaultNamespace
{
	/// <summary>
	/// Description of Container1.
	/// </summary>
	public class Container1 : System.Windows.Forms.Form
	{
		public Container1()
		{
			//
			// The InitializeComponent() call is required for Windows Forms designer support.
			//
			InitializeComponent();

			//
			// The InitializeBeans() call is required for WComp.NET designer support.
			//
			InitializeBeans();
			
			//
			// TODO: Add constructor code after the InitializeBeans() call.
			//
		}
		
		[STAThread]
		public static void Main(string[] args)
		{
			Application.Run(new Container1());
		}
		
		#region WComp.NET designer generated code
		/// <summary>
		/// This method is required for WComp.NET designer support.
		/// Do not change the method contents inside the source code editor.
		/// The WComp.NET designer might not be able to load this method if it was changed manually.
		/// </summary>
		          this.CheckEdfPrice = new System.Windows.Forms.Button();
            this.textBox1 = new System.Windows.Forms.TextBox();
            // 
            // setKWhPrice
            // 
            this.setKWhPrice.AutoCompleteCustomSource = null;
            this.setKWhPrice.Text = "0,617";
            this.setKWhPrice.Controls = null;
            this.setKWhPrice.DataBindings = null;
   
		
		/// <summary>
		/// This method is required for WComp.NET designer support.
		/// Do not change the method contents inside the source code editor.
		/// The WComp.NET designer might not be able to load this method if it was changed manually.
		/// </summary>
		          this.Reset Total Price.Location = new System.Drawing.Point(296, 72);
            // 
   
		#endregion
	}
}
