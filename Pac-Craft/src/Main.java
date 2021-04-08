
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class Main extends Application {

    boolean haut = false;
    boolean bas = false;
    boolean gauche = false;
    boolean droite = false;
    boolean continuer = false;
    boolean gameOver;

    @Override
    public void start(Stage stage) throws InterruptedException {
        gameOver= false;
        // Tilemap tilemap = new Tilemap(0);
        Tilemap tilemap = new Tilemap(1);
        Personnage Steve = new Personnage();
        //Setting the Scene object
        Group root = new Group(tilemap.getCanvas());
        Scene scene = new Scene(root, tilemap.getNbBlockWidth()*tilemap.getBlockSide(), tilemap.getNbBlockHeight()*tilemap.getBlockSide());
        stage.setTitle("Pac-Craft");
        stage.getIcons().add(new Image("img/icon.png"));
        stage.setResizable(false);
        stage.setScene(scene);

        // mainLoop(Steve,tilemap);

        final long startNanoTime = System.nanoTime();

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                int j = 0;

                tilemap.display(tilemap.map, Steve.steveIcon, Steve.pos_x, Steve.pos_y);
                Steve.deplacement(gauche, droite, haut, bas);
            }
        }.start();

        stage.show();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:    Collision.collide(Steve.pos_x, Steve.pos_y,tilemap,2,Steve); break;
                    case DOWN:  Collision.collide(Steve.pos_x, Steve.pos_y,tilemap,3,Steve); break;
                    case LEFT:  Collision.collide(Steve.pos_x, Steve.pos_y,tilemap,0,Steve); break;
                    case RIGHT: Collision.collide(Steve.pos_x, Steve.pos_y,tilemap,1,Steve); break;
                    case P: gameOver = true; break;
                    // case SHIFT: running = true; break;
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}