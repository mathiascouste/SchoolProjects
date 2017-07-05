#include "landercommandable.h"
#include "particle.h"

#include <iostream>

using namespace std;

#define NOMBREPARTICLES 100

LanderCommandable::LanderCommandable()
{
    mainReactorPower = 0;
    mainReactor =  false;
    rXReactorPlus = rXReactorMinus = false;
    rZReactorPlus = rZReactorMinus = false;

    TEMPS_STABILISATION = DEFAULT_TEMPS_STABILISATION;
    coefStabilisation = 0.8;
    axisSelector = false;
    axisSelectorState = 0;

    stabilisateur = false;
    stabilisateurActif = false;
    angleDesire = Vector3D(0,0,0);
    axisDesire = Vector3D(0,0,0);

    ordreStabilisation = Vector3D(0,0,0);
}

void LanderCommandable::dessiner() {
    glTranslated(position.X/1000,
                 position.Y/1000,
                 position.Z/1000);

    glRotated(this->rotation.X,1,0,0);
    glRotated(this->rotation.Y,0,1,0);
    glRotated(this->rotation.Z,0,0,1);

    dessinerCarlingue();
    dessinerReacteurs();

    glRotated(-this->rotation.X,1,0,0);
    glRotated(-this->rotation.Y,0,1,0);
    glRotated(-this->rotation.Z,0,0,1);

    glTranslated(-position.X/1000,
                 -position.Y/1000,
                 -position.Z/1000);
}

void LanderCommandable::dessinerReacteurs() {

    glTranslated(0, -BOUNDING/2000, 0);
    glRotated(90,1,0,0);

    glColor3ub(255,255,255);

    GLUquadric* params = gluNewQuadric();

    gluQuadricDrawStyle(params,GLU_FILL);

    gluDisk(params,0,BOUNDING/5000,36,1);

    gluDeleteQuadric(params);


    glRotated(-90,1,0,0);
    glTranslated(0, BOUNDING/2000, 0);
}

void LanderCommandable::OnKeyboard(const SDL_KeyboardEvent & event) {
    switch(event.keysym.sym) {
    case SDLK_SPACE:
        mainReactor = true;
        break;
    case SDLK_LEFT: // Z
        rZReactorPlus = true;
        break;
    case SDLK_RIGHT:// Z
        rZReactorMinus = true;
        break;
    case SDLK_UP:   // X
        rXReactorPlus = true;
        break;
    case SDLK_DOWN: // X
        rXReactorMinus = true;
        break;
    case SDLK_PAGEUP: // ^
        mainReactorPower += 0.05;
        if(mainReactorPower > 1) {
            mainReactorPower = 1;
        }
        break;
    case SDLK_PAGEDOWN: // v
        mainReactorPower -= 0.05;
        if(mainReactorPower < 0) {
            mainReactorPower = 0;
        }
        break;
    case SDLK_t:
        stabilisateur = !stabilisateur;
        break;
    case SDLK_r:
        stabilisateur = true;
        axisSelector = true;
        break;
    default: {
        //std::cout << "Nothing to do" << std::endl;
        }
    }
}
const float Pi = 3.141592654f;

inline float DegToRad(float x)
{
    return x / 180 * Pi;
}

inline float RadToDeg(float x)
{
    return x / Pi * 180;
}
void LanderCommandable::applyCommandToAccell(double timelapse) {

    /*if(ordinateur != 0) {
        ordinateur->justDoIt(timelapse);
    }*/

    if(mainReactor && mainReactorPower > 0 && mainReactorPower <= 1) {
        Vector3D direction = Vector3D(0,1,0).rotation(Vector3D(1,0,0),
                                                      DegToRad(rotation.X)
                                                      ).rotation(Vector3D(0,1,0),
                                                                 DegToRad(rotation.Y)
                                                                 ).rotation(Vector3D(0,0,1),
                                                                            DegToRad(rotation.Z));
        acceleration += direction*mainReactorPower;

        for(int i = 0 ; i < NOMBREPARTICLES*mainReactorPower ; i++) {
            Vector3D depart = this->position + -1/2*direction*BOUNDING*((double)(rand()%1000))/1000;
            Vector3D vitesse = -100*direction*mainReactorPower;
            vitesse.X += ((double)(rand()%1000))/1000;
            vitesse.Y += ((double)(rand()%1000))/1000;
            vitesse.Z += ((double)(rand()%1000))/1000;
            Particle * particle = new Particle(depart, vitesse);
            particle->setDeadAge(1);
            Particle::ajouterDansListe(particle);
        }
        mainReactor = false;
    }
    if(rZReactorPlus) {
        this->accelerationRotation.Z = 0.01;
        rZReactorPlus = false;
    }
    if(rZReactorMinus) {
        this->accelerationRotation.Z = -0.01;
        rZReactorMinus = false;
    }
    if(rXReactorPlus) {
        this->accelerationRotation.X = 0.01;
        rXReactorPlus = false;
    }
    if(rXReactorMinus) {
        this->accelerationRotation.X = -0.01;
        rXReactorMinus = false;
    }

    if(axisSelector) {
        axesSelectorer(timelapse);
    }
    if(stabilisateur) {
        stabiliser(timelapse);
    }
}

