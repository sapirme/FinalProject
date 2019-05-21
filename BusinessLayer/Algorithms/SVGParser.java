package Algorithms;

import Graph.*;
import Shapes.*;
import SystemObj.ViewPoint;

import java.util.Set;
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
                        int j ;
                        for(j=1; j < vals.length-4; j=j+3){
                            double xStart = Double.parseDouble(vals[j]);
                            double yStart = Double.parseDouble(vals[j+1]);
                            double xEnd = Double.parseDouble(vals[j+3]);
                            double yEnd = Double.parseDouble(vals[j+4]);
                            Line l = new Line(xStart, yStart, xEnd, yEnd);
                            System.out.println(l.toString());
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
        System.out.println("finish shapes");
        zero(shapes1);
        zero(shapes2);
        Pair<List<Shape>,List<Shape>> p = new Pair<>(shapes1,shapes2);
        return p;
    }

    public static Enums.checkingAns decide(String svgStr, ViewPoint v1, ViewPoint v2) {
        Pair<List<Shape>,List<Shape>> shapes = fileToShapes(svgStr);
        System.out.println("check size");
        if (shapes.getFirst().size()>40 || shapes.getSecond().size()>40){
            System.out.println("to many");
            return Enums.checkingAns.TO_MANY_SHAPS;
        }
        //v1.setSapes(shapes.getFirst());
        //v2.setSapes(shapes.getSecond());

        Graph g1 = CheckingAlgorithm.createGraph(shapes.getFirst());
        Graph g2 = CheckingAlgorithm.createGraph(shapes.getSecond());

        //v1.setGraph(g1);
        //v2.setGraph(g2);
        if (v1!=null){
            v1.setSapes(shapes.getFirst());
            v1.setGraph(new Graph(g1));
            //System.out.println("update g1");
        }
        if (v2!=null){
            v2.setSapes(shapes.getSecond());
            v2.setGraph(new Graph(g2));
            //System.out.println("update g2");
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

    /*
    public static void main(String[] args) throws FileNotFoundException
    {
        //String path1_No = "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n" +
        //        "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" version=\"1.1\" width=\"828px\" height=\"437px\" viewBox=\"-0.5 -0.5 828 437\" content=\"&lt;mxfile modified=&quot;2019-01-08T15:41:01.964Z&quot; host=&quot;www.draw.io&quot; agent=&quot;Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36&quot; etag=&quot;OJhjLbhanzPTBIF17VGJ&quot; version=&quot;10.0.22&quot;&gt;&lt;diagram id=&quot;mZNr6_R3YRMUqTDTcL6z&quot; name=&quot;Page-1&quot;&gt;1ZZdT8IwFIZ/zS5NtnXTcSkIGpVI5MJ42axnW2O3Ll1hw19vZ7uPihi4MIQr2ren5/Q8bws4aJY39wKX2ZITYI7vksZBd47ve0GI1Eer7LQSIVcLqaDEBA3Cmn6CEbuwDSVQWYGScyZpaYsxLwqIpaVhIXhthyWc2VVLnMKesI4x21ffKJGZ6cK/GfQHoGnWVfauJ3olx12w6aTKMOH1SEJzB80E51KP8mYGrIXXcdH7FgdW+4MJKORRGxbvL8vnbbJOyer1UTZJ+DS/inSWLWYb07A5rNx1BKAgty1INSt4ocRpJnOmZp4aVlLwj56M6mmq9wPZwzqc0+u7V9cGeA5S7FRIPfANDbNshLbTBDAs6dZOj43NaZ+ur7DiVBX23cbaYK4jmrh2gopvRAxmz5jmjzSRH/6dSGKRgtxLpAajpgfp26wTjPP8I5xjTD2T1rE6oxLWJY7blVq9VNtFXJX67SS0AVV1mlDGZpxxMZhuyoGQ0JzqbMfMtZD5gZmPjI9+MT5yD3tsYT2ZIbp4ht7k3AyDi2cYnP0ehhfPMAzC/2KopsOPpP76HP5qoPkX&lt;/diagram&gt;&lt;/mxfile&gt;\" style=\"background-color: rgb(255, 255, 255);\"><defs/><path d=\"M 1 200 L 850 200\" fill=\"none\" stroke=\"#000000\" stroke-width=\"2\" stroke-miterlimit=\"10\" pointer-events=\"none\"/><ellipse cx=\"120\" cy=\"90\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/><ellipse cx=\"120\" cy=\"40\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/><ellipse cx=\"120\" cy=\"290\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/><ellipse cx=\"120\" cy=\"395\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/></svg>";
        //String path1_No = "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n" +
        //        "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" version=\"1.1\" width=\"828px\" height=\"512px\" viewBox=\"-0.5 -0.5 828 512\" content=\"&lt;mxfile modified=&quot;2019-01-08T15:43:53.650Z&quot; host=&quot;www.draw.io&quot; agent=&quot;Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36&quot; etag=&quot;zyJc15WEMefk8OVY6fhO&quot; version=&quot;10.0.22&quot;&gt;&lt;diagram id=&quot;mZNr6_R3YRMUqTDTcL6z&quot; name=&quot;Page-1&quot;&gt;1ZZNb6MwEIZ/DcdKGENDjpts2mq3VavmsNqjhQewajAyTiD99TVr8+GkWTWHKuIU+7VnxvO8mODhddHeS1LlT4IC9wKfth7+6QUBCiOsfzrlYJRFGBshk4zaTaOwZe9gRd+qO0ahdjYqIbhilSsmoiwhUY5GpBSNuy0V3K1akQxOhG1C+Kn6h1GVGzUOFqP+ACzL+8rodmlWCtJvtp3UOaGimUh44+G1FEKZUdGugXfwei4m7u7M6nAwCaX6UsDd3+enx326zejL6y/VptHvzY01Y0/4zjZsD6sOPQEo6Y8OpJ6VotTiKlcF1zOkh7WS4m0go3tamXigJ1jHc6Khe/3YgChAyYPe0ox8I8ssn6DtNQmcKLZ30xNrczakGyq8CKYLB37rBNjHES99N0EtdjIBGzOleZQmDqL/J1JEZqBOEunBpOlR+mfWBcah4AvOca6vSedYkzMF24ok3Uqjb6rrIqkrc3dS1oKuukoZ52vBhRxNt+VAKmgvdbZn5jvIgtDOJ8bHnxgf++c9drBezBDPjyHCLkS0vDbEcP4Qw6tDjOYH8eg2R4trM7ydH0N0BBFd/ZW4mD/EEH0bRD0dP9jMX/n42Ys3Hw==&lt;/diagram&gt;&lt;/mxfile&gt;\" style=\"background-color: rgb(255, 255, 255);\"><defs/><path d=\"M 1 250 L 850 250\" fill=\"none\" stroke=\"#000000\" stroke-width=\"2\" stroke-miterlimit=\"10\" pointer-events=\"none\"/><ellipse cx=\"120\" cy=\"140\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/><ellipse cx=\"170\" cy=\"90\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/><ellipse cx=\"170\" cy=\"390\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/><ellipse cx=\"120\" cy=\"470\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/><ellipse cx=\"220\" cy=\"40\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/><ellipse cx=\"220\" cy=\"310\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/></svg>";
        //String path1_No = "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n" +
        //        "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" version=\"1.1\" width=\"828px\" height=\"562px\" viewBox=\"-0.5 -0.5 828 562\" content=\"&lt;mxfile modified=&quot;2019-01-08T14:41:29.012Z&quot; host=&quot;www.draw.io&quot; agent=&quot;Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36&quot; etag=&quot;7npSR9FBMKwpCaOUsg1O&quot; version=&quot;10.0.22&quot;&gt;&lt;diagram id=&quot;mZNr6_R3YRMUqTDTcL6z&quot; name=&quot;Page-1&quot;&gt;5ZfbbqMwEIafhstKNYeEXG7TtFUP2qq5WO2lhQewajAyTiD79DVrczAk2WzVqop6FftnPIO/3x4Rx1tm9a3ARfrECTDHvSS14107rov8wFM/jbLTytwPtZAISkxQL6zpHzDipVE3lEBpBUrOmaSFLUY8zyGSloaF4JUdFnNmVy1wAhNhHWE2VX9RIlOthu681++AJmlbGc0W+kmG22CzkzLFhFcDyVs53lJwLvUoq5fAGngtF73u5sDT7sUE5PKkBTe/fz49buN1Qp5f7mUdBw+rC19n2WK2MRs2Lyt3LQGVRcFWk6sqpRLWBY6aJ5XyW2mpzJiaITXEZaEdiGkNquhVTBlbcsaF0nKeNylMORAS6oP7QB0ddayAZyDFToWYBaHhaQ6Ub6ZV7w6aGS0dOtOK2JyIpMvcQ1MDw+0/GAbnxxC5NsTukA4ohnsghp/FcH4Cw5z8aC50D2LArZSCv3Y3FHWUgEyu9z8ZDRgEexi0mgCGJd3a6feBMRWeOVWFOwt82wHPHaEt+UZEYBYNr/Uoz/g+TBJJLBKQk0R/bep2/X7nwg91zj0D55DNe/FO40I3OJ7ok41bnF/bGrf+r+/97WfKOVEcN38//OLmj9B37/7BR3X/SaIDTUSxxLtBWNEElEd63mx/nd52nfHEDqWm/YevDu//PnirNw==&lt;/diagram&gt;&lt;/mxfile&gt;\" style=\"background-color: rgb(255, 255, 255);\"><defs/><ellipse cx=\"160\" cy=\"80\" rx=\"80\" ry=\"80\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/><ellipse cx=\"160\" cy=\"220\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/><path d=\"M 40 280 L 280 280\" fill=\"none\" stroke=\"#000000\" stroke-miterlimit=\"10\" pointer-events=\"none\"/><path d=\"M 1 350 L 850 350\" fill=\"none\" stroke=\"#000000\" stroke-width=\"2\" stroke-miterlimit=\"10\" pointer-events=\"none\"/><ellipse cx=\"160\" cy=\"480\" rx=\"80\" ry=\"80\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/><ellipse cx=\"160\" cy=\"480\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/><path d=\"M 40 480 L 150 480 Q 160 480 170 480 L 280 480\" fill=\"none\" stroke=\"#000000\" stroke-miterlimit=\"10\" pointer-events=\"none\"/></svg>";
        String path1_No = "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n" +
                "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" version=\"1.1\" width=\"847px\" height=\"402px\" viewBox=\"-0.5 -0.5 847 402\" content=\"&lt;mxfile modified=&quot;2019-01-09T15:27:46.293Z&quot; host=&quot;www.draw.io&quot; agent=&quot;Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36&quot; etag=&quot;zNG7xrZlOEGVWBvbdGKo&quot; version=&quot;10.0.26&quot;&gt;&lt;diagram id=&quot;3nfdeHwg7NlBeZPwvNkS&quot; name=&quot;Page-1&quot;&gt;5Vddj5swEPw1PJ4EBpLcY49eW1WqVDWt7tnCG7BqMDLOQfrra+o1n4numru0avMSmfF61js7G4QXJkX7XtEq/yQZCI/4rPXCtx4hmzUxvx1wsEB0u7FApjizUDAAW/4DEPQR3XMG9SRQSyk0r6ZgKssSUj3BqFKymYbtpJhmrWgGC2CbUrFEHzjTOZZF1gP+AXiWu8zB6tbuFNQFYyV1TplsRlB474WJklLbVdEmIDrtnC723LsTu/3FFJT6OQc+ltVXkvjlA82+rL8zsfvm39wgyyMVeywYL6sPTgHDYsQ2D3dNzjVsK5p2O41pt8FyXQjzFJglrSvbgR1vwSS923EhEimkMlgpy44C04HS0J6sI+jVMa4CWYBWBxOCByIfBUVHBU7gZtQfhPJRaxxG0RFZzzyIZhao229oSJ6hYcnedGYchBjpZqOBLYz4pCKjiuMjFTtMgaCaP07pj8mAGT5LbhL3AoermXK13KsUMGrsOHfQDX8UTzq1INJUZaAXRL+60Jd5fmPC/7UxqG+4mU1CdGaj4vlIzYlONMpIRw+jsKoLqE9fOIqO5xn6bhlf1QXRtblg/kd3tgvmRJdyweYPuCD+91900d9+0a2ubJL6Brx0khZEF5okl+eik7S+MhfE5JVcsCC6kAti8iIXmMfho8SGD1924f1P&lt;/diagram&gt;&lt;/mxfile&gt;\" style=\"background-color: rgb(255, 255, 255);\"><defs/><ellipse cx=\"440\" cy=\"40\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/><path d=\"M 0 240 L 845 240\" fill=\"none\" stroke=\"#000000\" stroke-miterlimit=\"10\" pointer-events=\"none\"/><path d=\"M 380 20 L 430 20 Q 440 20 450 20 L 500 20\" fill=\"none\" stroke=\"#000000\" stroke-miterlimit=\"10\" pointer-events=\"none\"/><path d=\"M 380 60 L 430 60 Q 440 60 450 60 L 500 60\" fill=\"none\" stroke=\"#000000\" stroke-miterlimit=\"10\" pointer-events=\"none\"/><ellipse cx=\"440\" cy=\"340\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/><path d=\"M 380 280 L 430 280 Q 440 280 450 280 L 500 280\" fill=\"none\" stroke=\"#000000\" stroke-miterlimit=\"10\" pointer-events=\"none\"/><path d=\"M 380 400 L 430 400 Q 440 400 450 400 L 500 400\" fill=\"none\" stroke=\"#000000\" stroke-miterlimit=\"10\" pointer-events=\"none\"/></svg>";

        if(decide(path1_No))
            System.out.println("yes!!!!");
        else
            System.out.println("no");
    }*/
}