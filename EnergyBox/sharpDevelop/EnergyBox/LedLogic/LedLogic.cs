/*
 * Crée par SharpDevelop.
 * Utilisateur: user
 * Date: 26/01/2016
 * Heure: 16:49
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
	public class LedLogic
	{
		private static int MAX = 100;
		private bool relayState;
		private int currentValue;
		
		public void setRelayState(String rState) {
			if(rState.CompareTo("on")==0) {
				relayState = true;
				computeLogic();
			} else if(rState.CompareTo("off") == 0) {
				relayState = false;
				computeLogic();
			}
		}
		
		public void setCurrentValue(String strValue) {
			currentValue = Int32.Parse(strValue);
			computeLogic();
		}
		
		public void computeLogic() {
			if(relayState) {
				double coef = ((double)currentValue) / ((double) MAX);
				int r = (int)(coef * 255.0);
				int g = (int)((1.0-coef)*255.0);
				int c = (r*256+g)*256;
				setColor(c.ToString("x6"));
			} else {
				setColor("blue");
			}
		}
		
		public void setColor(String colorStr) {
			if(ledColorEvent != null) {
				ledColorEvent(colorStr);
			}
		}

		/// <summary>
		/// Here are the delegate and his event.
		/// A function checking nullity should be used to fire events (like FireIntEvent).
		/// </summary>
		public delegate void LedColorEvent(String val);
		/// <summary>
		/// the following declaration is the event by itself. Its name, here "PropertyChanged",
		/// is the name of the event as it will be displayed in the bean type's interface.
		/// </summary>
		public event LedColorEvent ledColorEvent;
	}
}
