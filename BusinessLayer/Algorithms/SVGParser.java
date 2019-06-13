package Algorithms;

import Graph.Edge;
import Graph.Graph;
import Graph.Pair;
import Shapes.Circle;
import Shapes.Line;
import Shapes.Shape;
import SystemObj.ViewPoint;

import java.util.LinkedList;
import java.util.List;



public class SVGParser {
    public static void zero(List<Shape> shapes){
        //find min Y
        if (shapes.size()==0) return;
        double minY = shapes.get(0).getMinY();
        for(Shape s : shapes){
            if (s.getMinY() < minY){
                minY = s.getMinY();
            }
        }
        for(Shape s : shapes){
            s.moveYbyH(minY);
        }

        //find min X
        double minX = shapes.get(0).getMinX();
        for(Shape s : shapes){
            if (s.getMinX() < minX){
                minX = s.getMinX();
            }
        }
        for(Shape s : shapes){
            s.moveXbyH(minX);
        }
    }

    public static Pair<List<Shape>,List<Shape>> fileToShapes(String svg) {
        String[] svgArr = svg.split("<");
        List<Shape> shapes1 = new LinkedList<Shape>();
        List<Shape> shapes2 = new LinkedList<Shape>();

        //Find the line in the middle
        double middle = 0;
        for(int i=0;i<svgArr.length;i++) {
            String line = svgArr[i];
            if(line.length()>6){
                String shape_name = line.substring(0,line.indexOf(" "));
                if(shape_name.equals("path")) {
                    String d = line.substring(line.indexOf("d=\"")+3,line.indexOf("\"",line.indexOf("d=\"")+3));
                    String[] vals = d.split(" ");
                    double xStart = Double.parseDouble(vals[1]);
                    double yStart = Double.parseDouble(vals[2]);
                    double xEnd = Double.parseDouble(vals[4]);
                    if(xEnd - xStart > 815){
                        middle = yStart;
                    }
                }
            }
        }

        for(int i=0;i<svgArr.length;i++) {
            String line = svgArr[i];
            //System.out.println(line);
            if(line.length()>6){
                String shape_name = line.substring(0,line.indexOf(" "));
                if(shape_name.equals("ellipse")){
                    double x = Double.parseDouble(line.substring(line.indexOf("cx=\"")+4,line.indexOf("\"",line.indexOf("cx=\"")+4)));
                    double y = Double.parseDouble(line.substring(line.indexOf("cy=\"")+4,line.indexOf("\"",line.indexOf("cy=\"")+4)));
                    double r = Double.parseDouble(line.substring(line.indexOf("rx=\"")+4,line.indexOf("\"",line.indexOf("rx=\"")+4)));
                    Shape ellipse = new Circle(x,y,r);
                    if(y<middle)
                        shapes1.add(ellipse);
                    else
                        shapes2.add(ellipse);
                }
                else{
                    if(shape_name.equals("path")) {
                        String d = line.substring(line.indexOf("d=\"")+3,line.indexOf("\"",line.indexOf("d=\"")+3));
                        String[] vals = d.split(" ");
                        int j ;
                        for(j=1; j < vals.length-4; j=j+3){
                            double xStart = Double.parseDouble(vals[j]);
                            double yStart = Double.parseDouble(vals[j+1]);
                            double xEnd = Double.parseDouble(vals[j+3]);
                            double yEnd = Double.parseDouble(vals[j+4]);
                            Line l = new Line(xStart, yStart, xEnd, yEnd);
                            //System.out.println(l.toString());
                            if(yStart<middle)
                                shapes1.add(l);
                            else {
                                if(yStart!=middle)
                                    shapes2.add(l);
                            }
                        }
                        if (vals[vals.length-1].equals("Z")){
                            double xStart  = Double.parseDouble(vals[vals.length-3]);
                            double yStart  = Double.parseDouble(vals[vals.length-2]);
                            double xEnd  = Double.parseDouble(vals[1]);
                            double yEnd  = Double.parseDouble(vals[2]);
                            Line l = new Line(xStart, yStart, xEnd, yEnd);
                            System.out.println(l.toString());
                            if(yStart<middle)
                                shapes1.add(l);
                            else {
                                if(yStart!=middle)
                                    shapes2.add(l);
                            }
                        }


                    }
                }
            }
        }
        zero(shapes1);
        zero(shapes2);
        Pair<List<Shape>,List<Shape>> p = new Pair<>(shapes1,shapes2);
        return p;
    }

    public static int sumNumOfCircles(List<Shape> shapes){
        int c=0;
        for (Shape s: shapes) {
            if (s instanceof Circle)
                c++;
        }
        return c;
    }

    public static int sumNumOfLines(List<Shape> shapes){
        int c=0;
        for (Shape s: shapes) {
            if (s instanceof Line)
                c++;
        }
        return c;
    }

    public static Enums.checkingAns decide(String svgStr, ViewPoint v1, ViewPoint v2) {
        Pair<List<Shape>,List<Shape>> shapes = fileToShapes(svgStr);
        if (shapes.getFirst().size()>40 || shapes.getSecond().size()>40){
            return Enums.checkingAns.TO_MANY_SHAPS;
        }

        Graph g1 = CheckingAlgorithm.createGraph(shapes.getFirst());
        Graph g2 = CheckingAlgorithm.createGraph(shapes.getSecond());

        if (v1!=null){
            v1.setCircleNum(sumNumOfCircles(shapes.getFirst()));
            v1.setLineNum(sumNumOfLines(shapes.getFirst()));
            v1.setGraph(new Graph(g1));
        }
        if (v2!=null){
            v2.setCircleNum(sumNumOfCircles(shapes.getSecond()));
            v2.setLineNum(sumNumOfLines(shapes.getSecond()));
            v2.setGraph(new Graph(g2));
        }

        System.out.println("Graph 1:");
        System.out.println(g1);
        System.out.println();
        System.out.println("Graph 2:");
        System.out.println(g2);
        System.out.println();

        Pair<LinkedList<List<Edge>>,LinkedList<List<Edge>>> p =CheckingAlgorithm.checkAlgorithem(g1, g2);

        System.out.println("ans :");
        System.out.println(p);
        if(p!=null){
            if (v1!=null){
                v1.setPaths(p.getFirst());
            }
            if (v2!=null){
                v2.setPaths(p.getSecond());
            }
            return Enums.checkingAns.CAN;
        }
        else {
            if (v1!=null) {
                v1.setPaths(null);
            }
            if (v2!=null) {
                v2.setPaths(null);
            }
            return Enums.checkingAns.CANT;
        }
    }

}