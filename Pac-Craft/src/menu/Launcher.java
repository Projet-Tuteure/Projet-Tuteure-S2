package menu;

import game.Controller;
import game.Sound;
import javafx.application.Platform;
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
    private final Controller controller;
    private static MediaPlayer musicPlayer;

    /**
     * creates a launcher with given main
     * @param controller the window to launch
     */
    public Launcher(Controller controller){
        this.controller = controller;
        musicPlayer = Sound.getPlayer("Sweden");
        musicPlayer.setOnEndOfMedia(() -> musicPlayer.seek(new Duration(0)));
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

        Image title = new Image("/ressources/img/title1.png");
        ImageView titleView = new ImageView(title);

        Button play = new Button("Play");
        play.setOnAction(event -> {
            musicPlayer.stop();
            controller.newGame(stage);
        });

        Button quitter = new Button("Quit");
        quitter.setOnAction(event -> Platform.exit());

        play.getStyleClass().add("button");
        quitter.getStyleClass().add("button");

        VBox titleBox = new VBox(titleView);
        titleBox.getStyleClass().add("title-box");

        VBox menuBox = new VBox(titleBox,play,quitter);
        menuBox.setSpacing(24);
        menuBox.setPadding(new Insets(74));
        menuBox.getStyleClass().add("main-box");
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("ressources/img/background.jpg",WIDTH,HEIGHT,false,false),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        );
        VBox mainBox = new VBox(menuBox);
        mainBox.setBackground(new Background(backgroundImage));

        mainMenu = new Scene(mainBox, WIDTH, HEIGHT);

        Font.loadFont( Controller.class.getClassLoader().getResourceAsStream("ressources/fonts/Minecraft.ttf"), 20);
        mainMenu.getStylesheets().add("ressources/styles/style.css");

        return mainMenu;
    }

    @Override
    public String toString() {
        return "menu.Launcher{" +
                "mainMenu=" + mainMenu +
                ", main=" + controller +
                '}';
    }
}