package DAL;

import Graph.Edge;
import Graph.Graph;
import Shapes.Shape;
import com.google.gson.Gson;
import com.mongodb.*;
import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.net.*;
import java.io.*;


public class DAL_InterfaceImpl implements DAL_Interface {
    private String myUrl = "http://132.72.23.63:3043/run";
    private static DAL_Interface single_instance = null;
    private Gson gson = new Gson();

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

    public int Send(String document){
        String charset = java.nio.charset.StandardCharsets.UTF_8.name();  // Or in Java 7 and later, use the constant: java.nio.charset.StandardCharsets.UTF_8.name()
        int status = -1;
        try {
            URL url = new URL(myUrl);
            /*String query = String.format("param1=%s",
                    URLEncoder.encode(document, charset));*/
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true); // Triggers POST.
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            try (OutputStream output = connection.getOutputStream()) {
                output.write(document.getBytes(charset));
            }
            status = connection.getResponseCode();
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println(status);

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    return status;

    }
    public boolean InsertViewPoints(String D3, String SVG, List<Shape> s1, List<Shape> s2, Graph g1, Graph g2, Set<List<Edge>> m1, Set<List<Edge>> m2, String email) {

        BasicDBObject[] document = new BasicDBObject[3];
        document[0] = new BasicDBObject();
        ObjectId id1 = ObjectId.get();
        document[0].put("ObjectId", id1);
        document[0].put("Shapes", gson.toJson(s1));
        document[0].put("Graph", gson.toJson(g1));
        document[0].put("Paths", gson.toJson(m1));

        document[1] = new BasicDBObject();
        ObjectId id2 = ObjectId.get();
        document[1].put("ObjectId", id2);
        document[1].put("Shapes", gson.toJson(s2));
        document[1].put("Graph", gson.toJson(g2));
        document[1].put("Paths", gson.toJson(m2));

        document[2] = new BasicDBObject();
        ObjectId id = ObjectId.get();
        document[2].put("ObjectId", id);
        document[2].put("SVG", SVG);
        document[2].put("3D", D3);
        document[2].put("ViewPointID1", id1);
        document[2].put("ViewPointID2", id2);
        document[2].put("Email", email);

        int status = Send(gson.toJson(document));
        if (status == 200)
            return true;
        else return false;
    }

    public Map<String,List<Shape>> getAllViewPoints() { // return map of vp's id and vp's shapes
        Map<String,List<Shape>> allViewPoints = new HashMap<String,List<Shape>>();
        BasicDBObject searchQuery = new BasicDBObject();
        DBCursor cursor = ViewPoints.find();
        while (cursor.hasNext()) {
            DBObject obj = cursor.next();
            String objId = obj.get("ObjectId").toString();
            System.out.println(objId);
            List<Shape> shapes = gson.fromJson(obj.get("Shapes").toString(),List.class);
            System.out.println(shapes.toString());
            allViewPoints.put(objId,shapes);
        }
        return allViewPoints;
    }

    public String getObjIDByViewPointID(String vpID){
        String objId = "";
        ObjectId toSearch = new ObjectId(vpID);
        BasicDBObject searchQuery = new BasicDBObject().append("ViewPointID1", toSearch);
        //searchQuery.put("ViewPointID1", "5c9c873403dfe0b563a3b82f");
        DBCursor cursor = Objects.find(searchQuery);
        if (cursor.hasNext()) {
            while (cursor.hasNext()) {
                DBObject obj = cursor.next();
                objId = obj.get("ObjectId").toString();
                //System.out.println(objId);
            }
        }
        else{
            searchQuery = new BasicDBObject().append("ViewPointID2", toSearch);
            cursor = Objects.find(searchQuery);
            if (cursor.hasNext()) {
                while (cursor.hasNext()) {
                    DBObject obj = cursor.next();
                    objId = obj.get("ObjectId").toString();
                    //System.out.println(objId);
                }
            }
        }
        return objId;
    }
}
