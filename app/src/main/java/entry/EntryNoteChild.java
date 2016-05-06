package entry;

import android.content.ContentValues;

import bit.eagzzycsl.smartable2.EnumEntry;

/**
 * Created by 宇 on 2016/3/12.
 */
public class EntryNoteChild extends Entry{
    public EntryNoteChild(String name) {
        super(name);
    }


    @Override
    public ContentValues toContentValues() {
        return null;
    }

    private String title;
    private String annotation;
    private String status = null;//归档情况： 未完成（1） 已完成（2） 已删除（3）

    private Integer create_year;
    private Integer create_month;
    private Integer create_day;
    private Integer create_hour;
    private Integer create_minute;


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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCreate_year() {
        return create_year;
    }

    public void setCreate_year(Integer create_year) {
        this.create_year = create_year;
    }

    public Integer getCreate_month() {
        return create_month;
    }

    public void setCreate_month(Integer create_month) {
        this.create_month = create_month;
    }

    public Integer getCreate_day() {
        return create_day;
    }

    public void setCreate_day(Integer create_day) {
        this.create_day = create_day;
    }

    public Integer getCreate_hour() {
        return create_hour;
    }

    public void setCreate_hour(Integer create_hour) {
        this.create_hour = create_hour;
    }

    public Integer getCreate_minute() {
        return create_minute;
    }

    public void setCreate_minute(Integer create_minute) {
        this.create_minute = create_minute;
    }
}
