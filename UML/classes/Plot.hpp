/*!
 * \file Plot.hpp
 *
 * \brief Définition de la classe Plot.
 */

#ifndef _PLOT_HPP_
#define _PLOT_HPP_

#include <iostream>

using namespace std;

class Plot {
	private:
		int hauteur; /*!< Entier strictement positif */
	public:
		Plot(int hauteur = 0);
		int getHauteur() const;
		void setHauteur(int hauteur);
	friend istream& operator>>( istream &flux, Plot & plot );
};

#endif
