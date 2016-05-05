package my;


import java.util.Calendar;

public class MyTime implements I_MyCalendar {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    public MyTime(int year, int month, int day, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
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

    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return this.month;
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

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
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

    public void setDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public void setMoment(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int compareTo(MyTime t) {
        return this.toMinutes() - t.toMinutes();
    }


    public Calendar toCalendar() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        return c;
    }

    public MyTime createFromCalendar(Calendar c) {
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
    public void syncToCalendar(Calendar c) {
        //待填充
    }

    public static MyTime createFromString(String s) {
        String[] ss = s.split(":");
        return new MyTime(Integer.valueOf(ss[0]), Integer.valueOf(ss[1]));
    }
}
