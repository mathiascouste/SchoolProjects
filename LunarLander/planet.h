#ifndef PLANET_H
#define PLANET_H

#include <GL/glew.h>
#include <SDL/SDL.h>
#include <GL/gl.h>
#include <GL/glu.h>
#include <cstdlib>
#include <list>

#include "corps.h"
#include "vector3d.h"

class Planet : public Corps
{
private:
    double rayon;
    double rayonAtmopshere;
    void dessinerSol();
    void dessinerAtmosphere();
public:
    static void dessinerPlanetes();
    Planet(double masse, double rayon);
    virtual void dessiner();
    double getRayon() { return this->rayon;}
    void setRayonAtmosphere(double r) { this->rayonAtmopshere = r; }
    virtual double getBoundingRay();
};

#endif // PLANET_H
