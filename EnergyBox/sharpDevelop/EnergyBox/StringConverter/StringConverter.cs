/*
 * Crée par SharpDevelop.
 * Utilisateur: user
 * Date: 11/02/2016
 * Heure: 11:30
 * 
 * Pour changer ce modèle utiliser Outils | Options | Codage | Editer les en-têtes standards.
 */
using System;
using WComp.Beans;

namespace WComp.Beans
{
	/// <summary>
	/// This is a sample bean, which has an integer evented property and a method.
	/// 
	/// Notes: for beans creating threads, the IThreadCreator interface should be implemented,
	/// 	providing a cleanup method should be implemented and named `Stop()'.
	/// For proxy beans, the IProxyBean interface should  be implemented,
	/// 	providing the IsConnected property, allowing the connection status to be drawn in
	/// 	the AddIn's graphical designer.
	/// 
	/// Several classes can be defined or used by a Bean, but only the class with the
	/// [Bean] attribute will be available in WComp. Its ports will be all public methods,
	/// events and properties definied in that class.
	/// </summary>
	[Bean(Category="MyCategory")]
	public class StringConverter
	{

		/// <summary>
		/// A method sending an event, which is here simply the argument + 1.
		/// Note that there is no return type to the method, because we use events to send
		/// information in WComp. Return values don't have to be used.
		/// </summary>
		public void input(String str) {
			if(str.CompareTo("True") == 0) {
				FireStringEvent("on");
			} else if(str.CompareTo("False") == 0) {
				FireStringEvent("off");
			} else {
				double d = Double.Parse(str);
				FireIntEvent((int)d);
				FireDoubleEvent(d);
			}
		}

		public delegate void IntValueEventHandler(int val);
		public event IntValueEventHandler intValue;
		public delegate void DoubleValueEventHandler(double val);
		public event DoubleValueEventHandler doubleValue;
		public delegate void StringValueEventHandler(String val);
		public event StringValueEventHandler stringValue;
		
		private void FireIntEvent(int i) {
			if (intValue != null)
				intValue(i);
		}
		
		private void FireDoubleEvent(double i) {
			if (doubleValue != null)
				doubleValue(i);
		}
		
		private void FireStringEvent(String i) {
			if (stringValue != null)
				stringValue(i);
		}
	}
}