bool LanderCommandable::hasToStabilize() {
    Vector3D vA = this->vitesseRotation;
    double difX = angleDesire.X - vA.X;
    double difY = angleDesire.Y - vA.Y;
    double difZ = angleDesire.Z - vA.Z;
    if(difX < 0) {
        difX *= -1;
    }
    if(difY < 0) {
        difY *= -1;
    }
    if(difZ < 0) {
        difZ *= -1;
    }
    if(difX > TOLERANCE) {
        return true;
    } else if(difY > TOLERANCE) {
        return true;
    } else if(difZ > TOLERANCE) {
        return true;
    } else {
        return false;
    }
}

void LanderCommandable::calculerPhaseStabilisation() {
    Vector3D diffVA = angleDesire - this->vitesseRotation;
    this->ordreStabilisation = Vector3D(diffVA.X,diffVA.Y,diffVA.Z);
    this->ordreStabilisation /= TEMPS_STABILISATION;
    nActionStabilisation = TEMPS_STABILISATION;
}

void LanderCommandable::stabiliser(double timelapse) {
    if(stabilisateurActif) {
        if(nActionStabilisation > 0) {
            this->accelerationRotation += this->ordreStabilisation;
            nActionStabilisation -= timelapse;
        } else {
            stabilisateurActif = false;
        }
    } else {
        if(hasToStabilize()) {
            calculerPhaseStabilisation();
            stabilisateurActif = true;
        }
    }
}

void LanderCommandable::doSpecial(double timelapse) {
    applyCommandToAccell(timelapse);
}

void LanderCommandable::initOrdinateur(Positionnable * target) {
    this->ordinateur = new Ordinateur();
    this->ordinateur->setLander(this);
    this->ordinateur->setActive(true);
    this->ordinateur->setTarget(target);
}

Vector3D LanderCommandable::calculateVMax() {
    Vector3D dif = axisDesire - this->rotation;
    Vector3D vMoy = dif/TEMPS_STABILISATION;
    return vMoy;
}

void LanderCommandable::axesSelectorer(double timelapse) {
    if(!stabilisateurActif) {
        switch(axisSelectorState) {
            case 0:
                this->TEMPS_STABILISATION = DEFAULT_TEMPS_STABILISATION*(1-coefStabilisation);
                this->angleDesire = Vector3D(0,0,0);
                cout << "STEP 1" << endl;
            break;
            case 1:
                this->TEMPS_STABILISATION = DEFAULT_TEMPS_STABILISATION*(coefStabilisation)/2;
                this->angleDesire = calculateVMax();
            cout << "STEP 2" << endl;
            break;
            case 2:
                this->TEMPS_STABILISATION = DEFAULT_TEMPS_STABILISATION*(coefStabilisation)/2;
                this->angleDesire = Vector3D(0,0,0);
                cout << "STEP 3" << endl;
            break;
        case 3:
            cout << "STEP 4 - FINISH !" << endl;
            break;
        }
        axisSelectorState++;
    } else {
        if(axisSelectorState == 4) {
            axisSelectorState = 0;
            axisSelector = false;
            stabilisateur = false;
            this->TEMPS_STABILISATION = DEFAULT_TEMPS_STABILISATION;
        }
    }
}
