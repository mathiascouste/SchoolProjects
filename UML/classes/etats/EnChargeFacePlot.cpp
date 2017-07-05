/*!
 * \file EnChargeFacePlot.cpp
 *
 * \brief Implémentation de la classe EnChargeFacePlot.
 */


#include "EnChargeFacePlot.hpp"
#include "EnCharge.hpp"
#include "AVideFacePlot.hpp"

EnChargeFacePlot* EnChargeFacePlot::m_instance;

/*!
 * \brief Retourne l'instance AVide
 * Si l'instance n'existe pas, crée un nouvel objet AVide. Sinon renvoie
 * le pointeur sur l'instance.
 * @return un pointeur sur l'instance.
 */
EnChargeFacePlot* EnChargeFacePlot::getInstance() {
	if(!m_instance) {
		m_instance = new EnChargeFacePlot();
	}
	return m_instance;
}

/*!
 * \brief Constructeur par défaut.
 * 
 * Instancie un état EnChargeFacePlot de nom "EnChargeFacePlot".
 */
EnChargeFacePlot::EnChargeFacePlot() {
	this->nom = "EnChargeFacePlot";
}

/*!
 * \brief Fait tourner le robot.
 * 
 * Renvoie le nouvel état dans lequel se trouve le robot après l'action
 * tourner : EnCharge.
 * @return un pointeur sur un état EnCharge.
 */
Etat* EnChargeFacePlot::tourner() {
	return EnCharge::getInstance();
}

/*!
 * \brief Le robot pèse l'objet qu'il tient.
 * 
 * Le robot ne change pas d'état dans ce cas. Renvoie l'état courant.
 * @return un pointeur sur l'état actuel.
 */
Etat* EnChargeFacePlot::peser() {
	return this;
}

/*!
 * \brief Le robot pose l'objet qu'il tient.
 * 
 * Le robot change d'état : renvoie le nouvel état AVideFacePlot.
 * @return un pointeur sur un état AVideFacePlot.
 */
Etat* EnChargeFacePlot::poser() {
	return AVideFacePlot::getInstance();
}
