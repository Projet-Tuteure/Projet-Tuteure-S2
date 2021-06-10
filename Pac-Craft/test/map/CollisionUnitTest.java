package map;

import character.Direction;
import character.Player;
import character.Zombie;
import game.Controller;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CollisionUnitTest {
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
    //  Test isOnSameLine

    @Test
    public void testIsOnSameLineSameLineNoWallBetween(){
        Tilemap tilemap = new Tilemap(0);
        Player player = Mockito.mock(Player.class);
        Zombie zombie = new Zombie();
        Mockito.when(player.getPositionX()).thenReturn(40.0);
        Mockito.when(player.getPositionY()).thenReturn(40.0);
        zombie.setPositionX(40.0);
        zombie.setPositionY(760.0);
        Assert.assertFalse(Collision.isOnSameLine(tilemap, player, zombie));
    }

    @Test
    public void testIsOnSameLineSameLineWallBetween(){
        Tilemap tilemap = new Tilemap(0);
        Player player = Mockito.mock(Player.class);
        Zombie zombie = new Zombie();
        Mockito.when(player.getPositionX()).thenReturn(40.0);
        Mockito.when(player.getPositionY()).thenReturn(360.0);
        zombie.setPositionX(40.0);
        zombie.setPositionY(400.0);
        Assert.assertTrue(Collision.isOnSameLine(tilemap, player, zombie));
    }

    @Test
    public void testIsOnSameLineSameColumnWallBetween(){
        Tilemap tilemap = new Tilemap(0);
        Player player = Mockito.mock(Player.class);
        Zombie zombie = new Zombie();
        Mockito.when(player.getPositionX()).thenReturn(40.0);
        Mockito.when(player.getPositionY()).thenReturn(40.0);
        zombie.setPositionX(760.0);
        zombie.setPositionY(40.0);
        Assert.assertFalse(Collision.isOnSameLine(tilemap, player, zombie));
    }

    @Test
    public void testIsOnSameLineSameColumnNoWallBetween(){
        Tilemap tilemap = new Tilemap(0);
        Player player = Mockito.mock(Player.class);
        Zombie zombie = new Zombie();
        Mockito.when(player.getPositionX()).thenReturn(40.0);
        Mockito.when(player.getPositionY()).thenReturn(40.0);
        zombie.setPositionX(120.0);
        zombie.setPositionY(40.0);
        Assert.assertTrue(Collision.isOnSameLine(tilemap, player, zombie));
    }

    @Test
    public void testIsOnSameLineDifferentLineAndColumn(){
        Tilemap tilemap = new Tilemap(0);
        Player player = Mockito.mock(Player.class);
        Zombie zombie = new Zombie();
        player.setPositionX(80.0);
        player.setPositionY(440.0);
        zombie.setPositionX(40.0);
        zombie.setPositionY(440.0);
        Assert.assertFalse(Collision.isOnSameLine(tilemap, player, zombie));
    }

    @Test
    public void testNotCollidingWithWallsAllDirection(){
        Zombie zombie = new Zombie();
        Tilemap tilemap = new Tilemap(0);
        zombie.setPositionX(40.0);
        zombie.setPositionY(80.0);
        zombie.setNewDirection(Direction.UP);
        Assert.assertTrue(Collision.notCollidingWithWalls(zombie,tilemap));
        zombie.setPositionY(400.0);
        zombie.setNewDirection(Direction.DOWN);
        Assert.assertTrue(Collision.notCollidingWithWalls(zombie,tilemap));
        zombie.setPositionX(720.0);
        zombie.setPositionY(40.0);
        zombie.setNewDirection(Direction.RIGHT);
        Assert.assertTrue(Collision.notCollidingWithWalls(zombie,tilemap));
        zombie.setPositionX(80.0);
        zombie.setNewDirection(Direction.LEFT);
        Assert.assertTrue(Collision.notCollidingWithWalls(zombie,tilemap));
    }

    @Test
    public void testCollidingWithWallsAllDirection(){
        Zombie zombie = new Zombie();
        Tilemap tilemap = new Tilemap(0);
        zombie.setPositionX(40.0);
        zombie.setPositionY(40.0);
        zombie.setNewDirection(Direction.UP);
        Assert.assertFalse(Collision.notCollidingWithWalls(zombie,tilemap));
        zombie.setPositionY(440.0);
        zombie.setNewDirection(Direction.DOWN);
        Assert.assertFalse(Collision.notCollidingWithWalls(zombie,tilemap));
        zombie.setPositionX(760.0);
        zombie.setPositionY(40.0);
        zombie.setNewDirection(Direction.RIGHT);
        Assert.assertFalse(Collision.notCollidingWithWalls(zombie,tilemap));
        zombie.setPositionX(40.0);
        zombie.setNewDirection(Direction.LEFT);
        Assert.assertFalse(Collision.notCollidingWithWalls(zombie,tilemap));
    }

    @Test
    public void testNotCollidingWithWallsZombieStatique(){
        Zombie zombie = new Zombie();
        Tilemap tilemap = new Tilemap(0);
        zombie.setPositionX(40.0);
        zombie.setPositionY(40.0);
        zombie.setNewDirection(Direction.STATIC);
        Assert.assertFalse(Collision.notCollidingWithWalls(zombie,tilemap));
    }
}
