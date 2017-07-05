/*!
 * \file EnCharge.hpp
 *
 * \brief Définition de la classe EnCharge.
 */

#ifndef _ENCHARGE_HPP_
#define _ENCHARGE_HPP_

#include <iostream>

#include "EnRoute.hpp"

/*!
 * \class EnCharge Singleton de l'état EnCharge.
 */
class EnCharge : public EnRoute {
	private:
		static EnCharge* m_instance; /*!< Instance unique de l'état EnCharge */
		EnCharge& operator= (const EnCharge& e){throw Etat::Copie_interdite_singleton();}
		EnCharge (const EnCharge& e){throw Etat::Copie_interdite_singleton();}
	protected:
		EnCharge();
	public:
		virtual Etat* rencontrerPlot();
		virtual Etat* avancer();
		virtual Etat* tourner();
		virtual Etat* peser();
		static EnCharge* getInstance();
};

#endif
