#ifndef TRIPLET_H
#define TRIPLET_H

#include "vector3d.h"
#include "positionnable.h"

class Triplet
{
public:
    double time;
    Vector3D impact;
    Positionnable * positionnable = NULL;
    Triplet();
    Triplet(double time, Positionnable * positionnable, Vector3D impact);
};

#endif // TRIPLET_H
