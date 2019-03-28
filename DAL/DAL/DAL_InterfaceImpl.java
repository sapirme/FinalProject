package DAL;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import Graph.*;
import org.bson.types.ObjectId;

import Shapes.Shape;
import com.mongodb.*;
import com.google.gson.*;



public class DAL_InterfaceImpl implements DAL_Interface {
    private DB db;
    private DBCollection Objects;
    private DBCollection ViewPoints;
    private static DAL_Interface single_instance = null;
    private Gson gson = new Gson();

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

    public boolean Connect(){
        try {
            //String connectionString = "mongodb://adar93:Adriel93%21@cluster0-shard-00-00-wy3je.gcp.mongodb.net:27017,cluster0-shard-00-01-wy3je.gcp.mongodb.net:27017,cluster0-shard-00-02-wy3je.gcp.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true";
            String connectionString = "mongodb://localhost:27017";
            MongoClientURI uri = new MongoClientURI(connectionString);
            MongoClient mongoClient = new MongoClient(uri);
            db = mongoClient.getDB("IllusionsDB");
            Objects = db.getCollection("Objects");
            ViewPoints = db.getCollection("ViewPoints");
            return true;
        }catch(IOException ie) {
            ie.printStackTrace();
        }
        return false;
    }
    public boolean InsertViewPoints(String D3, String SVG, List<Shape> s1, List<Shape> s2, Graph g1, Graph g2, Set<List<Edge>> m1, Set<List<Edge>> m2, String email) {

        BasicDBObject document = new BasicDBObject();
        ObjectId id1 = ObjectId.get();
        document.put("ObjectId", id1);
        document.put("Shapes", gson.toJson(s1));
        document.put("Graph", gson.toJson(g1));
        document.put("Paths", gson.toJson(m1));
        ViewPoints.insert(document);

        document = new BasicDBObject();
        ObjectId id2 = ObjectId.get();
        document.put("ObjectId", id2);
        document.put("Shapes", gson.toJson(s2));
        document.put("Graph", gson.toJson(g2));
        document.put("Paths", gson.toJson(m2));
        ViewPoints.insert(document);

        document = new BasicDBObject();
        ObjectId id = ObjectId.get();
        document.put("ObjectId", id);
        document.put("SVG", SVG);
        document.put("3D", D3);
        document.put("ViewPointID1", id1);
        document.put("ViewPointID2", id2);
        document.put("Email", email);
        Objects.insert(document);

        return true;
    }
}
