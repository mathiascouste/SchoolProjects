#define NOMBREPARTICLES 1

#include <iostream>
#include <cstdlib>

#include "lander.h"
#include "pave.h"
#include "vector3d.h"
#include "particle.h"
#include "texture.h"

#include <math.h>

using namespace std;

Lander::Lander()
{
    this->masse = 500;
}

void Lander::dessiner() {
    glTranslated(position.X/1000,
                 position.Y/1000,
                 position.Z/1000);

    glRotated(this->rotation.X,1,0,0);
    glRotated(this->rotation.Y,0,1,0);
    glRotated(this->rotation.Z,0,0,1);

    //pave.Dessiner();
    dessinerCarlingue();

    glRotated(-this->rotation.X,1,0,0);
    glRotated(-this->rotation.Y,0,1,0);
    glRotated(-this->rotation.Z,0,0,1);

    glTranslated(-position.X/1000,
                 -position.Y/1000,
                 -position.Z/1000);
}

void Lander::dessinerCarlingue() {
    glColor3ub(255,255,255);

    glEnable(GL_TEXTURE_2D);
    glBindTexture(GL_TEXTURE_2D, Texture::acier);

    GLUquadric* params = gluNewQuadric();

    gluQuadricDrawStyle(params,GLU_FILL);
    gluQuadricTexture(params,GL_TRUE);

    gluSphere(params,(double)BOUNDING/(double)2000,10,10);

    gluDeleteQuadric(params);

    glDisable(GL_TEXTURE_2D);
}

void Lander::emitParticle() {
    for(int i = 0 ; i < NOMBREPARTICLES ; i++) {
        Particle * particle = new Particle(position.X,
                                           position.Y,
                                           position.Z,
                                           -vitesse.X*((double)(rand()%1000))/1000,
                                           -vitesse.Y*((double)(rand()%1000))/1000,
                                           -vitesse.Z*((double)(rand()%1000))/1000);
        Particle::ajouterDansListe(particle);
    }
}

double Lander::getBoundingRay() {
    return BOUNDING/2;
}
