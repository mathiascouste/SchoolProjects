#include "particle.h"

#include <iostream>

#define ESPERANCE 100

std::list<Particle*> Particle::particle_list;

void Particle::ajouterDansListe(Particle * p) {
    Particle::particle_list.push_back(p);
}



Particle::Particle(double px,double py, double pz, double vx, double vy, double vz)
{
    position = Vector3D(px,py,pz);
    vitesse = Vector3D(vx,vy,vz);
    age = 0;
    deadAge = ESPERANCE;
}

Particle::Particle(Vector3D position, Vector3D vitesse) {
    this->position = position;
    this->vitesse = vitesse;
    age = 0;
    deadAge = ESPERANCE;
}

void Particle::dessinerALL() {
    for (std::list<Particle*>::iterator it=(Particle::particle_list.begin()); it!=(Particle::particle_list.end()); it++) {
        (*it)->dessiner();
    }
}

void Particle::dessiner() {
    glTranslated(this->position.X/1000,this->position.Y/1000,this->position.Z/1000);

    glBegin(GL_POINTS);
    int color = (int)((1 - age/ESPERANCE)*255);
    if(isOld() == 1) color = 0;
    glColor3ub(color, color , color); glVertex3d(0,0,0);
    glEnd();

    glTranslated(-this->position.X/1000,-this->position.Y/1000,-this->position.Z/1000);
}


void Particle::animateALL(double timelapse) {
    unsigned int size = Particle::particle_list.size();
    for(unsigned int i = 0 ; i < size ; i++) {
        Particle * tmpP = Particle::particle_list.front();
        Particle::particle_list.pop_front();
        tmpP->age += timelapse;
        if(tmpP->isOld() != 1) {
            Particle::particle_list.push_back(tmpP);
        }
    }

    for (std::list<Particle*>::iterator it=(Particle::particle_list.begin()); it!=(Particle::particle_list.end()); it++) {
        (*it)->calculateNextPosition(timelapse);
    }
    for (std::list<Particle*>::iterator it=(Particle::particle_list.begin()); it!=(Particle::particle_list.end()); it++) {
        (*it)->updatePosition();
    }
}

int Particle::isOld() {
    if(age > deadAge) return 1;
    else return 0;
}

int Particle::olderThan(double old) {
    if(age > old) return 1;
    else return 0;
}
