package game;

import character.Direction;
import character.Player;
import character.Zombie;
import map.Collision;
import map.Tilemap;
import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import menu.UI;

import java.util.ArrayList;

public class Game {
    final Controller controller;
    private final int PAUSEVALUE = 10;

    private static MediaPlayer musicPlayer;
    private boolean paused;
    private AnimationTimer animationTimer;
    private final Tilemap tilemap;
    private final UI ui;

    // Creating zombies
    private final Player player;
    private final Zombie zombie1;
    private final Zombie zombie2;
    private final Zombie zombie3;
    private final Zombie zombie4;
    private final ArrayList<Zombie> zombieArrayList;

    /**
     * Creates new game with given main
     * @param controller the main to use
     */
    public Game(Controller controller) {
        // Interface init
        this.controller = controller;
        this.paused = false;
        this.tilemap = new Tilemap(0);
        this.ui = new UI(tilemap);
        initMusicPlayer();

        // character.Player init
        this.player = new Player(ui);

        // character.Zombie init
        zombie1 = new Zombie();
        zombie2 = new Zombie();
        zombie3 = new Zombie();
        zombie4 = new Zombie();
        zombieArrayList = new ArrayList<>();
        zombieArrayList.add(zombie1);
        zombieArrayList.add(zombie2);
        zombieArrayList.add(zombie3);
        zombieArrayList.add(zombie4);
    }

    /**
     * Create a new game on the nth map with givenmain,coin number and hp
     * @param controller the main to use
     * @param nMap the id of the map
     * @param coinNumber the number of coin
     * @param hp the left hp
     */
    public Game(Controller controller, int nMap, int coinNumber, int hp) {
        // Interface init
        this.controller = controller;
        this.paused = false;
        this.tilemap = new Tilemap(nMap);
        this.ui = new UI(tilemap, hp, coinNumber);
        initMusicPlayer();

        // character.Player init
        this.player = new Player(ui);
        this.player.setNbPiece(coinNumber);
        this.player.setHp(hp);

        // character.Zombie init
        zombie1 = new Zombie();
        zombie2 = new Zombie();
        zombie3 = new Zombie();
        zombie4 = new Zombie();
        zombieArrayList = new ArrayList<>();
        zombieArrayList.add(zombie1);
        zombieArrayList.add(zombie2);
        zombieArrayList.add(zombie3);
        zombieArrayList.add(zombie4);
    }

    /**
     * Initialise the in-game music player
     */
    public void initMusicPlayer(){
        musicPlayer= Sound.getPlayer("Rubedo");
        musicPlayer.setVolume(0.08);
        musicPlayer.setStartTime(new Duration(1000*30));
    }

    /**
     * Generates the game Scene
     * @param stage the parent stage
     * @return the game scene
     */
    public Scene gameScene(Stage stage) {
        if(!musicPlayer.getStatus().equals(MediaPlayer.Status.PLAYING))
            musicPlayer.play();

        // Creating main stackpane
        StackPane stackPane = new StackPane();
        ObservableList<javafx.scene.Node> stackPaneChildren;
        stackPaneChildren = stackPane.getChildren();
        stackPaneChildren.add(controller.root);
        stackPaneChildren.add(ui);

        // Scene creation
        Scene scene = new Scene(stackPane, tilemap.getNbBlockWidth() * tilemap.getBlockSide(), tilemap.getNbBlockHeight() * tilemap.getBlockSide());

        // Graphics init
        StackPane holder = new StackPane();
        holder.setStyle("-fx-background-color: black");
        Canvas canvas = new Canvas(tilemap.getNbBlockWidth() * tilemap.getBlockSide(), tilemap.getNbBlockHeight() * tilemap.getBlockSide());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        holder.getChildren().add(canvas);
        controller.root.getChildren().add(holder);

        // game.Game loop
        final long startNanoTime = System.nanoTime();
        this.animationTimer = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                if (!paused) {
                    tilemap.display(tilemap.getMap(), gc);
                    player.nextFrame(tilemap, gc, t, player);

                    for (Zombie zombie : zombieArrayList) {
                        zombie.nextFrame(tilemap, gc, t, player);

                        Collision.collidingWithZombie(player, zombie);
                    }

                    if (tilemap.getNumberOfCoin()==0){
                        winMenu(stage, stackPaneChildren, tilemap);
                    }
                    Collision.getCoin(tilemap, player);
                    Collision.getGoldApple(tilemap, player, zombieArrayList);
                    gameOver(stage, stackPaneChildren, tilemap);
                }

                // Fps handling (fps = 1000 / PAUSEVALUE)
                try {
                    Thread.sleep(PAUSEVALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        animationTimer.start();

        // Input handling
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    player.setNewDirection(Direction.UP);
                    break;
                case DOWN:
                    player.setNewDirection(Direction.DOWN);
                    break;
                case LEFT:
                    player.setNewDirection(Direction.LEFT);
                    break;
                case RIGHT:
                    player.setNewDirection(Direction.RIGHT);
                    break;
                case ESCAPE:
                    PauseMenu(stage, stackPaneChildren, tilemap);
                    break;
            }
        });

