package Object3D;

import javafx.geometry.Point3D;

import java.util.LinkedList;

public interface ObjectInteface {

    public double d=1;
    public String getText();
    public void setText(String text);
    public String listToText (LinkedList<Point3D> list, double minZ, double Height);
    public String addButtomCube(double minx,double maxx,double miny,double maxy,double z);
}
