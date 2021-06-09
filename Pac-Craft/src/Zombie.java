import javafx.scene.canvas.GraphicsContext;

import java.util.*;

public class Zombie extends Sprite{
    private final int SPAWNX = 400;
    private final int SPAWNY = 200;

    private boolean fearMode;

    /**
     * Generate a new Zombie
     */
    public Zombie(){
        super("img/zombie.png",0, 0, 12,400,200, 1, 40,40, false);
        this.fearMode = false;
    }

    /**
     * Generate a new Zombie
     * @param initialXSpriteAlive x position of the zombie character in the sprite's sheet
     * @param posX x position of zombie character
     * @param posY y position of zombie character
     * @param width width of zombie character
     * @param height height of zombie character
     */
    public Zombie(int initialXSpriteAlive, int initialYSpriteAlive, int posX, int posY, int width, int height){
        super("img/zombie.png", initialXSpriteAlive, initialYSpriteAlive, 2, posX, posY, 0.5, width, height, false);
        this.fearMode = false;
    }

    /**
     * Calls the different functions managing the display of the sprite at the next frame
     * @param gc GraphicContext for draw
     * @param time Time elapsed between the 2 frames
     */
    public void nextFrame(Tilemap tilemap, GraphicsContext gc, double time, Player player){
        if (tilemap.isCenter(this.getCenterPosX(), this.getCenterPosY())){
            ArrayList<Direction> listDirections = new ArrayList<Direction>();
            Random random = new Random();
            if ( !Collision.isOnSameLine(tilemap,player, this)){
                listDirections = this.zombiePossibleDirections(tilemap, player);
                this.newDirection = listDirections.get(random.nextInt(listDirections.size()));
            }else {
                if (player.isSuperMode()) {
                    listDirections = this.getAway(tilemap, player);
                    this.newDirection = listDirections.get(0);
                } else {
                    this.newDirection = this.smartPath(player);
                }
            }
            if (Collision.notCollidingWithWalls(this, tilemap)){
                this.currentDirection = this.newDirection;
            }else {
                this.currentDirection = listDirections.get(random.nextInt(listDirections.size()));
            }
        }

        this.updatePositionCanvas(time);
        this.render(gc);
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
     * @param gc canvas to draw in
     */
    public void animationKilled(GraphicsContext gc){
        super.setDyingAnimation(gc,0, 0, 6, 1);
        super.getDyingAnimation().play();
    }

    public void respawn(){
        super.respawn(SPAWNX, SPAWNY);
    }

    /**
     * Method to set Zombie dead and respawn
     */
    public void dead(){
        super.setActualSpeed(getDefaultSpeed());
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Zombie.super.respawn(SPAWNX,SPAWNY);
            }
        }, 1300);
    }

    /**
     * The dumb version of zombie AI
     * @param tilemap the map played
     * @return random available direction
     */
    public ArrayList<Direction> zombiePossibleDirections(Tilemap tilemap, Player player) {
        int x = (int)getPositionX();
        int y = (int)getPositionY();
        int sizeBlock = (int)super.getWidth();
        Random random = new Random();
        Direction actualDirection = super.getCurrentDirection();

        ArrayList<Direction> listDirection = new ArrayList<>();
        if(tilemap.getTileFromXY(x + sizeBlock, y) != 1)
            listDirection.add(Direction.RIGHT);
        if (tilemap.getTileFromXY(x, y + sizeBlock) != 1)
            listDirection.add(Direction.DOWN);
        if(tilemap.getTileFromXY(x - sizeBlock, y) != 1)
            listDirection.add(Direction.LEFT);
        if(tilemap.getTileFromXY(x, y - sizeBlock) != 1)
            listDirection.add(Direction.UP);

        if (!player.isSuperMode()){
            switch (actualDirection){
                case UP:
                    if (listDirection.size() > 1)
                        listDirection.remove(Direction.DOWN);
                    break;
                case RIGHT:
                    if (listDirection.size() > 1)
                        listDirection.remove(Direction.LEFT);
                    break;
                case DOWN:
                    if (listDirection.size() > 1)
                        listDirection.remove(Direction.UP);
                    break;
                case LEFT:
                    if (listDirection.size() > 1)
                        listDirection.remove(Direction.RIGHT);
                    break;
            }
            System.out.println("Je suis con");
        }
        return listDirection;
    }

    /**
     *
     * @param player
     * @return
     */
    public Direction smartPath(Player player){
        if (this.getPositionY()==player.getPositionY()){
            if ((this.getPositionX() - player.getPositionX())>0) {
                return Direction.LEFT;
            }else {
                return Direction.RIGHT;
            }
        }else if (this.getPositionX()==player.getPositionX()){
            if ((this.getPositionY() - player.getPositionY())>0){
                return Direction.UP;
            } else {
                return Direction.DOWN;
            }
        }
        return Direction.STATIC;
    }

    /**
     *
     * @param tilemap
     * @param player
     * @return
     */
    public ArrayList<Direction> getAway(Tilemap tilemap, Player player){
        ArrayList<Direction> possibleDirection = this.zombiePossibleDirections(tilemap, player);
        if (this.getPositionY()==player.getPositionY()){
            if ((this.getPositionX() - player.getPositionX())>0) {
                possibleDirection.remove(Direction.LEFT);
            }else {
                possibleDirection.remove(Direction.RIGHT);
            }
        }else if (this.getPositionX()==player.getPositionX()){
            if ((this.getPositionY() - player.getPositionY())>0){
                possibleDirection.remove(Direction.UP);
            } else {
                possibleDirection.remove(Direction.DOWN);
            }
        }

        return possibleDirection;
    }

    /**
     * Used to reset Zombie
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
        return "Zombie{" +
                "fearMode=" + fearMode +
                '}';
    }
}
