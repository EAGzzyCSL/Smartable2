package entry;

/**
 * Created by EAGzzyCSL on 2016/2/11.
 */
public class EntrySomeDay extends Entry {
    public EntrySomeDay(String name) {
        super(name);
    }

    private String title;
    private String annotation;
    private String status = null;//归档情况： 未完成（1） 已完成（2） 已删除（3）

    private Integer create_year;
    private Integer create_month;
    private Integer create_day;
    private Integer create_hour;
    private Integer create_minute;

    private Integer alert_year;
    private Integer alert_month;
    private Integer alert_day;
    private Integer alert_hour;
    private Integer alert_minute;

    public Integer getAlert_year() {
        return alert_year;
    }

    public void setAlert_year(Integer alert_year) {
        this.alert_year = alert_year;
    }

    public Integer getAlert_month() {
        return alert_month;
    }

    public void setAlert_month(Integer alert_month) {
        this.alert_month = alert_month;
    }

    public Integer getAlert_day() {
        return alert_day;
    }

    public void setAlert_day(Integer alert_day) {
        this.alert_day = alert_day;
    }

    public Integer getAlert_hour() {
        return alert_hour;
    }

    public void setAlert_hour(Integer alert_hour) {
        this.alert_hour = alert_hour;
    }

    public Integer getAlert_minute() {
        return alert_minute;
    }

    public void setAlert_minute(Integer alert_minute) {
        this.alert_minute = alert_minute;
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
