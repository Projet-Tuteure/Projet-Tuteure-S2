# Comment installer le projet

Afin de pouvoir faire tourner le jeu PAC-CRAFT, suivez les étapes suivantes.

## JavaFX

Si java supérieur à java 10, 
- Téléchargez le sdk dispoible ici : https://gluonhq.com/products/javafx/

## Modules de tests

Installer JUNIT4 et Mockito si besoin de faire tourner les tests.

## Faire tourner sous intellij IDEA
Importer dans les librairies le sdk de javafx, junit, mockito.
Si version supérieur à java10, créer une run configuration avec :
  VM option : 
`--module-path /path/to/JAVAFX/SDK/lib --add-modules javafx.controls,javafx.fxml,javafx.media,javafx.graphics`
  Working directory : se placer dans le dossier src/
  
## Compiler et éxecuter à la main (en se plaçant dans le dossier src/)
`javac --module-path /chemin/javafx/sdk/lib --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.media Main.java game/*.java menu/*.java character/*.java map/*.java`
`java --module-path /chemin/javafx/sdk/lib --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.media Main`

# Bon jeu !



