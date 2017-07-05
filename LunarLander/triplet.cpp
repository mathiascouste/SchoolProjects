#include "triplet.h"


Triplet::Triplet()
: time(0), positionnable(NULL), impact(Vector3D(0,0,0))
{}

Triplet::Triplet(double time, Positionnable * positionnable, Vector3D impact)
: time(time), positionnable(positionnable), impact(impact)
{
}
