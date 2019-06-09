package Graph;
import Shapes.*;

public class Edge {
    private Shape f;
    private Vertex from;
    private Vertex to;

    public Edge(Shape f, Vertex from, Vertex to){
        this.f = f;
        this.from = from;
        this.to = to;
    }

    public Edge(Edge other){
        this.f = other.getF();
        this.from = other.getFrom();
        this.to = other.getTo();
    }

    public void setF(Shape f){
        this.f = f;
    }

    public Shape getF(){
        return f;
    }

    public void setFrom(Vertex from){
        this.from = from;
    }

    public Vertex getFrom(){
        return from;
    }

    public void setTo(Vertex to){
        this.to = to;
    }

    public Vertex getTo(){
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Edge)) {
            return false;
        }
        Edge other = (Edge) o;
        if(this.from.equals(other.getFrom()) && this.to.equals(other.getTo()) && this.f.equals(other.getF())) return true;
        else return false;
    }

    public String toString(){
        return this.from+"--"+this.f+"->"+this.to;
    }
}
