import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
    Group root;
    Launcher launcher;
    Game game;
    int nMap;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        initWindow(stage);
        setLauncher(stage);
        stage.show();
    }

    /**
     * initialize window
     * @param stage the parent stage
     */
    public void initWindow(Stage stage){
        stage.setTitle("Pac-Craft");
        stage.getIcons().add(new Image("img/icon.png"));
        stage.setResizable(false);

        root = new Group();
        launcher = new Launcher(this);
        nMap = 0;
    }

    /**
     * Creates a new game on given stage
     * @param stage the parent stage
     */
    public void newGame(Stage stage){
        root = new Group();
        game = new Game(this);
        stage.setScene(game.gameScene(stage));
    }

    /**
     * Creates a new gamein given stage with given coin number and hp
     * @param stage
     * @param coinNumber
     * @param hp
     */
    public void newGame(Stage stage, int coinNumber, int hp){
        this.nMap+=1;
        root = new Group();
        game = new Game(this, this.nMap, coinNumber, hp);
        stage.setScene(game.gameScene(stage));
    }

    /**
     * Sets launcher as scene from the given stage
     * @param stage the parent stage
     */
    public void setLauncher(Stage stage) {
        stage.setScene(launcher.getScene(stage));
    }
}
