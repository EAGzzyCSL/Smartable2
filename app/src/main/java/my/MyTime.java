package my;


import java.util.Calendar;

public class MyTime implements I_MyCalendar {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    public void setTime(int hour,int minute){
        valueSet(hour,minute);
    }


    private void valueSet(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public MyTime(int hour, int minute) {
        valueSet(hour, minute);
    }

    public MyTime() {

    }

    public int getDay() {
        return this.day;
    }

    public int getHour() {
        return this.hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public int toMinutes() {
        return this.getHour() * 60 + this.getMinute();
    }


    public void setDay(int day) {
        this.day = day;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }


    public void setMoment(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int compareTo(MyTime t) {
        return this.toMinutes() - t.toMinutes();
    }



    public static MyTime createFromCalendar(Calendar c) {
        return new MyTime(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
    }

    @Override
    public void syncFromCalendar(Calendar c) {
        valueSet(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
    }

    @Override
    public String convertToString() {
        return String.format("%02d:%02d:00", hour, minute);
    }

    @Override
    public Calendar convertToCalendar() {
        Calendar c=Calendar.getInstance();
        syncFromCalendar(c);
        return c;
    }

    @Override
    public void syncToCalendar(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY,hour);
        c.set(Calendar.MINUTE,minute);
    }

    public static MyTime createFromString(String s) {
        String[] ss = s.split(":");
        return new MyTime(Integer.valueOf(ss[0]), Integer.valueOf(ss[1]));
    }

}
