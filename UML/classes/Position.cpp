/*!
 * \file Position.cpp
 *
 * \brief Implémentation de la classe Position.
 */

#include "Position.hpp"

/*!
 * \brief Contructeur
 * Instancie une position avec les coordonnées x et y passées en paramètre.
 * @param x un entier représentant l'abscisse
 * @param y un entier représentant l'ordonnée
 */
Position::Position(int x, int y):x(x),y(y){}

/*!
 * \brief Renvoie la position en abscisse
 * @return un entier
 */
int Position::getX() const{
	return this->x;
}

/*!
 * \brief Renvoie la position en ordonnée
 * @return un entier
 */
int Position::getY() const{
	return this->y;
}

/*!
 * \brief Modifie l'abscisse
 * @param x un entier relatif
 */
void Position::setX(int x) {
	this->x = x;
}

/*!
 * \brief Modifie l'ordonnée
 * @param y un entier relatif
 */
void Position::setY(int y) {
	this->y = y;
}

/*!
 * \brief Surchage de l'opérateur <<
 * @param os la référence de l'ostream 
 * @param p la position constante a écrire
 */
ostream& operator<<( ostream &os, Position const& p )
{
	os << "(X=" << p.x << ",Y=" << p.y << ")";
    return os;
}
