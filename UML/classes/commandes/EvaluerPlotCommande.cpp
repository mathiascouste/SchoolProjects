#include "EvaluerPlotCommande.hpp"

EvaluerPlotCommande EvaluerPlotCommande::basic("EVALUERPLOT");

EvaluerPlotCommande::EvaluerPlotCommande(string nom) : CommandeRobot(nom, false) {
}

void EvaluerPlotCommande::load(Invocateur * invocateur) {
	this->robot = invocateur->currentRobot();
}

void EvaluerPlotCommande::execute() {
	this->robot->evaluerPlot();
}

Commande* EvaluerPlotCommande::nouveau() {
	return new EvaluerPlotCommande(_nom);
}
