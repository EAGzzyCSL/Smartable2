package my;

import java.io.Serializable;
import java.util.Calendar;

public class MyDate implements I_MyCalendar, Serializable {
    private Calendar cDate;

    //TODO 不安全
    public MyDate(Calendar c) {
        this.cDate = c;
    }

    public MyDate() {
        this(Calendar.getInstance());
    }

    public MyDate(int year, int month, int day) {
        this();
        setDate(year, month, day);
    }

    public MyDate(String s) {
        this();
        setDate(s);
    }


    public int getYear() {
        return cDate.get(Calendar.YEAR);
    }

    public int getMonth() {
        return cDate.get(Calendar.MONTH) + 1;
    }

    public int getDay() {
        return cDate.get(Calendar.DAY_OF_MONTH);
    }

    public void setYear(int year) {
        cDate.set(Calendar.YEAR, year);
    }

    public void setMonth(int month) {
        cDate.set(Calendar.MONTH, month - 1);
    }

    public void setDay(int day) {
        cDate.set(Calendar.DAY_OF_MONTH, day);
    }

    public void setDate(int year, int month, int day) {
        setYear(year);
        setMonth(month);
        setDay(day);
    }

    public void setDate(String s) {
        if (s != null) {
            String[] ss = s.split("-");
            setDate(Integer.valueOf(ss[0]), Integer.valueOf(ss[1]), Integer.valueOf(ss[2]));
        }
    }

    /*转换*/
    @Override
    public String convertToString() {
        //这种方法也不是最好的，目前先用这种
        return String.format("%02d-%02d-%02d", getYear(), getMonth(), getDay());
    }

    @Override
    public String convertToLocalString() {
        return String.format("%d年%d月%d日", getYear(), getMonth(), getDay());
    }

    public void dayAdd(int day) {
        cDate.add(Calendar.DAY_OF_MONTH, day);
    }
}
