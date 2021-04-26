

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class Personnage {
    enum Direction {
        GAUCHE,
        DROITE,
        BAS,
        HAUT,
        STATIQUE
    };

    final Image STEVEICON = new Image("Img/Steve.png");
    final int SPRITEWIDTH = 20;

    int positionX;
    int positionY;
    int actualSpeed;
    int nbPiece;
    Direction newDirection;
    Direction currentDirection;

    boolean isInvincible = false;
    boolean wasInvincible = false;

    public Personnage(){
        this.positionX = 90;
        this.positionY = 50;
        this.actualSpeed = 1;
        this.nbPiece = 0;
        this.newDirection = Direction.STATIQUE;
        this.currentDirection = Direction.STATIQUE;
    }

    /*
    public void deplacement(Tilemap tilemap, GraphicsContext gc){
        if (tilemap.isCenter(this.getCenterPosX(), this.getCenterPosY())){
            if (Collision.notCollidingWithWalls(this, tilemap)){
                this.currentDirection = this.newDirection;
            } else if (this.currentDirection == this.newDirection){
                this.currentDirection = Direction.STATIQUE;
            }
        }
        switch (this.currentDirection){
            case HAUT:
                this.positionY -=this.actualSpeed;
                break;
            case BAS:
                this.positionY +=this.actualSpeed;
                break;
            case DROITE:
                this.positionX +=this.actualSpeed;
                break;
            case GAUCHE:
                this.positionX -=this.actualSpeed;
                break;
            default:
                break;
        }
        gc.drawImage(this.STEVEICON,this.positionX,this.positionY);
    }


    public void setNewDirection(Direction direction){
        this.newDirection = direction;
    }

    public  void checkInvincibility (){
        if(wasInvincible == false && isInvincible == true){
            wasInvincible = true;
            // code de Thomas et Laurent

        }

    }

    public int getHeight(){return (int) STEVEICON.getHeight();};

    public int getPositionX(){
        return this.positionX;
    }

    public int getPositionY(){
        return this.positionY;
    }

    public int getCenterPosX(){
        return this.positionX +this.SPRITEWIDTH /2;
    }

    public int getCenterPosY(){
        return this.positionY +this.SPRITEWIDTH /2;
    }

     */
}
