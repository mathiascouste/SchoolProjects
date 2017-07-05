#ifndef _COMMANDEROBOT_HPP_
#define _COMMANDEROBOT_HPP_

#include "Commande.hpp"
#include "../Robot.hpp"

class CommandeRobot : public Commande {
	protected:
		Robot * robot;
		CommandeRobot(string nom, bool reversible = false);
		virtual Commande * nouveau();
};

#endif
