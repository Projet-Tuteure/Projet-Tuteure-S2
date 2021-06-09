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
    private int blockSize;
    private int halfBlockSize;
    private Tilemap tilemap;

    /**
     * creates a ui with given tilemap
     * @param tilemap
     */
    public UI(Tilemap tilemap){
        this(tilemap,3,0);
    }

    /**
     * creates a ui with given tilemap hp and score value
     * @param tilemap
     * @param hp
     * @param score
     */
    public UI(Tilemap tilemap, int hp, int score){
        this.blockSize= tilemap.getBlockSide();
        this.halfBlockSize = blockSize/2;
        this.tilemap = tilemap;
        this.hp = hp;
        this.coin =score;

        initWidgets();
    }

    /**
     * initialize all ui components
     */
    private void initWidgets(){
        int fontSize = 20;
        Font minecraftFont = Font.loadFont( Main.class.getClassLoader().getResourceAsStream("fonts/Minecraft.ttf"), fontSize);

        Pane scorePane = new Pane();

        ImageView coinImg = new ImageView(new Image("img/coin.png"));
        coinImg.setLayoutX(halfBlockSize - coinImg.getImage().getWidth()/2);
        coinImg.setLayoutY(halfBlockSize - coinImg.getImage().getHeight()/2);

        coinLbl = new Text(Integer.toString(coin));
        coinLbl.setLayoutX(halfBlockSize + coinImg.getImage().getWidth());
        coinLbl.setLayoutY(halfBlockSize + fontSize/2 - 2);
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
            coeurView.setLayoutX(i * blockSize + blockSize/2 - (int) coeurTemplate.getWidth()/2);
        }
    }

    /**
     * adds i hp and updates ui
     * @param i number of pv to add
     */
    public void addHp(int i){
        hp+=i;
        updatePv(hpPane);
    }

    /**
     * @return the number of hp
     */
    public int getHp() {
        return hp;
    }

    /**
     * set hp to given hp
     * @param hp the number of hp
     */
    public void setHp(int hp) {
        this.hp = hp;
        updatePv(hpPane);
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
        coin +=n;
        updateCoin();
    }

    /**
     * updates score's iu
     */
    private void updateCoin() {
        coinLbl.setText(Integer.toString(coin));
    }
}
