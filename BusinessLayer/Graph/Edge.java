package Graph;
import Shapes.*;

public class Edge implements Comparable<Edge>{
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

    @Override
    public int compareTo(Edge e) {
        if (f!=null && e.getF()!=null){
            if (e.getFrom().compareTo(this.from) == 0 && e.getTo().compareTo(this.to) == 0)
                return 0;
            if (e.getFrom().compareTo(this.from) == 0 && this.to.compareTo(e.getTo()) > 0)
                return 1;
            if (e.getFrom().compareTo(this.from) == 0 && this.to.compareTo(e.getTo()) < 0)
                return -1;
            if (this.from.compareTo(e.getFrom()) > 0)
                return 1;
            else
                return -1;
        }
        else {
            if (e.getFrom().compareTo(this.from) == 0 && e.getTo().compareTo(this.to) == 0)
                return 0;
            if (e.getFrom().compareTo(this.from) == 0 && this.to.compareTo(e.getTo()) > 0)
                return 1;
            if (e.getFrom().compareTo(this.from) == 0 && this.to.compareTo(e.getTo()) < 0)
                return -1;
            if (this.from.compareTo(e.getFrom()) > 0)
                return 1;
            else
                return -1;
        }

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
        /*if (this.f == null || other.getF()==null){
            return this.to.equals(other.getTo()) && this.from.equals(other.getFrom());
        }
        else{
            return this.to.equals(other.getTo()) && this.from.equals(other.getFrom()) && this.f.equals(other.getF());
        }*/

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
