import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

public class Player extends Sprite{
    private final int SPAWNX = 400;
    private final int SPAWNY = 320;

    private int hp;
    private int coinNumber;
    private boolean isSuperMode;
    private int superPowerDuration;
    private double superPowerSpeed;
    private UI ui;
    private MediaPlayer walkingPlayer;
    private MediaPlayer runningPlayer;

    /**
     * Generates a new Player
     */
    public Player(UI ui){
        super("img/steve.png",420, 0, 12,400,320,1,40,40, true);
        this.hp = 3;
        this.isSuperMode = false;
        this.superPowerDuration = 10000; // 10 secondes
        this.superPowerSpeed = super.getDefaultSpeed() * 2;
        this.ui = ui;
        // Sound
        this.walkingPlayer = Sound.getPlayer("walking");
        walkingPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                walkingPlayer.seek(new Duration(0));
            }
        });
        this.runningPlayer = Sound.getPlayer("running");
    }

    /**
     * Manages the transition between two frames
     * @param tilemap the tilemap
     * @param gc GraphicContext for draw
     * @param time Time elapsed between the 2 frames
     * @param player the player
     */
    public void nextFrame(Tilemap tilemap,GraphicsContext gc, double time, Player player){
        if (tilemap.isCenter(this.getCenterPosX(), this.getCenterPosY())){
            if (Collision.notCollidingWithWalls(this, tilemap)){
                if(player.isSuperMode()){
                    if(!runningPlayer.getStatus().equals(MediaPlayer.Status.PLAYING))
                        runningPlayer.play();
                }else{
                    if(!walkingPlayer.getStatus().equals(MediaPlayer.Status.PLAYING))
                        walkingPlayer.play();
                }
                this.currentDirection = this.newDirection;
            } else {
                if(player.isSuperMode()){
                    if(runningPlayer.getStatus().equals(MediaPlayer.Status.PLAYING))
                        runningPlayer.stop();
                }else{
                    if(walkingPlayer.getStatus().equals(MediaPlayer.Status.PLAYING))
                        walkingPlayer.stop();
                }
                this.currentDirection = Sprite.Direction.STATIC;
            }
        }

        this.updatePositionCanvas(time);

        this.render(gc);
    }

    /**
     * Stops every sounds the player is emitting
     */
    public void stopSounds(){
        if(walkingPlayer.getStatus().equals(MediaPlayer.Status.PLAYING))
            walkingPlayer.stop();
        if(runningPlayer.getStatus().equals(MediaPlayer.Status.PLAYING))
            runningPlayer.stop();
    }

    /**
     * @return Health Point of player
     */
    public int getHp() {
        return this.hp;
    }

    /**
     * @param hp Health point
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * @return is player in supermode
     */
    public boolean isSuperMode() {
        return this.isSuperMode;
    }

    /**
     * @return duration of SuperPower
     */
    public int getSuperPowerDuration() {
        return this.superPowerDuration;
    }

    /**
     * Return the number of coin owned
     * @return number of coins
     */
    public int getNbPiece() {
        return coinNumber;
    }

    /**
     * Sets number of coin owned to given number of coin
     * @param coinNumber the number of coin
     */
    public void setNbPiece(int coinNumber) {
        this.coinNumber = coinNumber;
        this.ui.setCoin(coinNumber);
    }

    /**
     * Add coins to player's coin total
     */
    public void addPiece(){
        this.coinNumber += 1;
        this.ui.addToCoin(1);
    }

    /**
     * Switch player to powerup mode
     */
    public void powerUp(){
        this.isSuperMode = true;
        super.setKillable(false);
        super.setActualSpeed(this.superPowerSpeed);
        // Center player to tile to avoid collision detection problem
        this.setPositionX(this.getPositionX()-this.getPositionX()%2);
        this.setPositionY(this.getPositionY()-this.getPositionY()%2);
        super.setKillable(false);
        super.setInitialYSpriteAlive(8);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Player.this.isSuperMode = false;
                Player.super.setKillable(true);
                Player.super.setActualSpeed(Player.super.getDefaultSpeed());
                Player.super.setInitialYSpriteAlive(0);
            }
        }, this.superPowerDuration);
    }

    /**
     * Makes player die and decrements hp
     */
    public void dead(){
        this.isSuperMode = false;
        super.setAlive(false);
        stopSounds();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                respawn(SPAWNX,SPAWNY);
                Player.super.setNewDirection(Direction.UP);
                hp--;
            }
        }, 1300);

        this.ui.decrementHp();
        Sound.getPlayer("minus").play();
    }

    /**
     * Runs player dying animation
     * @param gc the canvas to draw in
     */
    public void animationKilled(GraphicsContext gc){
        super.setDyingAnimation(gc,0, 0, 6, 1);
        super.getDyingAnimation().play();
    }

    /**
     * Puts player back to initial status
     */
    @Override
    public void reset() {
        super.reset();
        super.setKillable(true);
        this.isSuperMode = false;
    }
}
