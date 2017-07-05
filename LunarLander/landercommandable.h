#ifndef LANDERCOMMANDABLE_H
#define LANDERCOMMANDABLE_H

#include <GL/glew.h>
#include <SDL/SDL.h>
#include <GL/gl.h>
#include <GL/glu.h>
#include <cstdlib>
#include "lander.h"
#include "ordinateur.h"
#include "vector3d.h"

#define TOLERANCE 0.0005
#define DEFAULT_TEMPS_STABILISATION 5000

class LanderCommandable : public Lander, public IStabLander
{
private:
    // Ordres
    double nActionStabilisation;
    Vector3D ordreStabilisation;
    Vector3D angleDesire;
    // commandes
    double mainReactorPower;
    bool mainReactor;
    bool rXReactorPlus, rXReactorMinus;
    bool rZReactorPlus, rZReactorMinus;
    Ordinateur * ordinateur;
    bool stabilisateur;
    bool stabilisateurActif;

    bool axisSelector;
    int axisSelectorState;
    double TEMPS_STABILISATION;
    double coefStabilisation;
    Vector3D axisDesire;
public:
    LanderCommandable();
    virtual void dessiner();
    void dessinerReacteurs();
    virtual void OnKeyboard(const SDL_KeyboardEvent & event);

    void applyCommandToAccell(double timelapse);

    void doSpecial(double timelapse);
    void setOrdinateur(Ordinateur* ordinateur) {this->ordinateur = ordinateur;}

    // ISTABLANDER
    virtual Vector3D getPosition() { return this->position;}
    void initOrdinateur(Positionnable * target);

    void stabiliser(double timelapse);
    bool hasToStabilize();
    void calculerPhaseStabilisation();

    void axesSelectorer(double timelapse);
    Vector3D calculateVMax();
};

#endif // LANDERCOMMANDABLE_H
