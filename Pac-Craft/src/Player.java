import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

public class Player extends Sprite{
    private final int SPAWNX = 400;
    private final int SPAWNY = 320;

    private int hp;
    private int nbPiece;
    private boolean isSuperMode;
    private int superPowerTime;
    private double superPowerSpeed;
    private UI ui;
    private MediaPlayer walkingPlayer;
    private MediaPlayer runningPlayer;

    /**
     * Generate a new Player
     */
    public Player(UI ui){
        super("img/steve.png",420, 0, 12,400,320,1,40,40, true);
        this.hp = 3;
        this.isSuperMode = false;
        this.superPowerTime = 10000; // 10 secondes
        this.superPowerSpeed = super.getDefaultSpeed()*2;
        this.ui = ui;
        // Sound
        this.walkingPlayer = Son.getPlayer("walking");
        walkingPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                walkingPlayer.seek(new Duration(0));
            }
        });
        this.runningPlayer = Son.getPlayer("running");
    }

    /** Generate a new Player
     * @param posX
     * @param posY
     * @param width
     * @param height
     */
    public Player(int posX, int posY, int width, int height, UI ui){
        super("img/steve.png", 850,0, 3, posX, posY, 0.5, width, height, true);
        this.hp = 3;
        this.isSuperMode = false;
        this.superPowerTime = 10000; // 10 secondes
        this.superPowerSpeed = super.getDefaultSpeed() *2;
        this.ui = ui;
    }

    /**
     * Appelle les différentes fonctions gérant l'affichage du sprite à la prochaine frame
     * @param gc GraphicContext dans lequel dessiner
     * @param t Temps écoulé entre les 2 frame
     */
    public void nextFrame(Tilemap tilemap,GraphicsContext gc, double t, Player player){
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
                this.currentDirection = Sprite.Direction.STATIQUE;
            }
        }

        this.update(t);

        this.render(gc);
    }

    /**
     * stops every sounds the player is emitting
     */
    public void stopSounds(){
        if(walkingPlayer.getStatus().equals(MediaPlayer.Status.PLAYING))
            walkingPlayer.stop();
        if(runningPlayer.getStatus().equals(MediaPlayer.Status.PLAYING))
            runningPlayer.stop();
    }

    /**
     * @return int Health Point of player
     */
    public int getHp() {
        return this.hp;
    }

    /**
     * @param hp int Health point
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * @return boolean True if player is in superMode, False if not
     */
    public boolean isSuperMode() {
        return this.isSuperMode;
    }

    /**
     * @param superMode True if superMode, False if not
     */
    public void setSuperMode(boolean superMode) {
        this.isSuperMode = superMode;
    }

    /**
     * @return int time of SuperPower
     */
    public int getSuperPowerTime() {
        return this.superPowerTime;
    }

    /**
     * @param superPowerTime int time for super power
     */
    public void setSuperPowerTime(int superPowerTime) {
        this.superPowerTime = superPowerTime;
    }

    /**
     * @return double speed when superPower activated
     */
    public double getSuperPowerSpeed() {
        return this.superPowerSpeed;
    }

    /**
     * @param superPowerSpeed double speed of superMode
     */
    public void setSuperPowerSpeed(double superPowerSpeed) {
        this.superPowerSpeed = superPowerSpeed;
    }

    /**
     * Retourne le nombre de pièce possédées
     * @return
     */
    public int getNbPiece() {
        return nbPiece;
    }

    /**
     * Définit le nombre de pièces possédées
     * @param nbPiece
     */
    public void setNbPiece(int nbPiece) {
        this.nbPiece = nbPiece;
        this.ui.setScore(nbPiece);
    }

    /**
     * Ajoute une pièce au joueur
     */
    public void addPiece(){
        this.nbPiece += 1;
        this.ui.addToScore(1);
    }

    /**
     * Method to set superMode and turn it off when time is up
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
                //Player.this.reset();
            }
        }, this.superPowerTime);
    }

    /**
     * Method to set player dead and respawn with HP decreased
     */
    public void dead(){
        this.isSuperMode = false;
        super.setAlive(false);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                respawn(SPAWNX,SPAWNY);
                Player.super.setNewDirection(Direction.HAUT);
                hp--;
            }
        }, 1300);

        this.ui.decrementPv();
        Son.getPlayer("minus").play();
    }

    /** Display the animation of player dying
     * @param gc the canva to draw in
     */
    public void animationKilled(GraphicsContext gc){
        super.setDyingAnimation(gc,0, 0, 6, 1);
        super.getDyingAnimation().play();
    }

    /**
     * Reset the player at the beginning without the living points
     */
    @Override
    public void reset() {
        super.reset();
        super.setKillable(true);
        this.isSuperMode = false;
    }
}
