#ifndef LANDER_H
#define LANDER_H

#include <GL/glew.h>
#include <SDL/SDL.h>
#include <GL/gl.h>
#include <GL/glu.h>
#include <cstdlib>

#include "corps.h"

#define BOUNDING (double)100

class Lander : public Corps
{
private:
public:
    Lander();
    virtual void dessiner();
    virtual void emitParticle();
    virtual double getBoundingRay();
    void dessinerCarlingue();
};

#endif // LANDER_H
