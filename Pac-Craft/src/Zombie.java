import javafx.scene.canvas.GraphicsContext;

import java.util.*;

public class Zombie extends Sprite{
    private final int SPAWNX = 400;
    private final int SPAWNY = 200;

    private boolean fearMode;
    private boolean type;

    /**
     * Generate a new Zombie
     * @param type
     */
    public Zombie(boolean type){
        super("img/zombie.png",0, 0, 12,400,200, 1, 40,40, false);
        this.fearMode = false;
        this.type = type;
    }

    /** Generate a new Zombie
     * @param initialXSpriteAlive x position of the zombie character in the sprite's sheet
     * @param posX x position of zombie character
     * @param posY y positon of zombie character
     * @param width width of zombie character
     * @param height height of zombie character
     */
    public Zombie(int initialXSpriteAlive, int initialYSpriteAlive, int posX, int posY, int width, int height){
        super("img/zombie.png", initialXSpriteAlive, initialYSpriteAlive, 2, posX, posY, 0.5, width, height, false);
        this.fearMode = false;
    }

    /**
     * Appelle les différentes fonctions gérant l'affichage du sprite à la prochaine frame
     * @param gc GraphicContext dans lequel dessiner
     * @param t Temps écoulé entre les 2 frame
     */
    public void nextFrame(Tilemap tilemap,GraphicsContext gc, double t, Player player){
        if (tilemap.isCenter(this.getCenterPosX(), this.getCenterPosY())){
            if (Collision.notCollidingWithWalls(this, tilemap)){
                this.currentDirection = this.newDirection;
            }
            if ( !Collision.isOnSameLine(tilemap,player,(Zombie) this)){
                Random random = new Random();
                ArrayList<Direction> listDirections;
                if (player.isSuperMode())
                    listDirections = ((Zombie) this).getAway(tilemap, player);
                else
                    listDirections = ((Zombie) this).directionZombie(tilemap, player);
                System.out.println("taille que l'on connaitre : " + listDirections.size());
                this.currentDirection = listDirections.get(random.nextInt(listDirections.size()));
                System.out.println("currente direction aleatoire : " + this.currentDirection);
                this.newDirection = this.currentDirection;
            }else {
                this.currentDirection = ((Zombie) this).aStarPath(tilemap, player);
                this.newDirection = this.currentDirection;
            }
        }

        this.update(t);

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

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
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
    public ArrayList<Direction> directionZombie(Tilemap tilemap, Player player) {
        int x = (int)getPositionX();
        int y = (int)getPositionY();
        int sizeBlock = (int)super.getWidth();
        Random random = new Random();
        Direction actualDirection = super.getCurrentDirection();

        ArrayList<Direction> listDirection = new ArrayList<>();
        if(tilemap.getTileFromXY(x + sizeBlock, y) != 1)
            listDirection.add(Direction.DROITE);
        if (tilemap.getTileFromXY(x, y + sizeBlock) != 1)
            listDirection.add(Direction.BAS);
        if(tilemap.getTileFromXY(x - sizeBlock, y) != 1)
            listDirection.add(Direction.GAUCHE);
        if(tilemap.getTileFromXY(x, y - sizeBlock) != 1)
            listDirection.add(Direction.HAUT);

        if (!player.isSuperMode()){
            switch (actualDirection){
                case HAUT:
                    if (listDirection.size() > 1)
                        listDirection.remove(Direction.BAS);
                    break;
                case DROITE:
                    if (listDirection.size() > 1)
                        listDirection.remove(Direction.GAUCHE);
                    break;
                case BAS:
                    if (listDirection.size() > 1)
                        listDirection.remove(Direction.HAUT);
                    break;
                case GAUCHE:
                    if (listDirection.size() > 1)
                        listDirection.remove(Direction.DROITE);
                    break;
            }
            System.out.println("Je suis con");
        }
        return listDirection;
    }

    public Direction aStarPath(Tilemap tilemap, Player player){
        Case fin = new Case(tilemap.getTileX((int)player.getPositionY()), tilemap.getTileY((int)player.getPositionX()));
        System.out.println(tilemap.getTileX((int)player.getPositionX()));
        System.out.println(tilemap.getTileY((int)player.getPositionY()));

        Case debut = new Case(tilemap.getTileX((int)this.getPositionY()),tilemap.getTileY((int)this.getPositionX()));
        PathFinding pathFinding = new PathFinding(debut, fin, tilemap.getMap(0));
        Case caseToGo = pathFinding.getLastElement();
        if (caseToGo != null){
            System.out.println("salut ");
            if (caseToGo.getX() > debut.getX()){
                System.out.println("Go a bas");
                return Direction.BAS;
            } else if (caseToGo.getY() < debut.getY()){
                System.out.println("Go a gauche");
                return Direction.GAUCHE;
            } else if (caseToGo.getY() > debut.getY()){
                System.out.println("Go a droite");
                return Direction.DROITE;
            } else if (caseToGo.getX() < debut.getX()){
                System.out.println("Go a HAUT");
                return Direction.HAUT;
            }
        }
        return Direction.STATIQUE;
    }

    public ArrayList<Direction> getAway(Tilemap tilemap, Player player){
        ArrayList<Direction> possibleDirection = this.directionZombie(tilemap, player);
        System.out.println("je fui");
        if (player.getPositionX() > this.getPositionX() || player.getPositionY() == this.getPositionY()){
            switch (player.getCurrentDirection()){
                case HAUT:
                    possibleDirection.remove(Direction.BAS);
                    break;
                case DROITE:
                    possibleDirection.remove(Direction.GAUCHE);
                    break;
                case BAS:
                    possibleDirection.remove(Direction.HAUT);
                    break;
                case GAUCHE:
                    possibleDirection.remove(Direction.DROITE);
                    break;
            }
        } else if (player.getPositionY() > this.getPositionY()){
            possibleDirection.remove(Direction.BAS);
        } else {
            possibleDirection.remove(Direction.HAUT);
        }
        if (possibleDirection.size() == 0){
            possibleDirection.add(Direction.STATIQUE);
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
                ", type=" + type +
                '}';
    }
}
