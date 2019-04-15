package Object3D;

import javafx.geometry.Point3D;

import java.util.LinkedList;

public class Obj3DFile implements ObjectInteface {
    private String text;
    public Obj3DFile (){
        text = "";
    }

    public String getText() {return text;}
    public void setText(String text){
        this.text = text;
    }

    public String listToText (LinkedList<Point3D> list, double minZ, double Height){
        String ans = "";
        if (list.size() <= 1){
            return ans;
        }
        Point3D p1, p2, p3, N;
        Point3D d1, d2, d3, d4, d5, d6, d7, d8;
        p1 = list.get(0);
        p2 = list.get(1);
        p3 = new Point3D(p1.getX(), p1.getY(), (minZ - Height));
        N = getNormal(p1,p2,p3);
        double t = Math.sqrt((d*d)/(N.getX()*N.getX()+N.getY()*N.getY()+N.getZ()*N.getZ()));
        Point3D d1Next = new Point3D(p1.getX()+ N.getX()*t,p1.getY() + N.getY()*t, p1.getZ() + N.getZ()*t); //
        Point3D d2Next  = new Point3D(p1.getX()- N.getX()*t,p1.getY() - N.getY()*t, p1.getZ() - N.getZ()*t);
        for(int i=0; i < list.size() - 1; i++){
            p1 = list.get(i);
            p2 = list.get(i+1);
            p3 = new Point3D(p1.getX(), p1.getY(), (minZ - Height));
            N = getNormal(p1,p2,p3);
            t = Math.sqrt((d*d)/(N.getX()*N.getX()+N.getY()*N.getY()+N.getZ()*N.getZ()));
            d1 = d1Next; //
            d2 = d2Next; //
            d3 = new Point3D(p2.getX()- N.getX()*t,p2.getY() - N.getY()*t, p2.getZ() - N.getZ()*t);
            d4 = new Point3D(p2.getX()+ N.getX()*t,p2.getY() + N.getY()*t, p2.getZ() + N.getZ()*t);
            d1Next = d4;
            d2Next = d3;
            d5 = new Point3D(d2.getX(),d2.getY(), (minZ - Height));
            d6 = new Point3D(d3.getX(),d3.getY(), (minZ - Height));
            d7 = new Point3D(d4.getX(),d4.getY(), (minZ - Height));
            d8 = new Point3D(d1.getX(),d1.getY(), (minZ - Height));

            ans = ans + addSurface( d1,  d2,  d3,  d4);
            ans = ans + addSurface( d2,  d3,  d6,  d5);
            ans = ans + addSurface( d1,  d4,  d7,  d8);
            ans = ans + addSurface( d1,  d2,  d5,  d8);
            ans = ans + addSurface( d4,  d3,  d6,  d7);
            ans = ans + addSurface( d5,  d6,  d7,  d8);
            /*p1 = list.get(i);
            p2 = list.get(i+1);
            ans = ans + "v " +  p1.getX()+ " " + p1.getY() + " " + p1.getZ() + "\n";
            ans = ans + "v " +  p2.getX()+ " " + p2.getY() + " " + p2.getZ() + "\n";

            ans = ans + "v " +  p2.getX()+ " " + p2.getY() + " " + (minZ - Height) + "\n";
            ans = ans + "v " +  p1.getX()+ " " + p1.getY() + " " + (minZ - Height) + "\n";

            ans = ans + "f -1 -2 -3 -4 \n";*/

        }
        return ans;
    }

    public String addSurface(Point3D d1, Point3D d2, Point3D d3, Point3D d4){
        String ans = "";
        ans = ans + "v " +  d1.getX()+ " " + d1.getY() + " " + d1.getZ() + "\n";
        ans = ans + "v " +  d2.getX()+ " " + d2.getY() + " " + d2.getZ() + "\n";
        ans = ans + "v " +  d3.getX()+ " " + d3.getY() + " " + d3.getZ() + "\n";
        ans = ans + "v " +  d4.getX()+ " " + d4.getY() + " " + d4.getZ() + "\n";
        ans = ans + "f -1 -2 -3 -4 \n";
        return ans;
    }

    public Point3D getNormal(Point3D p0, Point3D p1, Point3D p2){
        Point3D u = new Point3D(p1.getX() - p0.getX(), p1.getY() - p0.getY(), p1.getZ() - p0.getZ());
        Point3D v = new Point3D(p2.getX() - p0.getX(), p2.getY() - p0.getY(), p2.getZ() - p0.getZ());

        double Nx = u.getY()*v.getZ() - u.getZ()*v.getY();
        double Ny = u.getX()*v.getZ() - u.getZ()*v.getX();
        double Nz = u.getX()*v.getY() - u.getY()*v.getX();

        return new Point3D(Nx,-Ny,Nz);
    }
}
