#ifndef _POSERCOMMANDE_HPP_
#define _POSERCOMMANDE_HPP_

#include "CommandeRobot.hpp"
#include "../Robot.hpp"

class PoserCommande : public CommandeRobot {
	private:
		static PoserCommande basic;
		Objet * lastObjet;
	public:
		PoserCommande(string nom);
		void load(Invocateur * invocateur);
		void execute();
		void desexecute();
		virtual Commande * nouveau();
};

#endif
