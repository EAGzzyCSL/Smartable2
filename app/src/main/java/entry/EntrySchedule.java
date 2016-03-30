package entry;

import my.MyTime;
public class EntrySchedule extends Entry {
    //临时的修改为了适应日历view
    private int id;
    private MyTime start;
    private MyTime end;
    public EntrySchedule(String name) {
        super(name);
    }

    public EntrySchedule(int id, String name, MyTime start, MyTime end) {
        this.id=id;
        this.name=name;
        this.start=start;
        this.end=end;

    }
    public int getId(){
        return id;
    }
    public MyTime getStart(){
        return this.start;
    }
    public MyTime getEnd(){
        return this.end;
    }
}
