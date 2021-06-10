package character;

import game.Controller;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EntityUnitTest {

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

    //********************************************************/
    //  Test isColliding

    @Test
    public void testIsColliding() {
        Entity entity1 = new Zombie();
        Entity entity2 = new Zombie();
        entity1.setPositionX(40.0);
        entity1.setPositionY(40.0);
        entity2.setPositionX(40.0);
        entity2.setPositionY(40.0);
        Assert.assertTrue(entity1.isColliding(entity2));
    }

    @Test
    public void testIsNotColliding() {
        Entity entity1 = new Zombie();
        Entity entity2 = new Zombie();
        entity1.setPositionX(40.0);
        entity1.setPositionY(40.0);
        entity2.setPositionX(40.0);
        entity2.setPositionY(80.0);
        Assert.assertFalse(entity1.isColliding(entity2));
    }
}
