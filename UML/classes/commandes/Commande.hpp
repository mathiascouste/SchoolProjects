#ifndef _COMMANDE_HPP_
#define _COMMANDE_HPP_

#include <string>
#include <map>
#include <stack>

#include "../Robot.hpp"
#include "Invocateur.hpp"

using namespace std;

class Invocateur;

class Commande {
	protected:
		static stack<Commande*> historique;
		static stack<Commande*> futurique;
		bool reversible;
		string _nom;
		Commande(string nom, bool reversible = true);
	public:
		static map<string,Commande*>& strCmd();
		static stack<Commande*>* getHistorique();
		static stack<Commande*>* getFuturique();
		virtual void load(Invocateur * invocateur);
		virtual void execute();
		virtual void desexecute();
		bool isReversible();
		static Commande* getNouvelleCommande(string cmd);
		virtual Commande * nouveau();
};

#endif
