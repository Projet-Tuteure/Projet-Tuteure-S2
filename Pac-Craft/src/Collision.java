

public class Collision {


    public static void getPiece(Tilemap tilemap,Personnage Steeve){
        int x = Steeve.getXaxe(tilemap.BLOCKSIDE);
        int y = Steeve.getYaxe(tilemap.BLOCKSIDE);
        if(tilemap.getValueOf(y,x)==2){
            tilemap.listeNiveaux[tilemap.niveauCourant][y][x]=0;
            Steeve.nbPiece +=1;
            System.out.println("TU AS "+ Steeve.nbPiece+" pièces chacal");
        }
    }

    public static void getGoldApple(Tilemap tilemap,Personnage Steeve){
        int x = Steeve.getXaxe(tilemap.BLOCKSIDE);
        int y = Steeve.getYaxe(tilemap.BLOCKSIDE);
        if(tilemap.getValueOf(y,x)==3){
            tilemap.listeNiveaux[tilemap.niveauCourant][y][x]=0;
            Steeve.isInvincible = true;
            System.out.println("Tu es invincible");
        }
    }



    public static void collide(int pos_x, int pos_y,Tilemap tilemap,int direction,Personnage Steve){
        int [] posFinal;


        int nextX = pos_x ;
        int nextY =  pos_y ;

        switch (direction){
            case 0:
                // gauche
                nextX -=Steve.vitesse;
                break;
            case 1:
                // droite
                nextX +=Steve.vitesse;
                break;
            case 2:
                // haut
                nextY -=Steve.vitesse;
                break;
            case 3:
                // bas
                nextY+=Steve.vitesse;
                break;
        }

        if(!isColliding(Steve.pos_x, Steve.pos_y, tilemap.BLOCKSIDE, direction,Steve.getHeight(),Steve,tilemap)){
            posFinal = new int[]{nextX, nextY};
        }else{
            posFinal = new int[]{pos_x,pos_y};
        }

        Steve.pos_x = posFinal[0];
        Steve.pos_y = posFinal[1];
    }


    public static boolean isColliding(int posX,int posY,int BLOCKSIDE,int direction,int personnageSize,Personnage Steeve,Tilemap tilemap){
        int x = Steeve.getXaxe(BLOCKSIDE);
        int y = Steeve.getYaxe(BLOCKSIDE);
        int placement = tilemap.getValueOf(y,x);
        int valeurGauche = 0;
        int valeurDroite = 0;
        int valeurHaut = 0;
        int valeurBas = 0;



        switch (direction){
            case 0:
                // gauche


                if(posY%BLOCKSIDE==0){y++;}
                valeurGauche = tilemap.getValueOf(y,x-1);
                
                if(tilemap.floorBlocks.contains(String.valueOf(placement)) && tilemap.floorBlocks.contains(String.valueOf(valeurGauche))){
                    return false;
                }
                else if(placement==1 && valeurGauche==1){return false;}
                else if(tilemap.floorBlocks.contains(String.valueOf(placement)) && valeurGauche==1 && posX-Steeve.vitesse<x*BLOCKSIDE){return true;}
                else if(placement==1&&tilemap.floorBlocks.contains(String.valueOf(valeurGauche))){return false;}
                else if(tilemap.floorBlocks.contains(String.valueOf(placement)) && valeurGauche == 1 && posX-Steeve.vitesse<(x*BLOCKSIDE)+BLOCKSIDE){return false;}
                else{
                    System.out.println("ffff");
                    return true;}



            case 1:
                // droite

                if(posY%BLOCKSIDE==0){y++;}
                valeurDroite = tilemap.getValueOf(y,x+1);
                if(tilemap.floorBlocks.contains(String. valueOf(placement)) && tilemap.floorBlocks.contains(String.valueOf(valeurDroite))){
                    return false;
                }
                else if(placement==1 && valeurDroite==1){return false;}
                else if(tilemap.floorBlocks.contains(String. valueOf(placement)) && valeurDroite==1 && posX+Steeve.vitesse>(x+1)*BLOCKSIDE){ return true;}
                else if(placement==1&&tilemap.floorBlocks.contains(String.valueOf(valeurDroite))){return false;}
                else if(tilemap.floorBlocks.contains(String. valueOf(placement)) && valeurDroite == 1 && posX+personnageSize+Steeve.vitesse<(x+1)*BLOCKSIDE){return false;}
                else{return true;}

            case 2:
                // haut

                if(posY%BLOCKSIDE==0){y++;}
                valeurHaut = tilemap.getValueOf(y-1,x);

                if(tilemap.floorBlocks.contains(String. valueOf(placement)) &&tilemap.floorBlocks.contains(String.valueOf(valeurHaut))){
                    return false;
                }
                else if(placement==1 && valeurHaut==1){return false;}
                else if(tilemap.floorBlocks.contains(String. valueOf(placement)) && valeurHaut==1 && posY-Steeve.vitesse>(y+1)*BLOCKSIDE){return true;}
                else if(placement==1 &&tilemap.floorBlocks.contains(String.valueOf(valeurHaut))){return false;}
                else if(tilemap.floorBlocks.contains(String. valueOf(placement)) && valeurHaut == 1 && posY-Steeve.vitesse>(y)*BLOCKSIDE){return false;}
                else{return true;}

            case 3:
                // bas
                System.out.println(posY+personnageSize+Steeve.vitesse+"        "+(y+1)*BLOCKSIDE);
                if(posY%BLOCKSIDE==0){y++;}
                valeurBas = tilemap.getValueOf(y+1,x);
                if(tilemap.floorBlocks.contains(String. valueOf(placement)) && tilemap.floorBlocks.contains(String.valueOf(valeurBas))){
                    return false;
                }
                else if(placement==1 && valeurBas==1){return true;}
                else if(tilemap.floorBlocks.contains(String. valueOf(placement)) && valeurBas==1 && posY+personnageSize+Steeve.vitesse>(y+1)*BLOCKSIDE){return true;}
                else if(placement==1&&tilemap.floorBlocks.contains(String.valueOf(valeurBas))){return false;}
                else if(tilemap.floorBlocks.contains(String. valueOf(placement)) && valeurBas == 1 && posY+personnageSize+Steeve.vitesse>(y)*BLOCKSIDE){return false;}
                else{return true;}
        }

        return true;
    }

}
