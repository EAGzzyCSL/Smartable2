package view;

import java.util.ArrayList;
import java.util.HashMap;

import entry.EntrySchedule;
import my.MyDate;

public abstract class CalendricViewItemProvider {
    private HashMap<Integer, ArrayList<EntrySchedule>> his = new HashMap<>();

    /*一个hash表用作缓存，但是该如何建立key还是一个问题*/
    public ArrayList<EntrySchedule> getItems(MyDate myDate) {
        return readFromDatabase(myDate);
    }

    /*覆写该方法从数据库中读取内容完成展示一周或者一天或者一个月的事项，所以这儿应该是两个参数，一个参数是从哪天开始，一个参数是读几天的*/
    public abstract ArrayList<EntrySchedule> readFromDatabase(MyDate myDate);
}
