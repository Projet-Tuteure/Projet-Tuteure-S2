

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.ArrayList;

public class Tilemap {
    final int BLOCKSIDE = 40;
    final String pathToTexture = "/Img/";
    final int nbTexture = 4;

    int[][][] listeNiveaux = {
            {
                    {1,1,1,1,1,1,1,1,1,1,1},
                    {1,2,0,0,0,1,0,0,0,0,1},
                    {1,2,1,1,0,1,0,1,1,0,1},
                    {1,3,1,0,0,0,0,0,1,0,1},
                    {1,0,1,0,1,1,1,0,1,0,1},
                    {1,0,0,0,0,0,0,0,0,0,1},
                    {1,0,1,0,1,1,1,0,1,0,1},
                    {1,0,1,0,0,0,0,0,1,0,1},
                    {1,0,1,1,0,1,0,1,1,0,1},
                    {1,0,0,0,0,1,0,0,0,0,1},
                    {1,1,1,1,1,1,1,1,1,1,1},
            },

            {
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,1},
                    {1,0,1,1,1,0,1,0,1,1,1,1,1,0,1,0,1,1,1,0,1},
                    {1,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,1},
                    {1,0,1,0,1,1,1,0,1,0,1,0,1,0,1,1,1,0,1,0,1},
                    {1,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,1},
                    {1,0,1,1,1,1,1,0,1,1,1,1,1,0,1,1,1,1,1,0,1},
                    {1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1},
                    {1,1,1,0,1,0,1,0,1,1,0,1,1,0,1,0,1,0,1,1,1},
                    {1,0,0,0,0,0,1,0,1,0,0,0,1,0,1,0,0,0,0,0,1},
                    {1,0,1,1,1,1,1,0,1,1,1,1,1,0,1,1,1,1,1,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,1,1,1,1,0,1,1,0,1,0,1,1,0,1,1,1,1,0,1},
                    {1,0,0,0,0,1,0,1,0,0,1,0,0,1,0,1,0,0,0,0,1},
                    {1,0,1,1,0,1,0,1,0,1,1,1,0,1,0,1,0,1,1,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,1,1,1,1,0,1,0,1,1,1,0,1,0,1,1,1,1,0,1},
                    {1,0,1,0,0,0,0,1,0,0,1,0,0,1,0,0,0,0,1,0,1},
                    {1,0,1,0,1,1,1,1,1,0,1,0,1,1,1,1,1,0,1,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
            }
    };

    int nbBlockWidth;
    int nbBlockHeight;
    int niveauCourant;
    Canvas canvas;
    GraphicsContext graphicsContext;
    Image[] pattern = new Image[nbTexture];
    int[][] map;
    ArrayList<Integer> floorBlocks = new ArrayList<Integer>();


    Tilemap(int nMap) {
        this.map = listeNiveaux[nMap];
        this.nbBlockWidth = listeNiveaux[nMap][0].length;
        this.nbBlockHeight = listeNiveaux[nMap].length;
        this.canvas = new Canvas(BLOCKSIDE * nbBlockWidth, BLOCKSIDE * nbBlockHeight);
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.niveauCourant = nMap;
        loadImages(this.pattern);

        //Initialisation de la liste de blocks sur lesquels on peut marcher
        floorBlocks.add(0);
        floorBlocks.add(2);
        floorBlocks.add(3);
    }

    public void loadImages(Image[] tableauImage){
        for (int i=0; i<this.pattern.length; i++){
            this.pattern[i] = new Image(pathToTexture+String.valueOf(i)+".png");
        }
    }

    public void display(int[][] map, GraphicsContext gc) {
        for (int i = 0; i < map[0].length; i++) {
            for (int j = 0; j < map.length; j++) {
                gc.drawImage(pattern[this.map[j][i]], i  * BLOCKSIDE, j * BLOCKSIDE);
            }
        }
    }

    public int getBlockSide(){
        return this.BLOCKSIDE;
    }

    public int getNbBlockHeight(){
        return this.nbBlockHeight;
    }

    public int getNbBlockWidth(){
        return this.nbBlockWidth;
    }

    public Canvas getCanvas(){
        return canvas;
    }

    public int getValueOf(int x ,int y){ return listeNiveaux[this.niveauCourant][x][y];}

    public int getTileX(int x){
        return x/BLOCKSIDE;
    }

    public int getTileY(int y){
        return y/BLOCKSIDE;
    }

    public int getTileFromXYTile(int xTile, int yTile){
        return this.listeNiveaux[this.niveauCourant][yTile][xTile];
    }

    public int getTileFromXY(int x, int y){
        return getTileFromXYTile(getTileX(x),getTileY(y));
    }

    public boolean isCenter(int x, int y){
        if (x%(BLOCKSIDE/2)==0 && y%(BLOCKSIDE/2)==0 && x%BLOCKSIDE!=0 && y%BLOCKSIDE!=0){
            return true;
        }
        return false;
    }

    public int getNumberOfCoin(){
        int compteur =0;
        for(int i =0;i< listeNiveaux[this.niveauCourant].length;i++){
            for(int l =0;l< listeNiveaux[this.niveauCourant][i].length;l++){
                if(this.getValueOf(i,l)==2){
                    compteur++;
                }
            }
        }
        return  compteur;
    }
}
