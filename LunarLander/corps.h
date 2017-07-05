#ifndef CORPS_H
#define CORPS_H

#include <GL/glew.h>
#include <SDL/SDL.h>
#include <GL/gl.h>
#include <GL/glu.h>
#include <GL/glut.h>
#include <cstdlib>
#include <list>
#include <utility>

#include "positionnable.h"
#include "triplet.h"

class Corps : public Positionnable
{
protected:
    double masse;
    virtual void calculateAcceleration();
    std::pair<double, double> resolve2nd(double a, double b, double c);
public:
    static std::list<Corps*> corps_list;
    static void ajouterDansListe(Corps * corps);
    static Triplet intersectALL(Corps * theCorps);
    static void animateALL(double timelapse);
    static void dessinerALL();

    Corps();
    virtual void dessiner();
    virtual Triplet intersect(Corps * theCorps);
    double vitesseOrbitale(double rayon);
    double goodDirection(Vector3D start, Vector3D final, Vector3D impact);
    virtual double getBoundingRay();
    double getMasse() { return this->masse;}

    Vector3D calculVectorRebond(Vector3D incident, Vector3D normale);
    void doRebound(Triplet triplet);

    virtual void doSpecial(double timelapse);
};

#endif // CORPS_H
