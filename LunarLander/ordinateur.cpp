#include "ordinateur.h"
#include <iostream>
#include <math.h>
#include <cstdlib>

#include "gauss.h"

using namespace std;

/*** ************************************* ***/
/*** Definition des methodes de Ordinateur ***/
/*** ************************************* ***/
Ordinateur::Ordinateur()
{
    active = false;
    lander = NULL;
    target = NULL;
    compteur = 0;
    tempus = 0;
}

double ttt = 0;

void Ordinateur::justDoIt(Uint32 timelapse) {
    compteur++;
    tempus += timelapse;
    //tempus++;
    if((int)compteur%5 != 0) {
        return;
    }
    ttt = tempus;

    if(!this->active) return;
    if(this->lander == NULL) return;
    if(this->target == NULL) return;

    double angle = this->mesureAngle();
    double error = 0;
    double errorCoefX = (1 - (((double)(rand() % 100)/100)*error - error/2));
    double errorCoefY = (1 - (((double)(rand() % 100)/100)*error - error/2));

    //std::cout << "ErrorCoefX=" << errorCoefX << " | ErrorCoefY=" << errorCoefY << std::endl;

    double errorX = this->lander->getPosition().X * errorCoefX;
    double errorY = this->lander->getPosition().Y * errorCoefY;

    accuAngle.pushData(angle);
    accuPosX.pushData(errorX);
    accuPosY.pushData(errorY);
    accuTemps.pushData(this->tempus);

    if(accuAngle.isFull() && accuPosX.isFull() && accuPosY.isFull() && accuTemps.isFull()) {
        betterCallGauss();
    } else {
        std::cout << "Remplissage : " << accuAngle.getFilling() << std::endl;
    }
}

double Ordinateur::mesureAngle() {
    double XL = this->lander->getPosition().X;
    double YL = this->lander->getPosition().Y;
    double XT = this->target->getPosition().X;
    double YT = this->target->getPosition().Y;

    double YDIFF = (YL-YT);
    double XDIFF = (XL-XT);

    return  atan2(YDIFF,XDIFF) - M_PI;
}

void Ordinateur::betterCallGauss() {
    int nIteration = accuAngle.getSize();
    double * angles = accuAngle.getArrayOfNLastElements(nIteration);
    double * x = accuPosX.getArrayOfNLastElements(nIteration);
    double * y = accuPosY.getArrayOfNLastElements(nIteration);
    double * temps = accuTemps.getArrayOfNLastElements(nIteration);
    double * response = new double[4];
    double x0, y0, vx0, vy0;

    // appel de la methode GaussPivot
    gauss(nIteration,angles,x,y,temps,response);

    x0 = response[0];
    y0 = response[1];
    vx0= response[2];
    vy0= response[3];

    std::cout << "--- Resultat du pivot de gauss en param ---" << std::endl;
    std::cout << "X0  : " << x0  << std::endl;
    std::cout << "Y0  : " << y0  << std::endl;
    std::cout << "VX0 : " << vx0 << std::endl;
    std::cout << "VY0 : " << vy0 << std::endl;

    double xD = x0+vx0*ttt;
    double yD = y0+vy0*ttt;
    std::cout << "Position déduite : x:" << xD << " y:" << yD << std::endl;

    /*
    delete [] angles;
    delete [] x;
    delete [] y;
    delete [] temps;
    delete [] response;
    */
}

/*** *************************************** ***/
/*** Definition des methodes de Accumulateur ***/
/*** *************************************** ***/

Accumulateur::Accumulateur(int size) {
    this->setSize(size);
}

void Accumulateur::setSize(int size) {
    this->size = size;
    this->data = new double[this->size];
    this->filling = 0;
}

void Accumulateur::pushData(double data) {
    if(this->filling == this->size) {
        for(int i = this->filling -1 ; i > 0 ; i--) {
            this->data[i] = this->data[i-1];
        }
        this->data[0] = data;
    } else {
        for(int i = this->filling -1 ; i > 0 ; i--) {
            this->data[i] = this->data[i-1];
        }
        this->data[0] = data;
        this->filling++;
    }
}

std::ostream& operator<<( std::ostream &flux, Accumulateur const& accu ) {
    flux << "[Filling : " << accu.getFilling() << "/" << accu.getSize() << " ]\n";
    for(int i = 0 ; i < accu.getFilling() ; i++) {
        flux << i << " > " << accu.getData(i) << "\n";
    }
    for(int i = accu.getFilling() ; i < accu.getSize() ; i++) {
        flux << i << " > " << "_" << "\n";
    }
    flux << "____________________";
    return flux;
}

double Accumulateur::getLast() const {
    if(!this->isEmpty()) {
        return this->getData(this->filling-1);
    } else {
        return -1;
    }
}

bool Accumulateur::isFull() const {
    return this->filling == this->size;
}
bool Accumulateur::isEmpty() const {
    return this->filling == 0;
}

double* Accumulateur::getArrayOfNLastElements(int size) {
    if(size > this->filling) {
        size = this->filling;
    }
    //int index = this->filling-size;
    //    return tab+index;
    double * tab = new double[size];
    for(int i = 0 ; i < size ; i++) {
        tab[i] = this->data[this->filling-size+i];
    }
    return tab;
}
