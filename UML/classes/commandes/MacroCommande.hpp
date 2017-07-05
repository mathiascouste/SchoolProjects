#ifndef _MACROCOMMANDE_HPP_
#define _MACROCOMMANDE_HPP_

#include <vector>
#include <map>

#include "Commande.hpp"
#include "Invocateur.hpp"

using namespace std;

class FinMacro;
class AppelMacro;

class MacroCommande : public Commande {
	private:
		static MacroCommande basic;
		vector<Commande*> cmdList;
		static map<string, MacroCommande*> macroMap;
	public:
		MacroCommande(string nom);
		static map<string, MacroCommande*>* getMacroMap();
		void load(Invocateur * invocateur);
		void special_execute();
		void desexecute();
		virtual Commande * nouveau();
};

class FinMacro : public Commande {
	private:
		static FinMacro basic;
	public:
		FinMacro(string nom);
		virtual Commande * nouveau();
};

class AppelMacro : public Commande {
	private:
		static AppelMacro basic;
		string nom;
	public:
		AppelMacro(string nom);
		void load(Invocateur * invocateur);
		void execute();
		void desexecute();
		virtual Commande * nouveau();
};

#endif
