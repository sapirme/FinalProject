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

        boolean ans = true;

        if (this.from!=null && other.getFrom()!=null){
            ans = this.from.equals(other.getFrom());
        }
        else if (this.from==null && other.getFrom()==null) {
            ans = true;
        }
        else ans = false;
        ////////////////////////////////////////////////
        if (this.to!=null && other.getTo()!=null){
            ans = ans && this.to.equals(other.getTo());
        }
        else if (this.to==null && other.getTo()==null){
            ans = ans && true;
        }
        else ans = ans && false;
        /////////////////////////////////////////////////
        if (this.f!=null && other.getF()!=null){
            ans = ans && this.f.equals(other.getF());
        }
        else if (this.f==null && other.getF()==null){
            ans = ans && true;
        }
        else ans = ans && false;
        /////////////////////////////////////////////////

        return ans;
    }

    public String toString(){
        return this.from+"--"+this.f+"->"+this.to;
    }
}
