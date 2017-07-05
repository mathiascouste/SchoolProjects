#ifndef TEXTURE_H
#define TEXTURE_H

#include <GL/glew.h>
#include <GL/glu.h>
#include "sdlglutils.h"

class Texture
{
public:
    static GLuint crate;
    static GLuint earth;
    static GLuint acier;
    static void loadTextureALL();
    Texture();
};

#endif // TEXTURE_H
