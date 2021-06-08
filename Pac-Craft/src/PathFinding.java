import java.util.*;

public class PathFinding {
    ArrayList<Case> ferme = new ArrayList<Case>();
    ArrayList<Case> ouverte = new ArrayList<Case>();
    ArrayList<Case> path = new ArrayList<>();

    boolean go = false;

    // les lignes 0 et les colonnes 0 sont ignorées dans le calcul du chemain
    // elles nous permettent seulement d'avoir un tableau de recherche commençant à 1 au lieu de 0;
    private int[][] tilemap;
    Case debut;
    Case fin;


    public PathFinding() {
    }

    public PathFinding(Case debut, Case fin, int[][] tilemap) {
        this.tilemap = tilemap;
        if (this.tilemap[debut.getX()][debut.getY()] != 1 && this.tilemap[fin.getX()][fin.getY()] != 1) {
            System.out.println("je suis dans le if de pathfinding");
            this.debut = debut;
            this.fin = fin;
            ajoutAdjacentAOuverte(debut);
        } else {
            System.out.println("Vous êtes sur un mur !!!");
            //path.add(null);
        }
        System.out.println("tilemap " + Arrays.deepToString(this.tilemap));
        System.out.println("debut " + this.debut);
        System.out.println("fin " + this.fin);
    }

    public void ajoutAdjacentAOuverte(Case debut) {
        int xc, yc;

        Case courante = debut;
        Case memoire = null;

        while (!isInList(fin, ferme)) {

            if (!isInList(courante, ferme)) {
                ferme.add(courante);

                xc = courante.getX();
                yc = courante.getY();

                if ((yc >= 1) && (tilemap[xc][yc - 1] != 1)) ajoutOuverte(courante, (new Case(xc, yc - 1)));//////
                if ((yc < tilemap[0].length - 1) && (tilemap[xc][yc + 1] != 1))
                    ajoutOuverte(courante, (new Case(xc, yc + 1)));//////
                if (xc < tilemap.length - 1) {
                    if ((tilemap[xc + 1][yc] != 1)) ajoutOuverte(courante, (new Case(xc + 1, yc)));///////
                    if ((yc >= 1) && (tilemap[xc + 1][yc - 1] != 1) && !((tilemap[xc][yc - 1] == 1) || (tilemap[xc + 1][yc] == 1)))
                        ajoutOuverte(courante, (new Case(xc + 1, yc - 1))); //les 2 dernieres conditions: !((pos[xc][yc-1] == 1) || (pos[xc+1][yc] == 1)) pour eviter de sauter par dessus les coins des murs
                    if ((yc < tilemap[0].length - 1) && (tilemap[xc + 1][yc + 1] != 1) && !((tilemap[xc + 1][yc] == 1) || (tilemap[xc][yc + 1] == 1)))
                        ajoutOuverte(courante, (new Case(xc + 1, yc + 1)));//les 2 dernieres conditions: !((pos[xc+1][yc] == 1) || (pos[xc][yc+1] == 1)) pour eviter de sauter par dessus les coins des murs
                }
                if (xc >= 1) {
                    if ((tilemap[xc - 1][yc] != 1))
                        ajoutOuverte(courante, (new Case(xc - 1, yc)));
                    if ((yc >= 1) && (tilemap[xc - 1][yc - 1] != 1) && !((tilemap[xc - 1][yc] == 1) || (tilemap[xc][yc - 1] == 1)))
                        ajoutOuverte(courante, (new Case(xc - 1, yc - 1)));//les 2 dernieres conditions: !((pos[xc-1][yc] == 1) || (pos[xc][yc-1] == 1)) pour eviter de sauter par dessus les coins des murs
                    if ((yc < tilemap[0].length - 1) && (tilemap[xc - 1][yc + 1] != 1) && !((tilemap[xc - 1][yc] == 1) || (tilemap[xc][yc + 1] == 1)))
                        ajoutOuverte(courante, (new Case(xc - 1, yc + 1)));///les 2 dernieres conditions: !((pos[xc-1][yc] == 1) || (pos[xc][yc+1] == 1)) pour eviter de sauter par dessus les coins des murs
                }

                memoire = courante;
            }

            ouverte.remove(courante);
            if (ouverte.isEmpty()) {
                if (Math.abs(memoire.getX() - fin.getX()) >= 1 && Math.abs(memoire.getY() - fin.getY()) >= 1)// pour permettre les sauts des coins des murs remplacer >= par > et && par ||
                {
                    System.out.println("Il n'y a pas de chemin entre ces deux point !!!");
                    break;
                } else break;
            }
            courante = getMinF();
        }
        fin.setParent(memoire);
        getParentPath();
        //dessine();
        //dessineResult();
    }

