/*
 * Crée par SharpDevelop.
 * Utilisateur: user
 * Date: 07/02/2016
 * Heure: 16:31
 * 
 * Pour changer ce modèle utiliser Outils | Options | Codage | Editer les en-têtes standards.
 */
using System;
using System.Collections.Generic;
using WComp.Beans;
using Statistics;

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
	public class Statistics
	{
		private static double VOLTAGE = 220;
		private List<Entry> entries = new List<Entry>();
		private double price = 0;
		private double totalPrice = 0;
		
		public void setPrice(String strPrice) {
			price = double.Parse(strPrice);
		}
		
		public void reset() {
			totalPrice = 0;
			emitTotalPrice();
		}
		
		public void addData(int data) {
			Entry e = new Entry();
			e.current = dataToCurrent(data);
			if(entries.Count == 0) {
				e.duration = 0;
			} else {
				e.duration = compareDateTime(entries[entries.Count -1].dateTime, e.dateTime);
			}
			e.power = VOLTAGE*e.current;
			e.work = e.power*e.duration;
			e.price = e.work * getPrice() /3600;
			entries.Add(e);
			totalPrice += e.price;
			emitTotalPrice();
		}
		
		public delegate void priceHandlerMethod(double price);
		public event priceHandlerMethod totalPriceEvent;
		
		private void emitTotalPrice() {
			if(totalPriceEvent != null) {
				totalPriceEvent(totalPrice);
			}
		}
		
		private double getPrice() {
			return price;
		}
		
		private double dataToCurrent(int data) {
			double dData = data;
			return dData*30/1024;
		}
	
		private double compareDateTime(DateTime deb, DateTime end) {
			double day = end.Day - deb.Day;
			double hour = end.Hour - deb.Hour + day * 24;
			double minute = end.Minute - deb.Minute + hour * 60;
			double second = end.Second - deb.Second + minute * 60;
			return second;
		}
	}
}
