package my;

/**
 * Created by 宇 on 2016/3/12.
 */
public interface TableFiled {

    interface TableName{
        String Schedule="Schedule";
        String TheseDays="TheseDays";
        String DDL="DDL";
        String ShortHand="ShortHand";
        String SomeDay="SomeDay";
        String Trigger="Trigger";
    }
    //公有的
    String ID = "_id";
    String TITLE = "title";
    String ANNOTATION = "annotation";//备注 -目前全部复制为 ""
    String STATUS = "status";//归档情况： 未完成（1） 已完成（2） 已删除（3）
    String DATE_CREATE = "date_create";

    //大部分都有的
    String DATE_begin = "date_begin";
    String DATE_end = "date_end";
    String ALERT = "alert";//提醒状态 否（0）；是(1)---（以后改进：填写具体数字，代表提前多少[分钟]提醒，0表示准时提醒，可以多次提醒）
    String DATE_alert = "date_alert";
    String LOCATION = "location";

    //1. 日程-Schedule
    //todo linkNote,详见Smartabl.pdf schedule

    //2. 这两天-TheseDays
    //ing...

    //3. DDL
    String DATE_ddl = "date_ddl";
    String TODO_DURATION = "todo_duration";//总共想花的时间
    String TODO_NUMBERS = "todo_numbers";//想用几次完成（注意加s）

    //4. 触发-Trigger
    //ing...

    //5. 速记-ShortHand
    String IS_UPPER = "isUpper";//置顶： 是（1） 否（2）
    String DATE_upper = "date_upper";

    //6. 有朝一日-Someday
    //ing...

    //7.1 笔记本 - Notebook
    //注意：删除笔记本的时候，系统自动先将齐下笔记标记为删除，然后再将笔记本标记为删除

    //7.2 笔记 - NoteChild
    //注意将_id的外键设为Notebook的_id
}
