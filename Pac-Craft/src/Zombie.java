import javafx.scene.canvas.GraphicsContext;
import java.util.Timer;
import java.util.TimerTask;

public class Zombie extends Sprite{
    private boolean fearMode;

    /**
     * Generate a new Zombie
     */
    public Zombie(){
        super("Art/zombie.png",0,0, 2, 250,250,0.5,64,64, false);
        this.fearMode = false;
    }

    /** Generate a new Zombie
     * @param initialXSpriteAlive x position of the zombie character in the sprite's sheet
     * @param posX x positon of zombie character
     * @param posY y positon of zombie character
     * @param width width of zombie character
     * @param height height of zombie character
     */
    public Zombie(int initialXSpriteAlive, int initialYSpriteAlive, int posX, int posY, int width, int height){
        super("Art/zombie.png", initialXSpriteAlive, initialYSpriteAlive, 2, posX, posY, 0.5, width, height, false);
        this.fearMode = false;
    }

    /**
     * @return boolean True if in fear of player, False if not
     */
    public boolean isFearMode() {
        return fearMode;
    }

    /**
     * @param fearMode boolean
     */
    public void setFearMode(boolean fearMode) {
        this.fearMode = fearMode;
    }

    /** Set the Zombie's speed lower by half, set him in the fearMode which makes him killable
     * @param player the Player who the Zombie is afraid of
     */
    public void fearOf(Player player){
        //setInitialYSpriteAlive(550);
        super.setActualSpeed(super.getDefaultSpeed()/2);
        this.fearMode = true;
        super.setKillable(true);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Zombie.this.reset();
            }
        }, player.getSuperPowerTime());
    }

    /** Display the animation of player dying
     * @param gc canva to draw in
     */
    public void animationKilled(GraphicsContext gc){
        super.setDyingAnimation(gc,5*64, 0, 4, 2);
        super.getDyingAnimation().play();
    }

    /**
     * Method to set Zombie dead and respawn
     */
    public void dead(){
        super.setActualSpeed(0);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Zombie.super.respawn(200,200);
                System.out.println("zombie meurt");
                //Zombie.this.reset();
            }
        }, 1300);
    }

    /**
     * Méthode to reset Zombie
     */
    @Override
    public void reset() {
        super.reset();
        super.setInitialYSpriteAlive(0);
        super.setKillable(false);
        this.fearMode = false;
    }

    @Override
    public String toString() {
        return "Zombie{" + super.toString() +
                "fearMode=" + fearMode +
                '}';
    }
}
