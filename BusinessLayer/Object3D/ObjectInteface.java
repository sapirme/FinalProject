package Object3D;

import javafx.geometry.Point3D;

import java.util.LinkedList;

public interface ObjectInteface {

    public String getText();
    public void setText(String text);
    public String listToText (LinkedList<Point3D> list, double minZ, double Height);
}
