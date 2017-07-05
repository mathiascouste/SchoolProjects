package model.interfaces;

import model.user.PrivateUser;
import model.user.PublicUser;

public interface iPublicUser extends java.rmi.Remote {
	public void friendRequest(iPublicUser publicUser) throws java.rmi.RemoteException;
	public String whoAreYou() throws java.rmi.RemoteException;
	public iPrivateUser acceptRequest(iPublicUser iPublicUser, iPrivateUser iPrivateUser) throws java.rmi.RemoteException;
	public void denyRequest(iPublicUser publicData) throws java.rmi.RemoteException;
	public String getName() throws java.rmi.RemoteException;
}
