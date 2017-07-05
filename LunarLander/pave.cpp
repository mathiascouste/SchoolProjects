#include "pave.h"
#include "texture.h"
#include "objloader.h"

Pave::Pave(double lx, double ly, double lz) : lX(lx), lY(ly), lZ(lz) {
}

void Pave::Dessiner() {

    double points[8][3] = {
        {-lX/2,-lY/2,-lZ/2},
        {-lX/2,lY/2,-lZ/2},
        {lX/2,lY/2,-lZ/2},
        {lX/2,-lY/2,-lZ/2},
        {-lX/2,-lY/2,lZ/2},
        {-lX/2,lY/2,lZ/2},
        {lX/2,lY/2,lZ/2},
        {lX/2,-lY/2,lZ/2}
    };

    glColor3ub(255,0,0);
    GLUquadric* params = gluNewQuadric();
    gluQuadricDrawStyle(params,GLU_LINE);
    //gluSphere(params,0.1,15,15);
    gluDeleteQuadric(params);

    glEnable(GL_TEXTURE_2D);

    glColor3ub(150,150,150);
    glBindTexture(GL_TEXTURE_2D, Texture::crate);

    glBegin(GL_QUADS);

    glTexCoord2d(0,0); glVertex3d(points[0][0],points[0][1],points[0][2]);
    glTexCoord2d(0,1); glVertex3d(points[1][0],points[1][1],points[1][2]);
    glTexCoord2d(1,1); glVertex3d(points[2][0],points[2][1],points[2][2]);
    glTexCoord2d(1,0); glVertex3d(points[3][0],points[3][1],points[3][2]);

    glTexCoord2d(0,0); glVertex3d(points[4][0],points[4][1],points[4][2]);
    glTexCoord2d(0,1); glVertex3d(points[5][0],points[5][1],points[5][2]);
    glTexCoord2d(1,1); glVertex3d(points[6][0],points[6][1],points[6][2]);
    glTexCoord2d(1,0); glVertex3d(points[7][0],points[7][1],points[7][2]);

    glTexCoord2d(0,0); glVertex3d(points[2][0],points[2][1],points[2][2]);
    glTexCoord2d(0,1); glVertex3d(points[1][0],points[1][1],points[1][2]);
    glTexCoord2d(1,1); glVertex3d(points[5][0],points[5][1],points[5][2]);
    glTexCoord2d(1,0); glVertex3d(points[6][0],points[6][1],points[7][2]);

    glTexCoord2d(0,0); glVertex3d(points[3][0],points[3][1],points[3][2]);
    glTexCoord2d(0,1); glVertex3d(points[0][0],points[0][1],points[0][2]);
    glTexCoord2d(1,1); glVertex3d(points[4][0],points[4][1],points[4][2]);
    glTexCoord2d(1,0); glVertex3d(points[7][0],points[7][1],points[7][2]);

    glTexCoord2d(0,0); glVertex3d(points[0][0],points[0][1],points[0][2]);
    glTexCoord2d(0,1); glVertex3d(points[1][0],points[1][1],points[1][2]);
    glTexCoord2d(1,1); glVertex3d(points[5][0],points[5][1],points[5][2]);
    glTexCoord2d(1,0); glVertex3d(points[4][0],points[4][1],points[4][2]);

    glTexCoord2d(0,0); glVertex3d(points[3][0],points[3][1],points[3][2]);
    glTexCoord2d(0,1); glVertex3d(points[2][0],points[2][1],points[2][2]);
    glTexCoord2d(1,1); glVertex3d(points[6][0],points[6][1],points[6][2]);
    glTexCoord2d(1,0); glVertex3d(points[7][0],points[7][1],points[7][2]);

    glEnd();

    glDisable(GL_TEXTURE_2D);

    ObjLoader::cube.draw();
}
