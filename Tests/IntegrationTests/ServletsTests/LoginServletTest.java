package IntegrationTests.ServletsTests;

import com.google.gson.Gson;
import flow.bgu.ac.il.IllusionServer;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class LoginServletTest {
    static IllusionServer server;
    static String myUrl = "http://localhost:8090";
    String charset = java.nio.charset.StandardCharsets.UTF_8.name();

    @Test
    public void testLoginIllegalToken() {
        String[] args = {};
        server = new IllusionServer();
        try{
            server.init(args);
            server.start();
        }catch (Exception e){

        }
        int status = -1;
        try {
            URL url = new URL(myUrl+"/login");
            String toSend = "illegal Input";

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

        try{
            server.stop();
            System.out.println("server closed");
        }catch (Exception e){

        }
    }


    @Test
    public void testLogin() {
        String[] args = {};
        server = new IllusionServer();
        try{
            server.init(args);
            server.start();
        }catch (Exception e){

        }
        int status = -1;
        try {
            URL url = new URL(myUrl+"/login");
            String toSend = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjY4NjQyODlmZmE1MWU0ZTE3ZjE0ZWRmYWFmNTEzMGRmNDBkODllN2QiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiYXpwIjoiNTkyNzAyNzUyMDcyLXBiZjVqOHEydDNiZzVtdTFodGlzanFobTk1bXMybGpyLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXVkIjoiNTkyNzAyNzUyMDcyLXBiZjVqOHEydDNiZzVtdTFodGlzanFobTk1bXMybGpyLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTA3NzY3NDg3ODMyNTUxNTczMzkxIiwiZW1haWwiOiJzYXBpci5tZW5haGVtQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdF9oYXNoIjoiLVZ3ZzJBQnhRQW9MeEV2M2sxZDlBdyIsIm5hbWUiOiJzYXBpciBtZW5haGVtIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS8tS1pmWTZWb2FvS2cvQUFBQUFBQUFBQUkvQUFBQUFBQUFBQlUvRVVkUTYzV2FkNjAvczk2LWMvcGhvdG8uanBnIiwiZ2l2ZW5fbmFtZSI6InNhcGlyIiwiZmFtaWx5X25hbWUiOiJtZW5haGVtIiwibG9jYWxlIjoiaGUiLCJpYXQiOjE1NjA3NjAxNDksImV4cCI6MTU2MDc2Mzc0OSwianRpIjoiZDg4ODJmZDU0ZmUzMTA2OGI3Y2QzZjcyZWFjNTRmMDE4ZjQ3ODcxZCJ9.DQGunzRUiWlzqlj4DQ1FHLeLC6gkuKYtGOfRe7hTJIS8IG_TcL_YgtI8-cdF01Ac2NbCPyaGFuxagI1T8VwZp2s0JwtCi4feqQQbPg7TWJdzE_jsRSi_wRMsAIsKYfy5CVJpNiPWksVx1PF7lPpGiLjRwwaC6vrCTqTvToBEVtVb2B7My3re-QlF21vrd4aKPq3oTjhBkrzY5jSNQGfawRK6Pf4HRj7gIz1CxS7hC2mQ6-qPJ1mqH941ztn0ETqW4owZWShz-FyMUs9znClYwHOA5hRpdHbEmCxmgSaGgiCQZ5ilG4iXkMB1NZdABp88d8kioqhApwO5iDSkh_QCHA";

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

        status = -1;
        try {
            URL url = new URL(myUrl+"/login");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            status = connection.getResponseCode();

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(200,status);

        try{
            server.stop();
            System.out.println("server closed");
        }catch (Exception e){

        }
    }



}

