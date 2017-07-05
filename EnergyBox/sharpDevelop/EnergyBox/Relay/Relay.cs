/*
 * Crée par SharpDevelop.
 * Utilisateur: user
 * Date: 19/01/2016
 * Heure: 14:18
 * 
 * Pour changer ce modèle utiliser Outils | Options | Codage | Editer les en-têtes standards.
 */
using System;
using WComp.Beans;
using RPi.I2C.Net;

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
	public class Relay
	{
		/// <summary>
		/// Fill in private attributes here.
		/// </summary>
		private String state;

		/// <summary>
		/// This property will appear in bean's property panel and bean's input functions.
		/// </summary>
		public String State {
			get { return state; }
			set {
				state = value;
				FireStringEvent(state);		// event will be fired for every property set.
			}
		}

		/// <summary>
		/// A method sending an event, which is here simply the argument + 1.
		/// Note that there is no return type to the method, because we use events to send
		/// information in WComp. Return values don't have to be used.
		/// </summary>
		public void ChangeState(String stateStr) {
			
			if(stateStr.CompareTo("on")==0) {
				try{
				RPi.I2C.Net.I2CBus.instance.WriteBytes(0x12, new byte[] {0x00,0x01});
				FireStringEvent("on");
				}catch{}
			}
			if(stateStr.CompareTo("off")==0) {
				try{
				RPi.I2C.Net.I2CBus.instance.WriteBytes(0x12, new byte[] {0x00,0x00});
				FireStringEvent("off");
				}catch{}
			}
			
		}

		/// <summary>
		/// Here are the delegate and his event.
		/// A function checking nullity should be used to fire events (like FireIntEvent).
		/// </summary>
		public delegate void StringValueEventHandler(String val);
		/// <summary>
		/// the following declaration is the event by itself. Its name, here "PropertyChanged",
		/// is the name of the event as it will be displayed in the bean type's interface.
		/// </summary>
		public event StringValueEventHandler StateChanged;
		
		private void FireStringEvent(String str) {
			if (StateChanged != null) {
				StateChanged(str);
			}
		}
	}
}
