package my;

import java.util.Calendar;

public class MyDate {
    private int year;
    private int month;
    private int day;

    public MyDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getMonth() {
        return this.month;
    }

    public int getYear() {

        return this.year;
    }

    public int getDay() {
        return this.day;
    }

    public static MyDate fromCalendar(Calendar c) {
        return new MyDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
    }
    //可能还需要提供一个方法供更新日期从calendar
}
