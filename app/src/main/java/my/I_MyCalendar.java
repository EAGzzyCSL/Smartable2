package my;

import java.util.Calendar;

/**
 * Created by eagzzycsl on 5/5/16.
 * 接口用于表示规范，方便自定义的日期类和java的calendar类,string类交互信息。
 */
public interface I_MyCalendar {
    /*--------###数据从自己流向calendar###--------*/

    /*产生和自己日期一致的日历对象*/
    Calendar convertToCalendar();

    /*将日历对象的时间更新为自己的时间*/
    void syncToCalendar(Calendar c);

    /*--------###数据从calendar流向自己###--------**/

    /*创建一个时间与已知日历对象一致的自己*/
    //这个方法本该是静态，但是需要java8的支持，而studio要2.1+才支持java8编译，考虑到这个方法目前用不上，暂时忽略它
    //Object createFromCalendar(Calendar c);

    /*将自己的时间更新为日历对象的时间*/
    void syncFromCalendar(Calendar c);

    /*--------###数据从自己流向string###--------**/

    //产生表示自己时间的字符串
    String convertToString();

    /*--------###数据从string流向自己###--------**/

    /*这里也是同样的问题，替代方法为在各个类中写三遍*/
    //static Object createFromString(String s);

    /*将时间转换为xx年xx月xx日格式*/
     String convertToLocalString();

}