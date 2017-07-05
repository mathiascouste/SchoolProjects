package model.user;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import model.Notification;
import model.interfaces.iPublicUser;

public class MyUser {
	private static MyUser instance;
	public static MyUser getInstance() {
		if(instance == null) {
			try {
				instance = new MyUser();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	private PrivateUser thisPrivateUser;
	private List<iPublicUser> friendsRequestISent;
	private List<iPublicUser> friendsRequestIReceived;
	private List<Notification> notifications;
	
	private MyUser() throws RemoteException {
		this.thisPrivateUser = new PrivateUser();
		this.friendsRequestIReceived = new ArrayList<iPublicUser>();
		this.friendsRequestISent = new ArrayList<iPublicUser>();
		this.notifications = new ArrayList<Notification>();
	}

	public PrivateUser getThisPrivateUser() {
		return thisPrivateUser;
	}

	public void setThisPrivateUser(PrivateUser thisPrivateUser) {
		this.thisPrivateUser = thisPrivateUser;
	}

	public List<iPublicUser> getFriendsRequestISent() {
		return friendsRequestISent;
	}

	public void setFriendsRequestISent(List<iPublicUser> friendsRequestISent) {
		this.friendsRequestISent = friendsRequestISent;
	}

	public List<iPublicUser> getFriendsRequestIReceived() {
		return friendsRequestIReceived;
	}

	public void setFriendsRequestIReceived(List<iPublicUser> friendsRequestIReceived) {
		this.friendsRequestIReceived = friendsRequestIReceived;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	public static void setInstance(MyUser instance) {
		MyUser.instance = instance;
	}
}
