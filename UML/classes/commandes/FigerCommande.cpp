#include "FigerCommande.hpp"

FigerCommande FigerCommande::basic("FIGER");

FigerCommande::FigerCommande(string nom) : CommandeRobot(nom,true) {
}

void FigerCommande::load(Invocateur * invocateur) {
	this->robot = invocateur->currentRobot();
}

void FigerCommande::execute() {
	lastEtat = robot->getEtat();
	this->robot->figer();
}
void FigerCommande::desexecute() {
	this->robot->repartir();
}

Commande* FigerCommande::nouveau() {
	return new FigerCommande(_nom);
}
