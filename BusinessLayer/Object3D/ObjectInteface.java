package Object3D;

import javafx.geometry.Point3D;

import java.util.LinkedList;

public interface ObjectInteface {

    public double d=1;
    public String getText();
    public void setText(String text);
    public String listToText (LinkedList<Point3D> list, double minZ, double Height);
    public String addButtomCube(double minx,double maxx,double miny,double maxy,double z);

    public static Point3D getNormal(Point3D p0, Point3D p1, Point3D p2){
        Point3D u = new Point3D(p1.getX() - p0.getX(), p1.getY() - p0.getY(), p1.getZ() - p0.getZ());
        Point3D v = new Point3D(p2.getX() - p0.getX(), p2.getY() - p0.getY(), p2.getZ() - p0.getZ());

        double Nx = u.getY()*v.getZ() - u.getZ()*v.getY();
        double Ny = u.getX()*v.getZ() - u.getZ()*v.getX();
        double Nz = u.getX()*v.getY() - u.getY()*v.getX();

        return new Point3D(Nx,-Ny,Nz);
    }
}
