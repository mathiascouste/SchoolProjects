package model.user;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import model.Notification;
import model.Publication;
import model.enums.Action;
import model.interfaces.iPrivateUser;
import model.interfaces.iPublicUser;

public class PrivateUser extends UnicastRemoteObject implements iPrivateUser {
	private static final long serialVersionUID = 1L;
	
	private List<Publication> wall;
	private List<iPrivateUser> friends;
	
	private PublicUser thisPublicUser;
	
	public PrivateUser() throws RemoteException {
		this(new PublicUser());
	}
	public PrivateUser(PublicUser publicUser) throws RemoteException {
		super();
		this.thisPublicUser = publicUser;
		this.wall = new ArrayList<Publication>();
		this.friends = new ArrayList<iPrivateUser>();
	}
	public List<Publication> getWall() {
		return wall;
	}
	public void setWall(List<Publication> wall) {
		this.wall = wall;
	}
	public List<iPrivateUser> getFriends() {
		return friends;
	}
	public void setFriends(List<iPrivateUser> friends) {
		this.friends = friends;
	}
	public PublicUser getThisPublicUser() {
		return thisPublicUser;
	}
	public void setThisPublicUser(PublicUser thisPublicUser) {
		this.thisPublicUser = thisPublicUser;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public void publish(Publication publication) throws RemoteException {
		this.wall.add(publication);
	}
	@Override
	public void notifyAction(PrivateUser privateUser, Action actionEnum) throws RemoteException {
		MyUser.getInstance().getNotifications().add(new Notification(privateUser,actionEnum));
	}
	@Override
	public String checkWall() throws RemoteException {
		List<Publication> shortWall = new ArrayList<Publication>();
		int MAX_MSSG = 10;
		for(int i = 0; i < MAX_MSSG; i++) {
			shortWall.add(this.wall.get(this.wall.size()-1-i));
		}
		String toRet = "";
		for(Publication p : shortWall) {
			toRet += p;
		}
		return toRet;
	}
	@Override
	public List<iPublicUser> friendSuggestion() throws RemoteException {
		List<iPublicUser> fS = new ArrayList<iPublicUser>();
		for(iPrivateUser pU : this.friends) {
			fS.add(pU.getThisPublicUser());
		}
		return fS;
	}
}
