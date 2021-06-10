# Comment installer le projet

Afin de pouvoir faire tourner le jeu PAC-CRAFT, suivez les étapes suivantes.

## JavaFX

Si la version de java est supérieur à 10, téléchargez le sdk de javaFX disponible ici : https://gluonhq.com/products/javafx/

## Modules de tests

Installez JUNIT4 et Mockito si besoin de faire tourner les tests.
  
## Compiler le jeu (en se plaçant dans le dossier src/)
`javac --module-path /chemin/javafx/sdk/lib --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.media Main.java game/*.java menu/*.java character/*.java map/*.java`

## Executer le jeu (en se plaçant dans le dossier src/)

`java --module-path /chemin/javafx/sdk/lib --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.media Main`

# Bon jeu !



