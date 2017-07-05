package model.interfaces;

import java.util.List;

import model.Publication;
import model.enums.Action;
import model.user.PrivateUser;
import model.user.PublicUser;

public interface iPrivateUser extends java.rmi.Remote {
	public void publish(Publication publication) throws java.rmi.RemoteException;
	public void notifyAction(PrivateUser privateUser, Action actionEnum) throws java.rmi.RemoteException;
	public String checkWall() throws java.rmi.RemoteException;
	public List<iPublicUser> friendSuggestion() throws java.rmi.RemoteException;
	public iPublicUser getThisPublicUser() throws java.rmi.RemoteException;
	public List<Publication> getWall() throws java.rmi.RemoteException;
}
