public class Case {
    private int x;
    private int y;

    private int f=0;
    private int g;
    private int h;

    private Case parent;

    public Case(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    public int getF() {
        return g+h;
    }

    public void setF(int f) {
        this.f = f;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public Case getParent() {
        return parent;
    }

    public void setParent(Case parent) {
        this.parent = parent;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Case{" +
                "x=" + x +
                ", y=" + y +
                ", f=" + f +
                ", g=" + g +
                ", h=" + h +
                ", parent=" + parent +
                '}';
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + x;
        result = PRIME * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Case other = (Case) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }
}