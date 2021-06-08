import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Partie {
    private final Main main;
    final int PAUSEVALUE = 10;

    boolean gameOver;
    boolean paused;
    AnimationTimer animationTimer;
    Tilemap tilemap;
    UI ui;
    Player player;
    Zombie zombie1;
    Zombie zombie2;
    Zombie zombie3;
    Zombie zombie4;
    ArrayList<Zombie> zombieArrayList;

    public Partie(Main main) {
        this.main = main;
        this.gameOver = false;
        this.paused = false;
        this.tilemap = new Tilemap(0);
        this.ui = new UI(tilemap);
        this.player = new Player(ui);
        zombie1 = new Zombie(false);
        zombie2 = new Zombie(false);
        zombie3 = new Zombie(false);
        zombie4 = new Zombie(false);
        zombieArrayList = new ArrayList<>();
        zombieArrayList.add(zombie1);
        zombieArrayList.add(zombie2);
        zombieArrayList.add(zombie3);
        zombieArrayList.add(zombie4);
    }

    public Partie(Main main, int nMap, int nbPiece, int hp) {
        this.main = main;
        this.gameOver = false;
        this.paused = false;
        this.tilemap = new Tilemap(nMap);
        this.ui = new UI(tilemap);
        this.player = new Player(ui);
        this.player.setNbPiece(nbPiece);
        this.player.setHp(hp);
        zombie1 = new Zombie(false);
        zombie2 = new Zombie(false);
        zombie3 = new Zombie(false);
        zombie4 = new Zombie(false);
        zombieArrayList = new ArrayList<>();
        zombieArrayList.add(zombie1);
        zombieArrayList.add(zombie2);
        zombieArrayList.add(zombie3);
        zombieArrayList.add(zombie4);
    }

    public Scene sceneJeu(Stage stage) {
        // Mise en place du stackPane principal
        StackPane stackPane = new StackPane();
        ObservableList stackPaneChildren = stackPane.getChildren();
        stackPaneChildren.add(main.root);
        stackPaneChildren.add(ui);

        // Création de la scène
        Scene scene = new Scene(stackPane, tilemap.getNbBlockWidth() * tilemap.getBlockSide(), tilemap.getNbBlockHeight() * tilemap.getBlockSide());

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

                if (!paused) {
                    tilemap.display(tilemap.map, gc);
                    player.nextFrame(tilemap, gc, t, player);

                    for (Zombie zombie : zombieArrayList) {
                        zombie.nextFrame(tilemap, gc, t, player);
                        if (Collision.collidingWithZombie(gc, player, zombie)) {
                            stop();
                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    start();
                                }
                            }, 1300);
                        }
                    }

                    if (tilemap.getNumberOfCoin()==0){
                        winMenu(stage, stackPaneChildren, tilemap);
                    }
                    Collision.getPiece(tilemap, player);
                    Collision.getGoldApple(tilemap, player, zombieArrayList);
                    gameOver(stage, stackPaneChildren, tilemap);
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
                        player.setNewDirection(Sprite.Direction.HAUT);
                        break;
                    case DOWN:
                        player.setNewDirection(Sprite.Direction.BAS);
                        break;
                    case LEFT:
                        player.setNewDirection(Sprite.Direction.GAUCHE);
                        break;
                    case RIGHT:
                        player.setNewDirection(Sprite.Direction.DROITE);
                        break;
                    case ESCAPE:
                        PauseMenu(stage, stackPaneChildren, tilemap);
                        break;
                }
            }
        });

        return scene;
    }

    private void PauseMenu(Stage stage, ObservableList stackChildren, Tilemap tilemap) {
        if (paused) return;
        paused=true;

        Text pauseTitle = new Text("Pause");
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

        Button play = new Button("Reprendre");
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

    private void gameOver(Stage stage, ObservableList stackChildren, Tilemap tilemap){
        if (paused || player.getHp()>0) return;
        paused=true;

        Text pauseTitle = new Text("Game Over");
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

        Button play = new Button("Rejouer");
        play.setOnAction(event -> {
            paused=false;
            animationTimer.stop();
            main.newGame(stage);
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

    private void winMenu(Stage stage, ObservableList stackChildren, Tilemap tilemap){
        if (paused) return;
        paused=true;

        Text pauseTitle = new Text("Victoire");
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

        Button play = new Button("Niveau suivant");
        play.setOnAction(event -> {
            paused=false;
            animationTimer.stop();
            main.newGame(stage, player.getNbPiece(), player.getHp());
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