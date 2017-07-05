/*!
 * \file Fige.hpp
 *
 * \brief Définition de la classe Fige.
 */

#ifndef _FIGE_HPP_
#define _FIGE_HPP_

#include "Etat.hpp"
#include "EnRoute.hpp"
#include "../Robot.hpp"
#include <map>

using namespace std;

/*!
 * \class Fige Singleton de l'état Fige.
 */
class Fige : public Etat {
	private:
		static Fige* m_instance; /*!< Instance unique de l'état EnCharge */
		Etat* etat; /*!< Etat du robot avant d'être figé. */
		map<void*, EnRoute*> mapy;
	protected:
		Fige();
	public:
		virtual Etat* repartir(void * ptr);
		static Fige* getInstance();
		void associerPtrEtat(void * ptr, EnRoute * etat);
		EnRoute * getEtatPtr(void * ptr);
};

#endif
