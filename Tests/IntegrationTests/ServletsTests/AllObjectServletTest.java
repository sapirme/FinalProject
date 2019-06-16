package IntegrationTests.ServletsTests;

import com.google.gson.Gson;
import flow.bgu.ac.il.IllusionServer;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class AllObjectServletTest {
    static IllusionServer server;
    static String myUrl = "http://localhost:8090";
    String charset = java.nio.charset.StandardCharsets.UTF_8.name();

    @Test
    public void testGetAllObjectReqDbNotConnected() {
        String[] args = {"8090", "", "0"};
        server = new IllusionServer();
        try{
            server.init(args);
            server.start();
        }catch (Exception e){

        }
        int status = -1;
        try {
            URL url = new URL(myUrl+"/allObjects");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            InputStream response = connection.getInputStream();
            status = connection.getResponseCode();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(20,status);

        try{
            server.stop();
            System.out.println("server closed");
        }catch (Exception e){

        }
    }

    @Test
    public void testGetAllObjectReqWithDb() {
        String[] args = {};
        server = new IllusionServer();
        try{
            server.init(args);
            server.start();
        }catch (Exception e){

        }
        int status = -1;
        try {
            URL url = new URL(myUrl+"/allObjects");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            InputStream response = connection.getInputStream();
            status = connection.getResponseCode();
            Assert.assertEquals(200,status);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            Gson gson = new Gson();
            List<String> ans = gson.fromJson(out.toString(), List.class);   //Prints the string content read from input stream
            reader.close();
            connection.disconnect();
            Assert.assertTrue(true);

        } catch (Exception e) {
            Assert.assertTrue(false);
            e.printStackTrace();
        }


        try{
            server.stop();
            System.out.println("server closed");
        }catch (Exception e){

        }
    }


}

