package menu;

import config.Tilemap;
import game.Controller;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class UI extends Pane{

    private int hp;
    private int coin;
    private Text coinLbl;
    private Pane hpPane;
    private final int blockSize;
    private final int halfBlockSize;
    private final Tilemap tilemap;

    /**
     * creates a ui with given tilemap
     * @param tilemap the tilemap
     */
    public UI(Tilemap tilemap){
        this(tilemap,3,0);
    }

    /**
     * creates a ui with given tilemap hp and coin value
     * @param tilemap the tilemap
     * @param hp the hp
     * @param coin the coin
     */
    public UI(Tilemap tilemap, int hp, int coin){
        this.blockSize = tilemap.getBlockSide();
        this.halfBlockSize = blockSize/2;
        this.tilemap = tilemap;
        this.hp = hp;
        this.coin = coin;

        initWidgets();
    }

    /**
     * initialize all ui components
     */
    private void initWidgets(){
        int fontSize = 20;
        Font minecraftFont = Font.loadFont( Controller.class.getClassLoader().getResourceAsStream("fonts/Minecraft.ttf"), fontSize);

        Pane scorePane = new Pane();

        ImageView coinImg = new ImageView(new Image("img/coin.png"));
        coinImg.setLayoutX(halfBlockSize - coinImg.getImage().getWidth()/2);
        coinImg.setLayoutY(halfBlockSize - coinImg.getImage().getHeight()/2);

        coinLbl = new Text(Integer.toString(coin));
        coinLbl.setLayoutX(halfBlockSize + coinImg.getImage().getWidth());
        coinLbl.setLayoutY((fontSize >> 1) + halfBlockSize - 2);
        coinLbl.setFont(minecraftFont);
        coinLbl.setFill(Color.WHITE);

        scorePane.getChildren().add(coinImg);
        scorePane.getChildren().add(coinLbl);
        hpPane = new Pane();
        updatePv(hpPane);
        getChildren().addAll(scorePane, hpPane);
    }


    /**
     * update the given hpPane according to the hp
     * @param hpPane the pane to update
     */
    public void updatePv(Pane hpPane){
        Image coeurTemplate = new Image("img/heart.png");
        int yOrigin = blockSize * (tilemap.getNbBlockHeight()-1) + blockSize/2 - (int)coeurTemplate.getHeight()/2;
        int xOrigin = 0;
        hpPane.setLayoutX(xOrigin);
        hpPane.setLayoutY(yOrigin);
        hpPane.getChildren().clear();

        int imgToDraw = hp;
        for(int i = 0; i < imgToDraw; i++){
            Image coeur = new Image("img/heart.png");
            ImageView coeurView = new ImageView(coeur);
            hpPane.getChildren().add(coeurView);
            coeurView.setLayoutX(((i * blockSize) + (blockSize >> 1)) - ((int) coeurTemplate.getWidth() / 2));
        }
    }

    /**
     * decrement hp and updates ui
     */
    public void decrementHp() {
        this.hp -= 1;
        updatePv(hpPane);
    }

    /**
     * sets coin to given coin and updates ui
     * @param coin the score
     */
    public void setCoin(int coin) {
        this.coin = coin;
        updateCoin();
    }

    /**
     * add given n to coin
     * @param n the number of coin to add
     */
    public void addToCoin(int n){
        coin += n;
        updateCoin();
    }

    /**
     * updates score's iu
     */
    private void updateCoin() {
        coinLbl.setText(Integer.toString(coin));
    }

    @Override
    public String toString() {
        return "menu.UI{" +
                "hp=" + hp +
                ", coin=" + coin +
                ", coinLbl=" + coinLbl +
                ", hpPane=" + hpPane +
                ", blockSize=" + blockSize +
                ", halfBlockSize=" + halfBlockSize +
                ", tilemap=" + tilemap +
                '}';
    }
}
