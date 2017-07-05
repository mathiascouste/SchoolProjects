/*!
 * \file VueRobot.cpp
 *
 * \brief Implémentation de la classe VueRobot.
 */

#include "VueRobot.hpp"
#include "AfficheurTexte.hpp"
#include "../Robot.hpp"

/*!
 * \brief Ajoute un afficheur à la liste des afficheurs.
 * 
 * @param aff l'afficheur à ajouter à la liste
 */
void VueRobot::attacherAfficheur(Afficheur* aff) {
	this->afficheurs.insert(aff);
	AfficheurTexte* affT = dynamic_cast<AfficheurTexte*>(aff);
	if(affT != 0) {
		affT->setRobot((Robot*)(this));
	}
}	

/*!
 * \brief Retire un afficheur de la liste des afficheurs.
 * 
 * @param aff l'afficheur à retirer de la liste.
 */
void VueRobot::detacherAfficheur(Afficheur* aff) {
	this->afficheurs.erase(aff);
	AfficheurTexte* affT = dynamic_cast<AfficheurTexte*>(aff);
	if(affT != 0){
		affT->setRobot(nullptr);
	}
}

/*!
 * \brief Notifie tous les afficheurs d'une modification
 * Notifie tous les afficheurs de la liste que l'objet observé a été
 * modifié.
 */
void VueRobot::notifier() {
	for(Afficheur* a : afficheurs){
		a->afficher();
	}
}
