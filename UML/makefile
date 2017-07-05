repC=classes/
repE=classes/etats/
repCmd=classes/commandes/
repA=classes/affichage/
CC=g++ -g -Wall -std=c++11 

ALL : clear Outils Etats Afficheurs Commandes Robot robot.exe

ALLCLEAN : ALL clean

###############################
##           EXEC            ##
###############################
robot.exe : main.cpp Outils Robot Etats Afficheurs Commandes
	$(CC) *.o main.cpp -o robot.exe

###############################
##          OUTILS           ##
###############################
Outils : Plot.o Objet.o Position.o
	@echo "Outils compiled ..."
Plot.o : $(repC)Plot.hpp $(repC)Plot.cpp
	$(CC) $(repC)Plot.cpp -c -o Plot.o
Objet.o : $(repC)Objet.hpp $(repC)Objet.cpp
	$(CC) $(repC)Objet.cpp -c -o Objet.o
Position.o : $(repC)Position.hpp $(repC)Position.cpp
	$(CC) $(repC)Position.cpp -c -o Position.o

###############################
##           ETAT            ##
###############################
Etats : Etat.o EnRoute.o Fige.o AVide.o AVideFacePlot.o EnCharge.o EnChargeFacePlot.o
	@echo "Etat compiled ..."
Etat.o : $(repE)Etat.hpp $(repE)Etat.cpp $(repC)Robot.hpp $(repC)Plot.hpp $(repC)Objet.hpp
	$(CC) $(repE)Etat.cpp -c -o Etat.o
EnRoute.o : $(repE)EnRoute.hpp $(repE)EnRoute.cpp $(repE)Etat.hpp $(repE)Fige.hpp $(repC)Robot.hpp
	$(CC) $(repE)EnRoute.cpp -c -o EnRoute.o
Fige.o : $(repE)Fige.hpp $(repE)Fige.cpp $(repE)Etat.hpp
	$(CC) $(repE)Fige.cpp -c -o Fige.o
AVide.o : $(repE)AVide.hpp $(repE)AVide.cpp $(repE)Etat.hpp
	$(CC) $(repE)AVide.cpp -c -o AVide.o
AVideFacePlot.o : $(repE)AVideFacePlot.hpp $(repE)AVideFacePlot.cpp $(repE)Etat.hpp
	$(CC) $(repE)AVideFacePlot.cpp -c -o AVideFacePlot.o
EnCharge.o : $(repE)EnCharge.hpp $(repE)EnCharge.cpp $(repE)Etat.hpp
	$(CC) $(repE)EnCharge.cpp -c -o EnCharge.o
EnChargeFacePlot.o : $(repE)EnChargeFacePlot.hpp $(repE)EnChargeFacePlot.cpp $(repE)Etat.hpp
	$(CC) $(repE)EnChargeFacePlot.cpp -c -o EnChargeFacePlot.o

###############################
##        AFFICHEUR          ##
###############################
Afficheurs : Afficheur.o AfficheurTexte.o
	@echo "Afficheurs compiled ..."
Afficheur.o : $(repA)Afficheur.hpp $(repA)Afficheur.cpp 
	$(CC) $(repA)Afficheur.cpp -c -o Afficheur.o
	
AfficheurTexte.o : $(repA)AfficheurTexte.hpp $(repA)AfficheurTexte.cpp $(repC)Robot.hpp $(repA)Afficheur.hpp Afficheur.o 
	$(CC) $(repA)AfficheurTexte.cpp -c -o AfficheurTexte.o
	
###############################
##        COMMANDES          ##
###############################
Commandes : Invocateur.o Commande.o UndoCommande.o RedoCommande.o MacroCommande.o CommandeRobot.o AvancerCommande.o TournerCommande.o PoserCommande.o PeserCommande.o SaisirCommande.o RencontrerPlotCommande.o EvaluerPlotCommande.o FigerCommande.o RepartirCommande.o
	@echo "Commandes compiled ..."
Invocateur.o : $(repCmd)Invocateur.hpp $(repCmd)Invocateur.cpp $(repC)Robot.hpp $(repCmd)Commande.hpp
	$(CC) $(repCmd)Invocateur.cpp -c -o Invocateur.o
Commande.o : $(repCmd)Commande.hpp $(repCmd)Commande.cpp
	$(CC) $(repCmd)Commande.cpp -c -o Commande.o
UndoCommande.o : $(repCmd)UndoCommande.hpp $(repCmd)UndoCommande.cpp $(repCmd)Commande.hpp
	$(CC) $(repCmd)UndoCommande.cpp -c -o UndoCommande.o
