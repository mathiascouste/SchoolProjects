#include "RedoCommande.hpp"

RedoCommande RedoCommande::basic("REDO");

RedoCommande::RedoCommande(string nom) : Commande(nom, false) {
}

void RedoCommande::execute() {
	if(!Commande::getFuturique()->empty()) {
		Commande * pcmd = Commande::getFuturique()->top();
		Commande::getFuturique()->pop();
		pcmd->execute();
		Commande::getHistorique()->push(pcmd);
	} else {
		cout << "Nothing to redo !!!" << endl;
	}
}

Commande* RedoCommande::nouveau() {
	return new RedoCommande(_nom);
}
