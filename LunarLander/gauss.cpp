#include "gauss.h"
#include "scene.h"

#include <iostream>

using namespace std;

double* gauss(int n,double angles[], double posx[], double posy[], double inst[], double* s){
  double e[n+1][n];
  for (int i=0;i<n;i++){
    e[0][i] = sin(angles[i]);
    e[1][i] = -cos(angles[i]);
    e[2][i] = sin(angles[i])*inst[i];
    e[3][i] = -cos(angles[i])*inst[i];
    e[4][i] = -cos(angles[i])*posy[i] + sin(angles[i])*posx[i];
  }
  int y=0;
  double temp;
  int a,t;
  for(int x=0;x<n-1;x++){
    for(a=1+x;a<n;a++){
      temp=e[x][a];
      for (t=x;t<n+1;t++){
        e[t][a]=e[t][a]*e[x][x]-e[t][x]*temp;  /// triangulation du systeme.
      }
    }
  }
  // maintenant on a un système triangulaire
  //Passons au remplacage..
  int af;
  int g;
  s[n-1]=e[n][n-1]/e[n-1][n-1];
  e[n][n-1]=0;
  e[n-1][n-1]=0;

  for (int ligne=1;ligne<=n;ligne++){
    for (int sol=2;sol<=n;sol++){
      e[n-ligne][n-sol]*=s[n-ligne];
      e[n][n-sol]-=e[n-ligne][n-sol];
      e[n-ligne][n-sol]=0;
    }
    s[n-(ligne+1)]=e[n][n-(ligne+1)]/e[n-(ligne+1)][n-(ligne+1)];

  }

  double rectif = (((inst[0]-inst[3])/3)/((double)MULT_TIME/(double)FPS));

  s[0] += (inst[0]-inst[3])/3*s[2]/rectif;
  s[1] += (inst[0]-inst[3])/3*s[3]/rectif;

  // Resolution.
  return s;
}

