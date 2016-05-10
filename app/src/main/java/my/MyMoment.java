package my;

import java.util.Calendar;

public class MyMoment implements I_MyCalendar {
    private MyDate myDate;
    private MyTime myTime;

    public MyMoment() {
        this.myDate = new MyDate();
        this.myTime = new MyTime();
    }
    public static MyMoment getNow(){
        return MyMoment.createFromCalendar(Calendar.getInstance());
    }

    public MyMoment(int year, int month, int day, int hour, int minute) {
        this(new MyDate(year, month, day), new MyTime(hour, minute));
    }

    public void setTime(int hour, int minute) {
        myTime.setTime(hour, minute);
    }

    public MyMoment(MyDate myDate, MyTime myTime) {
        this.myDate = myDate;
        this.myTime = myTime;
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
        return new MyMoment(MyDate.createFromCalendar(c), MyTime.createFromCalendar(c));
    }

    @Override
    public void syncFromCalendar(Calendar c) {
        myDate.syncFromCalendar(c);
        myTime.syncFromCalendar(c);
    }

    @Override
    public String convertToString() {
        return myDate.convertToString() + " " + myTime.convertToString();
    }

    @Override
    public String convertToLocalString() {
        return myDate.convertToLocalString() + myTime.convertToLocalString();
    }

    @Override
    public Calendar convertToCalendar() {
        Calendar c = Calendar.getInstance();
        syncFromCalendar(c);
        return c;
    }

    @Override
    public void syncToCalendar(Calendar c) {
        myDate.syncFromCalendar(c);
        myTime.syncFromCalendar(c);
    }

    public static MyMoment createFromString(String s) {
        String[] ss = s.split(" ");
        return new MyMoment(MyDate.createFromString(ss[0]), MyTime.createFromString(ss[1]));
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
    public MyDate getDate(){
        return this.myDate;
    }
    public MyTime getTime(){
        return this.myTime;
    }

}
