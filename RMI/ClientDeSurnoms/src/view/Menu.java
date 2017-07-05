package view;

import java.util.Scanner;

public class Menu {
	private static Scanner scan = new Scanner(System.in);
	
	public static void afficheMenu() {
		System.out.println("###   MENU   ###");
		System.out.println(" 1 - Ajouter une association nom - surnom");
		System.out.println(" 2 - Modifier un nom");
		System.out.println(" 3 - Modifier un surnom");
		System.out.println(" 4 - Visionner l'ensemble des données");
		System.out.println(" 5 - Visionner tous les noms");
		System.out.println(" 6 - Visionner tous les surnoms");
		System.out.println(" 7 - Visionner tous les surnoms d'un nom");
		System.out.println(" 8 - Supprimer un surnom");
		System.out.println(" 9 - Supprimer un nom et tous les surnoms associés");
		System.out.println(" 0 - Quitter");
	}

	public static void print(String string) {
		System.out.println(string);
	}

	public static int getChoix() {
		String str = "";
		while(!str.matches("[0-9]")) {
			System.out.println("Entrez un chiffre pour avancer");
			str = scan.nextLine();
		}
		return new Integer(str).intValue();
	}

	public static int getPort() {
		String str = "";
		while(!str.matches("[0-9]{3,4}")) {
			System.out.println("Entrez le port de connexion");
			str = scan.nextLine();
		}
		return new Integer(str).intValue();
	}

	public static void demanderArguments(String[] arguments) {
		for(int i = 0 ; i < arguments.length ; i++) {
			if(arguments[i] != null) {
				System.out.println("Veuillez saisir le " + arguments[i]);
				arguments[i] = scan.next();
			}
		}
	}

}
