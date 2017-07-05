package connexion;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connexion {
	private static Connexion instance = null;
	private String adress;
	private int port;

	private Socket socket;

	ObjectOutputStream out = null;
	ObjectInputStream in = null;

	public Connexion(String adress, int port) {
		this.adress = adress;
		this.port = port;
		this.socket = null;
	}

	public static Connexion getInstance(String adresse, int port) {
		if (instance == null) {
			instance = new Connexion(adresse, port);
		}
		return instance;
	}

	public static Connexion getInstance() {
		if (instance == null) {
			return null;
		}
		return instance;
	}

	public String connect() {
		try {
			this.socket = new Socket(this.adress, this.port);
		} catch (UnknownHostException e) {
			return "UnknownHost";
		} catch (IOException e) {
			return "Error";
		}
		return "OK";
	}

	public boolean isConnected() {
		if (this.socket != null) {
			return this.socket.isConnected();
		} else {
			return false;
		}
	}

	public String sendString(String message) {
		PrintWriter out;
		try {
			out = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			return "IOEXCEPTION";
		}
		out.print(message);
		out.flush();
		return "OK";
	}

	public String sendObject(Object object) {
		try {
			if (out == null) {
				out = new ObjectOutputStream(new BufferedOutputStream(
						socket.getOutputStream()));
			}
			out.writeObject(object);
			out.flush();
		} catch (IOException e) {
			return "IOEXCEPTION";
		}
		return "OK";
	}

	public String receiveString() {
		BufferedReader in;
		String ret = "";
		String line;
		try {
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			while ((line = in.readLine()) != null) {
				ret += line;
			}
		} catch (IOException e1) {
			ret = "ERREUR";
		}
		return ret;
	}

	public Object receiveObject() {
		Object object = null;
		try {
			if (in == null) {
				in = new ObjectInputStream(new BufferedInputStream(
						(socket.getInputStream())));
			}
			object = in.readObject();
		} catch (IOException | ClassNotFoundException e) {
		}
		return object;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public static void main(String[] args) {
		Connexion connexion = new Connexion("www.couste.eu", 80);
		System.out.println(connexion.connect());
		String str = "GET / HTTP/1.1\nHost: www.couste.eu\nConnection: Close\n\n";
		System.out.println(connexion.sendString(str));
		System.out.println(connexion.receiveString());
	}

	public void close() {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
