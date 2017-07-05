# Drone Express

L'objectif de ce projet est de pouvoir fournir une architecture de livraison par drones.

## Pré-requis

Drone Express nécessite d'avoir deux plateformes de déploiement servicemix et tomcat.

## Déploiement sous Unix

Modifier le fichier build.sh afin que la variable path_to_servicemix pointe vers le dossier deploy/ de service mix.

Aller dans le dossier droneexpress-optimizer et lancer la commande mvn tomee:run

Enfin afin d'afficher l'interface graphique, aller dans le dossier droneexpress-flightmonitor puis lancer mvn exec:java

## Client lourd

Après avoir déployé les services (surtout flightplan et warehouses), aller dans le dossier droneexpress-flightmonitor puis executer la commande
mvn exec:java
