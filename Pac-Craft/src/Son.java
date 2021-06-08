import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Son {
    public static MediaPlayer getPlayer(String path) {
        String filePath = "src/sons/"+path+".wav";
        Media sound = new Media(new File(filePath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        return mediaPlayer;
    }
}