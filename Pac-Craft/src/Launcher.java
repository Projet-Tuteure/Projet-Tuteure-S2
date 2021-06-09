import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Launcher {
    private Scene mainMenu;
    private final Main main;
    private static MediaPlayer musicPlayer;

    /**
     * creates a launcher with given main
     * @param main the window to launch
     */
    public Launcher(Main main){
        this.main = main;
        musicPlayer= Sound.getPlayer("Sweden");
        musicPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                musicPlayer.seek(new Duration(0));
            }
        });
        musicPlayer.setStartTime(new Duration(1000*15));
        musicPlayer.setVolume(1);
    }

    /**
     * Generates the launcher scene
     * @param stage the parent stage
     * @return the generated scene
     */
    public Scene getScene(Stage stage) {
        if(!musicPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)){
            musicPlayer.play();
        }

        final int WIDTH = 840;
        final int HEIGHT = 520;

        Image title = new Image("/img/title1.png");
        ImageView titleView = new ImageView(title);

        Button play = new Button("Play");
        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                musicPlayer.stop();
                main.newGame(stage);
            }
        });

        Button quitter = new Button("Quit");
        quitter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });

        play.getStyleClass().add("button");
        quitter.getStyleClass().add("button");

        VBox titleBox = new VBox(titleView);
        titleBox.getStyleClass().add("title-box");

        VBox menuBox = new VBox(titleBox,play,quitter);
        menuBox.setSpacing(HEIGHT/21);
        menuBox.setPadding(new Insets(HEIGHT/7));
        menuBox.getStyleClass().add("main-box");
        BackgroundImage bgimg = new BackgroundImage(
                new Image("img/background.jpg",WIDTH,HEIGHT,false,false),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        );
        VBox mainBox = new VBox(menuBox);
        mainBox.setBackground(new Background(bgimg));

        mainMenu = new Scene(mainBox, WIDTH, HEIGHT);

        Font.loadFont( Main.class.getClassLoader().getResourceAsStream( "fonts/Minecraft.ttf"), 20);
        mainMenu.getStylesheets().add("styles/style.css");

        return mainMenu;
    }
}