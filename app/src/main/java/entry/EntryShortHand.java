package entry;

import android.content.ContentValues;

import my.TableFiled;

/**
 * Created by EAGzzyCSL on 2016/2/11.
 */
public class EntryShortHand extends Entry {
    public EntryShortHand(String name) {
        super(name);
    }


    private String title = null;
    private String annotation = null;
    private String date_create = null;
    private String status = null;//归档情况： 未完成（1） 已完成（2） 已删除（3）

    private String isUpper;//置顶： 是（1） 否（2）
    private String date_upper;


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

    public String getIsUpper() {
        return isUpper;
    }

    public void setIsUpper(String isUpper) {
        this.isUpper = isUpper;
    }

    public String getDate_upper() {
        return date_upper;
    }

    public void setDate_upper(String date_upper) {
        this.date_upper = date_upper;
    }

    public ContentValues toContentValues(){
        ContentValues cv = new ContentValues();
        cv.put(TableFiled.TITLE, this.getTitle());
        cv.put(TableFiled.ANNOTATION, this.getAnnotation());
        cv.put(TableFiled.DATE_CREATE, this.getDate_create());
        cv.put(TableFiled.STATUS, this.getStatus());

        cv.put(TableFiled.IS_UPPER, this.getIsUpper());
        cv.put(TableFiled.DATE_upper, this.getDate_upper());
        return cv;
    }
}
