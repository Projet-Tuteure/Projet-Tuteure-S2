import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
    Group root;
    Launcher launcher;
    Partie partie;
    int nMap;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        initFenetre(stage);
        setLauncher(stage);
        stage.show();
    }

    public void initFenetre(Stage stage){
        stage.setTitle("Pac-Craft");
        stage.getIcons().add(new Image("img/icon.png"));
        stage.setResizable(false);

        root = new Group();
        launcher = new Launcher(this);
        nMap = 0;
    }

    public void newGame(Stage stage){
        root = new Group();
        partie = new Partie(this);
        stage.setScene(partie.sceneJeu(stage));
    }

    public void newGame(Stage stage, int nbPiece, int hp){
        this.nMap+=1;
        root = new Group();
        partie = new Partie(this, this.nMap, nbPiece, hp);
        stage.setScene(partie.sceneJeu(stage));
    }

    public void setLauncher(Stage stage) {
        stage.setScene(launcher.getScene(stage));
    }
}
