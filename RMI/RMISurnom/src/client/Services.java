package client;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class Services {

	public static String deleteNom(String nom) {
		try {
			Client.service.deleteName(nom);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return "OK";
	}

	public static String deleteSurnom(String surnom) {
		try {
			Client.service.deleteSurname(surnom);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return "OK";
	}

	public static String getSurnom(String surnom) {
		List<String> ret = null;
		try {
			ret = Client.service.getSurnames(surnom);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if(ret!=null) {
			return ret.toString();
		} else {
			return "NOK";
		}
	}

	public static String getSurnom() {
		List<String> ret = null;
		try {
			ret = Client.service.getSurnames();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if(ret!=null) {
			return ret.toString();
		} else {
			return "NOK";
		}
	}

	public static String getName() {
		List<String> ret = null;
		try {
			ret = Client.service.getNames();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if(ret!=null) {
			return ret.toString();
		} else {
			return "NOK";
		}
	}

	public static String getAll() {
		Map<String,List<String>> ret = null;
		try {
			ret = Client.service.getAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if(ret!=null) {
			return ret.toString();
		} else {
			return "NOK";
		}
	}

	public static String postSurname(String surnom, String newSurnom) {
		try {
			Client.service.postSurname(surnom, newSurnom);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return "OK";
	}

	public static String postName(String nom, String newNom) {
		try {
			Client.service.postName(nom, newNom);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return "OK";
	}

	public static String putSurname(String nom, String surnom) {
		try {
			Client.service.putSurname(nom, surnom);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return "OK";
	}
}
