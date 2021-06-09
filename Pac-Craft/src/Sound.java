import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Sound {
    /**
     * @param path the name of the sound file
     * @return the media player of given sound
     */
    public static MediaPlayer getPlayer(String path) {
        String filePath = "src/sons/"+path+".wav";
        Media sound = new Media(new File(filePath).toURI().toString());
        return new MediaPlayer(sound);
    }
}