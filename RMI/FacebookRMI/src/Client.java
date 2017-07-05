import java.rmi.RemoteException;

import model.Session;


public class Client {
	public static void main(String[] args) throws RemoteException {
		Session session = new Session();
		
		session.createIdentity();
		session.showIdentity();
		session.subscribe();
		session.lookup();
		session.letsgo();
		
		session.close();
	}
}
