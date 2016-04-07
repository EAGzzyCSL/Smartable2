package my;


import java.util.Calendar;

public class MyTime {
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

    public MyTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
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

    public void setDate(int year ,int month ,int day){
        this.year=year;
        this.month=month;
        this.day=day;
    }
    public void setMoment(int hour,int minute){
        this.hour=hour;
        this.minute=minute;
    }
    public int compareTo(MyTime t) {
        return this.toMinutes() - t.toMinutes();
    }


    public Calendar toCalendar(){
        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month-1);
        c.set(Calendar.DAY_OF_MONTH,day);
        c.set(Calendar.HOUR_OF_DAY,hour);
        c.set(Calendar.MINUTE,minute);
        return c;
    }
}
