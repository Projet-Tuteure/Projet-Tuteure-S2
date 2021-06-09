import javafx.scene.canvas.GraphicsContext;
import java.util.*;

public class Zombie extends Sprite{
    private final int SPAWNX = 400;
    private final int SPAWNY = 200;

    /**
     * Generates a new Zombie
     */
    public Zombie(){
        super("img/zombie.png",0, 0, 12,400,200, 1, 40,40, false);
    }

    /**
     * Manages transition between frames
     * @param tilemap the tilemap
     * @param gc GraphicContext for draw
     * @param time Time elapsed between the 2 frames
     * @param player the player
     */
    public void nextFrame(Tilemap tilemap, GraphicsContext gc, double time, Player player){
        // Check if is at center of tile
        if (tilemap.isCenter(this.getCenterPosX(), this.getCenterPosY())){
            ArrayList<Direction> listDirections = new ArrayList<Direction>();
            Random random = new Random();
            // Check if zombie sees player, if not random direction
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
            // Check if there is no collision problems with the new direction, if yes, random other direction
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
     * Makes zombie killable and slows him
     * @param player the Player who the Zombie is afraid of
     */
    public void fearOf(Player player){
        super.setActualSpeed(super.getDefaultSpeed()/2);
        super.setKillable(true);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Zombie.this.reset();
            }
        }, player.getSuperPowerDuration());
    }

    /**
     * Runs player dying animation
     * @param gc canvas to draw in
     */
    public void animationKilled(GraphicsContext gc){
        super.setDyingAnimation(gc,0, 0, 6, 1);
        super.getDyingAnimation().play();
    }

    /**
     * Respawn zombie and tp it to spawn
     */
    public void respawn(){
        super.respawn(SPAWNX, SPAWNY);
    }

    /**
     * Makes zombie die and respawn
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
     * The  version of zombie AI
     * @param tilemap the map played
     * @return random available direction
     */
    public ArrayList<Direction> zombiePossibleDirections(Tilemap tilemap, Player player) {
        int x = (int)getPositionX();
        int y = (int)getPositionY();
        int sizeBlock = (int)super.getWidth();
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
        }
        return listDirection;
    }

    /**
     * Calculate best direction to go nearest to player
     * @param player the player to go to
     * @return the best direction
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
     * Computes directions to get away from player
     * @param tilemap the tilemap
     * @param player the player to get away from
     * @return possible positions to get away from player
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
     * Puts back zombie to initial state
     */
    @Override
    public void reset() {
        super.reset();
        super.setInitialYSpriteAlive(0);
        super.setKillable(false);
    }
}
