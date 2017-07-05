/*
 * Crée par SharpDevelop.
 * Utilisateur: user
 * Date: 25/01/2016
 * Heure: 22:49
 * 
 * Pour changer ce modèle utiliser Outils | Options | Codage | Editer les en-têtes standards.
 */
using System;
using System.Threading; // For the thread demo purposes
using WComp.Beans;
using RPi.I2C.Net;

namespace WComp.Beans
{
	/// <summary>
	/// This is a sample bean, using a thread, which has an integer evented property and a method 
        ///     to start the thread.
	/// 
	/// Notes: this bean uses the IThreadCreator interface providing a cleanup method named `Stop()'.
	/// Several classes can be defined or used by a Bean, but only the class with the
	/// [Bean] attribute will be available in WComp. Its ports will be all public methods,
	///     events and properties definied in that class.
	/// </summary>
	[Bean(Category="MyCategory")]
	public class CurrentSensor : IThreadCreator {
		private static int ACTIVE = 1;
		private static int INACTIVE = 0;
		private static int LOW_THRESHOLD = 10;
		
		private Thread t;	  // Private attributes of the class
		private volatile bool run = false;
		private int sleepVal = 1000;
		private int state = INACTIVE;
		private int kCurrent = 0;
 
		public CurrentSensor() {
			Start();
		}
		
		public void setCurrentReadPeriod(String nSeconds) {
			try {
				int seconds = Int32.Parse(nSeconds);
				sleepVal = 1000*seconds;
			} catch {
				
			}
		}
 
		private void Start() {  // method starting the thread
			if (!run) {
				run = true;
				t = new Thread(new ThreadStart(ThreadLoopMethod));
				t.Start();
			} 
		}
		public void Stop() {   // IThreadCreator defines the Stop() method
			run = false;
		} 
 
		 // Loop sample
		 public void ThreadLoopMethod() {
			 while(run) {
				 Thread.Sleep(sleepVal);
				 
				 try {
					 I2CBus.instance.WriteBytes(0x12,new byte[] {0x02});
			 		 int current = I2CBus.instance.ReadBytes(0x12,1)[0];
			 		 int filteredCurrent = filterCurrent(current);
			 		 
			 		 checkIfStateChanged(filteredCurrent);
					 if(currentValueEvent != null) {
					 	currentValueEvent(current);
					 }
				 } catch{}
			 }
		}
		 
		 private int filterCurrent(int current) {
		 	kCurrent = (kCurrent + current)/2;
		 	return kCurrent;
		 }
		 
		 private void checkIfStateChanged(int current) {
		 	int oldState = state;
		 	if(current <= LOW_THRESHOLD && state == ACTIVE) {
		 		state = INACTIVE;
		 	}
		 	if(current > LOW_THRESHOLD && state == INACTIVE) {
		 		state = ACTIVE;
		 	}
		 	if(state != oldState) {
		 		if(stateChangedEvent != null) {
				 	stateChangedEvent(state);
				 }
		 	}
		 }
 
		// --- Start: Output port sample ---
		public delegate void CurrentValue_Signature(int val);
		// The delegate defines the signature of the output method
		public event CurrentValue_Signature currentValueEvent;
		
		public delegate void StateChanged_Signature(int state);
		public event StateChanged_Signature stateChangedEvent;
	}
}
