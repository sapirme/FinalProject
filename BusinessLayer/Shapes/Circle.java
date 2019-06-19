package Shapes;

import Graph.*;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class Circle extends Shape{
    private double x;
    private double y;
    private double r;

    public Circle (double x,double y,double r) {
        this.x=x;
        this.y=y;
        this.r=r;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    @Override
    public List<Point2D.Double> getIntersections_accept(Shape s) {
        return s.getIntersections_visit(this);
    }

    @Override
    public List<Double> getIntersections_visit(Circle s) {
        List<Point2D.Double> intesection=new LinkedList<>();
        double x1,x2,y1,y2,r1,r2,R;
        x1=x;x2=s.getX();
        y1=y;y2=s.getY();
        r1=r;r2=s.getR();

        double d = Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1)); //distance between centers
        if (d > r1+r2 || d < Math.abs(r1-r2) || (d==0 && r1==r2)) return intesection;

        double a = (r1*r1-r2*r2+d*d)/(2*d);
        double doubleH = r1*r1-a*a;
        double h = Math.sqrt(doubleH);

        Point2D.Double temp=new Point2D.Double((x2-x1)*(a/d)+x1,(y2-y1)*(a/d)+y1);

        Point2D.Double p1 = new Point2D.Double(temp.x+h*(y2-y1)/d,temp.y-h*(x2-x1)/d);
        Point2D.Double p2 = new Point2D.Double(temp.x-h*(y2-y1)/d,temp.y+h*(x2-x1)/d);
        intesection.add(p1);
        if(!p1.equals(p2)) {
            intesection.add(p2);
        }

        return intesection;
    }

    @Override
    public double getYbyX(double x,double yFrom, double yTo) {
        if ((yTo > yFrom && y < yTo) || (yFrom > yTo && y < yFrom) )
            return getY() + Math.sqrt(getR()*getR() - (getX() - x)*(getX() - x)); //top
        else return getY() - Math.sqrt(getR()*getR() - (getX() - x)*(getX() - x));//down

    }

    @Override
    public void createShapeGraph(List<Shape> shapes,Graph g) {
        Set<Point2D.Double> points=new HashSet<Point2D.Double>();
        for (Shape s : shapes) {
            if (s!=this)
                points.addAll(this.getIntersections_accept(s));
        }

        Vertex right=new Vertex(r+x,y);
        Vertex left=new Vertex(x-r,y);
        Vertex up=new Vertex(x,y+r);
        Vertex down=new Vertex(x,y-r);

        Vertex v=g.isVertexExist(right);
        if (v==null) g.addVertex(right);
        else right=v;
        v=g.isVertexExist(left);
        if (v==null) g.addVertex(left);
        else left=v;
        v=g.isVertexExist(up);
        if (v==null) g.addVertex(up);
        else up=v;
        v=g.isVertexExist(down);
        if (v==null) g.addVertex(down);
        else down=v;


        g.addEdge(new Edge(this,left,up),false);
        g.addEdge(new Edge(this,left,down),false);
        g.addEdge(new Edge(this,up,right),false);
        g.addEdge(new Edge(this,down,right),false);
        this.splitEdges(g,points);
    }

    @Override
    public List<Point2D.Double> getIntersections_visit(Line s) {
        List<Point2D.Double> ans=new LinkedList<Point2D.Double>();
        double a = s.getM()*s.getM() + 1;
        double b = 2 * ((s.getB()-y)*s.getM()-x);
        double c = (s.getB()-y)*(s.getB()-y) + x*x - r*r;
        double[] intesectionX = QuadraticEquationSol(a,b,c);
        for (int i=0;i<intesectionX.length;i++) {
            if (s.getXStart()<=intesectionX[i] && intesectionX[i]<=s.getXEnd()) {
                ans.add(new Point2D.Double(intesectionX[i],s.getM()*intesectionX[i]+s.getB()));
            }
        }

        return ans;
    }

    private double[] QuadraticEquationSol(double a,double b, double c) {
        double result = b * b - 4.0 * a * c;
        double[] array;
        if (result > 0.0) {
            double r1 = (-b + Math.pow(result, 0.5)) / (2.0 * a);
            double r2 = (-b - Math.pow(result, 0.5)) / (2.0 * a);
            array=new double[2];
            array[0]=r1;
            array[1]=r2;
        }
        else if (result == 0.0) {
            double r1 = -b / (2.0 * a);
            array=new double[1];
            array[0]=r1;
        }
        else {
            array=new double[0];
        }
        return array;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Circle)) {
            return false;
        }
        Circle other = (Circle) o;
        if(this.x==other.getX() && this.y==other.getY() && this.r==other.getR()) return true;
        else return false;
    }

    @Override
    public String toString(){
        return "center: ("+x+","+y+") radius: "+r;
    }

    public double getMinY(){
        return this.y - this.r;
    }

    public double getMinX(){
        return this.x - this.r;
    }

    public void moveYbyH(double h){
        this.y = this.y - h;
    }

    public void moveXbyH(double h){
        this.x = this.x - h;
    }

}
