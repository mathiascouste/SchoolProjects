#include "scene.h"
#include <iostream>

using namespace std;

int stopTheLoop;

Scene::Scene()
{
    time_per_frame = 1000/FPS;
    this->toFollow = NULL;
}

void stop()
{
    stopTheLoop = 1;
}

bool Scene::OnInit() {
    // INIT
    SDL_Init(SDL_INIT_VIDEO);
    atexit(stop);

    stopTheLoop = 0;

    SDL_WM_SetCaption("SDL GL Application", NULL);
    screen = SDL_SetVideoMode(LARGEUR_FENETRE, HAUTEUR_FENETRE, 32, SDL_OPENGL);

    // INIT GL
    glMatrixMode( GL_PROJECTION );
    glLoadIdentity( );
    gluPerspective(70,(double)LARGEUR_FENETRE/HAUTEUR_FENETRE,0.001,1000000);

    glEnable(GL_DEPTH_TEST);
    glEnable(GL_TEXTURE_2D);

    Texture::loadTextureALL();
    //ObjLoader::loadObjALL();
    // END INIT GL

    SDL_EnableKeyRepeat(10,10);


    /* Les planetes */
    Planet * terre = new Planet(1.48*pow(10,15), 2500);
    terre->setPosition(Vector3D(0,-10,0));
    terre->setVitesse(Vector3D(0.2,0.3,0));

    /* Lander */
    double altiture = 1500;
    double distanceLanderTerre = terre->getRayon() + altiture;
    lander = new LanderCommandable();
    lander->setPosition(terre->getPosition() + Vector3D(distanceLanderTerre,0,0));
    lander->setVitesse(terre->getVitesse() + Vector3D(0,terre->vitesseOrbitale(distanceLanderTerre),0));

    lander->initOrdinateur(terre);

    Corps::ajouterDansListe(terre);
    Corps::ajouterDansListe(lander);

    /* Camera */
    this->toFollow = lander;
    //this->toFollow = NULL;
    camera = new FreeFlyCamera(Vector3D(lander->getPosition().X/1000,0,0));

    last_time = SDL_GetTicks();

    return 1;
}

void Scene::OnCleanup() {
    //delete camera;
}

void Scene::OnLoop() {
    while(SDL_PollEvent(&event)) {
        OnEvent(event);
    }
    current_time = SDL_GetTicks();
    elapsed_time = current_time - last_time;
    last_time = current_time;

    //elapsed_time *=  MULT_TIME;
    double calculTime = (double)MULT_TIME/(double)FPS;

    Vector3D oldPositionToFollow;
    if(this->toFollow!=NULL) {
        oldPositionToFollow = toFollow->getPosition();
    }
    Corps::animateALL(calculTime);
    Particle::animateALL(calculTime);

    if(this->toFollow!=NULL) {
        Vector3D diffPositionToFollow = toFollow->getPosition() - oldPositionToFollow;
        camera->animate(calculTime);
        camera->setPosition(camera->getPosition() + diffPositionToFollow/1000);
    } else {
        camera->animate(calculTime);
    }

    OnRender();

    stop_time = SDL_GetTicks();
    if ((stop_time - last_time) < time_per_frame)
    {
        SDL_Delay(time_per_frame - (stop_time - last_time));
    }
}

void Scene::OnRender() {
    glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );

    glMatrixMode( GL_MODELVIEW );
    glLoadIdentity();

    camera->look();

    Corps::dessinerALL();
    Particle::dessinerALL();

    //drawInfo();

    glFlush();
    SDL_GL_SwapBuffers();
}

void Scene::drawInfo() {
    //--------> C'est ici qu'on passe en 2D
    glDisable(GL_DEPTH_TEST);

    glMatrixMode(GL_PROJECTION);
    glPushMatrix();
    glLoadIdentity();
    gluOrtho2D(0.0, LARGEUR_FENETRE, 0.0, HAUTEUR_FENETRE);
    glMatrixMode(GL_MODELVIEW);
    glPushMatrix();
    glLoadIdentity();


    glColor3ub(255, 255, 255);

    glEnable(GL_TEXTURE_2D);
    GLuint glID = loadText("toto", false);
    std::cout << glID << std::endl;

    glBindTexture(GL_TEXTURE_2D, glID);

    glBegin(GL_QUADS);
    glTexCoord2d(0,1);  glVertex2i(10,  50);
    glTexCoord2d(1,1);  glVertex2i(100, 50);
    glTexCoord2d(1,0);  glVertex2i(100, 10);
    glTexCoord2d(0,0);  glVertex2i(10,  10);
    glEnd();

    glDisable(GL_TEXTURE_2D);

    glMatrixMode(GL_PROJECTION);
    glPopMatrix();
    glMatrixMode(GL_MODELVIEW);
    glPopMatrix();

    glEnable(GL_DEPTH_TEST);

    //--------> Fin de la 2D
}

int Scene::OnExecute() {
    if(OnInit()==0) return 1;
    while(stopTheLoop==0) {
        OnLoop();
        OnRender();
    }
    SDL_Quit();
    OnCleanup();
}

void Scene::OnEvent(SDL_Event event) {
    switch(event.type)
    {
        case SDL_QUIT:
            exit(0);
            break;
        case SDL_KEYDOWN:
            switch (event.key.keysym.sym)
            {
                break;
                    case SDLK_ESCAPE:
                    exit(0);
                break;
                default :
                    camera->OnKeyboard(event.key);
                    lander->OnKeyboard(event.key);
                    break;
            }
            break;
        case SDL_KEYUP:
            camera->OnKeyboard(event.key);
            break;
        case SDL_MOUSEMOTION:
            camera->OnMouseMotion(event.motion);
            break;
        case SDL_MOUSEBUTTONUP:
        case SDL_MOUSEBUTTONDOWN:
            camera->OnMouseButton(event.button);
            break;
    }
}
