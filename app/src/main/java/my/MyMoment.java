package my;

import java.io.Serializable;
import java.util.Calendar;

//TODO 如果使用return this可以实现连续调用
public class MyMoment implements I_MyCalendar, Serializable {
    private MyDate myDate;
    private MyTime myTime;
    private Calendar cM;

    public MyMoment() {
        this.cM = Calendar.getInstance();
        this.myDate = new MyDate(cM);
        this.myTime = new MyTime(cM);
    }

    public MyMoment(String s) {
        this();
        if (s != null) {
            String[] ss = s.split(" ");
            setDate(ss[0]);
            setTime(ss[1]);
        }
    }

    public MyMoment(int year, int month, int day, int hour, int minute) {
        this();
        setDate(year, month, day);
        setTime(hour, minute);
    }

    public void setDate(String s) {
        myDate.setDate(s);
    }

    public void setDate(int year, int month, int day) {
        myDate.setDate(year, month, day);
    }

    public void setTime(String s) {
        myTime.setTime(s);
    }

    public void setTime(int hour, int minute) {
        myTime.setTime(hour, minute);
    }

    public MyMoment newSameMoment() {
        /*创建一个新的moment并为它设置时间，如果需要的话写一个从MyMoment创建对象的构造方法*/
        MyMoment m = new MyMoment();
        m.setDate(this.getYear(), this.getMonth(), this.getDay());
        m.setTime(this.getHour(), this.getMinute());
        return m;
    }

    public void dayAdd(int d) {
        myDate.dayAdd(d);
    }

    public MyMoment hourAdd(int h) {
        myTime.hourAdd(h);
        return this;
    }

    public void minuteAdd(int m) {
        myTime.minuteAdd(m);
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


    public MyDate getDate() {
        return this.myDate;
    }

    public MyTime getTime() {
        return this.myTime;
    }

    public void setDay(int d) {
        myDate.setDay(d);
    }

    public void setHour(int h) {
        myTime.setHour(h);
    }

    /*转换*/
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


    public int compareToNow() {
        return this.compareToNow(Calendar.getInstance());
    }

    public int compareToNow(Calendar c) {
        return cM.compareTo(c);
    }

    public int computeDiff(Calendar c) {
        //实现方法不是很好但是比宦的要好
        return (int) ((c.getTimeInMillis() - cM.getTimeInMillis()) / 1000);
    }

    public int computeDiffWithNow() {
        return computeDiff(Calendar.getInstance());
    }

    public boolean isToday(){
        MyMoment today_moment = new MyMoment();
        if (this.getYear() == today_moment.getYear()
            && this.getMonth() == today_moment.getMonth()
            && this.getDay() == today_moment.getDay() ){
            return true;
        }else {
            return false;
        }
    }
}
