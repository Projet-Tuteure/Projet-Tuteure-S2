# Comment installer et jouer au jeu

Afin de pouvoir faire tourner le jeu PAC-CRAFT, suivez les étapes suivantes.

## JavaFX

Si la version de java est supérieur à 10, téléchargez le sdk de javaFX disponible ici : https://gluonhq.com/products/javafx/

## Modules de tests

JUnit4 et Mockito sont nécessaires dans le cas où l'on voudrait éxecuter les tests unitaires.
  
## Compiler le jeu (en se plaçant dans le dossier src/ en y indiquant le chemin du SDK)
`javac --module-path /CHEMIN/JAVAFX/SDK/lib --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.media Main.java game/*.java menu/*.java character/*.java map/*.java`

## Executer le jeu (en se plaçant dans le dossier src/ en y indiquant le chemin du SDK)

`java --module-path /CHEMIN/JAVAFX/SDK/lib --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.media Main`

# Bon jeu !



