import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class UI extends Pane{

    private int hp;
    private int score;
    private Text scoreLbl;
    private ImageView coinImg;
    private Pane pvPane;
    private int blockSize;
    private int halfBlockSize;
    private Tilemap tilemap;

    public UI(Tilemap tilemap){
        this(tilemap,3,0);
    }

    public UI(Tilemap tilemap, int hp, int score){
        this.blockSize= tilemap.getBlockSide();
        this.halfBlockSize = blockSize/2;
        this.tilemap = tilemap;
        this.hp = hp;
        this.score=score;

        initWidgets();
    }

    private void initWidgets(){
        int fontSize = 20;
        Font minecraftFont = Font.loadFont( Main.class.getClassLoader().getResourceAsStream("fonts/Minecraft.ttf"), fontSize);

        // Création du compteur de pièces
        Pane scorePane = new Pane();

        coinImg = new ImageView(new Image("img/coin.png"));
        coinImg.setLayoutX(halfBlockSize-coinImg.getImage().getWidth()/2);
        coinImg.setLayoutY(halfBlockSize-coinImg.getImage().getHeight()/2);

        scoreLbl = new Text(Integer.toString(score));
        scoreLbl.setLayoutX(halfBlockSize+coinImg.getImage().getWidth());
        scoreLbl.setLayoutY(halfBlockSize+fontSize/2-2);
        scoreLbl.setFont(minecraftFont);
        scoreLbl.setFill(Color.WHITE);

        scorePane.getChildren().add(coinImg);
        scorePane.getChildren().add(scoreLbl);
        pvPane = new Pane();
        updatePv(pvPane);
        getChildren().addAll(scorePane,pvPane);
    }

    public void updatePv(Pane pvPane){
        Image coeurTemplate = new Image("img/heart.png");
        int yOrigin = blockSize*(tilemap.getNbBlockHeight()-1)+blockSize/2-(int)coeurTemplate.getHeight()/2;
        int xOrigin = 0;
        pvPane.setLayoutX(xOrigin);
        pvPane.setLayoutY(yOrigin);
        pvPane.getChildren().clear();

        int imgToDraw = hp;
        for(int i = 0 ; i < imgToDraw ; i++){
            Image coeur = new Image("img/heart.png");
            ImageView coeurView = new ImageView(coeur);
            pvPane.getChildren().add(coeurView);
            coeurView.setLayoutX(i*blockSize+blockSize/2-(int)coeurTemplate.getWidth()/2);
        }
    }

    public void addPv(int i){
        hp+=i;
        updatePv(pvPane);
    }

    public int getPv() {
        return hp;
    }

    public void setPv(int hp) {
        this.hp = hp;
        updatePv(pvPane);
    }

    public void decrementPv() {
        this.hp -= 1;
        updatePv(pvPane);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        updateScore();
    }

    public void addToScore(int n){
        score+=n;
        updateScore();
    }

    private void updateScore() {
        scoreLbl.setText(Integer.toString(score));
    }
}
