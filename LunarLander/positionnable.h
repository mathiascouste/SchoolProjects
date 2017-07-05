#ifndef POSITIONNABLE_H
#define POSITIONNABLE_H

#include <GL/glew.h>
#include <SDL/SDL.h>
#include <GL/gl.h>
#include <GL/glu.h>
#include <cstdlib>

#include "vector3d.h"

class Positionnable
{
protected:
    Vector3D position, nextPosition;
    Vector3D vitesse, nextVitesse;
    Vector3D acceleration;
    Vector3D rotation;
    Vector3D vitesseRotation;
    Vector3D accelerationRotation;
public:
    Positionnable();

    // accessors
    void setPosition(Vector3D position) { this->position = position;}
    void setVitesse(Vector3D vitesse) { this->vitesse = vitesse;}
    void setRotationVitesse(Vector3D vitesseRotation);
    virtual Vector3D getVitesse() {return vitesse;}
    virtual Vector3D getPosition() { return position;}

    // methods
    virtual void dessiner();
    //void moveFromAccel(Uint32 timelapse);
    void calculateNextVitesse(double timelapse);
    void calculateNextPosition(double timelapse);
    void updatePosition();
    void updateVitesse();
    void resetAcceleration();
};

#endif // POSITIONNABLE_H
