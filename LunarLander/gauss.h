#ifndef GAUSS_H
#define GAUSS_H


#include <stdio.h>
#include <math.h>

/*
  n: nombre d'equation (int)
  angles: liste des 4 angles mesurés (double)
  x: liste des positions de l'observateur en x (double)
  y: liste des positions de l'observateur en y (double)
  t: liste des instants de mesures (double)
  s: pointeur sur un tableau de long double de taille 4 (double)

  return: tableau contenant x0 y0 vx vy, matrice d'état du mobile
*/
double* gauss(int n,double angles[], double posx[], double posy[], double inst[], double* s);

#endif // GAUSS_H
