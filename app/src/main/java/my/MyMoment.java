package my;

import java.util.Calendar;

public class MyMoment implements I_MyCalendar {
    private MyDate myDate;
    private MyTime myTime;

    public MyMoment(MyDate myDate, MyTime myTime) {
        this.myDate = myDate;
        this.myTime = myTime;
    }

    public int getYear() {
        return myDate.getYear();
    }

    public int getMonth() {
        return myDate.getMonth();

    }

    public int getDay() {
        return myDate.getDay();
    }

    public int getHour() {
        return myTime.getHour();
    }

    public int getMinute() {
        return myTime.getMinute();
    }

    public static MyMoment createFromCalendar(Calendar c) {
        return null;
    }

    @Override
    public void syncFromCalendar(Calendar c) {
        //待填充
    }

    @Override
    public String convertToString() {
        return myDate.convertToString() + " " + myTime.convertToString();
    }

    @Override
    public void syncToCalendar(Calendar c) {
        //待填充
    }

    public static MyMoment createFromString(String s) {
        String[] ss = s.split(" ");
        return new MyMoment(MyDate.createFromString(ss[0]), MyTime.createFromString(ss[1]));
    }

}
