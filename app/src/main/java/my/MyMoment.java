package my;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

public class MyMoment implements I_MyCalendar, Serializable {
    private MyDate myDate;
    private MyTime myTime;
    private Calendar cM;

    public MyMoment() {
        this.cM = Calendar.getInstance();
        this.myDate = new MyDate(cM);
        this.myTime = new MyTime(cM);
    }

    public MyMoment newSameMoment() {
        /*创建一个新的moment并为它设置时间，如果需要的话写一个从MyMoment创建对象的构造方法*/
        MyMoment m = new MyMoment();
        m.setDate(this.getYear(), this.getMonth(), this.getDay());
        m.setTime(this.getHour(), this.getMinute());
        return m;
    }

    public MyMoment hourAdd(int h) {
        myTime.hourAdd(h);
        return this;
    }


    public static MyMoment getNow() {
        return MyMoment.createFromCalendar(Calendar.getInstance());
    }

    public MyMoment(int year, int month, int day, int hour, int minute) {
        this();
        setDate(year, month, day);
        setTime(hour, minute);
    }

    public void setTime(int hour, int minute) {
        myTime.setTime(hour, minute);
    }

    public MyMoment(MyDate myDate, MyTime myTime) {
        this();
        setDate(myDate.getYear(), myDate.getMonth(), myDate.getDay());
        setTime(myTime.getHour(), myTime.getMinute());
    }

    public void setDate(int year, int month, int day) {
        myDate.setDate(year, month, day);
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
        if (c != null) {
            return new MyMoment(MyDate.createFromCalendar(c), MyTime.createFromCalendar(c));
        } else {
            return null;
        }
    }

    @Override
    public void syncFromCalendar(Calendar c) {
        if (c != null) {
            myDate.syncFromCalendar(c);
            myTime.syncFromCalendar(c);
        }
    }

    @Override
    public String convertToString() {
        if (myDate != null && myTime != null) {
            return myDate.convertToString() + " " + myTime.convertToString();
        } else {
            return null;
        }
    }

    @Override
    public String convertToLocalString() {
        if (myDate != null && myTime != null) {
            return myDate.convertToLocalString() + myTime.convertToLocalString();
        } else {
            return null;
        }
    }

    @Override
    public Calendar convertToCalendar() {
        Calendar c = Calendar.getInstance();
        syncToCalendar(c);
        return c;
    }

    @Override
    public void syncToCalendar(Calendar c) {
        if (c != null) {
            myDate.syncToCalendar(c);
            myTime.syncToCalendar(c);
        }
    }

    public static MyMoment createFromString(String s) {
        if (s != null) {
            String[] ss = s.split(" ");
            return new MyMoment(MyDate.createFromString(ss[0]), MyTime.createFromString(ss[1]));
        } else {
            return null;
        }
    }

    public int compareToNow() {
        return this.compareToNow(Calendar.getInstance());
    }

    public int compareToNow(Calendar c) {
        return this.convertToCalendar().compareTo(c);
    }

    public int computeDiff(Calendar c) {
        //实现方法不是很好但是比宦的要好
        return (int) (c.getTimeInMillis() - this.convertToCalendar().getTimeInMillis() / 1000);
    }

    public int computeDiffWithNow() {
        return computeDiff(Calendar.getInstance());
    }

    public MyDate getDate() {
        return this.myDate;
    }

    public MyTime getTime() {
        return this.myTime;
    }

}
