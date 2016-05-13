package entry;

import android.content.ContentValues;

import my.MyLog;
import my.MyMoment;
import my.TableFiled;

public class EntrySchedule extends Entry {

    //TODO 使用tilte还是使用name问题，我当时文档上写的是title，但是entry类中写的是name，既然这样那就用title好了。
    public EntrySchedule(String name) {
        super(name);
    }

    private String annotation = null;
    private MyMoment date_create = null;
    private String status = null;//归档情况： 未完成（1） 已完成（2） 已删除（3）

    private MyMoment date_begin = null;
    private MyMoment date_end = null;
    private String alert = null;//不提醒(0) 提醒(1)
    private MyMoment date_alert = null;
    private String location = null;

    public EntrySchedule(String title, String annotation, MyMoment date_create, String status
            , MyMoment date_begin, MyMoment date_end, String alert, MyMoment date_alert, String location) {
        this.title = title;
        this.annotation = annotation;
        this.date_create = date_create;
        this.status = status;
        this.date_begin = date_begin;
        this.date_end = date_end;
        this.alert = alert;
        this.date_alert = date_alert;
        this.location = location;
    }

    public EntrySchedule(int id, String title, String annotation, MyMoment date_create, String status
            , MyMoment date_begin, MyMoment date_end, String alert, MyMoment date_alert, String location) {
        this(title, annotation, date_create, status, date_begin, date_end, alert, date_alert, location);
        this.id = id;
    }


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

    public MyMoment getDate_create() {
        return date_create;
    }

    public void setDate_create(MyMoment date_create) {
        this.date_create = date_create;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MyMoment getDate_begin() {
        return date_begin;
    }

    public void setDate_begin(MyMoment date_begin) {
        this.date_begin = date_begin;
    }

    public MyMoment getDate_end() {
        return date_end;
    }

    public void setDate_end(MyMoment date_end) {
        this.date_end = date_end;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public MyMoment getDate_alert() {
        return date_alert;
    }

    public void setDate_alert(MyMoment date_alert) {
        this.date_alert = date_alert;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    //TODO toCV方法要防止空指针异常
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(TableFiled.TITLE, this.getTitle());
        cv.put(TableFiled.ANNOTATION, this.getAnnotation());
        cv.put(TableFiled.DATE_CREATE, this.getDate_create().convertToString());
        cv.put(TableFiled.STATUS, this.getStatus());

        cv.put(TableFiled.DATE_begin, this.getDate_begin().convertToString());
        MyLog.i("toCv时的时间", this.getDate_begin().convertToLocalString());
        cv.put(TableFiled.DATE_end, this.getDate_end().convertToString());
        cv.put(TableFiled.ALERT, this.getAlert());
        cv.put(TableFiled.DATE_alert, this.getDate_alert().convertToString());
        cv.put(TableFiled.LOCATION, this.getLocation());
        return cv;
    }
}
