/*!
 * \file VueRobot.hpp
 *
 * \brief Definition de la classe VueRobot.
 */
 
#ifndef _VUEROBOT_HPP
#define _VUEROBOT_HPP

#include <iostream>
#include <set>
#include "Afficheur.hpp"

using namespace std;

class Afficheur;

class VueRobot {
	protected:
		VueRobot() {};
	private:
		set<Afficheur*> afficheurs;
	public:
		void attacherAfficheur(Afficheur* aff);
		void detacherAfficheur(Afficheur* aff);
		void notifier();
};

#endif

