#ifndef _UNDOCOMMANDE_HPP_
#define _UNDOCOMMANDE_HPP_

#include "Commande.hpp"
#include "../Robot.hpp"

class UndoCommande : public Commande {
	private:
		static UndoCommande basic;
	public:
		UndoCommande(string nom);
		void execute();
		virtual Commande * nouveau();
};

#endif
