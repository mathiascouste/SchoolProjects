/*!
 * \file Robot.cpp
 *
 * \brief Implémentation de la classe Robot.
 */

#include <cstdio>
#include <string.h>

#include "Robot.hpp"

/*!
 * \brief Constructeur par défaut.
 * 
 * Instancie un robot positionné en (0,0), dirigé vers le Nord "N",
 * qui ne porte pas d'objet et n'est pas face à un plot.
 */
Robot::Robot() {
	this->direction = "N";
	this->dernierOrdre = "INIT";
	this->etat = Etat::getEtatInitial();
	this->plot = NULL;
	this->objet = NULL;
}

/*!
 * \brief Retourne dans quelle direction va le robot.
 * 
 * @return une string représentant la direction ("S","N","E" ou "O").
 */
string Robot::getDirection() const{
	return this->direction;
}

/*!
 * \brief Retourne l'état dans lequel se trouve le robot.
 * 
 * @return un pointeur sur l'état du robot.
 */
Etat* Robot::getEtat() const{
	return this->etat;
}

/*!
 * \brief Retourne le plot en face duquel se trouve le robot.
 * 
 * @return un pointeur sur le plot en face du robot.
 */
Plot* Robot::getPlot() const{
	return this->plot;
}

/*!
 * \brief Retourne l'objet tenu par le robot.
 * 
 * @return un pointeur sur l'objet tenu par le robot.
 */
Objet* Robot::getObjet() const{
	return this->objet;
}

/*!
 * \brief Retourne la position du robot.
 * 
 * @return la position (x,y) du robot.
 */
Position Robot::getPosition() const{
	return this->position;
}

/*!
 * \brief Retourne le dernier ordre donné au robot.
 * 
 * @return une string représentant le dernier ordre donné.
 */
string Robot::getDernierOrdre() const{
	return this->dernierOrdre;
}

/*!
 * \brief Modifie la position du robot.
 * 
 * @param x un entier représentant la position en abscisse
 * @param y un entier représentant la position en ordonnée
 * 
 */
void Robot::setPosition(int x, int y){
	this->position.setX(x);
	this->position.setY(y);
}

/*!
 * \brief Fait avancer le robot.
 * 
 * Le robot avance aux coordonnées indiquées.
 * @param x un entier représentant la position en abscisse
 * @param y un entier représentant la position en ordonnée
 */
void Robot::avancer(int x, int y) {
	if(this->etat){
		this->etat = this->etat->avancer();
		this->setPosition(x,y);
		char buf[100];
		sprintf(buf, "Avancer en (%d, %d)", x, y);
		dernierOrdre = string(buf);
		notifier();
	}
}

/*!
 * \brief Fait tourner le robot.
 * 
 * Le robot se tourne vers la direction indiquée.
 * @param direction un string ("N", "S", "E" ou "O")
 */
void Robot::tourner(string direction) {
	if(this->etat){
		this->etat = this->etat->tourner();
		this->direction = direction;
		if(this->plot) {
			this->plot = NULL;
		}
		char buf[100];
		sprintf(buf, "Tourner direction %s", direction.c_str());
		dernierOrdre = string(buf);
		notifier();
	}
}

/*!
 * \brief Fait saisir un objet au robot.
 * 
 * Associe l'objet passe en paramètre au robot.
 * Cet objet se trouve sur le plot en face du robot.
 * @param objet un pointeur sur l'objet à saisir
 */
void Robot::saisir(Objet * objet) {
	if(this->etat){
		this->etat = this->etat->saisir();
		this->objet = objet;
		dernierOrdre = "Saisir";
		notifier();
	}
}

/*!
 * \brief Le robot pose l'objet qu'il tient.
 * 
 * Dissocie l'objet lié au robot. 
 * Cet objet est reposé sur le plot qui est en face du robot.
 */
void Robot::poser() {
	if(this->etat){
		this->etat = this->etat->poser();
		if(objet){
			this->objet = NULL;
		}
		dernierOrdre = "Poser";
		notifier();
	}
}

/*!
 * \brief Le robot pèse l'objet qu'il tient.
 * 
 * Renvoie le poids de l'objet tenu par le robot.
 * @return un entier : le poids de l'objet tenu
 */
int Robot::peser() {
	if(this->etat){
		this->etat = this->etat->peser();
		dernierOrdre = "Peser";
		notifier();
		return this->objet->getPoids();
	} else {
		return -1;
	}
}

/*!
 * \brief Fait rencontrer un plot au robot.
 * 
 * Associe au robot un plot se trouvant en face de lui.
 * @param pointeur sur le plot
 */
void Robot::rencontrerPlot(Plot * plot) {
	if(this->etat){
		this->etat = this->etat->rencontrerPlot();
		this->plot = plot;
		dernierOrdre = "Rencontrer plot";
		notifier();
	}
}

/*!
 * \brief Le robot calcule la hauteur du plot.
 * 
 * Renvoie la hauteur du plot se trouvant face au robot.
 * @return un entier : la hauteur du plot
 */
int Robot::evaluerPlot() {
	if(this->etat){
		this->etat = this->etat->evaluerPlot();
		dernierOrdre = "Evaluer Plot";
		notifier();
		return this->plot->getHauteur();
	} else {
		return -1;
	}
}

/*!
 * \brief Fige le robot
 * 
 * Le robot est stoppé dans l'état dans lequel il se trouve.
 * Le seul message qu'il accepte est alors repartir().
 */
void Robot::figer() {
	if(this->etat){
		this->etat = this->etat->figer(this);
		dernierOrdre = "Figer";
		notifier();
	}
}

/*!
 * \brief Défige le robot
 * 
 * Le robot repart dans l'état dans lequel il se trouvait avant d'être figé.
 */
void Robot::repartir() {
	if(this->etat){
		this->etat = this->etat->repartir(this);
		dernierOrdre = "Repartir";
		notifier();
	}
}
