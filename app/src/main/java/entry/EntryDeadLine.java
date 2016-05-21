package entry;

import android.content.ContentValues;

import my.MyMoment;
import my.TableFiled;

/**
 * Created by EAGzzyCSL on 2016/2/11.
 */
public class EntryDeadLine extends Entry {
    public EntryDeadLine(String name) {
        super(name);
    }

    private String annotation = null;
    private MyMoment date_create = null;
    private String status = null;//归档情况： 未完成（1） 已完成（2） 已删除（3）

    private String alert = null;//不提醒(0) 提醒(1)
    private MyMoment date_alert = null;
    private String location = null;

    private int todo_duration;//总共想花的时间
    private int todo_numbers;//想用几次完成（注意加s）
    private MyMoment date_ddl = null;

    public EntryDeadLine(String title, String annotation, MyMoment date_create, String status, String alert,
                         MyMoment date_alert, String location, int todo_duration, int todo_numbers, MyMoment date_ddl) {
        this.title = title;
        this.annotation = annotation;
        this.date_create = date_create;
        this.status = status;

        this.alert = alert;
        this.date_alert = date_alert;
        this.location = location;
        this.todo_duration = todo_duration;
        this.todo_numbers = todo_numbers;
        this.date_ddl = date_ddl;
    }

    public EntryDeadLine(int id, String title, String annotation, MyMoment date_create, String status, String alert,
                         MyMoment date_alert, String location, int todo_duration, int todo_numbers, MyMoment date_ddl) {
        this(title, annotation, date_create, status, alert, date_alert, location, todo_duration, todo_numbers, date_ddl);
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTodo_duration() {
        return todo_duration;
    }

    public void setTodo_duration(int todo_duration) {
        this.todo_duration = todo_duration;
    }

    public int getTodo_numbers() {
        return todo_numbers;
    }

    public void setTodo_numbers(int todo_numbers) {
        this.todo_numbers = todo_numbers;
    }

    public MyMoment getDate_create() {
        return date_create;
    }

    public void setDate_create(MyMoment date_create) {
        this.date_create = date_create;
    }

    public MyMoment getDate_alert() {
        return date_alert;
    }

    public void setDate_alert(MyMoment date_alert) {
        this.date_alert = date_alert;
    }

    public MyMoment getDate_ddl() {
        return date_ddl;
    }

    public void setDate_ddl(MyMoment date_ddl) {
        this.date_ddl = date_ddl;
    }

    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(TableFiled.TITLE, this.getTitle());
        cv.put(TableFiled.ANNOTATION, this.getAnnotation());
        cv.put(TableFiled.DATE_CREATE, this.getDate_create().convertToString());
        cv.put(TableFiled.STATUS, this.getStatus());

        cv.put(TableFiled.ALERT, this.getAlert());
        cv.put(TableFiled.DATE_alert, this.getDate_alert().convertToString());
        cv.put(TableFiled.LOCATION, this.getLocation());

        cv.put(TableFiled.TODO_DURATION, this.getTodo_duration());
        cv.put(TableFiled.TODO_NUMBERS, this.getTodo_numbers());
        cv.put(TableFiled.DATE_ddl, this.getDate_ddl().convertToString());
        return cv;
    }
}
