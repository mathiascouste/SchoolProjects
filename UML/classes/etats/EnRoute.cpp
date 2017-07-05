/*!
 * \file EnRoute.cpp
 *
 * \brief Implémentation de la classe EnRoute.
 */

#include "EnRoute.hpp"
#include "Fige.hpp"

/*!
 * \brief Constructeur
 * Constructeur d'un état EnRoute de nom "EnRoute". 
 * Non utilisé car classe abstraite non instanciable.
 */
EnRoute::EnRoute() {
	this->nom = "EnRoute";
}

/*!
 * \brief Fige le robot
 * 
 * Le robot change d'état : renvoie un pointeur sur le nouvel état Fige.
 * @return un pointeur sur un état Fige.
 */
Etat* EnRoute::figer(void * ptr) {
	Fige * fige = Fige::getInstance();
	fige->associerPtrEtat(ptr, this);
	return fige;
}
