
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
     * @param personnage
     */
    public static void getGoldApple(Tilemap tilemap,Player personnage){
        int xIndex = tilemap.getTileX(personnage.getCenterPosX());
        int yIndex = tilemap.getTileY(personnage.getCenterPosY());
        if(tilemap.getTileFromXYTile(xIndex, yIndex)==3){
            tilemap.listeNiveaux[tilemap.niveauCourant][yIndex][xIndex]=0;
            personnage.powerUp();
            personnage.setPositionX(personnage.getPositionX()-personnage.getPositionX()%2);
            personnage.setPositionY(personnage.getPositionY()-personnage.getPositionY()%2);
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
}
