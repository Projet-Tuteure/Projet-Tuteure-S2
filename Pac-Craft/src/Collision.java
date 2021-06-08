import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Collision {
    /**
     * Vérifie si le personnage se situe sur une pièce,
     * si oui, la ramasse et incrémente le nombre de pièces
     * @param tilemap
     * @param personnage
     */
    public static void getPiece(Tilemap tilemap,Player personnage){
        int xIndex = tilemap.getTileX(personnage.getCenterPosX());
        int yIndex = tilemap.getTileY(personnage.getCenterPosY());
        if(tilemap.getTileFromXYTile(xIndex, yIndex)==2){
            tilemap.listeNiveaux[tilemap.niveauCourant][yIndex][xIndex]=0;
            personnage.addPiece();
        }
    }

    /**
     * Vérifie si le personnage se situe sur une pomme dorée,
     * si oui, la ramasse et passe le personnage en mode "invincible"
     * @param tilemap
     * @param player
     * @param zombies
     */
    public static void getGoldApple(Tilemap tilemap, Player player, ArrayList<Zombie> zombies) {
        int xIndex = tilemap.getTileX(player.getCenterPosX());
        int yIndex = tilemap.getTileY(player.getCenterPosY());
        if (tilemap.getTileFromXYTile(xIndex, yIndex) == 3) {
            tilemap.listeNiveaux[tilemap.niveauCourant][yIndex][xIndex] = 0;
            player.powerUp();
            for (int i = 0; i < zombies.size(); i++){
                zombies.get(i).fearOf(player);
            }
        }
    }

    /**
     * Vérifie si la prochaine direction du personnage l'envoie dans un mur,
     * si non, retourne true
     * @param personnage
     * @param tilemap
     * @return
     */
    public static boolean notCollidingWithWalls(Sprite personnage, Tilemap tilemap){
        switch (personnage.newDirection){
            case HAUT:
                if (tilemap.floorBlocks.contains(tilemap.getTileFromXY(personnage.getCenterPosX(), personnage.getCenterPosY()-tilemap.BLOCKSIDE))){
                    return true;
                }
                return false;
            case BAS:
                if (tilemap.floorBlocks.contains(tilemap.getTileFromXY(personnage.getCenterPosX(), personnage.getCenterPosY()+tilemap.BLOCKSIDE))){
                    return true;
                }
                return false;
            case DROITE:
                if (tilemap.floorBlocks.contains(tilemap.getTileFromXY(personnage.getCenterPosX()+tilemap.BLOCKSIDE, personnage.getCenterPosY()))){
                    return true;
                }
                return false;
            case GAUCHE:
                if (tilemap.floorBlocks.contains(tilemap.getTileFromXY(personnage.getCenterPosX()-tilemap.BLOCKSIDE, personnage.getCenterPosY()))){
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    /**
     * @param player Sprite to compare position with zombie
     * @param zombie Sprite to compare position with player
     * @return boolean True if collide, False if not
     */
    public static boolean collidingWithZombie(GraphicsContext gc, Player player, Zombie zombie) {
        if (player.isColliding(zombie) && !player.isSuperMode() && player.isKillable()) {
            player.animationKilled(gc);
            player.dead();
            return true;
        }
        if (zombie.isColliding(player) && player.isSuperMode() && zombie.isKillable()) {
            zombie.animationKilled(gc);
            zombie.dead();
            return true;
        }
        return false;
    }
}
