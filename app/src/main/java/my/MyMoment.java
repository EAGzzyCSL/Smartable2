package my;

/**
 * Created by EAGzzyCSL on 2016/2/11.
 */
public class MyMoment {
    private MyDate myDate;
    private MyTime myTime;

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
}
