/*!
 * \file EnRoute.hpp
 *
 * \brief Définition de la classe EnRoute.
 */

#ifndef _ENROUTE_HPP_
#define _ENROUTE_HPP_

#include "Etat.hpp"

/*!
 * \class EnRoute Etat EnRoute.
 */
class EnRoute : public Etat {
	protected:
		EnRoute();
	public:
		virtual Etat* figer(void * ptr);
};

#endif
