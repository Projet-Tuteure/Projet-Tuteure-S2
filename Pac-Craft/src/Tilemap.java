

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Tilemap {
    final int BLOCKSIDE = 80;
    final String pathToTexture = "/img/";
    final int nbTexture = 2;

    int[][][] listeNiveaux = {
            {
                    {1,1,1,1,1,1,1,1,1,1,1},
                    {1,0,0,0,0,1,0,0,0,0,1},
                    {1,0,1,1,0,1,0,1,1,0,1},
                    {1,0,1,0,0,0,0,0,1,0,1},
                    {1,0,1,0,1,1,1,0,1,0,1},
                    {1,0,0,0,0,0,0,0,0,0,1},
                    {1,0,1,0,1,1,1,0,1,0,1},
                    {1,0,1,0,0,0,0,0,1,0,1},
                    {1,0,1,1,0,1,0,1,1,0,1},
                    {1,0,0,0,0,1,0,0,0,0,1},
                    {1,1,1,1,1,1,1,1,1,1,1},
            },
            {
                    {1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,0,0,0,0,1,0,0,0,0,1,1},
                    {1,0,1,1,0,1,0,1,1,0,1,1},
                    {1,0,1,0,0,0,0,0,1,0,1,1},
                    {1,0,1,0,1,1,1,0,1,0,1,1},
                    {1,0,0,0,0,0,0,0,0,0,1,1},
                    {1,0,1,0,1,1,1,0,1,0,1,1},
                    {1,0,1,0,0,0,0,0,1,0,1,1},
                    {1,0,1,1,0,1,0,1,1,0,1,1},
                    {1,0,0,0,0,1,0,0,0,0,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,0}
            }
    };

    int nbBlockWidth;
    int nbBlockHeight;
    Canvas canvas;
    GraphicsContext graphicsContext;
    Image[] pattern = new Image[nbTexture];
    int[][] map;

    Tilemap(int nMap) {
        this.map = listeNiveaux[nMap];
        this.nbBlockWidth = listeNiveaux[nMap][0].length;
        this.nbBlockHeight = listeNiveaux[nMap].length;
        //System.out.println(nbBlockHeight+","+nbBlockWidth);
        this.canvas = new Canvas(BLOCKSIDE * nbBlockWidth, BLOCKSIDE * nbBlockHeight);
        this.graphicsContext = canvas.getGraphicsContext2D();
        loadImages(this.pattern);
    }

    public void loadImages(Image[] tableauImage){
        for (int i=0; i<this.pattern.length; i++){
            this.pattern[i] = new Image(pathToTexture+String.valueOf(i)+".PNG");
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

}
