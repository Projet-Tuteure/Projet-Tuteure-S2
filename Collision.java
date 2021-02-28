package sample;

public class Collision {

    public static int [] collide(int pos_x, int pos_y,Tilemap tilemap,int direction,int Personnage_Size){
        int [] posFinal;
        //System.out.println(Personnage_Size);
        //System.out.println("{"+pos_x+" , "+pos_y+" }");
        boolean allowed = true;
        int nextX = pos_x ;
        int nextY =  pos_y ;

        switch (direction){
            case 0:
                // gauche
                nextX -=10;
                break;
            case 1:
                // droite
                nextX +=10;
                break;
            case 2:
                // haut
                nextY -=10;
                break;
            case 3:
                // bas
                nextY+=10;
                break;
        }

        if(!isColliding(nextX,nextY, tilemap.map, tilemap.BLOCKSIDE, direction)){
            posFinal = new int[]{nextX, nextY};
        }else{
            posFinal = new int[]{pos_x,pos_y};
        }
       // System.out.println("Carré de " +" Haut_Gauche = " + nextX+" , "+ nextY);
        //System.out.println("Carré de " +" Bas_Droit = " + nextX+Personnage_Size+" , "+ nextY+Personnage_Size);
        return posFinal;
    }


    public static boolean isColliding(int posNextX,int posNextY,int [][] tab,int BLOCKSIDE,int direction ){
        boolean collide = true;

        boolean xwasGood = false;
        boolean ywasGood = false;

        boolean xGood = false;
        boolean yGood = false;

        int a=0;
        int b=0;

        for (int i = 0; i < tab[0].length; i++) {
            for (int j = 0; j < tab.length; j++) {
                 xGood = false;
                 yGood = false;
                 xwasGood = false;
                 ywasGood = false;

                 if(i*BLOCKSIDE<posNextX){ xwasGood=true;}
                if(j*BLOCKSIDE<posNextY){ ywasGood=true;}
                if((i+1)*BLOCKSIDE<posNextX){xGood=true;}
                if((i+1)*BLOCKSIDE<posNextY){yGood=true;}
                a=i;
                b=j;
                if(xGood==true&&yGood==true&&xwasGood==true&&ywasGood==true){break;}
            }
        }
        System.out.println("Position à peu près = "+a+"  "+b);
        switch (direction){
            case 0:
                // gauche

                break;
            case 1:
                // droite

                break;
            case 2:
                // haut

                break;
            case 3:
                // bas

                break;
        }

        return collide;
    }



}
