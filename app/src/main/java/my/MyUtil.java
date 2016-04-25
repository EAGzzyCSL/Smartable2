package my;


import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUtil {
    //一个工具类，提供一些静态方法供调用
    private static String[] weekEtoCString = new String[]{"", "日", "一", "二", "三", "四", "五", "六"};

    public static String weekEtoC(int e) {
        return "周" + weekEtoCString[e];
    }

    public static int dpToPxInCode(float density, int dp) {
        return (int) (density * dp + 0.5);
    }

    public static int pxToDpInCode(float density, int px) {
        return (int) (px / density + 0.5);
    }

    //传过来一个日期和时间，并给一个提醒类型，返回一个具体的提醒时间
    public static String getAlertTime(String date, String remind_picker) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date_ddl = simpleDateFormat.parse(date);
        String Date_alert = null;
        if(remind_picker == "30min"){
            Date_alert = simpleDateFormat.format(date_ddl.getTime() - 30 * 60 * 1000);
        }
        else if(remind_picker == "1h"){
            Date_alert = simpleDateFormat.format(date_ddl.getTime() - 60 * 60 * 1000);
        }
        else if(remind_picker == "3h"){
            Date_alert = simpleDateFormat.format(date_ddl.getTime() - 3 * 60 * 60 * 1000);
        }
        else if(remind_picker == "5min"){
            Date_alert = simpleDateFormat.format(date_ddl.getTime() - 5 * 60 * 1000);
        }
        else if(remind_picker == "9:00 in the morning"){
            Date date_temp = date_ddl;
            date_temp.setDate(date_ddl.getDate());
            date_temp.setHours(9);
            date_temp.setMinutes(0);
            Date_alert = simpleDateFormat.format(date_temp);
        }
        else if(remind_picker == "22:00 last day"){
            Date date_temp = date_ddl;
            date_temp.setDate(date_ddl.getDate() - 1);
            date_temp.setHours(22);
            date_temp.setMinutes(0);
            Date_alert = simpleDateFormat.format(date_temp);
        }
        else if(remind_picker == "Exact"){//TODO 具体提醒时间 ，暂定同30分钟
            Date_alert = simpleDateFormat.format(date_ddl.getTime() - 30 * 60 * 1000);
        }
        else{           //todo 这个当测试用，不设置时间，会崩
//            Date_alert = simpleDateFormat.parse(simpleDateFormat.format(date.getTime() - 30 * 60 * 1000));
            Log.e("Tag", "error_MyUtil");
        }
        return Date_alert;
    }
}
