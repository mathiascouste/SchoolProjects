/*!
 * \file Etat.hpp
 *
 * \brief Définition de la classe Etat.
 */

#ifndef _ETAT_HPP_
#define _ETAT_HPP_

#include <iostream>

#include "../Plot.hpp"
#include "../Objet.hpp"

#include "../Robot.hpp"

using namespace std;

class Etat {
	protected:
		string nom; /*!< Nom de l'état pour l'affichage de la trace*/
		Etat();
	public:
		virtual ~Etat(){};
		string getNom();
		virtual Etat* avancer();
		virtual Etat* tourner();
		virtual Etat* saisir();
		virtual Etat* poser();
		virtual Etat* peser();
		virtual Etat* rencontrerPlot();
		virtual Etat* evaluerPlot();
		virtual Etat* figer(void * ptr);
		virtual Etat* repartir(void * ptr);
		static Etat* getEtatInitial();
		
		class Etat_action_non_autorisee{
			friend ostream& operator<<(ostream &os, Etat_action_non_autorisee const& e){
				os << "###################################################";
				os << endl;
				os << "Erreur : Action interdite dans cet état !";
				os << endl;
				return os;
			}
		};
		class Copie_interdite_singleton{
			friend ostream& operator<<(ostream &os, Copie_interdite_singleton const& e){
				os << "###################################################";
				os << endl;
				os << "Erreur : cet état est un singleton, copie impossible.";
				os << endl;
				return os;
			}
		};
};

#endif
