#include "corps.h"
#include "vector3d.h"
#include <iostream>
#include <cmath>

std::list<Corps*> Corps::corps_list;

void Corps::ajouterDansListe(Corps * corps) {
    Corps::corps_list.push_back(corps);
    std::cout << "Element ajouté dans la liste. Nous avons maintenant " << Corps::corps_list.size() << " element" << std::endl;
}

Corps::Corps() {
    masse = 0;
}

void Corps::animateALL(double timelapse) {
    for (std::list<Corps*>::iterator it=(Corps::corps_list.begin()); it!=(Corps::corps_list.end()); it++) {
        (*it)->doSpecial(timelapse);
    }
    for (std::list<Corps*>::iterator it=(Corps::corps_list.begin()); it!=(Corps::corps_list.end()); it++) {
        (*it)->calculateAcceleration();
    }
    for (std::list<Corps*>::iterator it=(Corps::corps_list.begin()); it!=(Corps::corps_list.end()); it++) {
        (*it)->calculateNextVitesse(timelapse);
    }
    for (std::list<Corps*>::iterator it=(Corps::corps_list.begin()); it!=(Corps::corps_list.end()); it++) {
        (*it)->calculateNextPosition(timelapse);
    }
    for (std::list<Corps*>::iterator it=(Corps::corps_list.begin()); it!=(Corps::corps_list.end()); it++) {
        (*it)->updateVitesse();
    }
    for (std::list<Corps*>::iterator it=(Corps::corps_list.begin()); it!=(Corps::corps_list.end()); it++) {
        (*it)->updatePosition();
    }
    for (std::list<Corps*>::iterator it=(Corps::corps_list.begin()); it!=(Corps::corps_list.end()); it++) {
        (*it)->resetAcceleration();
    }
}

double Corps::vitesseOrbitale(double rayon) {
    return sqrt(masse*0.0000000000667384/rayon);
}

void Corps::calculateAcceleration() {
    for (std::list<Corps*>::iterator it=(Corps::corps_list.begin()); it!=(Corps::corps_list.end()); it++) {
        if(this == (*it)) break;
        double G = 6.6738480*pow(10,-11);
        Vector3D vd = (*it)->position - position;
        double distance = vd.length();
        double force = G * (*it)->masse*masse / pow(distance,2);
        Vector3D va = vd.normalize();
        va *= (force/masse);
        acceleration += va;
    }
}

void Corps::dessinerALL() {
    for (std::list<Corps*>::iterator it=(Corps::corps_list.begin()); it!=(Corps::corps_list.end()); it++) {
        (*it)->dessiner();
    }
}

void Corps::dessiner() {
    glTranslated(this->position.X/1000,this->position.Y/1000,this->position.Z/1000);

    glColor3ub(255,0,0);
    GLUquadric* params = gluNewQuadric();
    gluQuadricDrawStyle(params,GLU_LINE);
    gluSphere(params,1,15,15);
    gluDeleteQuadric(params);

    glTranslated(-this->position.X/1000,-this->position.Y/1000,-this->position.Z/1000);
}

Triplet Corps::intersectALL(Corps * theCorps) {
    Triplet triplet(100000000, NULL,Vector3D(0,0,0));
    for (std::list<Corps*>::iterator it=(Corps::corps_list.begin()); it!=(Corps::corps_list.end()); it++) {
        if(theCorps != (*it)) {
            Triplet tmpD = (*it)->intersect(theCorps);
            if(tmpD.time >= 0 && tmpD.time < triplet.time) {
                triplet = tmpD;
            }
        }
    }
    return triplet;
}

double Corps::goodDirection(Vector3D start, Vector3D final, Vector3D impact) {
    if(impact.X >= std::min(start.X, final.X)
            && impact.X <= std::max(start.X, final.X)
            && impact.Y >= std::min(start.Y, final.Y)
            && impact.Y <= std::max(start.Y, final.Y)
            && impact.Z >= std::min(start.Z, final.Z)
            && impact.Z <= std::max(start.Z, final.Z)) {
        return (impact - start).length();
    } else {
        return (final - start).length();
    }
}


double Corps::getBoundingRay() {
    return 0;
}

Vector3D milieu(Vector3D A, Vector3D B) {
    return (A+B)/2;
}

Triplet Corps::intersect(Corps * theCorps) {

    double Ra = this->getBoundingRay();
    double Rb = theCorps->getBoundingRay();
    double Ax = this->vitesse.X - theCorps->vitesse.X;
    double Ay = this->vitesse.Y - theCorps->vitesse.Y;
    double Az = this->vitesse.Z - theCorps->vitesse.Z;
    double Bx = this->position.X - theCorps->position.X;
    double By = this->position.Y - theCorps->position.Y;
    double Bz = this->position.Z - theCorps->position.Z;

    double a = Ax*Ax + Ay*Ay + Az*Az;
    double b = 2*Ax*Bx + 2*Ay*By + 2*Az*Bz;
    double c = Bx*Bx + By*By + Bz*Bz - std::pow(Ra+Rb,2);

    std::pair<double,double> iT = resolve2nd(a,b,c); // it : impactsTimes
    if(iT.first < 0 && iT.second < 0) {
        return Triplet(-1,NULL,Vector3D());
    } else if(iT.first < 0) {
        return Triplet(iT.second,
                       this,
                       milieu(this->position + this->vitesse*iT.second,
                              theCorps->position + theCorps->vitesse*iT.second));
    } else if(iT.second < 0) {
        return Triplet(iT.first,
                       this,
                       milieu(this->position + this->vitesse*iT.first,
                              theCorps->position + theCorps->vitesse*iT.first));
    } else {
        double temps = std::min(iT.first, iT.second);
        return Triplet(temps,
                       this,
                       milieu(this->position + this->vitesse*temps,
                              theCorps->position + theCorps->vitesse*temps));
    }
}

std::pair<double, double> Corps::resolve2nd(double a, double b, double c) {
    double delta = std::pow(b,2) - 4*a*c;
    if(delta < 0) {
        return std::make_pair<double,double>(-1,-1);
    } else if(delta > 0) {
        double t1 = (b-std::sqrt(delta))/(2*a);
        double t2 = (b+std::sqrt(delta))/(2*a);
        return std::make_pair<double,double>(t1,t2);
    } else {
        double t0 = b/(2*a);
        return std::make_pair<double,double>(t0,-1);
    }
}

Vector3D Corps::calculVectorRebond(Vector3D incident, Vector3D normale) {
      Vector3D rebond;
      double pscal = (incident.X*normale.X +  incident.Y*normale.Y +  incident.Z*normale.Z);
      rebond.X = incident.X -2*pscal*normale.X;
      rebond.Y = incident.Y -2*pscal*normale.Y;
      rebond.Z = incident.Z -2*pscal*normale.Z;
      return rebond;
}

void Corps::doRebound(Triplet triplet) {
    Vector3D incident = this->vitesse.normalize();
    Vector3D normale = this->position - triplet.impact;
    normale = normale.normalize();
    this->vitesse = calculVectorRebond(incident, normale) * this->vitesse.length();
}

void Corps::doSpecial(double timelapse) {
}
