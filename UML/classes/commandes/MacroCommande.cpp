#include "MacroCommande.hpp"

MacroCommande MacroCommande::basic("DEFMACRO");
FinMacro FinMacro::basic("FINMACRO");
AppelMacro AppelMacro::basic("APPELER");

map<string, MacroCommande*> MacroCommande::macroMap;

MacroCommande::MacroCommande(string nom) : Commande(nom, false) {
}

void MacroCommande::load(Invocateur * invocateur) {
	string nomMacro = invocateur->nextString();
	Commande * pcmd;
	bool continuer = true;
	while(continuer) {
		pcmd = invocateur->nextCommande();
		if(dynamic_cast<FinMacro*>(pcmd)==NULL) {
			cmdList.push_back(pcmd);
		} else {
			continuer = false;
		}
	}
	macroMap.insert(pair<string, MacroCommande*>(nomMacro, this));
}

void MacroCommande::special_execute() {
	for(unsigned int i = 0 ; i < cmdList.size(); i++) {
		cmdList[i]->execute();
	}
}

void MacroCommande::desexecute() {
	for(int i = (int)cmdList.size()-1 ; i >= 0; i--) {
		if(cmdList[i]->isReversible()) {
			cmdList[i]->desexecute();
		}
	}
}

Commande* MacroCommande::nouveau() {
	return new MacroCommande(_nom);
}

map<string, MacroCommande*>* MacroCommande::getMacroMap() {
	return &macroMap;
}

FinMacro::FinMacro(string nom) : Commande(nom, false) {
}

Commande * FinMacro::nouveau() {
	return new FinMacro(_nom);
}

AppelMacro::AppelMacro(string nom) : Commande(nom, true) {}

void AppelMacro::load(Invocateur * invocateur) {
	this->nom = invocateur->nextString();
}

void AppelMacro::execute() {
	MacroCommande * pmcmd = MacroCommande::getMacroMap()->at(this->nom);
	pmcmd->special_execute();
}

void AppelMacro::desexecute() {
	MacroCommande * pmcmd = MacroCommande::getMacroMap()->at(this->nom);
	pmcmd->desexecute();
}

Commande * AppelMacro::nouveau() {
	return new AppelMacro(_nom);
}
