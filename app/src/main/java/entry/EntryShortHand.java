package entry;

/**
 * Created by EAGzzyCSL on 2016/2/11.
 */
public class EntryShortHand extends Entry {
    public EntryShortHand(String name) {
        super(name);
    }

    private Integer _id = null;
    private String title = null;
    private String annotation = null;
    private String date_create = null;
    private String status = null;//归档情况： 未完成（1） 已完成（2） 已删除（3）

    private String isUpper;//置顶： 是（1） 否（2）
    private String date_upper;

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
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
}
