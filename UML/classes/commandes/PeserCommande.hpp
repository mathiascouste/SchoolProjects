#ifndef _PESERCOMMANDE_HPP_
#define _PESERCOMMANDE_HPP_

#include "CommandeRobot.hpp"
#include "../Robot.hpp"

class PeserCommande : public CommandeRobot {
	private:
		static PeserCommande basic;
	public:
		PeserCommande(string nom);
		void load(Invocateur * invocateur);
		void execute();
		virtual Commande * nouveau();
};

#endif
