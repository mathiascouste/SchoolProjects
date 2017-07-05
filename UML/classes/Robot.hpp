/*!
 * \file Robot.hpp
 *
 * \brief Definition de la classe Robot.
 */

#ifndef _ROBOT_HPP_
#define _ROBOT_HPP_

#include <iostream>

#include "Position.hpp"
#include "Plot.hpp" 
#include "Objet.hpp"
#include "etats/Etat.hpp"
#include "affichage/VueRobot.hpp"

using namespace std;

class Etat;

class Robot : public VueRobot {
	private:
		Position position; 	/*!< Coordonnées (x,y) */
		string direction; 	/*!< "N", "S", "E" ou "O" */
		string dernierOrdre;/*! Le dernier ordre donné au robot */
		Etat* etat; 		/*!< Etat dans lequel se trouve le robot*/
		Plot* plot; 		/*!< Le plot situé en face du robot*/
		Objet* objet; 		/*!< Objet tenu par le robot */
		void setPosition(int x, int y);
	public:
		Robot();
		string getDirection() const;
		Etat* getEtat() const;
		Plot* getPlot() const;
		Objet* getObjet() const;
		Position getPosition() const;
		string getDernierOrdre() const;		
		void avancer(int x, int y);
		void tourner(string direction);
		void saisir(Objet * objet);
		void poser();
		int peser();
		void rencontrerPlot(Plot * plot);
		int evaluerPlot();
		void figer();
		void repartir();
};

#endif
