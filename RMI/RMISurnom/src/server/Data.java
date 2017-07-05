package server;

import interfaces.iService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Data extends UnicastRemoteObject implements iService {
	private static final long serialVersionUID = 1L;

	private class Entry {
		public String key;
		public String value;

		public Entry(String key, String value) {
			this.key = key;
			this.value = value;
		}
	}

	private static Data instance;
	private List<Entry> associations;

	public Data() throws RemoteException {
		super();
		this.associations = new ArrayList<Entry>();
	}

	public static Data getInstance() throws RemoteException {
		if (instance == null) {
			instance = new Data();
		}
		return instance;
	}

	private boolean nameExists(String name) {
		for (Entry e : this.associations) {
			if (e.key.equals(name)) {
				return true;
			}
		}
		return false;
	}

	private boolean surnameExists(String surname) {
		for (Entry e : this.associations) {
			if (e.value.equals(surname)) {
				return true;
			}
		}
		return false;
	}

	public void putSurname(String name, String surname) {
		System.out.println("PUTSURNAME");
		if (!surnameExists(surname)) {
			this.associations.add(new Entry(name, surname));
		}
	}

	public void postName(String name, String newName) {
		System.out.println("POSTNAME");
		for (Entry e : this.associations) {
			if (e.key.equals(name)) {
				e.key = newName;
			}
		}
	}

	public void postSurname(String surname, String newSurname) {
		System.out.println("POSTSURNAME");
		for (Entry e : this.associations) {
			if (e.value.equals(surname)) {
				e.value = newSurname;
			}
		}
	}

	public void deleteSurname(String surname) {
		System.out.println("DELETESURNAME");
		for (int i = 0; i < this.associations.size(); i++) {
			if (this.associations.get(i).value.equals(surname)) {
				this.associations.remove(i);
				return;
			}
		}
		return;
	}

	public void deleteName(String name) {
		System.out.println("DELETENAME");
	    if(name == null) return;
	    if(!nameExists(name)) return;
        for(int i = 0 ; i < this.associations.size() ; i++) {
            if(this.associations.get(i).key.equals(name)) {
                this.associations.remove(i);
                i--;
            }
        }
        return;
    }
	@Override
	public Map<String, List<String>> getAll() throws RemoteException {
		System.out.println("GETALL");
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		Set<String> nom = new HashSet<String>(getNames());
		List<String> l = new ArrayList<String>(nom);
		for (String n : l) {
			List<String> ll = new ArrayList<String>();
			for (Entry e : this.associations) {
				if (e.key.equals(n)) {
					ll.add(e.value);
				}
			}
			map.put(n, ll);
		}
		return map;
	}

	@Override
	public List<String> getNames() throws RemoteException {
		System.out.println("GETNAMES");
		Set<String> set = new HashSet<String>();
		for (Entry e : this.associations) {
			set.add(e.key);
		}
		return new ArrayList<String>(set);
	}

	@Override
	public List<String> getSurnames(String name) throws RemoteException {
		System.out.println("GETSURNAMES");
		Set<String> set = new HashSet<String>();
		for (Entry e : this.associations) {
			if(e.key.equals(name)) {
				set.add(e.value);
			}
		}
		return new ArrayList<String>(set);
	}

	@Override
	public List<String> getSurnames() throws RemoteException {
		System.out.println("GETSURNAMES");
		Set<String> set = new HashSet<String>();
		for (Entry e : this.associations) {
			set.add(e.value);
		}
		return new ArrayList<String>(set);
	}

	@Override
	public void testInConsole() throws RemoteException {
		System.out.println("Ca marche !!!");
	}
}
