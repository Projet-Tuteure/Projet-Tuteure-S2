

import javafx.scene.image.Image;


public class Personnage {
    final Image steveIcon = new Image("img/Steeve.png");
    int pos_x = 560;
    int pos_y = 430;


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
    public int getHeight(){return (int) steveIcon.getHeight();};

}
