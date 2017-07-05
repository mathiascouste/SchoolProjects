#include "RencontrerPlotCommande.hpp"
#include "../Plot.hpp"

RencontrerPlotCommande RencontrerPlotCommande::basic("RENCONTRERPLOT");

RencontrerPlotCommande::RencontrerPlotCommande(string nom) : CommandeRobot(nom, true) {
}
void RencontrerPlotCommande::load(Invocateur * invocateur) {
	this->robot = invocateur->currentRobot();
	nextPlot = invocateur->nextPlot();
}

void RencontrerPlotCommande::execute() {
	this->robot->rencontrerPlot(nextPlot);
}

void RencontrerPlotCommande::desexecute() {
	this->robot->tourner(this->robot->getDirection());
}

Commande* RencontrerPlotCommande::nouveau() {
	return new RencontrerPlotCommande(_nom);
}
