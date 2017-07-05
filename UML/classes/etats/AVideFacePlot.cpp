/*!
 * \file AVideFacePlot.cpp
 *
 * \brief Implémentation de la classe AVideFacePlot.
 */

#include "AVideFacePlot.hpp"
#include "AVide.hpp"
#include "EnChargeFacePlot.hpp"

AVideFacePlot* AVideFacePlot::m_instance;

/*!
 * \brief Retourne l'instance AVideFacePlot
 * Si l'instance n'existe pas, crée un nouvel objet AVideFacePlot. 
 * Sinon renvoie le pointeur sur l'instance.
 * @return un pointeur sur l'instance.
 */
AVideFacePlot* AVideFacePlot::getInstance() {
	if(!m_instance){
		m_instance = new AVideFacePlot();
	}
	return m_instance;
}

/*!
 * \brief Constructeur par défaut.
 * 
 * Instancie un état AVideFacePlot de nom "AVideFacePlot".
 */
AVideFacePlot::AVideFacePlot() {
	this->nom = "AVideFacePlot";
}

/*!
 * \brief Fait tourner le robot.
 * 
 * Le robot change d'état : retourne un pointeur sur le nouvel état AVide.
 * @return un pointeur sur l'instance de l'état AVide
 */
Etat* AVideFacePlot::tourner() {
	return AVide::getInstance();
}

/*!
 * \brief Le robot calcule la hauteur du plot.
 * 
 * Le robot ne change pas d'état : retourne un pointeur sur l'état actuel.
 * @return un pointeur sur l'état actuel
 * 
 */
Etat* AVideFacePlot::evaluerPlot() {
	return this;
}

/*!
 * \brief Fait saisir un objet au robot.
 * 
 * Le robot change d'état : retourne un pointeur sur le nouvel état EnChargeFacePlot.
 * @return un pointeur sur un état EnChargeFacePlot.
 */
Etat* AVideFacePlot::saisir() {
	return EnChargeFacePlot::getInstance();
}
