/*!
 * \file Fige.cpp
 *
 * \brief Implémentation de la classe Fige.
 */

#include "Fige.hpp"

Fige* Fige::m_instance;

/*!
 * \brief Constructeur.
 * Instancie un état Fige en gardant en mémoire l'ancien état du robot.
 * @param etat un pointeur sur l'état actuel du robot
 */
Fige::Fige() {
	this->etat = NULL;
	this->nom = "Fige";
}

/*!
 * \brief Défige le robot.
 * 
 * Le robot repart dans l'état dans lequel il se trouvait avant d'être figé.
 * @return un pointeur sur le nouvel état.
 */
Etat* Fige::repartir(void * ptr) {
	return this->getEtatPtr(ptr);
}


Fige* Fige::getInstance() {
	if(m_instance == NULL) {
		m_instance = new Fige();
	}
	return m_instance;
}

void Fige::associerPtrEtat(void * ptr, EnRoute * etat) {
	//map.at((int)ptr) = etat;
	mapy.insert(make_pair(ptr,etat));
}
EnRoute * Fige::getEtatPtr(void * ptr) {
	return mapy.at(ptr);
}
