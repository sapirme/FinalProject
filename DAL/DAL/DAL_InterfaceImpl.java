package DAL;

import Graph.Edge;
import Graph.Graph;
import Shapes.Shape;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import org.bson.types.ObjectId;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class DAL_InterfaceImpl implements DAL_Interface {
    private String myUrl = "http://132.72.23.63:3043";
    private static DAL_Interface single_instance = null;
    private Gson gson = new Gson();
    String charset = java.nio.charset.StandardCharsets.UTF_8.name();  // Or in Java 7 and later, use the constant: java.nio.charset.StandardCharsets.UTF_8.name()


    private DBCollection Objects;
    private DBCollection ViewPoints;

    private DAL_InterfaceImpl()
    {

    }

    public static DAL_Interface getInstance()
    {
        if(single_instance  == null)
        {
            single_instance  = new DAL_InterfaceImpl();
        }
        return single_instance ;
    }

    private int Send(String document){
        int status = -1;
        try {
            URL url = new URL(myUrl+"/run");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true); // Triggers POST.
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            try (OutputStream output = connection.getOutputStream()) {
                output.write(document.getBytes(charset));
            }
            status = connection.getResponseCode();
            System.out.println("Insert Status: ");
            System.out.println(status);

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;

    }


    public boolean InsertObject(String D3, String SVG, String XML, List<Shape> s1, List<Shape> s2, Graph g1, Graph g2, LinkedList<List<Edge>> m1, LinkedList<List<Edge>> m2, String email) {

        BasicDBObject[] document = new BasicDBObject[3];
        document[0] = new BasicDBObject();
        ObjectId id1 = ObjectId.get();
        document[0].put("ObjectId", id1.toString());
        document[0].put("Shapes", s1);
        document[0].put("Graph", gson.toJson(g1));
        document[0].put("Paths", gson.toJson(m1));

        document[1] = new BasicDBObject();
        ObjectId id2 = ObjectId.get();
        document[1].put("ObjectId", id2.toString());
        document[1].put("Shapes", s2);
        document[1].put("Graph", gson.toJson(g2));
        document[1].put("Paths", gson.toJson(m2));

        document[2] = new BasicDBObject();
        ObjectId id = ObjectId.get();
        document[2].put("ObjectId", id.toString());
        document[2].put("SVG", SVG);
        document[2].put("XML", XML);
        document[2].put("3D", D3);
        document[2].put("ViewPointID1", id1.toString());
        document[2].put("ViewPointID2", id2.toString());
        document[2].put("Email", email);

        int status = Send(gson.toJson(document));
        if (status == 200)
            return true;
        else return false;
    }

    public List<String> getAllIDs(){
        try {
            URL url = new URL(myUrl+"/run");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            InputStream response = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(response));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            List<String> ans = gson.fromJson(out.toString(),List.class);   //Prints the string content read from input stream
            reader.close();
            connection.disconnect();
            return ans;
        } catch (Exception e) {
            e.printStackTrace();
            //return null;
        }
        return null;
    }

    public List<String> getNext8(List<String> IDs){
        int status = -1;
        try {
            URL url = new URL(myUrl+"/getillusions");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true); // Triggers POST.
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            try (OutputStream output = connection.getOutputStream()) {
                output.write(gson.toJson(IDs).getBytes(charset));
            }
            status = connection.getResponseCode();
            if (status == 200) {
                InputStream response = connection.getInputStream();
                System.out.println("hereeee+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                BufferedReader reader = new BufferedReader(new InputStreamReader(response));
                StringBuilder out = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                }
                List<String> ans = gson.fromJson(out.toString(), List.class);   //Prints the string content read from input stream
                reader.close();
                connection.disconnect();
                return ans;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;

    }

    public Map<String,List<Shape>> getAllViewPoints() { // return map of vp's id and vp's shapes
        try {
            URL url = new URL(myUrl+"/similarities");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            InputStream response = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(response));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            //Map<String,List<Shape>> ans = new HashMap<String,List<Shape>>();


            Map<String,List<Shape>> ans =  gson.fromJson(out.toString(), Map.class);   //Prints the string content read from input stream
            /*
            for (Map.Entry<String,List<String>> entry: map.entrySet()){
                //List<String> list =  gson.fromJson(entry.getValue(), List.class);
                List <Shape> listAns = new LinkedList<Shape>();
                for (String st : entry.getValue()){
                    listAns.add(gson.fromJson(st, Shape.class)new Circle(1,1,1));
                }
                ans.put(entry.getKey(),listAns);
            }*/

            reader.close();
            connection.disconnect();
            return ans;
        } catch (Exception e) {
            e.printStackTrace();
            //return null;
        }
        return null;
    }

    public List<String> getObjIDByViewPointID(List<String> vpIDs){
        int status = -1;
        try {
            URL url = new URL(myUrl+"/similarities");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true); // Triggers POST.
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            try (OutputStream output = connection.getOutputStream()) {
                output.write(gson.toJson(vpIDs).getBytes(charset));
            }
            status = connection.getResponseCode();
            if (status == 200) {
                InputStream response = connection.getInputStream();
                System.out.println("hereeee+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                BufferedReader reader = new BufferedReader(new InputStreamReader(response));
                StringBuilder out = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                }
                List<String> ans = gson.fromJson(out.toString(), List.class);   //Prints the string content read from input stream
                reader.close();
                connection.disconnect();
                return ans;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;

    }

    @Override
    public String getObjectXml(String objectId) {
        int status = -1;
        try {
            URL url = new URL(myUrl+"/getxml");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true); // Triggers POST.
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            try (OutputStream output = connection.getOutputStream()) {
                output.write(objectId.getBytes(charset));
            }
            status = connection.getResponseCode();
            if (status == 200) {
                InputStream response = connection.getInputStream();
                System.out.println("hereeee+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                BufferedReader reader = new BufferedReader(new InputStreamReader(response));
                StringBuilder out = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                }
                String ans = out.toString();   //Prints the string content read from input stream
                reader.close();
                connection.disconnect();
                //System.out.println(ans);
                return ans;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
