package entry;

import android.content.ContentValues;

import my.MyMoment;
import my.TableFiled;

/**
 * Created by EAGzzyCSL on 2016/2/11.
 */
public class EntryShortHand extends Entry {
    public EntryShortHand(String name) {
        super(name);
    }


    private String title;
    private String annotation;
    private MyMoment date_create;
    private String status = null;//归档情况： 未完成（1） 已完成（2） 已删除（3）

    private String isUpper;//置顶： 是（1） 否（2）
    private MyMoment date_upper;

    public EntryShortHand(String title, String annotation, MyMoment date_create, String status, String isUpper, MyMoment date_upper) {
        this.title = title;
        this.annotation = annotation;
        this.date_create = date_create;
        this.status = status;
        this.isUpper = isUpper;
        this.date_upper = date_upper;
    }
    //
    public EntryShortHand(int id, String title, String annotation, MyMoment date_create, String status, String isUpper, MyMoment date_upper) {
        this(title, annotation, date_create, status, isUpper, date_upper);
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

    public String getIsUpper() {
        return isUpper;
    }

    public void setIsUpper(String isUpper) {
        this.isUpper = isUpper;
    }

    public MyMoment getDate_upper() {
        return date_upper;
    }

    public void setDate_upper(MyMoment date_upper) {
        this.date_upper = date_upper;
    }

    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(TableFiled.TITLE, this.getTitle());
        cv.put(TableFiled.ANNOTATION, this.getAnnotation());
        cv.put(TableFiled.DATE_CREATE, this.getDate_create().convertToString());
        cv.put(TableFiled.STATUS, this.getStatus());

        cv.put(TableFiled.IS_UPPER, this.getIsUpper());
        cv.put(TableFiled.DATE_upper, date_upper == null ? null : date_upper.convertToString());
        return cv;
    }
}
