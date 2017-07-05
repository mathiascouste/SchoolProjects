/*
 * Crée par SharpDevelop.
 * Utilisateur: user
 * Date: 07/02/2016
 * Heure: 16:27
 * 
 * Pour changer ce modèle utiliser Outils | Options | Codage | Editer les en-têtes standards.
 */
using System;

namespace Statistics
{
	/// <summary>
	/// Description of Entry.
	/// </summary>
	public class Entry
	{
		public DateTime dateTime;
		public double current;
		public double power;
		public double duration;
		public double work;
		public double price;		
		
		public Entry()
		{
			dateTime = DateTime.Now;
			current = 0;
			power = 0;
			duration = 0;
			work = 0;
			price = 0;
		}
	}
}
