/*!
 * \file Etat.cpp
 *
 * \brief Implémentation de la classe Etat.
 */

#include "Etat.hpp"
#include "AVide.hpp"

#include <iostream>

/*!
 * \brief Constructeur (deprecated)
 * Constructeur d'un Etat de nom "Etat". Non utilisé car classe 
 * abstraite non instanciable.
 */
Etat::Etat() {
	this->nom = "Etat";
}

/*!
 * Renvoie le nom de l'état
 * @return une string
 */
string Etat::getNom() {
	return this->nom;
}

/*!
 * Renvoie une exception : méthode abstraite
 */
Etat* Etat::avancer() {
	throw Etat::Etat_action_non_autorisee();
}

/*!
 * Renvoie une exception : méthode abstraite
 */
Etat* Etat::tourner() {
	throw Etat::Etat_action_non_autorisee();
}
/*!
 * Renvoie une exception : méthode abstraite
 */
Etat* Etat::saisir() {
	throw Etat::Etat_action_non_autorisee();
}

/*!
 * Renvoie une exception : méthode abstraite
 */
Etat* Etat::poser() {
	throw Etat::Etat_action_non_autorisee();
}

/*!
 * Renvoie une exception : méthode abstraite
 */
Etat* Etat::peser() {
	throw Etat::Etat_action_non_autorisee();
}

/*!
 * Renvoie une exception : méthode abstraite
 */
Etat* Etat::rencontrerPlot() {
	throw Etat::Etat_action_non_autorisee();
}

/*!
 * Renvoie une exception : méthode abstraite
 */
Etat* Etat::evaluerPlot() {
	throw Etat::Etat_action_non_autorisee();
}

/*!
 * Renvoie une exception : méthode abstraite
 */
Etat* Etat::figer(void * ptr) {
	throw Etat::Etat_action_non_autorisee();
}

/*!
 * Renvoie une exception : méthode abstraite
 */
Etat* Etat::repartir(void * ptr) {
	throw Etat::Etat_action_non_autorisee();
}

Etat* Etat::getEtatInitial(){
	return AVide::getInstance();
}
