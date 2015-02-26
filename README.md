Projet JAVA - EPF TIC P2016
=======

###auteurs : Vincent Baud, William Bruneau, Théo Poulain

## Fonctionnalités implémentées :


* Création de tournois avec choix du nom du tournois, du nombre d'équipe et du type de tournois.

* 2 types de tournois :
	* Tournois avec phase de poule :
		* Création de poules allant de 3 à 5 équipes suivant les cas (exemple : 6 équipes = 2 poules de 3, 9 équipes = 1 poule de 4 et 1 poule de 5)
		* A la fin de la phase de poule, les 2 premières équipes de chaque poule sont qualifiées et passent en phase d'élimination directe.
	* Tournois en élimination directe :
		* Pour chaque tour, s'il y a un nombre d'équipe impaire, une équipe est préqualifiée. Un algorithme empêche une même équipe d'être préqualifiée deux fois de suite.
		
* Les équipes ont plusieurs paramètres :
	* un nom, un nombre de joueurs, et une description.
	* Ces paramètres sont modifiables à tout moment.
	* Il est également possible de consulter les __statistiques__ d'une équipe (nombre de but, matchs joués...).

* 2 versions de l'application sont implémentés : 
	* une version __console__.
	* une version __JavaFX__. 
	
* Une batterie de test est implémenté à l'aide de __JUnit__.

* Un __web service__ est également implémenté permettant d'accéder au travers d'une interface JavaFX au gestionnaire de tournois(non présent sur GitHub).
