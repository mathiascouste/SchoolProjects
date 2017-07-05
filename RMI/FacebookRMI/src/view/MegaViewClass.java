package view;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

import model.Notification;
import model.Publication;
import model.Session;
import model.TempWall;
import model.interfaces.iPrivateUser;
import model.interfaces.iPublicUser;
import model.user.MyUser;

public class MegaViewClass {
	private Session session;
	
	public MegaViewClass(Session session) {
		this.session = session;
	}

	public void toInfinityAndBeyond() {
		this.basicMenu();
	}

	private void basicMenu() {
		String toPrint = "";
		toPrint += "### Menu principal ###\n";
		toPrint += "1 - Ajouter un ami\n";
		toPrint += "2 - Voir mes amis\n";
		toPrint += "3 - Voir mes notifications\n";
		toPrint += "4 - Voir mes demandes\n";
		toPrint += "5 - Voir mon mur\n";
		toPrint += "Saisir le numéro de l'action à effectuer : ";
		System.out.println(toPrint);
		switch(askNumber()) {
		case 1:
			try {
				this.addFriendMenu();
			} catch (RemoteException e) {}
			break;
		case 2:
			this.seeFriendsMenu();
			break;
		case 3:
			this.seeNotif();
			break;
		case 4:
			this.seeAsks();
			break;
		case 5:
			this.seeWall(this.session.getUser().getThisPrivateUser());
			break;
		default:
			this.basicMenu();
		}
	}

	private void seeWall(iPrivateUser privateUser) {
		try {
			TempWall tW = new TempWall(privateUser.getWall());
			String toPrint = "";
			toPrint += "## Mur de "+privateUser.getThisPublicUser().getName()+" ##\n";
			for(Publication p : tW.getTempWall()) {
				toPrint += " > " + p.toString()+"\n";
			}
			toPrint += "Taper \"+\" pour voir les messages suivants, \"-\" pour les précédents, \"publish:message\" pour publier \"message\" :";
			System.out.println(toPrint);
			String line = this.askLine();
			String tab[] = line.split(":");
			String opt = tab[0].toUpperCase();
			if(opt.equals("+")) {
				tW.plus();
				this.seeWall(privateUser);
			} else if(opt.equals("-")) {
				tW.minus();
				this.seeWall(privateUser);
			} else if(opt.equals("PUBLISH") && tab.length > 1) {
				String msg = tab[1];
				for(int i = 2 ; i < tab.length ; i++) {
					msg+= ":"+tab[i];
				}
				Publication pp = new Publication(MyUser.getInstance().getThisPrivateUser().getThisPublicUser().getName(),msg);
				privateUser.publish(pp);
				System.out.println("Le message a été publié ...");
				Thread.sleep(200);
				this.basicMenu();
			} else {
				this.basicMenu();
			}
		} catch (RemoteException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void seeAsks() {
		String toPrint = "";
		toPrint += "## Tableau des demandes d'amis ##\n";
		List<iPublicUser> friendRequests = MyUser.getInstance().getFriendsRequestIReceived();
		for(int i = 0 ; i < friendRequests.size() ; i++) {
			try {
				toPrint += i + " - " + friendRequests.get(i).getName() + "\n";
			} catch (RemoteException e) {}
		}
		toPrint += "Entrer \"ACCEPT:#\" or \"DENY:#\" pour accepter ou refuser une demande d'ami : ";
		System.out.println(toPrint);
		String line = askLine();
		String tab[] = line.split(":");
		if(tab.length >= 2) {
			String opt = tab[0].toUpperCase();
			int n = Integer.decode(tab[1]).intValue();
			if(n >= 0 && n < friendRequests.size()) {
				iPublicUser pUser = friendRequests.get(n);
				if(opt.equals("ACCEPT")) {
					try {
						iPrivateUser friend = (iPrivateUser) pUser.acceptRequest((iPublicUser)MyUser.getInstance().getThisPrivateUser().getThisPublicUser(), (iPrivateUser)MyUser.getInstance().getThisPrivateUser());
						if(friend!=null) {
							MyUser.getInstance().getFriendsRequestISent().remove(friend.getThisPublicUser());
							MyUser.getInstance().getThisPrivateUser().getFriends().add(friend);
							this.shortMessage("Vous avez accepté la demande de "+ friend.getThisPublicUser().getName() + "...");
						} else {
							this.shortMessage("Il semblerait que ce programme ait un léger problème ;)");
						}
						this.basicMenu();
					} catch (RemoteException e) {}
				} else if(opt.equals("DENY")) {
					try {
						pUser.denyRequest(MyUser.getInstance().getThisPrivateUser().getThisPublicUser());
						MyUser.getInstance().getFriendsRequestIReceived().remove(pUser);
						this.shortMessage("Vous avez refusé la demande de "+ pUser.getName() + "...");
						this.basicMenu();
					} catch (RemoteException e) {}
				} else {
					this.basicMenu();
				}
			} else {
				this.basicMenu();
			}
		} else {
			this.basicMenu();
		}
	}

	private void shortMessage(String string) {
		System.out.println(string);
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {}
	}

	private String askLine() {
		Scanner scan = new Scanner(System.in);
		return scan.nextLine();
	}

	private void seeNotif() {
		String toPrint = "";
		toPrint += "## Tableau des notifications ##\n";
		List<Notification> notif = MyUser.getInstance().getNotifications();
		for(int i = notif.size()-1 ; i >= 0 ; i--) {
			toPrint += notif.get(i).getNotification()+"\n";
		}
		toPrint += "Entrez [ENTER] pour quitter : ";
		System.out.println(toPrint);
		askNumber();
		this.basicMenu();
	}

	private void seeFriendsMenu() {
		String toPrint = "";
		toPrint += "## Voir mes amis ##\n";
		List<iPrivateUser> friends = MyUser.getInstance().getThisPrivateUser().getFriends();
		for(int i = 0 ; i < friends.size(); i++) {
			try {
				toPrint += i + " - " + friends.get(i).getThisPublicUser().getName()+"\n";
			} catch (RemoteException e) {}
		}
		toPrint += "Entrez le numéro de la personne à voir , -1 sinon :";
		System.out.println(toPrint);
		int n = askNumber();
		if(n >= 0 && n < friends.size()) {
			this.seeWall(friends.get(n));
		} else {
			this.basicMenu();
		}
	}

	private void addFriendMenu() throws RemoteException {
		String toPrint = "";
		toPrint += "## Ajouter un ami ##\n";
		this.session.lookup();
		for(int i = 0 ; i < this.session.getConnectedUsers().size() ; i++) {
			toPrint += i + " - " + this.session.getConnectedUsers().get(i).whoAreYou()+"\n";
		}
		toPrint += "Entrez le numéro de la personne à ajouter , -1 sinon :";
		System.out.println(toPrint);
		int n = askNumber();
		if(n >= 0 && n < this.session.getConnectedUsers().size()) {
			iPublicUser pU =  this.session.getConnectedUsers().get(n);
			pU.friendRequest(MyUser.getInstance().getThisPrivateUser().getThisPublicUser());
			MyUser.getInstance().getFriendsRequestISent().add(pU);
			System.out.println("Demande d'ami enregistrée ...");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {}
		}
		this.basicMenu();
	}

	private int askNumber() {
		Scanner scan = new Scanner(System.in);
		String response = scan.next();
		Integer i = new Integer(-1);
		try {
			i = Integer.decode(response);
		} catch(NumberFormatException e) {
		}
		return i.intValue();
	}
}
