/*!
 * \file AVide.cpp
 *
 * \brief Implémentation de la classe AVide.
 */

#include "AVide.hpp"
#include "AVideFacePlot.hpp"

AVide* AVide::m_instance;

/*!
 * \brief Retourne l'instance AVide
 * Si l'instance n'existe pas, crée un nouvel objet AVide. Sinon renvoie
 * le pointeur sur l'instance.
 * @return un pointeur sur l'instance.
 */
AVide* AVide::getInstance() {
	if(!m_instance) {
		m_instance = new AVide();
	}
	return m_instance;
}

/*!
 * \brief Constructeur par défaut.
 * 
 * Instancie un état AVide de nom "AVide".
 */
AVide::AVide() {
	this->nom = "AVide";
}

/*!
 * \brief Fait tourner le robot.
 * 
 * Le robot ne change pas d'état : retourne un pointeur sur l'état actuel.
 * @return un pointeur sur l'état actuel
 */
Etat* AVide::tourner() {
	return this;
}

/*!
 * \brief Fait avancer le robot.
 * 
 * Le robot ne change pas d'état : retourne un pointeur sur l'état actuel.
 * @return un pointeur sur l'état actuel
 */
Etat* AVide::avancer() {
	return this;
}

/*!
 * \brief Fait rencontrer un plot au robot.
 * 
 * Le robot change d'état : renvoie le nouvel état AVideFacePlot.
 * @return un pointeur sur état
 */
Etat* AVide::rencontrerPlot() {
	return AVideFacePlot::getInstance();
}
