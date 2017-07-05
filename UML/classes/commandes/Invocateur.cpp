#include "Invocateur.hpp"
#include "AvancerCommande.hpp"
#include "TournerCommande.hpp"
#include "PoserCommande.hpp"
#include "PeserCommande.hpp"
#include "SaisirCommande.hpp"
#include "RencontrerPlotCommande.hpp"
#include "EvaluerPlotCommande.hpp"
#include "FigerCommande.hpp"
#include "RepartirCommande.hpp"
#include "UndoCommande.hpp"
#include "RedoCommande.hpp"
#include "MacroCommande.hpp"

Invocateur::Invocateur(Robot * robot) : robot(robot) {}

void Invocateur::afficherMenu() {
		cout << "#################### MENU ####################" << endl;
		cout << "Liste des actions possibles ..." << endl;
		cout << " - AVANCER <X> <Y>" << endl;
		cout << " - TOURNER <DIRECTION>" << endl;
		cout << " - POSER" << endl;
		cout << " - PESER" << endl;
		cout << " - SAISIR <POIDS_OBJET>" << endl;
		cout << " - RENCONTRERPLOT <HAUTEUR_PLOT>" << endl;
		cout << " - EVALUERPLOT" << endl;
		cout << " - FIGER" << endl;
		cout << " - REPARTIR" << endl;
		cout << " - UNDO" << endl;
		cout << " - REDO" << endl;
		cout << " - DEFMACRO <NOMMACRO> <COMMANDE>* FINMACRO" << endl;
		cout << " - APPELER <NOMMACRO>" << endl;
		cout << " - [^C] - quitter" << endl;
}

void Invocateur::lireCommandes() {
	afficherMenu();
	while(lireUneCommande());
}
int Invocateur::lireUneCommande() {
	Commande * pcmd = nextCommande();
	if(pcmd!=NULL) {
		try {
			pcmd->execute();
			Commande::getHistorique()->push(pcmd);
			while(pcmd->isReversible() && !Commande::getFuturique()->empty()) Commande::getFuturique()->pop();
		} catch(Etat::Etat_action_non_autorisee) {
			cout << "Ordre non exécutable du à l'état !!!\n";
		}
		return 1;
	} else {
		afficherMenu();
		return 1;
	}
}

Commande * Invocateur::nextCommande() {
	string cmd;
	cout << " > ";
	cin >> cmd;
	Commande * pcmd = Commande::getNouvelleCommande(cmd);
	if(pcmd!=NULL) {
		pcmd->load(this);
	}
	return pcmd;
}

int Invocateur::nextInt() {
	int x;
	cin >> x;
	return x;
}

string Invocateur::nextString() {
	string s;
	cin >> s;
	return s;
}

Plot* Invocateur::nextPlot() {
	Plot * plot = new Plot();
	cin >> (*plot);
	return plot;
}

Objet* Invocateur::nextObjet() {
	Objet * objet = new Objet();
	cin >> (*objet);
	return objet;
}

Robot* Invocateur::currentRobot() {
	return this->robot;
}
