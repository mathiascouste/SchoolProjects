import connexion.Connexion;
import sds.ErrorCode;
import sds.Query;
import sds.Reply;
import sds.Service;

public class Services {

	public static String deleteNom(String string) {
		Query query = new Query(Service.DELETE_NAME, string, null);
		Connexion.getInstance().sendObject(query);
		Reply reply = null;
		Object object = null;
		if ((object = Connexion.getInstance().receiveObject()) instanceof Reply) {
			reply = (Reply) object;
			return fromReplyToString(query, reply);
		} else {
			return "ERROR : problème de reception";
		}
	}

	public static String deleteSurnom(String string) {
		Query query = new Query(Service.DELETE_SURNAME, string, null);
		Connexion.getInstance().sendObject(query);
		Reply reply = null;
		Object object = null;
		if ((object = Connexion.getInstance().receiveObject()) instanceof Reply) {
			reply = (Reply) object;
			return fromReplyToString(query, reply);
		} else {
			return "ERROR : problème de reception";
		}
	}

	public static String getSurnom(String string) {
		Query query = new Query(Service.GET_SURNAME, string, null);
		Connexion.getInstance().sendObject(query);
		Reply reply = null;
		Object object = null;
		if ((object = Connexion.getInstance().receiveObject()) instanceof Reply) {
			reply = (Reply) object;
			return fromReplyToString(query, reply);
		} else {
			return "ERROR : problème de reception";
		}
	}

	public static String getSurnom() {
		Query query = new Query(Service.GET_SURNAME, null, null);
		Connexion.getInstance().sendObject(query);
		Reply reply = null;
		Object object = null;
		if ((object = Connexion.getInstance().receiveObject()) instanceof Reply) {
			reply = (Reply) object;
			return fromReplyToString(query, reply);
		} else {
			return "ERROR : problème de reception";
		}
	}

	public static String getName() {
		Query query = new Query(Service.GET_NAME, null, null);
		Connexion.getInstance().sendObject(query);
		Reply reply = null;
		Object object = null;
		if ((object = Connexion.getInstance().receiveObject()) instanceof Reply) {
			reply = (Reply) object;
			return fromReplyToString(query, reply);
		} else {
			return "ERROR : problème de reception";
		}
	}

	public static String getAll() {
		Query query = new Query(Service.GET_ALL, null, null);
		Connexion.getInstance().sendObject(query);
		Reply reply = null;
		Object object = null;
		if ((object = Connexion.getInstance().receiveObject()) instanceof Reply) {
			reply = (Reply) object;
			return fromReplyToString(query, reply);
		} else {
			return "ERROR : problème de reception";
		}
	}

	public static String postSurname(String string, String string2) {
		Query query = new Query(Service.POST_SURNAME, string, string2);
		Connexion.getInstance().sendObject(query);
		Reply reply = null;
		Object object = null;
		if ((object = Connexion.getInstance().receiveObject()) instanceof Reply) {
			reply = (Reply) object;
			return fromReplyToString(query, reply);
		} else {
			return "ERROR : problème de reception";
		}
	}

	public static String postName(String string, String string2) {
		Query query = new Query(Service.POST_NAME, string, string2);
		Connexion.getInstance().sendObject(query);
		Reply reply = null;
		Object object = null;
		if ((object = Connexion.getInstance().receiveObject()) instanceof Reply) {
			reply = (Reply) object;
			return fromReplyToString(query, reply);
		} else {
			return "ERROR : problème de reception";
		}
	}

	public static String putSurname(String string, String string2) {
		System.out.println(Connexion.getInstance().isConnected());
		Query query = new Query(Service.PUT_SURNAME, string, string2);
		System.out.println(Connexion.getInstance().sendObject(query));
		Reply reply = null;
		Object object = Connexion.getInstance().receiveObject();
		if (object instanceof Reply) {
			reply = (Reply) object;
			return fromReplyToString(query, reply);
		} else {
			return "ERROR : problème de reception";
		}
	}

	private static String fromReplyToString(Query query, Reply reply) {
		String toRet = "";
		if (reply.getCodeError() == ErrorCode.OK) {
			toRet += "Réponse au service - " + serviceName(reply.getService())
					+ " :\n";
			if (reply.getService() >= 4 && reply.getService() <= 6) {
				toRet += "Objet réponse : \n";
				toRet += reply.getData();
			} else {
				toRet += "Le service s'est bien déroullé";
			}
		} else {
			toRet += "Erreur : ";
			switch (reply.getCodeError()) {
			case ErrorCode.BAD_REQUEST:
				toRet += "BAD REQUEST";
				break;
			case ErrorCode.IM_A_TEAPOT:
				toRet += "Apparement, le serveur est une théière";
				break;
			case ErrorCode.LOCKED:
				toRet += "la ressource est vérouillée";
				break;
			case ErrorCode.NOT_FOUND:
				toRet += "la ressource n'a pas été trouvée";
				break;
			case ErrorCode.NOT_IMPLEMENTED:
				toRet += "ce service n'est pas implémenté";
				break;
			}
		}
		return toRet;
	}

	private static String serviceName(int service) {
		switch (service) {
		case Service.DELETE_NAME:
			return "DELETE_NAME";
		case Service.DELETE_SURNAME:
			return "DELETE_SURNAME";
		case Service.GET_ALL:
			return "GET_ALL";
		case Service.GET_NAME:
			return "GET_NAME";
		case Service.GET_SURNAME:
			return "GET_SURNAME";
		case Service.POST_NAME:
			return "POST_NAME";
		case Service.POST_SURNAME:
			return "POST_SURNAME";
		case Service.PUT_SURNAME:
			return "PUT_SURNAME";
		default:
			return "UNKNOWN_SERVICE";
		}
	}

	public static String close() {
		Query query = new Query(Service.CLOSE, null, null);
		Connexion.getInstance().sendObject(query);
		return "";
	}
}
