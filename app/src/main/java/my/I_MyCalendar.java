package my;


/**
 * Created by eagzzycsl on 5/5/16.
 * 接口用于表示规范，方便自定义的日期类和java的calendar类,string类交互信息。
 */
public interface I_MyCalendar {

    //产生表示自己时间的字符串
    String convertToString();

    /*将时间转换为xx年xx月xx日格式*/
    String convertToLocalString();

}