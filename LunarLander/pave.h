#ifndef PAVE_H
#define PAVE_H

#include <GL/glew.h>
#include <SDL/SDL.h>
#include <GL/gl.h>
#include <GL/glu.h>
#include <GL/glut.h>
#include <cstdlib>

class Pave
{
private:
    double lX, lY, lZ;
public:
    Pave(double lx, double ly, double lz);
    void Dessiner();
    void setColor(int r,int g,int b);
};

#endif // PAVE_H
