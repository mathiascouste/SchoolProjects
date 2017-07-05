package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Serveur {
	public static void main(String [] args){
		System.out.println("SERVEUR :");
		try {
			Registry registry = LocateRegistry.createRegistry(1993);
			registry.rebind("service", Data.getInstance());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
