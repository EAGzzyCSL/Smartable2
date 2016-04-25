package entry;

/**
 * Created by EAGzzyCSL on 2016/2/11.
 */
public class EntryTrigger extends Entry {
    public EntryTrigger(String name) {
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

    private Integer start_year;
    private Integer start_month;
    private Integer start_day;
    private Integer start_hour;
    private Integer start_minute;

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

    public Integer getStart_year() {
        return start_year;
    }

    public void setStart_year(Integer start_year) {
        this.start_year = start_year;
    }

    public Integer getStart_month() {
        return start_month;
    }

    public void setStart_month(Integer start_month) {
        this.start_month = start_month;
    }

    public Integer getStart_day() {
        return start_day;
    }

    public void setStart_day(Integer start_day) {
        this.start_day = start_day;
    }

    public Integer getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(Integer start_hour) {
        this.start_hour = start_hour;
    }

    public Integer getStart_minute() {
        return start_minute;
    }

    public void setStart_minute(Integer start_minute) {
        this.start_minute = start_minute;
    }
}
