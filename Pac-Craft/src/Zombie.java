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
            if (!this.isType()){
                this.currentDirection = this.directionZombie(tilemap);
                this.newDirection = this.currentDirection;
            }else {
                this.currentDirection = this.aStarPath(tilemap, player);
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
        super.setDyingAnimation(gc,5*64, 0, 4, 2);
        super.getDyingAnimation().play();
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
                System.out.println("zombie meurt");
                //Zombie.this.reset();
            }
        }, 1300);
    }

    /**
     * The dumb version of zombie AI
     * @param tilemap the map played
     * @return random available direction
     */
    public Direction directionZombie(Tilemap tilemap) {
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
        return listDirection.get(random.nextInt(listDirection.size()));
    }

    public Direction aStarPath(Tilemap tilemap, Player player){
        Case fin = new Case(tilemap.getTileX((int)player.getPositionX()), tilemap.getTileY((int)player.getPositionY()));
        System.out.println(tilemap.getTileX((int)player.getPositionX()));
        System.out.println(tilemap.getTileY((int)player.getPositionY()));

        Case debut = new Case(tilemap.getTileX((int)this.getPositionX()),tilemap.getTileY((int)this.getPositionY()));
        System.out.println("x zombie : " + debut.getX()+ " et y zombie : " + debut.getY());
        PathFinding pathFinding = new PathFinding(debut, fin, tilemap.getMap(0));
        System.out.println(Arrays.deepToString(tilemap.getMap(0)));
        Case caseToGo = pathFinding.getLastElement();
        System.out.println("xToGo : " + caseToGo.getX() + " et yToGo : "+caseToGo.getY());
        if (caseToGo != null){
            System.out.println("salut ");
            if (caseToGo.getX() > debut.getX()){
                System.out.println("Go a droite");
                return Direction.DROITE;
            } else if (caseToGo.getY() < debut.getY()){
                System.out.println("Go a haut");
                return Direction.HAUT;
            } else if (caseToGo.getY() > debut.getY()){
                System.out.println("Go a bas");
                return Direction.BAS;
            } else if (caseToGo.getX() < debut.getX()){
                System.out.println("Go a gauche");
                return Direction.GAUCHE;
            }
        }
        return Direction.STATIQUE;
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
