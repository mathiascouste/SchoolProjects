#include "SaisirCommande.hpp"
#include "../Objet.hpp"

SaisirCommande SaisirCommande::basic("SAISIR");

SaisirCommande::SaisirCommande(string nom) : CommandeRobot(nom, true) {
}

void SaisirCommande::load(Invocateur * invocateur) {
	this->robot = invocateur->currentRobot();
	nextObjet = invocateur->nextObjet();
}

void SaisirCommande::execute() {
	this->robot->saisir(nextObjet);
}
void SaisirCommande::desexecute() {
	this->robot->poser();
}


Commande* SaisirCommande::nouveau() {
	return new SaisirCommande(_nom);
}
