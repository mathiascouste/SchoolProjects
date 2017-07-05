package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface iService extends Remote {
	public void testInConsole() throws RemoteException;
	public void putSurname(String name, String surname) throws RemoteException;
	public void postName(String name, String newName) throws RemoteException;
	public void postSurname(String surname, String newSurname) throws RemoteException;
	public Map<String, List<String>> getAll() throws RemoteException;
	public List<String> getNames() throws RemoteException;
	public List<String> getSurnames(String name) throws RemoteException;
	public List<String> getSurnames() throws RemoteException;
	public void deleteSurname(String surname) throws RemoteException;
	public void deleteName(String name) throws RemoteException;
}
