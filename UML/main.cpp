#include <iostream>

#include "classes/Plot.hpp"
#include "classes/Objet.hpp"
#include "classes/Robot.hpp"
#include "classes/affichage/Afficheur.hpp"
#include "classes/affichage/AfficheurTexte.hpp"
#include "classes/commandes/Invocateur.hpp"

using namespace std;

int main() {
	cout << endl << "=== DEBUT DU PROGRAMME DE TEST ===" << endl;
		
	//Initialisation des objets
	Robot robot;
	Plot plot(5);
	Objet objet(3);
	AfficheurTexte * affT = new AfficheurTexte();
	//affT->setRobot(&robot);
	robot.attacherAfficheur(affT);

	// Preparation de commande
	Invocateur invoc(&robot);
	invoc.lireCommandes();
	
	robot.detacherAfficheur(affT);
	
	cout << "=== FIN DU PROGRAMME DE TEST ===" << endl << endl;
	
	// Nettoyage : afficheurTexte
	delete affT;
	
	return 0;
}
