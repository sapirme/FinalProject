package SystemObj;

import org.bson.types.ObjectId;

import java.util.LinkedList;
import java.util.List;

public class PoolObj {

    private int index = 0;
    private List<ObjectId> allID;

    public void setAllID(List<ObjectId> allID){
        this.allID = allID;
    }

    public List<ObjectId> next8(){
        List<ObjectId> l = new LinkedList<>();
        int i = 0;
        while (i < 8 && index < allID.size()) {
            l.add(allID.get(index));
            index++;
            i++;
        }
        return l;
    }

    public List<ObjectId> prev8(){
        List<ObjectId> l = new LinkedList<>();
        int i = 0;
        while (i < 8 && index > 0) {
            l.add(allID.get(index));
            index--;
            i++;
        }
        return l;
    }
}