#ifndef VECTOR3D_H
#define VECTOR3D_H

/* Implémentation intuitive d'une classe Vector3D pour les lecteurs du cours
d'OpenGL,débutants en C++. Il est évident qu'une approche template est à
privilégier pour favoriser la réutilisabilité et l'inlinisme. */

#include <iostream>

class Vector3D
{
public:
    double X;
    double Y;
    double Z;

    Vector3D();
    Vector3D(double x,double y,double z);
    Vector3D(const Vector3D & v);
    Vector3D(const Vector3D & from,const Vector3D & to);

    Vector3D & operator= (const Vector3D & v);

    Vector3D & operator+= (const Vector3D & v);
    Vector3D operator+ (const Vector3D & v) const;

    Vector3D & operator-= (const Vector3D & v);
    Vector3D operator- (const Vector3D & v) const;

    Vector3D & operator*= (const double a);
    Vector3D operator* (const double a)const;
    friend Vector3D operator* (const double a,const Vector3D & v);

    Vector3D & operator/= (const double a);
    Vector3D operator/ (const double a)const;

    Vector3D crossProduct(const Vector3D & v)const;
    double length()const;
    Vector3D & normalize();
    friend std::ostream& operator<<( std::ostream &flux, Vector3D const& vector )
    {
        flux << "[" << vector.X << ", " << vector.Y << ", " << vector.Z << "]";
        return flux;
    }

    Vector3D rotation(const Vector3D n, double angle) const;
    double scalar(const Vector3D n) const;
};

#endif //VECTOR3D_H
