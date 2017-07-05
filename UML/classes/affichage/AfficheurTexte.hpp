/*!
 * \file AfficheurTexte.hpp
 *
 * \brief Definition de la classe AfficheurTexte.
 */
 
#ifndef _AFFICHEURTEXTE_HPP_
#define _AFFICHEURTEXTE_HPP_

#include <iostream>
#include "Afficheur.hpp"
#include "../Robot.hpp"

using namespace std;

class AfficheurTexte final : public Afficheur{
	private:
		Robot* robot; /*!< Robot observé */
	public:
		AfficheurTexte();
		~AfficheurTexte();
		void afficher();
		void setRobot(Robot* robot);
};

#endif

