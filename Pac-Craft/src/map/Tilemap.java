package map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Arrays;

public class Tilemap {
    final int BLOCKSIDE = 40;
    final String pathToTexture = "/ressources/img/";
    final int nbTexture = 5;

    int[][][] levelList = {
            // Cahier des charges demonstration
            {
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1},
                    {1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1},
                    {1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
                    {1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
                    {1,1,0,0,0,0,0,0,3,0,0,0,0,0,2,0,2,0,0,1,1},
                    {1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
            },
            // tilemap demonstration
            {
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1},
                    {1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1},
                    {1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,2,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,1},
                    {1,1,0,1,0,1,0,1,0,0,0,0,0,0,0,0,1,0,0,1,1},
                    {1,1,0,1,0,1,0,1,0,0,0,0,0,0,0,0,4,0,0,1,1},
                    {1,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
            },
            // Coins demonstration
            {
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1},
                    {1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1},
                    {1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,0,0,0,0,0,0,2,2,2,2,2,0,0,0,0,0,0,1,1},
                    {1,1,0,0,2,2,0,0,0,0,0,0,0,0,0,2,2,0,0,1,1},
                    {1,1,0,0,2,2,0,0,0,0,0,0,0,0,0,2,2,0,0,1,1},
                    {1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
            },
            // Bonus demonstration
            {
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1},
                    {1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1},
                    {1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1},
                    {1,1,0,0,0,0,0,3,0,0,0,0,0,3,0,0,0,0,0,1,1},
                    {1,1,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
                    {1,1,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
                    {1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
            },
            // Player demonstration
            {
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1},
                    {1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1},
                    {1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1},
                    {1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
                    {1,1,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
                    {1,1,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
                    {1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
            },
            // Zombies demonstration
            {
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
                    {1,1,0,0,0,0,0,1,1,1,0,1,1,1,1,0,1,1,0,1,1},
                    {1,1,0,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1},
                    {1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1},
                    {1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
                    {1,1,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
                    {1,1,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
                    {1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
            },
            // Sound demonstration
            {
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1},
                    {1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1},
                    {1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1},
                    {1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
                    {1,1,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,1,1},
                    {1,1,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
                    {1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
            },
            // Conclusion demonstration
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
                    {1,2,2,2,1,3,1,2,2,2,1,2,2,2,1,3,1,2,2,2,1},
                    {1,2,1,2,2,2,1,2,1,2,2,2,1,2,1,2,2,2,1,2,1},
                    {1,2,1,1,1,2,1,2,1,1,1,1,1,2,1,2,1,1,1,2,1},
                    {1,2,2,2,1,2,2,2,2,2,2,2,2,2,2,2,1,2,2,2,1},
                    {1,2,1,2,2,2,1,2,1,1,4,1,1,2,1,2,2,2,1,2,1},
                    {1,2,1,1,1,1,1,2,1,0,0,0,1,2,1,1,1,1,1,2,1},
                    {1,2,1,2,2,2,1,2,1,1,1,1,1,2,1,2,2,2,1,2,1},
                    {1,2,2,2,1,2,1,2,2,2,0,2,2,2,1,2,1,2,2,2,1},
                    {1,1,1,2,1,2,2,2,1,1,1,1,1,2,2,2,1,2,1,1,1},
                    {1,3,1,2,1,2,1,2,1,2,2,2,1,2,1,2,1,2,1,3,1},
                    {1,2,2,2,2,2,1,2,2,2,1,2,2,2,1,2,2,2,2,2,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
            },
            {
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,2,2,2,1,2,2,2,1,2,2,2,1,2,2,2,1,2,2,2,1},
                    {1,2,1,2,1,2,1,2,2,2,1,2,2,2,1,2,1,2,1,2,1},
                    {1,2,1,2,3,2,1,1,1,2,1,2,1,1,1,2,3,2,1,2,1},
                    {1,2,1,1,1,2,1,2,2,2,2,2,2,2,1,2,1,1,1,2,1},
                    {1,2,2,2,1,2,2,2,1,1,4,1,1,2,2,2,1,2,2,2,1},
                    {1,2,1,2,2,2,1,2,1,0,0,0,1,2,1,2,2,2,1,2,1},
                    {1,2,1,2,1,1,1,2,1,1,1,1,1,2,1,1,1,2,1,2,1},
                    {1,2,3,2,1,2,2,2,2,2,0,2,2,2,2,2,1,2,3,2,1},
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
    ArrayList<Integer> floorBlocks = new ArrayList<>();
    ArrayList<Integer> floorBlocksZombie = new ArrayList<>();


    /**
     * Constructor of the tilemap given the number of the map
     * @param nMap the number of the map
     */
    public Tilemap(int nMap) {
        nMap%=levelList.length;
        this.map = levelList[nMap];
        this.nbBlockWidth = levelList[nMap][0].length;
        this.nbBlockHeight = levelList[nMap].length;
        this.canvas = new Canvas(BLOCKSIDE * nbBlockWidth, BLOCKSIDE * nbBlockHeight);
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.currentLevel = nMap;
        loadImages();

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
     */
    public void loadImages(){
        for (int i = 0; i < this.pattern.length; i++)
            this.pattern[i] = new Image(this.pathToTexture + "texture-" + i + ".png");
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

    /**
     * @return the block width/height
     */
    public int getBlockSide(){
        return this.BLOCKSIDE;
    }

    /**
     * @return the number of blocks in y axis
     */
    public int getNbBlockHeight(){
        return this.nbBlockHeight;
    }

    /**
     * @return the number of blocks in x axis
     */
    public int getNbBlockWidth(){
        return this.nbBlockWidth;
    }

    /**
     * Gets the type of tilemap block at given coordinates
     * @param x the x coordinate of current level
     * @param y the y coordinate of current level
     * @return the type of block
     */
    public int getValueOf(int x ,int y){ return levelList[this.currentLevel][x][y];}

    /**
     * @param x x position
     * @return tilemap x corresponding to given x
     */
    public int getTileX(int x){
        return x/BLOCKSIDE;
    }

    /**
     * @param y y position
     * @return tilemap y corresponding to given y
     */
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
     * Compute the postion and get if it is centered
     * @param x position X
     * @param y position Y
     * @return boolean if is in the center of block
     */
    public boolean isCenter(int x, int y){
        return x % (BLOCKSIDE / 2) == 0 && y % (BLOCKSIDE / 2) == 0 && x % BLOCKSIDE != 0 && y % BLOCKSIDE != 0;
    }

    /**
     * Number of coins in the tilemap
     * @return number of coins in the tile map
     */
    public int getNumberOfCoin(){
        int counter = 0;
        for(int i = 0; i < levelList[this.currentLevel].length; i++){
            for(int j = 0; j < levelList[this.currentLevel][i].length; j++){
                if(this.getValueOf(i, j) == 2){
                    counter++;
                }
            }
        }
        return counter;
    }

    /**
     * @return the map
     */
    public int[][] getMap() {
        return map;
    }

    @Override
    public String toString() {
        return "config.Tilemap{" +
                "BLOCKSIDE=" + BLOCKSIDE +
                ", pathToTexture='" + pathToTexture + '\'' +
                ", nbTexture=" + nbTexture +
                ", levelList=" + Arrays.toString(levelList) +
                ", nbBlockWidth=" + nbBlockWidth +
                ", nbBlockHeight=" + nbBlockHeight +
                ", currentLevel=" + currentLevel +
                ", canvas=" + canvas +
                ", graphicsContext=" + graphicsContext +
                ", pattern=" + Arrays.toString(pattern) +
                ", map=" + Arrays.toString(map) +
                ", floorBlocks=" + floorBlocks +
                ", floorBlocksZombie=" + floorBlocksZombie +
                '}';
    }
}
