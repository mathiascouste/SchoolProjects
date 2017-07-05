#include "AvancerCommande.hpp"

AvancerCommande AvancerCommande::basic("AVANCER");

AvancerCommande::AvancerCommande(string nom) : CommandeRobot(nom, true) {
}
void AvancerCommande::load(Invocateur * invocateur) {
	this->robot = invocateur->currentRobot();
	nextX = invocateur->nextInt();
	nextY = invocateur->nextInt();
}

void AvancerCommande::execute() {
	lastX = robot->getPosition().getX();
	lastY = robot->getPosition().getY();
	this->robot->avancer(nextX,nextY);
}
void AvancerCommande::desexecute() {
	this->robot->avancer(lastX,lastY);
}

Commande* AvancerCommande::nouveau() {
	return new AvancerCommande(_nom);
}
