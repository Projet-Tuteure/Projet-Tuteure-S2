import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;

public class Collision {

    /**
     * Checks if player is on a coin,
     * if yes, pick it up increments coin
     * @param tilemap the tilemap
     * @param player the player
     */
    public static void getCoin(Tilemap tilemap, Player player){
        int xIndex = tilemap.getTileX(player.getCenterPosX());
        int yIndex = tilemap.getTileY(player.getCenterPosY());
        if(tilemap.getTileFromXYTile(xIndex, yIndex) == 2){
            tilemap.levelList[tilemap.currentLevel][yIndex][xIndex]=0;
            player.addPiece();
        }
    }

    /**
     * Checks if player is on golden apple,
     * if yes, pick it up and switches to power up mode
     * @param tilemap the tilemap
     * @param player the player
     * @param zombies the list of zombies
     */
    public static void getGoldApple(Tilemap tilemap, Player player, ArrayList<Zombie> zombies){
        int xIndex = tilemap.getTileX(player.getCenterPosX());
        int yIndex = tilemap.getTileY(player.getCenterPosY());
        if (tilemap.getTileFromXYTile(xIndex, yIndex) == 3) {
            tilemap.levelList[tilemap.currentLevel][yIndex][xIndex] = 0;
            player.powerUp();
            for (int i = 0; i < zombies.size(); i++){
                zombies.get(i).fearOf(player);
            }
        }
    }

    /**
     * Checks if next direction of the player isn't a wall
     * @param character the player's sprite
     * @param tilemap the tilemap
     * @return true if player isn't colliding with walls
     */
    public static boolean notCollidingWithWalls(Sprite character, Tilemap tilemap){
        switch (character.newDirection){
            case UP:
                if (tilemap.floorBlocks.contains(tilemap.getTileFromXY(character.getCenterPosX(), character.getCenterPosY()-tilemap.BLOCKSIDE))){
                    return true;
                }
                return false;
            case DOWN:
                if (tilemap.floorBlocks.contains(tilemap.getTileFromXY(character.getCenterPosX(), character.getCenterPosY()+tilemap.BLOCKSIDE))){
                    return true;
                }
                return false;
            case RIGHT:
                if (tilemap.floorBlocks.contains(tilemap.getTileFromXY(character.getCenterPosX()+tilemap.BLOCKSIDE, character.getCenterPosY()))){
                    return true;
                }
                return false;
            case LEFT:
                if (tilemap.floorBlocks.contains(tilemap.getTileFromXY(character.getCenterPosX()-tilemap.BLOCKSIDE, character.getCenterPosY()))){
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    /**
     * Checks if given zombie isn't colliding with wall on next position
     * @param zombie the zombie
     * @param tilemap the tilemap
     * @return zombie isn't going into wall
     */
    public static boolean notCollidingWithWalls(Zombie zombie, Tilemap tilemap){
        switch (zombie.newDirection){
            case UP:
                if (tilemap.floorBlocksZombie.contains(tilemap.getTileFromXY(zombie.getCenterPosX(), zombie.getCenterPosY()-tilemap.BLOCKSIDE))){
                    return true;
                }
                return false;
            case DOWN:
                if (tilemap.floorBlocksZombie.contains(tilemap.getTileFromXY(zombie.getCenterPosX(), zombie.getCenterPosY()+tilemap.BLOCKSIDE))){
                    return true;
                }
                return false;
            case RIGHT:
                if (tilemap.floorBlocksZombie.contains(tilemap.getTileFromXY(zombie.getCenterPosX()+tilemap.BLOCKSIDE, zombie.getCenterPosY()))){
                    return true;
                }
                return false;
            case LEFT:
                if (tilemap.floorBlocksZombie.contains(tilemap.getTileFromXY(zombie.getCenterPosX()-tilemap.BLOCKSIDE, zombie.getCenterPosY()))){
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    /**
     * Checks if player collides with zombie
     * @param player Sprite to compare position with zombie
     * @param zombie Sprite to compare position with player
     * @return player colliding wih zombie
     */
    public static boolean collidingWithZombie(GraphicsContext gc, Player player, Zombie zombie) {
        if (player.isColliding(zombie) && !player.isSuperMode() && player.isKillable()) {
            player.animationKilled(gc);
            player.dead();
            zombie.respawn();
            return true;
        }
        if (zombie.isColliding(player) && player.isSuperMode() && zombie.isKillable()) {
            zombie.animationKilled(gc);
            zombie.dead();
            return true;
        }
        return false;
    }

    /**
     *  Checks if given player is on same line as given zombie
     * @param tilemap the tilemap
     * @param player the player
     * @param zombie the zombie
     * @return player on same line as zombie
     */
    public static boolean isOnSameLine(Tilemap tilemap, Player player, Zombie zombie){
        if (player.getPositionX() == zombie.getPositionX()){
            for (int i = Math.min((int)player.getPositionY(),(int)zombie.getPositionY()); i< Math.max((int)player.getPositionY(),(int)zombie.getPositionY()) ; i+=40){
                if (tilemap.getTileFromXY((int) player.getPositionX(), i)== 1){
                    return false;
                }
            }
            return true;
        } else if (player.getPositionY() == zombie.getPositionY()){
            for (int i = Math.min((int)player.getPositionX(),(int)zombie.getPositionX()); i< Math.max((int)player.getPositionX(),(int)zombie.getPositionX()); i+=40){
                if (tilemap.getTileFromXY(i, (int) player.getPositionY())== 1){
                    return false;
                }
            }
            return true;
        } else
            return false;
    }
}
