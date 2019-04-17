package Object3D;

import javafx.geometry.Point3D;

import java.util.LinkedList;

public class Stl3DFile implements ObjectInteface {
    private String text;


    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String listToText(LinkedList<Point3D> list, double minZ, double Height) {
        String ans = "solid OpenSCAD_Model\n";
        if (list.size() <= 1) return "";

        Point3D p0, p1, p2, p3;
        Point3D u, v;
        double Nx, Ny, Nz;

        for(int i=0; i < list.size() - 1; i++){
            p0 = list.get(i);
            p1 = list.get(i+1);
            p2  = new Point3D(p1.getX(), p1.getY(), (minZ - Height));
            p3  = new Point3D(p0.getX(), p0.getY(), (minZ - Height));

            u = new Point3D(p1.getX() - p0.getX(), p1.getY() - p0.getY(), p1.getZ() - p0.getZ());
            v = new Point3D(p2.getX() - p0.getX(), p2.getY() - p0.getY(), p2.getZ() - p0.getZ());

            Nx = u.getY()*v.getZ() - u.getZ()*v.getY();
            Ny = u.getX()*v.getZ() - u.getZ()*v.getX();
            Nz = u.getX()*v.getY() - u.getY()*v.getX();

            ans = ans + "facet normal " + Nx + " " + -Ny + " " + Nz + "\n";
            ans = ans + "\touter loop\n";
            ans = ans + "\t\tvertex " +  p0.getX()+ " " + p0.getY() + " " + p0.getZ() + "\n";
            ans = ans + "\t\tvertex " +  p1.getX()+ " " + p1.getY() + " " + p1.getZ() + "\n";
            ans = ans + "\t\tvertex " +  p2.getX()+ " " + p2.getY() + " " + p2.getZ() + "\n";
            ans = ans + "\tendloop\n";
            ans = ans + "endfacet\n";

            ans = ans + "facet normal " + Nx + " " + -Ny + " " + Nz + "\n";
            ans = ans + "\touter loop\n";
            ans = ans + "\t\tvertex " +  p0.getX()+ " " + p0.getY() + " " + p0.getZ() + "\n";
            ans = ans + "\t\tvertex " +  p2.getX()+ " " + p2.getY() + " " + p2.getZ() + "\n";
            ans = ans + "\t\tvertex " +  p3.getX()+ " " + p3.getY() + " " + p3.getZ() + "\n";
            ans = ans + "\tendloop\n";
            ans = ans + "endfacet\n";
        }
        ans = ans + "endsolid OpenSCAD_Model";
        return ans;
    }
}
