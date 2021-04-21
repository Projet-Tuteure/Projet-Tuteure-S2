
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
        Tilemap tilemap = new Tilemap(0);
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

                tilemap.display(tilemap.map, Steve.steveIcon, Steve.posX, Steve.posY);
                Steve.deplacement(tilemap);
                Collision.getPiece(tilemap,Steve);
                Collision.getGoldApple(tilemap,Steve);
                try {
                    Thread.sleep(10);
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
                    case UP:    Steve.setNewDirection(Personnage.Direction.HAUT); break;
                    case DOWN:  Steve.setNewDirection(Personnage.Direction.BAS); break;
                    case LEFT:  Steve.setNewDirection(Personnage.Direction.GAUCHE); break;
                    case RIGHT: Steve.setNewDirection(Personnage.Direction.DROITE); break;
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
