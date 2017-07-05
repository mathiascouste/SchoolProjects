#include "PoserCommande.hpp"

PoserCommande PoserCommande::basic("POSER");

PoserCommande::PoserCommande(string nom) : CommandeRobot(nom, true) {
}
void PoserCommande::load(Invocateur * invocateur) {
	this->robot = invocateur->currentRobot();
}

void PoserCommande::execute() {
	lastObjet = robot->getObjet();
	this->robot->poser();
}
void PoserCommande::desexecute() {
	this->robot->saisir(lastObjet);
}

Commande* PoserCommande::nouveau() {
	return new PoserCommande(_nom);
}
