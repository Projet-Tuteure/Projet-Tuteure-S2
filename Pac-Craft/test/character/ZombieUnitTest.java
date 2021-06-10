package character;

import map.Tilemap;
import game.Controller;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

public class ZombieUnitTest {

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
    //  Test smartPath

    @Test
    public void testSmartPathZombieSameLineRightPlayer(){
        Zombie zombie = new Zombie();
        Player player = Mockito.mock(Player.class);
        Mockito.when(player.getPositionX()).thenReturn(80.0);
        Mockito.when(player.getPositionY()).thenReturn(40.0);
        zombie.setPositionX(40.0);
        zombie.setPositionY(40.0);
        Assert.assertEquals(Direction.RIGHT, zombie.smartPath(player));
    }

    @Test
    public void testSmartPathZombieSameLineLeftPlayer(){
        Zombie zombie = new Zombie();
        Player player = Mockito.mock(Player.class);
        Mockito.when(player.getPositionX()).thenReturn(40.0);
        Mockito.when(player.getPositionY()).thenReturn(40.0);
        zombie.setPositionX(80.0);
        zombie.setPositionY(40.0);
        Assert.assertEquals(Direction.LEFT, zombie.smartPath(player));
    }

    @Test
    public void testSmartPathZombieSameColumnAbovePlayer(){
        Zombie zombie = new Zombie();
        Player player = Mockito.mock(Player.class);
        Mockito.when(player.getPositionX()).thenReturn(40.0);
        Mockito.when(player.getPositionY()).thenReturn(40.0);
        zombie.setPositionX(40.0);
        zombie.setPositionY(80.0);
        Assert.assertEquals(Direction.UP, zombie.smartPath(player));
    }

    @Test
    public void testSmartPathZombieSameColumnUnderPlayer(){
        Zombie zombie = new Zombie();
        Player player = Mockito.mock(Player.class);
        Mockito.when(player.getPositionX()).thenReturn(40.0);
        Mockito.when(player.getPositionY()).thenReturn(80.0);
        zombie.setPositionX(40.0);
        zombie.setPositionY(40.0);
        Assert.assertEquals(Direction.DOWN, zombie.smartPath(player));
    }

    @Test
    public void testSmartPathZombieDifferentPositionPlayer(){
        Zombie zombie = new Zombie();
        Player player = Mockito.mock(Player.class);
        Mockito.when(player.getPositionX()).thenReturn(80.0);
        Mockito.when(player.getPositionY()).thenReturn(40.0);
        zombie.setPositionX(40.0);
        zombie.setPositionY(80.0);
        Assert.assertEquals(Direction.STATIC, zombie.smartPath(player));
    }

    //****************************************************************/
    //  Test getAway

    @Test
    public void testgetAwayZombiePlayerSameLineWhenZombieRight() {
        Tilemap tilemap = new Tilemap(0);
        Zombie zombie = new Zombie();
        Player player = Mockito.mock(Player.class);
        Mockito.when(player.getPositionX()).thenReturn(40.0);
        Mockito.when(player.getPositionY()).thenReturn(40.0);
        zombie.setPositionX(80.0);
        zombie.setPositionY(40.0);
        Assert.assertEquals(Arrays.asList(Direction.RIGHT), zombie.getAway(tilemap,player));
    }

    @Test
    public void testgetAwayZombiePlayerSameLineWhenZombieLeft() {
        Tilemap tilemap = new Tilemap(0);
        Zombie zombie = new Zombie();
        Player player = Mockito.mock(Player.class);
        Mockito.when(player.getPositionX()).thenReturn(120.0);
        Mockito.when(player.getPositionY()).thenReturn(40.0);
        zombie.setPositionX(80.0);
        zombie.setPositionY(40.0);
        Assert.assertEquals(Arrays.asList(Direction.LEFT), zombie.getAway(tilemap,player));
    }

    @Test
    public void testgetAwayZombiePlayerSameColumnWhenZombieUnder() {
        Tilemap tilemap = new Tilemap(0);
        Zombie zombie = new Zombie();
        Player player = Mockito.mock(Player.class);
        Mockito.when(player.getPositionX()).thenReturn(40.0);
        Mockito.when(player.getPositionY()).thenReturn(40.0);
        zombie.setPositionX(40.0);
        zombie.setPositionY(80.0);
        Assert.assertEquals(Arrays.asList(Direction.DOWN), zombie.getAway(tilemap,player));
    }

    @Test
    public void testgetAwayZombiePlayerSameColumnWhenZombieAbove() {
        Tilemap tilemap = new Tilemap(0);
        Zombie zombie = new Zombie();
        Player player = Mockito.mock(Player.class);
        Mockito.when(player.getPositionX()).thenReturn(40.0);
        Mockito.when(player.getPositionY()).thenReturn(120.0);
        zombie.setPositionX(40.0);
        zombie.setPositionY(80.0);
        Assert.assertEquals(Arrays.asList(Direction.UP), zombie.getAway(tilemap,player));
    }

    //***********************************************************/
    // Test zombiePossibleDirection

    @Test
    public void testZombiePossibleDirectionUpRight() {
        Tilemap tilemap = new Tilemap(0);
        Zombie zombie = new Zombie();
        Player player = Mockito.mock(Player.class);
        zombie.setPositionX(40.0);
        zombie.setPositionY(440.0);
        Assert.assertEquals(Arrays.asList(Direction.RIGHT, Direction.UP), zombie.zombiePossibleDirections(tilemap,player));
    }

    @Test
    public void testZombiePossibleDirectionDownLeft() {
        Tilemap tilemap = new Tilemap(0);
        Zombie zombie = new Zombie();
        Player player = Mockito.mock(Player.class);
        zombie.setPositionX(760.0);
        zombie.setPositionY(40.0);
        Assert.assertEquals(Arrays.asList(Direction.DOWN, Direction.LEFT), zombie.zombiePossibleDirections(tilemap,player));
    }

    @Test
    public void testZombiePossibleDirectionPlayerIsSuperMode() {
        Tilemap tilemap = new Tilemap(0);
        Zombie zombie = new Zombie();
        Player player = Mockito.mock(Player.class);
        Mockito.when(player.isSuperMode()).thenReturn(false);
        // direction up
        zombie.setPositionX(760.0);
        zombie.setPositionY(80.0);
        zombie.setCurrentDirection(Direction.UP);
        Assert.assertEquals(Arrays.asList(Direction.UP), zombie.zombiePossibleDirections(tilemap,player));
        // direction down
        zombie.setPositionX(760.0);
        zombie.setPositionY(80.0);
        zombie.setCurrentDirection(Direction.DOWN);
        Assert.assertEquals(Arrays.asList(Direction.DOWN), zombie.zombiePossibleDirections(tilemap,player));
        // direction Left
        zombie.setPositionX(760.0);
        zombie.setPositionY(40.0);
        zombie.setCurrentDirection(Direction.LEFT);
        Assert.assertEquals(Arrays.asList(Direction.DOWN, Direction.LEFT), zombie.zombiePossibleDirections(tilemap,player));
        // direction down
        zombie.setPositionX(40.0);
        zombie.setPositionY(40.0);
        zombie.setCurrentDirection(Direction.RIGHT);
        Assert.assertEquals(Arrays.asList(Direction.RIGHT, Direction.DOWN), zombie.zombiePossibleDirections(tilemap,player));
    }



    //*********************************************************/
}
