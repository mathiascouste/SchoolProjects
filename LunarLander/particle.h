#ifndef PARTICLE_H
#define PARTICLE_H

#include <GL/glew.h>
#include <list>
#include <SDL/SDL.h>
#include <GL/gl.h>
#include <GL/glu.h>
#include <cstdlib>

#include "positionnable.h"
#include "vector3d.h"

class Particle : public Positionnable
{
private:
    double age;
    double deadAge;
public:
    static std::list<Particle*> particle_list;
    static void ajouterDansListe(Particle * p);
    Particle(double px,double py, double pz, double vx, double vy, double vz);
    Particle(Vector3D position, Vector3D vitesse);
    virtual void dessiner();
    static void dessinerALL();
    static void animateALL(double timelapse);
    int olderThan(double old);
    int isOld();
    void setDeadAge(double dead) { this->deadAge = dead;}
};

#endif // PARTICLE_H
