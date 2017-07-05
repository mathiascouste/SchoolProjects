package model.user;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import model.interfaces.iPrivateUser;
import model.interfaces.iPublicUser;

public class PublicUser extends UnicastRemoteObject implements iPublicUser{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String introduction;
	
	public PublicUser() throws RemoteException {
		this("","");
	}
	public PublicUser(String name, String introduction) throws RemoteException {
		super();
		this.name = name;
		this.introduction = introduction;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public void friendRequest(iPublicUser publicUser) throws RemoteException {
		List<iPublicUser> received = MyUser.getInstance().getFriendsRequestIReceived();
		if(!received.contains(publicUser)) {
			received.add(publicUser);
		}
	}
	@Override
	public String whoAreYou() throws RemoteException {
		return "My name is " + this.name + ".\nLet me introduce myself :\n" + this.introduction;
	}
	@Override // Partie publique et privée de l'user qui accepte la requête
	public iPrivateUser acceptRequest(iPublicUser publicUser, iPrivateUser privateUser) throws RemoteException {
		// Comparing to actual friends
		List<iPrivateUser> friends = MyUser.getInstance().getThisPrivateUser().getFriends();
		if(friends.contains(privateUser)) {
			System.out.println("ERROR:deja amis");
			return null; // Ces users sont déjà amis
		}
		// Comparing to friends request sent
		List<iPublicUser> friendsRequest = MyUser.getInstance().getFriendsRequestISent();
		if(friendsRequest.contains(publicUser)) { // Cet user a bien fait une requete vers publicUser
			friendsRequest.remove(publicUser);
			MyUser.getInstance().getThisPrivateUser().getFriends().add(privateUser);
			return MyUser.getInstance().getThisPrivateUser();
		} else {
			System.out.println("ERROR:pas de requete faites");
			return null;
		}
	}
	@Override
	public void denyRequest(iPublicUser publicUser) throws RemoteException {
		// Comparing to friends request sent
		List<iPublicUser> friendsRequest = MyUser.getInstance().getFriendsRequestISent();
		if(friendsRequest.contains(publicUser)) {
			friendsRequest.remove(publicUser);
		}
	}
}
