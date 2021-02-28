# Projet-Tuteure-S2

## Structure du jeu

```java
CONST boolean gauche = false;  
CONST boolean droite = false;  
CONST boolean haut = false;  
CONST boolean bas = false;  
CONST boolean mouse = false;

fonction getInput():  
  Si flecheGauche:  
    gauche=true;  
  Sinon :  
    gauche=false;  
  ...  

Tant que fenêtre ouverte (boucle principale) :  
  Afficher interface utilisateur  
  Si bouton "start" :  
    Partie nouvellePartie = new Partie();  
    Tant que nouvellePartie.status()!="gameOver":  
      getInput();  
      nouvellePartie.refresh(gauche,droite,haut,bas);  
    afficher "Game Over"+bouton menu  
    COUCOU c'est aurélien
```
