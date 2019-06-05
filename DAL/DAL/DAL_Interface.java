package DAL;

import Graph.Pair;

import java.util.List;
import java.util.Map;

public interface DAL_Interface {
     boolean InsertObject(String D3, String SVG, String XML, int circles1, int lines1, int circles2, int lines2, String id_token);
     List<String> getAllIDs();
     List<String> getNext8(List<String> IDs);
     Map<String, Pair<Integer, Integer>> getAllViewPoints(); // return map of vp's id and vp's shapes
     List<String> getObjIDByViewPointID(List<String> vpIDs);

     String getObjectXml(String objectId);
}
