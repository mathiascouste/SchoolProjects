#ifndef SCENE_H
#define SCENE_H

#include <GL/glew.h>
#include <SDL/SDL.h>
#include <GL/gl.h>
#include <GL/glu.h>
#include <GL/glut.h>
#include <cstdlib>
#include <iostream>

#include <cmath>

#include "freeflycamera.h"
#include "landercommandable.h"
#include "planet.h"
#include "particle.h"
#include "texture.h"
#include "objloader.h"

#include "sdlglutils.h"

#define FPS 40
#define MULT_TIME 1000

#define LARGEUR_FENETRE 640  // 1280 OU 640
#define HAUTEUR_FENETRE 480  // 960  OU 480

class Scene
{
private:
    SDL_Surface *screen = NULL;
    FreeFlyCamera * camera;
    LanderCommandable * lander;
    SDL_Event event;
    Uint32 time_per_frame;

    Uint32 last_time,current_time,elapsed_time; //for time animation
    Uint32 stop_time; //for frame limit

    Corps* toFollow;
public:
    Scene();
    bool OnInit();
    void OnCleanup();
    void OnLoop();
    void OnRender();
    int OnExecute();
    void OnEvent(SDL_Event event);
    void drawInfo();
};

void stop();

#endif // SCENE_H
