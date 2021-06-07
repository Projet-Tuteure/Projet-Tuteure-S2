import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;


public class Main extends Application {
    Group root;
    Launcher launcher;
    Partie partie;

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
        root = new Group();
        launcher = new Launcher(this);
    }

    public void newGame(Stage stage){
        root = new Group();
        partie = new Partie(this);
        stage.setScene(partie.sceneJeu(stage));
    }

    public void setLauncher(Stage stage) {
        stage.setScene(launcher.getScene(stage));
    }
}
