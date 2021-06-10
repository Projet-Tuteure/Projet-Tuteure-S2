# Projet-Tuteuré-S2

## Organisation du repository

* 1 dossier ressources avec tous les documents .pdf et le rapport
* 1 dossier Pac-Craft contenant le projet Intellij IDEA avec toutes les classes du jeu

## Membres du groupe

* Henry MONT (Chef de projet)
* Aurélien VALOGNE
* Laurent NGETH
* Thomas ROBERT
* Tristan DURET
* Léo CARREZ

## Installation du projet sous intelliJ IDEA

1. Télécharger le javaFX SDK : https://gluonhq.com/products/javafx/ (version 11, adapté à votre système d'exploitation)

2. L'extraire dans un dossier significatif

3. Lancer IntelliJ IDEA

4. Installer le plugin javaFX

   1. Effectuer Ctrl+Alt+S -> Plugins -> Installed
   2. Vérifier que JavaFX est "enabled"

5. Créer un nouveau projet

   1. Sélectionner JavaFX
   2. Project SDK : 11 (télécharger si nécessaire)
   3. Suivant
   4. Project name : Pac-Craft

6. Ajouter la librairie JavaFX

   1. Effectuer Ctrl+Shift+Alt+S -> Librarie -> + -> Java
   2. Sélectionner le chemin vers le dossier /lib de la librairie téléchargé précédement
   3. Sélectionner "ok" dans les pop-up suivants

7. Ajouter les VM options

   1. cliquer sur run -> edit configurations -> modify options -> Add VM options

   2. Dans les VM options, taper :

      1. ```shell
         --module-path /chemin/vers/javafx/sdk/lib --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.media
         ```

8. 

