#ifndef _AVANCERCOMMANDE_HPP_
#define _AVANCERCOMMANDE_HPP_

#include "CommandeRobot.hpp"
#include "../Robot.hpp"

class AvancerCommande : public CommandeRobot {
	private:
		static AvancerCommande basic;
		int nextX, nextY, lastX, lastY;
	public:
		AvancerCommande(string nom);
		void load(Invocateur * invocateur);
		void execute();
		void desexecute();
		virtual Commande * nouveau();
};

#endif
