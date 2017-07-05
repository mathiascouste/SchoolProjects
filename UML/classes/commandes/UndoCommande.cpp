#include "UndoCommande.hpp"

UndoCommande UndoCommande::basic("UNDO");

UndoCommande::UndoCommande(string nom) : Commande(nom, false) {
}

void UndoCommande::execute() {
	if(!historique.empty()) {
		Commande * pcmd = historique.top();
		historique.pop();
		pcmd->desexecute();
		futurique.push(pcmd);
	} else {
		cout << "Nothing to Undo !!!" << endl;
	}
}

Commande* UndoCommande::nouveau() {
	return new UndoCommande(_nom);
}
