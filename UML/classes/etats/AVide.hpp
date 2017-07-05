/*!
 * \file AVide.hpp
 *
 * \brief Définition de la classe AVide.
 */

#ifndef _AVIDE_HPP_
#define _AVIDE_HPP_

#include <iostream>

#include "EnRoute.hpp"

/*!
 * \class AVide Singleton de l'état AVide.
 */
class AVide : public EnRoute {
	private:
		static AVide* m_instance; /*!< Instance unique de l'état AVide */
		AVide& operator= (const AVide& etat){ throw Etat::Copie_interdite_singleton();}
		AVide (const AVide& e){throw Etat::Copie_interdite_singleton();}
	protected:
		AVide();
	public:
		virtual Etat* tourner();
		virtual Etat* avancer();
		virtual Etat* rencontrerPlot();
		static AVide* getInstance();
};

#endif
