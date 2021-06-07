import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.util.Stack;


public class Main extends Application {

    final int PAUSEVALUE = 10;

    boolean continuer = false;
    boolean gameOver;

    @Override
    public void start(Stage stage) throws InterruptedException {
        // Initialisation des variables
        gameOver= false;
        Tilemap tilemap = new Tilemap(0);
        Player Steve = new Player();
        UI ui = new UI(tilemap);

        // Mise en place de la fenetre
        Group root = new Group();

        // Mise en place du stackPane
        StackPane stackPane = new StackPane();
        ObservableList stackPaneChildren = stackPane.getChildren();
        stackPaneChildren.add(root);
        stackPaneChildren.add(ui);

        Scene scene = new Scene(stackPane,tilemap.getNbBlockWidth()*tilemap.getBlockSide(), tilemap.getNbBlockHeight()*tilemap.getBlockSide());
        stage.setTitle("Pac-Craft");
        stage.getIcons().add(new Image("img/icon.png"));
        stage.setResizable(false);
        stage.setScene(scene);

        // Mise en place des éléments graphiques
        StackPane holder = new StackPane();
        holder.setStyle("-fx-background-color: black");
        Canvas canvas = new Canvas(tilemap.getNbBlockWidth()*tilemap.getBlockSide(), tilemap.getNbBlockHeight()*tilemap.getBlockSide());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        holder.getChildren().add(canvas);
        root.getChildren().add(holder);

        // Boucle principale
        final long startNanoTime = System.nanoTime();
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                int j = 0;

                tilemap.display(tilemap.map,gc);
                // Steve.deplacement(tilemap, gc);
                Steve.nextFrame(tilemap, gc, t);
                Collision.getPiece(tilemap,Steve);
                Collision.getGoldApple(tilemap,Steve);

                // Control du nombre de frame par seconde (fps = 1000 / PAUSEVALUE)
                try {
                    Thread.sleep(PAUSEVALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        stage.show();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:    Steve.setNewDirection(Sprite.Direction.HAUT); break;
                    case DOWN:  Steve.setNewDirection(Sprite.Direction.BAS); break;
                    case LEFT:  Steve.setNewDirection(Sprite.Direction.GAUCHE); break;
                    case RIGHT: Steve.setNewDirection(Sprite.Direction.DROITE); break;
                    case ESCAPE: playSound("walking.wav"); break;
                    case P: gameOver = true; break;
                    // case SHIFT: running = true; break;
                }
            }
        });
    }

    public static void playSound(String path) {
        String absolutePath = "/home/hmont/Documents/cours/projet_tuto/S2/Projet-Tuteure-S2/Pac-Craft/src/Sons/";
        String filePath = absolutePath + path;
        Media sound = new Media(new File(filePath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
