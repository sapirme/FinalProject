package Graph;

public class Vertex implements Comparable<Vertex>{
    private double x;
    private double y;

    public Vertex(double x,double y){
        this.x = x;
        this.y = y;
    }

    public Vertex (Vertex v){
        this.x = v.getX();
        this.y = v.getY();
    }

    @Override
    public int compareTo(Vertex v) {
        if (Math.abs(v.getX()-this.x)<1 && Math.abs(v.getY()-this.y)<1)
            return 0;
        if (Math.abs(v.getX()-this.x)<1 && v.getY()<this.y)
            return 1;
        if (Math.abs(v.getX()-this.x)<1 && v.getY()>this.y)
            return -1;
        if (v.getX()<this.x)
            return 1;
        else
            return -1;
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

        if(Math.abs(this.x - other.getX())<1 && Math.abs(this.y - other.getY())<1) return true;
        else return false;
    }

    @Override
    public String toString(){
        return "<"+this.x+","+this.y+">";
    }
}
