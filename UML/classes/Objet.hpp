/*!
 * \file Objet.hpp
 *
 * \brief Définition de la classe Objet.
 */

#ifndef _OBJET_HPP_
#define _OBJET_HPP_

#include <iostream>

using namespace std;

class Objet {
	private:
		int poids; /*!< Un entier strictement positif */
	public:
		Objet(int poids = 0);
		int getPoids() const;
		void setPoids(int poids);
	friend istream& operator>>( istream &flux, Objet & objet );
};

#endif
