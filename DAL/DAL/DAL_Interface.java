package DAL;

import Graph.Edge;
import Graph.Graph;
import Shapes.Shape;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface DAL_Interface {
     boolean InsertObject(String D3, String SVG, List<Shape> s1, List<Shape> s2, Graph g1, Graph g2, LinkedList<List<Edge>> m1, LinkedList<List<Edge>> m2, String email);
     List<String> getAllIDs();
     List<String> getNext8(List<String> IDs);
     Map<String,List<Shape>>  getAllViewPoints(); // return map of vp's id and vp's shapes
     String getObjIDByViewPointID(String vpID);
}
