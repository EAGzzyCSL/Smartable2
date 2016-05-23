package entry;

import android.content.ContentValues;

import my.MyMoment;
import my.TableFiled;

/**
 * Created by 宇 on 2016/3/12.
 */
public class EntryNotebook extends Entry{
    public EntryNotebook(String name) {
        super(name);
    }

    private String annotation = null;
    private MyMoment date_create = null;
    private String status = null;//归档情况： 未完成（1） 已完成（2） 已删除（3）

    public EntryNotebook(String title, String annotation, MyMoment date_create, String status) {
        this.title = title;
        this.annotation = annotation;
        this.date_create = date_create;
        this.status = status;
    }

    public EntryNotebook(int id, String title, String annotation, MyMoment date_create, String status) {
        this(title, annotation, date_create, status);
        this.id = id;
    }

    @Override
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

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(TableFiled.TITLE, this.getTitle());
        cv.put(TableFiled.ANNOTATION, this.getAnnotation());
        cv.put(TableFiled.DATE_CREATE, this.getDate_create().convertToString());
        cv.put(TableFiled.STATUS, this.getStatus());
        return cv;
    }
}
