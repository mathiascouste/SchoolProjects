#ifndef SDLGLUTILS_H
#define SDLGLUTILS_H

#include <GL/glew.h>
#include <GL/gl.h>
#include <SDL/SDL.h>
#include <string>

#ifndef GL_CLAMP_TO_EDGE
#define GL_CLAMP_TO_EDGE 0x812F
#endif

GLuint loadTexture(const char * filename,bool useMipMap = true);
int takeScreenshot(const char * filename);
void drawAxis(double scale = 1);
int initFullScreen(unsigned int * width = NULL,unsigned int * height = NULL);
int XPMFromImage(const char * imagefile, const char * XPMfile);
SDL_Cursor * cursorFromXPM(const char * xpm[]);
SDL_Surface *load_image( std::string filename );
void apply_surface( int x, int y, SDL_Surface* source, SDL_Surface* destination );
GLuint loadText(const char * _text, bool useMipMap);

#endif //SDLGLUTILS_H
