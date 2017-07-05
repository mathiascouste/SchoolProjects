/*!
 * \file Afficheur.hpp
 *
 * \brief Definition de la classe Afficheur.
 */

#ifndef _AFFICHEUR_HPP_
#define _AFFICHEUR_HPP_

#include <iostream>

using namespace std;

class Afficheur {
	protected:
		Afficheur();
	public:		
		virtual void afficher();		
		class Afficheur_non_defini{};
};

#endif