        return scene;
    }

    /**
     * Pause menu during a game
     * @param stage the parent stage
     * @param stackChildren the ObesrvableList of the stackPane
     * @param tilemap the tilemap
     */
    private void PauseMenu(Stage stage, ObservableList<javafx.scene.Node> stackChildren, Tilemap tilemap) {
        if (paused) return;
        paused=true;

        player.stopSounds();

        Text pauseTitle = new Text("Pause");
        Font minecraftFont = Font.loadFont(Controller.class.getClassLoader().getResourceAsStream("ressources/fonts/Minecraft.ttf"), 20);
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
            paused = false;
            stackChildren.remove(pauseBox);
        });

        Button menu = new Button("Menu principal");
        menu.setOnAction(event -> {
            paused = false;
            animationTimer.stop();
            musicPlayer.stop();
            controller.setLauncher(stage);
        });

        play.getStyleClass().add("button");
        menu.getStyleClass().add("button");

        pauseBox.getChildren().add(play);
        pauseBox.getChildren().add(menu);
        pauseBox.setSpacing(7);

        pauseBox.getStylesheets().add("ressources/styles/style.css");

        stackChildren.add(pauseBox);
    }

    /**
     * The game over menu
     * @param stage the parent stage
     * @param stackChildren the ObservableList of the stackPane
     * @param tilemap the tilemap
     */
    private void gameOver(Stage stage, ObservableList<javafx.scene.Node> stackChildren, Tilemap tilemap){
        if (paused || player.getHp()>0) return;
        paused = true;

        Sound.getPlayer("death").play();
        player.stopSounds();

        Text pauseTitle = new Text("Game Over");
        Font minecraftFont = Font.loadFont(Controller.class.getClassLoader().getResourceAsStream("ressources/fonts/Minecraft.ttf"), 20);
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
            paused = false;
            animationTimer.stop();
            musicPlayer.stop();
            controller.newGame(stage);
        });

        Button menu = new Button("Menu principal");
        menu.setOnAction(event -> {
            paused = false;
            animationTimer.stop();
            musicPlayer.stop();
            controller.setLauncher(stage);
        });

        play.getStyleClass().add("button");
        menu.getStyleClass().add("button");

        pauseBox.getChildren().add(play);
        pauseBox.getChildren().add(menu);
        pauseBox.setSpacing(7);

        pauseBox.getStylesheets().add("ressources/styles/style.css");

        stackChildren.add(pauseBox);
    }

    /**
     * Win menu
     * @param stage the parent stage
     * @param stackChildren the ObservableList of the StackPane
     * @param tilemap the tilemap
     */
    private void winMenu(Stage stage, ObservableList<javafx.scene.Node> stackChildren, Tilemap tilemap){
        if (paused) return;
        paused = true;

        player.stopSounds();

        Text pauseTitle = new Text("Victoire");
        Font minecraftFont = Font.loadFont(Controller.class.getClassLoader().getResourceAsStream("ressources/fonts/Minecraft.ttf"), 20);
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
            paused = false;
            animationTimer.stop();
            musicPlayer.stop();
            controller.newGame(stage, player.getNbPiece(), player.getHp());
        });

        Button menu = new Button("Menu principal");
        menu.setOnAction(event -> {
            paused = false;
            animationTimer.stop();
            musicPlayer.stop();
            controller.setLauncher(stage);
        });

        play.getStyleClass().add("button");
        menu.getStyleClass().add("button");

        pauseBox.getChildren().add(play);
        pauseBox.getChildren().add(menu);
        pauseBox.setSpacing(7);

        pauseBox.getStylesheets().add("ressources/styles/style.css");

        stackChildren.add(pauseBox);
    }

    @Override
    public String toString() {
        return "game.Game{" +
                "main=" + controller +
                ", PAUSEVALUE=" + PAUSEVALUE +
                ", paused=" + paused +
                ", animationTimer=" + animationTimer +
                ", tilemap=" + tilemap +
                ", ui=" + ui +
                ", player=" + player +
                ", zombie1=" + zombie1 +
                ", zombie2=" + zombie2 +
                ", zombie3=" + zombie3 +
                ", zombie4=" + zombie4 +
                ", zombieArrayList=" + zombieArrayList +
                '}';
    }
}