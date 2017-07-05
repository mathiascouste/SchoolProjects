#include "texture.h"

GLuint Texture::crate;
GLuint Texture::earth;
GLuint Texture::acier;

void Texture::loadTextureALL() {
    Texture::crate = loadTexture("images/crate2.png");
    Texture::earth = loadTexture("images/carte.png");
    Texture::acier = loadTexture("images/acier.jpg");
}

Texture::Texture()
{
}
