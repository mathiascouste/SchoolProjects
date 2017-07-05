#include "TournerCommande.hpp"

TournerCommande TournerCommande::basic("TOURNER");

TournerCommande::TournerCommande(string nom) : CommandeRobot(nom, true) {
}

void TournerCommande::load(Invocateur * invocateur) {
	this->robot = invocateur->currentRobot();
	nextDirection = invocateur->nextString();
}

void TournerCommande::execute() {
	lastDirection = robot->getDirection();
	lastPlot = robot->getPlot();
	this->robot->tourner(nextDirection);
}
void TournerCommande::desexecute() {
	this->robot->tourner(lastDirection);
	if(lastPlot) {
		robot->rencontrerPlot(lastPlot);
	}
}

Commande* TournerCommande::nouveau() {
	return new TournerCommande(_nom);
}
