/*!
 * \file EnChargeFacePlot.hpp
 *
 * \brief Définition de la classe EnChargeFacePlot.
 */

#ifndef _ENCHARGEFACEPLOT_HPP_
#define _ENCHARGEFACEPLOT_HPP_

#include <iostream>

#include "EnRoute.hpp"

/*!
 * \class EnChargeFacePlot Singleton de l'état EnChargeFacePlot.
 */
class EnChargeFacePlot : public EnRoute {
	private:
		static EnChargeFacePlot* m_instance; /*!< Instance unique de l'état EnChargeFacePlot */
		EnChargeFacePlot& operator= (const EnChargeFacePlot& e){throw Etat::Copie_interdite_singleton();}
		EnChargeFacePlot (const EnChargeFacePlot& e){throw Etat::Copie_interdite_singleton();}
	protected:
		EnChargeFacePlot();
	public:
		virtual Etat* tourner();
		virtual Etat* peser();
		virtual Etat* poser();
		static EnChargeFacePlot* getInstance();
};

#endif
