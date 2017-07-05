/*!
 * \file Plot.cpp
 *
 * \brief Implémentation de la classe Plot.
 */

#include "Plot.hpp"

/*!
 * \brief Constructeur.
 * 
 * Instancie un plot de hauteur passée en paramètre.
 * @param hauteur un entier strictement positif
 */
Plot::Plot(int hauteur): hauteur(hauteur>0?hauteur:0) {}

/*!
 * \brief Renvoie la hauteur du plot.
 * @return un entier strictement positif
 */
int Plot::getHauteur() const{
	return this->hauteur;
}

/*!
 * \brief Modifie la hauteur du plot.
 * Remplace la hauteur du plot par la valeur passée en paramètre.
 * @param hauteur un entier strictement positif.
 */
void Plot::setHauteur(int hauteur) {
	this->hauteur = hauteur;
}

istream& operator>>( istream &flux, Plot & plot ) {
    flux >> plot.hauteur;
    return flux;
}
