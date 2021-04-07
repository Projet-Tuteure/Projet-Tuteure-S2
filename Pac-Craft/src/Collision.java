

public class Collision {

    public static void collide(int pos_x, int pos_y,Tilemap tilemap,int direction,Personnage Steve){
        int [] posFinal;
        //System.out.println(Personnage_Size);
        //System.out.println("{"+pos_x+" , "+pos_y+" }");

        int nextX = pos_x ;
        int nextY =  pos_y ;

        switch (direction){
            case 0:
                // gauche
                nextX -=2;
                break;
            case 1:
                // droite
                nextX +=2;
                break;
            case 2:
                // haut
                nextY -=2;
                break;
            case 3:
                // bas
                nextY+=2;
                break;
        }

        if(!isColliding(Steve.pos_x, Steve.pos_y, tilemap.map, tilemap.BLOCKSIDE, direction,Steve.getHeight())){
            posFinal = new int[]{nextX, nextY};
        }else{
            posFinal = new int[]{pos_x,pos_y};
        }

        Steve.pos_x = posFinal[0];
        Steve.pos_y = posFinal[1];
    }


    public static boolean isColliding(int posX,int posY,int [][] tab,int BLOCKSIDE,int direction,int personnageSize ){
        int x =0;
        int y =0;
        int placement = 0;
        int valeurGauche = 0;
        int valeurDroite = 0;
        int valeurHaut = 0;
        int valeurBas = 0;


        switch (direction){
            case 0:
                // gauche

                while(posX>x*BLOCKSIDE){
                    x++;
                }
                while(posY>y*BLOCKSIDE){
                    y++;
                }
                if(x*BLOCKSIDE!=posX){
                    x--;
                }
               y--;

                if(posY%BLOCKSIDE==0){y++;}
                placement = tab[y][x];
                valeurGauche = tab[y][x-1];
                if(placement == 0 && valeurGauche == 0){
                    return false;
                }
                else if(placement==1 && valeurGauche==1){return false;}
                else if(placement==0 && valeurGauche==1 && posX==x*BLOCKSIDE){return true;}
                else if(placement==1&&valeurGauche==0){return false;}
                else if(placement == 0 && valeurGauche == 1 && posX-3<(x*BLOCKSIDE)+BLOCKSIDE){return false;}
                else{return true;}



            case 1:
                // droite

                while(posX>x*BLOCKSIDE){
                    x++;
                }
                while(posY>y*BLOCKSIDE){
                    y++;
                }
                if(x*BLOCKSIDE!=posX){
                    x--;
                }
                y--;
                if(posY%BLOCKSIDE==0){y++;}
                placement = tab[y][x];
                valeurDroite = tab[y][x+1];
                if(placement == 0 && valeurDroite == 0){
                    return false;
                }
                else if(placement==1 && valeurDroite==1){return false;}
                else if(placement==0 && valeurDroite==1 && posX==(x+1)*BLOCKSIDE){return true;}
                else if(placement==1&&valeurDroite==0){return false;}
                else if(placement == 0 && valeurDroite == 1 && posX+personnageSize+3<(x+1)*BLOCKSIDE){return false;}
                else{return true;}

            case 2:
                // haut
                while(posX>x*BLOCKSIDE){
                    x++;
                }
                while(posY>y*BLOCKSIDE){
                    y++;
                }
                if(x*BLOCKSIDE!=posX){
                    x--;
                }
                y--;

                if(posY%BLOCKSIDE==0){y++;}
                placement = tab[y][x];
                valeurHaut = tab[y-1][x];
                System.out.println(posY+"  "+(y)*BLOCKSIDE);
                System.out.println(placement+"            "+valeurHaut);
                if(placement == 0 && valeurHaut == 0){
                    return false;
                }
                else if(placement==1 && valeurHaut==1){return false;}
                else if(placement==0 && valeurHaut==1 && posY==(y+1)*BLOCKSIDE){return true;}
                else if(placement==1&&valeurHaut==0){return false;}
                else if(placement == 0 && valeurHaut == 1 && posY-3>(y)*BLOCKSIDE){return false;}
                else{return true;}

            case 3:
                // bas
                while(posX>x*BLOCKSIDE){
                    x++;
                }
                while(posY>y*BLOCKSIDE){
                    y++;
                }
                if(x*BLOCKSIDE!=posX){
                    x--;
                }
                y--;

                if(posY%BLOCKSIDE==0){y++;}
                placement = tab[y][x];
                valeurBas = tab[y+1][x];
                System.out.println(posY+"  "+(y)*BLOCKSIDE);
                System.out.println(placement+"            "+valeurBas);
                if(placement == 0 && valeurBas == 0){
                    return false;
                }
                else if(placement==1 && valeurBas==1){return false;}
                else if(placement==0 && valeurBas==1 && posY+personnageSize==(y+1)*BLOCKSIDE){return true;}
                else if(placement==1&&valeurBas==0){return false;}
                else if(placement == 0 && valeurBas == 1 && posY+personnageSize+3>(y)*BLOCKSIDE){return false;}
                else{return true;}
        }

        return true;
    }



}
