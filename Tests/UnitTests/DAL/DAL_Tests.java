package DAL;

import Graph.*;
import Graph.Pair;
import Shapes.SVGParser;
import Shapes.Shape;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
//import com.gson;

import static org.junit.Assert.*;

public class DAL_Tests {
    DAL_Interface dal = DAL_InterfaceImpl.getInstance();
    String SVG1_Yes = "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" version=\"1.1\" width=\"828px\" height=\"437px\" viewBox=\"-0.5 -0.5 828 437\" content=\"&lt;mxfile modified=&quot;2019-01-08T15:41:01.964Z&quot; host=&quot;www.draw.io&quot; agent=&quot;Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36&quot; etag=&quot;OJhjLbhanzPTBIF17VGJ&quot; version=&quot;10.0.22&quot;&gt;&lt;diagram id=&quot;mZNr6_R3YRMUqTDTcL6z&quot; name=&quot;Page-1&quot;&gt;1ZZdT8IwFIZ/zS5NtnXTcSkIGpVI5MJ42axnW2O3Ll1hw19vZ7uPihi4MIQr2ren5/Q8bws4aJY39wKX2ZITYI7vksZBd47ve0GI1Eer7LQSIVcLqaDEBA3Cmn6CEbuwDSVQWYGScyZpaYsxLwqIpaVhIXhthyWc2VVLnMKesI4x21ffKJGZ6cK/GfQHoGnWVfauJ3olx12w6aTKMOH1SEJzB80E51KP8mYGrIXXcdH7FgdW+4MJKORRGxbvL8vnbbJOyer1UTZJ+DS/inSWLWYb07A5rNx1BKAgty1INSt4ocRpJnOmZp4aVlLwj56M6mmq9wPZwzqc0+u7V9cGeA5S7FRIPfANDbNshLbTBDAs6dZOj43NaZ+ur7DiVBX23cbaYK4jmrh2gopvRAxmz5jmjzSRH/6dSGKRgtxLpAajpgfp26wTjPP8I5xjTD2T1rE6oxLWJY7blVq9VNtFXJX67SS0AVV1mlDGZpxxMZhuyoGQ0JzqbMfMtZD5gZmPjI9+MT5yD3tsYT2ZIbp4ht7k3AyDi2cYnP0ehhfPMAzC/2KopsOPpP76HP5qoPkX&lt;/diagram&gt;&lt;/mxfile&gt;\" style=\"background-color: rgb(255, 255, 255);\"><defs/><path d=\"M 1 200 L 850 200\" fill=\"none\" stroke=\"#000000\" stroke-width=\"2\" stroke-miterlimit=\"10\" pointer-events=\"none\"/><ellipse cx=\"120\" cy=\"90\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/><ellipse cx=\"120\" cy=\"40\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/><ellipse cx=\"120\" cy=\"290\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/><ellipse cx=\"120\" cy=\"395\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/></svg>";

    @Before
    public void SetUp(){
        dal.Connect();
    }

    @Test
    public void InsertTest() {
        Pair<List<Shape>,List<Shape>> shapes = SVGParser.fileToShapes(SVG1_Yes);
        List<Shape> s1 = shapes.getFirst();
        List<Shape> s2 = shapes.getSecond();
        Graph g1 = CheckingAlgorithm.createGraph(s1);
        Graph g2 = CheckingAlgorithm.createGraph(s2);
        Pair<Set<List<Edge>>,Set<List<Edge>>> p = CheckingAlgorithm.checkAlgorithem(g1, g2);
        Set<List<Edge>> m1 = p.getFirst();
        Set<List<Edge>> m2 = p.getSecond();
        String D3 = "blablabla";
        String email = "adarne@post.bgu.ac.il";


        dal.InsertViewPoints(D3, SVG1_Yes, s1, s2, g1, g2, m1, m2, email);

    }

}