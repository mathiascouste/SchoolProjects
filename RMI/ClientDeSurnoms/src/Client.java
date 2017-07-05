import connexion.Connexion;
import view.Menu;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connexion connexion = recuperationConnexion();
		connexion.connect();
		while (connexion.isConnected()) {
			Menu.afficheMenu();
			int choix = Menu.getChoix();
			if (choix != 0) {
				String arguments[] = new String[2];
				recupererArguments(choix, arguments);
				Menu.print(utiliserMethodes(choix, arguments));
			} else {
				utiliserMethodes(0,null);
				break;
			}
		}
		connexion.close();
		Menu.print("Byebye");
	}

	private static Connexion recuperationConnexion() {
		int port = Menu.getPort();
		String arguments[] = new String[2];
		arguments[0] = "adresse du serveur";
		arguments[1] = null;
		Menu.demanderArguments(arguments);
		return Connexion.getInstance(arguments[0], port);
	}

	private static String utiliserMethodes(int choix, String[] arguments) {
		switch (choix) {
		case 0:
			return Services.close();
		case 1:
			return Services.putSurname(arguments[0], arguments[1]);
		case 2:
			return Services.postName(arguments[0], arguments[1]);
		case 3:
			return Services.postSurname(arguments[0], arguments[1]);
		case 4:
			return Services.getAll();
		case 5:
			return Services.getName();
		case 6:
			return Services.getSurnom();
		case 7:
			return Services.getSurnom(arguments[0]);
		case 8:
			return Services.deleteSurnom(arguments[0]);
		case 9:
			return Services.deleteNom(arguments[0]);
		}
		return "";
	}

	private static void recupererArguments(int choix, String[] arguments) {
		arguments[0] = null;
		arguments[1] = null;

		switch (choix) {
		case 1:
			arguments[0] = "nom";
			arguments[1] = "surnom";
			break;
		case 2:
			arguments[0] = "nom actuel";
			arguments[1] = "nouveau nom";
			break;
		case 3:
			arguments[0] = "surnom actuel";
			arguments[1] = "nouveau surnom";
			break;
		case 7:
			arguments[0] = "nom";
			break;
		case 8:
			arguments[0] = "surnom";
			break;
		case 9:
			arguments[0] = "nom";
			break;
		}

		Menu.demanderArguments(arguments);
	}

}
