package my;


import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

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
        MyMoment m = date.newSameMoment();
        switch (remind_picker) {
            case "30min": {
                m.minuteAdd(-30);
                break;
            }
            case "1h": {
                m.hourAdd(-1);
                break;
            }
            case "3h": {
                m.hourAdd(-3);
                break;
            }
            case "5min": {
                m.minuteAdd(-5);
                break;
            }
            case "9:00 in the morning": {
                m.setHour(9);
                break;
            }
            case "22:00 last day": {
                m.dayAdd(-1);
                m.setHour(22);
                break;
            }
            default: {

                break;
            }
        }
        return m;
    }

    public static int indexOf(EnumEntry[] es, EnumEntry e) {
        for (int i = 0; i < es.length; i++) {
            if (es[i] == e) {
                return i;
            }
        }
        return -1;
    }

    /*传过来一个时间MyMoment_A，返回一个时间Mymoment_B：0.5~1个小时后的某个整/半点 */
    public static MyMoment SuitableTime(MyMoment myMoment_A) {
        //首先将时间加为0.5~1个小时后的某个整/半点
        //10:01 ~ 10:30 => 11:00
        //10:31 ~ 10:59 => 11:30
        //11:00 => 11:30
        //注意小时大于24的情况（我们是在时间父类解决的）
        myMoment_A = myMoment_A.newSameMoment();

        //把小时 * 2 + 分钟%30

        if (myMoment_A.getMinute() >= 1 && myMoment_A.getMinute() <= 30) {
//            myTime.setHour(myTime.getHour() + 1 >= 24 ? myTime.getHour() + 1 - 24 : myTime.getHour() + 1);
            myMoment_A.hourAdd(1);
            myMoment_A.setMinute(0);
        } else if (myMoment_A.getMinute() >= 31 && myMoment_A.getMinute() <= 59) {
            myMoment_A.hourAdd(1);
            myMoment_A.setMinute(30);
        } else {
            myMoment_A.setMinute(30);
        }
        return myMoment_A;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    //设定时间为“昨天”、“今天”等..
    public static void settvTime(TextView tvtime, MyMoment myMoment) {
        tvtime.setVisibility(View.VISIBLE);
        if (myMoment.isToday()) {//今天
            tvtime.setText("TODAY " + String.format("%02d:%02d", myMoment.getHour(), myMoment.getMinute()));
        } else {
            tvtime.setText(myMoment.getMonth() + "/" + myMoment.getDay() + " "
                    + String.format("%02d:%02d", myMoment.getHour(), myMoment.getMinute()));
        }
    }
}