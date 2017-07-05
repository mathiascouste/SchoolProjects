#include "Commande.hpp"

stack<Commande*> Commande::historique;
stack<Commande*> Commande::futurique;

/*map<string, Commande*>* Commande::getStrCmd() {
	return &strCmd;
}*/
map<string, Commande*>& Commande::strCmd() {
	static map<string, Commande*>* strCmd = new map<string, Commande*>;
	return *strCmd;
}
stack<Commande*>* Commande::getHistorique() {
	return &historique;
}
stack<Commande*>* Commande::getFuturique() {
	return &futurique;
}
		
Commande::Commande(string nom, bool reversible) : _nom(nom) {
	this->reversible = reversible;
	strCmd()[nom] = this;
}

bool Commande::isReversible() {
	return this->reversible;
}

void Commande::load(Invocateur * invocateur) {
}

void Commande::execute() {
}

void Commande::desexecute(){
}

Commande* Commande::getNouvelleCommande(string cmd) {
	Commande * tmp = strCmd()[cmd];
	if(tmp!=NULL) {
		return tmp->nouveau();
	} else {
			return NULL;
	}
}


Commande* Commande::nouveau() {
	return new Commande(_nom, reversible);
}
