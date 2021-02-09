public class TileMap {
  final String[][] MAP1 = [
  ['x','x','x'],
  ['x','x','x'],
  ['x','x','x']
  ];

  int numeroMap;

  TileMap(int initX, int initY){
    this.numeroMap = 1;
  }

  public void setMap(nouvelleMap){
    this.nouvelleMap=numeroMap;
  }

  public String[][] getMap(numeroMap){
    switch (numeroMap) {
      case 1:
        return MAP1;
        break;
    }
  }
}
