/*!
 * \file Objet.cpp
 *
 * \brief Implémentation de la classe Objet.
 */

#include "Objet.hpp"

/*!
 * \brief Constructeur.
 * 
 * Instancie un objet de poids égale à la valeur passée en paramètre.
 * Si le poids donné n'est pas strictement positif, l'objet instancié
 * sera de poids nul.
 * @param poids un entier strictement positif.
 */
Objet::Objet(int poids):poids(poids>0?poids:0) {}

/*!
 * Renvoie le poids de l'objet.
 * @return un entier strictement positif ou nul
 */
int Objet::getPoids() const{
	return this->poids;
}

/*!
 * Modifie le poids de l'objet.
 * @param poids un entier strictement positif
 */
void Objet::setPoids(int poids) {
	if(poids > 0) {
		this->poids = poids;
	}
}

istream& operator>>( istream &flux, Objet & objet ) {
    flux >> objet.poids;
    return flux;
}
