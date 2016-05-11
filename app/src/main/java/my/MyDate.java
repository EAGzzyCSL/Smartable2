package my;

import java.io.Serializable;
import java.util.Calendar;

public class MyDate implements I_MyCalendar,Serializable {
    private int year;
    private int month;
    private int day;

    private void valueSet(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public void setDate(int year, int month, int day) {
        valueSet(year, month, day);
    }

    public MyDate(int year, int month, int day) {
        valueSet(year, month, day);
    }

    public MyDate() {

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
        if (c != null) {
            return new MyDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        } else {
            return null;
        }
    }

    @Override
    public void syncFromCalendar(Calendar c) {
        if(c!=null){
            valueSet(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        }
    }

    @Override
    public String convertToString() {
        //这种方法也不是最好的，目前先用这种
        return String.format("%02d-%02d-%02d", year, month, day);
    }

    @Override
    public String convertToLocalString() {
        return String.format("%d年%d月%d日", year, month, day);
    }

    @Override
    public Calendar convertToCalendar() {
        Calendar c = Calendar.getInstance();
        syncFromCalendar(c);
        return c;
    }

    @Override
    public void syncToCalendar(Calendar c) {
        if (c != null) {
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
        }
    }

    public static MyDate createFromString(String s) {
        if (s != null) {
            String[] ss = s.split("-");
            return new MyDate(Integer.valueOf(ss[0]), Integer.valueOf(ss[1]), Integer.valueOf(ss[2]));
        } else {
            return null;
        }
    }
}