RedoCommande.o : $(repCmd)RedoCommande.hpp $(repCmd)RedoCommande.cpp $(repCmd)Commande.hpp
	$(CC) $(repCmd)RedoCommande.cpp -c -o RedoCommande.o
MacroCommande.o : $(repCmd)MacroCommande.hpp $(repCmd)MacroCommande.cpp $(repCmd)Commande.hpp
	$(CC) $(repCmd)MacroCommande.cpp -c -o MacroCommande.o
	
CommandeRobot.o : $(repCmd)CommandeRobot.hpp $(repCmd)CommandeRobot.cpp $(repC)Robot.hpp $(repCmd)Commande.cpp $(repCmd)Commande.o
	$(CC) $(repCmd)CommandeRobot.cpp -c -o CommandeRobot.o

AvancerCommande.o : $(repCmd)AvancerCommande.hpp $(repCmd)AvancerCommande.cpp $(repCmd)CommandeRobot.hpp $(repC)Robot.hpp
	$(CC) $(repCmd)AvancerCommande.cpp -c -o AvancerCommande.o
TournerCommande.o : $(repCmd)TournerCommande.hpp $(repCmd)TournerCommande.cpp $(repCmd)CommandeRobot.hpp $(repC)Robot.hpp
	$(CC) $(repCmd)TournerCommande.cpp -c -o TournerCommande.o
PoserCommande.o : $(repCmd)PoserCommande.hpp $(repCmd)PoserCommande.cpp $(repCmd)CommandeRobot.hpp $(repC)Robot.hpp
	$(CC) $(repCmd)PoserCommande.cpp -c -o PoserCommande.o
PeserCommande.o : $(repCmd)PeserCommande.hpp $(repCmd)PeserCommande.cpp $(repCmd)CommandeRobot.hpp $(repC)Robot.hpp
	$(CC) $(repCmd)PeserCommande.cpp -c -o PeserCommande.o
SaisirCommande.o : $(repCmd)SaisirCommande.hpp $(repCmd)SaisirCommande.cpp $(repCmd)CommandeRobot.hpp $(repC)Robot.hpp $(repC)Objet.hpp
	$(CC) $(repCmd)SaisirCommande.cpp -c -o SaisirCommande.o
RencontrerPlotCommande.o : $(repCmd)RencontrerPlotCommande.hpp $(repCmd)RencontrerPlotCommande.cpp $(repCmd)CommandeRobot.hpp $(repC)Robot.hpp $(repC)Objet.hpp
	$(CC) $(repCmd)RencontrerPlotCommande.cpp -c -o RencontrerPlotCommande.o
EvaluerPlotCommande.o : $(repCmd)EvaluerPlotCommande.hpp $(repCmd)EvaluerPlotCommande.cpp $(repCmd)CommandeRobot.hpp $(repC)Robot.hpp $(repC)Objet.hpp
	$(CC) $(repCmd)EvaluerPlotCommande.cpp -c -o EvaluerPlotCommande.o
FigerCommande.o : $(repCmd)FigerCommande.hpp $(repCmd)FigerCommande.cpp $(repCmd)CommandeRobot.hpp $(repC)Robot.hpp $(repC)Objet.hpp
	$(CC) $(repCmd)FigerCommande.cpp -c -o FigerCommande.o
RepartirCommande.o : $(repCmd)RepartirCommande.hpp $(repCmd)RepartirCommande.cpp $(repCmd)CommandeRobot.hpp $(repC)Robot.hpp $(repC)Objet.hpp
	$(CC) $(repCmd)RepartirCommande.cpp -c -o RepartirCommande.o

###############################
##          ROBOT            ##
###############################
Robot : VueRobot.o Robot.o
	@echo "Robot compiled ..."
VueRobot.o : $(repA)VueRobot.hpp $(repA)VueRobot.cpp
	$(CC) $(repA)VueRobot.cpp -c -o VueRobot.o
Robot.o : $(repC)Robot.hpp $(repC)Robot.cpp $(repC)Plot.hpp $(repC)Objet.hpp $(repC)Position.hpp $(repE)Etat.hpp $(repA)VueRobot.hpp VueRobot.o
	$(CC) $(repC)Robot.cpp -c -o Robot.o
	

###############################
##         CLEANING          ##
###############################
clean :
	rm *.o
clear :
	clear
mrproper :
	rm robot.exe
	rm *.o
