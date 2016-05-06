package entry;

import android.content.ContentValues;

import bit.eagzzycsl.smartable2.EnumEntry;
import my.TableFiled;

/**
 * Created by EAGzzyCSL on 2016/2/11.
 */
public class EntryTheseDays extends Entry {
    public EntryTheseDays(String name) {
        super(name);
    }


    private String title = null;
    private String annotation = null;
    private String date_create = null;
    private String status = null;//归档情况： 未完成（1） 已完成（2） 已删除（3）


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

    public ContentValues toContentValues(){
        ContentValues cv = new ContentValues();
        cv.put(TableFiled.TITLE, this.getTitle());
        cv.put(TableFiled.ANNOTATION, this.getAnnotation());
        cv.put(TableFiled.DATE_CREATE, this.getDate_create());
        cv.put(TableFiled.STATUS, this.getStatus());
        return cv;
    }
}
