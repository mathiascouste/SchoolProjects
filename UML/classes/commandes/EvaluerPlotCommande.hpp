#ifndef _EVALUERPLOTCOMMANDE_HPP_
#define _EVALUERPLOTCOMMANDE_HPP_

#include "CommandeRobot.hpp"
#include "../Robot.hpp"

class EvaluerPlotCommande : public CommandeRobot {
	private:
		static EvaluerPlotCommande basic;
	public:
		EvaluerPlotCommande(string nom);
		void load(Invocateur * invocateur);
		void execute();
		virtual Commande * nouveau();
};

#endif
