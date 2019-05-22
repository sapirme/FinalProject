package DAL;

import Graph.*;
import Shapes.Shape;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Set;
import java.util.Map;

public interface DAL_Interface {
     boolean InsertObject(String D3, String SVG, List<Shape> s1, List<Shape> s2, Graph g1, Graph g2, Set<List<Edge>> m1, Set<List<Edge>> m2, String email);
     List<ObjectId> getAllIDs();
     List<String> getNext8(List<ObjectId> IDs);
     Map<String,List<Shape>>  getAllViewPoints(); // return map of vp's id and vp's shapes
     String getObjIDByViewPointID(String vpID);
}
