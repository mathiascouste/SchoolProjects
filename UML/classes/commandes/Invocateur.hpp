#ifndef _INVOCATEUR_HPP_
#define _INVOCATEUR_HPP_

#include <iostream>
#include <string>

#include "../Robot.hpp"
#include "Commande.hpp"

using namespace std;

class Commande;

class Invocateur {
	private:
		Robot * robot;
		int lireUneCommande();
		void afficherMenu();
	public:
		Invocateur(Robot * robot);
		void lireCommandes();
		int nextInt();
		Plot* nextPlot();
		Objet* nextObjet();
		string nextString();
		Robot* currentRobot();
		Commande * nextCommande();
};

#endif
