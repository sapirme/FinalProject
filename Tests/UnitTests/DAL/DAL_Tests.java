package DAL;

import Algorithms.CheckingAlgorithm;
import Graph.Edge;
import Graph.Graph;
import Graph.Pair;
import Shapes.SVGParser;
import Shapes.Shape;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
//import com.gson;

public class DAL_Tests {
    DAL_Interface dal = DAL_InterfaceImpl.getInstance();
    String SVG1_Yes = "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" version=\"1.1\" width=\"828px\" height=\"437px\" viewBox=\"-0.5 -0.5 828 437\" content=\"&lt;mxfile modified=&quot;2019-01-08T15:41:01.964Z&quot; host=&quot;www.draw.io&quot; agent=&quot;Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36&quot; etag=&quot;OJhjLbhanzPTBIF17VGJ&quot; version=&quot;10.0.22&quot;&gt;&lt;diagram id=&quot;mZNr6_R3YRMUqTDTcL6z&quot; name=&quot;Page-1&quot;&gt;1ZZdT8IwFIZ/zS5NtnXTcSkIGpVI5MJ42axnW2O3Ll1hw19vZ7uPihi4MIQr2ren5/Q8bws4aJY39wKX2ZITYI7vksZBd47ve0GI1Eer7LQSIVcLqaDEBA3Cmn6CEbuwDSVQWYGScyZpaYsxLwqIpaVhIXhthyWc2VVLnMKesI4x21ffKJGZ6cK/GfQHoGnWVfauJ3olx12w6aTKMOH1SEJzB80E51KP8mYGrIXXcdH7FgdW+4MJKORRGxbvL8vnbbJOyer1UTZJ+DS/inSWLWYb07A5rNx1BKAgty1INSt4ocRpJnOmZp4aVlLwj56M6mmq9wPZwzqc0+u7V9cGeA5S7FRIPfANDbNshLbTBDAs6dZOj43NaZ+ur7DiVBX23cbaYK4jmrh2gopvRAxmz5jmjzSRH/6dSGKRgtxLpAajpgfp26wTjPP8I5xjTD2T1rE6oxLWJY7blVq9VNtFXJX67SS0AVV1mlDGZpxxMZhuyoGQ0JzqbMfMtZD5gZmPjI9+MT5yD3tsYT2ZIbp4ht7k3AyDi2cYnP0ehhfPMAzC/2KopsOPpP76HP5qoPkX&lt;/diagram&gt;&lt;/mxfile&gt;\" style=\"background-color: rgb(255, 255, 255);\"><defs/><path d=\"M 1 200 L 850 200\" fill=\"none\" stroke=\"#000000\" stroke-width=\"2\" stroke-miterlimit=\"10\" pointer-events=\"none\"/><ellipse cx=\"120\" cy=\"90\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/><ellipse cx=\"120\" cy=\"40\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/><ellipse cx=\"120\" cy=\"290\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/><ellipse cx=\"120\" cy=\"395\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/></svg>";
    String SVG2_Yes = "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" version=\"1.1\" width=\"828px\" height=\"512px\" viewBox=\"-0.5 -0.5 828 512\" content=\"&lt;mxfile modified=&quot;2019-01-08T15:43:53.650Z&quot; host=&quot;www.draw.io&quot; agent=&quot;Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36&quot; etag=&quot;zyJc15WEMefk8OVY6fhO&quot; version=&quot;10.0.22&quot;&gt;&lt;diagram id=&quot;mZNr6_R3YRMUqTDTcL6z&quot; name=&quot;Page-1&quot;&gt;1ZZNb6MwEIZ/DcdKGENDjpts2mq3VavmsNqjhQewajAyTiD99TVr8+GkWTWHKuIU+7VnxvO8mODhddHeS1LlT4IC9wKfth7+6QUBCiOsfzrlYJRFGBshk4zaTaOwZe9gRd+qO0ahdjYqIbhilSsmoiwhUY5GpBSNuy0V3K1akQxOhG1C+Kn6h1GVGzUOFqP+ACzL+8rodmlWCtJvtp3UOaGimUh44+G1FEKZUdGugXfwei4m7u7M6nAwCaX6UsDd3+enx326zejL6y/VptHvzY01Y0/4zjZsD6sOPQEo6Y8OpJ6VotTiKlcF1zOkh7WS4m0go3tamXigJ1jHc6Khe/3YgChAyYPe0ox8I8ssn6DtNQmcKLZ30xNrczakGyq8CKYLB37rBNjHES99N0EtdjIBGzOleZQmDqL/J1JEZqBOEunBpOlR+mfWBcah4AvOca6vSedYkzMF24ok3Uqjb6rrIqkrc3dS1oKuukoZ52vBhRxNt+VAKmgvdbZn5jvIgtDOJ8bHnxgf++c9drBezBDPjyHCLkS0vDbEcP4Qw6tDjOYH8eg2R4trM7ydH0N0BBFd/ZW4mD/EEH0bRD0dP9jMX/n42Ys3Hw==&lt;/diagram&gt;&lt;/mxfile&gt;\" style=\"background-color: rgb(255, 255, 255);\"><defs/><path d=\"M 1 250 L 850 250\" fill=\"none\" stroke=\"#000000\" stroke-width=\"2\" stroke-miterlimit=\"10\" pointer-events=\"none\"/><ellipse cx=\"120\" cy=\"140\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/><ellipse cx=\"170\" cy=\"90\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/><ellipse cx=\"170\" cy=\"390\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/><ellipse cx=\"120\" cy=\"470\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/><ellipse cx=\"220\" cy=\"40\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/><ellipse cx=\"220\" cy=\"310\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/></svg>";
    String SVG4_Yes = "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" version=\"1.1\" width=\"850px\" height=\"543px\" viewBox=\"-0.5 -0.5 850 543\" style=\"background-color: rgb(255, 255, 255);\"><defs/><path d=\"M 0 200 L 847 200\" fill=\"none\" stroke=\"#000000\" stroke-miterlimit=\"10\" pointer-events=\"none\"/><ellipse cx=\"400\" cy=\"40\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/><ellipse cx=\"400\" cy=\"480\" rx=\"40\" ry=\"40\" fill=\"none\" stroke=\"#000000\" pointer-events=\"none\"/><path d=\"M 460 20 L 340 20\" fill=\"none\" stroke=\"#000000\" stroke-miterlimit=\"10\" pointer-events=\"none\"/><path d=\"M 460 540 L 340 540\" fill=\"none\" stroke=\"#000000\" stroke-miterlimit=\"10\" pointer-events=\"none\"/><path d=\"M 460 420 L 340 420\" fill=\"none\" stroke=\"#000000\" stroke-miterlimit=\"10\" pointer-events=\"none\"/><path d=\"M 460 60 L 340 60\" fill=\"none\" stroke=\"#000000\" stroke-miterlimit=\"10\" pointer-events=\"none\"/></svg>";

