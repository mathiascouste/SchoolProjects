/*
 * Crée par SharpDevelop.
 * Utilisateur: user
 * Date: 19/01/2016
 * Heure: 15:48
 * 
 * Pour changer ce modèle utiliser Outils | Options | Codage | Editer les en-têtes standards.
 */
using System;

namespace RPi.I2C.Net
{
	/// <summary>
	/// Description of I2CBus_win.
	/// </summary>
	public class I2CBus : II2CBus, IDisposable
	{
		public static I2CBus instance = new I2CBus("/dev/i2c-1");
		
		/// <summary>
		/// .ctor
		/// </summary>
		/// <param name="busPath"></param>
		private I2CBus(string busPath)
		{
		}


		/// <summary>
		/// Creates new instance of I2CBus.
		/// </summary>
		/// <param name="busPath">Path to system file associated with I2C bus.<br/>
		/// For RPi rev.1 it's usually "/dev/i2c-0",<br/>
		/// For rev.2 it's "/dev/i2c-1".</param>
		/// <returns></returns>
		public static I2CBus Open(string busPath)
		{
			return new I2CBus(busPath);
		}



		public void Finalyze()
		{
			Dispose(false);
		}



		public void Dispose()
		{
			Dispose(true);
			GC.SuppressFinalize(this);
		}



		protected virtual void Dispose(bool disposing)
		{
		}


		/// <summary>
		/// Writes single byte.
		/// </summary>
		/// <param name="address">Address of a destination device</param>
		/// <param name="b"></param>
		public void WriteByte(int address, byte b)
		{
		}


		/// <summary>
		/// Writes array of bytes.
		/// </summary>
		/// <remarks>Do not write more than 3 bytes at once, RPi drivers don't support this currently.</remarks>
		/// <param name="address">Address of a destination device</param>
		/// <param name="bytes"></param>
		public void WriteBytes(int address, byte[] bytes)
		{
		}


		/// <summary>
		/// Writes command with data.
		/// </summary>
		/// <param name="address"></param>
		/// <param name="command"></param>
		/// <param name="data"></param>
		public void WriteCommand(int address, byte command, byte data)
		{
		}


		/// <summary>
		/// Writes command with data.
		/// </summary>
		/// <param name="address"></param>
		/// <param name="command"></param>
		/// <param name="data1"></param>
		/// <param name="data2"></param>
		public void WriteCommand(int address, byte command, byte data1, byte data2)
		{
		}

		
		/// <summary>
		/// Writes command with data.
		/// </summary>
		/// <param name="address"></param>
		/// <param name="command"></param>
		/// <param name="data"></param>
		public void WriteCommand(int address, byte command, ushort data)
		{
		}


		/// <summary>
		/// Reads bytes from device with passed address.
		/// </summary>
		/// <param name="address"></param>
		/// <param name="count"></param>
		/// <returns></returns>
		public byte[] ReadBytes(int address, int count)
		{
			byte[] buf = new byte[count];
			return buf;
		}
	}
}
