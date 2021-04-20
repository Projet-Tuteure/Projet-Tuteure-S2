

import javafx.scene.image.Image;


public class Personnage {
    final Image steveIcon = new Image("img/Steeve.png");
    int pos_x = 50;
    int pos_y = 50;
    int vitesse = 15;
    int nbPiece = 0;
    boolean isInvincible = false;
    boolean wasInvincible = false;


    public void deplacement(boolean gauche, boolean droite, boolean haut, boolean bas){
        if ( bas==true){
            pos_y-=1;
        }
        if ( haut==true){
            pos_y+=1;
        }
        if ( gauche==true){
            pos_x-=1;
        }
        if ( droite==true){
            pos_x+=1;
        }

    }


    public  void checkInvincibility (){
        if(wasInvincible == false && isInvincible == true){
            wasInvincible = true;
            // code de Thomas et Laurent

        }

    }
    public int getHeight(){return (int) steveIcon.getHeight();};

    public int getXaxe (int BLOCKSIDE){
        int x = 0;

        while(pos_x>x*BLOCKSIDE){
            x++;
        }
        if(x*BLOCKSIDE!=pos_x){
            x--;
        }
        return x;
    }

    public int getYaxe (int BLOCKSIDE){
        int y = 0;
        while(pos_y>y*BLOCKSIDE){
            y++;
        }
        y--;
        return y;
    }
}
