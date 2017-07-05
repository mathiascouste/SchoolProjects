#include "PeserCommande.hpp"

PeserCommande PeserCommande::basic("PESER");

PeserCommande::PeserCommande(string nom) : CommandeRobot(nom, false) {
}

void PeserCommande::load(Invocateur * invocateur) {
	this->robot = invocateur->currentRobot();
}

void PeserCommande::execute() {
	this->robot->peser();
}


Commande* PeserCommande::nouveau() {
	return new PeserCommande(_nom);
}
