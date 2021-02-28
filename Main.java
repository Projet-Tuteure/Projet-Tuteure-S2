package sample;
// https://www.reddit.com/r/javahelp/comments/84izvw/javafx_problem_refreshing_scene_when_circles_move/

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class Main extends Application {

    int[][] niveau1 = {
            {1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,1,0,0,0,0,1},
            {1,0,1,1,0,1,0,1,1,0,1},
            {1,0,1,0,0,0,0,0,1,0,1},
            {1,0,1,0,1,1,1,0,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,0,1,1,1,0,1,0,1},
            {1,0,1,0,0,0,0,0,1,0,1},
            {1,0,1,1,0,1,0,1,1,0,1},
            {1,0,0,0,0,1,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1},
    };

    int[][] niveau2 = {
            {1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,1,0,0,0,0,1,1},
            {1,0,1,1,0,1,0,1,1,0,1,1},
            {1,0,1,0,0,0,0,0,1,0,1,1},
            {1,0,1,0,1,1,1,0,1,0,1,1},
            {1,0,0,0,0,0,0,0,0,0,1,1},
            {1,0,1,0,1,1,1,0,1,0,1,1},
            {1,0,1,0,0,0,0,0,1,0,1,1},
            {1,0,1,1,0,1,0,1,1,0,1,1},
            {1,0,0,0,0,1,0,0,0,0,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,0}
    };

    boolean haut = false;
    boolean bas = false;
    boolean gauche = false;
    boolean droite = false;
    boolean continuer = false;
    boolean gameOver;

    @Override
    public void start(Stage stage) throws InterruptedException {
        gameOver= false;
        // Tilemap tilemap = new Tilemap(niveau1, niveau1[0].length, niveau1.length);
        Tilemap tilemap = new Tilemap(niveau2, niveau2[0].length, niveau2.length);
        Personnage Steve = new Personnage();
        //Setting the Scene object
        Group root = new Group(tilemap.getCanvas());
        Scene scene = new Scene(root, tilemap.getNbBlockWidth()*tilemap.getBlockSide(), tilemap.getNbBlockHeight()*tilemap.getBlockSide());
        stage.setTitle("PACRAFT");
        stage.getIcons().add(new Image("sample/img/icon.png"));
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

                Collision.collide(Steve.pos_x, Steve.pos_y, tilemap,j,Steve.getHeight());
                Steve.deplacement(gauche, droite, haut, bas);
                tilemap.display(tilemap.map, Steve.steveIcon, Steve.pos_x, Steve.pos_y);
            }
        }.start();

        stage.show();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:    Steve.pos_y-=10; break;
                    case DOWN:  Steve.pos_y+=10; break;
                    case LEFT:  Steve.pos_x-=10; break;
                    case RIGHT: Steve.pos_x+=10; break;
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
