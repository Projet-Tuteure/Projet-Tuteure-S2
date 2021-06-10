package map;

import game.Controller;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TilemapUnitTest {
    @Before
    public void testA() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                new JFXPanel(); // Initializes the JavaFx Platform
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        Controller.launch(); // Create and
                        // initialize
                        // your app.

                    }
                });
            }
        });
        thread.start();// Initialize the thread
        Thread.sleep(1*1000); // Time to use the app, with out this, the thread
        // will be killed before you can tell.
    }

    @Test
    public void testIsCenter(){
        Tilemap tilemap = new Tilemap(8);
        Assert.assertTrue(tilemap.isCenter(60,60));
    }

    @Test
    public void testIsNotCenter(){
        Tilemap tilemap = new Tilemap(8);
        Assert.assertFalse(tilemap.isCenter(70,70));
    }
}
