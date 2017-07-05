#include "CommandeRobot.hpp"

CommandeRobot::CommandeRobot(string nom,bool reversible) : Commande(nom, reversible) {
}

Commande* CommandeRobot::nouveau() {
	return new CommandeRobot(_nom);
}
