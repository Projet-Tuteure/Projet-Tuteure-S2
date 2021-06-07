import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class UI extends Pane{

    private int pv;
    private int score;
    private Text scoreLbl;
    private Pane pvPane;
    private int blockSize;
    private Tilemap tm;

    public UI(Tilemap tm){
        this(tm,3,0);
    }

    public UI(Tilemap tm,int pv,int score){
        this.pv = pv;
        this.score=score;
        blockSize=tm.getBlockSide();
        this.tm=tm;

        initWidgets();
    }

    private void initWidgets(){
        int fontSize = 20;

        scoreLbl = new Text(Integer.toString(score));
        scoreLbl.setLayoutX(blockSize/2);
        scoreLbl.setLayoutY(blockSize/2+fontSize/2-2);
        Font minecraftFont = Font.loadFont( Main.class.getClassLoader().getResourceAsStream( "fonts/Minecraft.ttf"), fontSize);
        scoreLbl.setFont(minecraftFont);
        scoreLbl.setFill(Color.WHITE);

        pvPane = new Pane();
        updatePv(pvPane);

        getChildren().addAll(scoreLbl,pvPane);

    }

    public void updatePv(Pane pvPane){
        Image coeurTemplate = new Image("img/hearth.png");
        int yOrigin = blockSize*(tm.getNbBlockHeight()-1)+blockSize/2-(int)coeurTemplate.getHeight()/2;
        int xOrigin = 0;
        pvPane.setLayoutX(xOrigin);
        pvPane.setLayoutY(yOrigin);
        pvPane.getChildren().clear();

        int imgToDraw = pv;
        for(int i = 0 ; i < imgToDraw ; i++){
            Image coeur = new Image("img/hearth.png");
            ImageView coeurView = new ImageView(coeur);
            pvPane.getChildren().add(coeurView);
            coeurView.setLayoutX(i*blockSize+blockSize/2-(int)coeurTemplate.getWidth()/2);
        }
    }

    public void addPv(int i){
        pv+=i;
        updatePv(pvPane);
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
        updatePv(pvPane);
    }

    public void decrementPv() {
        this.pv -= 1;
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
