package data;

import java.util.ArrayList;

import entry.EntryNotebook;

/**
 * Created by 宇 on 2016/2/15.
 */
public class Data {
    private static Data instance;
    private ArrayList<EntryNotebook> entryNotebookList;

    private Data(){
        generate();
    }

    public static Data getInstance(){
        if(null == instance){
            instance = new Data();
        }
        return instance;
    }

    public ArrayList<EntryNotebook>getData() {
        return entryNotebookList;
    }

    private void generate(){
        for(int i = 0;i<20;i++){
            this.entryNotebookList.add(new EntryNotebook("title" + i));
        }
    }
}
