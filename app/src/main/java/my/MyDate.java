package my;

import java.io.Serializable;
import java.util.Calendar;

public class MyDate implements I_MyCalendar, Serializable {
    private Calendar cDate;

    public MyDate() {
        this.cDate = Calendar.getInstance();
    }
    //TODO 不安全
    public MyDate(Calendar c){
        this.cDate=c;
    }

    public MyDate(int year, int month, int day) {
        this();
        setDate(year, month, day);
    }

    public int getYear() {
        return cDate.get(Calendar.YEAR);
    }

    public int getMonth() {
        return cDate.get(Calendar.MONTH);
    }

    public int getDay() {
        return cDate.get(Calendar.DAY_OF_MONTH);
    }

    public void setYear(int year) {
        cDate.set(Calendar.YEAR, year);
    }

    public void setMonth(int month) {
        cDate.set(Calendar.MONTH, month);
    }

    public void setDay(int day) {
        cDate.set(Calendar.DAY_OF_MONTH, day);
    }

    public void setDate(int year, int month, int day) {
        setYear(year);
        setMonth(month);
        setDay(day);
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
        if (c != null) {
            setDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        }
    }

    @Override
    public String convertToString() {
        //这种方法也不是最好的，目前先用这种
        return String.format("%02d-%02d-%02d", getYear(), getMonth(), getDay());
    }

    @Override
    public String convertToLocalString() {
        return String.format("%d年%d月%d日", getYear(), getMonth(), getDay());
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
            c.set(Calendar.YEAR, getYear());
            c.set(Calendar.MONTH, getMonth());
            c.set(Calendar.DAY_OF_MONTH, getDay());
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
