package SystemObj;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class PoolObj {

    private int index = 0;
    private List<String> allID;

    public void setAllID(List<String> allID){
        this.allID = allID;
    }

    public List<String> next8(){
        List<String> l = new LinkedList<>();
        int i = 0;
        while (i < 8 && index < allID.size()) {
            l.add(allID.get(index));
            index++;
            i++;
        }
        return l;
    }

    public List<String> prev8(){
        List<String> l = new LinkedList<>();
        int i = 0;
        while (i < 8 && index > 0) {
            l.add(allID.get(index));
            index--;
            i++;
        }
        return l;
    }

    public void saveFiles(List<String> files){
        BufferedWriter output = null;
        int i = 0;
        try {
            for (String f: files) {
                File file = new File(".\\files\\dbObject"+i+".stl");
                output = new BufferedWriter(new FileWriter(file));
                output.write(f);
                i++;

            }
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if ( output != null ) {
                try {
                    output.close();
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        }

    }
}