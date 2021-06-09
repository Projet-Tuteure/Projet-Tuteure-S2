import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Arrays;

public class Tilemap {
    final int BLOCKSIDE = 40;
    final String pathToTexture = "/img/";
    final int nbTexture = 5;

    int[][][] levelList = {
/*            {
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,3,0,0,0,0,0,0,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,0,1,1,4,1,1,0,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,0,1,0,0,0,1,0,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,0,1,1,1,1,1,0,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,0,0,0,0,0,0,0,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
            },*/
            {
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,2,2,2,2,2,2,2,2,2,1,2,2,2,2,2,2,2,2,2,1},
                    {1,2,1,1,1,1,1,2,1,2,2,2,1,2,1,1,1,1,1,2,1},
                    {1,2,2,3,1,2,2,2,1,1,1,1,1,2,2,2,1,3,2,2,1},
                    {1,1,1,2,1,2,1,2,2,2,2,2,2,2,1,2,1,2,1,1,1},
                    {1,2,2,2,2,2,1,2,1,1,4,1,1,2,1,2,2,2,2,2,1},
                    {1,2,1,1,1,1,1,2,1,0,0,0,1,2,1,1,1,1,1,2,1},
                    {1,2,2,2,2,2,2,2,1,1,1,1,1,2,2,2,2,2,2,2,1},
                    {1,2,1,1,1,1,2,1,1,2,0,2,1,1,2,1,1,1,1,2,1},
                    {1,2,2,2,3,1,2,1,2,2,1,2,2,1,2,1,3,2,2,2,1},
                    {1,2,1,1,2,1,2,1,2,1,1,1,2,1,2,1,2,1,1,2,1},
                    {1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
            },
            {
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,2,2,2,1,2,1,2,2,2,1,2,2,2,1,2,1,2,2,2,1},
                    {1,2,1,2,2,2,1,2,1,2,2,2,1,2,1,2,2,2,1,2,1},
                    {1,2,1,1,1,2,1,2,1,1,1,1,1,2,1,2,1,1,1,2,1},
                    {1,2,2,2,1,2,2,2,2,2,2,2,2,2,2,2,1,2,2,2,1},
                    {1,2,1,2,2,2,1,2,1,1,4,1,1,2,1,2,2,2,1,2,1},
                    {1,2,1,1,1,1,1,2,1,0,0,0,1,2,1,1,1,1,1,2,1},
                    {1,2,1,2,2,2,1,2,1,1,1,1,1,2,1,2,2,2,1,2,1},
                    {1,2,2,2,1,2,1,2,2,2,0,2,2,2,1,2,1,2,2,2,1},
                    {1,1,1,2,1,2,2,2,1,1,1,1,1,2,2,2,1,2,1,1,1},
                    {1,2,1,2,1,2,1,2,1,2,2,2,1,2,1,2,1,2,1,2,1},
                    {1,2,2,2,2,2,1,2,2,2,1,2,2,2,1,2,2,2,2,2,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
            },
            {
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,2,2,2,1,2,2,2,1,2,2,2,1,2,2,2,1,2,2,2,1},
                    {1,2,1,2,1,2,1,2,2,2,1,2,2,2,1,2,1,2,1,2,1},
                    {1,2,1,2,2,2,1,1,1,2,1,2,1,1,1,2,2,2,1,2,1},
                    {1,2,1,1,1,2,1,2,2,2,2,2,2,2,1,2,1,1,1,2,1},
                    {1,2,2,2,1,2,2,2,1,1,4,1,1,2,2,2,1,2,2,2,1},
                    {1,2,1,2,2,2,1,2,1,0,0,0,1,2,1,2,2,2,1,2,1},
                    {1,2,1,2,1,1,1,2,1,1,1,1,1,2,1,1,1,2,1,2,1},
                    {1,2,2,2,1,2,2,2,2,2,0,2,2,2,2,2,1,2,2,2,1},
                    {1,2,1,2,2,2,1,2,1,1,1,1,1,2,1,2,2,2,1,2,1},
                    {1,2,1,2,1,1,1,2,2,2,1,2,2,2,1,1,1,2,1,2,1},
                    {1,2,2,2,2,2,2,2,1,2,2,2,1,2,2,2,2,2,2,2,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
            }
    };

    int nbBlockWidth;
    int nbBlockHeight;
    int currentLevel;
    Canvas canvas;
    GraphicsContext graphicsContext;
    Image[] pattern = new Image[nbTexture];
    int[][] map;
    ArrayList<Integer> floorBlocks = new ArrayList<Integer>();
    ArrayList<Integer> floorBlocksZombie = new ArrayList<Integer>();


    /**
     * Constructor of the tilemap given the number of the map
     * @param nMap the number of the map
     */
    Tilemap(int nMap) {
        this.map = levelList[nMap];
        this.nbBlockWidth = levelList[nMap][0].length;
        this.nbBlockHeight = levelList[nMap].length;
        this.canvas = new Canvas(BLOCKSIDE * nbBlockWidth, BLOCKSIDE * nbBlockHeight);
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.currentLevel = nMap;
        loadImages(this.pattern);

        //players can walk on these
        floorBlocks.add(0);
        floorBlocks.add(2);
        floorBlocks.add(3);
        //zombies can walk on these
        floorBlocksZombie.add(0);
        floorBlocksZombie.add(2);
        floorBlocksZombie.add(3);
        floorBlocksZombie.add(4);
    }

    /**
     * Load all images
     * @param tabImage tap of images
     */
    public void loadImages(Image[] tabImage){
        for (int i = 0; i < this.pattern.length; i++)
            this.pattern[i] = new Image(this.pathToTexture+String.valueOf(i)+".png");
    }

    /**
     * Draw the map in the graphicsContext
     * @param tilemap map to draw
     * @param gc graphic content to use
     */
    public void display(int[][] tilemap, GraphicsContext gc) {
        for (int i = 0; i < tilemap[0].length; i++) {
            for (int j = 0; j < tilemap.length; j++) {
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

    public int getValueOf(int x ,int y){ return levelList[this.currentLevel][x][y];}

    public int getTileX(int x){
        return x/BLOCKSIDE;
    }

    public int getTileY(int y){
        return y/BLOCKSIDE;
    }

    public int getTileFromXYTile(int xTile, int yTile){
        return this.levelList[this.currentLevel][yTile][xTile];
    }

    public int getTileFromXY(int x, int y){
        return getTileFromXYTile(getTileX(x),getTileY(y));
    }

    /**
     * Return tab of map
     * @param level of map
     * @return map of desired level
     */
    public int[][] getMap(int level) {
        return this.levelList[level];
    }

    /**
     * 
     * @param x position X
     * @param y position Y
     * @return boolean if is in the center of block
     */
    public boolean isCenter(int x, int y){
        if (x%(BLOCKSIDE/2)==0 && y%(BLOCKSIDE/2)==0 && x%BLOCKSIDE!=0 && y%BLOCKSIDE!=0){
            return true;
        }
        return false;
    }

    /**
     * Number of coins in the tilemap
     * @return number of coins in the til
     */
    public int getNumberOfCoin(){
        int counter =0;
        for(int i = 0; i< levelList[this.currentLevel].length; i++){
            for(int l = 0; l< levelList[this.currentLevel][i].length; l++){
                if(this.getValueOf(i,l)==2){
                    counter++;
                }
            }
        }
        return counter;
    }
}
