/*
 * Crée par SharpDevelop.
 * Utilisateur: user
 * Date: 19/01/2016
 * Heure: 14:18
 * 
 * Pour changer ce modèle utiliser Outils | Options | Codage | Editer les en-têtes standards.
 */
using System;
using System.Globalization;
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
	public class Led
	{
		/// <summary>
		/// Fill in private attributes here.
		/// </summary>
		private String color;

		/// <summary>
		/// This property will appear in bean's property panel and bean's input functions.
		/// </summary>
		public String MyColor {
			get { return color; }
			set {
				color = value;
				FireStringEvent(color);		// event will be fired for every property set.
			}
		}

		/// <summary>
		/// A method sending an event, which is here simply the argument + 1.
		/// Note that there is no return type to the method, because we use events to send
		/// information in WComp. Return values don't have to be used.
		/// </summary>
		public void ChangeColor(String color) {
			
			// rouge
			if(color.CompareTo("red") == 0) {
				try{
				RPi.I2C.Net.I2CBus.instance.WriteBytes(0x12, new byte[] {0x01, 0xff, 0x00, 0x00});
				 } catch{}
			}
			// vert
			else if(color.CompareTo("green") == 0) {
					try{
				RPi.I2C.Net.I2CBus.instance.WriteBytes(0x12, new byte[] {0x01, 0x00, 0xff, 0x00});
				 } catch{}
			}
			// bleu
			else if(color.CompareTo("blue") == 0) {
					try{
				RPi.I2C.Net.I2CBus.instance.WriteBytes(0x12, new byte[] {0x01, 0x00, 0x00, 0xff});
				 } catch{}
			}
			// blanc
			else if(color.CompareTo("white") == 0) {
					try{
				RPi.I2C.Net.I2CBus.instance.WriteBytes(0x12, new byte[] {0x01, 0xff, 0xff, 0xff});
				 } catch{}
			}
			// noir
			else if(color.CompareTo("black") == 0) {
					try{
				RPi.I2C.Net.I2CBus.instance.WriteBytes(0x12, new byte[] {0x01, 0x00, 0x00, 0x00});
				 } catch{}
			}
			// magenta
			else if(color.CompareTo("magenta") == 0) {
					try{
				RPi.I2C.Net.I2CBus.instance.WriteBytes(0x12, new byte[] {0x01, 0xff, 0x00, 0xff});
				 } catch{}
			}
			// jone
			else if(color.CompareTo("yellow") == 0) {
					try{
				RPi.I2C.Net.I2CBus.instance.WriteBytes(0x12, new byte[] {0x01, 0xff, 0xff, 0xe0});
				 } catch{}
			}
			else{
				try{
					
					byte blue = Byte.Parse(color.Substring(0,2), NumberStyles.HexNumber);
					byte red = Byte.Parse(color.Substring(2,2), NumberStyles.HexNumber);
					byte green = Byte.Parse(color.Substring(4,2), NumberStyles.HexNumber);
					
					RPi.I2C.Net.I2CBus.instance.WriteBytes(0x12, new byte[] {0x01, blue, red, green});
				}
				catch {
					try{
					RPi.I2C.Net.I2CBus.instance.WriteBytes(0x12, new byte[] {0x01, 0x00, 0x00, 0x00});
				 } catch{}
				}
			}
			FireStringEvent(color);
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
		public event StringValueEventHandler ColorChanged;
		
		private void FireStringEvent(String str) {
			if (ColorChanged != null)
				ColorChanged(str);
		}
	}
}
