import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Launcher {
    private Scene mainMenu;
    private final Main main;

    public Launcher(Main main){
        this.main = main;
    }

    public Scene getScene(Stage stage) {
        final int WIDTH = 840;
        final int HEIGHT = 520;

        Image title = new Image("/img/title1.png");
        ImageView titleView = new ImageView(title);

        Button play = new Button("Jouer");
        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.newGame(stage);
            }
        });

        Button quitter = new Button("Quitter");
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