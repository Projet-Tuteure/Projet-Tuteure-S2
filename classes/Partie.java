public class Partie {
  boolean gameOver;

  Partie(int initX, int initY){
    this.gameOver=false;
  }

  public void estTermine(){
    return this.gameOver;
  }
}
