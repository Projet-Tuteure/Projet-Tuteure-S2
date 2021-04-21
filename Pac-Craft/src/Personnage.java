

import javafx.scene.image.Image;


public class Personnage {
    enum Direction {
        GAUCHE,
        DROITE,
        BAS,
        HAUT,
        STATIQUE
    };

    final Image steveIcon = new Image("img/Steve.png");
    final int spriteWidth = 20;

    int posX;
    int posY;
    int vitesse;
    int nbPiece;
    Direction newDirection;
    Direction currentDirection;

    boolean isInvincible = false;
    boolean wasInvincible = false;

    public Personnage(){
        this.posX = 90;
        this.posY = 50;
        this.vitesse = 1;
        this.nbPiece = 0;
        this.newDirection = Direction.STATIQUE;
        this.currentDirection = Direction.STATIQUE;
    }

    public void deplacement(Tilemap tilemap){
        if (tilemap.isCenter(this.getCenterPosX(), this.getCenterPosY())){
            if (Collision.notCollidingWithWalls(this, tilemap)){
                this.currentDirection = this.newDirection;
            } else if (this.currentDirection == this.newDirection){
                this.currentDirection = Direction.STATIQUE;
            }
        }
        switch (this.currentDirection){
            case HAUT:
                this.posY -=this.vitesse;
                break;
            case BAS:
                this.posY +=this.vitesse;
                break;
            case DROITE:
                this.posX +=this.vitesse;
                break;
            case GAUCHE:
                this.posX -=this.vitesse;
                break;
            default:
                break;
        }
    }

    public void setNewDirection(Direction direction){
        this.newDirection =direction;
    }

    public  void checkInvincibility (){
        if(wasInvincible == false && isInvincible == true){
            wasInvincible = true;
            // code de Thomas et Laurent

        }

    }

    public int getHeight(){return (int) steveIcon.getHeight();};

    public int getPosX(){
        return this.posX;
    }

    public int getPosY(){
        return this.posY;
    }

    public int getCenterPosX(){
        return this.posX +this.spriteWidth/2;
    }

    public int getCenterPosY(){
        return this.posY +this.spriteWidth/2;
    }

    public int getXaxe (int BLOCKSIDE){
        int x = 0;

        while(posX >x*BLOCKSIDE){
            x++;
        }
        if(x*BLOCKSIDE!= posX){
            x--;
        }
        return x;
    }

    public int getYaxe (int BLOCKSIDE){
        int y = 0;
        while(posY >y*BLOCKSIDE){
            y++;
        }
        y--;
        return y;
    }
}
