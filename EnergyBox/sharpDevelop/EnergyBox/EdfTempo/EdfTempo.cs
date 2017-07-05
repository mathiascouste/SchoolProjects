/*
 * Crée par SharpDevelop.
 * Utilisateur: user
 * Date: 07/02/2016
 * Heure: 16:03
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
	public class EdfTempo
	{
		private static String tempo;
		
		
		
		public delegate void priceHandlerMethod(double price);
		public event priceHandlerMethod newPrice;
		
		public void checkPrice() {
			if(newPrice != null) {
				newPrice(getPrice());
			}
		}
		
		// prix kWh
		private double getPrice() {
			if(shouldResetTempo()) {
				getTempo();
			}
			if(tempo == "BLEU") {
				return 0.111;
			} else if (tempo == "BLANC") {
				return 0.155;
			}else if(tempo == "ROUGE") {
				return 0.617;
			}
			return 0;
		}
		
		private bool shouldResetTempo() {
			return tempo == null;
		}
		
		private void getTempo() {
			tempo = "ROUGE";
		}
	}
}
