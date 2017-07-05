#include "planet.h"
#include "texture.h"

#include <cmath>
#include <algorithm>

Planet::Planet(double masse, double rayon) : rayon(rayon), rayonAtmopshere(0) {
    this->masse = masse;
}

void Planet::dessiner() {

    glTranslated(this->position.X/1000,this->position.Y/1000,this->position.Z/1000);

    glRotated(this->rotation.X,1,0,0);
    glRotated(this->rotation.Y,0,1,0);
    glRotated(this->rotation.Z,0,0,1);

    this->dessinerSol();
    if(this->rayonAtmopshere > 0) {
        this->dessinerAtmosphere();
    }

    glRotated(-this->rotation.X,1,0,0);
    glRotated(-this->rotation.Y,0,1,0);
    glRotated(-this->rotation.Z,0,0,1);
    glTranslated(-this->position.X/1000,-this->position.Y/1000,-this->position.Z/1000);
}

void Planet::dessinerSol() {
    glColor3ub(255,255,255);

    glEnable(GL_TEXTURE_2D);

    glBindTexture(GL_TEXTURE_2D, Texture::earth);
    GLUquadric* params = gluNewQuadric();

    gluQuadricDrawStyle(params,GLU_FILL);
    gluQuadricTexture(params,GL_TRUE);

    gluSphere(params,this->rayon/1000,200,200);
    gluDeleteQuadric(params);

    glDisable(GL_TEXTURE_2D);
}

void Planet::dessinerAtmosphere() {
    glColor4ub(0,0,255,1);

    GLfloat bleu[] = {0.0f, 0.0f, 1.0f, 0.10f};

    glColor4fv(bleu);

    //initialisation de la transparence
    glEnable(GL_BLEND);
    //la couleur de l'objet va être (1-alpha_de_l_objet) * couleur du fond et (le_reste * couleur originale)
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    GLUquadric* params = gluNewQuadric();

    gluQuadricDrawStyle(params,GLU_FILL);

    gluSphere(params,(this->rayon+this->rayonAtmopshere)/1000,100,100);
    gluDeleteQuadric(params);

    glDisable(GL_BLEND);
}

double Planet::getBoundingRay() {
    return this->rayon;
}
