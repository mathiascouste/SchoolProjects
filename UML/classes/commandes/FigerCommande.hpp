#ifndef _FIGERCOMMANDE_HPP_
#define _FIGERCOMMANDE_HPP_

#include "CommandeRobot.hpp"
#include "../Robot.hpp"

class FigerCommande : public CommandeRobot {
	private:
		static FigerCommande basic;
		Etat * lastEtat;
	public:
		FigerCommande(string nom);
		void load(Invocateur * invocateur);
		void execute();
		void desexecute();
		virtual Commande * nouveau();
};

#endif
