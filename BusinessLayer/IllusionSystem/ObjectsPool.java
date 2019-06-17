package IllusionSystem;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

public class ObjectsPool {
    private int indexStart = 0; //include
    private int indexEnd = 0; //not include
    private List<String> allID;

    public void setAllID(List<String> allID){
        this.allID = allID;
        this.indexStart = 0;
        this.indexEnd = 0;
    }

    public String getIndexName(int index){
        if (index < 0 || index > 7 || indexStart+index >= allID.size()){
            return null;
        }
        return allID.get(indexStart+index);
    }

    public List<String> next8(){
        List<String> l = new LinkedList<>();
        int i = 0;
        if (indexEnd < allID.size())    indexStart=indexEnd;

        while (i < 8 && indexEnd < allID.size()) {
            l.add(allID.get(indexEnd));
            indexEnd++;
            i++;
        }
        return l;
    }

    public List<String> prev8(){
        List<String> l = new LinkedList<>();
        int i = 0;

        if (indexStart >= 0) {
            indexEnd=indexStart;
        }
        indexStart--;
        while (i < 8 && indexStart >= 0) {
            l.add(0,allID.get(indexStart));
            indexStart--;
            i++;
        }
        indexStart++;
        return l;
    }

    public List<Integer> saveFiles(List<String> files){
        List<Integer> ans = new ArrayList<Integer>();
        BufferedWriter output = null;
        int i=0;
        for (String f: files) {
            try {
                File file = new File(".\\files\\dbObject" + i + ".stl");
                output = new BufferedWriter(new FileWriter(file));
                output.write(f);
                ans.add(i);
                i++;

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return ans;
    }
}