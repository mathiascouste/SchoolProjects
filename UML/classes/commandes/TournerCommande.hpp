#ifndef _TOURNERCOMMANDE_HPP_
#define _TOURNERCOMMANDE_HPP_

#include "CommandeRobot.hpp"
#include "../Robot.hpp"

class TournerCommande : public CommandeRobot {
	private:
		static TournerCommande basic;
		string lastDirection, nextDirection;
		Plot * lastPlot;
	public:
		TournerCommande(string nom);
		void load(Invocateur * invocateur);
		void execute();
		void desexecute();
		virtual Commande * nouveau();
};

#endif
