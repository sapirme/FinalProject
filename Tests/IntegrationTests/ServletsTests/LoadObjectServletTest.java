package IntegrationTests.ServletsTests;

import com.google.gson.Gson;
import flow.bgu.ac.il.IllusionServer;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class LoadObjectServletTest {
    static IllusionServer server;
    static String myUrl = "http://localhost:8090";
    static String charset = java.nio.charset.StandardCharsets.UTF_8.name();
    static List<String> objectsNames = null;


    public static void getNames(){
        try {
            URL url = new URL(myUrl+"/allObjects");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            InputStream response = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(response));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            Gson gson = new Gson();
            objectsNames = gson.fromJson(out.toString(), List.class);   //Prints the string content read from input stream
            reader.close();
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public int sendReq(){
        int status = -1;
        try {

            if (objectsNames.size()>0) {
                URL url = new URL(myUrl+"/LoadObject");
                String toSend = "0";

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true); // Triggers POST.
                connection.setRequestProperty("Accept-Charset", charset);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
                try (OutputStream output = connection.getOutputStream()) {
                    output.write(toSend.getBytes(charset));
                }
                status = connection.getResponseCode();

                connection.disconnect();
            }
        } catch (Exception e) {
            System.out.println("err");
            System.out.println(e.toString());
            System.out.println("err");
        }
        return status;
    }


    @Test
    public void testLoadObjReqWithDb() {
        String[] args = {};
        server = new IllusionServer();
        try{
            server.init(args);
            server.start();
        }catch (Exception e){

        }
        getNames();
        int status = sendReq();

        Assert.assertEquals(200,status);

        try{
            server.stop();
            System.out.println("server closed");
        }catch (Exception e){

        }
    }

    @Test
    public void testLoadObjIllegalInput() {
        String[] args = {};
        server = new IllusionServer();
        try{
            server.init(args);
            server.start();
        }catch (Exception e){

        }
        getNames();
        int status = -1;
        try {
            URL url = new URL(myUrl+"/LoadObject");

            String toSend = "bad input ~!";

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
        Assert.assertEquals(HttpServletResponse.SC_BAD_REQUEST,status);

        try{
            server.stop();
            System.out.println("server closed");
        }catch (Exception e){

        }
    }


}

