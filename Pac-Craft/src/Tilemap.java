

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.ArrayList;

public class Tilemap {
    final int BLOCKSIDE = 40;
    final String pathToTexture = "/img/";
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
    ArrayList<String> floorBlocks = new ArrayList<String>();


    Tilemap(int nMap,int numNiveau) {
        this.map = listeNiveaux[nMap];
        this.nbBlockWidth = listeNiveaux[nMap][0].length;
        this.nbBlockHeight = listeNiveaux[nMap].length;
        //System.out.println(nbBlockHeight+","+nbBlockWidth);
        this.canvas = new Canvas(BLOCKSIDE * nbBlockWidth, BLOCKSIDE * nbBlockHeight);
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.niveauCourant = numNiveau;
        loadImages(this.pattern);
        floorBlocks.add("0");
        floorBlocks.add("2");
        floorBlocks.add("3");
    }

    public void loadImages(Image[] tableauImage){
        for (int i=0; i<this.pattern.length; i++){
            this.pattern[i] = new Image(pathToTexture+String.valueOf(i)+".png");
        }
    }

    public void display(int[][] map,Image Steve,int posx,int posy) {
        for (int i = 0; i < map[0].length; i++) {
            for (int j = 0; j < map.length; j++) {
                graphicsContext.drawImage(pattern[this.map[j][i]], i  * BLOCKSIDE, j * BLOCKSIDE);
            }
        }
        graphicsContext.drawImage(Steve,posx,posy);
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
