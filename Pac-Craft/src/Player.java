import javafx.scene.canvas.GraphicsContext;

import java.util.Timer;
import java.util.TimerTask;

public class Player extends Sprite{
    private int hp;
    private boolean isSuperMode;
    private int superPowerTime;
    private double superPowerSpeed;
    private int nbPiece;

    /**
     * Generate a new Player
     */
    public Player(){
        super("img/steve.png",420, 0, 12,400,280,1,40,40, true);
        this.hp = 3;
        this.isSuperMode = false;
        this.superPowerTime = 10000; // 10 secondes
        this.superPowerSpeed = super.getDefaultSpeed()*2;
    }

    /** Generate a new Player
     * @param posX
     * @param posY
     * @param width
     * @param height
     */
    public Player(int posX, int posY, int width, int height){
        super("Art/steve.png", 850,0, 3, posX, posY, 0.5, width, height, true);
        this.hp = 3;
        this.isSuperMode = false;
        this.superPowerTime = 10000; // 10 secondes
        this.superPowerSpeed = super.getDefaultSpeed() *2;
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
    }

    /**
     * Ajoute une pièce au joueur
     */
    public void addPiece(){
        this.nbPiece += 1;
    }

    /**
     * Method to set superMode and turn it off when time is up
     */
    public void powerUp(){
        this.isSuperMode = true;
        super.setActualSpeed(this.superPowerSpeed);
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
        super.setActualSpeed(0);
        this.isSuperMode = false;
        super.setAlive(false);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                respawn(100,100);
                hp--;
            }
        }, 1300);
    }

    /** Display the player to the canvas
     * @param gc GraphicsContext the canva to draw in
     */
    /*
    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        if (this.isSuperMode){
            gc.clearRect(0,0,500,500); // mettre des variable fixe pour les tailles de l'écran
            double sY = super.getDirection() * super.getNbImage()*super.getHeight()+(super.getIndex()*super.getHeight());
            double xSprite;
            if (super.getIndex()%2 == 0)
                xSprite = super.getInitialXSpriteAlive();
            else
                xSprite = 900;

            gc.drawImage(super.getImage(), xSprite, sY ,super.getWidth(), super.getHeight(), super.getPositionX(),super.getPositionY(), super.getWidth(),super.getHeight());
        }
    }

     */

    /** Display the animation of player dying
     * @param gc the canva to draw in
     */
    public void animationKilled(GraphicsContext gc){
        super.setDyingAnimation(gc,5*64, 0, 4, 2);
        super.getDyingAnimation().play();
    }

    /**
     * Reset the player at the beginning without the living points
     */
    @Override
    public void reset() {
        super.reset();
        //super.respawn(100, 100);
        super.setKillable(true);
        this.isSuperMode = false;
    }

    @Override
    public String toString() {
        return "Player{" +
                super.toString() +
                "hp=" + hp +
                ", isSuperMode=" + isSuperMode +
                ", superPowerTime=" + superPowerTime +
                ", superPowerSpeed=" + superPowerSpeed +
                '}';
    }
}
