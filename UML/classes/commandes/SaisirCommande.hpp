#ifndef _SAISIRCOMMANDE_HPP_
#define _SAISIRCOMMANDE_HPP_

#include "CommandeRobot.hpp"
#include "../Robot.hpp"

#include <iostream>

using namespace std;

class SaisirCommande : public CommandeRobot {
	private:
		static SaisirCommande basic;
		Objet * nextObjet;
	public:
		SaisirCommande(string nom);
		void load(Invocateur * invocateur);
		void execute();
		void desexecute();
		virtual Commande * nouveau();
};

#endif
