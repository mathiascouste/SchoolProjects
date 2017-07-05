#ifndef STABILISATEUR_H
#define STABILISATEUR_H

#include "vector3d.h"
#include "positionnable.h"
#include <vector>

class IStabLander
{
    public:
        virtual ~IStabLander() {}
    virtual Vector3D getPosition() = 0;
};

class Accumulateur
{
private:
    int size;
    int filling;
    double*data;
public:
    Accumulateur(int size = 4);
    void setSize(int size);
    int getSize() const { return this->size;}
    int getFilling() const { return this->filling;}
    void pushData(double data);
    double getData(int index) const { return this->data[index];}
    double* getArrayOfNLastElements(int size);
    double getLast() const;
    bool isFull() const;
    bool isEmpty() const;
    friend std::ostream& operator<<( std::ostream &flux, Accumulateur const& accu );
};

class Ordinateur
{
private:
    IStabLander * lander;
    bool active;
    Positionnable * target;
    Accumulateur accuAngle;
    Accumulateur accuPosX;
    Accumulateur accuPosY;
    Accumulateur accuTemps;
    double compteur;
    double tempus;
    void betterCallGauss();
public:
    Ordinateur();
    void setLander(IStabLander * lander) {this->lander = lander;}
    void setActive(bool active) { this->active = active;}
    void setTarget(Positionnable * target) { this->target = target;}

    void justDoIt(Uint32 timelapse);
    double mesureAngle();
};

#endif // STABILISATEUR_H