    @Before
    public void SetUp(){
    }

    @Test
    public void InsertTest() {

        Pair<List<Shape>,List<Shape>> shapes = SVGParser.fileToShapes(SVG2_Yes);
        List<Shape> s1 = shapes.getFirst();
        List<Shape> s2 = shapes.getSecond();
        Graph g1 = CheckingAlgorithm.createGraph(s1);
        Graph g2 = CheckingAlgorithm.createGraph(s2);
        Pair<Set<List<Edge>>,Set<List<Edge>>> p = CheckingAlgorithm.checkAlgorithem(g1, g2);
        Set<List<Edge>> m1 = p.getFirst();
        Set<List<Edge>> m2 = p.getSecond();
        String D3 = "blablabla";
        String email = "adarne@post.bgu.ac.il";
        dal.InsertViewPoints(D3, SVG2_Yes, s1, s2, g1, g2, m1, m2, email);
/*
        shapes = SVGParser.fileToShapes(SVG4_Yes);
        s1 = shapes.getFirst();
        s2 = shapes.getSecond();
        g1 = CheckingAlgorithm.createGraph(s1);
        g2 = CheckingAlgorithm.createGraph(s2);
        p = CheckingAlgorithm.checkAlgorithem(g1, g2);
        m1 = p.getFirst();
        m2 = p.getSecond();
        D3 = "blablabla";
        email = "adarne@post.bgu.ac.il";
        dal.InsertViewPoints(D3, SVG4_Yes, s1, s2, g1, g2, m1, m2, email);*/
    }

    @Test
    public void get50HeadViewPointsTest() {
        Map<String, List<Shape>> AllViewPoints  = dal.getAllViewPoints();
    }

    @Test
    public void get100HeadViewPointsTest() {
        Map<String, List<Shape>> AllViewPoints  = dal.getAllViewPoints();
    }


    @Test
    public void getObjIDByViewPoint1IDTest() {
        assertEquals( "5c9c882e03dfd17ff94d21f7",dal.getObjIDByViewPointID("5c9c882d03dfd17ff94d21f3"));
    }

    @Test
    public void getObjIDByViewPoint2IDTest() {
        assertEquals( "5c9c87f703dfbbc885fe9f42",dal.getObjIDByViewPointID("5c9c87f703dfbbc885fe9f40"));
    }

}