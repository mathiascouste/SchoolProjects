/*!
 * \file Afficheur.cpp
 *
 * \brief Implémentation de la classe Afficheur.
 */

#include "Afficheur.hpp"

/*!
 * \brief Constructeur par défaut.
 */
Afficheur::Afficheur() {}

/*!
 * \brief Affiche les informations du robot.
 * 
 * Méthode virtuelle non définie ici. Lève une exception.
 */
void Afficheur::afficher() {
	throw Afficheur::Afficheur_non_defini();
}
