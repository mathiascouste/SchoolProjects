/*!
 * \file EnCharge.cpp
 *
 * \brief Implémentation de la classe EnCharge.
 */

#include "EnCharge.hpp"
#include "EnChargeFacePlot.hpp"

EnCharge* EnCharge::m_instance;

/*!
 * \brief Retourne l'instance AVide
 * Si l'instance n'existe pas, crée un nouvel objet AVide. Sinon renvoie
 * le pointeur sur l'instance.
 * @return un pointeur sur l'instance.
 */
EnCharge* EnCharge::getInstance() {
	if(!m_instance){
		m_instance = new EnCharge();
	}
	return m_instance;
}


/*!
 * \brief Constructeur par défaut.
 * 
 * Instancie un état EnCharge de nom "EnCharge".
 */
EnCharge::EnCharge() {
	this->nom = "EnCharge";
}

/*!
 * \brief Fait rencontrer un plot au robot.
 * 
 * Le robot change d'état : renvoie le nouvel état EnChargeFacePlot.
 */
Etat* EnCharge::rencontrerPlot() {
	return EnChargeFacePlot::getInstance();
}

/*!
 * \brief Fait avancer le robot.
 * 
 * Le robot ne change pas d'état : retourne un pointeur sur l'état actuel.
 * @return un pointeur sur l'état actuel
 */
Etat* EnCharge::avancer() {
	return this;
}

/*!
 * \brief Fait tourner le robot.
 * 
 * Le robot ne change pas d'état : retourne un pointeur sur l'état actuel.
 * @return un pointeur sur l'état actuel
 */
Etat* EnCharge::tourner() {
	return this;
}

/*!
 * \brief Le robot pèse l'objet qu'il tient.
 * 
 * Le robot ne change pas d'état : retourne un pointeur sur l'état actuel.
 * @return un pointeur sur l'état actuel
 */
Etat* EnCharge::peser() {
	return this;
}
