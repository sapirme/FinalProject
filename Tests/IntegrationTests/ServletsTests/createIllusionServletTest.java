package IntegrationTests.ServletsTests;
import flow.bgu.ac.il.IllusionServer;
import org.junit.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;


public class createIllusionServletTest {
    static IllusionServer server;
    static String myUrl = "http://localhost:8090";
    String charset = java.nio.charset.StandardCharsets.UTF_8.name();
    @BeforeClass
    public static void SetUp(){
        String[] args = {"8090", "", "0"};
        server = new IllusionServer();
        try{
            server.init(args);
            server.start();
        }catch (Exception e){

        }

    }


    @Test
    public void testPostReqIllegalInput() {
        int status = -1;
        try {
            String toSend = "illegal Input";
            URL url = new URL(myUrl+"/run");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true); // Triggers POST.
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            try (OutputStream output = connection.getOutputStream()) {
                output.write(toSend.getBytes(charset));
            }
            status = connection.getResponseCode();

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(500,status);
    }

    @Test
    public void testPostReqLegalInput() {
        int status = -1;
        try {
            String toSend = "[\"<mxGraphModel grid=\\\"1\\\" gridSize=\\\"10\\\" guides=\\\"1\\\" tooltips=\\\"1\\\" connect=\\\"1\\\" arrows=\\\"0\\\" fold=\\\"1\\\" page=\\\"1\\\" pageScale=\\\"1\\\" pageWidth=\\\"850\\\" pageHeight=\\\"1100\\\" background=\\\"#ffffff\\\">\\n  <root>\\n    <mxCell id=\\\"0\\\"/>\\n    <mxCell id=\\\"1\\\" parent=\\\"0\\\"/>\\n    <mxCell id=\\\"2\\\" value=\\\"\\\" style=\\\"rounded=0;endArrow=none;html=1;\\\" edge=\\\"1\\\" parent=\\\"1\\\">\\n      <mxGeometry width=\\\"50\\\" height=\\\"50\\\" relative=\\\"1\\\" as=\\\"geometry\\\">\\n        <mxPoint y=\\\"240\\\" as=\\\"sourcePoint\\\"/>\\n        <mxPoint x=\\\"825\\\" y=\\\"240\\\" as=\\\"targetPoint\\\"/>\\n      </mxGeometry>\\n    </mxCell>\\n    <mxCell id=\\\"3\\\" value=\\\"\\\" style=\\\"ellipse;whiteSpace=wrap;html=1;aspect=fixed;\\\" vertex=\\\"1\\\" parent=\\\"1\\\">\\n      <mxGeometry x=\\\"200\\\" y=\\\"40\\\" width=\\\"80\\\" height=\\\"80\\\" as=\\\"geometry\\\"/>\\n    </mxCell>\\n    <mxCell id=\\\"4\\\" value=\\\"\\\" style=\\\"rhombus;whiteSpace=wrap;html=1;\\\" vertex=\\\"1\\\" parent=\\\"1\\\">\\n      <mxGeometry x=\\\"200\\\" y=\\\"280\\\" width=\\\"80\\\" height=\\\"80\\\" as=\\\"geometry\\\"/>\\n    </mxCell>\\n  </root>\\n</mxGraphModel>\\n\",\"<svg xmlns=\\\"http://www.w3.org/2000/svg\\\" xmlns:xlink=\\\"http://www.w3.org/1999/xlink\\\" version=\\\"1.1\\\" width=\\\"827px\\\" height=\\\"321px\\\" viewBox=\\\"-0.5 -0.5 827 321\\\"><defs/><path d=\\\"M 0 200 L 825 200\\\" fill=\\\"none\\\" stroke=\\\"#000000\\\" stroke-miterlimit=\\\"10\\\" pointer-events=\\\"none\\\"/><ellipse cx=\\\"240\\\" cy=\\\"40\\\" rx=\\\"40\\\" ry=\\\"40\\\" fill=\\\"none\\\" stroke=\\\"#000000\\\" pointer-events=\\\"none\\\"/><path d=\\\"M 240 240 L 280 280 L 240 320 L 200 280 Z\\\" fill=\\\"none\\\" stroke=\\\"#000000\\\" stroke-miterlimit=\\\"10\\\" pointer-events=\\\"none\\\"/></svg>\"]";

            URL url = new URL(myUrl+"/run");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true); // Triggers POST.
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            try (OutputStream output = connection.getOutputStream()) {
                output.write(toSend.getBytes(charset));
            }
            status = connection.getResponseCode();

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(200,status);
    }

    @Test
    public void testGetReq() {
        int status = -1;
        try {
            URL url = new URL(myUrl+"/run");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            InputStream response = connection.getInputStream();
            status = connection.getResponseCode();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(20,status);
    }

    @AfterClass
    public static void tearDown() {
        try{
            server.stop();
            System.out.println("server closed");
        }catch (Exception e){

        }
    }

}

