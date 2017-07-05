#include "RepartirCommande.hpp"

RepartirCommande RepartirCommande::basic("REPARTIR");

RepartirCommande::RepartirCommande(string nom) : CommandeRobot(nom, true) {
}

void RepartirCommande::load(Invocateur * invocateur) {
	this->robot = invocateur->currentRobot();
}

void RepartirCommande::execute() {
	this->robot->repartir();
}
void RepartirCommande::desexecute() {
	this->robot->figer();
}

Commande* RepartirCommande::nouveau() {
	return new RepartirCommande(_nom);
}
