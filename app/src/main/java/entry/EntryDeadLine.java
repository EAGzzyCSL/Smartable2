package entry;

/**
 * Created by EAGzzyCSL on 2016/2/11.
 */
public class EntryDeadLine extends Entry {
    public EntryDeadLine(String name) {
        super(name);
    }

    private Integer _id = null;
    private String title = null;
    private String annotation = null;
    private String date_create = null;
    private String status = null;//归档情况： 未完成（1） 已完成（2） 已删除（3）

    private String alert = null;//不提醒(0) 提醒(1)
    private String date_alert = null;
    private String location = null;

    private Integer todo_duration;//总共想花的时间
    private Integer todo_numbers;//想用几次完成（注意加s）
    private String date_ddl = null;

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
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

    public Integer getTodo_duration() {
        return todo_duration;
    }

    public void setTodo_duration(Integer todo_duration) {
        this.todo_duration = todo_duration;
    }

    public Integer getTodo_numbers() {
        return todo_numbers;
    }

    public void setTodo_numbers(Integer todo_numbers) {
        this.todo_numbers = todo_numbers;
    }

    public String getDate_create() {
        return date_create;
    }

    public void setDate_create(String date_create) {
        this.date_create = date_create;
    }

    public String getDate_alert() {
        return date_alert;
    }

    public void setDate_alert(String date_alert) {
        this.date_alert = date_alert;
    }

    public String getDate_ddl() {
        return date_ddl;
    }

    public void setDate_ddl(String date_ddl) {
        this.date_ddl = date_ddl;
    }
}
