package my;

import java.util.Calendar;

public class MyDate implements I_MyCalendar {
    private int year;
    private int month;
    private int day;

    private void valueSet(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public MyDate(int year, int month, int day) {
        valueSet(year, month, day);
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


    public static MyDate createFromCalendar(Calendar c) {
        return new MyDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void syncFromCalendar(Calendar c) {
        valueSet(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public String convertToString() {
        //这种方法也不是最好的，目前先用这种
        return String.format("%02d-%02d-%02d", year, month, day);
    }

    @Override
    public void syncToCalendar(Calendar c) {
        //待填充
    }

    public static MyDate createFromString(String s) {
        String[] ss = s.split("-");
        return new MyDate(Integer.valueOf(ss[0]), Integer.valueOf(ss[1]), Integer.valueOf(ss[2]));
    }
}