    public void afficheList(ArrayList<Case> list) {
        Iterator<Case> ito = list.iterator();
        Case test;
        while (ito.hasNext()) {
            test = ito.next();
            System.out.println(test.toString() + "-->:" + " X= " + test.getY() + " Y= " + test.getX() + " F= " + test.getF());
        }
    }

    public boolean isInList(Case courante, ArrayList<Case> list) {
        Iterator ito = list.iterator();
        while (ito.hasNext()) {
            if (ito.next().equals(courante))
                return true;
        }
        return false;
    }

    public void ajoutOuverte(Case courante, Case adjacente) {
        int g = courante.getG() + ((adjacente.getX() == courante.getX() || adjacente.getY() == courante.getY()) ? 10 : 15);
        int h = (Math.abs(adjacente.getX() - fin.getX()) + Math.abs(adjacente.getY() - fin.getY()));
        int f = g + h;
        if (isInList(adjacente, ouverte)) {
            if (adjacente.getF() > f) {
                adjacente.setG(g);
                adjacente.setF(f);
                adjacente.setParent(courante);
            }
        } else if (!isInList(adjacente, ferme)) {
            adjacente.setG(g);
            adjacente.setH(h);
            adjacente.setF(f);
            adjacente.setParent(courante);
            ouverte.add(adjacente);
        }
    }

    public int[][] getPos() {
        return this.tilemap;
    }

    public void setPos(int[][] pos) {
        this.tilemap = pos;
    }

    public ArrayList<Case> getPath() {
        return path;
    }

    public void setPath(ArrayList<Case> path) {
        this.path = path;
    }

    public Case getLastElement(){
        return this.path.get(this.path.size()-1);
    }

    public Case getMinF() {
        Case min = null;
        min = ouverte.get(0);
        Iterator<Case> fIt = ouverte.iterator();

        while (fIt.hasNext()) {
            min = compareF(min, fIt.next());
        }
        return min;
    }

    public void getParentPath() {
        Case curr = this.fin;
        while (!curr.equals(debut)) {
            path.add(curr);
            curr = curr.getParent();
        }
        //path.add(debut);
        this.go = true;
    }

    public Case compareF(Case cF1, Case cF2) {
        if (cF1.getF() < cF2.getF()) return cF1;
        return cF2;
    }

    @Override
    public String toString() {
        return "PathFinding{" +
                "ferme=" + ferme +
                "\n, ouverte=" + ouverte +
                "\n, path=" + path +
                "\n, go=" + go +
                "\n, pos=" + Arrays.toString(tilemap) +
                "\n, debut=" + debut +
                "\n, fin=" + fin +
                '}';
    }

    public void dessine() {
        for (int[] po : tilemap) {
            for (int i : po) System.out.print(i + " ");
            System.out.println();
        }
    }

    public void dessineResult() {
        System.out.println("*****************");
        Iterator<Case> itFerme = path.iterator();
        Case fil = null;
        while (itFerme.hasNext()) {
            fil = itFerme.next();
            tilemap[fil.getX()][fil.getY()] = 8;
        }
        System.out.println("*****************");
        for (int pl = 0; pl < tilemap.length; pl++) {
            for (int c = 0; c < tilemap[0].length; c++)
                System.out.print(tilemap[pl][c] + " ");
            System.out.println();
        }
    }


    public static void main(String[] args) {
        Case debut = new Case(1,1);
        Case fin = new Case(11, 19);
        int [][] tab = {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
                {1,2,1,1,1,1,1,2,1,1,2,1,1,2,1,1,1,1,1,2,1},
                {1,2,2,2,1,2,2,2,2,2,2,2,2,2,2,2,1,2,2,2,1},
                {1,1,1,2,1,2,1,2,1,1,0,1,1,2,1,2,1,2,1,1,1},
                {1,2,2,2,2,2,1,2,1,0,0,0,1,2,1,2,2,2,2,2,1},
                {1,2,1,1,1,1,1,2,1,1,1,1,1,2,1,1,1,1,1,2,1},
                {1,2,2,2,2,2,2,2,2,2,0,2,2,2,2,2,2,2,2,2,1},
                {1,2,1,1,1,1,2,1,1,2,1,2,1,1,2,1,1,1,1,2,1},
                {1,2,2,2,2,1,2,1,2,2,1,2,2,1,2,1,2,2,2,2,1},
                {1,2,1,1,2,1,2,1,2,1,1,1,2,1,2,1,2,1,1,2,1},
                {1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        };

        PathFinding recherche = new PathFinding(debut, fin, tab);
        recherche.dessine();
        recherche.dessineResult();
        // Case lastElement = recherche.getLastElement();
        //System.out.println("le putain de f " + debut.getF());
        System.out.println(recherche.path);
    }
}