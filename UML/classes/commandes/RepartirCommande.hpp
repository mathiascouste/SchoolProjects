#ifndef _REPARTIRCOMMANDE_HPP_
#define _REPARTIRCOMMANDE_HPP_

#include "CommandeRobot.hpp"
#include "../Robot.hpp"

class RepartirCommande : public CommandeRobot {
	private:
		static RepartirCommande basic;
	public:
		RepartirCommande(string nom);
		void load(Invocateur * invocateur);
		void execute();
		void desexecute();
		virtual Commande * nouveau();
};

#endif
