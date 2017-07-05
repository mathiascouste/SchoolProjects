#ifndef _RENCONTRERPLOTCOMMANDE_HPP_
#define _RENCONTRERPLOTCOMMANDE_HPP_

#include "CommandeRobot.hpp"
#include "../Robot.hpp"

#include <iostream>

using namespace std;

class RencontrerPlotCommande : public CommandeRobot {
	private:
		static RencontrerPlotCommande basic;
		Plot * nextPlot;
	public:
		RencontrerPlotCommande(string nom);
		void load(Invocateur * invocateur);
		void execute();
		void desexecute();
		virtual Commande * nouveau();
};

#endif
