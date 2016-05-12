package my;


import java.io.Serializable;
import java.util.Calendar;

public class MyTime implements I_MyCalendar, Serializable {
    private Calendar cTime;

    public MyTime() {
        this.cTime = Calendar.getInstance();
    }

    public MyTime(Calendar c) {
        this.cTime = c;
    }

    public MyTime(int hour, int minute) {
        this();
        setTime(hour, minute);
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

    public void hourAdd(int h) {
        cTime.add(Calendar.HOUR_OF_DAY, h);
    }
    /*以下为方便其它地方调用而创建*/

    public int toMinutes() {
        return this.getHour() * 60 + this.getMinute();
    }


    public int compareTo(MyTime t) {

        return this.toMinutes() - t.toMinutes();
    }


    public static MyTime createFromCalendar(Calendar c) {
        if (c != null) {
            return new MyTime(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
        } else {
            return null;
        }
    }

    @Override
    public void syncFromCalendar(Calendar c) {
        if (c != null) {
            setTime(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
        }
    }

    @Override
    public String convertToString() {
        return String.format("%02d:%02d:00", getHour(), getMinute());
    }

    @Override
    public String convertToLocalString() {
        return String.format("%d时%d分", getHour(), getMinute());
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
            c.set(Calendar.HOUR_OF_DAY, getHour());
            c.set(Calendar.MINUTE, getMinute());
        }
    }

    public static MyTime createFromString(String s) {
        if (s != null) {
            String[] ss = s.split(":");
            return new MyTime(Integer.valueOf(ss[0]), Integer.valueOf(ss[1]));
        } else {
            return null;
        }
    }

}
