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
        Point3D p1, p2;
        for(int i=0; i < list.size() - 1; i++){
            p1 = list.get(i);
            p2 = list.get(i+1);
            ans = ans + "v " +  p1.getX()+ " " + p1.getY() + " " + p1.getZ() + "\n";
            ans = ans + "v " +  p2.getX()+ " " + p2.getY() + " " + p2.getZ() + "\n";

            ans = ans + "v " +  p2.getX()+ " " + p2.getY() + " " + (minZ - Height) + "\n";
            ans = ans + "v " +  p1.getX()+ " " + p1.getY() + " " + (minZ - Height) + "\n";

            ans = ans + "f -1 -2 -3 -4 \n";

        }
        return ans;
    }
}
