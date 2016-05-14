package my;


import java.io.Serializable;
import java.util.Calendar;

public class MyTime implements I_MyCalendar, Serializable {
    private Calendar cTime;

    public MyTime() {
        this(Calendar.getInstance());
    }

    public MyTime(Calendar c) {
        this.cTime = c;
    }

    public MyTime(int hour, int minute) {
        this();
        setTime(hour, minute);
    }

    public MyTime(String s) {
        this();
        setTime(s);

    }


    public int getHour() {
        return cTime.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinute() {
        return cTime.get(Calendar.MINUTE);
    }

    public void setHour(int hour) {
        cTime.set(Calendar.HOUR_OF_DAY, hour);
    }

    public void setMinute(int minute) {
        cTime.set(Calendar.MINUTE, minute);
    }

    public void setTime(int hour, int minute) {
        setHour(hour);
        setMinute(minute);
    }

    public void setTime(String s) {
        if (s != null) {
            String[] ss = s.split(":");
            setTime(Integer.valueOf(ss[0]), Integer.valueOf(ss[1]));
        }
    }

    public void hourAdd(int h) {
        cTime.add(Calendar.HOUR_OF_DAY, h);
    }

    public void minuteAdd(int m) {
        cTime.add(Calendar.MINUTE, m);
    }

    /*转换部分*/
    @Override
    public String convertToString() {
        return String.format("%02d:%02d:00", getHour(), getMinute());
    }

    @Override
    public String convertToLocalString() {
        return String.format("%d时%d分", getHour(), getMinute());
    }
    /*以下为方便其它地方调用而创建*/

    public int toMinutes() {
        return this.getHour() * 60 + this.getMinute();
    }

    /*比较部分*/
    public int compareTo(MyTime t) {

        return this.toMinutes() - t.toMinutes();
    }

}
