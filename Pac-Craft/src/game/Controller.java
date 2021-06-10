package game;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import menu.Launcher;


public class Controller extends Application {
    Group root;
    Launcher launcher;
    Game game;
    int nMap;

    public static void start(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        initWindow(stage);
        setLauncher(stage);
        stage.show();
    }

    /**
     * Initialize window
     * @param stage the parent stage
     */
    public void initWindow(Stage stage){
        stage.setTitle("Pac-Craft");
        stage.getIcons().add(new Image("ressources/img/icon.png"));
        stage.setResizable(false);

        this.root = new Group();
        launcher = new Launcher(this);
        nMap = 0;
    }

    /**
     * Creates a new game on given stage
     * @param stage the parent stage
     */
    public void newGame(Stage stage){
        this.root = new Group();
        game = new Game(this);
        stage.setScene(game.gameScene(stage));
    }

    /**
     * Creates a new game in given stage with given coin number and hp
     * @param stage the parent stage
     * @param coinNumber the coin number
     * @param hp left hp
     */
    public void newGame(Stage stage, int coinNumber, int hp){
        this.nMap += 1;
        this.root = new Group();
        this.game = new Game(this, this.nMap, coinNumber, hp);
        stage.setScene(game.gameScene(stage));
    }

    /**
     * Sets launcher as scene from the given stage
     * @param stage the parent stage
     */
    public void setLauncher(Stage stage) {
        stage.setScene(launcher.getScene(stage));
        this.nMap=0;
    }

    @Override
    public String toString() {
        return "game.Main{" +
                "root=" + root +
                ", launcher=" + launcher +
                ", game=" + game +
                ", nMap=" + nMap +
                '}';
    }
}
