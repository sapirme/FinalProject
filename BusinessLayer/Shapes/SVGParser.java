package Shapes;

import Graph.*;
import java.io.File;
import java.util.Set;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


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
                    if(xEnd - xStart > 845){
                        middle = yStart;
                    }
                }
            }
        }

        for(int i=0;i<svgArr.length;i++) {
            String line = svgArr[i];
            System.out.println(line);
            if(line.length()>6){
                String shape_name = line.substring(0,line.indexOf(" "));
                if(shape_name.equals("ellipse")){
                    //System.out.println(line.substring(8,40));
                    double x = Double.parseDouble(line.substring(line.indexOf("cx=\"")+4,line.indexOf("\"",line.indexOf("cx=\"")+4)));
                    double y = Double.parseDouble(line.substring(line.indexOf("cy=\"")+4,line.indexOf("\"",line.indexOf("cy=\"")+4)));
                    double r = Double.parseDouble(line.substring(line.indexOf("rx=\"")+4,line.indexOf("\"",line.indexOf("rx=\"")+4)));
                    Shape ellipse = new Circle(x,y,r);
                    System.out.println(ellipse.toString());
                    if(y<middle)
                        shapes1.add(ellipse);
                    else
                        shapes2.add(ellipse);
                }
                else{
                    if(shape_name.equals("path")) {
                        String d = line.substring(line.indexOf("d=\"")+3,line.indexOf("\"",line.indexOf("d=\"")+3));
                        String[] vals = d.split(" ");
                        double xStart = Double.parseDouble(vals[1]);
                        double yStart = Double.parseDouble(vals[2]);
                        double xEnd = Double.parseDouble(vals[4]);
                        double yEnd = Double.parseDouble(vals[5]);
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

        zero(shapes1);
        zero(shapes2);
        Pair<List<Shape>,List<Shape>> p = new Pair<>(shapes1,shapes2);
        return p;
    }

    public static Boolean decide(String path1) {
        Pair<List<Shape>,List<Shape>> shapes = fileToShapes(path1);
        Graph g1 = CheckingAlgorithm.createGraph(shapes.getFirst());
        Graph g2 = CheckingAlgorithm.createGraph(shapes.getSecond());
        System.out.println("Graph 1:");
        System.out.println(g1);
        System.out.println("Graph 2:");
        System.out.println(g2);
        Pair<Set<List<Edge>>,Set<List<Edge>>> p =CheckingAlgorithm.checkAlgorithem(g1, g2);
        System.out.println("ans :");
        System.out.println(p);
        if(p!=null){
            return true;
        }
        else
            return false;
    }
/*
    public static void main(String[] args) throws FileNotFoundException
    {
        String path1_No = "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" height=\"241\" version=\"1.1\" viewBox=\"0 0 849 241\" width=\"849\"><ellipse cx=\"200\" cy=\"40\" fill=\"#FFFFFF\" rx=\"40\" ry=\"40\" stroke=\"#000000\" stroke-width=\"1.0\"/><ellipse cx=\"200\" cy=\"200\" fill=\"#FFFFFF\" rx=\"40\" ry=\"40\" stroke=\"#000000\" stroke-width=\"1.0\"/><g><path d=\"M 0.5 120.0 L 847.75 120.0\" fill=\"none\" stroke=\"#000000\" stroke-width=\"1.0\"/></g></svg>";
        if(decide(path1_No))
            System.out.println("yes!!!!");
        else
            System.out.println("no");
    }*/
}