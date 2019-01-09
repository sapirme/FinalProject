package Graph;

public class Vertex {
    private double x;
    private double y;

    public Vertex(double x,double y){
        this.x = x;
        this.y = y;
    }

    public void setX(int x){
        this.x = x;
    }

    public double getX(){
        return this.x;
    }

    public void setY(int y){
        this.y = y;
    }

    public double getY(){
        return this.y;
    }

    public boolean isBetween(Vertex v1,Vertex v2) {
        if (v1.getX() < x && x < v2.getX()) {
            if (Math.min(v1.getY(), v2.getY()) <= y && y <= Math.max(v1.getY(), v2.getY()))
                return true;

        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Vertex)) {
            return false;
        }
        Vertex other = (Vertex) o;
        if(this.x == other.getX() && this.y == other.getY()) return true;
        else return false;
    }

    @Override
    public String toString(){
        return "<"+this.x+","+this.y+">";
    }
}
