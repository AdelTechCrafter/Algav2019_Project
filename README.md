# Algav2019_Project
Algorithmic Project about minimum-area enclosing rectangle(Toussaint) and  Circle (Ritter)


## Content
### src.algorithms:
#### Algorithmes.java : Cercle minimum (Ritter) ,Enveloppe convexe(QuickHull), Rectangle minimum(Toussaint) 
Attention: Pour Rectangle minimum l'algorithme de Toussaint se trouve dans RotatingCalipers.java

#### Stats.java: 
calcul des temps moyens d'exécutions des algorithmes de Ritter et Toussaint.
calcul de la moyenne des qualités des algorithmes de Ritter(cercle minimum) et Toussaint(Rectangle minimum) selon la formule:
qualite=(aire conteneur/aire polygoneConvexe) − 100%

fonctions qualityRectangleSamples(),qualityRectangleSamples(), timeCircleSamples() et timeRectangleSamples() 
pour associer les qualités/temps d'executions des algorithmes de Ritter et  de Toussaint aux instances de la base de test de Steven Varoumas

### src.tests
Utilisation de JfreeChart pour visualiser les différences de qualité et de temps d'exécution de Cercle minimum et Rectangle minimum 
sur les instances de tests de Steven Varoumas.

#### TestQuality.java:
génère de fichier results/QualityResults.jpeg : comparaisons des qualités
#### TestTime.java:
génère de fichier results/TimeResults.jpeg : comparaisons des temps d'exécutions

