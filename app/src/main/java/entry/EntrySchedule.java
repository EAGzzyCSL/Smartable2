package entry;

import android.content.ContentValues;

import my.MyMoment;
import my.MyTime;
import my.TableFiled;

public class EntrySchedule extends Entry {

    public EntrySchedule(String name) {
        super(name);
    }

    private String title = null;
    private String annotation = null;
    private String date_create = null;
    private String status = null;//归档情况： 未完成（1） 已完成（2） 已删除（3）

    private String date_begin = null;
    private String date_end = null;
    private String alert = null;//不提醒(0) 提醒(1)
    private String date_alert = null;
    private String location = null;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getDate_create() {
        return date_create;
    }

    public void setDate_create(String date_create) {
        this.date_create = date_create;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate_begin() {
        return date_begin;
    }

    public void setDate_begin(String date_begin) {
        this.date_begin = date_begin;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getDate_alert() {
        return date_alert;
    }

    public void setDate_alert(String date_alert) {
        this.date_alert = date_alert;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(TableFiled.TITLE, this.getTitle());
        cv.put(TableFiled.ANNOTATION, this.getAnnotation());
        cv.put(TableFiled.DATE_CREATE, this.getDate_create());
        cv.put(TableFiled.STATUS, this.getStatus());

        cv.put(TableFiled.DATE_begin, this.getDate_begin());
        cv.put(TableFiled.DATE_end, this.getDate_end());
        cv.put(TableFiled.ALERT, this.getAlert());
        cv.put(TableFiled.DATE_alert, this.getDate_alert());
        cv.put(TableFiled.LOCATION, this.getLocation());
        return cv;
    }

    //以下是赵仲印写的-为了测试
    //临时的修改为了适应日历view
    private MyMoment start;
    private MyMoment end;

//    public EntrySchedule(int _id, String name, MyTime start, MyTime end) {
//        this.id = _id;
//        this.name = name;
//        this.start = start;
//        this.end = end;
//
//    }

    public EntrySchedule(int id, String name, MyMoment start, MyMoment end) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public EntrySchedule(int id, String name, String start, String end) {
        this(id, name, MyMoment.createFromString(start), MyMoment.createFromString(end));
    }

    public MyMoment getStart() {
        return this.start;
    }

    public MyMoment getEnd() {
        return this.end;
    }
}
