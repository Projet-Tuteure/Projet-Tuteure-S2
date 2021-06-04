import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;

public class Son {

    private static Media zombie;
    private static Media walking;
    private static Media theme;

    private final int BUFFER_SIZE = 128000;
    private File soundFile;
    private AudioInputStream audioStream;
    private AudioFormat audioFormat;
    private SourceDataLine sourceLine;

    public Son(){
        /*zombie = new Media("Sons/zombie.mp3");
        walking = new Media("Sons/walking.mp3");
        theme = new Media("Sons/theme.mp3");*/
    }

    public void playZombie() {
        MediaPlayer player = new MediaPlayer(Son.zombie);
        player.play();
    }

    public void playWalking() {
    }

    public void playTheme() {
        MediaPlayer player = new MediaPlayer(Son.theme);
        player.play();
    }
}
