#include "positionnable.h"
#include <iostream>
#include "corps.h"
#include "triplet.h"

Positionnable::Positionnable() : position(Vector3D(0,0,0)), vitesse(Vector3D(0,0,0)), acceleration(Vector3D(0,0,0))
{
    rotation = Vector3D(0,0,0);
    vitesseRotation = Vector3D(0,0,0);
    nextPosition = Vector3D(0,0,0);
    nextVitesse = Vector3D(0,0,0);
}

void Positionnable::setRotationVitesse(Vector3D vitesseRotation) {
    this->vitesseRotation = vitesseRotation;
}

void Positionnable::dessiner() {}

void Positionnable::calculateNextVitesse(double timelapse) {
    double timelapseSecond = timelapse;

    Corps * theCorps = dynamic_cast<Corps*>(this);
    if(theCorps != NULL) {
        Triplet triplet = Corps::intersectALL(theCorps);
        if(triplet.positionnable == NULL) {
            nextVitesse = vitesse + acceleration*timelapseSecond;
        } else {
            // En cas d'impact
            nextVitesse = Vector3D(0,0,0);
        }
    } else {
        nextVitesse = vitesse + acceleration*timelapseSecond;
    }

    vitesseRotation += accelerationRotation*timelapseSecond;
}

void Positionnable::calculateNextPosition(double timelapse) {
    double timelapseSecond = timelapse;
    nextPosition = position + vitesse*timelapseSecond;
    rotation += vitesseRotation*timelapseSecond;
    while(rotation.X > 360) rotation.X -= 360;
    while(rotation.Y > 360) rotation.Y -= 360;
    while(rotation.Z > 360) rotation.Z -= 360;
    while(rotation.X < 0) rotation.X += 360;
    while(rotation.Y < 0) rotation.Y += 360;
    while(rotation.Z < 0) rotation.Z += 360;
}

void Positionnable::updatePosition() {
    position = nextPosition;
}

void Positionnable::updateVitesse() {
    vitesse = nextVitesse;
}
void Positionnable::resetAcceleration() {
    acceleration = Vector3D(0,0,0);
    accelerationRotation = Vector3D(0,0,0);
}
