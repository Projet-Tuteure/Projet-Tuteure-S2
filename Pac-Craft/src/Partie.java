import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Partie {
    private final Main main;
    final int PAUSEVALUE = 10;

    boolean gameOver;
    boolean paused;
    AnimationTimer animationTimer;
    Tilemap tilemap;
    UI ui;
    Player steve;

    public Partie(Main main) {
        this.main = main;
        this.gameOver = false;
        this.paused = false;
        this.tilemap = new Tilemap(0);
        this.ui = new UI(tilemap);
        this.steve = new Player(ui);
    }

    public Scene sceneJeu(Stage stage) {
        // Mise en place du stackPane
        StackPane stackPane = new StackPane();
        ObservableList stackPaneChildren = stackPane.getChildren();
        stackPaneChildren.add(main.root);
        stackPaneChildren.add(ui);

        Scene scene = new Scene(stackPane, tilemap.getNbBlockWidth() * tilemap.getBlockSide(), tilemap.getNbBlockHeight() * tilemap.getBlockSide());
        stage.setTitle("Pac-Craft");
        stage.getIcons().add(new Image("img/icon.png"));
        stage.setResizable(false);
        stage.setScene(scene);

        // Mise en place des éléments graphiques
        StackPane holder = new StackPane();
        holder.setStyle("-fx-background-color: black");
        Canvas canvas = new Canvas(tilemap.getNbBlockWidth() * tilemap.getBlockSide(), tilemap.getNbBlockHeight() * tilemap.getBlockSide());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        holder.getChildren().add(canvas);
        main.root.getChildren().add(holder);

        // Boucle principale
        final long startNanoTime = System.nanoTime();
        this.animationTimer = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                int j = 0;

                if (!paused) {
                    tilemap.display(tilemap.map, gc);
                    steve.nextFrame(tilemap, gc, t);
                    Collision.getPiece(tilemap, steve);
                    Collision.getGoldApple(tilemap, steve);
                }

                // Control du nombre de frame par seconde (fps = 1000 / PAUSEVALUE)
                try {
                    Thread.sleep(PAUSEVALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        animationTimer.start();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        steve.setNewDirection(Sprite.Direction.HAUT);
                        break;
                    case DOWN:
                        steve.setNewDirection(Sprite.Direction.BAS);
                        break;
                    case LEFT:
                        steve.setNewDirection(Sprite.Direction.GAUCHE);
                        break;
                    case RIGHT:
                        steve.setNewDirection(Sprite.Direction.DROITE);
                        break;
                    case ESCAPE:
                        PauseMenu(stage, stackPaneChildren, tilemap);
                        break;
                    case P:
                        gameOver=true;
                        break;
                }
            }
        });

        return scene;
    }

    void PauseMenu(Stage stage, ObservableList stackChildren, Tilemap tilemap) {
        if (paused) return;
        paused=true;

        Text pauseTitle = new Text("Pac-Craft");
        Font minecraftFont = Font.loadFont(Main.class.getClassLoader().getResourceAsStream("fonts/Minecraft.ttf"), 20);
        pauseTitle.setFont(minecraftFont);
        pauseTitle.setFill(Color.WHITE);
        pauseTitle.setStyle("-fx-font-size: 30px;");

        VBox pauseBox = new VBox(pauseTitle);

        pauseBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6);");
        pauseBox.setAlignment(Pos.CENTER);
        pauseBox.setPadding(new Insets(100, 100, 100, 100));

        pauseBox.setMinWidth(tilemap.getNbBlockWidth() * tilemap.getBlockSide());
        pauseBox.setMaxWidth(tilemap.getNbBlockWidth() * tilemap.getBlockSide());
        pauseBox.setMinHeight(tilemap.getNbBlockHeight() * tilemap.getBlockSide());
        pauseBox.setMaxHeight(tilemap.getNbBlockHeight() * tilemap.getBlockSide());

        Button play = new Button("Play");
        play.setOnAction(event -> {
            paused=false;
            stackChildren.remove(pauseBox);
        });

        Button menu = new Button("Menu principal");
        menu.setOnAction(event -> {
            paused=false;
            animationTimer.stop();
            main.setLauncher(stage);
        });

        play.getStyleClass().add("button");
        menu.getStyleClass().add("button");

        pauseBox.getChildren().add(play);
        pauseBox.getChildren().add(menu);
        pauseBox.setSpacing(7);

        pauseBox.getStylesheets().add("styles/style.css");

        stackChildren.add(pauseBox);
    }
}