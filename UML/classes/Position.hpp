/*!
 * \file Position.hpp
 *
 * \brief Définition de la classe Position.
 */

#ifndef _POSITION_HPP_
#define _POSITION_HPP_

#include <iostream>

using namespace std;

class Position {
	private:
		int x; /*!< Position en abscisse */
		int y; /*!< Position en ordonnée */
	public:
		Position(int x = 0, int y = 0);
		int getX() const;
		int getY() const;
		void setX(int x);
		void setY(int y);
		friend ostream& operator<<( ostream &os, Position const& p );
};

#endif
