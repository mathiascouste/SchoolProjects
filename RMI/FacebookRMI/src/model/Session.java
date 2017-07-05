package model;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import view.MegaViewClass;

import model.interfaces.iPublicUser;
import model.user.MyUser;

public class Session {
	private Registry registry;
	private MyUser user;
	private List<iPublicUser> connectedUsers;
	
	public Session() {
		try {
			this.registry = LocateRegistry.getRegistry(1099);
		} catch (RemoteException e) {
			System.out.println("Aborting to connect to rmiRegistory ...\n Try connecting to internet");
			System.exit(0);
		}
		this.user = MyUser.getInstance();
		this.connectedUsers = new ArrayList<iPublicUser>();
	}

	public void createIdentity() {
		Scanner scan = new Scanner(System.in);
		System.out.print("Quel est ton nom ?\n>");
		this.user.getThisPrivateUser().getThisPublicUser().setName(scan.next());
		System.out.print("Quel est ta description ?\n>");
		this.user.getThisPrivateUser().getThisPublicUser().setIntroduction(scan.next());
	}

	public void showIdentity() {
		System.out.println("Hello "+user.getThisPrivateUser().getThisPublicUser().getName() +" :");
		System.out.println("Name\t\t:  "+user.getThisPrivateUser().getThisPublicUser().getName());
		System.out.println("Introduction\t:  "+user.getThisPrivateUser().getThisPublicUser().getIntroduction());
	}

	public void subscribe() {
		try {
			this.registry.rebind(((String)this.user.getThisPrivateUser().getThisPublicUser().getName()+"Stub"), this.user.getThisPrivateUser().getThisPublicUser());
		} catch (RemoteException e) {
			e.printStackTrace();
			System.out.println("Maybe name seems already used ...\n Try using another one");
			System.exit(0);
		}
	}

	public void lookup() {
		try {
			String[] list = this.registry.list();
			this.connectedUsers.clear();
			for(String l : list) {
				iPublicUser publicUser = (iPublicUser) this.registry.lookup(l);
				if(!this.user.getThisPrivateUser().getThisPublicUser().getName().equals(publicUser.getName())) {
					this.connectedUsers.add(publicUser);
				}
			}
		} catch (RemoteException | NotBoundException e) {
			System.out.println("Sorry but your Internet is down\n Try starting it before using this program ...");
			System.exit(0);
		}
	}

	public void letsgo() {
		MegaViewClass mVC = new MegaViewClass(this);
		mVC.toInfinityAndBeyond();
	}

	public void close() {
		System.exit(0);
	}

	public Registry getRegistry() {
		return registry;
	}

	public void setRegistry(Registry registry) {
		this.registry = registry;
	}

	public MyUser getUser() {
		return user;
	}

	public void setUser(MyUser user) {
		this.user = user;
	}

	public List<iPublicUser> getConnectedUsers() {
		return connectedUsers;
	}

	public void setConnectedUsers(List<iPublicUser> connectedUsers) {
		this.connectedUsers = connectedUsers;
	}

}
