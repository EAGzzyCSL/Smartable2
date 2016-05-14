package my;


import java.util.Calendar;

import bit.eagzzycsl.smartable2.EnumEntry;

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
    public static MyMoment getAlertTime(MyMoment date, String remind_picker) {
        Calendar c = date.convertToCalendar();

        switch (remind_picker) {
            case "30min": {
                c.add(Calendar.MINUTE, -30);
                break;
            }
            case "1h": {
                c.add(Calendar.HOUR_OF_DAY, -1);
                break;
            }
            case "3h": {
                c.add(Calendar.HOUR_OF_DAY, -3);
                break;
            }
            case "5min": {
                c.add(Calendar.MINUTE, -5);
                break;
            }
            case "9:00 in the morning": {
                c.set(Calendar.HOUR_OF_DAY, 9);
                break;
            }
            case "22:00 last day": {
                c.add(Calendar.DAY_OF_MONTH, -1);
                c.set(Calendar.HOUR_OF_DAY, 22);
                break;
            }
            default: {

                break;
            }
        }

        return MyMoment.createFromCalendar(c);
    }

    public static int indexOf(EnumEntry[] es, EnumEntry e) {
        for (int i = 0; i < es.length; i++) {
            if (es[i] == e) {
                return i;
            }
        }
        return -1;
    }
}