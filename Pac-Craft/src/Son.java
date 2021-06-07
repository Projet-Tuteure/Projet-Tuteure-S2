import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Son {

    private static String zombie;
    private static String walking;
    private static String theme;

    public Son(){
        zombie = "sons/zombie.mp3";
        walking = "walking.wav";
        theme = "sons/theme.mp3";
    }

    public void playZombie() {
    }

    public void playWalking() {
        playSound("walking.wav");
    }

    public void playTheme() {
    }

    public static void playSound(String path) {
        String absolutePath = "/home/hmont/Documents/cours/projet_tuto/S2/Projet-Tuteure-S2/Pac-Craft/src/Sons/";
        String filePath = absolutePath + path;
        Media sound = new Media(new File(filePath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
}
