/*!
 * \file AVideFacePlot.hpp
 *
 * \brief Définition de la classe AVideFacePlot.
 */

#ifndef _AVIDEFACEPLOT_HPP_
#define _AVIDEFACEPLOT_HPP_

#include "EnRoute.hpp"

/*!
 * \class AVideFacePlot Singleton de l'état AVideFacePlot.
 */
class AVideFacePlot : public EnRoute {
	private:
		static AVideFacePlot* m_instance; /*!< Instance unique de l'état AVideFacePlot.*/
		AVideFacePlot& operator= (const AVideFacePlot& e){throw Etat::Copie_interdite_singleton();}
		AVideFacePlot (const AVideFacePlot& e){throw Etat::Copie_interdite_singleton();}
	protected:
		AVideFacePlot();
	public:
		virtual Etat* tourner();
		virtual Etat* evaluerPlot();
		virtual Etat* saisir();
		static AVideFacePlot* getInstance();
};

#endif