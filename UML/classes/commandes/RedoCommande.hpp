#ifndef _REDOCOMMANDE_HPP_
#define _REDOCOMMANDE_HPP_

#include "Commande.hpp"
#include "../Robot.hpp"

class RedoCommande : public Commande {
	private:
		static RedoCommande basic;
	public:
		RedoCommande(string nom);
		void execute();
		virtual Commande * nouveau();
};

#endif
