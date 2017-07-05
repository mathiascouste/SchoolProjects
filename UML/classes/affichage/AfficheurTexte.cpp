/*!
 * \file AfficheurTexte.cpp
 *
 * \brief Implémentation de la classe AfficheurTexte.
 */

#include "AfficheurTexte.hpp"

/*!
 * \brief Constructeur par défaut.
 * 
 * Instancie un afficheur de type texte. 
 */
AfficheurTexte::AfficheurTexte() {}

/*!
 * \brief Destructeur.
 */
AfficheurTexte::~AfficheurTexte() {
	this->robot = NULL;
}

/*!
 * \brief Affiche les informations du robot
 * 
 * Affiche en mode textuel les informations du robot observé.
 */
void AfficheurTexte::afficher() {
	cout << "###################################################" << endl;
	//cout << "Ordre : " << this->robot->getDernierOrdre() << endl;
	cout << "## Etat (" << this->robot->getEtat()->getNom() << ") ##" << endl;
	cout << "Position : " << this->robot->getPosition() << endl;
	cout << "Direction : " << this->robot->getDirection() << endl;
	if(this->robot->getObjet()) {
		cout << "Poids objet : " << this->robot->getObjet()->getPoids() << endl;
	} else {
		cout << "Pas d'objet" << endl;
	}
	if(this->robot->getPlot()) {
		cout << "Hauteur plot : " << this->robot->getPlot()->getHauteur() << endl;
	} else {
		cout << "Pas de plot" << endl;
	}
}

/*!
 * \brief Change le robot observé par cet afficheur.
 * 
 * @param robot le nouveau robot observé par l'afficheur.
 */
void AfficheurTexte::setRobot(Robot * robot){
	this->robot = robot;
}
